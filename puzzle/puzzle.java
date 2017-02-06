import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashSet;
import java.util.Collections;
import java.util.Arrays;

public class puzzle {
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";

  public static final boolean DEBUG = true;
  public static final int SIZE = 9;
  public static final long SOLVED_STATE = 4886718336L;

  public static HashSet<sPair> solutionSet;


  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    // Number of Test Cases
    int nCases = stdin.nextInt();

    // Record all solutions
    solutionSet = new HashSet<sPair>();

    // Automatically insert the first solution
    solutionSet.add(new sPair(new State(SOLVED_STATE), 0));

    for (int i = 0; i < nCases; i++) {
      // Get the board
      // Since the values are only up to 8, I should be able to store them in just 4 bits
      // Read in the initial state of the board
      long startingBoard = 0;
      for (int j = 0; j < SIZE; j++)
        startingBoard = (startingBoard << 4) + stdin.nextInt();

      // Create the starting state of the board
      State tmpState = new State(tmp);

      // Debug preview of the board
      if (DEBUG) tmpState.view();

      // // Create a queue to run the BFS on
      // Queue<sPair> q = new LinkedList<sPair>();
      //
      // q.add(new sPair(tmpState, 0));
      //
      // sPair p = q.poll();

      // while (!p.state.isSolved() && !q.isEmpty()) {
      //
      //   p = q.poll();
      //   ArrayList<State> next = p.state.getNextMoves();
      //
      //   // Try to enqueue
      //   for (int k = 0; k < next.size(); k++) {
      //     // Get the next case (add, divide, or multiply)
      //     State item = next.get(k);
      //
      //     // Make sure that item is in bounds and not yet visited
      //     if (!solutionSet.contains(item)) {
      //       item.distance = p.distance + 1;
      //       q.add(new sPair(item, item.distance));
      //     }
      //   }
      // }


      // We know it is solved when it is in the correct
      if(tmpState.isSolved())
        System.out.println("Solved!");
      else
        System.out.println("Not solved yet.");
    }

  }
}

class State {
  public long id;
  public long[][] board;
  public int distance;


  public State(long l) {
    // Save ID hash value
    this.id = l;
    if (puzzle.DEBUG) System.out.println(this.id);

    this.distance = 0;

    // Convert this to actual board
    board = new long[3][3];

    long[] tmp = new long[puzzle.SIZE];
    for (int i = 0; i < puzzle.SIZE; i++)
      tmp[puzzle.SIZE - 1 - i] = 0xF & (this.id >> (i * 4));

    tmp = reverse(tmp);

    for (int i = 0; i < puzzle.SIZE; i++) {
      int row = i / 3;
      int col = i % 3;
      this.board[row][col] = tmp[puzzle.SIZE - 1 - i];
    }
  }

  // Debug display of the board
  public void view() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        System.out.print(this.board[i][j] + " ");
      }
      System.out.println();
    }
  }

  public ArrayList<State> getNextMoves() {
    ArrayList<State> result = new ArrayList<State>();
    // Figure out which are the next valid moves to enqueue




    return result;
  }

  public long[] reverse(long [] a) {
    for (int i = 0; i < (a.length - 1) / 2; i++) {
      long tmp = a[i];
      a[i] = a[a.length - i - 1];
      a[a.length - i - 1] = tmp;
    }
    return a;
  }

  public boolean isSolved() {
    return (this.id == puzzle.SOLVED_STATE);
  }
}

class sPair {
  State state;
  int distance;

  public sPair(State s, int d) {
    this.state = s;
    this.distance = d;
  }

  @Override
  public String toString() {
    return this.state.id + " ( " + this.state.distance + ")";
  }
}
