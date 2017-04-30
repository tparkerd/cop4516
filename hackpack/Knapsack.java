/*
Example Input

  long capacity = 20;
  long[] values = {1, 2, 3, 4, 5};
  long[] weights = {2, 6, 3, 5, 4};

Example call

Knapsack sack = new Knapsack(values, weights, capacity);
long answer = sack.solve();

*/

public class Knapsack {
  private long[] values, weights, dp;
  private long capacity;

  public Knapsack(long[] values, long[] weights, long capacity) {
     // values & weights never change, so this should be fine
    this.values = values; this.weights = weights;
    this.dp = new long[(int)capacity + 1];
    this.capacity = capacity;
  }

  // Get the most you could spend
  // Duplicates allowed
  public long solve() {
    // For each item...
    for (int i = 0; i < values.length; i++) {
      // for (long j = capacity; j >= weights[i]; j--) { // Backward loop - don't allow duplicates
      for (long j = weights[i]; j <= capacity; j++) { // Forward loop
        dp[(int)j] = Math.max(dp[(int)j], dp[(int)(j - weights[i])] + values[i]);
      }
    }
    return dp[(int)capacity];
  }
}
