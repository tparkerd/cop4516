import java.io.*;
import java.util.*;
public class maxcross {
	public static final boolean DEBUG = false;

	public static int N, // Number of crosswalks
								 		K, // Continugous block length
										B; // Number of broken crosswalks

	public static ArrayList<boolean[]> perms;


	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("maxcross.in"));
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("maxcross.out")));

		String[] data = br.readLine().split(" ");
		N = Integer.parseInt(data[0]);
		K = Integer.parseInt(data[1]);
		B = Integer.parseInt(data[2]);

		if (DEBUG) System.out.printf("Case: %d %d %d\n", N, K, B);

		// Signals
		int[] signals = new int[N];


		if (DEBUG) System.out.println("Signals: " + Arrays.toString(signals));

		int[] broken = new int[B];

			int min = 10000000;
		// 0 = fixed, 1 = broken
		for (int i = 0; i < B; i++) {
			int index = Integer.parseInt(br.readLine()) - 1;
			signals[index] = 1;
			broken[i] = index + 1;
		}

		if (DEBUG) System.out.println(Arrays.toString(broken));
		Arrays.sort(broken);
		if (DEBUG) System.out.println(Arrays.toString(broken));

		if (DEBUG) System.out.println("Broken: " + Arrays.toString(signals));


		int result = -1;
		if (DEBUG) System.out.println(result);

		perms = new ArrayList<boolean[]>();
		dfs(broken, new boolean[B], 0);
		for (int i = 0; i < perms.size() - 1; i++) {
			boolean[] usedPerm = perms.get(i);
			if (DEBUG) System.out.println(Arrays.toString(usedPerm));
			// For each permutation, make an array of those crosswalks and see
			// if it satisfies the K sized block of crosswalks
			int[] curCase = new int[N];
			for (int j = 0; j < usedPerm.length; j++) {
				if (usedPerm[j])
				curCase[broken[j] - 1] = 1;
			}




			// For each of the successful blocks, see which one requires the least
			// number of fixed
			if (DEBUG) System.out.println(Arrays.toString(curCase));
			if (DEBUG) System.out.println(numFixed(usedPerm));
			int range = findRange(curCase);
			if (range >= K)
				min = Math.min(min, numFixed(usedPerm));

			if (DEBUG) System.out.println(range);


		}


		pw.println(min);

		pw.close();
	}


	public static int findRange(int[] a) {
		int max = -1;

		int counter = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] == 1) {
				max = Math.max(max, counter);
				counter = 0;
			}
			else if (i == a.length - 1) {
				max = Math.max(max, counter + 1);
			}
			else
				counter++;
		}


		return max;
	}

	public static void dfs(int[] values, boolean[] used, int start) {

		// For each element start from the first available, valid option
		for (int i = start; i < used.length; i++) {
			if (!used[i]) {
				used[i] = true;
				dfs(values, used, start + 1);
				used[i] = false;
			}
		}
		boolean[] tmp = new boolean[used.length];
		for (int j = 0; j < used.length; j++) {
			tmp[j] = used[j];
		}
		perms.add(tmp);

	}

	public static int numFixed(boolean[] a) {
		int counter = 0;
		for (int i = 0; i < a.length; i++)
			if (a[i])
				counter++;

		return counter;
	}

}
