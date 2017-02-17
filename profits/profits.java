import java.util.*;

public class profits {
	public static final boolean DEBUG = false;
	
	public static void main(String args[]) {
		Scanner stdin = new Scanner(System.in);
		
		// For each test case...
		int nDays = stdin.nextInt();;
		int[] days;
		while (nDays != 0) {
			// Collect all of the days, however this may be unnecessary
			days = new int[nDays];
			for (int i = 0; i < nDays; i++) {
				days[i] = stdin.nextInt();
			}
			
			if (DEBUG) System.out.println(nDays + ": " + Arrays.toString(days));
						
						
			if (DEBUG) System.out.println(Arrays.toString(days));
			System.out.println(MCSS(days));
			

			// Get the next test case
			nDays = stdin.nextInt();
			
		}
	}
	
	public static int MCSS(int[] a) {
		int max = 0, sum = 0, start = 0, end = 0, i = 0;
		
		int tmp = -999999;
		for (int j = 0; j < a.length; j++)
			if (a[j] > tmp && a[j] < 0) 
				tmp = a[j];
		
		if (tmp < max)
			max = tmp;
		else 
			max = 0;
		
		if (DEBUG) System.out.println("Tmp: " + tmp);
		
		for (int j = 0; j < a.length; j++) {
			sum += a[j];
			
			if (sum > max) {
				max = sum;
				start = i;
				end = j;
			}
			else if (sum < 0) {
				i = j + 1;
				sum = 0;
			}
		}
		return max;
	}
	
}

	
	