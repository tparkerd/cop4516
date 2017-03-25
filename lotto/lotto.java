import java.util.*;

public class lotto {
  public static final boolean DEBUG = false;
  public static int counter;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    // // Row is the starting number, column is the upper limit value
    // int[][] solutions = new int[11][20];
    // solutions[0][0] = 1;
    // for (int i = 0; i < solutions.length; i++) {
    //   if (DEBUG) System.out.println(Arrays.toString(solutions[i]));
    // }



    // Get the first case
    int n = stdin.nextInt();
    int m = stdin.nextInt();
    counter = 0;

    // For each test case...
    int cCase = 1;
    while (n != 0 && m != 0) {
      boolean[] used = new boolean[m];
      Arrays.fill(used, false);
      // if (DEBUG) System.out.println(Arrays.toString(used));
      odometer(new int[n], m, 0);
      System.out.printf("Case %d: n = %d, m = %d, # lists = %d\n", cCase++, n, m, counter);

      // Get next case.
      n = stdin.nextInt();
      m = stdin.nextInt();
      counter = 0;
    }
  }

  public static void odometer(int[] digits, int numDigits, int k) {
    // Make sure the sub-permutation is valid first
    if (k >= 2 && (digits[k - 2] * 2) >= digits[k - 1])
      return;

    if (k == digits.length) {
      counter++;
      if (DEBUG) System.out.println(Arrays.toString(digits));
      return;
    }
    for (int i = 1; i <= numDigits; i++) {
      digits[k] = i;
      odometer(digits, numDigits, k + 1);
    }
  }
}
