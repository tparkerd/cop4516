 import java.util.*;


public class hexagon {

  public static int NUM_SIDES = 6;
  public static int NUM_PIECES = 7;
  public static boolean solved;
  public static int[] solution;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    // Get number of test cases
    int n = stdin.nextInt();

    // For each test case...
    for (int i = 1; i <= n; i++) {

      // Assume there is no solution for this test case
      solved = false;
      solution = new int[7];

      // Read in the digits of each piece
      int[][] pieces = new int[NUM_PIECES][NUM_SIDES];
      for (int j = 0; j < NUM_PIECES; j++)
        for (int k = 0; k < NUM_SIDES; k++)
          pieces[j][k] = stdin.nextInt();

      // Clean out order array
      int[] perm = new int[NUM_PIECES];
      for (int j = 0; j < NUM_PIECES; j++)
      perm[j] = -1;

      System.out.printf("Case " + i + ": ");

      solveRec(pieces, perm, new boolean[NUM_PIECES], 0);
      if (solved)
        printOrder(solution);
      else
        System.out.println("No solution");
    }
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

    // Base case: if the current permuation is invalid, do not bother trying
    // subsequent cases
    if(!isValid(pieces, perm)) return;

    // For each of the unused pieces...
    for (int i = 0; i < perm.length; i++) {
      // If one that is available
      if (!used[i]) {

        // Mark it as used
        used[i] = true;
        perm[k] = i;

        // Rotate each one and try it in place
        for (int j = 0; j < NUM_SIDES; j++) {

          // If this is the center piece, always have ONE on the north face
          if (isFirstPlacedPiece(used))
            rotatePiece(pieces[i], 1, 0);
          else
            rotatePiece(pieces[i], 1, j);

          solveRec(pieces, perm, used, k + 1);

          // Base case: if this candiate perm is the solution, exit
          if (solved) return;

        }

        // Mark the current piece as unused for alternate permutations
        perm[k] = -1;
        used[i] = false;
      }
    }
  }

  // Validity Check Function
  public static boolean isValid(int[][] pieces, int[] perm) {

    int i;
    // Skip any valid positions
    for (i = 1; (i < perm.length - 1) && (perm[i + 1] != -1); i++);
    // i = 1;

    // Base case: two or fewer pieces placed is always be a valid case
    // Therefore, we start checking on the third placed piece
    // Validate every placed piece until it appears
    // all pieces were placed, or there is an empty position
    for (; (i < perm.length) && (perm[i] != -1); i++) {

      // Check if placed piece matches the center value's face
      // Get piece out of the perm, to find the values from pieces
      int pieceIndex = perm[i]; // Piece #
      int[] piece = pieces[pieceIndex]; // Piece Value
      int centerPieceFace = pieces[perm[0]][(i + NUM_SIDES - 1) % NUM_SIDES]; // Center's face index
      int newPieceCenterFace = piece[(i + 2) % NUM_SIDES]; // New piece's face value

      // Check if the new piece and the center piece share identical values
      if (newPieceCenterFace != centerPieceFace) return false;

      // If only two pieces have been placed, that means there is no neighboring
      // piece other than the center one
      if (perm[2] == -1)  return true;

      // Check if shared face with previously placed piece are equal

      // CURRENT PIECE
      // Okay, get the current piece, which is stored at the perm[i]
      int placedPieceIndexInPieces = perm[i]; // CHECK
      int[] placedPiece = pieces[placedPieceIndexInPieces]; // CHECK

      // Get the face of the newly placed piece that is shared
      int currentPieceSharedFaceIndex = (i + 3) % NUM_SIDES; // CHECK
      int currentPieceSharedFace = placedPiece[currentPieceSharedFaceIndex]; // -- UNSURE

      // PREVIOUS PIECE
      // Get the piece that shares a face
      int previousPieceIndex = perm[(i + (NUM_PIECES - 1)) % NUM_PIECES]; // CHECK
      int[] previousPiecePlaced = pieces[previousPieceIndex]; // CHECK

      // Get the face fo the previously placed piece that is shared
      int previousPieceSharedFaceIndex = i % NUM_SIDES;
      int previousPieceSharedFace = previousPiecePlaced[previousPieceSharedFaceIndex];

      if (previousPieceSharedFace != currentPieceSharedFace) return false;

      // Check the final piece if it has been placed
      // **Hacky** - could be cleaned up and use the NUM_PIECES
      if (perm[perm.length - 1] != -1) {
        // Last piece
        int lastPieceFinalFace = pieces[perm[6]][1];
        int secondPieceFinalFace = pieces[perm[1]][4];

        if (lastPieceFinalFace != secondPieceFinalFace)
          return false;
        // Otherwise, the solution was found
        else {
          solved = true;
          solution = perm;
          return true;
        }
      }
    }
    return true;
  }

  public static boolean isFirstPlacedPiece(boolean[] used) {
    int count = 0;
    for (int i = 0; i < used.length; i++) {
      if (used[i]) {
        count++;
      }
    }
    return (count < 2);
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
    for (i = 0; (i <= list.length - 1) && (list[i] != -1); i++)
      System.out.printf("%-2d", list[i]);
    System.out.println();
  }

}
