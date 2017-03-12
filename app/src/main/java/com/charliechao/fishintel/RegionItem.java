package com.charliechao.fishintel;

public class RegionItem {

    private int mId;
    private String mName;
    private String mRegion;
    private String mURL;

    public RegionItem(int id, String name, String region, String url) {
        mId = id;
        mName = name;
        mRegion = region;
        mURL = url;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getRegion() {
        return mRegion;
    }

    public String getURL() {
        return mURL;
    }

}
