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
    if (DEBUG) System.out.printf("printOdometer(%s, %d);\n", Arrays.toString(odometer), k);
      // Base case.
      if (k == odometer.length) process(odometer);

      // Fill in each possible digit in slot k and recurse.
      else {
          int i;
          for (i=0; i<=N; i++) {
              odometer[k] = i;
              printOdometer(odometer, k+1);
          }
      }
  }


  public static void bar(int[] perm, boolean[] used, int k) {
    System.out.printf("bar(%s, %s, %d);\n", Arrays.toString(perm), Arrays.toString(used), k);
    if (k == perm.length) process(perm);

    for (int i = 0; i < used.length; i++) {
      if (!used[i]) {
        used[i] = true;
        perm[k] = i;
        bar(perm, used, k + 1);
        used[i] = false;
      }
    }
  }

  public static void process(int[] perm) {
    System.out.println(Arrays.toString(perm));

  }

  public static void foo(int[] combo, boolean[] used, boolean[] diff, int k) {
    if (DEBUG) System.out.printf("foo(%s, %s, %s, %d)\n", Arrays.toString(combo), Arrays.toString(used), Arrays.toString(diff), k);
    // Found a valid combo
    if (combo.length == k) {
      System.out.println(":: " + Arrays.toString(combo));
      return;
    }
    // For each of the possible values that could be placed
    for (int i = 1; i < used.length; i++) {
      // Get the last value
      // If it's an empty list, this one will be valid regardless
      if (k == 0) {
        combo[k] = i; // Set the starting digit to the ith value
        used[i] = true;
        diff[0] = true;
        // No need to check difference b/c it's just a single value
        foo(combo, used, diff, k + 1);
        used[i] = false;

      // Otherwise, at least one digit was added previously, so we need
      // to find out what would be the next greater value to add to the end
      } else {
        // From the largest number, N, get the next valid value to add to the combo
        int big = used.length - 1;
        if (DEBUG) System.out.println("Big index: " + big);
        while (big > 1) {
          if (used[big - 1]) break;
          big--;
        }
        if (DEBUG) System.out.println("Big index'': " + big);

        // Check to see if the differences haven't been already used
        for (int d = 0; d < k; d++) {
          if (DEBUG) System.out.printf("Comparing: |%d - %d| = %d\n", big, combo[d], Math.abs(big - combo[d]));
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
        used[big] = false;
        // Reset diff array
        Arrays.fill(diff, false);
        for (int d = 0; d < k; d++) {
          for (int d2 = d + 1; d2 < k; d2++) {
            diff[Math.abs(combo[d] - combo[d2])] = true;
          }
        }
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
