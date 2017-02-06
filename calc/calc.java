import java.util.Scanner;
import java.util.Arrays;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;

public class calc {
  public static final int MAX_VALUE = 1000000;
  public static int target;
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

        // Get the target answer
        target = stdin.nextInt();
        // Get all the solutions for this case
        int[] solutions = solveBFS();

        // Display the answer
        System.out.println(solutions[target]);
      }
    }


  public static int[] solveBFS() {
    // Nothing has been visited
    int[] visited = new int[MAX_VALUE + 1];
    Arrays.fill(visited, -1);

    // Make a queuehuehuehue
    Queue<Pair> q = new LinkedList<Pair>();

    // Add the starting value to the queue
    q.add(new Pair(0, 0));

    // There is not distance from the starting point
    visited[0] = 0;

    // The actual BFS
    while(!q.isEmpty()) {
      // Get next item and its neighbors
      Pair p = q.poll();
      ArrayList<Integer> next = getNext(p.value);

      // Try to enqueue
      for (int i = 0; i < next.size(); i++) {
        // Get the next case (add, divide, or multiply)
        int item = next.get(i);

        // Make sure that item is in bounds and not yet visited
        if (visited[item] == -1) {
          visited[item] = p.distance + 1;
          if(item == target)
            return visited;
          else
            q.add(new Pair(item, visited[item]));
        }
      }
    }
    return visited;
  }
  public static ArrayList<Integer> getNext(int x) {
    ArrayList<Integer> result = new ArrayList<Integer>();

    // Case 1: Add A
    result.add((x + data.a) % MAX_VALUE);

    // Case 2: Multiply by B
    result.add((x * data.b) % MAX_VALUE);

    // Case 3: Integer Division by C
    result.add((x / data.c) % MAX_VALUE);

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
