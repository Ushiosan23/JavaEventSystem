package com.github.ushiosan23.eventsystem.listener;

import com.github.ushiosan23.eventsystem.base.IListener;
import com.github.ushiosan23.eventsystem.event.ValueEvent;

public interface ISizeListListener extends IListener {

    @SuppressWarnings("unchecked")
    @Override
    default void invoke(Object eventObject) {
        if (eventObject instanceof ValueEvent) {
            ValueEvent<?> event = (ValueEvent<?>) eventObject;

            if (event.getSource() instanceof Integer) {
                onSizeChanged((ValueEvent<Integer>) event);
            }
        }
    }

    void onSizeChanged(ValueEvent<Integer> event);

}
