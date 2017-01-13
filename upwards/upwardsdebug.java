import java.util.*;

public class upwardsdebug{

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    // Get number of test cases
    int n = Integer.parseInt(stdin.nextLine());
    System.out.println("Number of test cases: " + n);

    // For each test case...
    for (int i = 1; i <= n; i++) {
      // Scan in the test cases
      String word = stdin.nextLine();
      System.out.println(word);
      if (isUpwards(word))
        System.out.println("YES");
      else
        System.out.println("NO");

    }
  }

  public static boolean isUpwards(String str) {

    for (int i = 1; i < str.length(); i++) {
      System.out.println("Comparing " + str.charAt(i - 1) + " to " + str.charAt(i));
      if (str.charAt(i) <= str.charAt(i - 1)) {
        System.out.println("Character comes before or is the same");
        return false;
      }
        System.out.println("Character is in order");
    }

    return true;
  }



}
