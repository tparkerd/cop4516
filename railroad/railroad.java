import java.util.*;

public class railroad {
  public static int[][] memo;
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
      trainA = new int[trainALength + 1];
      trainB = new int[trainBLength + 1];

      for (int i = 0; i < trainALength; i++)
        trainA[i] = stdin.nextInt();

      for (int i = 0; i < trainBLength; i++)
        trainB[i] = stdin.nextInt();

      if (DEBUG) System.out.println("A: " + Arrays.toString(trainA));
      if (DEBUG) System.out.println("B: " + Arrays.toString(trainB));

      order = new int[trainALength + trainBLength];
      for (int i = 0; i < order.length; i++)
        order[i] = stdin.nextInt();

      if (DEBUG) System.out.println("O: " + Arrays.toString(order));

      // memo = new int[1000][1000];
      // for (int i = 0; i < memo.length; i++)
      //   Arrays.fill(memo[i], -1);
      // memo[0][0] = 1;

      if (solve(0, 0))
        System.out.println("possible");
      else
        System.out.println("not possible");



      trainALength = stdin.nextInt();
      trainBLength = stdin.nextInt();
    }

  }

  public static boolean solve(int a, int b) {
    if (DEBUG) System.out.printf("Call: %d, %d (%d + %d = %d == %d)\n", a, b, a, b, a + b, order.length);
    if ((a + b) == order.length) return true;
    boolean left = false;
    boolean right = false;

    if (a < trainALength && trainA[a] == order[a + b])
      left = solve(a + 1, b);
    if (b < trainBLength && trainB[b] == order[a + b])
      right = solve(a, b + 1);

    return left | right;
  }
}
