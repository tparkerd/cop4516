// Arup Guha
// 6/21/2012
// Solution to 2009 SER Problem: Museum Guards
// Note: Runs in 30 seconds on my laptop (on the judge data).
// Edited on 3/6/2017 to use Ford-Fulkerson Code shown in class.

import java.util.*;

public class museum {

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);

		int n = stdin.nextInt();

		while (n != 0) {

			// 0 = source, s-1 = sink, 1-n guards, n+1 -> n+48 shifts
			int s = n + 2 + 48;
			int[][] graph = new int[s][s];
			for (int i=0; i<s; i++)
				Arrays.fill(graph[i], 0);

			// Read in each guard.
			for (int i=0; i<n; i++) {
				int shifts = stdin.nextInt();
				int max = stdin.nextInt();
				boolean[] available = new boolean[1440];
				Arrays.fill(available, false);

				// Fill in guard's availability.
				for (int j=0; j<shifts; j++) {
					String t1 = stdin.next();
					String t2 = stdin.next();
					fillArray(convert(t1), convert(t2), available);
				}

				// Convert the minutes to 30 minute availability slots.
				int[] slots = getSlots(available);

				// These are the flows we want.
				graph[s-2][i] = max/30;
				for (int j=0; j<slots.length; j++) {
					graph[i][n+j] = slots[j];
				}
			}

			System.out.println("N VALUE " + n);

			// Try 0 guards, then 1, etc.
			if (!solvable(graph, n, 1))
				System.out.println("0");
			else {
				int tryval = 1;
				while (solvable(graph, n, tryval)) tryval++;
				System.out.println(tryval-1);
			}

			n = stdin.nextInt();
		}
	}

	// Returns true iff we can cover each shift with tryval guards.
	public static boolean solvable(int[][] graph, int n, int tryval) {
		System.out.println("Show graph");
		for (int i = 0; i < graph.length; i++)
			System.out.println(Arrays.toString(graph[i]));

		int s = graph.length;
		FordFulkerson myNetFlow = new FordFulkerson(n+48);
		for (int i=0; i<s; i++)
			for (int j=0; j<s; j++)
				if (graph[i][j] > 0)
					myNetFlow.add(i, j, graph[i][j]);

		// Fill in the flows from all the shifts to the sink.
		for (int j=0; j<48; j++)
			myNetFlow.add(n+j, s-1, tryval);

		int flow = myNetFlow.ff();
		return flow == tryval*48;
	}

	// Returns the number of minutes after midnight represented by s.
	public static int convert(String s) {
		int hr = 10*(s.charAt(0) - '0') + s.charAt(1) - '0';
		int min = 10*(s.charAt(3) - '0') + s.charAt(4) - '0';
		return 60*hr + min;
	}

	// Fills available in interval [start, end) if start < end.
	// Or [start, 1439], [0, end) if end < start. Or all slots if start == end.
	public static void fillArray(int start, int end, boolean[] available) {

		// Easy case.
		if (start == end) Arrays.fill(available, true);

		// Standard case.
		if (start < end) {
			for (int i=start; i<end; i++)
				available[i] = true;
		}

		// Loop through midnight.
		else {
			for (int i=0; i<end; i++)
				available[i] = true;
			for (int i=start; i<available.length; i++)
				available[i] = true;
		}
	}

	// Convert minute availability to 30 minute time slots.
	public static int[] getSlots(boolean[] array) {

		int[] slots = new int[48];
		for (int i=0; i<slots.length; i++) {

			// Make sure we can make all 30 minutes before being available for this slot.
			int ans = 1;
			for (int j=0; j<30; j++)
				if (!array[30*i+j]) {
					ans = 0;
					break;
				}
			slots[i] = ans;
		}
		return slots;
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
