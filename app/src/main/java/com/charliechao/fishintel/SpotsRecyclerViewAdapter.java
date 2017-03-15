package com.charliechao.fishintel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charliechao.fishintel.SpotsFragment.OnSpotsListInteractionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * RecyclerView grouped items implementation based on:
 * http://stackoverflow.com/questions/41447044/divide-elements-on-groups-in-recyclerview-or-grouping-recyclerview-items-say-by
 */
public class SpotsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ListItem> mValues;
    private final OnSpotsListInteractionListener mListener;

    public SpotsRecyclerViewAdapter(SpotItem[] items, SpotsFragment.OnSpotsListInteractionListener listener, Context context) {
        HashMap<String, List<SpotItem>> map = getGroupedList(items, context);
        mValues = new ArrayList<>();
        for (String group: map.keySet()) {
            ListHeaderItem header = new ListHeaderItem(group);
            mValues.add(header);
            for (SpotItem spot: map.get(group)) {
                ListObjectItem<SpotItem> item = new ListObjectItem<>(spot);
                mValues.add(item);
            }
        }
        mListener = listener;
    }

    private HashMap<String, List<SpotItem>> getGroupedList(SpotItem[] spots, Context context) {
        HashMap<String, List<SpotItem>> map = new HashMap<>();
        ContentDatabase db = new ContentDatabase(context);
        for (SpotItem item: spots) {
            String key = db.getRegion(item.getRegionId()).getName();
            if (map.containsKey(key)) {
                map.get(key).add(item);
            } else {
                List<SpotItem> list = new ArrayList<>();
                list.add(item);
                map.put(key, list);
            }
        }
        return map;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder;
        View view;
        if (viewType == ListItem.TYPE_HEADER) {
            view = inflater.inflate(R.layout.list_header_item, parent, false);
            viewHolder = new HeaderViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.fragment_spots_list_item, parent, false);
            viewHolder = new ItemViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ListItem listItem = mValues.get(position);
        if (holder.getItemViewType() == ListItem.TYPE_HEADER) {
            ListHeaderItem item = (ListHeaderItem) listItem;
            HeaderViewHolder vh = (HeaderViewHolder) holder;
            vh.mView.setText(item.getLabel());
        } else {
            ListObjectItem<SpotItem> item = (ListObjectItem<SpotItem>) listItem;
            final ItemViewHolder vh = (ItemViewHolder) holder;
            vh.mItem = item.getObject();
            vh.mName.setText(vh.mItem.getName());
            vh.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        mListener.onSpotsListInteraction(vh.mItem);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mValues.get(position).getType();
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public final TextView mView;

        public HeaderViewHolder(View view) {
            super(view);
            mView = (TextView) view;
        }

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView mName;
        public SpotItem mItem;

        public ItemViewHolder(View view) {
            super(view);
            mView = view;
            mName = (TextView) view.findViewById(R.id.spot_list_text_name);
        }
    }

}
