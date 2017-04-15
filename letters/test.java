import java.util.*;

public class test {
	public static final boolean DEBUG = true;
	public static void main(String[] args) {
		// printCombos(new int[4], 0);
		// printperms(new boolean[4], 0);
		odor(new boolean[4], 0);
		// odormeter(new int[4], 5, 0);
	}

	public static void printCombos(int[] combo, int k) {
		System.out.println(Arrays.toString(combo));

		int start = 0;
		if (k > 0) start = combo[k - 1] + 1;

		for (int i = start; i < combo.length; i++) {
			combo[k] = i;
			printCombos(combo, k + 1);
		}
	}


	public static void printperms(boolean[] used, int k) {
		if (k == used.length) System.out.println(Arrays.toString(used));

		for (int i = 0; i < used.length; i++) {
			if (!used[i]) {
				used[i] = true;
				printperms(used, k + 1);
				used[i] = false;
			}
		}
	}

	public static void odor(boolean[] values, int k) {
		if (k >= values.length) {
			System.out.println(Arrays.toString(values));
			return;
		}

		for (int i = 0; i < 2; i++) {
			values[k] = (i % 2 == 0);
			odor(values, k + 1);
		}
	}

 // public static void odometer(int[] digits, int numDigits, int k) {
 // if (k == digits.length) {
 //
 // return;
 // }
 // for (int i=0; i<NUMDIGITS; i++) {
 // digits[k] =i;
 // odometer(digits, k+1);
 // }
}
