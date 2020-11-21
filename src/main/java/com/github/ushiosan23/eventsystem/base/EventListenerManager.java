package com.github.ushiosan23.eventsystem.base;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;

/**
 * Create event listener manager.<br>
 * Create, delete, fire and manage all event connections.
 */
public class EventListenerManager implements Serializable {

    /* ---------------------------------------------------------
     *
     * Internal static properties
     *
     * --------------------------------------------------------- */

    /**
     * Default array empty.
     */
    private static final Object[] EMPTY = new Object[0];

    /* ---------------------------------------------------------
     *
     * Internal properties
     *
     * --------------------------------------------------------- */

    /**
     * Save all event connections.
     * By default this object is empty.
     */
    protected transient volatile Object[] connections = EMPTY;

    /* ---------------------------------------------------------
     *
     * Public methods
     *
     * --------------------------------------------------------- */

    /**
     * Get all connections from this manager.
     *
     * @return {@link Object} with all connections.
     */
    public Object[] getConnections() {
        return connections;
    }

    /**
     * Get connections by type.
     *
     * @param tClass Target class to get.
     * @param <T>    Generic type.
     * @return {@link T} array instance with result elements.
     */
    @SuppressWarnings({"unchecked"})
    public <T extends IListener> T[] getConnections(Class<T> tClass) {
        // Set internal vars
        Object[] all = connections;
        int size = countConnectionsType(all, tClass);
        // Create array instance
        T[] result = (T[]) Array.newInstance(tClass, size);
        // Iteration
        int position = 0;
        for (int i = all.length - 2; i >= 0; i -= 2) {
            if (all[i] == tClass) {
                T temporal = (T) all[i + 1];
                result[position++] = temporal;
            }
        }

        // Get result
        return result;
    }

    /**
     * Count how many connections has the same type.
     *
     * @param type Search connection type.
     * @return size of elements found.
     */
    public int countConnectionsType(@NotNull Class<?> type) {
        return countConnectionsType(connections, type);
    }

    /**
     * Add listener to current manager.
     *
     * @param tClass   Target class type.
     * @param instance Target instance of listener.
     * @param <T>      Generic type.
     */
    public synchronized <T extends IListener> void add(@NotNull Class<T> tClass, @NotNull T instance) {
        // Check if instance is valid
        if (!tClass.isInstance(instance)) throw new IllegalArgumentException(
                String.format("Listener %s is not of type %s", instance, tClass)
        );

        // Check if connections are empty
        if (connections == EMPTY) {
            // Create new array object with 2 new values.
            // Save in first value class type and then the instance.
            connections = new Object[]{tClass, instance};
        } else {
            // Save size
            int cSize = connections.length;
            // Create new object with new size
            Object[] temp = new Object[cSize + 2];
            // Copy connections to temporal array
            System.arraycopy(connections, 0, temp, 0, cSize);

            // Set values
            temp[cSize] = tClass;
            temp[cSize + 1] = instance;

            // Set new value to connections
            connections = temp;
        }
    }

    /**
     * Remove listener to current manager.
     *
     * @param tClass   Target class type.
     * @param instance Target instance of listener.
     * @param <T>      Generic type.
     */
    public synchronized <T extends IListener> void remove(@NotNull Class<T> tClass, @NotNull T instance) {
        // Check if instance is valid
        if (!tClass.isInstance(instance)) throw new IllegalArgumentException(
                String.format("Listener %s is not of type %s", instance, tClass)
        );
        // Save instance index position
        int found = -1;
        // Iterate all connections
        for (int i = connections.length - 2; i >= 0; i -= 2) {
            if ((connections[i] == tClass) && connections[i + 1].equals(instance)) {
                found = i;
                break;
            }
        }

        // Exit if found is -1
        if (found == -1) return;
        // Remove from connections
        Object[] temp = new Object[connections.length - 2];
        // Copy elements
        System.arraycopy(connections, 0, temp, 0, found);
        // Copy the second part of array
        if (found < temp.length)
            System.arraycopy(connections, found + 2, temp, found, temp.length - found);
        // Set connections
        connections = (temp.length == 0) ? EMPTY : temp;
    }

    /**
     * Write object in object stream.
     *
     * @param stream Object stream serialized.
     * @throws IOException if object is not valid to write.
     */
    @SuppressWarnings({"rawtypes", "ConstantConditions"})
    public void writeObject(@NotNull ObjectOutputStream stream) throws IOException {
        Object[] all = connections;
        // set default stream object
        stream.defaultWriteObject();

        for (int i = 0; i < all.length; i += 2) {
            Class<?> iClass = (Class) all[i];
            IListener iListener = (IListener) all[i + 1];

            // Check if is not null
            if (iListener != null && iClass instanceof Serializable) {
                stream.writeObject(iClass.getName());
                stream.writeObject(iListener);
            }
        }

        stream.writeObject(null);
    }

    /* ---------------------------------------------------------
     *
     * Internal methods
     *
     * --------------------------------------------------------- */

    /**
     * Count how many connections has the same type.
     *
     * @param connections Connection list.
     * @param type        Search connection type.
     * @return size of elements found.
     */
    private int countConnectionsType(@NotNull Object[] connections, @NotNull Class<?> type) {
        int counter = 0;
        // Iterate all connections
        for (int i = 0; i < connections.length; i += 2) {
            // Check if is the same type
            if (type == connections[i])
                counter++;
        }
        // Get size
        return counter;
    }

    /**
     * Read object input stream, read object and add to connections.
     *
     * @param stream Target stream to read.
     * @throws IOException            Error to read.
     * @throws ClassNotFoundException Object class not exists.
     */
    @SuppressWarnings("unchecked")
    private void readObject(@NotNull ObjectInputStream stream) throws IOException, ClassNotFoundException {
        // Reset all connections
        connections = EMPTY;
        // Set default object
        stream.defaultReadObject();
        // Result
        Object listenerOrNull;

        // Iterate result
        while (null != (listenerOrNull = stream.readObject())) {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            IListener iListener = (IListener) stream.readObject();
            String iName = (String) listenerOrNull;
            // Check package access
            checkPackageAccess(iName);
            // Get type
            Class<IListener> temp = (Class<IListener>) Class.forName(iName, true, loader);
            add(temp, iListener);
        }
    }

    /**
     * Custom implementation to ReflectUtil class package.
     *
     * @param name Target package.
     */
    private static void checkPackageAccess(String name) {
        // get security manager
        SecurityManager manager = System.getSecurityManager();
        // Check if manager is null
        if (manager == null) return;
        // Replace slashes
        String cName = name.replace('/', '.');
        // Check start char
        if (cName.startsWith("[")) {
            int cIndex = cName.lastIndexOf('[') + 2;
            if (cIndex > 1 && cIndex < cName.length())
                cName = cName.substring(cIndex);
        }
        // check dots
        int dIndex = cName.lastIndexOf('.');
        if (dIndex != -1) manager.checkPackageAccess(cName.substring(0, dIndex));
    }

}
