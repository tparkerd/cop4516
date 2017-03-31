import java.util.*;

public class mixedset {
  static final boolean DEBUG = true;
  static ArrayList<Integer[]> list;
  static int N, S, K;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    list = new ArrayList<Integer[]>();
    int nCases = stdin.nextInt();

    // For each case...
    for (int i = 1; i <= nCases; i++) {
      N = stdin.nextInt();
      S = stdin.nextInt();
      K = stdin.nextInt();
      if (DEBUG) System.out.printf("Case #%d: %d, %d, %d\n", i, N, S, K);
      // printcombos(new int[S], 0);
      // System.out.println(list.get(K-1));
      // foo(new int[S], new boolean[N + 1], new boolean[N + 1], 0);

      // boolean[] used = new boolean[N + 1];
      // used[0] = true;
      // bar(new int[S], used, 0);
      printOdometer(new int[S], 0);
    }
  }


  // Prints all possible seetings of odometer with n digits with the first k fixed.
  public static void printOdometer(int[] odometer, int k) {
      // Base case.
      if (k == odometer.length) {
        validate(odometer);
        return;
      }

      // Fill in each possible digit in slot k and recurse.
      else {
          int i;
          for (i=0; i <= N; i++) {
              odometer[k] = i;
              printOdometer(odometer, k+1);
          }
      }
  }

  public static void validate(int[] perm) {
    boolean[] used = new boolean[K + 1];
    for (int i = 0; i < perm.length - 1; i++) {
      for (int j = i + 1; j < perm.length; j++) {
        // Base case: cannot start with zero
        if (perm[i] == 0 || perm[j] == 0) return;
        // Base case: values much be unique
        if (perm[i] == perm[j]) return;
        // Check that differences do not overlap
        int diff = Math.abs(perm[i] - perm[j]);
        if (used[diff])
          return;
        else
          used[diff] = true;
      }
    }
    System.out.println("Passed: " + Arrays.toString(perm));
    // list.add(perm);
  }
}
