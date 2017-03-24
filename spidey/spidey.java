import java.util.*;

public class spidey {
	public static final boolean DEBUG = false;
	public static ArrayList<Integer>[] graph;
	public static boolean[] visited;
	public static String successString = new String("Way to go, Spider-Man!\n");
	public static String failureString = new String("It's the end of the world!\n");

	@SuppressWarnings("unchecked")
	public static void main(String args[]){
		Scanner stdin = new Scanner(System.in);
		int nCases = stdin.nextInt();

		// For each case...
		caseLoop: // label so I can skip case if it has same-side edges
		for (int i = 0; i < nCases; i++) {
			if (DEBUG) System.out.printf("%d] ", i + 1);
			// Get the number nodes and the edges
			int nVertices = stdin.nextInt();
			// Base case: if there aren't any vertices
			if (nVertices == 0) {
				if (DEBUG) System.out.println("Zero vertices");
				// Read in the number of edges to move along to the next case
				stdin.nextInt();
				System.out.println(failureString);
				continue;
			}
			// Make a new graph
			graph = new ArrayList[nVertices];
			for (int j = 0; j < nVertices; j++) {
				graph[j] = new ArrayList<Integer>();
			}
			int nEdges = stdin.nextInt();
			// For each edge, add it to the graph
			for (int j = 0; j < nEdges; j++) {
				int v1 = stdin.nextInt();
				int v2 = stdin.nextInt();
				// Check if there is any web that connects same sides
				if ((v1 % 2) == (v2 % 2)) {
					System.out.println(failureString);
					continue caseLoop;
				}

				// Add the edge to the graph
				graph[v1].add(v2);
				graph[v2].add(v1);
			}

			if (DEBUG) System.out.println("Show the graph");
			for (int debug = 0; debug < graph.length; debug++) {
				if (DEBUG) System.out.println((debug) + ") " +graph[debug].toString());
			}

			if (bfs(graph)) {
				System.out.println(successString);
			} else {
				System.out.println(failureString);
			}
		}
	}

	// v is the initial vertex to start the graph, this shouldn't matter
	// if there is only one component in the graph
	@SuppressWarnings("unchecked")
	public static boolean bfs(ArrayList[] graph) {
		int n = graph.length;
		int counter = 1; // since we automatically assume that the
		int[] distance = new int[n];
		Arrays.fill(distance, -1);
		distance[0] = 0;
		ArrayDeque<Integer> q = new ArrayDeque<Integer>();
		q.offer(0);
		if (DEBUG) System.out.println("Start at " + 0);

		while(q.size() > 0) {
			if (DEBUG) System.out.println("Q: " + q.toString());
			int cur = q.poll();
			if (DEBUG) System.out.println("Pop:  " + cur);
			for (Integer next : (ArrayList<Integer>)graph[cur]) {
				if (distance[next] == -1) {
					distance[next] = distance[cur] + 1;
					q.offer(next);
					if (DEBUG) System.out.println("Push: " + next);
					counter++;
				}
				else {
					if (DEBUG) System.out.println(next + " has been visited");
				}
			}
		}

		if (DEBUG) System.out.println(Arrays.toString(distance));

		if (DEBUG) System.out.println("Counter: " + counter);
		// Return true if the number of traversed nodes is equal to the
		// acutal number of nodes
		return (counter == n);
	}
}

/*

	Test cases

	Case 1: Complete valid graph (SUCCESS)
	6 7
	0 1
	2 3
	4 5
	0 3
	2 1
	2 5
	4 3

	Case 2: Two components, invalid (FAILURE)
	6 4
	0 1
	2 1
	2 3
	4 5

	Case 3: Valid graph, zig-zag 0>1>2>3>4>5 (SUCCESS)
	6 6
	0 1
	1 2
	2 3
	3 4
	4 5
	0 3

	Case 4: Valid zig-zag, but has a same-side edge to invalidate it 0>1>2>3>4>5 (FAILURE)
	6 6
	0 1
	1 2
	2 3
	3 4
	4 5
	0 2

	Case 5: Six nodes, but no edge, so empty graph (FAILURE)
	6 0

	Case 6: No nodes, no edges (FAILURE?) - can't cross crater, so fail?
	0 0

	Case 7: Another valid graph (SUCCESS)
	5 5
	0 1
	0 3
	1 2
	2 3
	3 4

	Case 8: Two-node valid graph (SUCCESS)
	2 2
	0 1
	1 0

	Case 9: Invalid, just horizontal lines (FAILURE)
	6 3
	0 1
	2 3
	4 5

	Case 10: Invalid, small graph, self connecting node (FAILURE)
	3 3
	0 0
	0 1
	1 2

	Case 11: One node (FAILURE?) Fails b/c he can't cross the crater?
	1 0




*/
