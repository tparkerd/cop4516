import java.util.*;

public class alphabet_2 {
  // Queue for the top level BFS.
	public ArrayDeque<Integer> q;

	// Stores the graph.
	public ArrayList<edge>[] adj;
	public int n;

	// s = source, t = sink
	public int s;
	public int t;

	// For BFS.
	public boolean[] blocked;
	public int[] dist;

	final public static int oo = (int)1E9;


  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = Integer.parseInt(stdin.nextLine());

    // For each case...
    for (int i = 0; i < nCases; i++) {
      // Get the RED and GREEN letters

    }
  }



  public int flow() {
    clear();
    int ret = 0;

    while(bfs()) {
      Arrays.fill(blocked, false);

      ret += dfs(s, oo);
    }
  }

  public void clear() {
    for (ArrayList<edge> edges : adj) {
      for (edge e : edges) {
        e.flow = 0;
      }
    }
  }
}

class edge {
  int v1, v2, cap, flow;
  edge rev;
  edge(int V1, int V2, int Cap, int Flow) {
    this.v1 = V1;
    this.v2 = V2;
    this.cap = Cap;
    this.flow = Flow;
  }
}
