package org.hash_map;

public class HashMap_Exm<K, V> implements Map<K, V> {

    private Node<K, V>[] table;
    private int size;

    public HashMap_Exm() {
        table = new Node[16];
    }


    @Override
    public V get(K key) {
        int hashedKey = hashKey(key);
        int startIndex = hashedKey;
        if(key == null) return null;
        while (table[hashedKey] != null) {
            if(table[hashedKey].getKey().equals(key)) {
                return table[hashedKey].getValue();
            }
            hashedKey = (hashedKey + 1) % table.length;
            if(startIndex == hashedKey) break;
        }
        return null;
    }

    @Override
    public V remove(K key) {
        int hashedKey = hashKey(key);
        int startIndex = hashedKey;
        if(key == null) return null;
        while (table[hashedKey] != null) {
            if(table[hashedKey].getKey().equals(key)) {
                Node<K,V> deleteElement = table[hashedKey];
                table[hashedKey] = null;
                return deleteElement.getValue();
            }
            hashedKey = (hashedKey + 1) % table.length;
            if(startIndex == hashedKey) break;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public V put(K key, V value) {
        int hashedKey = hashKey(key);
        int startIndex = hashedKey;

        if (key == null) return null;
        sizeExpansion();
        /**
         * Update value if key Exist
         */
        while(occupied(hashedKey)) {
            if(table[hashedKey].getKey().equals(key)) {
                V oldValue = table[hashedKey].getValue();
                table[hashedKey].setValue(value);
                return oldValue;
            }
            hashedKey = (hashedKey + 1) % table.length;
            if (startIndex == hashedKey) {
                throw new RuntimeException("Map is full.");
            }
        }
        table[hashedKey] = new Node<>(key, value);
        size++;
        return null;
    }

    private boolean occupied(int key) {
        return table[key] != null;
    }

    private int hashKey(K key) {
        return Math.abs(key.hashCode() % table.length);
    }

    private void sizeExpansion() {
        if((double) size / table.length >  0.75) {
            Node<K, V>[] oldTable =  table;
            table = (Node<K, V>[]) new Node[oldTable.length * 2];
            size = 0;
            for (int i = 0; i < oldTable.length; i++) {
                if (oldTable[i] != null) {
                    put(oldTable[i].getKey(), oldTable[i].getValue());
                }
            }
        }
    }

}
