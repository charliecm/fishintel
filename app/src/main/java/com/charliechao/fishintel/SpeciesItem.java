package com.charliechao.fishintel;

/**
 * Created by shuangshengmiao on 2017-03-11.
 */

public class SpeciesItem {
    private int mId;
    private String mName;
    private String mLatinName;
    private String mType;
    private String mDescription;
    private int[] mLocationIds;
    private int mImage;

    public SpeciesItem(int id, String name, String latinName, String type, String description, int[] locationIds,int image){


            mId = id;
            mName = name;
            mLatinName = latinName;
            mType = type;
            mDescription = description;
            mLocationIds = locationIds;
        mImage = image;


    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }
    public String getlatin() {
        return mLatinName;
    }

    public String gettype() {
        return mType;
    }
    public int[] getlacationids() {
        return mLocationIds;
    }

    public String getdescription() {return mDescription;}
    public int getImage() {
        return mImage;
    }

}