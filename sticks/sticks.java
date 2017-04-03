import java.util.*;

public class sticks {
  static int[][] memo;
  static int[][] dim;
  static int n;

  public static int[][][] subDim;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();
    // For each case...
    for (int l = 0; l < nCases; l++) {
      int length = stdin.nextInt();
      n = stdin.nextInt();
      dim = new int[n + 1][2];

      // Fugly code to build all the dimensions
      int[] tmp = new int[n + 2];
      tmp[0] = 0;
      int in = 0;
      int lastIn = 0;
      for (int i = 1; i <= n ; i++) {
        lastIn = in;
        in = stdin.nextInt();
        tmp[i] = in - lastIn;
      }
      tmp[n + 1] = length - in;

      for (int j = 1; j < tmp.length; j++) dim[j - 1][0] = tmp[j];
      for (int j = 0; j < dim.length; j++) dim[j][1] = 1;

      for (int[] c : dim) {
        System.out.println(Arrays.toString(c));
      }

      // Set up memo table.
			memo = new int[n + 1][n + 1];
			for (int i=0; i<=n; i++) Arrays.fill(memo[i], -1);

      System.out.println(minMult(0, dim.length - 1));
    }
  }

  // Run the matrix chain multiplication algorithm adapted for this problem.
  	public int runMCM() {

  		// Will store out intermediate answers to the question:
  		// What is the minimum cost for fusing some contiguous subsequence of
  		// lots? It will also store the dimensions of the new lot.
  		Dimensions[][] minMult = new Dimensions[dim.length][dim.length];


  		// Initialize our matrix to show that it takes no cost for the
  		// individual lots themselves without fusing anything.
  		for (int i=0; i<minMult.length; i++)
  			minMult[i][i] = dim[i];


  		// c+1 stands for the number of lots that are put together. In the first
  		// loop, we are just calculating the cost of putting two lots together.
  		for (int c = 1; c < minMult.length; c++) {

  			for (int o = 0; o < minMult.length-c; o++) {

  				// This will eventually store the minimum cost for putting
  				// together lots o through o+c, assuming that we number the lots
  				// 0 through dim.length-1. The -1 is a flag/sentinel value to
  				// indicate that we don't have a minimum calculated yet.
  				Dimensions min = null;

  				// s stands for the boundary at which we are fusing the two
  				// lots.
  				for (int s=o; s<o+c; s++) {

  					// Make the calculation for creating the lot by fusing
  					// at location s. Here are the three components of the cost:
  					// 1) Cost of first part of the lot.
  					// 2) Cost of the second part of the lot.
  					// 3) Cost of fusing those two lots.

  					// This is the cost of the two lots being fused.
  					int value = minMult[o][s].cost + minMult[s+1][o+c].cost;

  					// This is the additional cost for the fusing. We are obtaining
  					// the minimum dimension of each of the two lots.
  					value += FUSE_FEE_CONSTANT*min(minMult[o][s].length, minMult[o][s].depth)*
  					                           min(minMult[s+1][o+c].length, minMult[s+1][o+c].depth);

  					// These are the length and depth of the fused lot.
  					int my_length = minMult[o][s].length+minMult[s+1][o+c].length;
  					int my_depth = max(minMult[o][s].depth, minMult[s+1][o+c].depth);

  					// No case has been determined, so this is the best seen so far.
  					if (min == null)
  						min = new Dimensions(my_length, my_depth, value);

  					// This case is better than any previous, so update it!
  					else if (value < min.cost)
  						min = new Dimensions(my_length, my_depth, value);
  				}

  				// Here we want to store the new LOT, with its dimensions and
  				// minimum cost.
  				minMult[o][o+c] = min;

  			}
  		}

  		// This is the answer to our question!
  		return minMult[0][minMult.length-1].cost;
  	}

  	// Returns the minimum of a and b.
  	public static int min(int a, int b) {
  		if (a < b)
  			return a;
  		return b;
  	}

  	// Returns the maximum of a and b.
  	public static int max(int a, int b) {
  		if (a > b)
  			return a;
  		return b;
  	}
}
