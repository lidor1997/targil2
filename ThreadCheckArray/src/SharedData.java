import java.util.ArrayList;

/**
 * The SharedData class represents shared data between threads.
 */
public class SharedData {
    // ArrayList to store the input array of integers
    private ArrayList<Integer> array;

    // ArrayList to store the status of each element in the array (whether it's included in the subset or not)
    private ArrayList<Boolean> winArray;

    // Flag to indicate if a solution has been found
    private boolean flag;

    // The target sum to be achieved
    private final int b;

    /**
     * Constructor to initialize SharedData with the input array and target sum.
     * 
     * @param array The input array of integers
     * @param b     The target sum
     */
    public SharedData(ArrayList<Integer> array, int b) {
        this.array = array;
        this.b = b;
    }

    /**
     * Get the ArrayList containing the status of each element in the array.
     * 
     * @return The winArray ArrayList
     */
    public ArrayList<Boolean> getWinArray() {
        return winArray;
    }

    /**
     * Set the ArrayList containing the status of each element in the array.
     * 
     * @param winArray The winArray ArrayList to set
     */
    public void setWinArray(ArrayList<Boolean> winArray) {
        this.winArray = winArray;
    }

    /**
     * Get the input array of integers.
     * 
     * @return The input array
     */
    public ArrayList<Integer> getArray() {
        return array;
    }

    /**
     * Get the target sum.
     * 
     * @return The target sum
     */
    public int getB() {
        return b;
    }

    /**
     * Get the flag indicating if a solution has been found.
     * 
     * @return The flag value
     */
    public boolean getFlag() {
        return flag;
    }

    /**
     * Set the flag indicating if a solution has been found.
     * 
     * @param flag The flag value to set
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
