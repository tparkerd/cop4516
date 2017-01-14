import java.util.*;


public class hexagon {
  public static int NUM_SIDES = 6;
  public static int NUM_PIECES = 7;
  public static enum Position {
    north, northeast, southeast, south, southwest, northwest
  }
  public static boolean[] usedPieces;
  public static int[] candidateAnswer;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    // Get number of test cases
    int n = stdin.nextInt();
    System.out.println("Number of test cases: " + n);

    // For each test case...
    for (int i = 1; i <= n; i++) {
      System.out.printf("Case " + i + ": ");
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
      solveRec(pieces, new boolean[7], 0, new int[7]);
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
  public static void solveRec(int[][] pieces, boolean[] used, int placed, int[] order) {
    // Base case: all pieces placed, we have a solution
    if (placed >= NUM_PIECES) {
      printOrder(order);
      return;
    }

    // While there is an unused piece
    int i = 0;
    while (used[i]) {
      // Piece the next available piece and place it, continue on...

      // Check if the placement is valid
      if (isValid(pieces, order))
        solveRec(pieces, used, placed + 1, order);
      else {
        System.out.println("No solution.");
        return;
      }

      i++;
    }


    //
    // for (i = 0; i < pieces.length; i++) {
    //   // Pick piece
    //   int[] piece = pieces[i];
    //
    //   // Rotate it so that 1 is on the northern face (i.e., top of the hexagon)
    //   rotatePiece(pieces[i], 1, Position.north.ordinal());
    //   System.out.println("Piece #" + i + " rotated: " + Arrays.toString(pieces[i]));
    //
    //   // Mark the current starting piece as used
    //   usedPieces[i] = true;
    //   System.out.println(Arrays.toString(usedPieces));
    //
    //
    //   // Place piece down in center position
    //
  }


}
