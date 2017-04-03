import java.util.*;

public class railroad {
  public static Boolean[][] memo;
  public static int[] A;
  public static int[] B;
  public static int[] C;
  public static int lenA;
  public static int lenB;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    // Get the first test case
    lenA = stdin.nextInt();
    lenB = stdin.nextInt();

    // For each case...
    while (lenA != 0 && lenB != 0) {

      // Initialize both trains to have 1002 cars
      // 1 extra on the front for no cars taken, and
      // 1 extra on the end for array bounds, just in case
      A = new int[1002];
      B = new int[1002];

      // Get all the cars for trains A & B
      for (int i = 1; i <= lenA; i++)
        A[i] = stdin.nextInt();
      for (int i = 1; i <= lenB; i++)
        B[i] = stdin.nextInt();

      // Get the order of products for the final train, C
      C = new int[2002];
      for (int i = 1; i <= lenA + lenB; i++)
        C[i] = stdin.nextInt();

      // Create a memo table for the 'path' to build the target order
      memo = new Boolean[1002][1002];
      for (int i = 0; i < memo.length; i++)
        Arrays.fill(memo[i], null);
      // If there are no cars, it is always valid
      memo[0][0] = true;

      // Solve!
      if (solveDP(lenA, lenB))
        System.out.println("possible");
      else
        System.out.println("not possible");

      // Get the next test case
      lenA = stdin.nextInt();
      lenB = stdin.nextInt();
    }
  }

  public static boolean solve(int a, int b) {
    // if (DEBUG) System.out.printf("Call: %d, %d (%d + %d = %d == %d)\n", a, b, a, b, a + b, C.length);
    if ((a + b) == C.length) return true;
    boolean left = false;
    boolean right = false;

    if (a < lenA && A[a] == C[a + b])
      left = solve(a + 1, b);
    if (b < lenB && B[b] == C[a + b])
      right = solve(a, b + 1);

    return left || right;
  }


  // Traces back from the end of the train to having no trains merged
  // The point of no trains (-,-) is always true
  //     Sample input case:
  //     3 3
  //     1 2 1
  //     2 1 1
  //     1 2 1 1 2 1
  //
  //     E.g.    -   1   2   1
  //          -  T   T
  //          2      T
  //          1      T
  //          1      T   T   T
  //
  //  Here is the only valid path back to having no trains. It checks if
  //  the last added item came from train A or B, and moves left or right
  //  accordingly. Start from no trains in the top left, the train is built
  //  by the turns. If path goes right, add 1 car from train A; if the path
  //  goes down, add 1 car from train B. Continue until no cars are left to add.
  //
  public static boolean solveDP(int a, int b) {
    // Can't go back any farther on either train
    if (a < 0 || b <  0) return false;

    // Answer already found! This actually only happens for memo[0][0]
    if (memo[a][b] != null) return memo[a][b];

    // Assume it's not a valid path until proven otherwise
    memo[a][b] = false;

    int c = a + b;
    // If I take the last item from A, then check to the left if it was
    // also a valid train
    if (A[a] == C[c] && !memo[a][b]) {
      memo[a][b] = solveDP(a - 1, b);
    }
    // If I took the last item from B, then check above if it was also
    // a valid train
    if (B[b] == C[c] && !memo[a][b]) {
      memo[a][b] = solveDP(a, b - 1);
    }
    return memo[a][b];
  }
}
