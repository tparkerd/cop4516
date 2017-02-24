import java.util.*;

public class routes2 {
  public static final boolean DEBUG = true;
  public static int[][] graph;
  public static int V;
  public static int E;
  public static ArrayList<Edge> edges;
  // public static Edge[] edges;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();

    // For each case...
    for (int i = 0; i < nCases; i++) {

      V = stdin.nextInt();     // num of intersections (n)
      if (DEBUG) System.out.println(V);
      E = stdin.nextInt(); // num of roads connecting the intersections (m)
      int q = stdin.nextInt(); // num of off-campus housing locations (q)

      edges = new ArrayList<Edge>();

      // For each road, get the start, end, and weight (weight is positive)
      for (int j = 0; j < E; j++) {
        int u = stdin.nextInt(); // start
        int v = stdin.nextInt(); // end
        int c = stdin.nextInt(); // weight

        edges.add(new Edge(u, v, c));
      }

      // For each off-campus location to query
      for (int j = 0; j < q; j++) {
        int u = 1; // start - always connects at intersection 1
        int v = stdin.nextInt(); // end

        if (DEBUG) System.out.println(Arrays.toString(bellmanFord(v - 1)));

      }


    }
  }


  public static int[] bellmanFord(int source) {
    int[] estimates = new int[V];

    for (int i = 0; i < estimates.length; i++) {
      estimates[i] = 9999999;
    }
    if (DEBUG) System.out.println(Arrays.toString(estimates));

    // Distance to itself
    estimates[source] = 0;

    for (int i = 0; i < V - 1; i++) {
      for (Edge e : edges) {
        if (DEBUG) System.out.println(e);
        if (estimates[e.s - 1] + e.w < estimates[e.d - 1]) {
          estimates[e.d - 1] = estimates[e.s - 1] + e.w;
        }
      }
    }

    return estimates;
  }
}


class Edge2 {
  int s;
  int d;
  int w;

  public Edge2(int s, int d, int w) {
    this.s = s;
    this.d = d;
    this.w = w;
  }

  @Override
  public String toString() {
    return "(" + this.s+", " +  this.d + ", " + this.w + ")";
  }
}
