package com.charliechao.fishintel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TidesItem {

    public final Date date;
    public final Date[] time;
    public final float[] height;
    private final float mMaxHeight;

    public TidesItem(Date date, Date[] time, float[] height) {
        this.date = date;
        this.time = time;
        this.height = height;
        float max = 0;
        for (float h: height) {
            if (h > max) {
                max = h;
            }
        }
        mMaxHeight = max;
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d", Locale.ENGLISH);
        return dateFormat.format(this.date);
    }

    public float getMaxHeight() {
        return mMaxHeight;
    }

    public static String getHour(Date time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm", Locale.ENGLISH);
        return dateFormat.format(time);
    }

}
