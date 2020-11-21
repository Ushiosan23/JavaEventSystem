package com.github.ushiosan23.eventsystem.property;

import com.github.ushiosan23.eventsystem.base.EventListenerManager;
import com.github.ushiosan23.eventsystem.base.IListener;
import com.github.ushiosan23.eventsystem.event.ValueEvent;
import com.github.ushiosan23.eventsystem.listener.IValueListener;

/**
 * Value property class. This class create simple object with events.
 *
 * @param <T> Generic type.
 */
public class ObjectProperty<T> {

    /* ---------------------------------------------------------
     *
     * Properties
     *
     * --------------------------------------------------------- */

    /**
     * Create event manager
     */
    protected final EventListenerManager eventListenerManager = new EventListenerManager();

    /**
     * Save current value property.
     */
    private T propertyValue;

    /* ---------------------------------------------------------
     *
     * Constructors
     *
     * --------------------------------------------------------- */

    /**
     * Create simple property value.
     *
     * @param propertyValue Target value.
     */
    public ObjectProperty(T propertyValue) {
        this.propertyValue = propertyValue;
    }

    /* ---------------------------------------------------------
     *
     * Public methods
     *
     * --------------------------------------------------------- */

    /**
     * Get property value.
     *
     * @return Current property value.
     */
    public T getPropertyValue() {
        return propertyValue;
    }

    /**
     * Set property value.
     *
     * @param propertyValue Target value to set.
     */
    public void setPropertyValue(T propertyValue) {
        T oldVal = getPropertyValue();
        this.propertyValue = propertyValue;
        fireEvent(IValueListener.class, new ValueEvent<>(this, oldVal, propertyValue));
    }

    /**
     * Add change listener.
     *
     * @param listener Target listener.
     */
    public void setOnChange(IValueListener<T> listener) {
        eventListenerManager.add(IValueListener.class, listener);
    }

    /**
     * Remove change listener from property.
     *
     * @param listener Target listener.
     */
    public void removeOnChange(IValueListener<T> listener) {
        eventListenerManager.remove(IValueListener.class, listener);
    }

    /**
     * Get object string representation.
     *
     * @return String representation.
     */
    @Override
    public String toString() {
        String pHex = Integer.toHexString(getPropertyValue().hashCode());
        String pClass = getPropertyValue().getClass().getName();

        return String.format("(@%s) %s: %s", pHex, pClass, getPropertyValue());
    }

    /* ---------------------------------------------------------
     *
     * Internal methods
     *
     * --------------------------------------------------------- */

    /**
     * Fire event to listeners.
     *
     * @param listener    Target listener class.
     * @param eventObject Send event.
     */
    protected void fireEvent(Class<? extends IListener> listener, Object eventObject) {
        // Get all listeners
        IListener[] listeners = eventListenerManager.getConnections(listener);
        // Send events
        for (IListener iListener : listeners) {
            iListener.invoke(eventObject);
        }
    }

}
