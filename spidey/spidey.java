import java.util.*;

public class spidey {
	public static ArrayList<Integer>[] graph;
	public static boolean[] visited;

	public static void main(String args[]){
		Scanner stdin = new Scanner(System.in);
		int nCases = stdin.nextInt();
		
		// For each case...
		for (int i = 0; i < nCases; i++) {
			// Get the number nodes and the edges
			int nVertices = stdin.nextInt();
			// Make a new graph
			graph = new ArrayList[nVertices];
			// Distance array
			visited = new boolean[graph.length];
			Arrays.fill(visited, false);
			for (int j = 0; j < nVertices; j++) {
				graph[j] = new ArrayList<Integer>();
			}
			int nEdges = stdin.nextInt();
			// For each edge, add it to the graph
			for (int j = 0; j < nEdges; j++) {
				int v1 = stdin.nextInt(); // 6
				int v2 = stdin.nextInt(); // 7
				// Add the edge to the graph
				graph[v1].add(v2);
				graph[v2].add(v1);
			}
			
			System.out.println("Show the graph");
			for (int debug = 0; debug < graph.length; debug++) {
				System.out.println(graph[debug].toString());
			}
			
			if (bfs(graph, graph[0].get(0))) {
				System.out.println("Way to go, Spider-Man!");
			} else {
				System.out.println("It's the end of the world!");
			}
			
					
		}
	}
	
	// v is the initial vertex to start the graph, this shouldn't matter
	// if there is only one component in the graph
	@SuppressWarnings("unchecked")
	public static boolean bfs(ArrayList[] graph, int v) {
		int n = graph.length;
		int counter = 0;
		int[] distance = new int[n];
		Arrays.fill(distance, -1);
		visited[n] = true;
		ArrayDeque<Integer> q = new ArrayDeque<Integer>();
		q.offer(v);
		
		while(q.size() > 0) {
			int cur = q.poll();
			for (Integer next : graph[cur]){
				if (distance[next] == -1) {
					distance[next] = distance[cur] + 1;
					q.offer(next);
					counter++;
				}
			}
		}
		
		// Return true if the number of traversed nodes is equal to the
		// acutal number of nodes
		return (counter == n);
	}
}


/*
 * 
 * 
 * 
 2
 6 7
 0 1
 2 3
 4 5
 0 3
 2 1
 2 5
 4 3
 6 4
 0 1 
 2 1
 2 3 
 4 5
 
 
 1
 6 7
 0 1
 2 3
 4 5
 0 3
 2 1
 2 5
 4 3
 
 
 
 
 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * */
 