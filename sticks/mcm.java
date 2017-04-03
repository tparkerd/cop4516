// Arup Guha
// 11/6/2014
// Written for DP #2 Lecture on Matrix Chain Multiplication Problem

import java.util.*;

public class mcm {

	public static int[][] memo;

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		int numCases = stdin.nextInt();

        // Process all input cases.
		for (int loop=0; loop<numCases; loop++) {

            // Read in all matrix dimensions.
			int n = stdin.nextInt();
			int[][] dim = new int[n][2];
			for (int i=0; i<n; i++)
				for (int j=0; j<2; j++)
					dim[i][j] = stdin.nextInt();

			for (int[] q : dim) {
				System.out.println(Arrays.toString(q));
			}

            // Set up memo table.
			memo = new int[n][n];
			for (int i=0; i<n; i++)
				Arrays.fill(memo[i], -1);



            // Solve and print it.
			System.out.println(minMult(dim, 0, n-1));
		}
	}

	public static int minMult(int[][] dim, int sIndex, int eIndex) {

		// If we've solved it, return the answer!
		if (memo[sIndex][eIndex] != -1) return memo[sIndex][eIndex];

        // No work to be done since the matrix itself is the answer.
		if (sIndex == eIndex) return 0;

		// Try first split.
		int best = minMult(dim, sIndex+1, eIndex) + dim[sIndex][0]*dim[sIndex][1]*dim[eIndex][1];

		// Try all other splits.
		for (int split = sIndex+1; split<eIndex; split++) {
			int costLeft = minMult(dim, sIndex, split);
			int costRight = minMult(dim, split+1, eIndex);
			int cost = costLeft + costRight + dim[sIndex][0]*dim[split][1]*dim[eIndex][1];
			best = Math.min(best, cost);
		}

		// Store the best answer and return.
		memo[sIndex][eIndex] = best;
		return best;
	}
}
