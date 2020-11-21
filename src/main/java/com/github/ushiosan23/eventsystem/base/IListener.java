package com.github.ushiosan23.eventsystem.base;

/**
 * Event interface
 */
public interface IListener {

    /**
     * Called when event is called.
     *
     * @param eventObject Target event.
     */
    void invoke(Object eventObject);

}
