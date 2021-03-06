package com.charliechao.fishintel;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Custom database instance and helper class for working with content data.
 */
public class ContentDatabase extends SQLiteAssetHelper {

    private SQLiteDatabase mDB;
    private Context mContext;

    public ContentDatabase(Context context) {
        super(context, Constants.DB_CONTENT_NAME, null, Constants.DB_CONTENT_VERSION);
        mDB = getWritableDatabase();
        mContext = context;
    }

    public SpotItem[] getSpots(int[] ids) {
        String[] columns = { "id", "name", "regionId", "areaId", "proxyCity", "latitude", "longitude", "speciesIds" };
        String selection = "";
        if (ids != null) {
            selection = "id in (" + toSimpleList(ids) + ")";
        }
        Cursor cursor = mDB.query("spots", columns, selection, null, null, null, null);
        ArrayList<SpotItem> items = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int regionId = cursor.getInt(2);
            int areaId = cursor.getInt(3);
            String proxyCity = cursor.getString(4);
            float latitude = cursor.getFloat(5);
            float longitude = cursor.getFloat(6);
            String[] speciesIdsStr = cursor.getString(7).split(",");
            int[] speciesIds = new int[0];
            if (speciesIdsStr[0].length() != 0) {
                speciesIds = new int[speciesIdsStr.length];
                for (int i = 0; i < speciesIds.length; i++) {
                    speciesIds[i] = Integer.parseInt(speciesIdsStr[i]);
                }
            }
            items.add(new SpotItem(id, name, regionId, areaId, proxyCity, latitude, longitude, speciesIds));
        }
        cursor.close();
        return items.toArray(new SpotItem[items.size()]);
    }

    public SpotItem getSpot(int id) {
        try {
            return getSpots(new int[]{ id })[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public SpotItem[] getAllSpots() {
        return getSpots(null);
    }

    public SpeciesItem[] getSpecies(int[] ids) {
        String[] columns = { "id", "name", "latinName", "type", "description", "spotsIds" };
        String selection = "";
        if (ids != null) {
            selection = "id in (" + toSimpleList(ids) + ")";
        }
        Cursor cursor = mDB.query("species", columns, selection, null, null, null, null);
        ArrayList<SpeciesItem> items = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String latinName = cursor.getString(2);
            String type = cursor.getString(3);
            String description = cursor.getString(4);
            String[] spotsIdsStr = cursor.getString(5).split(",");
            int[] spotsIds = new int[0];
            if (spotsIdsStr[0].length() != 0) {
                spotsIds = new int[spotsIdsStr.length];
                for (int i = 0; i < spotsIds.length; i++) {
                    spotsIds[i] = Integer.parseInt(spotsIdsStr[i]);
                }
            }
            int img = mContext.getResources().getIdentifier("species_" + String.valueOf(id), "drawable", mContext.getPackageName());
            items.add(new SpeciesItem(id, name, latinName, type, description, spotsIds, img));
        }
        cursor.close();
        return items.toArray(new SpeciesItem[items.size()]);
    }

    public SpeciesItem getSpecies(int id) {
        try {
            return getSpecies(new int[]{ id })[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public SpeciesItem[] getAllSpecies() {
        return getSpecies(null);
    }

    public AreaItem[] getAreas(int[] ids) {
        String[] columns = { "id", "name", "url" };
        String selection = "";
        if (ids != null) {
            selection = "id in (" + toSimpleList(ids) + ")";
        }
        Cursor cursor = mDB.query("areas", columns, selection, null, null, null, null);
        ArrayList<AreaItem> items = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String url = cursor.getString(2);
            items.add(new AreaItem(id, name, url));
        }
        cursor.close();
        return items.toArray(new AreaItem[items.size()]);
    }

    public AreaItem getArea(int id) {
        try {
            return getAreas(new int[]{ id })[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public AreaItem[] getAllAreas() {
        return getAreas(null);
    }

    public RegionItem[] getRegion(int[] ids) {
        String[] columns = { "id", "name", "region", "url" };
        String selection = "";
        if (ids != null) {
            selection = "id in (" + toSimpleList(ids) + ")";
        }
        Cursor cursor = mDB.query("regions", columns, selection, null, null, null, null);
        ArrayList<RegionItem> items = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String region = cursor.getString(2);
            String url = cursor.getString(3);
            items.add(new RegionItem(id, name, region, url));
        }
        cursor.close();
        return items.toArray(new RegionItem[items.size()]);
    }

    public RegionItem getRegion(int id) {
        try {
            return getRegion(new int[]{ id })[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public RegionItem[] getAllRegions() {
        return getRegion(null);
    }

    public TideStationItem[] getTideStations(int[] ids) {
        String[] columns = { "id", "stationId", "name", "latitude", "longitude" };
        String selection = "";
        if (ids != null) {
            selection = "id in (" + toSimpleList(ids) + ")";
        }
        Cursor cursor = mDB.query("tide_stations", columns, selection, null, null, null, null);
        ArrayList<TideStationItem> items = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int stationId = cursor.getInt(1);
            String name = cursor.getString(2);
            float latitude = cursor.getFloat(3);
            float longitude = cursor.getFloat(4);
            items.add(new TideStationItem(id, stationId, name, latitude, longitude));
        }
        cursor.close();
        return items.toArray(new TideStationItem[items.size()]);
    }

    public TideStationItem getTideStation(int id) {
        try {
            return getTideStations(new int[]{ id })[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public TideStationItem[] getAllTideStations() {
        return getTideStations(null);
    }

    private String toSimpleList(int[] array) {
        String output = Arrays.toString(array);
        return output.substring(1, output.length() - 1);
    }

}
