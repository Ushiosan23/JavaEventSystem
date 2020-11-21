package com.github.ushiosan23.eventsystem.event;

import com.github.ushiosan23.eventsystem.base.EventObject;
import org.jetbrains.annotations.NotNull;

/**
 * Value event object.
 *
 * @param <T> Target value result.
 */
public final class ValueEvent<T> extends EventObject {

    /**
     * Save old value property.
     */
    private final T oldValue;

    /**
     * Save new value property.
     */
    private final T newValue;

    /**
     * Create event object from source.
     *
     * @param source {@link Object} any type object.
     * @param oldVal Last property value
     * @param newVal Current property value
     */
    public ValueEvent(@NotNull Object source, @NotNull T oldVal, @NotNull T newVal) {
        super(source);
        oldValue = oldVal;
        newValue = newVal;
    }

    /**
     * Get old value property.
     *
     * @return last value.
     */
    public T getOldValue() {
        return oldValue;
    }

    /**
     * Get new value property.
     *
     * @return current value.
     */
    public T getNewValue() {
        return newValue;
    }

    /**
     * Create instance from values.
     *
     * @param source {@link Object} any type object.
     * @param vOld   Last property value.
     * @param vNew   Current property value.
     * @param <T>    Generic value type.
     * @return Value event instance.
     */
    public static <T> ValueEvent<T> from(Object source, T vOld, T vNew) {
        return new ValueEvent<>(source, vOld, vNew);
    }

}
