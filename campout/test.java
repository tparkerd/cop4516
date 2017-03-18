import java.util.*;

public class test {
  public static final boolean DEBUG = false;
  public static final int NUM_STUDENTS = 10;
  public static final int offset = NUM_STUDENTS + 1; // +1 b/c of the source

  public static void main(String[] args) {

    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();

    for (int c = 0; c < nCases; c++) {
      // s - 2 = src, s-1 = sink, 0-n students, n+1 -> shifts
      int s = NUM_STUDENTS + (7 * 24) + 2;
      int[][] graph = new int[s][s];

      // Initialize the availability graph
      for (int i = 0; i < s; i++) {
        Arrays.fill(graph[i], 1);
      }


      // Unlink the sink from everything
      Arrays.fill(graph[s - 1], 0);
      // Unlink the source from everything
      Arrays.fill(graph[s - 2], 0);

      // Only the source should link a student
      for (int j = 0; j < s - 2; j++)
        Arrays.fill(graph[j], 0, NUM_STUDENTS, 0);
      // Link source to all students
      Arrays.fill(graph[s - 2], 0, NUM_STUDENTS, 168); // 168 hours max per student
      // Students cannot link to the sink or source
      for (int j = 0; j < NUM_STUDENTS; j++) {
        graph[j][s - 2] = 0;
        graph[j][s - 1] = 0;
      }

      // Shifts cannot link to other shifts
      for (int j = NUM_STUDENTS; j < s; j++) {
        Arrays.fill(graph[j], NUM_STUDENTS, s - 1, 0);
      }


      // // Shifts
      // // Unlink all shifts from one another
      // for (int j = NUM_STUDENTS; j < s - 2; j++)
      //   Arrays.fill(graph[j], NUM_STUDENTS, s - 2, 0);
      //
      // // Source
      // // Unlink the source from the shifts, itself, and the sink
      // Arrays.fill(graph[s - 2], NUM_STUDENTS, s - 2, 0);
      // // Nothing should link back to the source
      // for (int j = 0; j < s; j++) {
      //   graph[j][s - 2] = 0;
      // }
      // // Source should not link to the sink
      // graph[s - 2][s - 1] = 0;
      //
      // // Sink
      // // Students should not link to the sink
      // for (int j = 0; j < NUM_STUDENTS; j++)
      //   graph[j][s - 1] = 0;

      // for (int[] e : graph) {
      //   if (DEBUG) System.out.println(Arrays.toString(e));
      // }
      // System.exit(0);


      // Read in person-by-person
      for (int j = 0; j < NUM_STUDENTS; j++) {
        int nBusyTimes = stdin.nextInt();
        // For each time the student is busy...
        for (int k = 0; k < nBusyTimes; k++) {
          // Get the day and times they are unavailable
          int day = stdin.nextInt();
          int startHour = stdin.nextInt();
          int endHour = stdin.nextInt();
          int weekStartHour = ((day - 1) * 24) + startHour;
          int weekEndHour = ((day - 1) * 24) + endHour;



          if (DEBUG) System.out.printf("#%d) day(%d) start(%d) end(%d) ws(%d) we(%d)\n", j + 1, day, startHour, endHour, weekStartHour, weekEndHour);


          // NOTE(timp): TRY CONVERTING THE START AND END TIMES AND THE CONSTANTS INTO THE WEEKLY ONES
          // Change the day and time to the indices on graph and update them
          // 00:00 - 04:00
          if ((startHour >= 0 && startHour < 4) || (endHour >= 0 && endHour < 4))
            for (int t = weekStartHour; t < weekEndHour; t++) {
              if (DEBUG) System.out.println("Adding to the graph: " + t);
              graph[j][t + offset] = 0;
            }

          // 04:00 - 08:00
          if ((startHour >= 4 && startHour < 8) || (endHour >= 4 && endHour < 8))
            for (int t = weekStartHour; t < weekEndHour; t++) {
              if (DEBUG) System.out.println("Adding to the graph: " + t);
              graph[j][t + offset] = 0;
            }

          // 08:00 - 12:00
          if ((startHour >= 8 && startHour < 12) || (endHour >= 8 && endHour < 12))
            for (int t = weekStartHour; t < weekEndHour; t++) {
              if (DEBUG) System.out.println("Adding to the graph: " + t);
              graph[j][t + offset] = 0;
            }

          // 12:00 - 16:00
          if ((startHour >= 12 && startHour < 16) || (endHour >= 12 && endHour < 16))
            for (int t = weekStartHour; t < weekEndHour; t++) {
              if (DEBUG) System.out.println("Adding to the graph: " + t);
              graph[j][t + offset] = 0;
            }

          // 16:00 - 20:00
          if ((startHour >= 16 && startHour < 20) || (endHour >= 16 && endHour < 20))
            for (int t = weekStartHour; t < weekEndHour; t++) {
              if (DEBUG) System.out.println("Adding to the graph: " + t);
              graph[j][t + offset] = 0;
            }

          // 20:00 - 24:00
          if ((startHour >= 20 && startHour < 24) || (endHour >= 20 && endHour < 24))
            for (int t = weekStartHour; t < weekEndHour; t++) {
              if (DEBUG) System.out.println("Adding to the graph: " + t);
              graph[j][t + offset] = 0;
            }
        }
      }

      // // Try 0 guards, then 1, etc.
			// if (!solvable(graph, NUM_STUDENTS, 0))
			// 	System.out.println("0");
			// else {
      //   if (DEBUG) System.out.println("Passed the first one");
			// 	int tryval = 1;
			// 	while (solvable(graph, NUM_STUDENTS, tryval)) tryval++;
			// 	System.out.println(tryval-1);
			// }

      boolean ans = solvable(graph, NUM_STUDENTS, 2);
      if (ans)
        System.out.printf("Case #%d: YES\n\n", c + 1);
      else
        System.out.printf("Case #%d: NO\n\n", c + 1);
    }
  }

// Returns true iff we can cover each shift with tryval guards.
public static boolean solvable(int[][] graph, int n, int tryval) {

  for (int[] e : graph) {
    if (DEBUG) System.out.println(Arrays.toString(e));
  }

  int s = graph.length;
  FordFulkerson myNetFlow = new FordFulkerson(n+168);
  for (int i=0; i<s; i++)
    for (int j=0; j<s; j++)
      if (graph[i][j] > 0)
        myNetFlow.add(i, j, graph[i][j]);

  // Fill in the flows from all the shifts to the sink.
  for (int j=0; j<168; j++)
    myNetFlow.add(n+j, s-1, tryval);

  int flow = myNetFlow.ff();
  return flow == tryval*168;
}
}


