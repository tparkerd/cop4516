import java.io.*;
import java.util.*;
public class helpcross {
	public static final boolean DEBUG = false;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("helpcross.in"));
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("helpcross.out")));

		int nChickens, nCows;
		String[] counts = br.readLine().split(" ");
		nChickens = Integer.parseInt(counts[0]);
		nCows = Integer.parseInt(counts[1]);

		if (DEBUG) System.out.println("(" + nChickens + ", " + nCows + ")");

		Chicken[] pollos = new Chicken[nChickens];
		Cow[] vacas = new Cow[nCows];

		// Read in the chickens
		for (int i = 0; i < nChickens; i++) {
			pollos[i] = new Chicken(Integer.parseInt(br.readLine()));
		}

		if (DEBUG) System.out.println(Arrays.toString(pollos));
		Arrays.sort(pollos);
		if (DEBUG) System.out.println("Sorted: " + Arrays.toString(pollos));

		// Read in the cows
		for (int i = 0; i < nCows; i++) {
			String[] tmp = br.readLine().split(" ");
			int start = Integer.parseInt(tmp[0]);
			int end = Integer.parseInt(tmp[1]);
			vacas[i] = new Cow(start, end);
		}

		if (DEBUG) System.out.println(Arrays.toString(vacas));
		Arrays.sort(vacas);
		if (DEBUG) System.out.println("Sorted: " + Arrays.toString(vacas));

		int counter = 0;
		for (int i = 0; i < nCows; i++) {
			// Chicken check!
			for (int j = 0; j < nChickens; j++) {
				if ((pollos[j].time >= vacas[i].start) && (pollos[j].time <= vacas[i].end) && !pollos[j].used) {
					pollos[j].used = true;
					counter++;
					break;
				}
			}
		}

		if (DEBUG) System.out.println(Arrays.toString(pollos));
		if (DEBUG) System.out.println(Arrays.toString(vacas));


		pw.println(counter);

		pw.close();
	}
}

class Chicken implements Comparable<Chicken> {
	int time;
	boolean used;
	public Chicken(int t) {
		this.time = t;
		this.used = false;
	}
	@Override
	public String toString() {
		return "(" + this.time + ", " + this.used + ")";
	}

	public int compareTo(Chicken other) {
		if (this.time > other.time)
			return 1;
		else if (this.time < other.time)
			return -1;
		else
			return 0;
	}
}

class Cow implements Comparable<Cow> {
	int start;
	int end;
	public Cow(int s, int e) {
		this.start = s;
		this.end = e;
	}

	@Override
	public String toString() {
		return "(" + this.start + ", " + this.end + ")";
	}

	public int compareTo(Cow other) {
		if (this.end > other.end)
			return 1;
		else if (this.end < other.end)
			return -1;
		else
			return 0;
	}
}
