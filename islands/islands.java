// Solution candidate to Islands, COP 4516: Spring 2017
// By Tim Parker

import java.util.*;

public class islands {
  public static final boolean DEBUG = false;
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();

    // For each case...
    for (int i = 0; i < nCases; i++) {
      System.out.printf("%d ", stdin.nextInt());
      int[] a = new int[12];
     for (int j = 0; j < 12; j++) {
       a[j] = stdin.nextInt();
     }
     System.out.println(solve(a));
    }
  }

  public static int solve(int[] a) {
    int islandCount = 0;
    // For each possible width of an island...
    for (int width = 1; width < 11; width++) {
      for (int start = 1; start <= 10 - width + 1;  start++) {
        // Find the minimum value for the current island to check again the
        // boundary elements
        int minValue = findMin(a, start, start + width);
        // Check if the current island of length 'width' is an island
        if (a[start - 1] < minValue && a[start + width] < minValue) {
          islandCount++;
        }
      }
    }
    return islandCount;
  }

  public static int findMin(int[] a, int start, int end) {
    int[] tmp = Arrays.copyOfRange(a, start, end);
    int min = tmp[0];
    for (int i = 0; i < tmp.length; i++) {
      if (min > tmp[i])
        min = tmp[i];
    }
    return min;
  }
}
