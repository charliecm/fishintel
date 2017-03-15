package com.charliechao.fishintel;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SpeciesFragment extends Fragment {

    private OnSpeciesListInteractionListener mListener;

    private ContentDatabase mDB;

    public SpeciesFragment() {}

    public static SpeciesFragment newInstance() {
        return new SpeciesFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSpeciesListInteractionListener) {
            mListener = (OnSpeciesListInteractionListener) context;
            mDB = new ContentDatabase(context);
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSpotsListInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_species_list, container, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.species_list_view);
        recyclerView.setAdapter(new SpeciesRecyclerViewAdapter(mDB.getAllSpecies(), mListener, true));
        return view;
    }
    
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnSpeciesListInteractionListener {
        void onSpeciesListInteraction(SpeciesItem item);
    }

}
