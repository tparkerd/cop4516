import java.util.*;

public class pancakes {
  public static final boolean DEBUG = true;
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = Integer.parseInt(stdin.nextLine());

    // For each test case...
    for (int i = 1; i <= nCases; i++) {
      String[] inputStr = stdin.nextLine().split(" ");
      char[] characterArray = inputStr[0].toCharArray();
      int K = Integer.parseInt(inputStr[1]);

      if (DEBUG) System.out.println(Arrays.toString(characterArray));
      if (DEBUG) System.out.println(K);

    }
  }

  public static int solve() {


    // Impossible
    return -1;
  }
}
