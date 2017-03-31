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
      foo(new int[S], new boolean[N + 1], new boolean[N + 1], 0);
    }
  }


  public static void foo(int[] combo, boolean[] used, boolean[] diff, int k) {
    // Found a valid combo
    if (combo.length == k) {
      System.out.println(":: " + Arrays.toString(combo));
      return;
    }
    // For each of the possible values that could be placed
    for (int i = 1; i < used.length; i++) {
      // Get the last value
      // If it's a single or empty list, this one will be valid regardless
      if (k <= 1) {
        combo[k] = i; // Set the starting digit to the ith value
        used[i] = true;
        // No need to check difference b/c it's just a single value
        foo(combo, used, diff, k + 1);

      // Otherwise, at least one digit was added previously, so we need
      // to find out what would be the next greater value to add to the end
      } else {
        // From the largest number, N, get the next valid value to add
        // to the combo
        int big;
        for (int aBiggerNumber = used.length - 1; big > 1; big--)
          // Look back an extra slot, if it's used, use the currect value
          if (used[big - 1]) break;

        // Check to see if the differences haven't been already used
        for (int d = 0; d < k - 1; d++) {
          // Difference between candidate and last added item
          int tmp = Math.abs(big - combo[d]);
          if (!diff[tmp]) {
            diff[tmp] = true;
          } else {
            return; // Oops, we already used this difference, forget this.
          } // End difference check
        } // End find next largest candidate
        combo[k] = big;
        used[big] = true;
        foo(combo, used, diff, k + 1);
      }
    }
  }

  public static void printcombosoriginal(int[] combo, int k) {
    System.out.println("::" + Arrays.toString(combo));

    int start = 0;
    if (k > 0) start = combo[k - 1] + 1;

    for (int i = start; i < combo.length; i++) {
      combo[k] = i;
      printcombosoriginal(combo, k + 1);
    }
  }


  public static void validate(Integer[] perm) {
    boolean[] used = new boolean[K];
    for (int i = 0; i < perm.length - 1; i++) {
      int diff = Math.abs(perm[i] - perm[i + 1]);
      if (used[diff]) return;
      used[diff] = true;
    }
    list.add(perm);
  }
}
