package com.charliechao.fishintel;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Custom application class.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Inject Calligraphy for custom font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

}
