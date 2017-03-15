package com.charliechao.fishintel;

/**
 * List item class for use with RecyclerView.
 */
public abstract class ListItem {

    public static final int TYPE_OBJECT = 0;
    public static final int TYPE_HEADER = 1;

    abstract public int getType();

}
