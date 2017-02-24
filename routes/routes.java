import java.util.*;

public class routes {
  public static int[][] G;
  public static int[][] D;
  public static int[][] P;
  public static int oo = 999999;
  public static int V;
  public static int E;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();

    // For each case...
    for (int i = 0; i < nCases; i++) {

      V = stdin.nextInt();     // num of intersections (n)
      E = stdin.nextInt();     // num of roads connecting the intersections (m)
      int q = stdin.nextInt(); // num of off-campus housing locations (q)

      G = new int[V][V];

      // Initialize the graph
      for (int j = 0; j < V; j++) {
        for (int k = 0; k < V; k++) {
          if (j == k)
            G[j][k] = 0;
          else
            G[j][k] = oo;
        }
      }

      // For each road, get the start, end, and weight
      for (int j = 0; j < E; j++) {
        int u = stdin.nextInt(); // start
        int v = stdin.nextInt(); // end
        int c = stdin.nextInt(); // weight
          G[u - 1][v - 1] = c;
      }

      floyd();

      // For each off-campus location to query
      for (int j = 0; j < q; j++) {
        int v = stdin.nextInt(); // end
        System.out.println(sumPath(0, v - 1));
      }


    }
  }

  public static void floyd() {
    // Predecessor graph
    P = new int[V][V];
    for (int i = 0; i < V; i++) {
      for (int j = 0; j < V; j++) {
        if (G[i][j] < oo)
          P[i][j] = i;
        else
          P[i][j] = -1;
      }
    }

    // Copy the graph to a distance graph
    D = new int[V][V];
    for (int i = 0; i < V; i++) {
      for (int j = 0; j < V; j++) {
        D[i][j] = G[i][j];
      }
    }

    // Floyd it!
    for (int k = 0; k < V; k++) {
      for (int i = 0; i < V; i++) {
        for (int j = 0; j < V; j++) {
          D[i][j] = Math.min(D[i][j], D[i][k] + D[k][j]);
          P[i][j] = P[k][j];
        }
      }
    }
  }

  public static int sumPath(int source, int target) {
    if (D[source][target] == oo)
      return -1;

    int total = 0;

    // LinkedList<Integer> path = new LinkedList<Integer>();
    // path.addFirst(target);
    while (target != source) {
      total += D[source][target];
      target = P[source][target];
      // path.addFirst(target);
    }
    return total;
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
    return "(" + this.s+", " +  this.d + ", " + this.w + ")";
  }
}
