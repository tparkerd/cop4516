import java.util.*;

public class translateion {
  public static Boolean[][] memo;
  public static int[] trainA;
  public static int[] trainB;
  public static int trainALength;
  public static int trainBLength;
  public static int[] order;


  public static final boolean DEBUG = false;
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    trainALength = stdin.nextInt();
    trainBLength = stdin.nextInt();

    // For each case...
    while (trainALength != 0 && trainBLength != 0) {
      trainA = new int[1010];
      trainB = new int[1010];

      for (int i = 0; i < trainALength; i++)
        trainA[i] = stdin.nextInt();

      for (int i = 0; i < trainBLength; i++)
        trainB[i] = stdin.nextInt();

      if (DEBUG) System.out.println("A: " + Arrays.toString(trainA));
      if (DEBUG) System.out.println("B: " + Arrays.toString(trainB));

      order = new int[trainALength + trainBLength + 1];
      for (int i = 0; i < trainALength + trainBLength; i++)
        order[i] = stdin.nextInt();

      if (DEBUG) System.out.println("O: " + Arrays.toString(order));

      memo = new Boolean[1010][1010];
      for (int i = 0; i < memo.length; i++)
        Arrays.fill(memo[i], null);
      memo[0][0] = true;

      if (solve(trainALength, trainBLength))
        System.out.println("possible");
      else
        System.out.println("not possible");
      //
      // for (Boolean[] b : memo) {
      //   System.out.println(Arrays.toString(b));
      // }

      trainALength = stdin.nextInt();
      trainBLength = stdin.nextInt();
    }

  }

  public static boolean solve(int a, int b) {
    if (a < 0 || b < 0) {
      return false;
    }
    System.out.printf("Call: %d, %d\n", a, b);

    // If already solved...
    if (memo[a][b] != null)
      return memo[a][b];

    // Assume not possible
    memo[a][b] = false;

    if (trainA[a] == order[a + b] && !memo[a][b])
      memo[a][b] = solve(a - 1, b);

    if (trainB[b] == order[a + b] && !memo[a][b])
      memo[a][b] = solve(a, b - 1);

    return memo[a][b];
  }
}
