import java.util.ArrayList;
import java.util.Scanner;

/**
 * The TestThreadCheckArray class demonstrates the use of threads to check
 * if a subset of an array of integers can sum up to a specified number.
 */
public class TestThreadCheckArray {
    public static void main(String[] args) {
        // Using Scanner to get user input
        try (Scanner input = new Scanner(System.in)) {
            Thread thread1, thread2; // Declaration of two threads

            // Get the size of the array from the user
            System.out.println("Enter array size");
            int num = input.nextInt();

            // Initialize the array with the specified size
            ArrayList<Integer> array = new ArrayList<>(num);

            // Get the elements of the array from the user
            System.out.println("Enter numbers for array");
            for (int index = 0; index < num; index++) {
                array.add(input.nextInt());
            }

            // Get the target number (b) from the user
            System.out.println("Enter number");
            int b = input.nextInt();

            // Create a SharedData object to hold the array and the target number
            SharedData sd = new SharedData(array, b);

            // Create and start two threads to check the array
            thread1 = new Thread(new ThreadCheckArray(sd), "thread1");
            thread2 = new Thread(new ThreadCheckArray(sd), "thread2");
            thread1.start();
            thread2.start();

            // Wait for both threads to finish
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Check the flag in SharedData to see if a solution was found
            if (!sd.getFlag()) {
                System.out.println("Sorry");
                return;
            }

            // Print the solution if found
            System.out.println("Solution for b: " + sd.getB() + ", n = " + sd.getArray().size());
            System.out.print("I:    ");
            for (int index = 0; index < sd.getArray().size(); index++) {
                System.out.print(index + "    ");
            }
            System.out.println();
            System.out.print("A:    ");
            for (int index : sd.getArray()) {
                System.out.print(index);
                int counter = 5;
                while (true) {
                    index = index / 10;
                    counter--;
                    if (index == 0) {
                        break;
                    }
                }
                for (int i = 0; i < counter; i++) {
                    System.out.print(" ");
                }
            }

            System.out.println();
            System.out.print("C:    ");
            for (boolean index : sd.getWinArray()) {
                if (index) {
                    System.out.print("1    ");
                } else {
                    System.out.print("0    ");
                }
            }
        }
    }
}
