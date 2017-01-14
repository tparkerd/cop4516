import java.util.*;


public class hexagon {
  public static int NUM_SIDES = 6;
  public static int NUM_PIECES = 7;
  public static int[] candidateAnswer;
  public static boolean[] usedPieces;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    // Get number of test cases
    int n = stdin.nextInt();
    System.out.println("Number of test cases: " + n);

    // For each test case...
    for (int i = 1; i <= n; i++) {
      usedPieces = new boolean[7];
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

      System.out.printf("\nCase " + i + ": \n");

      // Clean out order array
      int[] perm = new int[7];
      for (int j = 0; j < 7; j++)
        perm[j] = -1;

      // if(!solveRec(pieces, new boolean[7], 0, order))
        // System.out.printf("No solution\n");
        // solveRec(pieces, usedPieces, 0, order);

      // printPerm(new int[6], new boolean[6], 0);

      solveRec(pieces, perm, new boolean[7], 0);

    }
  }


  // Rotate a piece until a chosen value is on the target face of the piece
  // E.g., rotatePiece(piece, 1, 0) would rotate the piece so that 1 would be
  // on the top (north) face of the hexagon.
  public static void rotatePiece(int[] piece, int value, int desiredPosition) {
    // Continue to rotate the piece until 1 is placed at the top
    while (piece[desiredPosition] != value) {
      // Save the last face of the piece
      int last = piece[piece.length - 1];

      // Shift every face up by one until the last face
      for (int i = piece.length - 2; i >= 0; i--)
          piece[i + 1] = piece[i];

      // Put the last piece into the first face
      piece[0] = last;
    }
    printOrder(piece);
  }

  public static void printOrder(int[] list) {
    int i;
    for (i = 0; i <= list.length - 1; i++)
      System.out.printf("%-2d", list[i]);
    System.out.println();
  }

  // Validity Check Function
  public static boolean isValid(int[][] pieces, int[] perm) {
    // Base case: two or fewer pieces placed is always be a valid case
    // Therefore, we start checking on the third placed piece
    // Validate every placed piece until it appears
    // all pieces were placed, or there is an empty position
    for (int i = 1; (i < perm.length) && (perm[i] != -1); i++) {

      // Check if placed piece matches the center value's face
      // Get piece out of the perm, to find the values from pieces
      int faceCount = pieces[0].length;
      int pieceIndex = perm[i];
      int[] piece = pieces[pieceIndex];
      int newPieceCenterFace = piece[(pieceIndex + 2) % faceCount];
      int centerPieceFace = pieces[0][pieceIndex - 1];

      if (newPieceCenterFace != centerPieceFace)
        return false;

      // Check if shared face with previously placed piece are equal
      int newPiecePreviousFace = piece[(pieceIndex + 3) % faceCount];
      int previousPieceSharedFace = pieces[pieceIndex - 1][pieceIndex % faceCount];
      if (newPiecePreviousFace != previousPieceSharedFace)
        return false;

      // Check the final piece if it has been placed
      if (perm[perm.length - 1] != -1) {
        // Last piece
        int lastPieceFinalFace = pieces[pieceIndex][(pieceIndex + 1) % faceCount];
        int secondPieceFinalFace = pieces[1][4];

        if (lastPieceFinalFace != secondPieceFinalFace)
          return false;
      }
    }
    return true;
  }

  //
  // Recursive approach to solving the problem
  //
  //  pieces: a list of the pieces
  //  used:   a list of used pieces (i.e., [T,F,F,T,F,F])
  //          Where the 1st and 4th pieces have been placed.
  //  k:      A counter of how many pieces have been placed
  //          This will start at zero, which will actually equate to the
  //          index of where the next piece should be placed. So make sure
  //          that this gets incremented on each call to place the next piece.
  //  perm:   The current permuation
  //
  public static void solveRec(int[][] pieces, int[] perm, boolean[] used, int k) {
    if(!isValid(pieces, perm)) {
      System.out.printf("Case %s invalid\n", Arrays.toString(perm));
      return;
    }

    if (k == perm.length) {
      System.out.println(Arrays.toString(perm));
      return;
    }
    for (int i = 0; i < perm.length; i++) {
      if (!used[i]) {
        used[i] = true;
        perm[k] = i;

        // Rotate each one
        for (int j = 0; j < NUM_SIDES; j++) {
          rotatePiece(pieces[i], j + 1, 0);
          solveRec(pieces, perm, used, k + 1);
        }
        used[i] = false;

      }
    }
  }


  public static void printPerm(int[] perm, boolean[] used, int k) {
    if (k == perm.length) {
      System.out.println(Arrays.toString(perm));
      return;
    }
    for (int i = 0; i < perm.length; i++) {
      if (!used[i]) {
        used[i] = true;
        perm[k] = i;
        printPerm(perm, used, k + 1);
        used[i] = false;
      }
    }
  }
}
