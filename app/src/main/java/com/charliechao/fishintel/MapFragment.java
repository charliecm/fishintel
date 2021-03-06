package com.charliechao.fishintel;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMarkerClickListener, View.OnClickListener {

    private OnMapMarkerClickListener mListener;

    private SpotItem[] mItems;
    private MapView mMapView;
    private GoogleMap mMap;
    private LinearLayout mLicense;

    public MapFragment() {}

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMapMarkerClickListener) {
            mListener = (OnMapMarkerClickListener) context;
            ContentDatabase db = new ContentDatabase(context);
            mItems = db.getAllSpots();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = (MapView) v.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
        mLicense = (LinearLayout) v.findViewById(R.id.map_btn_license);
        mLicense.setOnClickListener(this);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (view == mLicense) {
            // Open license info webpage
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://www.fishing.gov.bc.ca/"));
            startActivity(intent);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        int width = getResources().getDisplayMetrics().widthPixels;
        mMap = googleMap;
        int i = 0;
        for (SpotItem item: mItems) {
            // Create spots markers
            LatLng latLng = new LatLng(item.getLatitude(), item.getLongitude());
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(item.getName()));
            marker.setTag(i);
            builder.include(marker.getPosition());
            i++;
        }
        // Show all markers in the view
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), width, width, 120));
        mMap.setOnMarkerClickListener(this);
        retrieveLocation();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (mListener != null) {
            mListener.onMapMarkerClick(mItems[(int) marker.getTag()]);
            return true;
        }
        return false;
    }

    public void retrieveLocation() {
        if (mMap != null &&
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Show user location on map
            mMap.setMyLocationEnabled(true);
        } else {
            Toast.makeText(getContext(), "Unable to find your location.", Toast.LENGTH_LONG).show();
        }
    }

    public interface OnMapMarkerClickListener {
        void onMapMarkerClick(SpotItem item);
    }

}
