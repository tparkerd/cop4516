// Reworked version of Arup Guha's Dinic's solution to Museum

import java.util.*;

public class campout {
	public static final boolean DEBUG = false;
	public static final int NUM_STUDENTS = 10;
	public static final int NUM_HOURS = 168;
	public static final int MAX_HOURS = 80;
	public static final int SHIFTS = 7 * 6;

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		int nCases = stdin.nextInt();

		// For each test case...
    for (int c = 0; c < nCases; c++) {
			// 0 = source, s-1 = sink, 1-n guards, n+1 -> n+SHIFTS shifts
			int s = NUM_STUDENTS + 2 + SHIFTS;
			int[][] graph = new int[s][s];
			for (int i=0; i<s; i++)
				Arrays.fill(graph[i], 0);

			// Read in each guard.
			for (int i=0; i < NUM_STUDENTS; i++) {
				int shifts = stdin.nextInt(); // number of busy periods
				int max = MAX_HOURS;
				boolean[] available = new boolean[SHIFTS * 24];
				Arrays.fill(available, false);

				// Fill in guard's availability.
				for (int j=0; j < shifts; j++) {
					int day = stdin.nextInt();
					int start = stdin.nextInt();
					int end = stdin.nextInt();
					fillArray(convert(day, start), convert(day, end), available);
				}

				// Convert the minutes to 30 minute availability slots.
				int[] slots = getSlots(available);

				// These are the flows we want.
				graph[s-2][i] = max/4;
				for (int j=0; j<slots.length; j++) {
					graph[i][NUM_STUDENTS+j] = slots[j];
				}
			}

			if (solvable(graph, NUM_STUDENTS, 3))
				System.out.printf("Case #%d: YES\n\n", c + 1);
			else
				System.out.printf("Case #%d: NO\n\n", c + 1);

			// // Try 0 guards, then 1, etc.
			// if (!solvable(graph, NUM_STUDENTS, 1))
			// 	System.out.println("0");
			// else {
			// 	int tryval = 1;
			// 	while (solvable(graph, NUM_STUDENTS, tryval)) tryval++;
			// 	System.out.println(tryval-1);
			// }
		}
	}

	// Returns true iff we can cover each shift with tryval guards.
	public static boolean solvable(int[][] graph, int n, int tryval) {

		int s = graph.length;
		Dinic myNetFlow = new Dinic(n+SHIFTS);
		for (int i=0; i<s; i++)
			for (int j=0; j<s; j++)
				if (graph[i][j] > 0)
					myNetFlow.add(i, j, graph[i][j], 0);

		// Fill in the flows from all the shifts to the sink.
		for (int j=0; j<SHIFTS; j++)
			myNetFlow.add(n+j, s-1, tryval, 0);

		if (DEBUG) myNetFlow.printGraph();

		int flow = myNetFlow.flow();
		if (DEBUG) System.out.println("Flow: " + flow);
		return flow == tryval*SHIFTS;
	}

	// Returns the number of minutes after midnight represented by s.
	public static int convert(int day, int hour) {
		return ((day - 1) * 24) + hour;
	}

	// Fills available in interval [start, end) if start < end.
	// Or [start, 1439], [0, end) if end < start. Or all slots if start == end.
	public static void fillArray(int start, int end, boolean[] available) {
			for (int i = start; i < end; i++)
				available[i] = true;

			if (DEBUG) System.out.println(Arrays.toString(available));
	}

	// Convert minute availability to 30 minute time slots.
	public static int[] getSlots(boolean[] array) {
		int[] slots = new int[SHIFTS]; // 42 shifts

		// Find start time
		int startIndex = 0;
		int endIndex = array.length - 1;
		while (startIndex < array.length && !array[startIndex]) startIndex++;
		while (endIndex >= 0 && !array[endIndex]) endIndex--;
		// Base case: there were no slots marked as available
		if (startIndex >= endIndex) {
			Arrays.fill(slots, 1);
			return slots;
		}

		int shiftStartIndex = startIndex - (startIndex % 4);
		int shiftEndIndex = endIndex + (4 - (endIndex % 4));
		for (int i = 0; i < slots.length; i++) {
			if (i < shiftStartIndex || i >= shiftEndIndex) {
				slots[i] = 1;
			}
		}

		if (DEBUG) System.out.println(Arrays.toString(slots));

		return slots;
	}
}

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

	@Override
	public String toString() {
		return cap + "";
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

	public void printGraph() {
		for (ArrayList<Edge> list : adj)
			System.out.println(list.toString());
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
