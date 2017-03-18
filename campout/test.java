import java.util.*;

public class test {
  public static final boolean DEBUG = false;
  public static final int NUM_STUDENTS = 10;
  public static final int offset = NUM_STUDENTS + 1; // +1 b/c of the source
  public static Dinic myNetFlow;

  public static void main(String[] args) {

    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();

    for (int c = 0; c < nCases; c++) {
      // 0 = src, s-1 = sink, 1-n students, n+1 -> shifts
      int s = NUM_STUDENTS + (7 * 24) + 2;
      int[][] graph = new int[s][s];

      // Initialize the availability graph
      for (int i = 0; i < s; i++) {
        Arrays.fill(graph[i], 1);
      }


      // Link source to all students
      Arrays.fill(graph[0], 0);
      graph[0][s-1] = 0; // Unlink the source from the sink
      for (int j = 1; j <= NUM_STUDENTS; j++) {
        graph[0][j] = 1;
        graph[j][s-1] = 0; // unlink the preson from the sink
      }

      for (int j = 0; j < s; j++) {
        // Nothing should link back to the source
        graph[j][0] = 0;
      }


      // Read in person-by-person
      for (int j = 1; j <= NUM_STUDENTS; j++) {
        int nBusyTimes = stdin.nextInt();
        // For each time the student is busy...
        for (int k = 0; k < nBusyTimes; k++) {
          // Get the day and times they are unavailable
          int day = stdin.nextInt();
          int startHour = stdin.nextInt();
          int endHour = stdin.nextInt();
          int weekStartHour = ((day - 1) * 24) + startHour;
          int weekEndHour = ((day - 1) * 24) + endHour;

          // if (DEBUG) System.out.printf("#%d) day(%d) start(%d) end(%d) ws(%d) we(%d)\n", j + 1, day, startHour, endHour, weekStartHour, weekEndHour);

          // Change the day and time to the indices on graph and update them
          // 00:00 - 04:00
          if ((startHour >= 0 && startHour < 4) || (endHour >= 0 && endHour < 4))
            for (int t = weekStartHour; t <= weekEndHour; t++) {
              // if (DEBUG) System.out.println("Adding to the graph: " + t);
              graph[j][t + offset] = 0;
            }

          // 04:00 - 08:00
          if ((startHour >= 4 && startHour < 8) || (endHour >= 4 && endHour < 8))
            for (int t = weekStartHour; t <= weekEndHour; t++) {
              // if (DEBUG) System.out.println("Adding to the graph: " + t);
              graph[j][t + offset] = 0;
            }

          // 08:00 - 12:00
          if ((startHour >= 8 && startHour < 12) || (endHour >= 8 && endHour < 12))
            for (int t = weekStartHour; t <= weekEndHour; t++) {
              // if (DEBUG) System.out.println("Adding to the graph: " + t);
              graph[j][t + offset] = 0;
            }

          // 12:00 - 16:00
          if ((startHour >= 12 && startHour < 16) || (endHour >= 12 && endHour < 16))
            for (int t = weekStartHour; t <= weekEndHour; t++) {
              // if (DEBUG) System.out.println("Adding to the graph: " + t);
              graph[j][t + offset] = 0;
            }

          // 16:00 - 20:00
          if ((startHour >= 16 && startHour < 20) || (endHour >= 16 && endHour < 20))
            for (int t = weekStartHour; t <= weekEndHour; t++) {
              // if (DEBUG) System.out.println("Adding to the graph: " + t);
              graph[j][t + offset] = 0;
            }

          // 20:00 - 24:00
          if ((startHour >= 20 && startHour < 24) || (endHour >= 20 && endHour < 24))
            for (int t = weekStartHour; t <= weekEndHour; t++) {
              // if (DEBUG) System.out.println("Adding to the graph: " + t);
              graph[j][t + offset] = 0;
            }
        }
      }

      for (int[] e : graph) {
        if (DEBUG) System.out.println(Arrays.toString(e));
      }

      int N = 180;

      FordFulkerson g = new FordFulkerson(N - 2);


      // Add edges to g
      for (int row = 0; row < N; row++) {
        for (int col = 0; col < N; col++) {
          if (graph[row][col] > 0) {
            if (DEBUG) System.out.printf("Adding matrix[%d][%d]\n", row, col);
            g.add(row, col, graph[row][col]);
          }
        }
      }

      int ans = g.ff();
      if (ans == 168)
        System.out.printf("Case #%d: YES\n", c + 1);
      else
        System.out.printf("Case #%d: NO\n", c + 1);
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
