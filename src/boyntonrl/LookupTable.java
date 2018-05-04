package boyntonrl;

import java.util.*;

public class LookupTable<K extends Comparable<? super K>, V>
        implements Map<K, V>{

    public static void main(String[] args) {

    }

    private static class Entry<K extends Comparable<? super K>, V>
            implements Comparable<Entry<K, V>>, Map.Entry<K, V> {
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Entry() {
            this(null, null);
        }

        @Override
        public int compareTo(Entry<K, V> o) {
            return this.key.compareTo(o.key);
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }

    private List<Entry<K, V>> table;

    @Override
    public int size() {
        return table.size();
    }

    @Override
    public boolean isEmpty() {
        return table.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return table.contains(key);
    }

    @Override
    public V get(Object key) throws ClassCastException {
        return table.get(Collections.binarySearch(table, new Entry((K) key, null))).getValue();
    }

    @Override
    public V put(K key, V value) throws ClassCastException {
        V previousValue = null;
        int location = Collections.binarySearch(table, new Entry((K) key, null));
        if (location > 0) {
            previousValue = table.get(location).setValue(value);
        } else {
            table.add(-location - 1, new Entry<>(key, value));
        }
        return previousValue;
    }

    // am i supposed to remove the whole entry or set the value to null?
    @Override
    public V remove(Object key) throws ClassCastException {
        V previousValue = null;
        int location = Collections.binarySearch(table, new Entry((K) key, null));
        if (location > 0) {
            previousValue = table.remove(location).getValue();
        }
        return previousValue;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException("method not supported");
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException("method not supported");
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("method not supported");
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException("method not supported");
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException("method not supported");
    }
}
