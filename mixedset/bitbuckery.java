import java.util.*;

public class bitbuckery {
  public static List<Long> l;
  public static HashSet<String> h;
  public static int N, S, K;

  @SuppressWarnings("unchecked")
  public static void main(String[] args) {

    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();

    long startTime = System.currentTimeMillis();

    // For each case...
    for (int i = 1; i <= nCases; i++) {
      l = new ArrayList<Long>();
      h = new HashSet<String>();
      N = stdin.nextInt();
      S = stdin.nextInt();
      K = stdin.nextInt();;

      // l.add(0l);
      // magic(0l, 0);
      for (long q = 0; q <= Math.pow(2, N); q++)
        l.add(q);
      // l.add(0l & (1 << 3));

      for (Long b : l)
        System.out.println(Long.toBinaryString(b));



      // int[] history = new int[N + 1];
      // Arrays.fill(history, 0);
      // perm(new TreeSet<Integer>(), history);
      // System.out.println(l.get(l.size() - 1));
      // for (String s : l)
      //   System.out.println(s);
    }

    long endTime   = System.currentTimeMillis();
    long totalTime = endTime - startTime;
    System.out.println("Run time: " + totalTime/1000.0);
  }

  public static void magic(long mask, int k) {
    if (l.size() == K) return;
    if (k == S) {
      l.add(mask);
      return;
    }


    for (int i = 0; i <= N; i++) {
      mask = mask | (1 << i);
      System.out.println(Long.toString(mask, 2));
      magic(mask, k + 1);
      mask = mask ^ (1 << i);
    }

  }

  //
  // @SuppressWarnings("unchecked")
  // public static void perm(SortedSet set, int[] history) {
  //   if (l.size() == K) return;
  //   if (set.size() >= S) {
  //     // Stringify!
  //     // Clean up the set's string and then add it to the list
  //     String mySet = set.toString().replace("[", "").replace("]", "").replace(",", "");
  //     l.add(mySet);
  //     // System.out.println(mySet);
  //     return;
  //   } else {
  //     for (int i = 1; i <= N; i++) {
  //       // When there is more than one item in the set, the item we want to
  //       // add must be larger than the largest in the set. Also, NO DUPS!
  //       if (set.size() >= 1 && i <= (int)set.last()) continue;
  //       else {
  //         // Try adding the next item
  //         set.add(i);
  //
  //         // Check that no two pairings share a difference
  //         // if not valid, return to back out of this branch
  //         if (!valid(set, history.clone())) {
  //           set.remove(i);
  //           continue;
  //         }
  //
  //         int[] tmphistory = history.clone();
  //         update(set, tmphistory);
  //
  //         // Valid candidate set found
  //         perm(set, tmphistory);
  //         set.remove(i);
  //       }
  //     }
  //   }
  // }
  //
  // @SuppressWarnings("unchecked")
  // public static boolean valid(SortedSet set, int[] freq) {
  //   if (set.size() < 2) return true;
  //   ArrayList<Integer> s = new ArrayList<Integer>(set);
  //   int last = (int)set.last();
  //   for (int i = 0; i < s.size() - 1; i++) {
  //     int diff = last - s.get(i);
  //     // System.out.println(set +  " failed");
  //     freq[diff]++;
  //     if (freq[diff] > 1) {
  //       return false;
  //     }
  //   }
  //   return true;
  // }
  //
  // @SuppressWarnings("unchecked")
  // public static boolean update(SortedSet set, int[] freq) {
  //   if (set.size() < 2) return true;
  //   ArrayList<Integer> s = new ArrayList<Integer>(set);
  //   int last = (int)set.last();
  //   for (int i = 0; i < s.size() - 1; i++) {
  //     int diff = last - s.get(i);
  //     freq[diff]++;
  //     if (freq[diff] > 1) {
  //       return false;
  //     }
  //   }
  //   return true;
  // }


}
