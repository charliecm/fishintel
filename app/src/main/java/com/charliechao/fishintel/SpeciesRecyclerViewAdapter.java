package com.charliechao.fishintel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.graphics.drawable.Drawable;

import com.charliechao.fishintel.SpeciesFragment.OnSpeciesListInteractionListener;




public class SpeciesRecyclerViewAdapter extends RecyclerView.Adapter<SpeciesRecyclerViewAdapter.ViewHolder> {

    private final SpeciesItem[] mValues;
    private final SpeciesFragment.OnSpeciesListInteractionListener mListener;

    public SpeciesRecyclerViewAdapter(SpeciesItem[] items, SpeciesFragment.OnSpeciesListInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_species, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SpeciesItem item = mValues[position];
        int image = item.getImage();
        holder.mItem = item;
        if (image != 0) {
            holder.mImage.setImageResource(image);
        }
        holder.mName.setText(item.getName());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onSpeciesListInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final ImageView mImage;
        public final TextView mName;
        public SpeciesItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            mImage = (ImageView) view.findViewById(R.id.species_species_list_img);
            mName = (TextView) view.findViewById(R.id.species_content);
        }

    }

}
