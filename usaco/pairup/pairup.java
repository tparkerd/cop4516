import java.io.*;
import java.util.*;
public class pairup {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("pairup.in"));
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("pairup.out")));

    int N = Integer.parseInt(br.readLine());

    // Store all of the cows
    int M = 0;
    ArrayList<Integer> cows = new ArrayList<Integer>();
    for (int i = 0; i < N; i++) {
      String[] line = br.readLine().split(" ");
      int x = Integer.parseInt(line[0]);
      int y = Integer.parseInt(line[1]);
      // Add the individual cows
      for (int j = 0; j < x; j++) {
        if ((x - j) > 0) {
          cows.add(y);
        }
      }
      M += x;
    }

    // Sort the cows
    Collections.sort(cows);

    // System.out.println(cows.toString());

    int candidate = cows.get(0) + cows.get(cows.size() - 1);
    for (int i = 1; i < cows.size() / 2; i++) {
      int tmp = cows.get(i) + cows.get(cows.size() - i - 1);
      if (tmp < candidate) {
        break;
      }
      else {
        candidate = Math.max(tmp, candidate);
      }
    }

		pw.println(candidate);
		pw.close();
	}
}
