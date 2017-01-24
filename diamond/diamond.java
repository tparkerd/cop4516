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

      int[] list = new int[nDiamonds];
      for (int low = 0, high = low + 1; low < nDiamonds; ) {
        // Base case: if we've reached the end of the array, the current low will
        // simply store the number of diamonds that are greater in size (i.e., to the right)
        if (high >= nDiamonds) {
          list[low] = Math.abs(nDiamonds - low);
          low++;
          high = low + 1;
          continue;
        }

        // Diamonds are within the bounds of the array
        // When the difference is greater than the size difference restriction
        // or we have reached the end of all diamonds
        // Save the difference and increment low to check the subsequent combinations
        if ((diamonds[high] - diamonds[low]) > k) {
          list[low] = Math.abs(high - low);
          low++;
          high = low + 1;

        // Otherwise, we're still in the bounds of the size restriction,
        // so keep counting! Woo!
        } else {
          high++;
        }
      }

      // // Now let's find the index of the rightmost, longest stretch
      // int max = 0;
      // int maxIndex = nDiamonds - 1;
      // for (int j = nDiamonds - 1; j >= 0; j--) {
      //   if (list[j] > max) {
      //     max = list[j];
      //     maxIndex = j;
      //   }
      // }

      if (DEBUG) System.out.print("Count List:\t");
      if (DEBUG) System.out.println(Arrays.toString(list));


      int firstMaxIndex = 0;
      int secondMaxIndex = nDiamonds - 1;

      // Sweep through until the ranges overlap
      // So long as the end of the current range does not overlap with the start
      // of the rightmost index, continue on...
      for (int j = 0; (j < nDiamonds) && (list[j] + j - 1) < secondMaxIndex; j++) {
        if (DEBUG) System.out.println("1st: " + list[j] + " to " + list[firstMaxIndex]  + "\n2nd: " + list[nDiamonds - j - 1] + " to " + list[secondMaxIndex] + "");

        // Save the last known working indices
        int firstTmp = firstMaxIndex;
        int secondTmp = secondMaxIndex;

        // See if the current lefthand range is larger than the previously known one
        if (list[j] > list[firstMaxIndex]) {
          firstMaxIndex = j;
          if (DEBUG) System.out.println("Change 1st max range to " + list[j]);
        }

        // Check to see if the next index largest from the righthand is larger than
        // the one before it
        if (list[nDiamonds - 1 - j] > list[secondMaxIndex]) {
          secondMaxIndex = j;
          if (DEBUG) System.out.println("Change 2nd max range to " + list[j]);
        }

        // Make sure that the new ranges do not overlap
        // If they do, reset the maxes to the old ones, and continue on
        if ((list[j] + j - 1) >= secondMaxIndex) {
          if (DEBUG) System.out.println("Whooops");
          firstMaxIndex = firstTmp;
          secondMaxIndex = secondTmp;
        }
      }



      // int tmpSecondMax = 0;
      // for (int j = 0; j < nDiamonds; j++) {
      //   // Base case: if J is currently traversing the range found as the
      //   // longest range in the set, skip it
      //   int forbiddenStart = maxIndex;                                 // Where the forbidden range starts
      //   int forbiddenEnd = list[maxIndex] + forbiddenStart - 1;   // Where the forbidden range ends
      //   int start = j;                                                 // Where the current range starts
      //   int end = list[j] + start - 1;                             // Where the current range ends
      //   if (DEBUG) System.out.println("Check bounds // Start: " + start + ", End: " + end);
      //
      //
      //   // Base case: if the range starts within the forbidden range, skip
      //   // to the end of the forbidden range
      //   if (start >= forbiddenStart && start <= forbiddenEnd) continue;
      //
      //   // If we start the current range before the known max and ends
      //   // after it starts
      //   if (start < forbiddenStart && end >= forbiddenStart) {
      //     // See by how many elements it overlaps
      //     int overlap = end - forbiddenStart + 1;
      //     tmpSecondMax = Math.max(tmpSecondMax, list[j] - overlap);
      //   } else {
      //     tmpSecondMax = Math.max(tmpSecondMax, list[j]);
      //   }
      // }

      if (DEBUG) System.out.println("First max: " + list[firstMaxIndex] + "\nSecond max: " + list[secondMaxIndex]);

      System.out.println(list[firstMaxIndex] + list[secondMaxIndex]);

    }
  }
}
