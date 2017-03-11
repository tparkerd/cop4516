import java.util.*;

public class alphabet {
	// public static int N = 50; // number of possible letters
	public static final boolean DEBUG = true;

	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);
		int nCases = Integer.parseInt(stdin.nextLine());

		// For each case...
		for (int i = 0; i < nCases; i++) {
			// Get all the letters and split into two
			String[] letters = stdin.nextLine().split(" ");
			char[] redLetters = letters[0].toCharArray();
			char[] greenLetters = letters[1].toCharArray();
			int N, M;
			N = redLetters.length + 1;
			M = greenLetters.length + 1;
			int[][] adjMatrix = new int[N][M];

			if (DEBUG) System.out.println("N: " + (N) + ", M: " + (M));

			// Fill in actual connections between red and green letters
			for (int r = 0; r < N; r++) {
				for (int g = 0; g < M; g++) {
					if (DEBUG) System.out.printf("L(%d, %d)\n", r, g);

					// if ((g < M - 2) && Math.abs(redLetters[r] - greenLetters[g]) > 2) {  } // valid
				}
			}

			// Debug, print the adjacency matrix
			for (int q = 0; q < adjMatrix.length; q++) {
				if (DEBUG) System.out.println((q + ": " + Arrays.toString(adjMatrix[q])));

			}


			FordFulkerson graph = new FordFulkerson(N);


			// Add edges to graph
			for (int row = 0; row < N; row++) {
				for (int col = 0; col < M; col++) {
					if (adjMatrix[row][col] > 0) {
						if (DEBUG) System.out.printf("Adding matrix[%d][%d]\n", row, col);
						graph.add(row, col, adjMatrix[row][col]);
					}
				}
			}

			int ans = graph.ff();
			System.out.println(ans);
		}
	}

	public static int convert(char c) {
			return (c - 'a' + 26);
	}
}


class FordFulkerson {
	int[][] cap;
	boolean[] vis;
	int n, s, t, oo = (int)1E9;

	// Set up default flow network with size+2 vertices, size is source, size+1 is sink.
	public FordFulkerson(int size) {
		n = size + 2;
		s = n - 2;
		t = n - 1;
		cap = new int[n][n];

		if (alphabet.DEBUG) System.out.println("Src: " + s + " Sink: " + t);
	}

	// Adds an edge from v1 -> v2 with capacity c.
	public void add(int v1, int v2, int c) {
		cap[v1][v2] = c;
	}

	// Wrapper function for Ford-Fulkerson Algorithm
	public int ff() {

		// Set visited array and flow.
		vis = new boolean[n]; int f = 0;
		while (true) {
			// Run one DFS.
			Arrays.fill(vis, false); int res = dfs(s, oo);

			// Nothing found, get out.
			if (res == 0) break;

			// Add this flow.
			f += res;
		}
		// Return it.
		return f;
	}

	// DFS to find augmenting math from v with maxflow at most min.
	public int dfs(int pos, int min) {

		// got to the sink, this is our flow.
		if (pos == t)  return min;

		// We've been here before - no flow.
		if (vis[pos])  return 0;

		// Mark this node and recurse.
		vis[pos] = true; int f = 0;

		// Just loop through all possible next nodes.
		for (int i = 0; i < n; i++) {

			// We can augment in this direction.
			if (cap[pos][i] > 0)
				f = dfs(i, Math.min(cap[pos][i], min));

			// We got positive flow on this recursive route, return it.
			if (f > 0) {

				// Subtract it going forward.
				cap[pos][i] -= f;

				// Add it going backwards, so that later, we can flow back through this edge as a backedge.
				cap[i][pos] += f;

				// Return this flow.
				return f;
			}
		}

		// If we get here there was no flow.
		return 0;
	}
}
