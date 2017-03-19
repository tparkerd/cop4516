import java.util.*;

public class campout {
  public static final boolean DEBUG = true;
  public static final int NUM_STUDENTS = 10;
  public static final int NUM_HOURS = 168;
  public static final int MAX_HOURS = 80;

  public static void main(String[] args) {

    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();

    // For each test case...
    for (int c = 0; c < nCases; c++) {
      // s - 2 = src, s - 1 = sink, 0 -> n = Student vertices, n -> (n + NUM_HOURS) = Shift vertices
      int s = NUM_STUDENTS + (7 * 24) + 2;
      int[][] graph = new int[s][s];

      // Initialize the availability graph
      // Since the input is a list of unavailibility, assume always available
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
      Arrays.fill(graph[s - 2], 0, NUM_STUDENTS, MAX_HOURS); // 168 hours max can be worked per student
      // Students cannot link to the sink or source
      for (int j = 0; j < NUM_STUDENTS; j++) {
        graph[j][s - 2] = 0;
        graph[j][s - 1] = 0;
      }

      // Shifts cannot link to other shifts
      for (int j = NUM_STUDENTS; j < s; j++) {
        Arrays.fill(graph[j], NUM_STUDENTS, s - 1, 0);
      }

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

          if (DEBUG) System.out.printf("#%d) day(%d) startHour(%d) endHour(%d) weekStartHour(%d) weekEndHour(%d)\n", j + 1, day, startHour, endHour, weekStartHour, weekEndHour);

          // If the busy period overlaps with any of the following shifts,
          // remove the edge from the graph
          // 00:00 - 04:00
          if ((startHour >= 0 && startHour < 4) || (endHour >= 0 && endHour < 4)) {
              if (DEBUG) System.out.println("Removing edge: Person(" + (j + 1) + ") Shift(" + weekStartHour + ")");
              Arrays.fill(graph[j], ((day - 1) * 24) + NUM_STUDENTS, ((day - 1) * 24) + NUM_STUDENTS + 4, 0);
            }

          // 04:00 - 08:00
          if ((startHour >= 4 && startHour < 8) || (endHour >= 4 && endHour < 8)) {
            Arrays.fill(graph[j], ((day - 1) * 24) + NUM_STUDENTS + 4, ((day - 1) * 24) + NUM_STUDENTS + 8, 0);
            if (DEBUG) System.out.println("Removeing edge: Person(" + (j + 1) + ") Shift(" + weekStartHour + ")");
            }

          // 08:00 - 12:00
          if ((startHour >= 8 && startHour < 12) || (endHour >= 8 && endHour < 12)) {
            Arrays.fill(graph[j], ((day - 1) * 24) + NUM_STUDENTS + 8, ((day - 1) * 24) + NUM_STUDENTS + 12, 0);
            if (DEBUG) System.out.println("Removeing edge: Person(" + (j + 1) + ") Shift(" + weekStartHour + ")");
            }

          // 12:00 - 16:00
          if ((startHour >= 12 && startHour < 16) || (endHour >= 12 && endHour < 16)) {
            Arrays.fill(graph[j], ((day - 1) * 24) + NUM_STUDENTS + 12, ((day - 1) * 24) + NUM_STUDENTS + 16, 0);
            if (DEBUG) System.out.println("Removeing edge: Person(" + (j + 1) + ") Shift(" + weekStartHour + ")");
            }

          // 16:00 - 20:00
          if ((startHour >= 16 && startHour < 20) || (endHour >= 16 && endHour < 20)) {
            Arrays.fill(graph[j], ((day - 1) * 24) + NUM_STUDENTS + 16, ((day - 1) * 24) + NUM_STUDENTS + 20, 0);
            if (DEBUG) System.out.println("Removeing edge: Person(" + (j + 1) + ") Shift(" + weekStartHour + ")");
            }

          // 20:00 - 24:00
          if ((startHour >= 20 && startHour < 24) || (endHour >= 20 && endHour < 24)) {
            Arrays.fill(graph[j], ((day - 1) * 24) + NUM_STUDENTS + 20, ((day - 1) * 24) + NUM_STUDENTS + 24, 0);
            if (DEBUG) System.out.println("Removeing edge: Person(" + (j + 1) + ") Shift(" + weekStartHour + ")");
            }
        }
      }

      // Although not necessary, I used the solvable function from Arup's
      // museum.java (Ford Fulkerson version).
      // NOTE(timp): The tryval is 2 b/c we are trying to make sure at least two
      // students can man the tent at all times.
      // This part is what I'm not sure about, well, that and the graph, but I'm
      // less confident of this part than the graph. The actual network flow  graph
      // is created inside of the solvable function.
      // boolean ans = solvable(graph, NUM_STUDENTS, 3);
      // if (ans)
      //   System.out.printf("Case #%d: YES\n\n", c + 1);
      // else
      //   System.out.printf("Case #%d: NO\n\n", c + 1);
      // Try 0 guards, then 1, etc.
      for (int[] e : graph) {
        if (DEBUG) System.out.println(Arrays.toString(e));
      }

      int length = graph.length;
      FordFulkerson myNetFlow = new FordFulkerson(NUM_STUDENTS + NUM_HOURS);
      for (int m=0; m<length; m++)
        for (int n=0; n<length; n++)
          if (graph[m][n] > 0)
            myNetFlow.add(m, n, graph[m][n]);

      // Fill in the flows from all the shifts to the sink.
      for (int n = 0; n <NUM_HOURS; n++)
        myNetFlow.add(NUM_STUDENTS + n, s-1, 3);

      int flow = myNetFlow.ff();
      boolean ans = (flow == 3 * NUM_HOURS);

      if (ans)
        System.out.printf("Case #%d: YES\n\n", c + 1);
      else
        System.out.printf("Case #%d: NO\n\n", c + 1);
    }
  }

  // Returns true iff we can cover each shift with tryval students
  public static boolean solvable(int[][] graph, int n, int tryval) {

    for (int[] e : graph) {
      if (DEBUG) System.out.println(Arrays.toString(e));
    }

    int s = graph.length;
    FordFulkerson myNetFlow = new FordFulkerson(n+NUM_HOURS);
    for (int i=0; i<s; i++)
      for (int j=0; j<s; j++)
        if (graph[i][j] > 0)
          myNetFlow.add(i, j, graph[i][j]);

    // Fill in the flows from all the shifts to the sink.
    for (int j=0; j<NUM_HOURS; j++)
      myNetFlow.add(n+j, s-1, tryval);

    int flow = myNetFlow.ff();
    return flow == tryval*NUM_HOURS;
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
