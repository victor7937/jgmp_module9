package com.epam.victor.expirement;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class CopyOnWriteMap<K, V> implements Map<K, V>{
    private volatile Map<K, V> internalMap;

    private final ReentrantLock lock = new ReentrantLock();

    public CopyOnWriteMap() {
        internalMap = new HashMap<K, V>();
    }

    public CopyOnWriteMap(int initialCapacity) {
        internalMap = new HashMap<K, V>(initialCapacity);
    }

    public CopyOnWriteMap(Map<K, V> data) {
        internalMap = new HashMap<K, V>(data);
    }

    public V put(K key, V value) {
        lock.lock();
        Map<K, V> copy = new HashMap<K, V>(internalMap);
        V val = copy.put(key, value);
        internalMap = copy;
        lock.unlock();
        return val;

    }

    public V remove(Object key) {
        lock.lock();
        Map<K, V> copy = new HashMap<K, V>(internalMap);
        V val = copy.remove(key);
        internalMap = copy;
        lock.unlock();
        return val;

    }

    public void putAll(Map<? extends K, ? extends V> newData) {
        lock.lock();
        Map<K, V> newMap = new HashMap<K, V>(internalMap);
        newMap.putAll(newData);
        internalMap = newMap;
        lock.unlock();
    }


    public void clear() {
        lock.lock();
        internalMap = new HashMap<K, V>();
        lock.unlock();
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