import java.util.Scanner;
import java.util.ArrayList;

public class puzzle {
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    // Number of Test Cases
    int nCases = stdin.nextInt();


    for (int i = 0; i < nCases; i++) {
      long[] states = new long[10000];
      // Get the board
      // Since the values are only up to 8, I should be able to store them in just 4 bits
      long curState = 0;
      for (int j = 0; j < 8; j++) {
        curState = (curState << 4) + stdin.nextInt();
      }
      System.out.println(curState);
    }

  }
}

class State {
  long id;

  public State(long l) {
    this.id = l;
  }

  // Debug display of the board
  public void view() {
    

  }
}
