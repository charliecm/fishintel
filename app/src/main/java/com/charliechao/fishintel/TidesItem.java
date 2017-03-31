package com.charliechao.fishintel;

import java.util.Date;

public class TidesItem {

    public final Date date;
    public final int[] time;
    public final float[] height;

    public TidesItem(Date date, int[] time, float[] height) {
        this.date = date;
        this.time = time;
        this.height = height;
    }

}
