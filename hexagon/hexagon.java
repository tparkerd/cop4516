import java.util.*;


public class hexagon {
  public static int NUM_SIDES = 6;
  public static int NUM_PIECES = 7;
  public static int[] candidateAnswer;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    // Get number of test cases
    int n = stdin.nextInt();
    System.out.println("Number of test cases: " + n);

    // For each test case...
    for (int i = 1; i <= n; i++) {

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
      int[] order = new int[7];
      for (int j = 0; j < 7; j++)
        order[j] = -1;
      if(!solveRec(pieces, new boolean[7], 0, order))
        System.out.printf("No solution\n");

      System.out.println("_____________________");
      System.out.println();
      System.out.println();
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
  }

  public static void printOrder(int[] list) {
    int i;
    for (i = 0; i < list.length - 1; i++)
      System.out.printf(list[i] + " ");
    System.out.println(list[i]);
  }

  // Validity Check Function
  public static boolean isValid(int[][] pieces, int[] order) {
    System.out.println("Validating... " + Arrays.toString(order));

    // Base case: two or fewer pieces placed is always be a valid case
    // Therefore, we start checking on the third placed piece
    // Validate every placed piece until it appears
    // all pieces were placed, or there is an empty position
    for (int i = 2; (i < order.length) && (order[i] != -1); i++) {

      // Check if placed piece matches the center value's face
      // Get piece out of the order, to find the values from pieces
      int faceCount = pieces[0].length;
      int pieceIndex = order[i];
      int[] piece = pieces[pieceIndex];
      int newPieceCenterFace = piece[(pieceIndex + 2) % faceCount];
      int centerPieceFace = pieces[0][pieceIndex - 1];

      System.out.println("Check ROOT piece faces");
      System.out.printf("%d =?= %d\n", newPieceCenterFace, centerPieceFace);
      if (newPieceCenterFace != centerPieceFace)
        return false;

      // Check if shared face with previously placed piece are equal
      int newPiecePreviousFace = piece[(pieceIndex + 3) % faceCount];
      int previousPieceSharedFace = pieces[pieceIndex - 1][pieceIndex % faceCount];
      System.out.printf("pieces[%d][%d]\n", pieceIndex - 1, (pieceIndex % faceCount));
      System.out.println("Check shared piece faces");
      System.out.printf("%d =?= %d\n", newPiecePreviousFace, previousPieceSharedFace);
      if (newPiecePreviousFace != previousPieceSharedFace)
        return false;

      // Check the final piece if it has been placed
      if (order[order.length - 1] != -1) {
        // Last piece
        int lastPieceFinalFace = pieces[pieceIndex][(pieceIndex + 1) % faceCount];
        int secondPieceFinalFace = pieces[1][4];

        if (lastPieceFinalFace != secondPieceFinalFace)
          return false;
      }
    }


    return true;
  }

  // Recursive approach to solving the problem
  //  pieces: a list of the pieces
  //  used:   a list of used pieces (i.e., [T,F,F,T,F,F])
  //          Where the 1st and 4th pieces have been placed.
  //  placed: A counter of how many pieces have been placed
  //          This will start at zero, which will actually equate to the
  //          index of where the next piece should be placed. So make sure
  //          that this gets incremented on each call to place the next piece.
  //  TODO(timp): order: I'm not quite sure how to use this yet, but I need
  //                     something that will keep track of the partial solution
  //                     until solved.
  public static boolean solveRec(int[][] pieces, boolean[] used, int placed, int[] order) {
    // Base case: all pieces placed, we have a solution
    if (placed >= NUM_PIECES) {
      printOrder(order);
      return true;
    }

    // Check if each piece is unused
    for (int i = 0; i < used.length; i++) {

      // If at least 5 are available, pick the next available piece
      // the charge
      if (placed < 2) {

        // If it's the starting piece, rotate one to the north face
        if (placed == 0)
          rotatePiece(pieces[i], 1, 0);
        // Otherwise, place the second piece, and rotate it so that
        // the south face is a one
        else
          rotatePiece(pieces[i], pieces[i - 1][0], 3);

        // Mark it as used
        used[i] = true;

        // Remember where it's placed and move to the next position
        order[placed++] = i;

      // Otherwise, it's one of the last five pieces to be placed. These need
      // to be validated as they are placed and backtracking needs to be used
      // to save on time and energy
      } else if (!used[i]) {
        // Pick it, and rotate it so that it has the correct value at the
        // correct face

        // Place it at the next available position
        order[placed] = i;

        if(!isValid(pieces, order)) // WHERE THE BACKTRACKING
          return false;

        // It's now used
        used[i] = true;
        return solveRec(pieces, used, placed + 1, order);
      }
    }
    return false;
  }
}
