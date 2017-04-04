package com.charliechao.fishintel;

class TideStationItem {

    private int mId;
    private int mStationId;
    private String mName;
    private float mLatitude;
    private float mLongitude;

    public TideStationItem(int id, int stationId, String name, float latitude, float longitude) {
        mId = id;
        mStationId = stationId;
        mName = name;
        mLatitude = latitude;
        mLongitude = longitude;
    }

    public int getId() {
        return mId;
    }

    public int getStationId() {
        return mStationId;
    }

    public String getName() {
        return mName;
    }

    public float getLatitude() {
        return mLatitude;
    }

    public float getLongitude() {
        return mLongitude;
    }

}