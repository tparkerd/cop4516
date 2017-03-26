import java.util.*;

public class robot {
  static final int oo = (int) 1e9;
  static final int N = 100;
  public static ArrayList<Edge>[] g;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int n = stdin.nextInt();

    // For each test case...
    while (n != 0) {
      ArrayList<Target> list = new ArrayList<Target>();
      // Add the starting 'target'
      // NOTE(timp): I'm not sure if the penalty should be 0 or 1.
      //             I'm going to assume zero.
      list.add(new Target(0, 0, 0, 0));

      // Remember all of the targets on the grid, as any target connects to
      // any following targets
      for (int i = 1; i <= n; i++) {
        int x = stdin.nextInt();
        int y = stdin.nextInt();
        int pen = stdin.nextInt();
        list.add(new Target(i, x, y, pen));
      }
      // Add the destination target
      // This will later be used as the destination (d) for dijkstra's
      list.add(new Target(list.size(), N, N, 0));

      // Initialize empty graph
      g = new ArrayList[N];
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
          // Overall weight is:
          //    Distance between last target and this one, sum of all previous
          //    penalty points, and one second to turn towards the next target
          int edge = list.get(j).id;
          double distToNextTarget = list.get(i).getDistanceTo(list.get(j));
          g[i].add(new Edge(edge, distToNextTarget + penaltyRunningTotal + 1));
          // Update running penalty total to include the last used target,
          // had it been skipped.
          penaltyRunningTotal += list.get(j).pen;
        }
      }

      // Answer!
      // The ending target is actually stored in the last item of the
      // original list of targets.
      double answer = dijkstras(0, list.size() - 1);
      // ...plus annoying formatting. As is, it includes trailing zeros.
      // System.out.printf("%s\n", (new DecimalFormat("#.000").format(answer)));
      System.out.printf("%.3f\n", answer);

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
      if (visited[at.e]) continue;
      visited[at.e] = true;

      // We made it, return the distance.
      if (at.e == d) return at.w;


      // Enqueue all the neighboring edges.
      for (Edge adj : g[at.e])
        if (!visited[adj.e]) pq.add(new Edge(adj.e, adj.w + at.w));
    }
    return oo;
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
    int xdist = this.x - next.x;
    int ydist = this.y - next.y;
    return Math.sqrt( (xdist * xdist) + (ydist * ydist) );
  }
}
