package com.charliechao.fishintel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SpotDetailsActivity extends AppCompatActivity {

    private SpotItem mData;
    private TextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_details);
        // Get intent data
        Intent intent = getIntent();
        if (intent != null) {
            mData = (SpotItem) intent.getSerializableExtra("data");
        }
        // Setup toolbar
        findViewById(R.id.image_toolbar_main_logo).setVisibility(View.GONE);
        mToolbarTitle = (TextView) findViewById(R.id.text_toolbar_main_title);
        mToolbarTitle.setText(mData.getName());
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_main));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Setup UI
        TextView txtName = (TextView) findViewById(R.id.spot_details_text_name);
        txtName.setText(mData.getName());
    }

}
