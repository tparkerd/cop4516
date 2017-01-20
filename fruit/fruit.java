import java.util.Scanner;
import java.util.Arrays;
import java.math.*;

public class fruit {
  public static final boolean DEBUG = false;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();
    // For each case...
    for (int i = 0; i < nCases; i++) {
      // Get the number of days
      int nDays = stdin.nextInt();
      // Sales
      int[] sales = new int[nDays];
      // Get a list of all the sales
      for (int j = 0; j < nDays; j++) {
        sales[j] = stdin.nextInt();
      }


      // Get the minimum fruit necessary to purchase
      int runningSum = 0;
      int minDailyPurchase = 0;
      for (int j = 0; j < nDays; j++) {
        runningSum += sales[j];
        minDailyPurchase = max(minDailyPurchase, (int)(Math.ceil(runningSum / (j + 1.0))));
      }
      if (DEBUG) System.out.println("Min fruit dailiy: " + minDailyPurchase);

      System.out.print(minDailyPurchase + " ");

      // Get the highest inventory
      int inventory = 0;
      int storage = 0;
      int maxStorage = 0;
      for (int j = 0; j < nDays; j++) {
        inventory = minDailyPurchase + inventory - sales[j];
        // storage = inventory - sales[j];
        if (inventory > maxStorage)
          maxStorage = inventory;
      }

      // System.out.println("Max storage: " + maxStorage);

      System.out.print(maxStorage + "\n");
      // if (DEBUG) System.out.println(Arrays.toString(sales));
    }
  }

  public static int max(int x, int y) {
    if (x > y) return x;
    return y;
  }
}
