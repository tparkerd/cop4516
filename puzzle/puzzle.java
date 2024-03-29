import java.util.*;

public class puzzle {
  public static final int SIZE = 9;
  public static final long SOLVED_STATE = 4886718336L;
  public static final int[] DX = {0,-1,0,1};
  public static final int[] DY = {1,0,-1,0};
  public static boolean solved;

  public static HashMap<Long, Integer> solutionSet;


  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    // Number of Test Cases
    int nCases = stdin.nextInt();

    // Record all solutions
    solutionSet = new HashMap<Long, Integer>();

    // Create a queue to run the BFS on
    Queue<Pair> q = new LinkedList<Pair>();

    q.add(new Pair(SOLVED_STATE, 0));
    int nMovesMade = 0;

    while (!q.isEmpty()) {
      Pair p = q.poll();
      solutionSet.put(p.stateId, p.distance);

      ArrayList<Pair> next = getNext(p);

      // Try to enqueue
      for (int k = 0; k < next.size(); k++) {
        // Get the next case (add, divide, or multiply)
        Pair item = next.get(k);

        // Make sure that item is in bounds and not yet visited
        if (!solutionSet.containsKey(item.stateId)) {
          item.distance = p.distance + 1;
          q.add(new Pair(item.stateId, item.distance));
          nMovesMade++;
        }
      }
    }

    for (int i = 0; i < nCases; i++) {
      // Get the board
      // Since the values are only up to 8, I should be able to store them in just 4 bits
      // Read in the initial state of the board

      long boardInQuestion = 0;
      for (int j = 0; j < SIZE; j++)
        boardInQuestion = (boardInQuestion << 4) + stdin.nextInt();

      System.out.println(solutionSet.get(boardInQuestion));
    }
  }
  public static ArrayList<Pair> getNext(Pair p) {
    // Result variable to always be returned
    ArrayList<Pair> list = new ArrayList<Pair>();

    // Get a more manageable version of the board
    long[][] board = toBoard(p.stateId);

    // Find the position of ZERO
    int zeroRow = -1, zeroCol = -1;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (board[i][j] == 0) {
          zeroRow = i;
          zeroCol = j;
        }
      }
    }

    // For each of the possible moves
    for (int i = 0; i < DY.length; i++) {
        // Create tmp board to make moves on
        long[][] tmpBoard = toBoard(p.stateId);
        // Check bounds for next move
        if (((DY[i] + zeroRow) >= 0 ) && // Not above of the board
            ((DY[i] + zeroRow) <= 2 ) && // Not below the board
            ((DX[i] + zeroCol) >= 0 ) && // Not left of the board
            ((DX[i] + zeroCol) <= 2)     // Not right of the board
          )
          // Make the move on a temp copy of the board, and then add its pair to list of upcoming moves
        {
          // Make the next move by swapping ZERO and the value that's in bounds
          long tmp = tmpBoard[DY[i] + zeroRow][DX[i] + zeroCol];
          tmpBoard[DY[i] + zeroRow][DX[i] + zeroCol] = 0;
          tmpBoard[zeroRow][zeroCol] = tmp;

          // Get its id value
          long tmpId = toId(tmpBoard);
          // Add it to the next possible moves
          list.add(new Pair(tmpId, p.distance + 1));
        }
    }
    return list;
  }

  public static long[][] toBoard(long id) {
    // Temporary board to always return
    long[][] board = new long[3][3];

    // Value to hold the each 4 bits that make up the id number
    long[] list = new long[SIZE];
    // Parse out each of the values into the list
    for (int i = 0; i < SIZE; i++)
      list[SIZE - 1 - i] = 0xF & (id >> (i * 4));

    // Because the bits are read in backwards, we need to reverse them
    list = reverse(list);

    // Insert each of the values into their appropriate slots
    for (int i = 0; i < SIZE; i++) {
      //     ROW    COL
      board[i / 3][i % 3] = list[SIZE - 1 - i];
    }
    return board;
  }

  public static long toId(long[][] b) {
    // Temporary id to always return
    long id = 0;

    // For each element, get the value, add it to the id and shift up by 4
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        id = id << 4;
        id += b[i][j];
      }
    }
    return id;
  }

  public static void displayBoard(long[][] board) {
    System.out.println("\nCurrent state\n-----");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        System.out.print(board[i][j] + " ");
      }
      System.out.println();
    }
    System.out.println("-----");
  }

  public static long[] reverse(long [] a) {
    for (int i = 0; i < (a.length - 1) / 2; i++) {
      long tmp = a[i];
      a[i] = a[a.length - i - 1];
      a[a.length - i - 1] = tmp;
    }
    return a;
  }
}

class Pair {
  public long stateId;
  public int distance;

  public Pair(long v, int d) {
    this.stateId = v;
    this.distance = d;
  }

  @Override
  public String toString() {
    long[][] tmp = puzzle.toBoard(this.stateId);
    puzzle.displayBoard(tmp);
    return this.stateId + " (" + this.distance + ")";
  }
}
