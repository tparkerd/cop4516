// Arup Guha
// 1/18/2016
// Solution to 2016 Proposed Mercer Problem: Lucas's Letters

import java.util.*;

public class prob9 {

	// Important input information.
	public static ArrayList[] scores;
	public static int tileSum;
	public static int numWords;
	public static String[] words;

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		int numCases = stdin.nextInt();

		// Process each case.
		for (int loop=0; loop<numCases; loop++) {

			// Create array of lists to store each tile score.
			scores = new ArrayList[26];
			for (int i=0; i<26; i++)
				scores[i] = new ArrayList<Integer>();

			// Add in each tile score to the appropriate list.
			int numTiles = stdin.nextInt();
			tileSum = 0;
			for (int i=0; i<numTiles; i++) {
				int index = stdin.next().charAt(0) - 'a';
				int tmp = stdin.nextInt();
				tileSum += tmp;
				scores[index].add(tmp);
			}

			// Sort in reverse order.
			for (int i=0; i<26; i++) {
				Collections.sort(scores[i]);
				Collections.reverse(scores[i]);
			}

			// Make cumulative frequency array.
			for (int i=0; i<26; i++) {
				for (int j=1; j<scores[i].size(); j++) {
					ArrayList<Integer> list = (ArrayList<Integer>)scores[i];
					list.set(j, list.get(j-1)+list.get(j));
				}
			}

			// Read in all of the words.
			numWords = stdin.nextInt();
			words = new String[numWords];
			for (int i=0; i<numWords; i++)
				words[i] = stdin.next();

			// Solve this problem instance.
			System.out.println(solve());
		}
	}

	// Returns the best score for this problem instance.
	public static int solve() {

		// This is what you get by playing nothing.
		int bestScore = -tileSum;

		// Try each subset of words.
		for (int mask=0; mask<(1<<numWords); mask++) {

			// Get the sum of letter frequencies for this subset.
			int[] freq = getFreq(mask);

			// If this set of letter frequencies can't be achieved, skip it.
			if (invalid(freq)) continue;

			// Update our score, if necessary.
			bestScore = Math.max(bestScore, score(freq));
		}

		// Here is our result.
		return bestScore;
	}

	// Returns the frequency of letters used for the subset indicated by mask.
	public static int[] getFreq(int mask) {

		int[] freq = new int[26];

		// Go through only the words chosen for this subset.
		for (int i=0; i<numWords; i++) {
			if ((mask & (1<<i)) > 0) {

				// Tally all letters in this word.
				for (int j=0; j<words[i].length(); j++)
					freq[words[i].charAt(j)-'a']++;
			}
		}

		return freq;
	}

	// Returns true iff the letters contained in freq are not in the tile set.
	public static boolean invalid(int[] freq) {

		// Go through each letter - this distribution is invalid iff a letter appears more in freq
		// than our tile set.
		for (int i=0; i<26; i++)
			if (freq[i] > scores[i].size())
				return true;

		// If we made it here, that this is a valid distribution.
		return false;
	}

	// Returns the best score of the tiles indicated by freq.
	public static int score(int[] freq) {

		int total = 0;

		// Add up score for each letter - note in this implementation, score[i][0] stores the sum of
		// using letter i once, not 0 times, which is why the if is necessary.
		for (int i=0; i<26; i++)
			if (freq[i] > 0)
				total += ((ArrayList<Integer>)scores[i]).get(freq[i]-1);

		// Return the total score, subtracting out unused tiles.
		return total - (tileSum-total);
	}
}

class tile implements Comparable<tile> {

	public char letter;
	public int score;

	public tile(char c, int num) {
		letter = c;
		score = num;
	}

	public int compareTo(tile other) {
		return other.score - this.score;
	}
}
