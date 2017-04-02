import java.util.*;

public class mixedset {
  public static List<String> l;
  public static HashSet<String> h;
  public static int N, S, K;

  @SuppressWarnings("unchecked")
  public static void main(String[] args) {

    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();

    // For each case...
    for (int i = 1; i <= nCases; i++) {
      l = new ArrayList<String>();
      h = new HashSet<String>();
      N = stdin.nextInt();
      S = stdin.nextInt();
      K = stdin.nextInt();;
      int[] history = new int[N + 1];
      history[0] = 1;
      perm(new TreeSet<Integer>(), history);
      System.out.println(l.get(l.size() - 1));
      for (String s : l)
        System.out.println(s);
    }
  }

  @SuppressWarnings("unchecked")
  public static void perm(SortedSet set, int[] history) {
    if (l.size() == K) return;
    if (set.size() >= S) {
      // Stringify!
      // Clean up the set's string and then add it to the list
      String mySet = set.toString().replace("[", "").replace("]", "").replace(",", "");
      l.add(mySet);
      return;
    } else {
      for (int i = 1; i <= N; i++) {
        // When there is more than one item in the set, the item we want to
        // add must be larger than the largest in the set. Also, NO DUPS!
        if (set.size() >= 1 && i <= (int)set.last()) continue;
        else {
          // Try adding the next item
          set.add(i);

          // Check that no two pairings share a difference
          // if not valid, return to back out of this branch
          if (!valid(set, history.clone())) {
            set.remove(i);
            continue;
          }
          // Valid candidate set found
          perm(set, history);
          set.remove(i);
        }
      }
    }
  }

  @SuppressWarnings("unchecked")
  public static boolean valid(SortedSet set, int[] freq) {
    if (set.size() < 2) return true;
    ArrayList<Integer> s = new ArrayList<Integer>(set);
    for (int i = 0; i < s.size() - 1; i++) {
      for (int j = i + 1; j < s.size(); j++) {
        int diff = Math.abs(s.get(i) - s.get(j));
        freq[diff]++;
        if (freq[diff] > 1) {
          return false;
        }
      }
    }
    return true;
  }
}
