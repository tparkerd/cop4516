import java.util.*;


public class hexagon {
  public int CASE_LENGTH = 42;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    // Get number of test cases
    int n = Integer.parseInt(stdin.nextLine());
    System.out.println("Number of test cases: " + n);

    // For each test case...
    for (int i = 1; i <= n; i++) {
      // Scan in the test cases
      System.out.println("Case " + i + ": ");
      String tmpCase = stdin.nextLine();
      String[] tmpCases = tmpCase.split("\\s");
      System.out.println(tmpCase);
      System.out.println(tmpCase.toString());
      // Convert all the strings to integers
    }
  }


  public static int[] rotatePiece(int[] piece) {
    // Rotate the piece N times
    // I'm not sure if it's best to rotate the original or to just
    // make copies and rotate it N number of times as an argument

    int[] tmp = { 1, 2, 3, 4, 5, 6 };
    return tmp;
  }
}
