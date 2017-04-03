import java.util.*;

public class sticks {
  static int[][] memo;
  static int[][] dim;
  static int n;

  public static int[][][] subDim;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();
    // For each case...
    for (int l = 0; l < nCases; l++) {
      int length = stdin.nextInt();
      n = stdin.nextInt();
      dim = new int[n + 1][2];

      // Fugly code to build all the dimensions
      int[] tmp = new int[n + 2];
      tmp[0] = 0;
      int in = 0;
      int lastIn = 0;
      for (int i = 1; i <= n ; i++) {
        lastIn = in;
        in = stdin.nextInt();
        tmp[i] = in - lastIn;
      }
      tmp[n + 1] = length - in;

      for (int j = 1; j < tmp.length; j++) dim[j - 1][0] = tmp[j];
      for (int j = 0; j < dim.length; j++) dim[j][1] = 1;

      for (int[] c : dim) {
        System.out.println(Arrays.toString(c));
      }

      // Set up memo table.
			memo = new int[n + 1][n + 1];
			for (int i=0; i<=n; i++) Arrays.fill(memo[i], -1);

      System.out.println(minMult(0, dim.length - 1));
    }
  }

  public static int minMult(int sIndex, int eIndex) {
    System.out.printf("Call: %d, %d\n", sIndex, eIndex);

		// If we've solved it, return the answer!
		if (memo[sIndex][eIndex] != -1) return memo[sIndex][eIndex];

    // No work to be done since the matrix itself is the answer.
		if (sIndex == eIndex) return 0;

		// Try first split.
		int best = minMult(sIndex+1, eIndex) + dim[sIndex][0];

		// Try all other splits.
		for (int split = sIndex+1; split<eIndex; split++) {
			int costLeft = minMult(sIndex, split) ;
			int costRight = minMult(split+1, eIndex);
			int cost = costLeft + costRight + dim[sIndex][0];
      System.out.printf("b(%d)\tcL(%d)\tcR(%d)\tc(%d)\tw(%d)\n", best, costLeft, costRight, cost, dim[sIndex][0]*dim[split][1]*dim[eIndex][1]);;
			best = Math.min(best, cost);
		}

		// Store the best answer and return.
		memo[sIndex][eIndex] = best;
		return best;
	}
}
