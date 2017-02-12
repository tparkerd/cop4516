import java.io.*;
import java.util.*;
public class crossroad {
	public static final boolean DEBUG = false;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("crossroad.in"));
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("crossroad.out")));

		// Get number of observations
		int n = Integer.parseInt(br.readLine());

		if (DEBUG) System.out.println("Number of observations: " + n);

		Integer[] cows = new Integer[11];

		if (DEBUG) System.out.println(Arrays.toString(cows));

		// Cross counter
		int counter = 0;
		for (int i = 0; i < n; i++) {
			String[] observation = br.readLine().split(" ");
			int id = Integer.parseInt(observation[0]);
			int side = Integer.parseInt(observation[1]);
			if (DEBUG) System.out.println(Arrays.toString(observation));

			if (cows[id] == null) {
				cows[id] = side;
			} else {
				if (cows[id] != side) {
					counter++;
					cows[id] = side;
				}
			}
		}


		if (DEBUG) System.out.println(Arrays.toString(cows));

		pw.println(counter);

		pw.close();
	}
}
