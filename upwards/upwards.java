import java.util.*;

public class upwards {

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    // Get number of test cases
    int n = Integer.parseInt(stdin.nextLine());

    // For each test case...
    for (int i = 1; i <= n; i++) {
      // Scan in the test cases
      String word = stdin.nextLine();
      if (isUpwards(word))
        System.out.println("YES");
      else
        System.out.println("NO");
    }
  }

  public static boolean isUpwards(String str) {

    for (int i = 1; i < str.length(); i++) {
      if (str.charAt(i) <= str.charAt(i - 1)) {
        return false;
      }
    }
    return true;
  }
}
