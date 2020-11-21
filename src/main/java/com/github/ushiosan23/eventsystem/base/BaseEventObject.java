package com.github.ushiosan23.eventsystem.base;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

/**
 * Base event object class.<br>
 * Define base object behaviour.
 */
public abstract class BaseEventObject implements Serializable {

    /* ---------------------------------------------------------
     *
     * Properties
     *
     * --------------------------------------------------------- */

    /**
     * Serializable field uid
     */
    private static final long serialVersionUID = -3691546201654289072L;

    /**
     * Source event object.
     */
    protected Object source;

    /* ---------------------------------------------------------
     *
     * Methods
     *
     * --------------------------------------------------------- */

    /**
     * Get source object.
     *
     * @return Source event object.
     */
    public Object getSource() {
        return source;
    }

    /**
     * Try to cast object and get result.
     *
     * @param tClass Object casting class.
     * @param <T>    Generic type class.
     * @return {@link T} request type or {@code null} if has an error.
     */
    @Nullable
    public <T> T getSourceTypeOrNull(@NotNull Class<T> tClass) {
        try {
            return tClass.cast(source);
        } catch (ClassCastException ignore) {
            return null;
        }
    }

    /**
     * Get object string representation.
     *
     * @return Event object string.
     */
    @Override
    public String toString() {
        String hexHash = Integer.toHexString(hashCode());
        String className = getClass().getName();

        return String.format("(@%s) %s: [source: %s]", hexHash, className, source);
    }

}
