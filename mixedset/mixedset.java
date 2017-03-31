import java.util.*;

public class mixedset {
  static final boolean DEBUG = true;
  static ArrayList<Integer[]> list;
  static int n, s, k;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    list = new ArrayList<Integer[]>();
    int nCases = stdin.nextInt();

    // For each case...
    for (int i = 1; i <= nCases; i++) {
      n = stdin.nextInt();
      s = stdin.nextInt();
      k = stdin.nextInt();
      if (DEBUG) System.out.printf("Case #%d: n, s, k = (%d, %d, %d)\n", i, n, s, k);
      perm(new Integer[s], n, 0);
      // Collections.sort(list);
      System.out.println(list.get(k-1));
    }
  }

  // public static void perm(Integer[] perm, boolean[] used, boolean[] diff, int q) {
  //   if (list.size() == k) {
  //     // validate(perm);
  //     list.add(perm);
  //     if (DEBUG) System.out.println(Arrays.toString(perm));
  //     return;
  //   }
  //
  //   for (int i = 1; i <= perm.length; i++) {
  //     if (!used[i]) {
  //       used[i] = true;
  //       perm[q] = i;
  //       perm(perm, used, diff, q + 1);
  //       used[i] = false;
  //     }
  //   }
  // }

  public static void perm(Integer[] perm, int digits, int K) {
    if (K == perm.length) {
      System.out.println(Arrays.toString(perm));
    }

    for (int i = 1; i <= digits; i++) {
      perm[K] = i;
      perm(perm, digits, K + 1);
    }
  }


  public static void validate(Integer[] perm) {
    boolean[] used = new boolean[k];
    for (int i = 0; i < perm.length - 1; i++) {
      int diff = Math.abs(perm[i] - perm[i + 1]);
      if (used[diff]) return;
      used[diff] = true;
    }
    list.add(perm);
  }
}
