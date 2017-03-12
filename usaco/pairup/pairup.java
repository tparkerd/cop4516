import java.io.*;
import java.util.*;
public class pairup {
	public static final boolean DEBUG = true;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("pairup.in"));
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("pairup.out")));

    int N = Integer.parseInt(br.readLine());

    // Store all of the cows
    int[][] data = new int[N][2];
    int M = 0;
    for (int i = 0; i < N; i++) {
      String[] line = br.readLine().split(" ");
      data[i][0] = Integer.parseInt(line[0]);
      data[i][1] = Integer.parseInt(line[1]);
      M += data[i][0];
    }

    for (int[] a : data) {
      if (DEBUG) System.out.println(Arrays.toString(a));
    }

    // Gather all the cows so they can be sorted
    int[] cows = new int[M];
    int cownter = 0;
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < data[i][0]; j++) {
        if ((data[i][0] - j) > 0) {
          cows[cownter++] = data[i][1];
        }
      }
    }

    // Sort the cows
    Arrays.sort(cows);

    for (int e : cows) {
      if (DEBUG) System.out.println(e);
    }

    int candidate = cows[0] + cows[cows.length - 1];

    for (int i = 1; i < cows.length / 2; i++) {
      int tmp = cows[i] + cows[cows.length - i - 1];
      if (tmp < candidate)
        break;
      else
        Math.max(tmp, candidate);
    }


		pw.println(candidate);

		pw.close();
	}
}
