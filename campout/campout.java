// Arup Guha
// 6/21/2012
// Solution to 2009 SER Problem: Museum Guards
// Edited on 3/6/2017 to use Dinic Code shown in class.

import java.util.*;

public class campout {
	public static final boolean DEBUG = true;
	public static final int NUM_STUDENTS = 10;
	public static final int NUM_SHIFTS = 6;

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		int nCases = stdin.nextInt();

		for (int c = 0; c < nCases; c++) {
			// 0 = src, s-1 = sink, 1-n students, n+1 -> shifts
			int s = NUM_STUDENTS + (NUM_SHIFTS * 7 * 24) + 2;
			int[][] graph = new int[s][s];

			// Initialize the availability graph
			for (int i = 0; i < s; i++) {
				Arrays.fill(graph[i], 0);
			}

			// Read in person-by-person
			for (int j = 0; j < NUM_STUDENTS; j++) {
				int nBusyTimes = stdin.nextInt();
				for (int k = 0; k < nBusyTimes; k++) {
					int day = stdin.nextInt();
					int startHour = stdin.nextInt();
					int endHour = stdin.nextInt();
					// Change the day and time to the indices on graph and update them

					int weekStartHour = ((day - 1) * 24) + startHour;
					int weekEndHour = ((day - 1) * 24) + endHour;

					if (DEBUG) System.out.printf("#%d) day(%d) start(%d) end(%d) ws(%d) we(%d)\n", j + 1, day, startHour, endHour, weekStartHour, weekEndHour);

					// 00:00 - 04:00
					if ((startHour > 0 && startHour < 4) || (endHour > 0 && endHour < 0))
						for (int t = weekStartHour; t <= weekEndHour; t++) {
							graph[j][t]++;
						}

					// 04:00 - 08:00
					if ((startHour > 4 && startHour < 8) || (endHour > 4 && endHour < 8))
						for (int t = weekStartHour; t <= weekEndHour; t++) {
							graph[j][t]++;
						}

					// 08:00 - 12:00
					if ((startHour > 8 && startHour < 12) || (endHour > 8 && endHour < 12))
						for (int t = weekStartHour; t <= weekEndHour; t++) {
							graph[j][t]++;
						}

					// 12:00 - 16:00
					if ((startHour > 12 && startHour < 16) || (endHour > 12 && endHour < 16))
						for (int t = weekStartHour; t <= weekEndHour; t++) {
							graph[j][t]++;
						}

					// 16:00 - 20:00
					if ((startHour > 16 && startHour < 20) || (endHour > 16 && endHour < 20))
						for (int t = weekStartHour; t <= weekEndHour; t++) {
							graph[j][t]++;
						}

					// 20:00 - 24:00
					if ((startHour > 20 && startHour < 24) || (endHour > 20 && endHour < 24))
						for (int t = weekStartHour; t <= weekEndHour; t++) {
							graph[j][t]++;
						}
				}

				for (int[] e : graph) {
					if (DEBUG) System.out.println(Arrays.toString(e));
				}

			}



			// // 0 = source, s-1 = sink, 1-n guards, n+1 -> n+48 shifts
			// int s = NUM_STUDENTS + 2 + 168;
			// int[][] graph = new int[s][s];
			// for (int i=0; i<s; i++)
			// 	Arrays.fill(graph[i], 0);
			//
			// // Read in each guard.
			// for (int i=0; i < NUM_STUDENTS; i++) {
			// 	int shifts = stdin.nextInt();
			// 	int max = stdin.nextInt();
			// 	boolean[] available = new boolean[1440];
			// 	Arrays.fill(available, false);
			//
			// 	// Fill in guard's availability.
			// 	for (int j=0; j<shifts; j++) {
			// 		String t1 = stdin.next();
			// 		String t2 = stdin.next();
			// 		fillArray(convert(t1), convert(t2), available);
			// 	}
			//
			// 	// Convert the minutes to 30 minute availability slots.
			// 	int[] slots = getSlots(available);
			//
			// 	// These are the flows we want.
			// 	graph[s-2][i] = max/30;
			// 	for (int j=0; j<slots.length; j++) {
			// 		graph[i][NUM_STUDENTS+j] = slots[j];
			// 	}
			// }
			//
			// // Try 0 guards, then 1, etc.
			// if (!solvable(graph, NUM_STUDENTS, 1))
			// 	System.out.println("0");
			// else {
			// 	int tryval = 1;
			// 	while (solvable(graph, NUM_STUDENTS, tryval)) tryval++;
			// 	System.out.println(tryval-1);
			}
		}
	}

