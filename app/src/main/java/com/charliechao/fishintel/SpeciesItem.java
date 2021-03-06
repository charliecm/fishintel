package com.charliechao.fishintel;

import java.io.Serializable;

public class SpeciesItem implements Serializable {

    private int mId;
    private String mName;
    private String mLatinName;
    private String mType;
    private String mDescription;
    private int[] mSpotsIds;
    private int mImage;

    public SpeciesItem(int id, String name, String latinName, String type, String description, int[] spotsIds, int image){
        mId = id;
        mName = name;
        mLatinName = latinName;
        mType = type;
        mDescription = description;
        mSpotsIds = spotsIds;
        mImage = image;
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

    public int[] getmSpotsIds() {
        return mSpotsIds;
    }

    public String getDescription() {return mDescription;}

    public int getImage() {
        return mImage;
    }

}
