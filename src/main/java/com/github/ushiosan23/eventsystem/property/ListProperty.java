package com.github.ushiosan23.eventsystem.property;

import com.github.ushiosan23.eventsystem.listener.ISizeListListener;

import java.util.ArrayList;
import java.util.List;

public final class ListProperty<T> extends ObjectProperty<ArrayList<T>> {

    /**
     * Create simple property value.
     *
     * @param propertyValue Target value.
     */
    public ListProperty(List<T> propertyValue) {
        super(getList(propertyValue));
    }

    /**
     * Set listener when list size change.
     *
     * @param listener listener value.
     */
    public void setOnSizeChange(ISizeListListener listener) {
        eventListenerManager.add(ISizeListListener.class, listener);
    }

    /**
     * Check list and get valid arraylist.
     *
     * @param list target list to check.
     * @param <Z>  Generic type list.
     * @return {@link ArrayList} result list.
     */
    private static <Z> ArrayList<Z> getList(List<Z> list) {
        return list instanceof ArrayList ? (ArrayList<Z>) list : new ArrayList<>(list);
    }

}
