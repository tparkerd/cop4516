import java.util.*;


public class relatives {
  public static final boolean DEBUG = true;
  public static int[][] G;
  public static int[][] D;
  public static int N;
  public static int R;
  public static HashMap<String, Integer> myHashMap;
  public static final int oo = 1000;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    int caseCounter = 1;
    // Get the people count and relationship count
    String nPeople = stdin.next();
    String nRelationships = stdin.next();
    if (DEBUG) System.out.printf("nPeople (%s) & nRelationships (%s)\n", nPeople, nRelationships);
    // N = Integer.parseInt(stdin.next());
    // R = Integer.parseInt(stdin.next());
    N = Integer.parseInt(nPeople);
    R = Integer.parseInt(nRelationships);
    if (DEBUG) System.out.printf("N (%d) & R (%d)\n", N, R);
    while (N != 0 && R != 0) {

      // Get all the relationships
      String[] relationships = new String[R * 2];
      for (int i = 0; i < (R * 2); i++) {
        relationships[i] = stdin.next();    // Get the current one (k)
        relationships[++i] = stdin.next();  // Get the next (k + 1) and the go to (2k)
      }

      // New graph to connect the people
      G = new int[N][N];
      // New hash map to store people as indices
      myHashMap = new HashMap<String, Integer>();

      // // Initialize all relationships to infinity
      // for (int[] row : G)
      //   Arrays.fill(row, oo);

      for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
          if (i == j)
            G[i][j] = 0;
          else
            G[i][j] = oo;
        }
      }

      // Insert everyone into the HashMap
      int indexCounter = 0;
      for (String str : relationships) {
        // Person not already mapped
        if (!myHashMap.containsKey(str)) {
          myHashMap.put(str, indexCounter);
          indexCounter++;
        }
      }

      printHashMap();

      // // A person has no distance from itself
      // for (int i = 0; i < R; i++) {
      //   for (int j = 0; j < R; j++) {
      //     if (i == j) {
      //       G[i][j] = 0;
      //     }
      //   }
      // }


      // Parse out the relationships from the relationships buffer
      // These are the edges on the G
      for (int i = 0; i < relationships.length - 1; i++) {
        int start = myHashMap.get(relationships[i]);
        int end = myHashMap.get(relationships[++i]);
        G[start][end] = 1;
        G[end][start] = 1;
      }

      printArray();

      floyd();

      printDistanceArray();

      System.out.printf("Network %d: ", caseCounter);
      int result = findMax();
      if (result == oo) {
        System.out.println("DISCONNECTED");
      } else {
        System.out.println(result);
      }


      // Get the people count and relationship count
      nPeople = stdin.next();
      nRelationships = stdin.next();
      if (DEBUG) System.out.printf("nPeople (%s) & nRelationships (%s)\n", nPeople, nRelationships);
      // N = Integer.parseInt(stdin.next());
      // R = Integer.parseInt(stdin.next());
      N = Integer.parseInt(nPeople);
      R = Integer.parseInt(nRelationships);
      if (DEBUG) System.out.printf("N (%d) & R (%d)\n", N, R);
      caseCounter++;
    }
  }



  public static void floyd() {
    // Copy the original graph to the distance graph
    D = new int[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        D[i][j] = G[i][j];
      }
    }

    for (int k = 0; k < N; k++) {
      for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
          D[i][j] = Math.min(D[i][j], D[i][k] + D[k][j]);
          // if ((D[i][j] > D[i][k] + D[k][j]) && (D[i][k] < oo) && (D[k][j] < oo)) {
          //   D[i][j] =  D[i][k] + D[k][j];
          // }
        }
      }
    }
  }

  public static int findMax() {
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        if (D[i][j] > max) {
          max = D[i][j];
        }
      }
    }
    return max;
  }


  public static void printArray() {
    if (DEBUG) System.out.println("Adjacency Matrix\n---------------------------");
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
    if (DEBUG) System.out.println("Distance Matrix\n---------------------------");
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

  public static void printHashMap() {
    if (DEBUG) System.out.println("HashMap (Name -> Index)\n---------------------------");
    if (DEBUG) myHashMap.forEach((k,v)-> System.out.println(k+", "+v));
    if (DEBUG) System.out.println("---------------------------");
  }
}
