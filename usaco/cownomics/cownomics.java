import java.io.*;
import java.util.*;
public class cownomics {
	public static final boolean DEBUG = true;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("cownomics.in"));
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cownomics.out")));

		int N, M;
		String[] line = br.readLine().split(" ");
		N = Integer.parseInt(line[0]);
		M = Integer.parseInt(line[1]);

		String spottedLCS;
		String spotTmp = br.readLine();
		for (int i = 1; i < N; i++) {
			String nextSpotGenes = br.readLine();
			spotTmp = lcs(spotTmp, nextSpotGenes);
		}

		System.out.println(spotTmp);

		String plainLCS;
		for (int i = 0; i < N; i++) {

		}

		pw.close();
	}


	public static String lcs(String spot, String plain) {
		System.out.println("Testing: " + spot + " && "  + plain);
		int i, j;
		int sLen = spot.length();
		int pLen = plain.length();
		int[][] grid = new int[sLen + 1][pLen + 1];
		String tmp = new String();

		for (i = 0; i <= sLen; i++) grid[i][0] = 0;
		for (i = 0; i <= pLen; i++) grid[0][i] = 0;

		// Fill in values

		for (i = 1; i <= sLen; i++) {
			for (j = 1; j <= pLen; j++) {
				if (spot.charAt(i - 1) == plain.charAt(j - 1)) {
					grid[i][j] = 1 + grid[i - 1][j - 1];
				}
				else {
					grid[i][j] = Math.max(grid[i][j-1], grid[i-1][j]);
				}
			}
		}

		for (i = 0; i < sLen; i++)
			System.out.println(Arrays.toString(grid[i]));

		// Rebuild String
		String ans = new String();
		int x = sLen;
		int y = pLen;
		while (x > 0 && y > 0) {
			// Go left if equal
			if (grid[x][y] == grid[x][y-1]) {
				y--;
			// If can't go left, try going up
			} else if (grid[x][y] == grid[x-1][y]) {
				x--;
			} else {
				ans += spot.charAt(x - 1);
				x--;
				y--;
			}
		}

		ans = new StringBuilder(ans).reverse().toString();

		return ans;

	}
}
