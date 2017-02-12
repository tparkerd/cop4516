import java.util.*;

public class longpath {
  // public static final boolean DEBUG = true;
  public static final int oo = 999999999; // I forgot that if you try to add to Integer.MAX_VALUE, it overflows -_-
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
      // For each street...
      for (int j = 0; j < edgeCount; j++) {
        // Make a connection to two cities via said street or are these just the intersections...

        int s = stdin.nextInt();  // Starting intersection
        int d = stdin.nextInt();  // Detination intersection
        int m = stdin.nextInt();  // Number of minutes to travel (weight)

        Edge tmpEdge = new Edge(s, d, -m);
        Edge tmpEdgeMin = new Edge(s, d, m);
        E.add(tmpEdge);
        Emin.add(tmpEdgeMin);
      }

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
        if ((closest[e.s] + e.w) < closest[e.d]) {
          closest[e.d] = closest[e.s] + e.w;
        }
      }
    }


    // Relax edges
    for (int i = 0; i < V - 1; i++) {
      // For every edge, try to correct the longest distance to it
      for(Edge e : E) {
        if ((longest[e.s] + e.w) < longest[e.d]) {
          longest[e.d] = longest[e.s] + e.w;
        }
      }
    }

    result[0] = closest[closest.length - 1];
    result[1] = -longest[longest.length - 1];

    return result;
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
