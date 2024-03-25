package com.epam.victor.expirement;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;


public class CopyOnWriteMapNoSync<K, V> implements Map<K, V> {
    //private volatile Map<K, V> internalMap;

    private final AtomicReference<Map<K, V>> reference;

    public CopyOnWriteMapNoSync() {
        reference = new AtomicReference<>(new HashMap<K, V>());
    }

    public CopyOnWriteMapNoSync(int initialCapacity) {
        reference = new AtomicReference<>(new HashMap<K, V>(initialCapacity));
    }

    public CopyOnWriteMapNoSync(Map<K, V> data) {
        reference = new AtomicReference<>(new HashMap<K, V>(data));
    }

    public V put(K key, V value) {
        Object[] result = new Object[1];
        reference.updateAndGet(current -> {
            Map<K, V> copy = new HashMap<>(current);
            V val = copy.put(key, value);
            result[0] = val;
            return copy;
        });
        return (V) result[0];

    }

    public V remove(Object key) {
        Object[] result = new Object[1];
        reference.updateAndGet(current -> {
            Map<K, V> copy = new HashMap<>(current);
            V val = copy.remove(key);
            result[0] = val;
            return copy;
        });
        return (V) result[0];

    }

    public void putAll(Map<? extends K, ? extends V> newData) {
        reference.updateAndGet(current -> {
            Map<K, V> newMap = new HashMap<>(current);
            newMap.putAll(newData);
            return newMap;
        });
    }


    public void clear() {
        reference.updateAndGet(current -> new HashMap<K, V>());
    }


    public int size() {
        return reference.get().size();
    }


    public boolean isEmpty() {
        return reference.get().isEmpty();
    }


    public boolean containsKey(Object key) {
        return reference.get().containsKey(key);
    }


    public boolean containsValue(Object value) {
        return reference.get().containsValue(value);
    }


    public V get(Object key) {
        return reference.get().get(key);
    }


    public Set<K> keySet() {
        return reference.get().keySet();
    }


    public Collection<V> values() {
        return reference.get().values();
    }


    public Set<Entry<K, V>> entrySet() {
        return reference.get().entrySet();
    }

    @Override
    public String toString() {
        return reference.get().toString();
    }
}