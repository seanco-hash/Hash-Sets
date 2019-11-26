import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * The class tests the performance of different collections.
 */
public class SimpleSetPerformanceAnalyzer {
    private static final String DIFFERENT_HASH_WORD_FROM_SAME_HASH_LIST = "hi";
    private static final String DIFFERENT_HASH_WORD_FROM_VARY_HASH_LIST = "hi";
    private static final String SAME_HASH_WORD_TO_SAME_HASH_LIAT = "-13170890158";
    private static final String WORD_THAT_APPEARS_IN_WORDS_LIST = "23";
    private static final int WARM_UP = 70000; // warm up iterators before measuring time.
    private static final int ITERATES_NUMBER = 70000;
    private static final int LINKED_LIST_ITERATORS_CHECK = 7000;
    private static final String SAME_HASH_DATA = "data1.txt"; // List of words with same hash code.
    private static final String DIFFERENT_HASH_DATA = "data2.txt"; // Words with different hash code.
    private static final String RESULTS_MSG = "The data structure number and the running time are: ";
    private static final int CONVERT_TO_MILLISECONDS = 1000000;
    private static final String START_MSG = "The numbers for each data structure are:" +
            "0 = OpenHashSet; 1 = ClosedHashSet; 2 = TreeSet; 3 = HashSet; 4 = LinkedList.";
    private static final int OPEN_HASH_SET = 0;
    private static final int CLOSED_HASH_SET = 1;
    private static final int TREE_HASH_SET = 2;
    private static final int JAVA_HASH_SET = 3;
    private static final int LINKED_LIST = 4;
    private SimpleSet[] collectionsArray;
    private String[] sameHashWords, differentHashWords;
    private static long timeBefore, timeAfter;

    /**
     * Creates an analyzer object that can run the tests. Each object initialed with different sets types
     * array and with 2 lists of words.
     */
    public SimpleSetPerformanceAnalyzer(){
        this.collectionsArray = new SimpleSet[5];
        collectionArrayInitialize(); // Fill in the collection array.
        this.sameHashWords = Ex3Utils.file2array(SAME_HASH_DATA); // Convert text file to words array.
        this.differentHashWords = Ex3Utils.file2array(DIFFERENT_HASH_DATA);

    }

    /**
     * Fill in the collection array with 5 different SimpleSet collections - openHashSet, CloseHashSer,
     * TreeSet, java HashSet and LinkedList. in order to wrap the last 3, we use CollectionFacadeSet.
     */
    private void collectionArrayInitialize(){
        collectionsArray[OPEN_HASH_SET] = new OpenHashSet();
        collectionsArray[CLOSED_HASH_SET] = new ClosedHashSet();
        collectionsArray[TREE_HASH_SET] = new CollectionFacadeSet(new TreeSet<String>());
        collectionsArray[JAVA_HASH_SET] = new CollectionFacadeSet(new HashSet<String>());
        collectionsArray[LINKED_LIST] = new CollectionFacadeSet(new LinkedList<String>());
    }

    /**
     * Add words from an array one by one to each collection type. Capable of measuring the running time
     * for each type. measured time in m's
     * @param wordsList: Words list to add.
     * @param printTimeResults: If true, the method will print the run time for each collection.
     */
    private void addHashAnalyzer(String[] wordsList, boolean printTimeResults){
        for (int i=0; i < collectionsArray.length; i++){
            timeBefore = System.nanoTime();
            for (int j=0; j < wordsList.length; j++){
                collectionsArray[i].add(wordsList[j]);
            }
            if (printTimeResults) {
                timeAfter = (System.nanoTime() - timeBefore) / CONVERT_TO_MILLISECONDS;
                System.out.println(RESULTS_MSG + i + ":  " + timeAfter);
            }
        }
    }

    /**
     * The method checks the running time for each collection type while it makes "contain" calls. Measured
     * time in n's.
     * @param searchWord: Word to check if contains.
     * @param wordList: The words list to initial the collection types with.
     */
    private void containsHashAnalyzer(String searchWord, String[] wordList){
        addHashAnalyzer(wordList, false); // Initial the collections data base to the list.
        for (int i=0; i < collectionsArray.length - 1; i++){ // Linked-List checked separately.
            for (int j=0; j < WARM_UP + 1; j++) // warm up iterators
                collectionsArray[i].contains(searchWord);
            timeBefore = System.nanoTime();
            for (int k=0; k < ITERATES_NUMBER + 1; k++)
                collectionsArray[i].contains(searchWord);
            timeAfter = System.nanoTime() - timeBefore;
            System.out.println(timeAfter / ITERATES_NUMBER);
        }
        containsInLinkedListAnalyzer(searchWord, collectionsArray[LINKED_LIST]); // Linked List checked in
        // a separate method because it doesn't need warm up.
    }

    /**
     * Checks the run time for LinkedList while performing contain method.
     * @param searchWord: Word to search.
     * @param lst: The linked List.
     */
    private void containsInLinkedListAnalyzer(String searchWord, SimpleSet lst){
        timeBefore = System.nanoTime();
        for (int k=0; k < LINKED_LIST_ITERATORS_CHECK + 1; k++)
           lst.contains(searchWord);
        timeAfter = System.nanoTime() - timeBefore;
        System.out.println(timeAfter / LINKED_LIST_ITERATORS_CHECK);
    }

    /**
     * The method that runs the analyzer.
     * @param args:
     */
    public static void main(String args[]) {
        SimpleSetPerformanceAnalyzer my_analyzer = new SimpleSetPerformanceAnalyzer();
        System.out.println(SimpleSetPerformanceAnalyzer.START_MSG);
        my_analyzer.addHashAnalyzer(my_analyzer.sameHashWords, true);
        //my_analyzer.addHashAnalyzer(my_analyzer.differentHashWords, true);
        //my_analyzer.containsHashAnalyzer(DIFFERENT_HASH_WORD_FROM_SAME_HASH_LIST, my_analyzer
          //    .sameHashWords);
    }

}
