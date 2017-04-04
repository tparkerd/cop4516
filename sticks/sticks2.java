import java.util.*;

public class sticks2 {
  public static final boolean DEBUG = true;
  public static int[][] segments;
  public static int[][] memo;
  public static int[][][] subDim;
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
      segments = new int[nCuts + 1][2];
      for (int i = 1; i <= nCuts + 1; i++) {
        segments[i - 1][1] = 1; // 'depth'
        segments[i - 1][0] = cuts[i] - cuts[i - 1];
      }

      // if (DEBUG) System.out.println("Segments: " + Arrays.toString(segments));
      for (int[] d : segments) {
        if (DEBUG) System.out.println(Arrays.toString(d));
      }


      // Set up memo table.
      int n = segments.length;
			memo = new int[n][n];
			for (int i=0; i<n; i++) Arrays.fill(memo[i], -1);

      for (int[] m : memo) {
        if (DEBUG) System.out.println(Arrays.toString(m));
      }

			// Pre-calculate dimensions of every possible building fuse.
			subDim = new int[n][n][2];
			for (int i=0; i<n; i++) {
				subDim[i][i][0] = segments[i][0];
				subDim[i][i][1] = segments[i][1];
				for (int j=i+1; j<n; j++) {
					subDim[i][j][0] = subDim[i][j-1][0] + segments[j][0];
					subDim[i][j][1] = Math.max(subDim[i][j-1][1], segments[j][1]);
				}
			}

      // if (DEBUG) System.out.println("Memo: " + Arrays.toString(memo));

      for (int[][] q : subDim) {
        for (int[] r : q) {
          if (DEBUG) System.out.println(Arrays.toString(r));
        }
      }

      // Solve and print!
      // System.out.println("----------------\nCase #" + (c + 1) + ": " + solve() + "\n----------------");
      System.out.println("----------------\nCase #" + (c + 1) + ": " + solve(0, segments.length - 1) + "\n----------------");

    }
  }
  	// Returns best cost of fusing building[start..end]
  	public static int solve(int start, int end) {

  		// Standard base cases in a memo solution.
  		if (start == end) return 0;
  		if (memo[start][end] != -1) return memo[start][end];

  		// Set this high enough.
  		int res = 1000000000;

  		// Try each split/fuse point.
  		for (int split = start; split < end; split++) {

  			// Recursive costs on both ends.
  			int leftCost = solve(start, split);
  			int rightCost = solve(split+1, end);

        if (DEBUG) System.out.printf("Left: %d / Right: %d\n", leftCost, rightCost);

  			// Get teh appropriate dimensions for oru const function.
  			int minLeft = Math.min(subDim[start][split][0], subDim[start][split][1]);
  			int minRight = Math.min(subDim[split+1][end][0], subDim[split+1][end][1]);

  			// Calculate cost for this split and update our best seen, if necessary.
  			res = Math.min(res, minLeft + minRight);
  		}

  		// Store and return.
  		return memo[start][end] = res;
  	}
}
