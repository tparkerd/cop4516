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
     if (DEBUG) System.out.println(Arrays.toString(a));
     System.out.println(solve(a));
    }
  }

  public static int solve(int[] a) {
    int islandCount = 0;
    for (int width = 1; width < 11; width++) {
      for (int start = 1; start <= 10 - width + 1;  start++) {
        if (DEBUG) System.out.println("--------Process Case--------");
        int minValue = findMin(a, start, start + width);
        if (DEBUG) System.out.println("Min Value: " + minValue);
        if (DEBUG) System.out.printf("a[%d] (%d) > %d && a[%d] (%d) > %d\n", start - 1, a[start -1], minValue, start + width, a[start + width], minValue);
        if (a[start - 1] < minValue && a[start + width] < minValue) {
          if (DEBUG) System.out.println("\n!~ Inc\n");
          islandCount++;
        }
      }
    }
    return islandCount;
  }

  public static int findMin(int[] a, int start, int end) {
    int[] tmp = Arrays.copyOfRange(a, start, end);
    int min = tmp[0];
    if (DEBUG) System.out.println(Arrays.toString(tmp));
    for (int i = 0; i < tmp.length; i++) {
      if (min > tmp[i])
        min = tmp[i];
    }
    return min;
  }
}
