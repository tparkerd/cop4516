import java.util.*;


public class hexagon {
  public static int NUM_SIDES = 6;
  public static int NUM_PIECES = 7;
  public static boolean[] usedPieces;
  public static int[] candidateAnswer;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);


    // Get number of test cases
    int n = stdin.nextInt();
    System.out.println("Number of test cases: " + n);

    // For each test case...
    for (int i = 1; i <= n; i++) {
      System.out.println("Case " + i + ": ");
      usedPieces = new boolean[7];
      System.out.println(Arrays.toString(usedPieces));

      int[][] pieces = new int[NUM_PIECES][NUM_SIDES];
      // Read in the digits of each piece
      for (int j = 0; j < NUM_PIECES; j++) {
        System.out.printf("Piece #" + (j) + ": ");
        for (int k = 0; k < NUM_SIDES; k++) {
          pieces[j][k] = stdin.nextInt();
          System.out.printf("%d ", pieces[j][k]);
        }
        System.out.println();
      }
      System.out.println();

      // SOLVE HERE
      solve(pieces);
    }
  }


  public static int[] rotatePiece(int[] piece) {
    // Rotate the piece N times
    // I'm not sure if it's best to rotate the original or to just
    // make copies and rotate it N number of times as an argument

    int[] tmp = { 1, 2, 3, 4, 5, 6 };
    return tmp;
  }

  public static void rotateOneToTopFace(int[] piece) {
    // Continue to rotate the piece until 1 is placed at the top
    while (piece[0] != 1) {

      // Save the last face of the piece
      int last = piece[piece.length - 1];

      // Shift every face up by one until the last face
      for (int i = piece.length - 2; i >= 0; i--)
          piece[i + 1] = piece[i];

      // Put the last piece into the first face
      piece[0] = last;
    }
  }

  public static void solve(int[][] pieces) {
    // Pick the center piece, one out of the seven, and fix it into place.
    // Do this for each piece.
    for (int i = 0; i < pieces.length; i++) {
      // Pick piece
      int[] piece = pieces[i];

      // Rotate it so that 1 is at the zeroth index (i.e., top of the hexagon)
      rotateOneToTopFace(pieces[i]);
      System.out.println("Piece #" + i + " rotated: " + Arrays.toString(pieces[i]));

      //
      usedPieces[i] = true;
      System.out.println(Arrays.toString(usedPieces));


      // Place piece down in center position



    }

  }
}
