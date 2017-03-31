package com.charliechao.fishintel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TidesFragment extends Fragment {

    public TidesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.fragment_tides_list, container, false);
        view.setAdapter(new TidesRecyclerViewAdapter(new TidesItem[1]));
        return view;
    }

}
