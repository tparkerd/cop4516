/*
Example

- Normal
  Input
    long capacity = 20;
    long[] values = {1, 2, 3, 4, 5};
    long[] weights = {2, 6, 3, 5, 4};

  Call
    Knapsack sack = new Knapsack(values, weights, capacity);
    long answer = sack.solve();

- Limited Inventory
  Input
    long capacity = 20;
    long[] values = {1, 2, 3, 4, 5};
    long[] weights = {2, 6, 3, 5, 4};
    long[] inventory = {1, 1, 1, 2, 5};

  Call
    Knapsack sack = new Knapsack(values, weights, capacity, inventory);
    long answer = sack.solve();
*/

public class Knapsack {
  private long[] values, weights, dp, inventory;
  private long capacity;

  public Knapsack(long[] values, long[] weights, long capacity) {
     // values & weights never change, so this should be fine
    this.values = values; this.weights = weights;
    this.dp = new long[(int)capacity + 1];
    this.capacity = capacity;
  }

  // Limited quantity of each
  public Knapsack(long[] values, long[] weights, long capacity, long[] inventory) {
    this(values, weights, capacity);
    this.inventory = inventory.clone(); // Modified, so we have to clone it
  }

  // Get the most you could spend
  // Duplicates allowed
  public long solve() {
    // For each item...
    for (int i = 0; i < values.length; i++) {
      // for (long j = capacity; j >= weights[i]; j--) { // Don't allow duplicates
      for (long j = weights[i]; j <= capacity; j++) { // Allow duplicates
        dp[(int)j] = Math.max(dp[(int)j], dp[(int)(j - weights[i])] + values[i]);
      }
    }
    return dp[(int)capacity];
  }

  // Solve but with a limited quantity of each
  public long solve() {
    // For each item...
    for (int i = 0; i < values.length; i++) {
      if(inventory[i] <= 0) continue; // skip this item b/c it's exhausted
      for (long j = weights[i]; j <= capacity; j++) {
        dp[(int)j] = Math.max(dp[(int)j], dp[(int)(j - weights[i])] + values[i]);
        inventory[i]--;
      }
    }
    return dp[(int)capacity];
  }
}
