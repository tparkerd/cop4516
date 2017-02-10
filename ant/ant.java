import java.util.*;

public class ant {
  public static final boolean DEBUG = false;
  public static Edge[] graph;
  public static void main(String[] args) {

    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();               // n
    // For each test case (campus)...
    for (int i = 0; i < nCases; i++) {
      int nHills = stdin.nextInt();             // h
      int nTunnels = stdin.nextInt();           // t

      if (DEBUG) System.out.println("Start\tEnd\tWeight");

      // Create the graph for each case
      graph = new Edge[nTunnels];
      // For each tunnel (connection/edge)
      for (int j = 0; j < nTunnels; j++) {
        // Add the connection
        // B/c we are using the number of hills and tunnels are the size of
        // their respective arrays, shift over the indices of these by ones
        // as not to go out of bounds
        int x = stdin.nextInt() - 1;            // Starting node/vertex
        int y = stdin.nextInt() - 1;            // Ending node/vertex
        int d = stdin.nextInt();                // Distance/Cost/Weight

        if (DEBUG) System.out.printf("%d\t%d\t%d\n", x, y, d);

        // Add the edge to the graph
        graph[j] = new Edge(x, y, d);
      }
      int result = kruskalAlgo.solve(graph, nHills);
      System.out.printf("Campus #%d: ", i + 1);
      if (result == -1)
        System.out.println("I'm a programmer, not a miracle worker!");
      else
        System.out.println(result);
    }
  }
}

// Edge class based on Guitar code
class Edge implements Comparable<Edge> {
  public int start;
  public int end;
  public int cost;

  public Edge(int s, int e, int c) {
    this.start = s;
    this.end = e;
    this.cost = c;
  }

  @Override
  public int compareTo(Edge e) {
    return this.cost - e.cost;
  }
}

// Krusal following Guitar example
class kruskalAlgo {
  public static int solve(Edge[] graph, int n) {
    // Sort the array
    Arrays.sort(graph);

    Dset trees = new Dset(n);
    int nEdges = 0, result = 0;

    // Assume the edges are in order
    for (int i = 0; i < graph.length; i++) {
      // Try to merge the two trees
      boolean merged = trees.union(graph[i].start, graph[i].end);
      if(!merged) continue; // if they couldn't merge, skip

      nEdges++;
      result += graph[i].cost;
      if (nEdges == n - 1) break; // reached the end
    }

    if (nEdges == n - 1)
      return result;
    return -1;
  }
}

class Dset {
  public int[] parent;
  public int[] height;
  public int n;

  public Dset(int size) {
    this.parent = new int[size];
    this.height = new int[size];
    for (int i = 0; i < size; i++)
      parent[i] = i;
  }

  public int find(int v) {
    // Base case: if it is itself, return itself
    if (parent[v] == v) return v;
    parent[v] = find(parent[v]);
    height[v] = 1;
    return parent[v];
  }

  public boolean union(int start, int end) {
    int first = find(start);
    int second = find(end);

    // Can't union the same vertex to itself
    if (first == second) return false;

    // Connect them such that the distance is minimized
    if (height[second] < height[first]) parent[second] = first;
    else if (height[first] < height[second]) parent[first] = second;
    else {
      parent[second] = first;
      height[first]++;
    }
    return true;
  }
}
