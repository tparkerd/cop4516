import java.util.*;

public class mixedset {
  public static int N, S, K, counter;

  public static void main(String[] args) {

    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();

    // For each case...
    for (int i = 1; i <= nCases; i++) {
      counter = 0;
      N = stdin.nextInt();
      S = stdin.nextInt();
      K = stdin.nextInt();;
      int[] differenceFrequencies = new int[N + 1];
      Arrays.fill(differenceFrequencies, 0);
      solve(new TreeSet<Integer>(), differenceFrequencies);
    }

  }

  @SuppressWarnings("unchecked")
  public static void solve(SortedSet set, int[] differenceFrequencies) {
    // Already found the k-th ranked item
    if (counter == K) return;
    if (set.size() >= S) {
      counter++;
      if (counter == K)
      // Stringify!
      // Clean up the set's string and display the answer
      System.out.println(set.toString().replace("[", "").replace("]", "").replace(",", ""));
      return;

    // Otherwise, the answer has yet to be found
    } else {
      for (int i = 1; i <= N; i++) {
        // When there is more than one item in the set, the item we want to
        // add must be larger than the largest in the set. Also, NO DUPS!
        if (set.size() >= 1 && i <= (int)set.last()) continue;
        else {
          // Try adding the next item
          set.add(i);

          int[] tmpFreq = differenceFrequencies.clone();
          // Check that no two pairings share a difference
          // if not valid, return to back out of this branch
          if (!valid(set, differenceFrequencies.clone(), tmpFreq)) {
            set.remove(i);
            continue;
          }
          // Valid candidate set found
          solve(set, tmpFreq);
          set.remove(i);
        }
      }
    }
  }

  @SuppressWarnings("unchecked")
  public static boolean valid(SortedSet set, int[] copy, int[] freq) {
    // Assume any empty or single item set is valid
    if (set.size() < 2) return true;
    // Since you can't directly access the values in a set, convert to list
    ArrayList<Integer> s = new ArrayList<Integer>(set);
    // Save the last item added for reference
    int last = (int)set.last();
    // For each of the items before the last item...
    for (int i = 0; i < s.size() - 1; i++) {
      // Calculate their difference
      int diff = last - s.get(i);
      // Increment the number of said difference found
      copy[diff]++;
      // If we founda a repeat difference, bail out
      if (copy[diff] > 1) {
        return false;
      }
    }

    // Otherwise, it was never proven invalid, so actually updates the real
    // list of difference frequencies
    for (int i = 0; i < s.size() - 1; i++) {
      int diff = last - s.get(i);
      freq[diff]++;
    }
    return true;
  }
}
