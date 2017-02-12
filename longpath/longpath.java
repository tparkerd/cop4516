import java.util.*;

public class longpath {
  public static final boolean DEBUG = true;
  public static final int oo = Integer.MAX_VALUE;
  public static final int ooo = Integer.MIN_VALUE;
  public static int[][] G;
  public static int V;
  public static ArrayList<Edge> E;
  public static ArrayList<Edge> Emin;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();

    // For each case...
    for (int i = 0; i < nCases; i++) {
      V = stdin.nextInt();  // # of Intersections
      E = new ArrayList<Edge>();
      Emin = new ArrayList<Edge>();
      int edgeCount = stdin.nextInt(); // # of One Way Streets
      if (DEBUG) System.out.printf("V: %d\tE: %d\n", V, edgeCount);

      if (DEBUG) System.out.println("Start\tDest\tWeight");
      // For each street...
      for (int j = 0; j < edgeCount; j++) {
        // Make a connection to two cities via said street or are these just the intersections...

        int s = stdin.nextInt();  // Starting intersection
        int d = stdin.nextInt();  // Detination intersection
        int m = -stdin.nextInt();  // Number of minutes to travel (weight)

        Edge tmpEdge = new Edge(s, d, m);
        Edge tmpEdgeMin = new Edge(s, d, -m);
        E.add(tmpEdge);
        Emin.add(tmpEdgeMin);

        // if (DEBUG) System.out.println(s + "\t" + d + "\t" + m);
        if (DEBUG) System.out.println(tmpEdge);
      }

      if (DEBUG) System.out.println(E.toString());
      if (DEBUG) System.out.println(Emin.toString());

      // New graph to put all the connections
      G = new int[V][V];

      // Initalize all of them to infinity
      // for (int j = 0; j < V; j++) {
      //   for (int k = 0; k < V; k++) {
      //     if (j == k)
      //       G[j][k] =  0;
      //     else
      //       G[j][k] = oo;
      //   }
      // }

      printGraph();


      int[] answers = bellmanFord();
      System.out.printf("%d %d\n", answers[0], answers[1]);

    }
  }

  public static int[] bellmanFord() {
    int[] result = new int[2];
    // Distance array of estimates
    int[] closest = new int[V];
    int[] longest = new int[V];

    for (int i = 0; i < longest.length; i++) {
      closest[i] = oo;
      longest[i] = oo;
    }

    // We know the closest/longest to the source is nothing
    closest[0] = 0;
    longest[0] = 0;

    // Relax edges
    for (int i = 0; i < V - 1; i++) {
      // For every edge, try to correct the closest distance to it
      for(Edge e : Emin) {
        if ((closest[e.s] + e.w) < closest[e.d])
        closest[e.d] = closest[e.s] + e.w;
      }
    }

    if (DEBUG) System.out.println("Closest: "+ Arrays.toString(closest));

    // Relax edges
    for (int i = 0; i < V - 1; i++) {
      // For every edge, try to correct the longest distance to it
      for(Edge e : E) {
        if ((longest[e.s] + e.w) < longest[e.d])
          longest[e.d] = longest[e.s] + e.w;
      }
    }

    if (DEBUG) System.out.println("Longest: "+ Arrays.toString(longest));

    result[0] = closest[closest.length - 1];
    result[1] = -longest[longest.length - 1];

    return result;
  }

  public static void printGraph() {
    if (DEBUG) System.out.println("Adjacency Matrix\n---------------------------");
    for (int i = 0; i < V; i++) {
      for (int j = 0; j < V; j++) {
        if (G[i][j] != oo) {
          if (DEBUG) System.out.print(G[i][j] + "\t");
        } else {
          if (DEBUG) System.out.print('\u221E' + "\t");
        }
      }
      if (DEBUG) System.out.println();
    }
    if (DEBUG) System.out.println("---------------------------");
  }
}

class Edge {
  int s;
  int d;
  int w;

  public Edge(int s, int d, int w) {
    this.s = s;
    this.d = d;
    this.w = w;
  }

  @Override
  public String toString() {
    return "("+s+", "+d+", "+w+")";
  }

  public void negate() {
    this.w = -this.w;
  }
}
