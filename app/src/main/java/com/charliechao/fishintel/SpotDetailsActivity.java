package com.charliechao.fishintel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class SpotDetailsActivity extends Activity implements SpeciesFragment.OnSpeciesListInteractionListener {

    private SpotItem mData;
    private ImageView mMap;
    private String mRegulationURL;
    private ImageView mWeatherImg;
    private TextView mWeatherDate;
    private TextView mWeatherCondition;
    private TextView mWeatherTemperature;
    private TextView mWeatherWind;
    private boolean mUseCelsius = false;
    private boolean mUseMetricUnit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_details);
        // Get intent data
        Intent intent = getIntent();
        if (intent != null) {
            mData = (SpotItem) intent.getSerializableExtra("data");
        } else {
            finish();
        }
        // Get preferences
        SharedPreferences prefs = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        if (prefs.getString(Constants.PREF_KEY_TEMPERATURE, Constants.PREF_VALUE_TEMPERATURE_CELSIUS).equals(Constants.PREF_VALUE_TEMPERATURE_CELSIUS)) {
            mUseCelsius = true;
        }
        if (prefs.getString(Constants.PREF_KEY_METRIC, Constants.PREF_VALUE_METRIC_METRIC).equals(Constants.PREF_VALUE_METRIC_METRIC)) {
            mUseMetricUnit = true;
        }
        // Get regulation URL
        ContentDatabase db = new ContentDatabase(this);
        mRegulationURL = db.getArea(mData.getAreaId()).getURL();
        // Setup toolbar
        findViewById(R.id.image_toolbar_main_logo).setVisibility(View.GONE);
        TextView toolbarTitle = (TextView) findViewById(R.id.text_toolbar_main_title);
        toolbarTitle.setText(mData.getName());
        toolbarTitle.setVisibility(View.VISIBLE);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_main));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Populate species list
        RecyclerView listSpecies = (RecyclerView) findViewById(R.id.spot_details_species_list);
        listSpecies.setAdapter(new SpeciesRecyclerViewAdapter(db.getSpecies(mData.getSpeciesIds()), this, false));
        listSpecies.setNestedScrollingEnabled(false);
        // Show static map
        // http://stackoverflow.com/questions/6465680/how-to-determine-the-screen-width-in-terms-of-dp-or-dip-at-runtime-in-android
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mMap = (ImageView) findViewById(R.id.spot_details_map);
        new FetchStaticMapTask().execute("http://maps.google.com/maps/api/staticmap?center=" +
                mData.getLatitude() + "," +
                mData.getLongitude() + "&zoom=12&size=" +
                (int) (dm.widthPixels / dm.density) + "x200&scale=" +
                (int) Math.min(Math.ceil(dm.density), 2) + "&markers=color:red%7C" +
                mData.getLatitude() + "," +
                mData.getLongitude() + "&sensor=false");
        // Weather
        mWeatherImg = (ImageView) findViewById(R.id.spot_details_weather_img);
        mWeatherDate = (TextView) findViewById(R.id.spot_details_weather_date);
        mWeatherCondition = (TextView) findViewById(R.id.spot_details_weather_condition);
        mWeatherTemperature = (TextView) findViewById(R.id.spot_details_weather_temperature);
        mWeatherWind = (TextView)findViewById(R.id.spot_details_weather_wind);
        new FetchWeatherTask().execute("http://api.geonames.org/findNearByWeatherJSON?lat=49&lng=-125&username=smiao381");
        // Tides
        // Find the closest tide station
        // TODO: Map tide stations to spots for best accuracy
        db = new ContentDatabase(this);
        double minDistance = Double.MAX_VALUE;
        int stationId = 0;
        String stationName = "";
        for (TideStationItem item: db.getAllTideStations()) {
            double distance = getDistance(mData.getLatitude(), item.getLatitude(), mData.getLongitude(), item.getLongitude(), 0, 0);
            if (distance < minDistance) {
                minDistance = distance;
                stationId = item.getStationId();
                stationName = item.getName();
            }
        }
        // Create tides fragment
        TidesFragment tidesFragment = new TidesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("stationId", stationId);
        bundle.putString("stationName", stationName);
        tidesFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.spot_details_tides, tidesFragment);
        transaction.commit();
    }

    /**
     * Opens direction for spot.
     * https://developers.google.com/maps/documentation/android-api/intents#launch_turn-by-turn_navigation
     */
    public void openDirections(View view) {
        String url = "google.navigation:q=" + mData.getLatitude() + "," + mData.getLongitude();
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    /**
     * Opens government area regulations webpage.
     */
    public void openRegulationLink(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(mRegulationURL));
        startActivity(intent);
    }

    /**
     * http://stackoverflow.com/a/16794680
     * Calculates the distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     * @returns Distance in meters.
     */
    public double getDistance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {
        final int R = 6371; // Radius of the earth
        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000;
        double height = el1 - el2;
        distance = Math.pow(distance, 2) + Math.pow(height, 2);
        return Math.sqrt(distance);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onSpeciesListInteraction(SpeciesItem item) {
        Intent intent = new Intent(this, SpeciesDetailsActivity.class);
        intent.putExtra("data", item);
        startActivity(intent);
    }

    /**
     * Retrieves a static Google Maps image and sets it to map image view.
     * http://www.techrepublic.com/article/pro-tip-use-googles-static-maps-api-to-illustrate-location-in-your-android-apps/
     * http://www.codexpedia.com/android/asynctask-and-httpurlconnection-sample-in-android/
     */
    public class FetchStaticMapTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bmp = null;
            HttpURLConnection http = null;
            URL url;
            try {
                // Make HTTP connection
                // TODO: Cancel logic
                url = new URL(params[0]);
                http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("GET");
                http.connect();
                InputStream inputStream = http.getInputStream();
                if (inputStream == null) {
                    return null;
                }
                // Read as bitmap
                bmp = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                return null;
            } finally {
                if (http != null) {
                    http.disconnect();
                }
            }
            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bmp) {
            if (bmp != null) {
                mMap.setImageBitmap(bmp);
            }
        }

    }

    /**
     * Retrieves weather data for spot location.
     */
    public class FetchWeatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String  result = "";
            URL url;
            HttpURLConnection urlConnection;
            try {
                // Make HTTP connection
                // TODO: Cancel logic
                url = new URL (urls [0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1){
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return  result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected  void onPostExecute(String result){
            try {
                // Extract weather data
                JSONObject jsonObject = new JSONObject(result);
                JSONObject weatherObservation = new JSONObject(jsonObject.getString("weatherObservation"));
                String date = weatherObservation.getString("datetime");
                String condition = weatherObservation.getString("weatherCondition");
                String cloud = weatherObservation.getString("clouds");
                if (condition.equals("n/a") && !cloud.isEmpty()) {
                    condition = WordUtils.capitalize(cloud);
                }
                String temperature = weatherObservation.getString("temperature");
                if (mUseCelsius) {
                    temperature = (Integer.parseInt(temperature)) + " °C";
                } else {
                    temperature = (int)(Integer.parseInt(temperature) * 1.8 + 32) + " °F";
                }
                String wind = weatherObservation.getString("windSpeed");
                if (mUseMetricUnit) {
                    wind = (Integer.parseInt(wind)) + " km/h";
                } else {
                    wind = (int)(Float.parseFloat(wind) * 0.621371) + " mph";
                }
                SimpleDateFormat dateParse = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d", Locale.ENGLISH);
                mWeatherDate.setText(dateFormat.format(dateParse.parse(date)));
                mWeatherCondition.setText(condition);
                mWeatherTemperature.setText(temperature);
                mWeatherWind.setText(wind);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
