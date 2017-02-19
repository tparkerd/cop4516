import java.util.*;

public class perfect {
	public static final boolean DEBUG = false;
	public static void main(String args[]) {
		Scanner stdin = new Scanner(System.in);

		// For each number (case)...
		int nCases = stdin.nextInt();
		for (int i = 0; i < nCases; i++) {
			long cNum = stdin.nextLong(); // Next case value/number
			System.out.println(solve(cNum));
		}
	}

	public static String solve(long value) {
		long runningTotal = 1;
		for (long i = 2; i <= Math.sqrt(value); i++) {
			if (value % i == 0) {
				if (i == Math.sqrt(value))
					runningTotal += i;
				else
					runningTotal += (i + (value / i));
			}
		}
		if (DEBUG) System.out.println(runningTotal);

		if (runningTotal == value)
			return new String("perfect");
		else if (runningTotal < value)
			return new String("defective");
		else if (runningTotal > value)
			return new String("abundant");
		else
			return new String("wrong answer");
	}
}
