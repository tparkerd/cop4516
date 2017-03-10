import java.util.*;

public class alphabet {
  public static final boolean DEBUG = true;

	private Edge[][] adjMat;
	private int source;
	private int dest;


  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = Integer.parseInt(stdin.nextLine());

    // For each case...
    for (int i = 0; i < nCases; i++) {
      // Get all the letters and split into two
      String[] letters = stdin.nextLine().split(" ");
      char[] redLetters = letters[0].toCharArray();
      char[] greenLetters = letters[1].toCharArray();
      int[][] adjMatrix = new int[28][28];

      // Fill in the connection for the letters
      // Connect the source to the red letters
      Arrays.fill(adjMatrix[26], 1);
      // Disconnect source from sink directly
      adjMatrix[26][27] = 0;
      // Diconnect source from itself
      adjMatrix[26][26] = 0;
      // Connect all green letters to sink
      for (int q = 0; q < 26; q++) {
        adjMatrix[q][27] = 1;
      }



      // Fill in actual connections between red and green letters
      for (int r = 0; r < redLetters.length; r++) {
        for (int g = 0; g < greenLetters.length; g++) {
          if (Math.abs(redLetters[r] - greenLetters[g]) > 2)
            adjMatrix[redLetters[r] - 'a'][greenLetters[g] - 'a'] = 1;
        }
      }

      if (DEBUG) System.out.println("-: [a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, >, *]");

      // Debug, print the adjacency matrix
      for (int q = 0; q < adjMatrix.length; q++) {
        if (q == 26) {
          if (DEBUG) System.out.println(">: " + Arrays.toString(adjMatrix[q]));
        } else if (q == 27) {
          if (DEBUG) System.out.println("*: " + Arrays.toString(adjMatrix[q]));
        }
        else {
          if (DEBUG) System.out.println((char)(q + 'a') + ": " + Arrays.toString(adjMatrix[q]));
        }
      }


      networkflow graph = new networkflow(adjMatrix, 0, 27);
      int ans = graph.getMaxFlow();
      System.out.println(ans);


    }
  }
}









  class networkflow {

  	private Edge[][] adjMat;
  	private int source;
  	private int dest;

  	// All positive entries in flows should represent valid flows
  	// between vertices. All other entries must be 0 or negative.
  	public networkflow(int[][] flows, int start, int end) {

  		source = start;
  		dest = end;
  		adjMat = new Edge[flows.length][flows.length];

  		for (int i=0; i<flows.length; i++) {
  			for (int j=0; j<flows[i].length; j++) {

  				// Fill in this flow.
  				if (flows[i][j] > 0)
  					adjMat[i][j] = new Edge(flows[i][j]);
  				else
  					adjMat[i][j] = null;
  			}
  		}
  	}

  	public ArrayList<direction> findAugmentingPath() {

  		// This will store the previous node visited in the BFS.
  		direction[] prev = new direction[adjMat.length];
  		boolean[] inQueue = new boolean[adjMat.length];
  		for (int i=0; i<inQueue.length; i++)
  			inQueue[i] = false;

  		// The source has no previous node.
  		prev[source] = new direction(-1, true);

  		LinkedList<Integer> bfs_queue = new LinkedList<Integer>();
  		bfs_queue.offer(new Integer(source));
  		inQueue[source] = true;

  		// Our BFS will go until we clear out the queue.
  		while (bfs_queue.size() > 0) {

  			// Add all the new neighbors of the current node.
  			Integer next = bfs_queue.poll();

  			// Find all neighbors and add into the queue. These are forward edges.
  			for (int i=0; i<adjMat.length; i++)
  				if (!inQueue[i] && adjMat[next][i] != null && adjMat[next][i].maxPushForward() > 0) {
  					bfs_queue.offer(new Integer(i));
  					inQueue[i] = true;
  					prev[i] = new direction(next, true);
  				}

  			// Now look for back edges.
  			for (int i=0; i<adjMat.length; i++)
  				if (!inQueue[i] && adjMat[i][next] != null && adjMat[i][next].maxPushBackward() > 0) {
  					bfs_queue.offer(new Integer(i));
  					inQueue[i] = true;
  					prev[i] = new direction(next, false);
  				}
  		}

  		// No augmenting path found.
  		if (!inQueue[dest])
  			return null;

  		ArrayList<direction> path = new ArrayList<direction>();

  		direction place = prev[dest];

  		direction dummy = new direction(dest, true);
  		path.add(dummy);

  		// Build the path backwards.
  		while (place.prev != -1) {
  			path.add(place);
  			place = prev[place.prev];
  		}

  		// Reverse it now.
  		Collections.reverse(path);

  		return path;
  	}

  	// Run the Max Flow Algorithm here.
  	public int getMaxFlow() {

  		int flow = 0;

  		ArrayList<direction> nextpath = findAugmentingPath();

  		// Loop until there are no more augmenting paths.
  		while (nextpath != null) {

  			// Check what the best flow through this path is.
  			int this_flow = Integer.MAX_VALUE;
  			for (int i=0; i<nextpath.size()-1; i++) {

  				if (nextpath.get(i).forward) {
  					this_flow = Math.min(this_flow, adjMat[nextpath.get(i).prev][nextpath.get(i+1).prev].maxPushForward());
  				}
  				else {
  					this_flow = Math.min(this_flow, adjMat[nextpath.get(i+1).prev][nextpath.get(i).prev].maxPushBackward());
  				}
  			}

  			// Now, put this flow through.
  			for (int i=0; i<nextpath.size()-1; i++) {

  				if (nextpath.get(i).forward) {
  					adjMat[nextpath.get(i).prev][nextpath.get(i+1).prev].pushForward(this_flow);
  				}
  				else {
  					adjMat[nextpath.get(i+1).prev][nextpath.get(i).prev].pushBack(this_flow);
  				}
  			}

  			// Add this flow in and then get the next path.
  			flow += this_flow;
  			nextpath = findAugmentingPath();

  		}

  		return flow;
  	}
  }

  class Edge {

  	private int capacity;
  	private int flow;

  	public Edge(int cap) {
  		capacity = cap;
  		flow = 0;
  	}

  	public int maxPushForward() {
  		return capacity - flow;
  	}

  	public int maxPushBackward() {
  		return flow;
  	}

  	public boolean pushForward(int moreflow) {

  		// We can't push through this much flow.
  		if (flow+moreflow > capacity)
  			return false;

  		// Push through.
  		flow += moreflow;
  		return true;
  	}

  	public boolean pushBack(int lessflow) {

  		// Not enough to push back on.
  		if (flow < lessflow)
  			return false;

  		flow -= lessflow;
  		return true;
  	}
  }

  class direction {

  	public int prev;
  	public boolean forward;

  	public direction(int node, boolean dir) {
  		prev = node;
  		forward = dir;
  	}

  	public String toString() {
  		if (forward)
  			return "" + prev + "->";
  		else
  			return "" + prev + "<-";
  	}
  }
