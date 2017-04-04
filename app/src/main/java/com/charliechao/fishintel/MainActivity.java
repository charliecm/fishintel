package com.charliechao.fishintel;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements
        MapFragment.OnMapMarkerClickListener,
        SpotsFragment.OnSpotsListInteractionListener,
        SpeciesFragment.OnSpeciesListInteractionListener {

    private ImageView mToolbarLogo;
    private TextView mToolbarTitle;
    private Fragment mFragment;
    private MapFragment mMapFragment;
    private SpotsFragment mSpotsFragment;
    private SpeciesFragment mSpeciesFragment;
    private SettingsFragment mSettingsFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    mToolbarLogo.setVisibility(View.VISIBLE);
                    mToolbarTitle.setVisibility(View.GONE);
                    showFragment(mMapFragment);
                    return true;
                case R.id.navigation_spots:
                    mToolbarLogo.setVisibility(View.GONE);
                    mToolbarTitle.setVisibility(View.VISIBLE);
                    mToolbarTitle.setText(R.string.title_spots);
                    showFragment(mSpotsFragment);
                    return true;
                case R.id.navigation_species:
                    mToolbarLogo.setVisibility(View.GONE);
                    mToolbarTitle.setVisibility(View.VISIBLE);
                    mToolbarTitle.setText(R.string.title_species);
                    showFragment(mSpeciesFragment);
                    return true;
                case R.id.navigation_settings:
                    mToolbarLogo.setVisibility(View.GONE);
                    mToolbarTitle.setVisibility(View.VISIBLE);
                    mToolbarTitle.setText(R.string.title_settings);
                    showFragment(mSettingsFragment);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Check preferences
        // TODO: Check first-time usage
        SharedPreferences sharedPrefs = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        sharedPrefs.edit().putString(Constants.PREF_KEY_VERSION, String.valueOf(BuildConfig.VERSION_CODE)).apply();
        // Ask for location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.PERMISSION_LOCATION);
        }
        // Setup toolbar
        mToolbarLogo = (ImageView) findViewById(R.id.image_toolbar_main_logo);
        mToolbarTitle = (TextView) findViewById(R.id.text_toolbar_main_title);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_main));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Bind bottom nav
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // Add fragments
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        mFragment = mMapFragment = MapFragment.newInstance();
        ft.add(R.id.layout_content, mMapFragment);
        mSpotsFragment = SpotsFragment.newInstance();
        ft.add(R.id.layout_content, mSpotsFragment);
        ft.hide(mSpotsFragment);
        mSpeciesFragment = SpeciesFragment.newInstance();
        ft.add(R.id.layout_content, mSpeciesFragment);
        ft.hide(mSpeciesFragment);
        mSettingsFragment = SettingsFragment.newInstance();
        ft.add(R.id.layout_content, mSettingsFragment);
        ft.hide(mSettingsFragment);
        ft.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSION_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Let map fragment know user location is available
                    mMapFragment.retrieveLocation();
                }
                break;
        }
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (mFragment == fragment) {
            return;
        }
        if (mFragment != null) {
            // Hide currently visible fragment
            ft.hide(mFragment);
        }
        ft.show(fragment);
        ft.commit();
        mFragment = fragment;
    }

    @Override
    public void onMapMarkerClick(SpotItem item) {
        Intent intent = new Intent(this, SpotDetailsActivity.class);
        intent.putExtra("data", item);
        startActivity(intent);
    }

    @Override
    public void onSpeciesListInteraction(SpeciesItem item) {
        Intent intent = new Intent(this, SpeciesDetailsActivity.class);
        intent.putExtra("data", item);
        startActivity(intent);
    }

    @Override
    public void onSpotsListInteraction(SpotItem item) {
        onMapMarkerClick(item);
    }

}
