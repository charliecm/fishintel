package com.charliechao.fishintel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TidesRecyclerViewAdapter extends RecyclerView.Adapter<TidesRecyclerViewAdapter.ViewHolder> {

    private final TidesItem[] mValues;

    public TidesRecyclerViewAdapter(TidesItem[] items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_tides_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        TidesItem item = holder.mItem = mValues[position];
    }

    @Override
    public int getItemCount() {
        return mValues.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public TidesItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
        }

    }
}
