package com.charliechao.fishintel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.charliechao.fishintel.SpeciesFragment.OnSpeciesListInteractionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * RecyclerView grouped items implementation based on:
 * http://stackoverflow.com/questions/41447044/divide-elements-on-groups-in-recyclerview-or-grouping-recyclerview-items-say-by
 */
public class SpeciesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ListItem> mValues;
    private final OnSpeciesListInteractionListener mListener;
    private boolean mIsGrouped = false;

    public SpeciesRecyclerViewAdapter(SpeciesItem[] items, OnSpeciesListInteractionListener listener, boolean isGrouped) {
        mIsGrouped = isGrouped;
        if (isGrouped) {
            HashMap<String, List<SpeciesItem>> map = getGroupedList(items);
            mValues = new ArrayList<>();
            for (String group: map.keySet()) {
                ListHeaderItem header = new ListHeaderItem(group);
                mValues.add(header);
                for (SpeciesItem spot: map.get(group)) {
                    ListObjectItem<SpeciesItem> item = new ListObjectItem<>(spot);
                    mValues.add(item);
                }
            }
        } else {
            mValues = new ArrayList<>();
            for (SpeciesItem species: items) {
                ListObjectItem<SpeciesItem> item = new ListObjectItem<>(species);
                mValues.add(item);
            }
        }
        mListener = listener;
    }

    private HashMap<String, List<SpeciesItem>> getGroupedList(SpeciesItem[] spots) {
        HashMap<String, List<SpeciesItem>> map = new HashMap<>();
        for (SpeciesItem item: spots) {
            String key = item.getType();
            if (map.containsKey(key)) {
                map.get(key).add(item);
            } else {
                List<SpeciesItem> list = new ArrayList<>();
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
            view = inflater.inflate(R.layout.fragment_species_list_item, parent, false);
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
            ListObjectItem<SpeciesItem> item = (ListObjectItem<SpeciesItem>) listItem;
            final ItemViewHolder vh = (ItemViewHolder) holder;
            vh.mItem = item.getObject();
            int image = vh.mItem.getImage();
            if (image != 0) {
                vh.mImage.setImageResource(image);
            }
            vh.mName.setText(vh.mItem.getName());
            vh.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        mListener.onSpeciesListInteraction(vh.mItem);
                    }
                }
            });
            if (!mIsGrouped) {
                // Change layout slightly for non-grouped item
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) vh.mView.getLayoutParams();
                params.bottomMargin = 8;
                vh.mView.setLayoutParams(params);
                vh.mChevron.setVisibility(View.GONE);
            }
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
        public final ImageView mImage;
        public final TextView mName;
        public final ImageView mChevron;
        public SpeciesItem mItem;

        public ItemViewHolder(View view) {
            super(view);
            mView = view;
            mImage = (ImageView) view.findViewById(R.id.species_list_img);
            mName = (TextView) view.findViewById(R.id.species_list_text_name);
            mChevron = (ImageView) view.findViewById(R.id.species_list_chevron);
        }

    }

}
