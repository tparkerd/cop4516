import java.util.*;

public class railroad {
  public static int[][] memo;
  public static int[] trainA;
  public static int[] trainB;
  public static int trainALength;
  public static int trainBLength;
  public static int[] order;


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

      order = new int[trainALength + trainBLength];
      for (int i = 0; i < order.length; i++)
        order[i] = stdin.nextInt();

      if (solve(0, 0))
        System.out.println("possible");
      else
        System.out.println("not possible");
        
      trainALength = stdin.nextInt();
      trainBLength = stdin.nextInt();
    }

  }

  public static boolean solve(int a, int b) {
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
