package com.epam.victor.expirement;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;


public class CopyOnWriteMapNoSync<K, V> implements Map<K, V>{
    private volatile Map<K, V> internalMap;

    public CopyOnWriteMapNoSync() {
        internalMap = new HashMap<K, V>();
    }

    public CopyOnWriteMapNoSync(int initialCapacity) {
        internalMap = new HashMap<K, V>(initialCapacity);
    }

    public CopyOnWriteMapNoSync(Map<K, V> data) {
        internalMap = new HashMap<K, V>(data);
    }

    public V put(K key, V value) {
        Map<K, V> copy = new HashMap<K, V>(internalMap);
        V val = copy.put(key, value);
        internalMap = copy;
        return val;

    }

    public V remove(Object key) {
        Map<K, V> copy = new HashMap<K, V>(internalMap);
        V val = copy.remove(key);
        internalMap = copy;
        return val;

    }

    public void putAll(Map<? extends K, ? extends V> newData) {
        Map<K, V> newMap = new HashMap<K, V>(internalMap);
        newMap.putAll(newData);
        internalMap = newMap;
    }


    public void clear() {
        internalMap = new HashMap<K, V>();
    }


    public int size() {
        return internalMap.size();
    }


    public boolean isEmpty() {
        return internalMap.isEmpty();
    }


    public boolean containsKey(Object key) {
        return internalMap.containsKey(key);
    }


    public boolean containsValue(Object value) {
        return internalMap.containsValue(value);
    }


    public V get(Object key) {
        return internalMap.get(key);
    }


    public Set<K> keySet() {
        return internalMap.keySet();
    }


    public Collection<V> values() {
        return internalMap.values();
    }


    public Set<Entry<K, V>> entrySet() {
        return internalMap.entrySet();
    }

    @Override
    public String toString() {
        return internalMap.toString();
    }
}