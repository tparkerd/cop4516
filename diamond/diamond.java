import java.util.*;
import java.math.*;

public class diamond {
  public static final boolean DEBUG = true;

  public static void main(String[] args) {
    // Read in the data
    Scanner stdin = new Scanner(System.in);
    // Number of cases
    int nCases = stdin.nextInt();

    // For each case...
    for (int i = 0; i < nCases && stdin.hasNext(); i++) {
      // Get the case details
      int nDiamonds = stdin.nextInt();      // Number of diamonds
      int k = stdin.nextInt();              // Max size difference
      int[] diamonds = new int[nDiamonds];
      // Add all the diamonds to a list
      for (int j = 0; j < nDiamonds; j++)
        diamonds[j] = stdin.nextInt();

      // Sort the diamonds, ascending order
      Arrays.sort(diamonds);
      if (DEBUG) System.out.println("Sorted:\t\t" + Arrays.toString(diamonds));

      int[] countList = new int[nDiamonds];
      for (int low = 0, high = low + 1; low < nDiamonds; ) {
        // Base case: if we've reached the end of the array, the current low will
        // simply store the number of diamonds that are greater in size (i.e., to the right)
        if (high >= nDiamonds) {
          countList[low] = Math.abs(nDiamonds - low);
          low++;
          high = low + 1;
          continue;
        }

        // Diamonds are within the bounds of the array
        // When the difference is greater than the size difference restriction
        // or we have reached the end of all diamonds
        // Save the difference and increment low to check the subsequent combinations
        if ((diamonds[high] - diamonds[low]) > k) {
          countList[low] = Math.abs(high - low);
          low++;
          high = low + 1;

        // Otherwise, we're still in the bounds of the size restriction,
        // so keep counting! Woo!
        } else {
          high++;
        }
      }

      // Now let's find the index of the rightmost, longest stretch
      int max = 0;
      int maxIndex = nDiamonds - 1;
      for (int j = nDiamonds - 1; j >= 0; j--) {
        if (countList[j] > max) {
          max = countList[j];
          maxIndex = j;
        }
      }

      if (DEBUG) System.out.print("Count List:\t");
      if (DEBUG) System.out.println(Arrays.toString(countList));

      int tmpSecondMax = 0;
      for (int j = 0; j < nDiamonds; j++) {
        // Base case: if J is currently traversing the range found as the
        // longest range in the set, skip it
        int forbiddenStart = maxIndex;                                 // Where the forbidden range starts
        int forbiddenEnd = countList[maxIndex] + forbiddenStart - 1;   // Where the forbidden range ends
        int start = j;                                                 // Where the current range starts
        int end = countList[j] + start - 1;                             // Where the current range ends
        if (DEBUG) System.out.println("Check bounds // Start: " + start + ", End: " + end);


        // Base case: if the range starts within the forbidden range, skip
        // to the end of the forbidden range
        if (start >= forbiddenStart && start <= forbiddenEnd) continue;

        // If we start the current range before the known max and ends
        // after it starts
        if (start < forbiddenStart && end >= forbiddenStart) {
          // See by how many elements it overlaps
          int overlap = end - forbiddenStart + 1;
          tmpSecondMax = Math.max(tmpSecondMax, countList[j] - overlap);
        } else {
          tmpSecondMax = Math.max(tmpSecondMax, countList[j]);
        }
      }

      if (DEBUG) System.out.println("First max: " + countList[maxIndex] + "\nSecond max: " + tmpSecondMax);

      System.out.println(countList[maxIndex] + tmpSecondMax);

    }
  }
}
