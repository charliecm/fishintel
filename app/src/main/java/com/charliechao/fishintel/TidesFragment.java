package com.charliechao.fishintel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TidesFragment extends Fragment {

    private int mStationId;

    public TidesFragment() {}

    public TidesFragment newInstance() {
        return new TidesFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mStationId = bundle.getInt("stationId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.fragment_tides_list, container, false);
        view.setAdapter(new TidesRecyclerViewAdapter(new TidesItem[1]));
        return view;
    }

}
