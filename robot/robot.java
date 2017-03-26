import java.util.*;
import java.math.*;
import java.text.*;

public class robot {
    static final boolean DEBUG = true;
    static final int oo = (int) 1e9;
    static int n;
    static ArrayList<Edge>[] g;

  @SuppressWarnings("unchecked")
  public static void main(String[] args) {

    int q = 101;

    // Create a big ole box to store all the possible distances
    // Each index is the value of either the X or Y position on an
    // everyday Cartesian grid
    double[][][][] d = new double[q][q][q][q];
    for (double[][][] a : d)
      for (double[][] b : a)
        for (double[] c : b)
          Arrays.fill(c, 0);

    // Precompute all the distances of the possible points
    // NOTE(timp): this could probably be optimized by storing this in a
    // 2-D array of just the differences between point A and point B
    // So the points (2, 2) -> (5, 6) and (3, 3) -> (6, 7) would both
    // just store their distances in the position (3, 4).
    for (int x1 = 0; x1 < q; x1++)
      for (int y1 = 0; y1 < q; y1++)
        for (int x2 = 0; x2 < q; x2++)
          for (int y2 = 0; y2 < q; y2++) {
            int xDist = Math.abs(x2 - x1);
            int yDist = Math.abs(y2 - y1);
            // BigDecimal = new BigDecimal(Math.sqrt( (xDist * xDist) + (yDist * yDist) ))
            // .setScale(2, RoundingMode.HALF_EVEN);
            d[x1][y1][x2][y2] = Math.sqrt( (xDist * xDist) + (yDist * yDist) );
          }

    // Yep! It totally works that way, and you can precompute and store
    // it faster this way
    double grid[][] = new double[q][q];
    for (int xdist = 0; xdist < q; xdist++) {
      for (int ydist = 0; ydist < q; ydist++) {
        grid[xdist][ydist] = Math.sqrt( (xdist * xdist) + (ydist * ydist) );
      }
    }


    // Get the distance from point (x1, y1) and (x2, y2);
    if (DEBUG) System.out.println(d[3][20][10][62]);
    if (DEBUG) System.out.println(d[0][0][100][100] + 20);
    // First point S, (0,0) is always 1 second in order to turn, and then
    // Another second for every node hit, but the end does not add one b/c it
    // does not need to turn to face another node
    if (DEBUG) System.out.println(d[0][0][50][50] + d[50][50][100][100] + 1 + 1);
    // God f'ing damn it, finally it lets me keep my damn trailing zeros!
    if (DEBUG) System.out.printf("%s\n", (new DecimalFormat("#.000").format(grid[7][42])));


    if (DEBUG) System.out.println((new Target(0,0,0,0)).getDistanceTo(new Target(1,100,100,0)));



      // Scanner stdin = new Scanner(System.in);
      // n = stdin.nextInt();
      //
      // while (n != 0) {
      //   if (DEBUG) System.out.println("N: " + n);
      //   g = new ArrayList[101];
      //   for (ArrayList list : g) list = new ArrayList<Integer>();
      //   // Start is always 0,0 and finish at 100, 100
      //   g[0].add(new Edge(0, 0));   // Add the starting point
      //   g[0].add(new Edge(100, 100)); // Add the final target, takes 100s to get there
      //
      //   // For each target, add it to the graph
      //   for (int i = 0; i < n; i++) {
      //
      //   }
      //
      //   // Next case
      //   n = stdin.nextInt();
      // }
  }











  // Finds shortest distance from s to d
  public static double dijkstras(int s, int d) {

    // Set up the priority queue.
    boolean[] visited = new boolean[n];
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
}
