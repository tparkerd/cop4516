import java.util.*;

public class sets {
  public static HashSet<SortedSet> h;
  public static List<SortedSet> l;
  public static int N, S, K;

  @SuppressWarnings("unchecked")
  public static void main(String[] args) {

    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();

    // For each case...
    for (int i = 1; i <= nCases; i++) {
      h = new HashSet<SortedSet>();
      l = new ArrayList<SortedSet>();
      N = stdin.nextInt();
      S = stdin.nextInt();
      K = stdin.nextInt();
      boolean[] differences = new boolean[N + 1];
      differences[0] = true;
      perm(new TreeSet<Integer>(), differences);
    }
  }

  @SuppressWarnings("unchecked")
  public static void perm(SortedSet set, boolean[] used) {
    if (l.size() == K) return;
    // System.out.printf("Call: %s (%d)\n", set.toString(), set.size());
    if (set.size() >= S) {
      if (h.contains(set)) return;
      System.out.println("::::"+set);
      l.add(set);
      h.add(set);
      return;
    } else {
      for (int i = 1; i <= N; i++) {
        // When there is more than one item in the set, the item we want to
        // add must be larger than the largest in the set. Also, NO DUPS!
        if (set.size() >= 1 && (int)set.last() >= i) continue;

        // System.out.printf("%s adding %d\n", set.toString(), i);
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

  @SuppressWarnings("unchecked")
  public static boolean checkDifferences(SortedSet set, boolean[] used, int newValue) {
    // Single item, always just a difference of zero, so it's good, go back
    // if (set.size() <= 1) return true;

    // Get all the values
    ArrayList<Integer> values = new ArrayList(set);

    // System.out.printf("Used: %s\n", Arrays.toString(used));

    // From first (0th) to last (set.size() - 1) -- b/c I already added the item
    for (int i = 0; i < set.size() - 1 ; i++) {
      // See the difference between each item and the new one
      // System.out.printf("|%d - %d| = %d\n", values.get(i), newValue, Math.abs(values.get(i) - newValue));
      int diff = Math.abs(values.get(i) - newValue);
      if (used[diff]) {
        // System.out.println("FAIL");
        return false;
      }
      used[diff] = true;
    }
    // System.out.println("PASS");
    return true;
  }
}
