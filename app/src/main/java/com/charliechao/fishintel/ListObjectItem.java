package com.charliechao.fishintel;

/**
 * A list item that has a data object.
 * @param <T> Data object type.
 */
public class ListObjectItem<T> extends ListItem {

    private T mObject;

    public ListObjectItem(T object) {
        mObject = object;
    }

    public T getObject() {
        return mObject;
    }

    @Override
    public int getType() {
        return TYPE_OBJECT;
    }

}
