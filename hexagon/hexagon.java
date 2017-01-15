 import java.util.*;


public class hexagon {

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
  public static int[] candidateAnswer;
  public static boolean[] usedPieces;
  public static int callCount;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    // Get number of test cases
    int n = stdin.nextInt();
    System.out.println("Number of test cases: " + n);

    callCount = 0;
    // For each test case...
    for (int i = 1; i <= n; i++) {
      usedPieces = new boolean[NUM_PIECES];
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
      int[] perm = new int[NUM_PIECES];
      for (int j = 0; j < NUM_PIECES; j++)
        perm[j] = -1;

      // if(!solveRec(pieces, new boolean[NUM_PIECES], 0, order))
        // System.out.printf("No solution\n");
        // solveRec(pieces, usedPieces, 0, order);

      // printPerm(new int[4], new boolean[4], 0);

      solveRec(pieces, perm, new boolean[NUM_PIECES], 0);

    }
  }


  // Validity Check Function
  public static boolean isValid(int[][] pieces, int[] perm) {
    printBoard(pieces, perm);

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
      int centerPieceFace = pieces[0][(pieceIndex + 3) % faceCount];

      System.out.printf("p[%d][%d] ===  p[%d][%d]\n", 0, (pieceIndex + 3) % faceCount, pieceIndex, (pieceIndex + 2) % faceCount);
      System.out.printf("Cen[%d]   ===  New[%d]\n", (pieceIndex + 3) % faceCount, (pieceIndex + 2) % faceCount );
      System.out.printf("  %d      ===  %d", centerPieceFace, newPieceCenterFace);

      // System.out.println("Check ROOT piece faces");
      // System.out.printf("%d =?= %d\n", newPieceCenterFace, centerPieceFace);

      if (newPieceCenterFace != centerPieceFace) {
        System.out.printf(ANSI_RED + "\t\u2715\n" + ANSI_RESET);
        // return false;
      } else {
        System.out.printf(ANSI_GREEN + "\t\u2713\n" + ANSI_RESET);
      }

      // Check if shared face with previously placed piece are equal
      int newPiecePreviousFace = piece[(pieceIndex + 3) % faceCount];
      int previousPieceSharedFace = pieces[pieceIndex - 1][pieceIndex % faceCount];

      System.out.printf("Pre[%d] === New[%d]\n", pieceIndex % faceCount, (pieceIndex + 3) % faceCount );
      System.out.printf("  %d    ===  %d", previousPieceSharedFace, newPiecePreviousFace);

      if (newPiecePreviousFace != previousPieceSharedFace) {
        System.out.printf(ANSI_RED + "\t\u2715\n" + ANSI_RESET);
        // return false;
      } else {
        System.out.printf(ANSI_GREEN + "\t\u2713\n" + ANSI_RESET);
      }

      // Check the final piece if it has been placed
      // if (perm[perm.length - 1] != -1) {
      //   // Last piece
      //   int lastPieceFinalFace = pieces[pieceIndex][(pieceIndex + 1) % faceCount];
      //   int secondPieceFinalFace = pieces[1][4];
      //
      //   if (lastPieceFinalFace != secondPieceFinalFace)
      //     return false;
      // }
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
    // System.out.print("[");
    // printOrder(perm);
    System.out.print(ANSI_YELLOW + "~Permutation~\t");
    System.out.println(Arrays.toString(perm) + ANSI_RESET);
    if(!isValid(pieces, perm)) return;

    if (k == perm.length) {
      System.out.println(ANSI_CYAN + "SOLUTION FOUND" + ANSI_RESET);
      System.out.println(Arrays.toString(perm));
      return;
    }

    for (int i = 0; i < perm.length; i++) {
      if (!used[i]) {
        used[i] = true;
        perm[k] = i;
        // solveRec(pieces, perm, used, k + 1);
        // Rotate each one
        for (int j = 0; j < NUM_SIDES; j++) {
          // If this is the center piece, always have ONE on the north face
          System.out.println(ANSI_BLUE + "Rotating piece:\t" + i + ANSI_RESET);
          if (isFirstPlacedPiece(used)) {
            rotatePiece(pieces[i], 1, 0);
          } else {
            rotatePiece(pieces[i], 1, j);
          }
          switch (j) {
            case 0:
              System.out.print((j + 1) + "st");
              break;
            case 1:
              System.out.print((j + 1) + "nd");
              break;
            case 2:
              System.out.print((j + 1) + "rd");
              break;
            default:
              System.out.print((j + 1) + "th");
              break;
          }
          System.out.println(" rotation:\t" + Arrays.toString(pieces[i]));
          System.out.println(ANSI_BLUE + "Placing piece:\t" + i + ANSI_RESET);

          solveRec(pieces, perm, used, k + 1);
        }
        perm[k] = -1;
        used[i] = false;

      }
    }
  }

  public static boolean isFirstPlacedPiece(boolean[] used) {
    int count = 0;
    for (int i = 0; i < used.length; i++) {
      if (used[i]) {
        count++;
      }
    }
    return (count > 1) ? false : true;
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