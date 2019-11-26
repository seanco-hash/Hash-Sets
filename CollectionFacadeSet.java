/**
 * The class wrapping a collection objects and suit them to SimpleSet form.
 */
public class CollectionFacadeSet extends java.lang.Object implements SimpleSet {

    protected java.util.Collection<java.lang.String> collection;

    /**
     * Creates a wrapped collection instance.
     * @param collection: The collection to wrap.
     */
    public CollectionFacadeSet(java.util.Collection<java.lang.String> collection){
        this.collection = collection;
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    public boolean add(java.lang.String newValue){
        if (collection.contains(newValue)) // To ensure that the collection will behave like set, we
            // firstly check if the new value is already exist.
            return false;
        collection.add(newValue);
        return true;
    }
    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    public boolean contains(java.lang.String searchVal){
        return collection.contains(searchVal);
    }
    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    public boolean delete(java.lang.String toDelete){
        return collection.remove(toDelete);
    }
    /**
     * @return The number of elements currently in the set
     */
    public int size(){
        return collection.size();
    }
}
