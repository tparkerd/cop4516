import java.util.*;

public class sticks2 {
  public static final boolean DEBUG = true;
  public static int[] segments;
  public static int[][] memo;
  public static int[][] subDim;
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();

    // For each case...
    for (int c = 0; c < nCases; c++) {
      // Get original stick length
      int stickLength = stdin.nextInt();

      // Number of cuts
      int nCuts = stdin.nextInt();

      // Gather cut locations, + start and end
      // NOTE(timp): I kept the start and end so I could use them to
      // determine the length of the first and last segment of the stick
      int[] cuts = new int[nCuts + 2];
      for (int i = 1; i <= nCuts; i++) cuts[i] = stdin.nextInt();
      cuts[nCuts + 1] = stickLength; // end of last segment
      if (DEBUG) System.out.println("Cuts: " + Arrays.toString(cuts));

      // Split up the stick into the known segment lengths
      segments = new int[nCuts + 1];
      for (int i = 1; i <= nCuts + 1; i++) segments[i - 1] = cuts[i] - cuts[i - 1];
      if (DEBUG) System.out.println("Segments: " + Arrays.toString(segments));


      // Set up memo table.
			memo = new int[segments.length][segments.length];
			Arrays.fill(memo, -1);

			// Pre-calculate dimensions of every possible building fuse.
			subDim = new int[segments.length][segments.length];
			for (int i=0; i< segments.length; i++) {
				subDim[i][i] = segments[i];
				for (int j=i+1; j< segments.length; j++) {
					subDim[i][j] = subDim[i][j-1] + segments[j];
				}
			}

      if (DEBUG) System.out.println("Memo: " + Arrays.toString(memo));

      for (int[] q : subDim) {
        if (DEBUG) System.out.println(Arrays.toString(q));
      }

      // Solve and print!
      // System.out.println("----------------\nCase #" + (c + 1) + ": " + solve() + "\n----------------");
      System.out.println("----------------\nCase #" + (c + 1) + ": " + solve(0, segments.length - 1) + "\n----------------");

    }
  }


  public static int solve(int start, int end) {

		// Standard base cases in a memo solution.
		if (start == end) return 0;
		if (memo[start][end] != -1) return memo[start][end];

		// Set this high enough.
		int res = 1000000000;

		// Try each split/fuse point.
		for (int split = start; split<end; split++) {

			// Recursive costs on both ends.
			int leftCost = solve(start, split);
			int rightCost = solve(split+1, end);

			// Get the appropriate dimensions for our cost function.
			int minLeft = Math.min(subDim[start][split], subDim[start][split]);
			int minRight = Math.min(subDim[split+1][end], subDim[split+1][end]);

			// Calculate cost for this split and update our best seen, if necessary.
			res = Math.min(res, leftCost+rightCost+minLeft*minRight);
		}

		// Store and return.
		return memo[start][end] = res;
	}
}
