import java.util.Scanner;
import java.util.ArrayList;

public class puzzle {
  public static final boolean DEBUG = true;
  public static final int SIZE = 9;
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    // Number of Test Cases
    int nCases = stdin.nextInt();


    for (int i = 0; i < nCases; i++) {
      long[] states = new long[10000];
      // Get the board
      // Since the values are only up to 8, I should be able to store them in just 4 bits
      // Read in the initial state of the board
      State tmpState = new State(0);
      for (int j = 0; j < SIZE; j++)
        tmpState.id = (tmpState.id << 4) + stdin.nextInt();

      // Debug preview of the board
      if (DEBUG) tmpState.view();


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
  public static final long SOLVED_STATE = 4886718336L;

  public State(long l) {
    this.id = l;
  }

  // Debug display of the board
  public void view() {
    long[] tmp = new long[puzzle.SIZE];
    for (int i = 0; i < puzzle.SIZE; i++) {
      tmp[puzzle.SIZE - 1 - i] = 0xF & (this.id >> (i * 4));
    }
    System.out.printf("[%d][%d][%d]\n[%d][%d][%d]\n[%d][%d][%d]\n",
                        tmp[0], tmp[1], tmp[2],
                        tmp[3], tmp[4], tmp[5],
                        tmp[6], tmp[7], tmp[8]);
  }

  public boolean isSolved() {
    return (this.id == SOLVED_STATE);
  }
}
