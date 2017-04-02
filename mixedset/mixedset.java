import java.util.*;

public class mixedset {
  public static final boolean DEBUG = false;
  public static HashSet<SortedSet> h;
  public static List<String> l;
  public static int N, S, K;

  @SuppressWarnings("unchecked")
  public static void main(String[] args) {

    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();

    // For each case...
    for (int i = 1; i <= nCases; i++) {
      h = new HashSet<SortedSet>();
      l = new ArrayList<String>();
      N = stdin.nextInt();
      S = stdin.nextInt();
      K = stdin.nextInt();
      boolean[] differences = new boolean[N + 1];
      differences[0] = true;
      perm(new TreeSet<Integer>(), differences);
      for (String s : l) {
        System.out.println(s);
      }
      // if (DEBUG) System.out.println(l.get(K - 1));

    }
  }

  @SuppressWarnings("unchecked")
  public static void perm(SortedSet set, boolean[] used) {
    if (l.size() == K) return;
    if (set.size() >= S) {
      if (h.contains(set)) return;
      // if (DEBUG) System.out.println("::::"+set);
      l.add(set.toString().replace("[", "").replace("]", "").replace(",", ""));
      h.add(set);
      return;
    } else {
      for (int i = 1; i <= N; i++) {
        // When there is more than one item in the set, the item we want to
        // add must be larger than the largest in the set. Also, NO DUPS!
        if (set.size() >= 1 && i <= (int)set.last()) continue;
        else {
          // Try adding the next item
          if (DEBUG) System.out.println("Adding: " + i);
          set.add(i);

          boolean[] tmpUsed = used.clone();
          // TODO(timp): Check that no two pairings share a difference
          // if not valid, return to back out of this branch
          if (!checkDifferences(set, tmpUsed, i)) {
            set.remove(i);
            continue;
          }
          // Valid candidate set found
          perm(set, tmpUsed.clone());
          set.remove(i);
        }
      }
    }
  }

  @SuppressWarnings("unchecked")
  public static boolean checkDifferences(SortedSet set, boolean[] used, int newValue) {
    if (DEBUG) System.out.println("Call:" + set.toString());
    if (DEBUG) System.out.println("Used: " + Arrays.toString(used));
    // Single item, always just a difference of zero, so it's good, go back
    if (set.size() < 2) return true;

    // Get all the values
    ArrayList<Integer> values = new ArrayList(set);

    // From first (0th) to last (set.size() - 1) -- b/c I already added the item
    for (int i = 0; i < set.size() - 1; i++) {
      // See the difference between each item and the new one
      int diff = Math.abs(values.get(i) - newValue);
      if (DEBUG) System.out.printf("|%d - %d| = %d\n", values.get(i), newValue, diff);
      if (used[diff]) {
        if (DEBUG) System.out.println("FAIL");
        return false;
      }
      used[diff] = true;
    }
    return true;
  }
}
