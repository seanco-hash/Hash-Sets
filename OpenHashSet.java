import static java.util.Objects.hash;

/**
 * The class represents an open hash set.
 */
public class OpenHashSet extends SimpleHashSet {

    private LinkedListInstance[] hashSet;

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16), upper load
     * factor (0.75) and lower load factor (0.25).
     */
    public OpenHashSet(){
        super(DEFAULT_LOWER_LOAD_FACTOR, DEFAULT_UPPER_LOAD_FACTOR);
        this.hashSet = new LinkedListInstance[INITIAL_CAPACITY];
        linkListsInitializer(hashSet);
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor: Extends the table when reach this factor
     * @param lowerLoadFactor: Reduce the table when reach below this factor
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor){
        super(lowerLoadFactor, upperLoadFactor);
        this.hashSet = new LinkedListInstance[INITIAL_CAPACITY];
        linkListsInitializer(hashSet); // Initial each set cell with linked list instance.
    }
    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     * @param data:
     */
    public OpenHashSet(java.lang.String[] data){
        super(DEFAULT_LOWER_LOAD_FACTOR, DEFAULT_UPPER_LOAD_FACTOR);
        this.hashSet = new LinkedListInstance[calculateInitialCapacity(data.length)]; // Creates new hash
        // in a calculated size according to the amount of data.
        linkListsInitializer(hashSet);
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
        if (contains(newValue)) // if already exist, returns false.
            return false;
        if ((float)(numOfElements + 1) / (float)(hashSet.length) > upperLoadFactor) // Prevents passing the
            // upper load factor by rehashing before adding.
            hashSet = reHash(hashSet.length * 2);
        hashIndex = hashCalculator(newValue, hashSet);
        hashSet[hashIndex].add(newValue);
        numOfElements ++;
        return true;
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    public boolean contains(java.lang.String searchVal){
        hashIndex = hashCalculator(searchVal, hashSet);
        return hashSet[hashIndex].contains(searchVal);
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    public boolean delete(java.lang.String toDelete){
        if (!contains(toDelete))
            return false;
        hashIndex = hashCalculator(toDelete, hashSet);
        hashSet[hashIndex].remove(toDelete);
        numOfElements--;
        if (((float)(numOfElements) / (float)(hashSet.length) < lowerLoadFactor) && hashSet.length > 1) // If
            // hashSet length > 1 it is possible to reduce.
            hashSet = reHash(hashSet.length / 2);
        return true;
    }

    /**
     * @return The number of elements currently in the set
     */
    public int size(){return super.size();}

    /**
     * @return The capacity of the set (number of cells)
     */
    public int capacity(){return hashSet.length;}

    /**
     * Performing re-hash in case of too full or too empty hash table.
     * @param newLength: The wanted size of the new table.
     * @return New hash set with the wanted capacity.
     */
    private LinkedListInstance[] reHash(int newLength){
        int newHashIndex;
        String curVal;
        LinkedListInstance[] newHashSet = new LinkedListInstance[newLength];
        linkListsInitializer(newHashSet);
        for (int i=0; i < hashSet.length; i++){
            for (int j=0; j < hashSet[i].size(); j++){
                curVal = hashSet[i].get(j);
                newHashIndex = hashCalculator(curVal, newHashSet);
                newHashSet[newHashIndex].add(curVal);
            }
        }
        return newHashSet;
    }

    /**
     * Calculates the hash value to a given String.
     * @param val: The given String.
     * @param hashSet: The current hash set.
     * @return the hash value for the String.
     */
    private int hashCalculator(String val, LinkedListInstance[] hashSet){
        return (int)(Math.abs(hash(val)) % hashSet.length);
    }

    /**
     * Receives an empty LinkedListInstance and initialize it with linked list instance in each cell.
     * @param curHashSet: The hah set to initial.
     */
    private void linkListsInitializer(LinkedListInstance[] curHashSet){
        for (int i=0; i < curHashSet.length; i++)
            curHashSet[i] = new LinkedListInstance();
    }
}
