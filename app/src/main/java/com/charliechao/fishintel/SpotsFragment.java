package com.charliechao.fishintel;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SpotsFragment extends Fragment {

    private OnSpotsListInteractionListener mListener;
    private ContentDatabase db;

    public SpotsFragment() {}

    public static SpotsFragment newInstance() {
        SpotsFragment fragment = new SpotsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spot_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new SpotsRecyclerViewAdapter(db.getAllSpots(), mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSpotsListInteractionListener) {
            db = new ContentDatabase(context);
            mListener = (OnSpotsListInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSpotsListInteractionListener");
        }
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
