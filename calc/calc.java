import java.util.Scanner;
import java.util.Arrays;
import java.util.Queue;
import java.util.LinkedList;

public class calc {
  public static final boolean DEBUG = true;
  public static final int MAX_VALUE = 1000000;

  public static void main(String[] args) {

    Scanner stdin = new Scanner(System.in);

    // For each case...
      int nCases = stdin.nextInt();
      for (int i = 0; i < nCases; i++) {
        int a, b, c, t;
        // Get the three values used to calculate
        a = stdin.nextInt();
        b = stdin.nextInt();
        c = stdin.nextInt();
        t = stdin.nextInt();

        // Nothing has been visited
        int[] solution = new int[MAX_VALUE];
        Arrays.fill(solution, -1);

        Queue<Pair> q = new LinkedList<Pair>();


      }



    }
}

class Pair {
  public int value;
  public int distance;

  public Pair(int v, int d) {
    this.value = v;
    this.distance = d;
  }
}
