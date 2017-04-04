import java.util.*;

public class extra {
  static int[][] memo;
  static int[][] dim;
  static int n;

  public static int[][][] subDim;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();
    // For each case...
    for (int l = 0; l < nCases; l++) {
      n = stdin.nextInt();
      dim = new int[n][2];
      for (int j = 0; j < dim.length; j++) dim[j][1] = 1;
      for (int j = 0; j < n; j++) dim[j][0] = stdin.nextInt();

      System.out.println(n);
      System.out.println("Dimensions");
      for (int[] c : dim) {
        System.out.println(Arrays.toString(c));
      }

      // Set up memo table.
			memo = new int[n][n];
			for (int i=0; i<n; i++) Arrays.fill(memo[i], -1);


      System.out.println("Solution " + mcm(0, n - 1));
    }
  }


  public static int mcm(int sIndex, int eIndex) {

		// If we've solved it, return the answer!
		if (memo[sIndex][eIndex] != -1) return memo[sIndex][eIndex];

        // No work to be done since the matrix itself is the answer.
		if (sIndex == eIndex) return 0;

		// Try first split.
		int best = mcm(sIndex+1, eIndex) + dim[sIndex][0]*dim[sIndex][1]*dim[eIndex][1];

		// Try all other splits.
		for (int split = sIndex+1; split<eIndex; split++) {
			int costLeft = mcm(sIndex, split);
			int costRight = mcm(split+1, eIndex);
			int cost = costLeft + costRight + dim[sIndex][0]*dim[split][1]*dim[eIndex][1];
			best = Math.min(best, cost);
		}

		// Store the best answer and return.
		memo[sIndex][eIndex] = best;
		return best;
	}
}
