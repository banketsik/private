package org.hash_map;

public interface Map<K, V> {
    public V put(K key, V value);
    public V get(K key);
    public V remove(K key);
    public int size();
    public default boolean isEmpty() {
        return size() == 0;
    }
}
