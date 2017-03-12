package com.charliechao.fishintel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charliechao.fishintel.SpotsFragment.OnSpotsListInteractionListener;
import com.charliechao.fishintel.dummy.DummyContent.DummyItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link SpotsFragment.OnSpotsListInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class SpotsRecyclerViewAdapter extends RecyclerView.Adapter<SpotsRecyclerViewAdapter.ViewHolder> {

    private  final SpotItem[] mValues;
    private final OnSpotsListInteractionListener mListener;

    public SpotsRecyclerViewAdapter(SpotItem[] items, SpotsFragment.OnSpotsListInteractionListener listener) {
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SpotItem item = mValues[position];


        holder.mItem = item;


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

        public final TextView mContentView;
        public SpotItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            mContentView = (TextView) view.findViewById(R.id.spot_list_text_name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
