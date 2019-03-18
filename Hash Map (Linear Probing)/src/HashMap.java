import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Your implementation of HashMap.
 * 
 * @author Balkrishna Patel
 * @userid bpatel66
 * @GTID 903023192
 * @version 1.0
 */
public class HashMap<K, V> {

    // DO NOT MODIFY OR ADD NEW GLOBAL/INSTANCE VARIABLES
    public static final int INITIAL_CAPACITY = 13;
    public static final double MAX_LOAD_FACTOR = 0.67;
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    @SuppressWarnings("unchecked")
    public HashMap(int initialCapacity) {
        table = (MapEntry<K, V>[]) new MapEntry[initialCapacity];
        size = 0;
    }

    /**
     * Adds the given key-value pair to the HashMap.
     * If an entry in the HashMap already has this key, replace the entry's
     * value with the new one passed in.
     *
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     *
     * At the start of the method, you should check to see if the array
     * violates the max load factor. For example, let's say the array is of
     * length 5 and the current size is 3 (LF = 0.6). For this example, assume
     * that no elements are removed in between steps. If a non-duplicate key is
     * added, the size will increase to 4 (LF = 0.8), but the resize shouldn't
     * occur until the next put operation.
     *
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key key to add into the HashMap
     * @param value value to add into the HashMap
     * @throws IllegalArgumentException if key or value is null
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("The key and/or the value that"
                    + " was passed in is null.");
        }
        if (((double) size / table.length) > MAX_LOAD_FACTOR) {
            int newTableSize = 2 * table.length + 1;
            resizeBackingTable(newTableSize);
        }
        int hash = Math.abs(key.hashCode() % table.length);
        if (table[hash] == null) {
            table[hash] = new MapEntry<>(key, value);
            size++;
            return null;
        } else if (table[hash].getKey().equals(key)) {
            V oldValue = table[hash].getValue();
            table[hash].setValue(value);
            return oldValue;
        } else if (table[hash] != null) {
            int numProbes = 0;
            int removedEntryIndex = 0;
            boolean removedEntryFound = false;
            while (table[(hash + numProbes) % table.length] != null) {
                int currentHash = (hash + numProbes) % table.length;
                if (numProbes >= table.length) { return null; }
                if (table[currentHash].isRemoved() && !removedEntryFound) {
                    removedEntryIndex = currentHash;
                    removedEntryFound = true;
                    numProbes++;
                } else if (table[currentHash].getKey().equals(key)) {
                    V oldValue = table[currentHash].getValue();
                    table[currentHash].setValue(value);
                    return oldValue;
                } else {
                    numProbes++;
                }
            }

            if (removedEntryFound) {
                //table[removedEntryIndex] = null;
                table[removedEntryIndex] = new MapEntry<>(key, value);
                size++;
                return null;
            } else {
                table[(hash + numProbes) % table.length] =
                        new MapEntry<>(key, value);
                size++;
                return null;
            }
        }
        return null;
    }

    /**
     * Removes the entry with a matching key from the HashMap.
     *
     * @param key the key to remove
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key does not exist
     * @return the value previously associated with the key
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key that was passed in is"
                    + " null.");
        }
        if (size == 0) {
            throw new NoSuchElementException("There are no elements in the " +
                    "Hash Map.");
        }
        int hash = Math.abs(key.hashCode() % table.length);
        if (table[hash] == null) {
            throw new NoSuchElementException("The key was not found in the "
                    + "Hash Map.");
        } else if (table[hash].getKey().equals(key)) {
            if (table[hash].isRemoved()) {
                throw new NoSuchElementException("The key was not found in "
                        + "the Hash Map.");
            } else {
                //V removedValue = table[hash].getValue();
                table[hash].setRemoved(true);
                size--;
                return table[hash].getValue();
            }
        } else {
            int numProbes = 0;
            while (table[(hash + numProbes) % table.length] != null) {
                int currentHash = (hash + numProbes) % table.length;
                if (table[currentHash].isRemoved()) {
                    throw new NoSuchElementException("The element does not " +
                            "exist in the Hash Map.");
                }
                if (numProbes >= table.length) {
                    throw new NoSuchElementException("The key wes not found in the " +
                        "Hash Map.");
                }
                if (table[currentHash].getKey().equals(key)) {
                    if (table[currentHash].isRemoved()) {
                        throw new NoSuchElementException("The element does " +
                                "not exist in the table.");
                    }
                    V removedValue = table[currentHash].getValue();
                    table[currentHash].setRemoved(true);
                    size--;
                    return removedValue;
                }
                numProbes++;
            }
            throw new NoSuchElementException("The key was not found in the "
                    + "Hash Map.");
        }
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     * @return the value associated with the given key
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key that was passed in is"
                    + " null.");
        }
        /*if (size == 0) {
            throw new NoSuchElementException("The table is empty.");
        }*/
        int hash = Math.abs(key.hashCode() % table.length);
        if (table[hash] == null) {
            throw new NoSuchElementException("The key was not found in the "
                    + "Hash Map.");
        }
        if (table[hash].getKey().equals(key)) {
            if (table[hash].isRemoved()) {
                throw new NoSuchElementException("The key was not found in "
                        + "the Hash Map.");
            } else {
                return table[hash].getValue();
            }
        } else {
            int numProbes = 0;
            while (table[(hash + numProbes) % table.length] != null) {
                if (numProbes >= table.length) {
                    throw new NoSuchElementException("The key was not found " +
                            "in the Hash Map.");
                }
                int currentHash = (hash + numProbes) % table.length;
                if (table[currentHash].getKey().equals(key)) {
                    if (table[currentHash].isRemoved()) {
                        throw new NoSuchElementException("The key was not "
                                + "found in the Hash Map.");
                    } else {
                        return table[currentHash].getValue();
                    }
                } else {
                    numProbes++;
                }
            }
            throw new NoSuchElementException("The key was not found in the "
                    + "Hash Map.");
        }
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @return whether or not the key is in the map
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key that was passed in is"
                    + " null.");
        }
        /*if (size == 0) {
            return false;
        }*/
        int hash = Math.abs(key.hashCode() % table.length);
        if (table[hash] == null) {
            return false;
        } else if (table[hash].getKey().equals(key)) {
            if (!table[hash].isRemoved()) {
                return true;
            } /*else {
                return false;
            }*/
            if (table[hash].isRemoved()) {
                return false;
            }
        } else {
            int numProbs = 0;
            while (table[(hash + numProbs) % table.length] != null) {
                int currentHash = (hash + numProbs) % table.length;
                if (numProbs >= table.length) {
                    return false;
                }
                if (table[currentHash].getKey().equals(key)) {
                    if (!table[currentHash].isRemoved()) {
                        return true;
                    } /*else {
                        return false;
                    }*/
                    if (table[currentHash].isRemoved()) {
                        return false;
                    }
                } else {
                    numProbs++;
                }
            }
        }
        return false;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     * Use {@code java.util.HashSet}.
     *
     * @return set of keys in this map
     */
    public Set<K> keySet() {
        HashSet<K> keySet = new HashSet<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                keySet.add(table[i].getKey());
            }
        }
        return keySet;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use {@code java.util.ArrayList} or {@code java.util.LinkedList}.
     *
     * You should iterate over the table in order of increasing index and add 
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> valueList = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                valueList.add(table[i].getValue());
            }
        }
        return valueList;
    }

    /**
     * Resize the backing table to {@code length}.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Remember that you cannot just simply copy the entries over to the new
     * array.
     *
     * Also, since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't need to check for duplicates.
     *
     * @param length new length of the backing table
     * @throws IllegalArgumentException if length is non-positive or less than
     * the number of items in the hash map.
     */
    @SuppressWarnings("unchecked")
    public void resizeBackingTable(int length) {
        if (length < 0 || length < size) {
            throw new IllegalArgumentException("The length is negative or "
                    + "less than size.");
        }
        MapEntry<K, V>[] tableCopy = table;
        table = (MapEntry<K, V>[]) new MapEntry[length];
        for (int i = 0; i < tableCopy.length; i++) {
            if (tableCopy[i] != null && !tableCopy[i].isRemoved()) {
                int hash = Math.abs(tableCopy[i].getKey().hashCode() % table
                        .length);
                if (table[hash] == null) {
                    table[hash] = tableCopy[i];
                } else {
                    int numProbes = 0;
                    while (table[(hash + numProbes) % table.length] != null) {
                        numProbes++;
                    }
                    table[(hash + numProbes) % table.length] = tableCopy[i];
                }
            }
        }
    }

    /**
     * Clears the table and resets it to the default length.
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        table = (MapEntry<K, V>[]) new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }
    
    /**
     * Returns the number of elements in the map.
     *
     * DO NOT USE OR MODIFY THIS METHOD!
     *
     * @return number of elements in the HashMap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * DO NOT USE THIS METHOD IN YOUR CODE. IT IS FOR TESTING ONLY.
     *
     * @return the backing array of the data structure, not a copy.
     */
    public MapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

}