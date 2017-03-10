package com.charliechao.fishintel;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import layout.MapFragment;

public class MainActivity extends AppCompatActivity implements MapFragment.OnMapMarkerClickListener {

    public static final String DEBUG_TAG = "MainActivity";

    private Fragment mFragment;
    private ImageView mToolbarLogo;
    private TextView mToolbarTitle;
    private Toolbar mToolbar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    mToolbarLogo.setVisibility(View.VISIBLE);
                    mToolbarTitle.setVisibility(View.GONE);
                    showFragment(MapFragment.newInstance());
                    return true;
                case R.id.navigation_spots:
                    mToolbarLogo.setVisibility(View.GONE);
                    mToolbarTitle.setVisibility(View.VISIBLE);
                    mToolbarTitle.setText(R.string.title_spots);
                    // TODO: Show proper fragment
                    removeFragment();
                    return true;
                case R.id.navigation_species:
                    mToolbarLogo.setVisibility(View.GONE);
                    mToolbarTitle.setVisibility(View.VISIBLE);
                    mToolbarTitle.setText(R.string.title_species);
                    // TODO: Show proper fragment
                    removeFragment();
                    return true;
                case R.id.navigation_settings:
                    mToolbarLogo.setVisibility(View.GONE);
                    mToolbarTitle.setVisibility(View.VISIBLE);
                    mToolbarTitle.setText(R.string.title_settings);
                    showFragment(SettingsFragment.newInstance());
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        mToolbarLogo = (ImageView) findViewById(R.id.image_toolbar_main_logo);
        mToolbarTitle = (TextView) findViewById(R.id.text_toolbar_main_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Bottom nav
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // Show first fragment
        showFragment(mFragment);
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.layout_content, fragment, "main");
        ft.commit();
        mFragment = fragment;
    }

    private void removeFragment() {
        if (mFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(mFragment).commit();
        }
        mFragment = null;
    }

    @Override
    public void onMapMarkerClick(int spotId) {
        Log.i(DEBUG_TAG, String.valueOf(spotId));
    }

}
