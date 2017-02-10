import java.util.*;

public class relatives {
  public static final boolean DEBUG = true;
  public static Edge[] graph;
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    while (stdin.hasNext()) {
      // Get the people count and relationship count
      String[] buf = stdin.nextLine().split(" ");
      int nPeople = Integer.parseInt(buf[0]);
      int nRelationships = Integer.parseInt(buf[1]);

      // EOF condition
      if (nPeople == 0 || nRelationships == 0) break;

      if (DEBUG) System.out.println("nPeople (" + nPeople + ")\t nRelationships (" + nRelationships + ")");

      // Get all the relationships
      String[] relationships = stdin.nextLine().split(" ");

      if (DEBUG) System.out.println("Person A\tPerson B");

      graph = new Edge[nRelationships];
      // Parse out the relationships from the relationships buffer
      // These are the edges on the graph
      for (int i = 0; i < nRelationships * 2; i++) {
        String personA = relationships[i];
        String personB = relationships[++i];
        if (DEBUG) System.out.println(relationships[i - 1] + "\t" + relationships[i]);
        Edge tmp = new Edge(personA, personB);
        graph[i / 2] = tmp;
      }

      System.out.println(Arrays.toString(graph));

    }
  }
}

class Edge {
  String personA;
  String personB;
  public Edge(String a, String b) {
    this.personA = a;
    this.personB = b;
  }

  @Override
  public String toString() {
    return (this.personA + " & " + this.personB);
  }
}
