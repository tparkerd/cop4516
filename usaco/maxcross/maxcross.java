import java.io.*;
import java.util.*;
public class maxcross {

	public static int N, // Number of crosswalks
								 		K, // Continugous block length
										B; // Number of broken crosswalks

	public static ArrayList<Perm> perms;


	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("maxcross.in"));
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("maxcross.out")));

		String[] data = br.readLine().split(" ");
		N = Integer.parseInt(data[0]);
		K = Integer.parseInt(data[1]);
		B = Integer.parseInt(data[2]);

		// Signals
		int[] broken = new int[B];

		int min = 9999999;
		// 0 = fixed, 1 = broken
		for (int i = 0; i < B; i++) {
			int index = Integer.parseInt(br.readLine()) - 1;
			broken[i] = index + 1;
		}

		Arrays.sort(broken);

		perms = new ArrayList<Perm>();
		dfs(broken, new boolean[B], 0);

		// Sort the permutations, from least fixed to most fixed
		Collections.sort(perms);

		// For each permutation, make an array of those crosswalks and see
		// if it satisfies the K sized block of crosswalks
		for (int i = 0; i < perms.size(); i++) {
			Perm p = perms.get(i);
			int[] curCase = new int[N];
			// Break the appropriate crosswalks
			for (int j = 0; j < p.data.length; j++) {
				if (p.data[j])
				curCase[broken[j] - 1] = 1;
			}

			// For each of the successful blocks, see which one requires the least
			// number of fixed
			int range = findRange(curCase);
			if (range >= K) {
				min = Math.min(min, p.nFixed);
				break;
			}
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
		Perm tmp = new Perm(used);
		perms.add(tmp);

	}
}

class Perm implements Comparable<Perm>{
	boolean[] data;
	int nFixed;
	public Perm(boolean[] u) {
		boolean[] tmp = new boolean[u.length];
		for (int i = 0; i < u.length; i++) {
			tmp[i] = u[i];
		}
		this.data = tmp;

		this.nFixed = this.numFixed();
	}

	private int numFixed() {
		int counter = 0;
		for (int i = 0; i < this.data.length; i++)
			if (!this.data[i])
				counter++;
		return counter;
	}

	public int compareTo(Perm other) {
		if (this.nFixed < other.nFixed)
			return 1;
		else if (this.nFixed < other.nFixed)
			return -1;
		else
			return 0;
	}

	public String toString() {
		return Arrays.toString(data) + " --> " + nFixed;
	}
}
