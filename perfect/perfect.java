import java.util.*;

public class perfect {
	public static final boolean DEBUG = true;
	public static void main(String args[]) {
		Scanner stdin = new Scanner(System.in);
		
		// For each number (case)...
		int nCases = stdin.nextInt();
		for (int i = 0; i < nCases; i++) {
			long cNum = stdin.nextLong(); // Next case value/number
			long[] result = euclid(cNum, 0);
			System.out.println(Arrays.toString(result));
			System.out.println(gcd(cNum, cNum));
		}
	}
	
	public static long[] euclid(long a, long b) {
		if (b == 0)
			return new long[] {1, 0, a};
		
		else {
			long q = a / b;
			long r = a % b;
			long[] rec = euclid(b, r);
			return new long[] {rec[1], rec[0] - q * rec[1], rec[2]};
		}
	}

	
	// Let's try GCD instead b/c that makes more sense at the moment
	public static long gcd(long a, long b) {
		if (b == 0)
			if (DEBUG) System.out.println(a);
		else
			if (DEBUG) System.out.println(gcd(b, a % b));
		return b == 0 ? a : gcd(b, a % b);
	}
	
}