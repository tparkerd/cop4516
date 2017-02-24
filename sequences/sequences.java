import java.util.*;

public class sequences {
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();

    // For each case...
    for (int i = 0; i < nCases; i++) {
      // Get the length of the array
      int length = stdin.nextInt();
      // Get value of k
      int k = stdin.nextInt();

      int[] tmp = new int[length];
      for (int j = 0; j < length; j++) {
        tmp[j] = stdin.nextInt();
      }

      int[] result = solve(tmp, k);
      for (int j = 0; j < result.length; j++) {
        System.out.printf("%d ", result[j]);
      }
      System.out.println();
    }
  }

  public static int[] solve(int[] a, int k) {
    if (k == 0)
      return a;

    int[] tmp = new int[a.length - 1];
    for (int i = 0; i < a.length - 1; i++) {
      tmp[i] = a[i] + a[i + 1];
    }
    return solve(tmp, k - 1);

  }
}
