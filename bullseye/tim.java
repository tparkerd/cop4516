// Solution candidate for Bullseye for COP 4516, Spring 2017

import java.util.*;
import java.math.BigInteger;

public class tim {
  public static final boolean DEBUG = false;
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();

    // For each case...
    for (int i = 1; i <= nCases; i++) {
      BigInteger r = stdin.nextBigInteger();
      BigInteger ink = stdin.nextBigInteger();
      System.out.printf("Case #%d: %d\n", i, solve(r, ink));
    }
  }

  public static long solve(BigInteger r, BigInteger ink) {
    // Initialize the bounds of specified by the spec
    long lo, mid, hi;
    lo = 0;
    hi = (long)1e18;

    // Fourth attempt~! Hope this works.
    // So long as the upper bound is higher than the lower bound
    while (hi > lo) {
      // Get the mid point to make a guess
      mid = (hi + lo) / 2;
      if (DEBUG) System.out.println(guess(r, mid));
      // Check to see if there is enough ink
      // Like Arup said, make sure to use <= to get an exact amount of ink
      // If not, it won't 'draw' a ring with the exact amount of ink necessary
      if (guess(r, mid).compareTo(ink) <= 0)
        // Too low! Try more rings!
        // Move up the lower bound to the one after the previous mid
        lo = mid + 1;
      else
        // Too high! Try fewer rings!
        // Move down the upper bound to before the previous mid
        // Not the one before, because you might miss one
        hi = mid; //
    }
    return lo - 1;
}

  // Attempt to draw this many rings, and return the amount of ink used
  // r = radius, n = nth ring to draw
  public static BigInteger guess(BigInteger r, long n) {
    BigInteger two = new BigInteger("2");
    BigInteger N = new BigInteger(String.valueOf(n));
    // First term: 1 = r = inner radius, R = outer radius = r + 1
    // A = πr² = π(R² - r²) = π((r + 1)² - r²) = π(2r + 1) = 3π
    // Second term: r = 3 (skipping one 1 cm ring)
    // A′ = π(2r′ + 1) = π(2(r + 2) + 1) = 5π
    // Series representation: π(2n² + n(2r - 1)) <~ took ages -_-
    //                    (2n+2r-1) * n
    return two.multiply(N).add(two.multiply(r).subtract(BigInteger.ONE)).multiply(N);
  }
}
