/**
 * An abstract class implements SimpleSet. Represents the hash sets among the simple sets.
 */
abstract class SimpleHashSet implements SimpleSet {

    public static final int INITIAL_CAPACITY = 16;
    public static final float DEFAULT_UPPER_LOAD_FACTOR = 0.75f;
    public static final float DEFAULT_LOWER_LOAD_FACTOR = 0.25f;
    protected float lowerLoadFactor, upperLoadFactor;
    protected int numOfElements;
    protected int hashIndex;

    /**
     * Helps to initial some basic attributes that each hash set has.
     * @param lowerLoadFactor: When the number of elements / capacity of the set is beneath this number,
     *  the set will re-hash and resize itself to smaller set.
     * @param upperLoadFactor:When the number of elements / capacity of the set is beyond this number,
     *  the set will re-hash and resize itself to larger set.
     */
    public SimpleHashSet(float lowerLoadFactor, float upperLoadFactor){
        this.lowerLoadFactor = lowerLoadFactor;
        this.upperLoadFactor = upperLoadFactor;
        this.numOfElements = 0;
    }

    /**
     * In case of creating new set from a given words list, this method is called in order to calculate the
     * wanted set size, for it will saves the running time of re-hashing over an over when adding the words.
     * @param expectedDataLength: The number of elements to add to the set.
     * @return expected capacity for the set.
     */
    protected int calculateInitialCapacity(int expectedDataLength){
        int curExpectedCapacity = INITIAL_CAPACITY;
        while ((float)(expectedDataLength) / (float)(curExpectedCapacity) > upperLoadFactor) // Calculates
            // the
        // hashSet size
            // according to the data length in order to prevent number of re-hashing when creating the hash.
            curExpectedCapacity = curExpectedCapacity * 2;
        return curExpectedCapacity;
    }

    /**
     * Calculates the correct set size after objects were added to the set. Checks if reduction is needed.
     * @param curCapacity: The hash set currents capacity.
     * @return The desirable set capacity according to the load factor.
     */
    protected int calculateFinalCapacity(int curCapacity){
        while ((float)(numOfElements) / (float)(curCapacity) < lowerLoadFactor)
            curCapacity = curCapacity / 2;
        return curCapacity;
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    public abstract boolean add(java.lang.String newValue);

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    public abstract boolean contains(java.lang.String searchVal);

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    public abstract boolean delete(java.lang.String toDelete);

    /**
     * @return The number of elements currently in the set
     */
    public int size(){return numOfElements;}

    /**
     * @return The capacity of the set (number of cells)
     */
    public abstract int capacity();

}
