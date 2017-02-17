import java.util.*;

public class perfect {
	public static final boolean DEBUG = false;
	public static void main(String args[]) {
		Scanner stdin = new Scanner(System.in);
			
		// For each number (case)...
		int nCases = stdin.nextInt();
		for (int i = 0; i < nCases; i++) {
			long cNum = stdin.nextLong(); // Next case value/number
			long[] result = euclid(cNum, 0);
			//System.out.println(Arrays.toString(result));
			//System.out.println(gcd(cNum, cNum));
			if (DEBUG) System.out.println(findFactors(cNum).toString());
			System.out.println(solve(findFactors(cNum), cNum));
		}
	}
	
	// Not sure if it's extended euclid
	public static long[] euclid(long a, long b) {
		if (b == 0) {
			if (DEBUG) System.out.printf("(%d, %d, %d)\n", 1, 0 , a);
			return new long[] {1, 0, a};
		}
		
		else {
			long q = a / b;
			long r = a % b;
			long[] rec = euclid(b, r);
			if (DEBUG) System.out.printf("(%d, %d, %d)\n", rec[1], rec[0] - q * rec[1] , rec[2]);
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
	
	// Let's brute force instead! I know this isn't the solution he wants, but I want to know if I actually understand
	// How am I supposed to optimally get all the factors, not just the prime factors...blegh
	public static ArrayList<Long> findFactors(Long a) {
		ArrayList<Long> tmp = new ArrayList<Long>(); // Holds all the valid factors
		for (long i = 2; i < a; i++) {
			if (a % i == 0)
				tmp.add(i);
		}
		return tmp;
	}
	
	public static String solve(ArrayList<Long> a, Long v) {
		long total = 0;
		for (Long value : a) {
			total += value;
		}
	
		if (total > v)
			return new String("abundant");
		else if (total < v) {
			return new String("defective");
		}
		return new String("perfect");
	}
	
}