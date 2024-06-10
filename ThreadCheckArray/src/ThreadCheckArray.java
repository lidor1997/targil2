import java.util.ArrayList;

/**
 * The ThreadCheckArray class implements the Runnable interface and
 * provides logic to find a subset of an array that sums up to a given number.
 */
public class ThreadCheckArray implements Runnable {
    // Flag to indicate if a solution has been found
    private boolean flag;

    // ArrayList to store the status of each element in the array (whether it's included in the subset or not)
    private ArrayList<Boolean> winArray;

    // Reference to the shared data object
    SharedData sd;

    // ArrayList to store the input array
    ArrayList<Integer> array;

    // The target sum to be achieved
    int b;

    // Constructor to initialize the ThreadCheckArray object
    public ThreadCheckArray(SharedData sd) {
        // Initialize shared data reference
        this.sd = sd;

        // Synchronize to ensure thread safety while accessing shared data
        synchronized (sd) {
            // Get the array and target sum from the shared data object
            array = sd.getArray();
            b = sd.getB();
        }

        // Initialize the winArray with the same size as the input array and set all values to false
        winArray = new ArrayList<>(array.size());
        for (int i = 0; i < array.size(); i++) {
            winArray.add(false);
        }
    }

    // Recursive method to find the subset sum
    void rec(int n, int b) {
        // Synchronize to ensure thread safety while accessing shared data
        synchronized (sd) {
            // If the flag is already set (indicating a solution has been found by another thread), return
            if (sd.getFlag())
                return;
        }

        // Base case: if only one element is remaining to be considered
        if (n == 1) {
            // Check if the remaining element is equal to the target sum or if the target sum is 0
            if (b == 0 || b == array.get(n - 1)) {
                // If so, set the flag indicating a solution has been found
                flag = true;
                // Synchronize to ensure thread safety while updating shared data
                synchronized (sd) {
                    sd.setFlag(true);
                }
            }
            // If the remaining element is part of the solution, mark it as true in winArray
            if (b == array.get(n - 1)) {
                winArray.set(n - 1, true);
            }
            return;
        }

        // Recursive call considering the current element in the subset
        rec(n - 1, b - array.get(n - 1));

        // If a solution has been found, mark the current element as part of the subset in winArray
        if (flag) {
            winArray.set(n - 1, true);
        }

        // Synchronize to ensure thread safety while accessing shared data
        synchronized (sd) {
            // If the flag is already set (indicating a solution has been found by another thread), return
            if (sd.getFlag())
                return;
        }

        // Recursive call without considering the current element in the subset
        rec(n - 1, b);
    }

    // Method to be executed when the thread starts
    @Override
    public void run() {
        // Check if there's more than one element in the array
        if (array.size() != 1) {
            // If so, invoke the recursive method to find the subset sum
            if (Thread.currentThread().getName().equals("thread1")) {
                rec(array.size() - 1, b - array.get(array.size() - 1));
            } else {
                rec(array.size() - 1, b);
            }
        }

        // If there's only one element in the array
        if (array.size() == 1) {
            // Check if the single element equals the target sum and no solution has been found yet
            if (b == array.get(0) && !flag) {
                // If so, mark the element as part of the subset in winArray and set the flag
                winArray.set(0, true);
                flag = true;
                // Synchronize to ensure thread safety while updating shared data
                synchronized (sd) {
                    sd.setFlag(true);
                }
            }
        }

        // If a solution has been found
        if (flag) {
            // If this thread was responsible for considering the last element, mark it in winArray
            if (Thread.currentThread().getName().equals("thread1")) {
                winArray.set(array.size() - 1, true);
            }
            // Synchronize to ensure thread safety while updating shared data
            synchronized (sd) {
                sd.setWinArray(winArray);
            }
        }
    }
}