// 	// Returns true iff we can cover each shift with tryval guards.
// 	public static boolean solvable(int[][] graph, int n, int tryval) {
//
// 		int s = graph.length;
// 		Dinic myNetFlow = new Dinic(n+48);
// 		for (int i=0; i<s; i++)
// 			for (int j=0; j<s; j++)
// 				if (graph[i][j] > 0)
// 					myNetFlow.add(i, j, graph[i][j], 0);
//
// 		// Fill in the flows from all the shifts to the sink.
// 		for (int j=0; j<48; j++)
// 			myNetFlow.add(n+j, s-1, tryval, 0);
//
// 		int flow = myNetFlow.flow();
// 		return flow == tryval*48;
// 	}
//
// 	// Returns the number of minutes after midnight represented by s.
// 	public static int convert(String s) {
// 		int hr = 10*(s.charAt(0) - '0') + s.charAt(1) - '0';
// 		int min = 10*(s.charAt(3) - '0') + s.charAt(4) - '0';
// 		return 60*hr + min;
// 	}
//
// 	// Fills available in interval [start, end) if start < end.
// 	// Or [start, 1439], [0, end) if end < start. Or all slots if start == end.
// 	public static void fillArray(int start, int end, boolean[] available) {
//
// 		// Easy case.
// 		if (start == end) Arrays.fill(available, true);
//
// 		// Standard case.
// 		if (start < end) {
// 			for (int i=start; i<end; i++)
// 				available[i] = true;
// 		}
//
// 		// Loop through midnight.
// 		else {
// 			for (int i=0; i<end; i++)
// 				available[i] = true;
// 			for (int i=start; i<available.length; i++)
// 				available[i] = true;
// 		}
// 	}
//
// 	// Convert minute availability to 30 minute time slots.
// 	public static int[] getSlots(boolean[] array) {
//
// 		int[] slots = new int[48];
// 		for (int i=0; i<slots.length; i++) {
//
// 			// Make sure we can make all 30 minutes before being available for this slot.
// 			int ans = 1;
// 			for (int j=0; j<30; j++)
// 				if (!array[30*i+j]) {
// 					ans = 0;
// 					break;
// 				}
// 			slots[i] = ans;
// 		}
// 		return slots;
// 	}
// }

// An edge connects v1 to v2 with a capacity of cap, flow of flow.
class Edge {
	int v1, v2, cap, flow;
	Edge rev;
	Edge(int V1, int V2, int Cap, int Flow) {
		v1 = V1;
		v2 = V2;
		cap = Cap;
		flow = Flow;
	}
}

class Dinic {

	// Queue for the top level BFS.
	public ArrayDeque<Integer> q;

	// Stores the graph.
	public ArrayList<Edge>[] adj;
	public int n;

	// s = source, t = sink
	public int s;
	public int t;


	// For BFS.
	public boolean[] blocked;
	public int[] dist;

	final public static int oo = (int)1E9;

	// Constructor.
	public Dinic (int N) {

		// s is the source, t is the sink, add these as last two nodes.
		n = N; s = n++; t = n++;

		// Everything else is empty.
		blocked = new boolean[n];
		dist = new int[n];
		q = new ArrayDeque<Integer>();
		adj = new ArrayList[n];
		for(int i = 0; i < n; ++i)
			adj[i] = new ArrayList<Edge>();
	}

	// Just adds an edge and ALSO adds it going backwards.
	public void add(int v1, int v2, int cap, int flow) {
		Edge e = new Edge(v1, v2, cap, flow);
		Edge rev = new Edge(v2, v1, 0, 0);
		adj[v1].add(rev.rev = e);
		adj[v2].add(e.rev = rev);
	}

	// Runs other level BFS.
	public boolean bfs() {

		// Set up BFS
		q.clear();
		Arrays.fill(dist, -1);
		dist[t] = 0;
		q.add(t);

		// Go backwards from sink looking for source.
		// We just care to mark distances left to the sink.
		while(!q.isEmpty()) {
			int node = q.poll();
			if(node == s)
				return true;
			for(Edge e : adj[node]) {
				if(e.rev.cap > e.rev.flow && dist[e.v2] == -1) {
					dist[e.v2] = dist[node] + 1;
					q.add(e.v2);
				}
			}
		}

		// Augmenting paths exist iff we made it back to the source.
		return dist[s] != -1;
	}

	// Runs inner DFS in Dinic's, from node pos with a flow of min.
	public int dfs(int pos, int min) {

		// Made it to the sink, we're good, return this as our max flow for the augmenting path.
		if(pos == t)
			return min;
		int flow = 0;

		// Try each edge from here.
		for(Edge e : adj[pos]) {
			int cur = 0;

			// If our destination isn't blocked and it's 1 closer to the sink and there's flow, we
			// can go this way.
			if(!blocked[e.v2] && dist[e.v2] == dist[pos]-1 && e.cap - e.flow > 0) {

				// Recursively run dfs from here - limiting flow based on current and what's left on this edge.
				cur = dfs(e.v2, Math.min(min-flow, e.cap - e.flow));

				// Add the flow through this edge and subtract it from the reverse flow.
				e.flow += cur;
				e.rev.flow = -e.flow;

				// Add to the total flow.
				flow += cur;
			}

			// No more can go through, we're good.
			if(flow == min)
				return flow;
		}

		// mark if this node is now blocked.
		blocked[pos] = flow != min;

		// This is the flow
		return flow;
	}

	public int flow() {
		clear();
		int ret = 0;

		// Run a top level BFS.
		while(bfs()) {

			// Reset this.
			Arrays.fill(blocked, false);

			// Run multiple DFS's until there is no flow left to push through.
			ret += dfs(s, oo);
		}
		return ret;
	}

	// Just resets flow through all edges to be 0.
	public void clear() {
		for(ArrayList<Edge> edges : adj)
			for(Edge e : edges)
				e.flow = 0;
	}
}
