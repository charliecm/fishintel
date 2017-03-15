package com.charliechao.fishintel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SpotDetailsActivity extends AppCompatActivity {

    private SpotItem mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_details);
        Intent intent = getIntent();
        if (intent != null) {
            mData = (SpotItem) intent.getSerializableExtra("data");
        }
        TextView txtName = (TextView) findViewById(R.id.spot_details_text_name);
        txtName.setText(mData.getName());
    }

}
