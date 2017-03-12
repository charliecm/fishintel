package com.charliechao.fishintel;

/**
 * Created by shuangshengmiao on 2017-03-11.
 */

public class SpeciesItem {
    private String mId;
    private String mName;
    private String mLatinName;
    private String mType;
    private String mDescription;
    private String mLocationIds;

    public SpeciesItem(String id, String name, String latinName, String type, String description, String locationIds){
        mId=id;
        mName=name;
        mLatinName=latinName;
        mType=type;
        mDescription=description;
        mLocationIds=locationIds;
    }

    public  String[] getmName(){

       String total[] = new String[6];
        total[0]=mName;
        total[1]=mLatinName;
        total[2]=mType;
        total[3]=mDescription;
        total[4]=mLocationIds;
        total[5]=mId;

        return total;

    }
}
