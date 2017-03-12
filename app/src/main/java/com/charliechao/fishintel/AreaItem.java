package com.charliechao.fishintel;

public class AreaItem {

    private int mId;
    private String mName;
    private String mURL;

    public AreaItem(int id, String name, String url) {
        mId = id;
        mName = name;
        mURL = url;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getURL() {
        return mURL;
    }

}
