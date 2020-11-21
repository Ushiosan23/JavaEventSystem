package com.github.ushiosan23.eventsystem.event;

import com.github.ushiosan23.eventsystem.base.EventObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public final class ListEvent extends EventObject {

    private final List<?> oldList;

    /**
     * Create event object from source.
     *
     * @param source {@link Object} any type object.
     */
    public ListEvent(@NotNull Object source, List<?> old) {
        super(source);
        oldList = old;
    }

    /**
     * Get old list instance.
     *
     * @return last list before change.
     */
    public List<?> getOldList() {
        return oldList;
    }

}
