package com.charliechao.fishintel;

import java.io.Serializable;

public class SpotItem implements Serializable {

    private int mId;
    private String mName;
    private int mRegionId;
    private int mAreaId;
    private String mProxyCity;
    private float mLatitude;
    private float mLongitude;
    private int[] mSpeciesIds;

    public SpotItem(int id, String name, int regionId, int areaId, String proxyCity, float latitude, float longitude, int[] speciesIds) {
        mId = id;
        mName = name;
        mRegionId = regionId;
        mAreaId = areaId;
        mProxyCity = proxyCity;
        mLatitude = latitude;
        mLongitude = longitude;
        mSpeciesIds = speciesIds;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public int getRegionId() {
        return mRegionId;
    }

    public int getAreaId() {
        return mAreaId;
    }

    public String getProxyCity() {
        return mProxyCity;
    }

    public float getLatitude() {
        return mLatitude;
    }

    public float getLongitude() {
        return mLongitude;
    }

    public int[] getSpeciesIds() {
        return mSpeciesIds;
    }


}
