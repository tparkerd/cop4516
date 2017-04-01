import java.util.*;

public class mixedset_old {
  static ArrayList<String> list;
  static HashSet<String> hash;
  static int N, S, K;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();

    // For each case...
    for (int i = 1; i <= nCases; i++) {
      list = new ArrayList<String>();
      hash = new HashSet<String>();
      N = stdin.nextInt();
      S = stdin.nextInt();
      K = stdin.nextInt();
      printOdometer(new Integer[S], 0);
      System.out.println(list.get(K - 1));
    }
  }

  // Prints all possible seetings of odometer with n digits with the first k fixed.
  public static void printOdometer(Integer[] odometer, int k) {
      if (K == list.size() - 1) return;

      // Base case.
      if (k == odometer.length) {
        validate(odometer);
        return;
      }

      // Fill in each possible digit in slot k and recurse.
      else {
          int i;
          for (i=0; i < N; i++) {
              odometer[k] = i + 1;
              printOdometer(odometer, k+1);
          }
      }
  }

  public static void validate(Integer[] perm) {
    boolean[] used = new boolean[N];
    String tmp = Arrays.toString(perm);
    tmp = tmp.replace("[", "");
    tmp = tmp.replace("]", "");
    tmp = tmp.replace(",", "");
    String[] characters = tmp.split(" ");
    Arrays.sort(characters);
    StringBuilder builder = new StringBuilder();
    for(String c : characters) {
      builder.append(c + " ");
    }

    for (int i = 0; i < perm.length; i++) {
      for (int j = i + 1; j < perm.length; j++) {
        // Base case: cannot start with zero
        if (perm[i] == 0 || perm[j] == 0) return;
        // Base case: values much be unique
        if (perm[i] == perm[j]) return;
        // Check that differences do not overlap
        int diff = Math.abs(perm[i] - perm[j]);
        if (used[diff])
          return;
        else
          used[diff] = true;
      }
    }

    if (!hash.contains(builder.toString())) {
      hash.add(builder.toString());
      list.add(builder.toString());
    }
  }
}
