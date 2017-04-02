import java.util.*;

public class mixedset_second {
  public static ArrayList<String> list;
  public static HashSet<String> hash;
  public static int N, S, K;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();

    // For each case...
    for (int i = 1; i <= nCases; i++) {
      System.out.println("Case: " + i);
      list = new ArrayList<String>();
      hash = new HashSet<String>();
      N = stdin.nextInt();
      S = stdin.nextInt();
      K = stdin.nextInt();
      combo(new int[S], 0, new boolean[N]);
      System.out.println(list);
      // System.out.println()
      // System.out.println("Solution: %s", )
      // System.out.println(list.get(K - 1));
    }
  }

  // Prints all possible seetings of a with n digits with the first k fixed.
  public static void combo(int[] a, int k, boolean[] used) {
    // Already found rank
    if (K == list.size() - 1) return;
    // Reached list length
    if (S == k) {
      // NOTE(timp): arrays pass by reference, not value, derp-a-derp, forgot that
      int[] tmp = a.clone();
      // Sort
      Arrays.sort(tmp);
      // Validate
      if (valid(tmp)) {
        // Stringify
        String key = Arrays.toString(tmp).replace("[", "").replace("]", "").replace(",", "");
        // Hash
        if (!hash.contains(key)) {
          // Add to hash set
          hash.add(key);
          // Add to list
          list.add(key);
        }
        // Bail out!
        return;
      }
    // Otherwise, keep trying to build the combo
    } else {
      for (int i = 0; i < N; i++) {
        a[k] = i + 1;
        // Check differences up to this point
        if (!distinctDifferences(a, k)) return;
        combo(a, k + 1, used);
      }
    }
  }

  public static boolean valid(int[] perm) {
    boolean[] used = new boolean[N];
      for (int i = 0; i < perm.length; i++) {
      for (int j = i + 1; j < perm.length; j++) {
        // Base case: cannot start with zero
        if (perm[i] == 0 || perm[j] == 0) return false;
        // Base case: values much be unique
        if (perm[i] == perm[j]) return false;
        // Check that differences do not overlap
        int diff = Math.abs(perm[i] - perm[j]);
        if (used[diff])
          return false;
        else
          used[diff] = true;
      }
    }
    return true;
  }

  public static boolean distinctDifferences(int[] a, int k) {
    int newValue = a[k];
    // Differences list
    boolean[] differences = new boolean[N];
    for (int i = 0; i < k; i++) {
      // Also, check to see if the new value is greater than the previous ones
      int d = a[i] - newValue;
      if (d <= 0) {
        return false;
      }
      if (differences[d]) {
        return false;
      } else {
        differences[d] = true;
      }
    }
    return true;
  }
}
