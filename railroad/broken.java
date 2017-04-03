import java.util.*;

public class broken {
  public static int[][] memo;
  public static int[] trainA;
  public static int[] trainB;
  public static int[] ordering;


  public static final boolean DEBUG = true;
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int trainALength = stdin.nextInt();
    int trainBLength = stdin.nextInt();

    // For each case...
    while (trainALength != 0 && trainBLength != 0) {
      trainA = new int[1000];
      trainB = new int[1000];

      for (int i = 0; i < trainALength; i++)
        trainA[i] = stdin.nextInt();

      for (int i = 0; i < trainBLength; i++)
        trainB[i] = stdin.nextInt();

      if (DEBUG) System.out.println("A: " + Arrays.toString(trainA));
      if (DEBUG) System.out.println("B: " + Arrays.toString(trainB));

      ordering = new int[trainALength + trainBLength];
      for (int i = 0; i < ordering.length; i++)
        ordering[i] = stdin.nextInt();

      if (DEBUG) System.out.println("O: " + Arrays.toString(ordering));

      memo = new int[1000][1000];
      for (int i = 0; i < memo.length; i++)
        Arrays.fill(memo[i], -1);
      memo[0][0] = 1;

      if (solve(trainALength, trainBLength) != 0)
        System.out.println("possible");
      else
        System.out.println("not possible");



      trainALength = stdin.nextInt();
      trainBLength = stdin.nextInt();
    }

  }

  public static int solve(int carAPosition, int carBPosition) {
    if (carAPosition < 0 || carBPosition < 0) return 0;
    int result = memo[carAPosition][carBPosition];
    if (result != -1)
      return result;

    result = 0;

    if (trainA[carAPosition] == ordering[carAPosition + carBPosition] && result != 0)
      result = solve(carAPosition - 1, carBPosition);
    if (trainB[carBPosition] == ordering[carAPosition + carBPosition] && result != 0)
      result = solve(carAPosition, carBPosition - 1);
    return result;
  }
}
