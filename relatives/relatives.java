import java.util.*;

public class relatives {
  public static final boolean DEBUG = false;
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
      String[] buf = stdin.nextLine().split(" ");
      nPeople = Integer.parseInt(buf[0]);
      int nRelationships = Integer.parseInt(buf[1]);


      // EOF condition
      if (nPeople == 0 || nRelationships == 0) break;

      if (DEBUG) System.out.println("nPeople (" + nPeople + ")\t nRelationships (" + nRelationships + ")");

      // Get all the relationships
      String[] relationships = stdin.nextLine().split(" ");
      if (DEBUG) System.out.println(Arrays.toString(relationships));

      // New graph to connect the people
      G = new int[nPeople][nPeople];
      // New hash map to store people as indices
      map = new HashMap<String, Integer>();

      // Initialize all relationships to infinity
      for (int[] row : G)
        Arrays.fill(row, oo);

      printArray();

      // Insert everyone into the HashMap
      int indexCounter = 0;
      for (String str : relationships) {
        // Person not already mapped
        if (!map.containsKey(str)) {
          map.put(str, indexCounter++);
        }
      }

      printHashMap();

      // A person has no distance from itself
      for (int i = 0; i < nRelationships; i++)
        for (int j = 0; j < nRelationships; j++)
          if (i == j)
            G[i][j] = 0;


      // Parse out the relationships from the relationships buffer
      // These are the edges on the G
      for (int i = 0; i < relationships.length - 1; i++) {
        if (DEBUG) System.out.println("Pair: " + relationships[i] + " & " + relationships[i + 1]);
        int start = map.get(relationships[i]);
        int end = map.get(relationships[++i]);
        G[start][end] = 1;
        G[end][start] = 1;
      }

      printArray();

      floyd();

      printDistanceArray();

      printP();

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
    int max = -1;
    for (int i = 0; i < nPeople; i++) {
      for (int j = 0; j < nPeople; j++) {
        if (D[i][j] > max) {
          max = D[i][j];
        }
      }
    }
    return max;
  }

  public static void printArray() {
    if (DEBUG) System.out.println("Current Graph\n---------------------------");
    for (int i = 0; i < G.length; i++) {
      for (int j = 0; j < G.length; j++) {
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

  public static void printDistanceArray() {
    if (DEBUG) System.out.println("Current Graph\n---------------------------");
    for (int i = 0; i < D.length; i++) {
      for (int j = 0; j < D.length; j++) {
        if (D[i][j] != oo) {
          if (DEBUG) System.out.print(D[i][j] + "\t");
        } else {
          if (DEBUG) System.out.print('\u221E' + "\t");
        }
      }
      if (DEBUG) System.out.println();
    }
    if (DEBUG) System.out.println("---------------------------");
  }

  public static void printP() {
    if (DEBUG) System.out.println("Current Graph\n---------------------------");
    for (int i = 0; i < P.length; i++) {
      for (int j = 0; j < P.length; j++) {
        if (P[i][j] != oo) {
          if (DEBUG) System.out.print(P[i][j] + "\t");
        } else {
          if (DEBUG) System.out.print('\u221E' + "\t");
        }
      }
      if (DEBUG) System.out.println();
    }
    if (DEBUG) System.out.println("---------------------------");
  }

  public static void printHashMap() {
    if (DEBUG) System.out.println("Current HashMapping\n---------------------------");
    if (DEBUG) map.forEach((k,v)-> System.out.println(k+", "+v));
    if (DEBUG) System.out.println("---------------------------");
  }
}
