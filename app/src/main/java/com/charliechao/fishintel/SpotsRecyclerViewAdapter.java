package com.charliechao.fishintel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charliechao.fishintel.SpotFragment.OnListFragmentInteractionListener;
import com.charliechao.fishintel.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class SpotsRecyclerViewAdapter extends RecyclerView.Adapter<SpotsRecyclerViewAdapter.ViewHolder> {

    private final SpotItem[] mValues;
    private final OnListFragmentInteractionListener mListener;


    public SpotsRecyclerViewAdapter(SpotItem[] items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_spot, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SpotsRecyclerViewAdapter.ViewHolder holder, int position) {
        SpotItem item = mValues[position];
        int id = item.getId();

        holder.mItem = item;

        if (id <= 0) {
            holder.mIdView.setText(id);
        }
        holder.mContentView.setText(item.getName());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onSpotsListInteraction(holder.mItem);
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
        public final TextView mIdView;
        public final TextView mContentView;
        public SpotItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
