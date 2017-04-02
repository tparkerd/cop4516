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
      boolean[] differences = new boolean[N];
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
      System.out.println(set);
      l.add(set);
      h.add(set);
      return;
    } else {
      for (int i = 1; i <= N; i++) {
        // When there is more than one item in the set, the item we want to
        // add must be larger than the largest in the set. Also, NO DUPS!
        if (set.size() >= 1 && (int)set.last() >= i) continue;

        // TODO(timp): Check that no two pairings share a difference
        // if not valid, return to back out of this branch
        checkDifferences(set, used, i);


        set.add(i);
        // Valid candidate set found
        perm(set, used);
        set.remove(i);
      }
    }
  }

  @SuppressWarnings("unchecked")
  public static boolean checkDifferences(SortedSet set, boolean[] used, int newValue) {
    if (set.size() <= 1) return true;
    // System.out.printf("checkDifferences(%s)\n\n", set.toString());

    // Clone the used array to know what has been previoulsy used
    boolean[] tmp = used.clone();
    // System.out.println("Tmp" + Arrays.toString(tmp));
    // System.out.println(newValue);
    ArrayList<Integer> setValues = new ArrayList<Integer>(set);
    // System.out.println("SetValues: " + setValues.toString());
    // For each of the items, see if they have been used
    for (int i = 0; i < set.size(); i++) {
      // System.out.printf("Calc diff: |%d - %d| = %d\n", newValue, setValues.get(i), Math.abs(newValue - setValues.get(i)));
      int diff = Math.abs(newValue - setValues.get(i));
      if (tmp[diff]) {
        System.out.println("Failed: " + diff);
        return false;
      }
      tmp[diff] = true;
    }
    used = tmp;
    return true;
  }
}
