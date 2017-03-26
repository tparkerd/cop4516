import java.util.*;
import java.math.*;
import java.text.*;

public class robot {
  static final boolean DEBUG = false;
  static final int oo = (int) 1e9;
  static final int N = 101;
  public static ArrayList<Edge>[] g;

  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int n = stdin.nextInt();

    while (n != 0) {
      if (DEBUG) System.out.println("N: " + n);
      ArrayList<Target> list = new ArrayList<Target>();
      // Add the starting node
      list.add(new Target(0, 0, 0, 1));
      // Remember all of the target on the grid, as any target connects to
      // any target that follows it.
      for (int i = 1; i <= n; i++) {
        int x = stdin.nextInt();
        int y = stdin.nextInt();
        int pen = stdin.nextInt();
        if (DEBUG) System.out.printf("Add Target(%d, %d, %d, %d)\n", i, x, y, pen);
        list.add(new Target(i, x, y, pen));
      }
      // Add the final target
      if (DEBUG) System.out.println("List size: " + list.size());
      list.add(new Target(list.size(), 100, 100, 0));

      // DEBUG
      if (DEBUG) System.out.println("Show list");
      for (Target t : list)
        if (DEBUG) System.out.println(t);

      // Create graph
      g = new ArrayList[101];
      for (int i = 0; i < g.length; i++)
        g[i] = new ArrayList<Edge>();

      for (int i = 0; i < list.size(); i++) {
        // Keep track of penalty points for skipped targets and reset it
        // when you test from a later starting node
        int penaltyRunningTotal = 0;
        // Connect the current target to all that follow it
        // Making sure to update the penalty for each of the nodes skipped
        for (int j = i + 1; j < list.size(); j++) {
          // Add to graph
          // Overall weight is the...
          // Distance between last target and this one, sum of all previous
          // penalty points, and one second to turn towards the next target
          int edge = list.get(j).id;
          double distToNextTarget = list.get(i).getDistanceTo(list.get(j));
          if (DEBUG) System.out.printf("g[%d].add(new Edge(%d, %.3f + 1 + %d = %.3f));\n", i, edge, distToNextTarget, penaltyRunningTotal, distToNextTarget + 1 + penaltyRunningTotal);
          g[i].add(new Edge(edge, distToNextTarget + 1 + penaltyRunningTotal));
          penaltyRunningTotal += list.get(j).pen;
        }
      }

      // DEBUG: show graph
      if (DEBUG) System.out.println("Show graph");
      for (ArrayList<Edge> l : g)
        if (DEBUG) System.out.println(l.toString());


      // Answer!
      // God f'ing damn it, finally it lets me keep my damn trailing zeros!
      if (DEBUG) System.out.println(N - 1);
      double answer = dijkstras(0, list.size() - 1);
      System.out.printf("%s\n", (new DecimalFormat("#.000").format(answer)));


      // Next case
      n = stdin.nextInt();
    }
  }

  // Finds shortest distance from s to d
  public static double dijkstras(int s, int d) {

    // Set up the priority queue.
    boolean[] visited = new boolean[N];
    PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
    pq.add(new Edge(s, 0));

    // Go till empty.
    while (!pq.isEmpty()) {

      // Get the next edge.
      Edge at = pq.poll();
      if (DEBUG) System.out.println("EDGE ID: " + at.e + " D: " + d);
      if (visited[at.e]) continue;
      visited[at.e] = true;

      // We made it, return the distance.
      if (at.e == d) {
        if (DEBUG) System.out.println("Solution found " + at.w);
        return at.w;
      }

      // Enqueue all the neighboring edges.
      for (Edge adj : g[at.e])
        if (!visited[adj.e]) {
          pq.add(new Edge(adj.e, adj.w + at.w));
          if (DEBUG) System.out.printf("pq.add(new Edge(%d, %.3f + %.3f));\n", adj.e, adj.w, at.w);
        }
    }
    return oo;
  }

  public static double findDistance(int a, int b) {
    return Math.sqrt((a*a) + (b*b));
  }
}

// Stores where an edge is going to and its weight.
class Edge implements Comparable<Edge> {
  int e;
  double w;

  public Edge(int e, double w) {
    this.e = e;
    this.w = w;
  }

  public int compareTo(Edge o) {
    if (w > o.w)
      return 1;
    else if( w < o.w)
      return -1;
    else
      return 0;
  }

  public String toString() {
    return "("+e+", "+w+")";
  }
}

class Target {
  int id;
  int x;
  int y;
  int pen;

  public Target(int id, int x, int y, int pen) {
    this.id = id;
    this.x = x;
    this.y = y;
    this.pen = pen;
  }

  public double getDistanceTo(Target next) {
    int xdist = Math.abs(this.x - next.x);
    int ydist = Math.abs(this.y - next.y);
    return Math.sqrt( (xdist * xdist) + (ydist * ydist) );
  }

  public String toString() {
    return "::"+id+" ("+x+", "+y+") " + pen;
  }
}
