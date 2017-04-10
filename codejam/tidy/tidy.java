import java.util.*;
import java.math.*;

public class tidy {
  public static final boolean DEBUG = false;
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = Integer.parseInt(stdin.nextLine());

    // For each case...
    for (int i = 1; i <= nCases; i++) {
      System.out.printf("Case #%d: ", i);

      // Get the string of numbers and make it an array
      char[] charArray = stdin.nextLine().toCharArray();
      int[] a = new int[charArray.length];
      for (int j = 0; j < a.length; j++) {
        a[j] = charArray[j] - '0';
      }
      if (DEBUG) System.out.printf("%s\t", Arrays.toString(a));

      for (int j = a.length - 1; j > 0; j--) {
        // If the latter one is less than the earlier one
        // Set latter to 9, decrement the earlier
        if (a[j] < a[j - 1]) {
          Arrays.fill(a, j, a.length, 9);
          a[j - 1]--;
        }
      }
      int j = 0;
      while (a[j] == 0) j++;
      for (; j < a.length; j++)
        System.out.printf("%d", a[j]);
      System.out.println();
    }
  }
}
