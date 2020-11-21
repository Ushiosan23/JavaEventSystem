package com.github.ushiosan23.eventsystem.listener;

import com.github.ushiosan23.eventsystem.base.EventObject;
import com.github.ushiosan23.eventsystem.base.IListener;
import com.github.ushiosan23.eventsystem.event.ValueEvent;

/**
 * Value event interface.
 *
 * @param <T> Target property type.
 */
public interface IValueListener<T> extends IListener {

    /**
     * Set default behaviour.
     *
     * @param eventObject Target event.
     */
    @SuppressWarnings("unchecked")
    @Override
    default void invoke(Object eventObject) {
        if (eventObject instanceof ValueEvent) {
            onValueChanged((ValueEvent<T>) eventObject);
        }
    }

    /**
     * Called when value change.
     *
     * @param event Target event object.
     */
    void onValueChanged(ValueEvent<T> event);

}
