package com.charliechao.fishintel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SpeciesDetailsActivity extends AppCompatActivity {

    private SpeciesItem mData;
    private TextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_species_details);
        // Get intent data
        Intent intent = getIntent();
        if (intent != null) {
            mData = (SpeciesItem) intent.getSerializableExtra("data");
        }
        // Setup toolbar
        findViewById(R.id.image_toolbar_main_logo).setVisibility(View.GONE);
        mToolbarTitle = (TextView) findViewById(R.id.text_toolbar_main_title);
        mToolbarTitle.setText(mData.getName());
        mToolbarTitle.setVisibility(View.VISIBLE);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_main));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    }

}
