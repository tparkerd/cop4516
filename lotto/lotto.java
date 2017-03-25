import java.util.*;

public class lotto {
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    // Rows (n) are the number of digits on the ticket
    int n = 1;
    // Columns are the range of digits, when m = 10, it's 1 to 10, inclusive.
    int m = 2000;

    // Assignment specs states no more than 10 numbers to a ticket, so to be
    // inclusive, n is 11.
    long[][] s = new long[11][m + 1];
    // Where there are no digits on the ticket, there's no valid ticket.
    // Where there is a range of zero, there's no valid ticket.
    // Doing this shifts the indices so you can just use s[2][200] to get
    // the exact number of tickets for a two-number ticket whose numbers range
    // from 1 to 200, inclusive.
    Arrays.fill(s[0], 0);
    for (int i = 0; i < n; i++) s[i][0] = 0;

    // Initialize the base case of just a single digit ticket
    for (int i = 1; i <= n; i++)
      for ( int j = i; j <= m; j++)
        s[i][j] = init(new int[i], j, 0);

    // Precompute all solutions within the range
    // All of row 1 is made up of base cases, so start on the row 2
    for (int i = 2; i < s.length; i++)
      for (int j = (int)Math.pow(2, i - 1); j < s[0].length; j++)
        //      = [Up 1][Left 2^(i - 2)]              + [Same row][Left 2^(i - 1)]
        s[i][j] = s[i-1][j - (int)Math.pow(2, i - 2)] + s[i][j - (int)Math.pow(2, i - 1)];

    // Get the first case
    int digits = stdin.nextInt();
    int range = stdin.nextInt();

    // For each test case...
    int cCase = 1;
    while (digits != 0 && range != 0) {
      System.out.printf("Case %d: n = %d, m = %d, # lists = %d\n", cCase++, digits, range, s[digits][range]);

      // Get next case.
      digits = stdin.nextInt();
      range = stdin.nextInt();
    }
  }

  public static long init(int[] digits, int numDigits, int k) {
    // Base case: not viable start perm
    if (digits.length > numDigits) return 0;

    // Make sure the sub-permutation is valid first
    for (int i = 0; i < k - 1; i++) {
      if ((digits[i] * 2) > digits[i + 1]) {
        return 0;
      }
    }
    if (k == digits.length) {
      return 1;
    }
    long sum = 0;
    for (int i = 1; i <= numDigits; i++) {
      digits[k] = i;
      sum += init(digits, numDigits, k + 1);
    }
    return sum;
  }
}
