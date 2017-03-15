package com.charliechao.fishintel;

/**
 * List item that has a header label.
 */
public class ListHeaderItem extends ListItem {

    public String mLabel;

    public ListHeaderItem(String label) {
        mLabel = label;
    }

    public String getLabel() {
        return mLabel;
    }

    @Override
    public int getType() {
        return TYPE_HEADER;
    }

}
