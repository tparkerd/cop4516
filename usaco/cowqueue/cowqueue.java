import java.io.*;
import java.util.*;
public class cowqueue {
	public static final boolean DEBUG = false;
	public static Queue<Cow> q;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("cowqueue.in"));
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowqueue.out")));
		int nCows = Integer.parseInt(br.readLine());

		Cow[] ca = new Cow[nCows];
		for (int i = 0; i < nCows; i++) {
			String[] tmp = br.readLine().split(" ");
			int start = Integer.parseInt(tmp[0]);
			int duration = Integer.parseInt(tmp[1]);
			Cow tmpCow = new Cow(start, duration);
			if (DEBUG) System.out.println(tmpCow);
			ca[i] = tmpCow;
		}

		if (DEBUG) System.out.println(Arrays.toString(ca));
		Arrays.sort(ca);
		if (DEBUG) System.out.println(Arrays.toString(ca));

		q = new LinkedList<Cow>();
		for (Cow c : ca) {
			q.offer(c);
		}

		if (DEBUG) System.out.print("Queue: ");
		if (DEBUG) System.out.println(q.toString());

		// Ending time of the last cow
		int end = 0;
		while (!q.isEmpty()) {
			// Get the next cow to be questioned
			Cow tmp = q.poll();

			// See if we have to wait for the next cow
			if (tmp.arrive > end)
				end = tmp.arrive + tmp.duration;
			else
				end += tmp.duration;
		}

		if (DEBUG) System.out.println(end);

		pw.println(end);


		pw.close();
	}
}

class Cow implements Comparable<Cow> {
	int arrive;
	int duration;
	public Cow(int a, int d) {
		this.arrive = a;
		this.duration = d;
	}

	public int compareTo(Cow other) {
		if (this.arrive < other.arrive)
			return -1;
		else if (this.arrive > other.arrive)
			return 1;
		return 0;
	}

	@Override
	public String toString() {
		return "("+ this.arrive + ", " + this.duration + ")";
	}
}
