import java.util.LinkedList;

/**
 * The class wrapping the linked-list objects in order to allow operate the linked lists as instances in an
 * array.
 */
public class LinkedListInstance {

    private LinkedList<String> my_list;

    /**
     * Creates new instance that contains linked list.
     */
    public LinkedListInstance(){
        my_list = new LinkedList<String>();
    }

    /**
     * Look for a specified value in the list.
     * @param objToSearch Value to search for
     * @return True iff objToSearch is found in the list
     */
    public boolean contains(String objToSearch){
        return my_list.contains(objToSearch);
    }

    /**
     * Add a specified element to the list.
     * @param newVal New value to add to the list
     */
    public void add(String newVal){
        my_list.add(newVal);
    }

    /**
     * @return The number of elements currently in the list
     */
    public int size(){ return my_list.size();}

    /**
     * @param index: index of the wanted value.
     * @return the value of the cell at the given index.
     */
    public String get(int index){
        return my_list.get(index);
    }

    /**
     * Removes the given object from the list.
     * @param toDelete: the wanted object to delete.
     */
    public void remove(String toDelete){my_list.remove(toDelete);}
}
