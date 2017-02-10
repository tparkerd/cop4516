import java.util.*;

public class longpath {
  public static final boolean DEBUG = true;
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();

    // For each case...
    for (int i = 0; i < nCases; i++) {
      int nCities = stdin.nextInt();
      int nStreets = stdin.nextInt();
      if (DEBUG) System.out.printf("nCities: %d\tnStreets: %d\n", nCities, nStreets);

      if (DEBUG) System.out.println("Start\tDest\tWeight");
      // For each street...
      for (int j = 0; j < nStreets; j++) {
        // Make a connection to two cities via said street or are these just the intersections...

        int s = stdin.nextInt();  // Starting intersection
        int d = stdin.nextInt();  // Detination intersection
        int m = stdin.nextInt();  // Number of minutes to travel (weight)

        if (DEBUG) System.out.println(s + "\t" + d + "\t" + m);

        // 0 = home, V - 1 = destination

      }
    }
  }
}
