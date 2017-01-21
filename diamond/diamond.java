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
    }
  }
}
