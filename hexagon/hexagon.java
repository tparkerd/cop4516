import java.util.*;


public class hexagon {
  public static int NUM_SIDES = 6;
  public static int NUM_PIECES = 7;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    // Get number of test cases
    int n = stdin.nextInt();
    System.out.println("Number of test cases: " + n);

    // For each test case...
    for (int i = 1; i <= n; i++) {
      System.out.println("Case " + i + ": ");

      int[][] pieces = new int[NUM_PIECES][NUM_SIDES];
      // Read in the digits of each piece
      for (int j = 0; j < NUM_PIECES; j++) {
        System.out.printf("Piece #" + (j + 1) + ": ");
        for (int k = 0; k < NUM_SIDES; k++) {
          pieces[j][k] = stdin.nextInt();
          System.out.printf("%d ", pieces[j][k]);
        }
        System.out.println();
      }
      System.out.println();
    }
  }


  public static int[] rotatePiece(int[] piece) {
    // Rotate the piece N times
    // I'm not sure if it's best to rotate the original or to just
    // make copies and rotate it N number of times as an argument

    int[] tmp = { 1, 2, 3, 4, 5, 6 };
    return tmp;
  }

  public static void solve(int[][] caseNumbers) {
    // Pick the center piece, one out of the seven, and fix it into place
    
  }
}
