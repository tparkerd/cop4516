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
    for (int i = 0; i < nCases; i++) {
      // Get the case details
      int nDiamonds = stdin.nextInt();      // Number of diamonds
      int k = stdin.nextInt();              // Max size difference
      int[] diamonds = new int[nDiamonds];
      // Add all the diamonds to a list
      for (int j = 0; j < nDiamonds; j++)
        diamonds[j] = stdin.nextInt();

      if (DEBUG) System.out.println(Arrays.toString(diamonds));

      // Sort the diamonds
      Arrays.sort(diamonds);
      // diamonds = reverse(diamonds);
      if (DEBUG) System.out.println(Arrays.toString(diamonds));
      // if (DEBUG) System.out.println("LOW\tHIGH\t[LOW]\t[HIGH]\tDIFF\n------------------------------------");

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


        // if (DEBUG) System.out.println(low + "\t" + high + "\t" + diamonds[low] + "\t" + diamonds[high] + "\t" + (upper - lower));

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

      // Now we have a list of all the diamond ranges
      // Let's find the maxes from the righthand side

      int[] rightHandMax = new int[nDiamonds];
      int max = 0;
      int maxIndex = nDiamonds - 1;
      for (int j = nDiamonds - 1; j >= 0; j--) {
        if (countList[j] > max) {
          max = countList[j];
          maxIndex = j;
        }
        rightHandMax[j] = max;
      }

      // if (DEBUG) System.out.println("Max index in righthand: " + maxIndex);

      if (DEBUG) System.out.println("Count List:");
      if (DEBUG) System.out.println(Arrays.toString(countList));
      // if (DEBUG) System.out.println("Max List:");
      // if (DEBUG) System.out.println(Arrays.toString(rightHandMax));

      int secondMax = 0;
      for (int j = 0; j < nDiamonds; j++) {
        // Base case: if J is currently traversing the range found as the
        // longest range in the set, skip it
        if (j >= maxIndex && j < countList[maxIndex] + maxIndex - 1)
          continue;

        secondMax = Math.max(secondMax, countList[j]);
      }

      if (DEBUG) System.out.println("First max: " + countList[maxIndex] + "\nSecond max: " + secondMax);

      System.out.println(countList[maxIndex] + secondMax);

    }
  }
}
