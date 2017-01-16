 import java.util.*;


public class hexagon {

  public static final boolean DEBUG = true;

  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";

  public static int NUM_SIDES = 6;
  public static int NUM_PIECES = 7;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    // Get number of test cases
    int n = stdin.nextInt();

    // For each test case...
    for (int i = 1; i <= n; i++) {
      int[][] pieces = new int[NUM_PIECES][NUM_SIDES];
      // Read in the digits of each piece
      for (int j = 0; j < NUM_PIECES; j++) {
        if (DEBUG) System.out.printf("Piece #" + (j) + ": ");
        for (int k = 0; k < NUM_SIDES; k++) {
          pieces[j][k] = stdin.nextInt();
          if (DEBUG) System.out.printf("%d ", pieces[j][k]);
        }
        if (DEBUG) System.out.println();
      }

      System.out.printf("Case " + i + ": ");

      // Clean out order array
      int[] perm = new int[NUM_PIECES];
      for (int j = 0; j < NUM_PIECES; j++)
        perm[j] = -1;

      if(!solveRec(pieces, perm, new boolean[NUM_PIECES], 0))
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
  public static boolean solveRec(int[][] pieces, int[] perm, boolean[] used, int k) {
    if (DEBUG) System.out.print(ANSI_RED + "\n\n~Permutation~\t");
    if (DEBUG) printOrder(perm);
    if (DEBUG) System.out.println(ANSI_RESET);
    if (DEBUG) printBoard(pieces, perm);


    if (k >= perm.length) {
      if (DEBUG) System.out.println(ANSI_RED);
      printOrder(perm);
      if (DEBUG) System.out.print(ANSI_RESET);
      return true;
    }

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
          if (DEBUG) System.out.println(ANSI_BLUE + "Rotating piece:\t" + i + ANSI_RESET);
          if (isFirstPlacedPiece(used)) {
            if (DEBUG) System.out.println(ANSI_CYAN + "FIRST PLACED IN PERM" + ANSI_RESET);
            rotatePiece(pieces[i], 1, 0);
          } else {
            rotatePiece(pieces[i], 1, j);
          }
          switch (j) {
            case 0:
              break;
            case 1:
              if (DEBUG) System.out.print((j + 1) + "nd");
              break;
            case 2:
              if (DEBUG) System.out.print((j + 1) + "rd");
              break;
            default:
              if (DEBUG) System.out.print((j + 1) + "th");
              break;
          }
          if (DEBUG) System.out.println(" rotation:\t" + Arrays.toString(pieces[i]));
          if (DEBUG) System.out.println(ANSI_BLUE + "Placing piece:\t" + i + ANSI_RESET);

          if (isValid(pieces, perm)) {
            return solveRec(pieces, perm, used, k + 1);
          }
        }
        perm[k] = -1;
        used[i] = false;

      }
    }
    return false;
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
      int[] piece = pieces[pieceIndex]; // Piece Values
      int centerPieceFace = pieces[perm[0]][(i + NUM_SIDES - 1) % NUM_SIDES]; // CHECK
      int newPieceCenterFace = piece[(pieceIndex + 2) % NUM_SIDES]; // CHECK

      if (DEBUG) System.out.print(ANSI_YELLOW);

      if (DEBUG) System.out.printf("\np[%d][%d] ===  p[%d][%d]\n", 0, (i + NUM_SIDES - 1) % NUM_SIDES, pieceIndex, (pieceIndex + 2) % NUM_SIDES);
      if (DEBUG) System.out.printf("Cen[%d]   ===  New[%d]\n", (pieceIndex + 3) % NUM_SIDES, (pieceIndex + 2) % NUM_SIDES );
      if (DEBUG) System.out.printf("  %d      ===  %d", centerPieceFace, newPieceCenterFace);

      if (DEBUG) System.out.print(ANSI_RESET);

      if (newPieceCenterFace != centerPieceFace) {
        if (DEBUG) System.out.printf(ANSI_RED + "\t\u2715\n" + ANSI_RESET);
        return false;
      } else {
        if (DEBUG) System.out.printf(ANSI_GREEN + "\t\u2713\n" + ANSI_RESET);
      }


      // If only two pieces have been placed, that means there is no neighboring
      // piece other than the center one
      if (perm[2] == -1)  return true;

      // Check if shared face with previously placed piece are equal

      ////////////////////
      // CURRENT PIECE
      ////////////////////
      // Okay, get the current piece, which is stored at the perm[i]
      int placedPieceIndexInPieces = perm[i]; // CHECK
      int[] placedPiece = pieces[placedPieceIndexInPieces]; // CHECK

      // Get the face of the newly placed piece that is shared
      int currentPieceSharedFaceIndex = (i + 3) % NUM_SIDES; // CHECK
      int currentPieceSharedFace = placedPiece[currentPieceSharedFaceIndex]; // -- UNSURE

      ////////////////////
      // PREVIOUS PIECE
      ////////////////////
      // Get the piece that shares a face
      int previousPieceIndex = perm[(i + (NUM_PIECES - 1)) % NUM_PIECES]; // CHECK
      int[] previousPiecePlaced = pieces[previousPieceIndex]; // CHECK

      // Get the face of the previously placed piece that shares a face
      int previousPieceSharedFaceIndex = i % NUM_SIDES;
      int previousPieceSharedFace = previousPiecePlaced[previousPieceSharedFaceIndex];

      // Get the face fo the previously placed piece that is shared
      if (DEBUG) System.out.printf(ANSI_CYAN+"Last piece placed: %d\n", placedPieceIndexInPieces);
      if (DEBUG) System.out.printf("p[%d][%d] ===  p[%d][%d]\n",
       previousPieceIndex, // CHECK
       previousPieceSharedFaceIndex, // CHECK
       placedPieceIndexInPieces, // CHECK
       currentPieceSharedFaceIndex); // CHECK
      if (DEBUG) System.out.printf("Pre[%d]   ===  New[%d]\n", previousPieceSharedFaceIndex, currentPieceSharedFaceIndex);
      if (DEBUG) System.out.printf("  %d      ===  %d" + ANSI_RESET, previousPieceSharedFace, currentPieceSharedFace);


      if (previousPieceSharedFace != currentPieceSharedFace) {
        if (DEBUG) System.out.printf(ANSI_RED + "\t\u2715\n" + ANSI_RESET);
        return false;
      } else {
        if (DEBUG) System.out.printf(ANSI_GREEN + "\t\u2713\n" + ANSI_RESET);
      }

      // Check the final piece if it has been placed
      if (perm[perm.length - 1] != -1) {
        // Last piece
        if (DEBUG) System.out.println(ANSI_CYAN + "CHECK LAST PIECE" + ANSI_RESET);
        int lastPieceFinalFace = pieces[pieceIndex][1];
        int secondPieceFinalFace = pieces[1][4];

        if (lastPieceFinalFace != secondPieceFinalFace)
          return false;
        // Otherwise, this is a solution!
        else {
          if (DEBUG) System.out.println(Arrays.toString(perm));
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

  public static void printBoard(int[][] pieces, int[] perm) {
    System.out.printf("Board:\n");
    System.out.printf("----------------------------------\n");
    for (int j = 0; j < perm.length && perm[j] > -1; j++) {
      System.out.printf("Piece #%d:\t", perm[j]);
      System.out.printf("%s\n", Arrays.toString(pieces[perm[j]]));
    }
    System.out.printf("----------------------------------\n");
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
    // printOrder(piece);
  }

  public static void printOrder(int[] list) {
    int i;
    for (i = 0; (i <= list.length - 1) && (list[i] != -1); i++)
      System.out.printf("%-2d", list[i]);
    System.out.println();
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
