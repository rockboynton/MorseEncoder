/*
 * CS2852
 * Spring 2018
 * Lab 8 - Morse Code Encoder
 * Name: Rock Boynton
 * Created: 5/7/2018
 */

package boyntonrl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Lookup table to store keys and their corresponding values
 * @param <K> the type of keys maintained by this table
 * @param <V> the type of mapped values
 */
public class LookupTable<K extends Comparable<? super K>, V>
        implements Map<K, V> {

    public static void main(String[] args) {
        TreeMap<String, String> utilMap = new TreeMap<>();
        LookupTable<String, String> lut = new LookupTable<>();
        loadMaps(utilMap, lut);
        System.out.println("Test put(): " + (testPut(utilMap, lut) ? "passed" : "failed"));
        System.out.println("Test get(): " + (testGet(utilMap, lut) ? "passed" : "failed"));
        System.out.println("Test size(): " + (testSize(utilMap, lut) ? "passed" : "failed"));
        System.out.println("Test containsKey(): " + (testContainsKey(utilMap, lut) ?
                "passed" : "failed"));
        System.out.println("Test remove(): " + (testRemove(utilMap, lut) ? "passed" : "failed"));
        System.out.println("Test isEmpty(): " + (testIsEmptyAndClear(utilMap, lut) ?
                "passed" : "failed"));
    }

    private static boolean testRemove(TreeMap<String, String> utilMap,
                                      LookupTable<String, String> lut) {
        boolean passed = false;
        if (utilMap.remove("I").equals(lut.remove("I"))) {
            if (utilMap.size() == lut.size()) {
                passed = true;
            }
        }
        return passed;
    }

    private static boolean testContainsKey(TreeMap<String, String> utilMap,
                                           LookupTable<String, String> lut) {
        boolean passed = false;
        if (utilMap.containsKey("Z") == lut.containsKey("Z") &&
                utilMap.containsKey("foo") == lut.containsKey("foo")) {
            passed = true;
        }
        return passed;
    }

    private static boolean testIsEmptyAndClear(TreeMap<String, String> utilMap,
                                               LookupTable<String, String> lut) {
        boolean passed = false;
        if (utilMap.isEmpty() == lut.isEmpty()) {
            utilMap.clear();
            lut.clear();
            if (utilMap.isEmpty() && lut.isEmpty()) {
                passed = true;
            }
        }
        return passed;
    }

    private static boolean testSize(TreeMap<String, String> utilMap,
                                    LookupTable<String, String> lut) {
        boolean passed = false;
        if (utilMap.size() == lut.size()) {
            passed = true;
        }
        return passed;
    }

    private static boolean testPut(TreeMap<String, String> utilMap,
                                   LookupTable<String, String> lut) {
        boolean passed = false;
        if (utilMap.toString().equals(lut.toString())) {
            passed = true;
        }
        return passed;
    }

    private static boolean testGet(TreeMap<String, String> utilMap,
                                       LookupTable<String, String> lut) {
        boolean passed = false;
        if (utilMap.get("A").equals(lut.get("A")) &&
                utilMap.get("Z").equals(lut.get("Z")) &&
                utilMap.get("I").equals(lut.get("I")) &&
                utilMap.get("foo") == lut.get("foo")) { // should be both null
            passed = true;
        }
        return passed;
    }

    private static void loadMaps(TreeMap<String, String> utilMap,
                                 LookupTable<String, String> lut) {
        utilMap.put("A", ".-");
        lut.put("A", ".-");
        utilMap.put("Z", "--..");
        lut.put("Z", "--..");
        utilMap.put("I", "..");
        lut.put("I", "..");
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

    private List<Entry<K, V>> table = new ArrayList<>();

    /**
     * Returns the number of key-value mappings in this map.
     * @return the number of key-value mappings in this map
     */
    @Override
    public int size() {
        return table.size();
    }

    /**
     * Returns true if this map contains no key-value mappings.
     * @return true if this map contains no key-value mappings
     */
    @Override
    public boolean isEmpty() {
        return table.isEmpty();
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     * @param key whose presence in this map is to be tested
     * @return true if this map contains a mapping for the specified key
     * @throws ClassCastException if the key is of an inappropriate type for this map
     */
    @Override
    public boolean containsKey(Object key) throws ClassCastException {
        int location = Collections.binarySearch(table, new Entry<>((K) key, null));
        return location >= 0;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this map contains no
     * mapping for the key.
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if this map contains no
     * mapping for the key
     * @throws ClassCastException if the key is of an inappropriate type for this map
     */
    @Override
    public V get(Object key) throws ClassCastException {
        int location = Collections.binarySearch(table, new Entry<>((K) key, null));
        return (location >= 0) ? table.get(location).getValue() : null;
    }

    /**
     * Associates the specified value with the specified key in this map. If
     * the map previously contained a mapping for the key, the old value is replaced by the
     * specified value. (A map m is said to contain a mapping for a key k if and only if
     * m.containsKey(k) would return true.)
     * @param key key with which the specified value is to be associated
     * @param value the previous value associated with key, or null if there was no mapping for key.
     *             A null return can also indicate that the map previously associated null with key.
     * @return value to be associated with the specified key
     * @throws ClassCastException if the class of the specified key or value prevents it from being
     * stored in this map
     */
    @Override
    public V put(K key, V value) throws ClassCastException {
        V previousValue = null;
        int location = Collections.binarySearch(table, new Entry<>(key, null));
        if (location > 0) {
            previousValue = table.get(location).setValue(value);
        } else {
            table.add(-location - 1, new Entry<>(key, value));
        }
        return previousValue;
    }

    /**
     * Removes the mapping for a key from this map if it is present. More formally, if this map
     * contains a mapping from key k to value v such that (key==null ? k==null : key.equals(k)),
     * that mapping is removed. (The map can contain at most one such mapping.)
     * Returns the value to which this map previously associated the key, or null if the map
     * contained no mapping for the key. Does not necessarily indicate that the map contained no
     * mapping for the key; it's also possible that the map explicitly mapped the key to null.
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with key, or null if there was no mapping for key.
     * @throws ClassCastException if the key is of an inappropriate type for this map (optional)
     */
    @Override
    public V remove(Object key) throws ClassCastException {
        V previousValue = null;
        int location = Collections.binarySearch(table, new Entry<>((K) key, null));
        if (location > 0) {
            previousValue = table.remove(location).getValue();
        }
        return previousValue;
    }

    /**
     * Removes all of the mappings from this map. The map will be empty after this call returns.
     */
    @Override
    public void clear() {
        table.clear();
    }

    /**
     * Returns a string representation of this map. The string representation consists of a list
     * of key-value mappings in the order returned by the map's entrySet view's iterator,
     * enclosed in braces ("{}"). Adjacent mappings are separated by the characters ", " (comma
     * and space). Each key-value mapping is rendered as the key followed by an equals sign
     * ("=") followed by the associated value. Keys and values are converted to strings as by
     * String.valueOf(Object).
     * @return a string representation of this map
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Entry entry : table) {
            sb.append(entry.key + "=" + entry.value + ", ");
        }
        return (sb.toString().length() > 2) ?
                sb.replace(sb.length() - 2, sb.length(), "}").toString() :
                sb.append("}").toString();
    }


    /**
     * Not Implemented
     * @param value
     * @return
     */
    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException("method not supported");
    }

    /**
     * Not Implemented
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException("method not supported");
    }

    /**
     * Not Implemented
     * @return
     */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("method not supported");
    }

    /**
     * Not Implemented
     * @return
     */
    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException("method not supported");
    }

    /**
     * Not Implemented
     * @return
     */
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException("method not supported");
    }
}
