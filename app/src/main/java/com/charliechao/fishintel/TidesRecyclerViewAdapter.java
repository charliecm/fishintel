package com.charliechao.fishintel;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TidesRecyclerViewAdapter extends RecyclerView.Adapter<TidesRecyclerViewAdapter.ViewHolder> {

    public static final int ITEM_WIDTH = 300;
    public static final int BAR_HEIGHT = 120;
    public static final int BAR_MARGIN = 4;

    private final TidesItem[] mValues;
    private ViewGroup mParent;
    private boolean useMetricUnit = true;

    public TidesRecyclerViewAdapter(TidesItem[] items, Context context) {
        mValues = items;
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        if (prefs.getString(Constants.PREF_KEY_METRIC, Constants.PREF_VALUE_METRIC_METRIC).equals(Constants.PREF_VALUE_METRIC_METRIC)) {
            useMetricUnit = true;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_tides_list_item, parent, false);
        mParent = parent;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        TidesItem item = holder.item = mValues[position];
        holder.date.setText(item.getDay());
        int size = item.time.length;
        if (holder.graph.getChildCount() > 0) {
            // Prevent duplication of bars
            return;
        }
        // Populate with bars
        for (int i = 0; i < size; i++) {
            LinearLayout graphItem = (LinearLayout) LayoutInflater.from(mParent.getContext())
                    .inflate(R.layout.tides_graph_item, mParent, false);
            // Bar
            View bar = graphItem.findViewById(R.id.tides_graph_bar);
            float heightPercentage = item.height[i] / item.getMaxHeight();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ITEM_WIDTH / size - (BAR_MARGIN * (size - 1)),
                    (int)(BAR_HEIGHT * heightPercentage));
            params.setMargins(0, (int)(BAR_HEIGHT - BAR_HEIGHT * heightPercentage), BAR_MARGIN, 0);
            bar.setLayoutParams(params);
            // Time
            TextView time = (TextView) graphItem.findViewById(R.id.tides_graph_time);
            time.setText(TidesItem.getHour(item.time[i]));
            // Height
            TextView height = (TextView) graphItem.findViewById(R.id.tides_graph_height);
            if (useMetricUnit) {
                height.setText(item.height[i] + " m");
            } else {
                height.setText((item.height[i] * 3.28084)  + " ft");
            }
            holder.graph.addView(graphItem);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        public final LinearLayout graph;
        public final TextView date;
        public TidesItem item;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            graph = (LinearLayout) view.findViewById(R.id.fragment_tides_item_graph);
            date = (TextView) view.findViewById(R.id.fragment_tides_item_date);
        }

    }
}
