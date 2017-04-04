import java.util.*;

public class sticks {
	public static final boolean DEBUG = true;
	public static int[] segments;

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

			// Solve and print!
			System.out.println("----------------\nCase #" + (c + 1) + ": " + solve() + "\n----------------");
		}
	}

	public static int solve() {
		// Stores the intermediate answers
		int[][] minMult = new int[segments.length][segments.length];

		// Initialize the matrix to show that it takes no cost for the
		// individual stick segments themselves, without re-joining them
		for (int i = 0; i < minMult.length; i++) minMult[i][i] = segments[i];

		// c + 1 is the number of lots we are joining together. In the first loop,
		// we just calculate the cost of putting two lots together
		for (int c = 1; c < minMult.length; c++) {
			for (int o = 0; o < minMult.length - c; o++) {
				// The boundary at which we are fusing the two segments
				for (int s = o; s < o + c; s++) {
					// Calculations are made!
					// This is the cost of the two segments re-joined
					// NOTE(timp): This is the part I'm most unsure about.
					// Do I need to save the minimum of each of the attempts to
					// join the segments at this point and use that as the cost for each?
					// Recursion might be easier to read for this part
					if (DEBUG) System.out.printf("Join Cost: %d [%d][%d] + %d [%d][%d] = %d [%d][%d]\n",
																			 minMult[o][s], o, s,															// Left stick?
																			 minMult[s + 1][o + c], s + 1, o + c,							// Right stick?
																			 minMult[o][s] + minMult[s + 1][o + c], o, o + c  // ??? Is this going in the right place?
					 													 	);
					int cost = minMult[o][s] + minMult[s + 1][o + c];

					// Store this value for future reference
					minMult[o][o + c] = cost;
				}
			}
		}

		// Show matrix!
		for (int i = 0; i < minMult.length; i++)
			if (DEBUG) System.out.println(Arrays.toString(minMult[i]));
		// Found the answer!
		return minMult[0][minMult.length - 1];
	}
}
