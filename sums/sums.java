import java.util.*;

public class sums {
  public static final boolean DEBUG = true;
  public static void main(String[] args) {
      Scanner stdin = new Scanner(System.in);
      int nCases = stdin.nextInt();
      for (int i = 0; i < nCases; i++) {
        // Get the case value
        int cValue = stdin.nextInt();
        // Display the case number and the N value for this case
        System.out.printf("%d %d %d\n", i + 1, cValue, findWeightedSum(cValue));
      }

  }

  public static int findWeightedSum(int n) {
    int k = 0;
    int result = 0;

    for (int i = 1; i <= n; i++) {
      result += (i * findT(i + 1));
    }

    return result;
  }

  public static int findT(int n) {
    int result = 0;
    for (int i = 1; i <= n; i++) {
      result += i;
    }

    return result;
  }
}
