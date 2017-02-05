import java.util.Scanner;
import java.util.Arrays;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;

public class calc {
  public static final boolean DEBUG = true;
  public static final int MAX_VALUE = 50;
  public static Datum data;

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
        data = new Datum(a, b, c);
        if (DEBUG) System.out.println(data.toString());
        t = stdin.nextInt();

        System.out.println(t + "\t" + solveBFS(t));
      }
    }


  public static int solveBFS(int s) {
    if (DEBUG) System.out.println("Input: " + s);
    // Nothing has been visited
    int[] visited = new int[MAX_VALUE];
    Arrays.fill(visited, -1);

    // Make a queuehuehuehue
    Queue<Pair> q = new LinkedList<Pair>();

    // Add the starting value to the queue
    q.add(new Pair(s, 0));

    // We've already tried this position, and it's distance zero from the target
    // The target is to get perform operations to get to the target value
    // NOTE(timp): I'm not sure why we are making this as zero
    visited[s] = 0;

    int nVisited = 1;
    int max = 0;
    int curLength = 0;

    // The actual BFS
    while(nVisited < MAX_VALUE - 1) {

      // Get next item and its neighbors
      Pair p = q.poll();
      if (DEBUG) System.out.println("Next value to try: " + p.toString());
      ArrayList<Integer> next = getNext(p.value);
      if (DEBUG) System.out.println("neighbors: " + next.toString());

      // Try to enqueue
      for (int i = 0; i < next.size(); i++) {
        // Get the next case (add, divide, or multiply)
        int item = next.get(i);

        // Item is in range, process it
        if (item > 0 && item < MAX_VALUE && visited[item] == -1) {
          if (DEBUG) System.out.println("Testing item!" + item);
          visited[item] = p.distance + 1;
          q.add(new Pair(item, visited[item]));
          nVisited++;
          max = visited[item];
        }
        // Just in case...
        else if (item == 0 || item >= MAX_VALUE) {
          q.add(new Pair(item, p.distance + 1));
        }
      }

    }
    return max;
  }


  public static ArrayList<Integer> getNext(int x) {
    ArrayList<Integer> result = new ArrayList<Integer>();

    // Case 1: Add A
    result.add((x + data.a) % MAX_VALUE);

    // Case 2: Integer Division by C
    result.add((x / data.c) % MAX_VALUE);

    // Case 3: Multiply by B
    result.add((x * data.b) % MAX_VALUE);

    // if (x < MAX_VALUE/data.b+1) result.add((data.b * x) % MAX_VALUE);

    return result;
  }

}

class Pair {
  public int value;
  public int distance;

  public Pair(int v, int d) {
    this.value = v;
    this.distance = d;
  }

  @Override
  public String toString() {
    return (this.value + " (" + this.distance + ")");
  }
}

class Datum {
  public int a;
  public int b;
  public int c;

  public Datum(int a, int b, int c) {
    this.a = a;
    this.b = b;
    this.c = c;
  }

  @Override
  public String toString() {
    return ("Data: " + a + " " + b + " " + c);
  }
}
