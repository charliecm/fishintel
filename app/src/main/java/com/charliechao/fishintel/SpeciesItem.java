package com.charliechao.fishintel;

public class SpeciesItem {

    private int mId;
    private String mName;
    private String mLatinName;
    private String mType;
    private String mDescription;
    private int[] mSpotsIds;

    public SpeciesItem(int id, String name, String latinName, String type, String description, int[] spotsIds) {
        mId = id;
        mName = name;
        mLatinName = latinName;
        mType = type;
        mDescription = description;
        mSpotsIds = spotsIds;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getLatinName() {
        return mLatinName;
    }

    public String getType() {
        return mType;
    }

    public String getDescription() {
        return mDescription;
    }

    public int[] getSpotsIds() {
        return mSpotsIds;
    }

}
