import java.util.Scanner;
import java.util.Arrays;

public class change {
  public static void main(String[] args) {

    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();

    // For each test case...
    for (int i = 0; i < nCases; i++) {
      // Get the number of denominations
      int nDenomintations = stdin.nextInt();
      int[] pkg = new int[nDenomintations];
      // For each type of denomination
      for (int j = 0; j < nDenomintations; j++) {
        pkg[j] = stdin.nextInt();
      }
      System.out.printf("Set: #%d: %d\n", i + 1, solve(pkg));
    }
  }

  public static int solve(int[] denominations) {
    Arrays.sort(denominations);
    // System.out.println(Arrays.toString(denominations));
    int total = 0;
    int i;
    for (i = 0; i < denominations.length && denominations[i] <= total + 1; i++) {
      total += denominations[i];
    }
    return total + 1;
  }
}
