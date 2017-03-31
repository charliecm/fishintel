package com.charliechao.fishintel;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SpotsFragment extends Fragment {

    private OnSpotsListInteractionListener mListener;

    private ContentDatabase mDB;

    public SpotsFragment() {}

    public static SpotsFragment newInstance() {
        SpotsFragment fragment = new SpotsFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSpotsListInteractionListener) {
            mListener = (OnSpotsListInteractionListener) context;
            mDB = new ContentDatabase(context);
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSpotsListInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spots_list, container, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.spots_list_view);
        recyclerView.setAdapter(new SpotsRecyclerViewAdapter(mDB.getAllSpots(), mListener, getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnSpotsListInteractionListener {
        void onSpotsListInteraction(SpotItem item);
    }

}
