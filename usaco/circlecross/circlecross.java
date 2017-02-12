import java.io.*;
import java.util.*;
public class circlecross {
	public static final boolean DEBUG = false;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("circlecross.in"));
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("circlecross.out")));

		String str = br.readLine();
		char[] a = str.toCharArray();

		if (DEBUG) System.out.println(Arrays.toString(a));


		int[] openStatus = new int[27];
		if (DEBUG) System.out.println(Arrays.toString(openStatus));

		int counter = 0;
		for (int i = 0; i < a.length; i++) {
			char tmpChar = a[i];
			if (openStatus[tmpChar - 'A'] == 0) {
				if (DEBUG) System.out.println("Opening " + tmpChar);
				openStatus[tmpChar - 'A'] = 1;
			} else {
				// Check to see if there are any cows still crossing
				int startIndex = indexOf(a, tmpChar);
				if (DEBUG) System.out.println("Opening index: " + tmpChar + " -> " + startIndex);
				int tmpCount = 0;
				for (int j = startIndex + 1; j < i; j++) {
					if (openStatus[a[j] - 'A'] == 1)
						tmpCount++;
				}
				counter += tmpCount;
				if (DEBUG) System.out.println(tmpCount);
				if (DEBUG) System.out.println("Closing " + tmpChar);
				openStatus[tmpChar - 'A'] = 0;
			}
		}

		pw.println(counter);

		pw.close();
	}

	public static int indexOf(char[] a, char c) {
		for (int i = 0; i < a.length; i++) {
			if (a[i] == c)
				return i;
		}
		return 25;
	}
}
