import java.util.*;

public class fundraised {
	public static final boolean DEBUG = false;

	public static void main(String args[]){
		Scanner stdin = new Scanner(System.in);
		int nCases = stdin.nextInt();

		// For each case...
		for (int c = 0; c < nCases; c++) {
			int n = stdin.nextInt();
			int capacity = stdin.nextInt();
			long[] dp = new long[capacity + 1];
			long[] weights = new long[n];
			long[] values = new long[n];

			// For each case...
			// Get the value of the items
			for (int i = 0; i < n; i++) {
				values[i] = stdin.nextLong();
			}
			// Get the cost of the items
			for (int i = 0; i < n; i++) {
				weights[i] = stdin.nextLong();
			}

			// Debug: Shows the values and weights
			if (DEBUG) System.out.println(Arrays.toString(values));
			if (DEBUG) System.out.println(Arrays.toString(weights));

			// For each item...
			// Forward loop for as many of each item we want
			for (int i = 0; i < n; i++) {
				// Up until the budget is exceeded...
				for (long w = weights[i]; w <= capacity; w++) {
					// Maximize the importance! woo!
					dp[(int)w] = Math.max(dp[(int)w], dp[(int)(w - weights[i])] + values[i] );
				}
			}

			System.out.println(dp[capacity]);
			// Debug! Shows how the budget is consumed up until max
			if (DEBUG) System.out.println(Arrays.toString(dp));

		}
	}
}
