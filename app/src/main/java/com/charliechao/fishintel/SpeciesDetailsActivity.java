package com.charliechao.fishintel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SpeciesDetailsActivity extends Activity implements SpotsFragment.OnSpotsListInteractionListener {

    private SpeciesItem mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_species_details);
        // Get intent data
        Intent intent = getIntent();
        if (intent != null) {
            mData = (SpeciesItem) intent.getSerializableExtra("data");
        } else {
            finish();
        }
        // Setup toolbar
        findViewById(R.id.image_toolbar_main_logo).setVisibility(View.GONE);
        TextView toolbarTitle = (TextView) findViewById(R.id.text_toolbar_main_title);
        toolbarTitle.setText(mData.getName());
        toolbarTitle.setVisibility(View.VISIBLE);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_main));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Set image preview source
        ImageView imgPreview = (ImageView) findViewById(R.id.species_details_image_preview);
        int image = mData.getImage();
        if (image != 0) {
            imgPreview.setImageResource(image);
        } else {
            imgPreview.setVisibility(View.GONE);
        }
        // Set description text
        TextView txtDescription = (TextView) findViewById(R.id.species_details_text_description);
        txtDescription.setText(mData.getDescription());
        // Populate spots list
        ContentDatabase db = new ContentDatabase(this);
        RecyclerView listSpots = (RecyclerView) findViewById(R.id.species_details_spots_list);
        listSpots.setAdapter(new SpotsRecyclerViewAdapter(db.getSpots(mData.getmSpotsIds()), this, null));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onSpotsListInteraction(SpotItem item) {
        Intent intent = new Intent(this, SpotDetailsActivity.class);
        intent.putExtra("data", item);
        startActivity(intent);
    }

}
