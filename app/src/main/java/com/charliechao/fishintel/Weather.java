package com.charliechao.fishintel;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by shuangshengmiao on 2017-04-01.
 */

public class Weather extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {



        String  result = "";
        URL url;
        HttpURLConnection urlConnection = null;
        try {
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
        super.onPostExecute(result);

        try {
            JSONObject jsonObject = new JSONObject(result);


//            String weatherInfor = jsonObject.getString("weatherObservation");

            Log.e("asd", result);
            JSONObject weatherObservation = new JSONObject(jsonObject.getString("weatherObservation"));

            String countryCode = weatherObservation.getString("countryCode");
            String temperature = weatherObservation.getString("temperature");
            String datetime = weatherObservation.getString("datetime");


            String clouds = weatherObservation.getString("clouds");


            SpotDetailsActivity.dataTextView.setText(datetime);
            SpotDetailsActivity.temperatureTextView.setText(temperature);
            SpotDetailsActivity.cloudsTextView.setText(clouds);
            SpotDetailsActivity.placeTextView.setText(countryCode);



//            MainActivity.temperatureText.setText(String.valueOf(tempIn)+"°F");
//            MainActivity.nameText.setText (jsonObject.getString("name"));
//            JSONArray jsonArray = new JSONArray(weatherInfor);
//
//            for (int i =0; i< jsonArray.length(); i++){
//                JSONObject jsonPart = jsonArray.getJSONObject(i);
//
//                //JSONObject weatherData =  new JSONObject(jsonObject.getString(""))
//
//
//
//            }
//
//




        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
