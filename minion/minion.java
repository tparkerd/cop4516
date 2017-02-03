import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

public class minion {
  public static final boolean DEBUG = true;
  public static ArrayList[] graph;
  public static String[] obstacles;
  public static int count;


  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    // Get the number of cases
    int nCases = Integer.parseInt(stdin.nextLine());
    for (int i = 0; i < nCases; i++) {
      // Get the trials the ninja is unable to accomplish
      int nObstacles = Integer.parseInt(stdin.nextLine());
      String[] obstacles = new String[nObstacles];
      if (DEBUG) System.out.println("Obstacles\n-------------------");
      for (int j = 0; j < nObstacles; j++) {
        obstacles[j] = stdin.nextLine();
        if (DEBUG) System.out.println((j + 1) + ")\t" + obstacles[j]);
      }

      if (DEBUG) System.out.println(Arrays.toString(obstacles));

      // Get the number of locations and routes
      String tmp = stdin.nextLine();
      String[] info = tmp.split(" ");
      int nLocations = Integer.parseInt(info[0]);
      int nRoutes = Integer.parseInt(info[1]);

      if (DEBUG) System.out.printf("# Locations\tTarget #\t# Routes\n    %d\t\t    %d\t\t    %d\n", nLocations, (nLocations - 1), nRoutes);

      // Instantiate the graph as an adjacency matrix
      graph = new ArrayList[nLocations];
      for (int j = 0; j < nLocations; j++) {
        graph[j] = new ArrayList<Node>();
      }

      // Get the connections between the nodes and insert them into the graph
      for (int j = 0; j < nRoutes; j++) {
        String tmp2 = stdin.nextLine();
        String[] nodeString = tmp2.split(" ");
        int a = Integer.parseInt(nodeString[0]);
        int b = Integer.parseInt(nodeString[1]);
        String weight = nodeString[2];

        Node nodeA = new Node(a, weight);
        Node nodeB = new Node(b, weight);
        graph[a].add(nodeB);
        graph[b].add(nodeA);
        if (DEBUG) System.out.println(a + " can go to " + b + " via " + weight);
        if (DEBUG) System.out.println(b + " can go to " + a + " via " + weight);
      }

      // Okay, now that we have the graph, we need to traverse it and see if
      // the ninja can make it to every node/location
      boolean[] visited = new boolean[nLocations];
      visited[0] = true;
      count = 0;

      if (DEBUG) System.out.println(Arrays.toString(obstacles));
      dfs(graph, new boolean[nLocations], 0, obstacles);
      if (DEBUG) System.out.println("ANSWER: " + count);

      if (count == nLocations - 1) {
        System.out.println(1);
      } else {
        System.out.println(0);
      }
    }
  }


  public static void dfs(ArrayList[] graph, boolean[] visited, int v, String[] obs) {
    visited[v] = true;
    for (Node next : ((ArrayList<Node>)graph[v])) {
      if (DEBUG) System.out.println(v + " -> " + next.toString());
      if (!next.in(obs)) {
        if(!visited[next.index]) {
          dfs(graph, visited, next.index, obs);
          count++;
        } else {
          if (DEBUG) System.out.println(next.index + " has already been visited.");
        }
      } else {
        if (DEBUG) System.out.println("Can't pass obstacle: " + next.weight);
      }
    }
  }

}

class Node {
  int index;
  String weight;

  public Node(int i, String w) {
    this.index = i;
    this.weight = w;
  }

  @Override
  public String toString() {
    return new String("(" + this.index + ", " + this.weight + ")");
  }

  public boolean in(String[] obstacles) {
    for (String str : obstacles) {
      if (str.equals(this.weight))
        return true;
    }
    return false;
  }
}
