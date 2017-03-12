package com.charliechao.fishintel;

import java.sql.Array;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by shuangshengmiao on 2017-03-11.
 */

public class SpotList {
    private  String mID;
    private String mname;
    private String mRegionId;
    private String mAreaId;
    private String mTideStationID;
    private String mProxyCity;
    private String mWaterType;
    private String mWaterAccess;
    private String mRoadAccess;
    private String mCoordinates;
    private String mSpecieIds;



    public SpotList(String id, String name, String regionid, String areaid,String tidestationid, String proxycity,String watertype, String wateraccess, String roadaccess, String coordinates){
        mID=id;
        mname=name;
        mRegionId =regionid;
        mAreaId =areaid;
        mTideStationID=tidestationid;
        mProxyCity=proxycity;
        mWaterType=watertype;
        mWaterAccess=wateraccess;
        mRoadAccess =roadaccess;
        mCoordinates =coordinates;

    }

    public String[] getName(){
        String total[] = new String[10];
        total[0]=mname;
        total[1]=mRegionId;
        total[2]=mID;
        total[4]=mAreaId;
        total[4]=mTideStationID;
        total[5]=mProxyCity;
        total[6]=mWaterType;
        total[7]=mWaterAccess;
        total[8]=mRoadAccess;
        total[9] =mCoordinates;

        return  total;

    }

    public ArrayList<String> getallspeciesids(String specieids){
        mSpecieIds=specieids;
        ArrayList<String> array = new ArrayList<String>();





    }
}