class FordFulkerson {

	// Stores graph.
	public int[][] cap;
	public int n;
	public int source;
	public int sink;

	// "Infinite" flow.
	final public static int oo = (int)(1E9);

	// Set up default flow network with size+2 vertices, size is source, size+1 is sink.
	public FordFulkerson(int size) {
		n = size + 2;
		source = n - 2;
		sink = n - 1;
		cap = new int[n][n];
	}

	// Adds an edge from v1 -> v2 with capacity c.
	public void add(int v1, int v2, int c) {
		cap[v1][v2] = c;
	}

	// Wrapper function for Ford-Fulkerson Algorithm
	public int ff() {

		// Set visited array and flow.
		boolean[] visited = new boolean[n];
		int flow = 0;

		// Loop until no augmenting paths found.
		while (true) {

			// Run one DFS.
			Arrays.fill(visited, false);
			int res = dfs(source, visited, oo);

			// Nothing found, get out.
			if (res == 0) break;

			// Add this flow.
			flow += res;
		}

		// Return it.
		return flow;
	}

	// DFS to find augmenting math from v with maxflow at most min.
	public int dfs(int v, boolean[] visited, int min) {

		// got to the sink, this is our flow.
		if (v == sink)  return min;

		// We've been here before - no flow.
		if (visited[v])  return 0;

		// Mark this node and recurse.
		visited[v] = true;
		int flow = 0;

		// Just loop through all possible next nodes.
		for (int i = 0; i < n; i++) {

			// We can augment in this direction.
			if (cap[v][i] > 0)
				flow = dfs(i, visited, Math.min(cap[v][i], min));

			// We got positive flow on this recursive route, return it.
			if (flow > 0) {

				// Subtract it going forward.
				cap[v][i] -= flow;

				// Add it going backwards, so that later, we can flow back through this edge as a backedge.
				cap[i][v] += flow;

				// Return this flow.
				return flow;
			}
		}

		// If we get here there was no flow.
		return 0;
	}
}
