package com.charliechao.fishintel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import layout.OnboardingSlideFragment;

public class OnboardingActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private static final int ITEMS_COUNT = 3;

    private ViewPager mPager;
    private Button mNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        mNext = (Button) findViewById(R.id.onboarding_next);
        mPager = (ViewPager) findViewById(R.id.onboarding_viewpager);
        mPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        mPager.addOnPageChangeListener(this);
    }

    /**
     * Shows the next slide.
     */
    public void next(View view) {
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
        if (mPager.getCurrentItem() == ITEMS_COUNT - 1) {
            finishOnboarding();
        }
    }

    /**
     * Completes the onboarding process.
     */
    public void finishOnboarding() {
        SharedPreferences prefs = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(Constants.PREF_KEY_FIRST_TIME, true).apply();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPageSelected(int position) {
        if (position == ITEMS_COUNT - 1) {
            mNext.setText(R.string.onboarding_next_go_label);
        } else {
            mNext.setText(R.string.onboarding_next_label);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageScrollStateChanged(int state) {}

    private class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return OnboardingSlideFragment.newInstance(R.drawable.ic_location_24dp, getString(R.string.onboarding_slide1_text), getString(R.string.onboarding_slide1_subtext));
                case 1:
                    return OnboardingSlideFragment.newInstance(R.drawable.ic_sim_card_alert_black_24dp, getString(R.string.onboarding_slide2_text), "");
                case 2:
                    return OnboardingSlideFragment.newInstance(R.drawable.ic_fish_24dp, getString(R.string.onboarding_slide3_text), "");
            }
            return null;
        }

        @Override
        public int getCount() {
            return ITEMS_COUNT;
        }
    }

}
