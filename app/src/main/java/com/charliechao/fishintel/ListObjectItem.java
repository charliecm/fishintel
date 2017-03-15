package com.charliechao.fishintel;

public class ListItem<T> {

    private T mObject;

    public ListItem(T object) {
        mObject = object;
    }

    public T getObject() {
        return mObject;
    }

}
