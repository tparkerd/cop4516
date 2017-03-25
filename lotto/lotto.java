import java.util.*;

public class lotto {
  public static final boolean DEBUG = true;
  public static int counter;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    int n = 10;
    int m = 20;

    // Row is the starting number, column is the upper limit value
    int[][] s = new int[n + 1][m + 1];
    Arrays.fill(s[0], 0);
    for (int i = 0; i < n; i++) s[i][0] = 0;
    for (int i = 0; i < s.length; i++) {
      if (DEBUG) System.out.println(Arrays.toString(s[i]));
    }

    System.out.println();

    // odometer(new int[n], m, 0);

    for (int i = 1; i <= 8; i++) { // number of digits
      for ( int j = i; j < m; j++) { // range of digits
        s[i][j] = odometer(new int[i], j, 0);
      }
    }

    for (int i = 0; i < s.length; i++) {
      if (DEBUG) System.out.println(Arrays.toString(s[i]));
    }
    //
    // // Get the first case
    // int n = stdin.nextInt();
    // int m = stdin.nextInt();
    // counter = 0;
    //
    // // For each test case...
    // int cCase = 1;
    // while (n != 0 && m != 0) {
    //   boolean[] used = new boolean[m];
    //   Arrays.fill(used, false);
    //   // if (DEBUG) System.out.println(Arrays.toString(used));
    //   // odometer(new int[n], m, 0);
    //   System.out.printf("Case %d: n = %d, m = %d, # lists = %d\n", cCase++, n, m, counter);
    //
    //   // Get next case.
    //   n = stdin.nextInt();
    //   m = stdin.nextInt();
    //   counter = 0;
    // }
  }

  public static int odometer(int[] digits, int numDigits, int k) {
    // Base case: not viable start perm
    if (digits.length > numDigits) return 0;

    // Make sure the sub-permutation is valid first
    for (int i = 0; i < k - 1; i++) {
      if ((digits[i] * 2) > digits[i + 1]) {
        return 0;
      }
    }
    if (k == digits.length) {
      counter++;
      if (DEBUG) System.out.println(Arrays.toString(digits));
      return 1;
    }
    int sum = 0;
    for (int i = 1; i <= numDigits; i++) {
      digits[k] = i;
      sum += odometer(digits, numDigits, k + 1);
    }
    return sum;
  }

  public static int countPerms(int[] digits, int numDigits, int k) {
    // // Base case: single digit
    // if (k == 0 || k == 1) return 1;

    // Make sure the sub-permutation is valid first
    for (int i = 0; i < k - 1; i++) {
      if ((digits[i] * 2) > digits[i + 1]) {
        // System.out.println("ERROR, NOT CORRECT PERM");
        return -1;
      }
    }

    if (k == digits.length) {
      if (DEBUG) System.out.println(Arrays.toString(digits));
      return 0;
    }
    int sum = 0;
    for (int i = 1; i <= numDigits; i++) {
      digits[k] = i;
      sum = countPerms(digits, numDigits, k + 1) + 1;
    }

    return sum;
  }
}
