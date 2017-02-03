import java.util.Scanner;
import java.util.Arrays;

public class minion {
  public static final boolean DEBUG = true;
  public static Graph graph;
  public String[] obstacles;


  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    // Get the number of cases
    int nCases = Integer.parseInt(stdin.nextLine());
    for (int i = 0; i < nCases; i++) {
      graph = new Graph();
      // Get the trials the ninja is unable to accomplish
      int nObstacles = Integer.parseInt(stdin.nextLine());
      String[] obstacles = new String[nObstacles];
      for (int j = 0; j < nObstacles; j++) {
        obstacles[i] = stdin.nextLine();
        if (DEBUG) System.out.println("Obstacle " + (j + 1) + ": " + obstacles[i]);
      }

      // Get the number of locations and routes
      String tmp = stdin.nextLine();
      String[] info = tmp.split(" ");
      int nLocations = Integer.parseInt(info[0]);
      int nRoutes = Integer.parseInt(info[1]);

      if (DEBUG) System.out.println("Location # " + nLocations);
      if (DEBUG) System.out.println("Route # " + nRoutes);

      // Get the connections between the nodes
      for (int j = 0; j < nRoutes; j++) {
        String tmp2 = stdin.nextLine();
        String[] nodeString = tmp2.split(" ");
        // Create a node
        Node tmpNode = new Node(Integer.parseInt(nodeString[0]),
                                Integer.parseInt(nodeString[1]),
                                nodeString[2]);
        if (DEBUG) System.out.println(tmpNode);

      }
    }
  }
}

class Node {
  int a;
  int b;
  String obstacle;

  public Node(int a, int b, String o) {
    this.a = a;
    this.b  = b;
    this.obstacle = o;
  }

  @Override
  public String toString() {
    return "Node: " + this.a + "\t" + this.b + "\t" + this.obstacle;
  }

}

class Graph {

  public Graph() {

  }

}


////////// PSEDUO


// dfs(v, used)
//   used[v] = true
//
//   for (u : neighbors)
//     if (u.isNotUsed())
//       dfs(u, used)
//
// Storing the graph
//
// ArrayList[] graph = new ArrayList...
// for each vertex (i < n)
//   graph[i] = new ArrayList<Integer>();
//
//   int edge = stdin
//
//   for (j = 0; j < edge; j++)
//     int v1 = stdin
//     int v2 = stdin
//
//     graph[v1].add(v2)
//     graph[v2].add(v1)
