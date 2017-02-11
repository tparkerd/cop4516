import java.util.*;

public class relatives {
  public static int[][] G;
  public static int[][] D;
  public static int[][] P;
  public static int nPeople;
  public static HashMap<String, Integer> map;
  public static final int oo = Integer.MAX_VALUE;

  public static void main(String[] args) {

    Scanner stdin = new Scanner(System.in);

    int caseCounter = 1;
    while (stdin.hasNext()) {
      // Get the people count and relationship count
      nPeople = Integer.parseInt(stdin.next());
      int nRelationships = Integer.parseInt(stdin.next());


      // EOF condition
      if (nPeople == 0 || nRelationships == 0) break;

      // Get all the relationships
      String[] relationships = new String[nRelationships * 2];
      for (int i = 0; i < nRelationships * 2; i++) {
        relationships[i] = stdin.next();
        relationships[++i] = stdin.next();
      }

      System.out.println(Arrays.toString(relationships));

      // New graph to connect the people
      G = new int[nPeople][nPeople];
      // New hash map to store people as indices
      map = new HashMap<String, Integer>();

      // Initialize all relationships to infinity
      for (int[] row : G)
        Arrays.fill(row, oo);

      // Insert everyone into the HashMap
      int indexCounter = 0;
      for (String str : relationships) {
        // Person not already mapped
        if (!map.containsKey(str)) {
          map.put(str, indexCounter++);
        }
      }

      // A person has no distance from itself
      for (int i = 0; i < nRelationships; i++)
        for (int j = 0; j < nRelationships; j++)
          if (i == j)
            G[i][j] = 0;


      // Parse out the relationships from the relationships buffer
      // These are the edges on the G
      for (int i = 0; i < relationships.length - 1; i++) {
        int start = map.get(relationships[i]);
        int end = map.get(relationships[++i]);
        G[start][end] = 1;
        G[end][start] = 1;
      }

      floyd();

      System.out.printf("Network %d: ", caseCounter);
      int result = findMax();
      if (result == oo) {
        System.out.println("DISCONNECTED");
      } else {
        System.out.println(result);
      }
      caseCounter++;
    }
  }



  public static void floyd() {
    P = new int[nPeople][nPeople];
    for (int i = 0; i < nPeople; ++i) {
      for (int j = 0; j < nPeople; ++j) {
        if (G[i][j] < oo)
          P[i][j] = i;
        else
          P[i][j] = -1;
      }
    }

    D = new int[nPeople][nPeople];
    for (int i = 0; i < nPeople; i++) {
      for (int j = 0; j < nPeople; j++) {
        D[i][j] = G[i][j];
      }
    }

    for (int k = 0; k < nPeople; k++) {
      for (int i = 0; i < nPeople; i++) {
        for (int j = 0; j < nPeople; j++) {
          if ((D[i][j] > D[i][k] + D[k][j]) && (D[i][k] < oo) && (D[k][j] < oo)) {
            D[i][j] =  D[i][k] + D[k][j];
            P[i][j] = P[k][j];
          }
        }
      }
    }
  }

  public static int findMax() {
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < nPeople; i++) {
      for (int j = 0; j < nPeople; j++) {
        if (D[i][j] > max) {
          max = D[i][j];
        }
      }
    }
    return max;
  }
}
