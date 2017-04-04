package com.charliechao.fishintel;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TidesFragment extends Fragment {

    private int mStationId;
    private TextView mStatus;
    private RecyclerView mList;

    public TidesFragment() {}

    public TidesFragment newInstance() {
        return new TidesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tides_list, container, false);
        mStatus = (TextView) view.findViewById(R.id.fragment_tides_txt_status);
        mList = (RecyclerView) view.findViewById(R.id.fragment_tides_list);
        mList.setNestedScrollingEnabled(false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mStationId = bundle.getInt("stationId");
        }
        new FetchTidesTask().execute(mStationId);
    }

    /**
     * Retrieves tide data by parsing HTML from government site.
     */
    public class FetchTidesTask extends AsyncTask<Integer, Void, Document> {

        @Override
        protected Document doInBackground(Integer... params) {
            try {
                // TODO: Cancel connection on fragment destroy
                return Jsoup.connect("http://www.tides.gc.ca/eng/station?sid=" + params[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Failed to get tides data :(", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Document doc) {
            if (doc == null) {
                mStatus.setText(getString(R.string.tides_status_error_connect));
                return;
            }
            mStatus.setVisibility(View.GONE);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            DateFormat hourFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
            ArrayList<TidesItem> items = new ArrayList<>();
            Elements tables = doc.select(".stationTables table");
            try {
                for (Element table : tables) {
                    // Get table date
                    String dateStr = table.select("thead tr th").get(0).text();
                    Date date;
                    date = dateFormat.parse(dateStr);
                    // Traverse rows
                    Elements rows = table.select("tbody tr");
                    Date[] time = new Date[rows.size()];
                    float[] height = new float[rows.size()];
                    for (int i = 0; i < rows.size(); i++) {
                        // Get time and height for each row
                        Elements columns = rows.get(i).select("td");
                        columns.get(0).text();
                        time[i] = hourFormat.parse(columns.get(0).text());
                        height[i] = Float.parseFloat(columns.get(1).text());
                    }
                    items.add(new TidesItem(date, time, height));
                }
                mList.setAdapter(new TidesRecyclerViewAdapter(items.toArray(new TidesItem[items.size()])));
            } catch (Exception e) {
                // Try and catch'em all
                e.printStackTrace();
                mStatus.setText(getString(R.string.tides_status_error_parse));
            }
        }

    }

}
