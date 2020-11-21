package com.github.ushiosan23.eventsystem.base;

import org.jetbrains.annotations.NotNull;

/**
 * Event object.
 */
public class EventObject extends BaseEventObject {

    /* ---------------------------------------------------------
     *
     * Constructors
     *
     * --------------------------------------------------------- */

    /**
     * Create event object from source.
     *
     * @param source {@link Object} any type object.
     */
    public EventObject(@NotNull Object source) {
        this.source = source;
    }

    /* ---------------------------------------------------------
     *
     * Methods
     *
     * --------------------------------------------------------- */

    /**
     * Get source from object.
     *
     * @param source Target source object.
     * @return {@link EventObject} instance.
     */
    public static EventObject from(@NotNull Object source) {
        return new EventObject(source);
    }

}
