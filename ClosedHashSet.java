import static java.util.Objects.hash;

/**
 * The class represents a closed hash set, extends SimpleHashSet.
 */
public class ClosedHashSet extends SimpleHashSet {

    private static final String DELETED_FIELD = new String(" "); // In order to recognize deleted
    // cells but save the possibility to add a "==" String to set.

    private String[] hashSet;

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public ClosedHashSet(){
        super(DEFAULT_LOWER_LOAD_FACTOR, DEFAULT_UPPER_LOAD_FACTOR); // Initial factors and num of elements.
        hashSet = new String[INITIAL_CAPACITY];
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor: The upper load factor of the hash table.
     * @param lowerLoadFactor: The lower load factor of the hash table
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor){
        super(lowerLoadFactor, upperLoadFactor);
        hashSet = new String[INITIAL_CAPACITY];
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
     * ignored. The new table has the default values of initial capacity (16), upper load factor (0.75),
     * and lower load factor (0.25).
     * @param data:  Values to add to the set.
     */
    public ClosedHashSet(java.lang.String[] data){
        super(DEFAULT_LOWER_LOAD_FACTOR, DEFAULT_UPPER_LOAD_FACTOR);
        this.hashSet = new String[calculateInitialCapacity(data.length)]; // Creates new hash
        // in a calculated size according to the amount of data.
        for (int i=0; i < data.length; i++)
            add(data[i]);
        int desirableCapacity = calculateFinalCapacity(hashSet.length); // In case of duplications in the
        // data list, the set will initial itself with larger capacity than needed. In this scenario we
        // will check the appropriate final needed capacity and rehash.
        if (desirableCapacity < hashSet.length)
            reHash(desirableCapacity);
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    public boolean add(java.lang.String newValue){
        if (contains(newValue)) // Prevents duplications.
            return false;
        if ((float)(numOfElements + 1) / (float)(hashSet.length) > upperLoadFactor) // Prevents passing the
            // upper load factor by rehashing before adding.
            hashSet = reHash(hashSet.length * 2);
        int i = 0; // Index for the hash value calculations.
        hashIndex = hashCalculator(newValue, hashSet, i);
        while (hashSet[hashIndex] != null){
            if (hashSet[hashIndex] == DELETED_FIELD) // We use "==" to check if it is really the deletion
                // mark or just a inserted string that is identical to the deletion mark.
                break; // if it is deletion mark, we will add the new value at this position.
            i++;
            hashIndex = hashCalculator(newValue, hashSet, i);
        }
        hashSet[hashIndex] = newValue;
        numOfElements++;
        return true;
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    public boolean contains(java.lang.String searchVal){
        int i = 0;
        hashIndex = hashCalculator(searchVal, hashSet, i);
        while (hashSet[hashIndex] != null){
            if (hashSet[hashIndex].equals(searchVal))
                return true;
            i++;
            hashIndex = hashCalculator(searchVal, hashSet, i);
        }
        return false;

    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    public boolean delete(java.lang.String toDelete){
        if (!contains(toDelete))
            return false;
        int i = 0;
        hashIndex = hashCalculator(toDelete, hashSet, i);
        while (!hashSet[hashIndex].equals(toDelete)){ // keep
            i++;
            hashIndex = hashCalculator(toDelete, hashSet, i);
        }
        hashSet[hashIndex] = DELETED_FIELD;
        numOfElements--;
        if (((float)(numOfElements) / (float)(hashSet.length) < lowerLoadFactor) && hashSet.length > 1)
            hashSet = reHash(hashSet.length / 2);
        return true;
    }

    /**
     * Calculates the hash value of a given value.
     * @param val: The value to calculate.
     * @param hashSet: The hashSet
     * @param i: The current try to find a empty cell for this value.
     * @return the calculated hash code for the given value.
     */
    private int hashCalculator(String val, String[] hashSet, int i){
        return  (int)((Math.abs((long)hash(val)) + (i + (long)i*i)/2)%hashSet.length);
    }

    /**
     * Performing re-hash in case of too full or too empty hash table.
     * @param newLength: The wanted size of the new table.
     * @return New hash set with the wanted capacity.
     */
    private String[] reHash(int newLength){
        String[] newHashSet = new String[newLength];
        int i = 0;
        for (int j=0; j < hashSet.length; j++) {
            if ((hashSet[j] == null) || hashSet[j] == DELETED_FIELD) // No value to re-hash.
                continue;
            hashIndex = hashCalculator(hashSet[j], newHashSet, i);
            while (newHashSet[hashIndex] != null){ // Searching empty cell to insert the values.
                i++;
                hashIndex = hashCalculator(hashSet[j], newHashSet, i);
            }
            newHashSet[hashIndex] = hashSet[j];
            i = 0;
        }
        return newHashSet;
    }

    /**
     * @return The number of elements currently in the set
     */
    public int size(){return super.size();}

    /**
     * @return The capacity of the set (number of cells)
     */
    public int capacity(){return hashSet.length;}


}
