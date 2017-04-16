// Solution Candidate to Lettersor COP 4516, Spring 2017
// By Tim Parker

import java.util.*;

public class letters {
  public static final boolean DEBUG = false;
  public static int answer;

  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = Integer.parseInt(stdin.nextLine());
    // For each case...
    for (int c = 0; c < nCases; c++) {
      // Create a new array of all the lists of tiles by type (letter face)
      // Always 26, b/c it's only lowercase letters
      PriorityQueue<Tile>[] tiles = new PriorityQueue[26];
      for (int t = 0; t < 26; t++)
        tiles[t] = new PriorityQueue<Tile>();
      // Get the number of tiles in the person's hand
      int nTilesTypes = Integer.parseInt(stdin.nextLine());
      answer = 0;
      // For each tile, add it to the list of tiles available
      for (int t = 0; t < nTilesTypes; t++) {
        String[] tileLine = stdin.nextLine().split(" ");
        int tileIndexByCharacter = tileLine[0].charAt(0) - 'a';
        Tile newTile = new Tile(tileLine[0].charAt(0), Integer.parseInt(tileLine[1]));
        tiles[tileIndexByCharacter].offer(newTile);
        answer += newTile.value;
      }

      if (DEBUG) System.out.println("--------Tiles--------");
      for (PriorityQueue l : tiles) if (DEBUG) System.out.println(l);

      // Gather up all the words
      int nWords = Integer.parseInt(stdin.nextLine());
      String[] words = new String[nWords];
      for (int w = 0; w < nWords; w++)
        words[w] = stdin.nextLine();

      // Solve it!
      answer = -answer;
      solve(tiles, words, new boolean[words.length], 0);
      System.out.println(answer);
    }
  }

  // Generic function that solves the problem, but it has to loop through
  // all the possible combinations of the dictionary. It has to try each
  // combination because if we process first word to last word, there's a
  // chance that we would never get to the last word, which would be
  // worth substantially overall.
  @SuppressWarnings("unchecked")
  public static void solve(PriorityQueue[] q, String[] words, boolean[] available, int k) {
    if (k >= available.length) {
      // DEBUG: Show the current test case with the available dictionary combo
      if (DEBUG) {
        if (DEBUG) System.out.println("---------Dictionary---------");
        for (int b = 0; b < words.length; b++) {
          if (available[b]) System.out.printf("%s ", words[b]);
        }
        System.out.println("\n----------------------------");
      }
      int caseResult = process(clone(q), words, available);
      if (DEBUG) System.out.printf("answer = Math.max(%d, %d);\n", answer, caseResult);
      answer = Math.max(answer, caseResult);
      return;
    }
    // Flip the word either available or unavailable
    for (int i = 0; i < 2; i++) {
      available[k] = (i % 2 == 0);
      solve(q, words, available, k + 1);
    }
  }

  // The 'meat' of the program that actually decides which tiles to use to
  // build all the available words in the subset of words in the dictionary
  @SuppressWarnings("unchecked")
  public static int process(PriorityQueue[] q, String[] words, boolean[] available) {
    int total = 0;
    // For each known word, try to create it
    for (int w = 0; w < words.length; w++) {
      // Check if the word is available for this combination of the dictionary
      if (!available[w]) continue;
      if (DEBUG) System.out.println("Build: " + words[w]);

      // Look up each letter in the array of PriorityQueues
      // Keep a history of used letters in case of impossible word
      Stack<Tile> processedTiles = new Stack<Tile>();
      int wordTotal = 0;
      for (int c = 0; c < words[w].length(); c++) {
        if (DEBUG) System.out.println("Look up: " + words[w].charAt(c));

        // Get the first letter
        // Get letter less the 'a' value to get the index
        Tile letter = (Tile)q[words[w].charAt(c) - 'a'].poll();

        // Save the tile for later in case the word fails
        processedTiles.push(letter);

        // Case: Word impossible to create
        // Can't get all the letters in the word, therefore it's invalid
        if (letter == null) {
          // Remove the null reference at the end b/c it was added to check if null
          processedTiles.pop();

          // Put the wrongly used tiles back into their respective queues
          // to make them available again
          while (!processedTiles.isEmpty()) {
            Tile tmpTile = processedTiles.pop();
            q[tmpTile.id - 'a'].offer(tmpTile);
          }

          if (DEBUG) System.out.println(processedTiles);
          // Impossible word is no worthless
          wordTotal = 0;
          // Bailout to try next word
          break;
        } // End clean up for invalid word

        // Increase the value of the word
        wordTotal += letter.value;

      } // End letter loop
      if (DEBUG) System.out.println("Word value: " + wordTotal);
      // Update the running total for this subset of words in the dictionary
      total += wordTotal;

    } // End words loop, so we've processed all words

    if (DEBUG) System.out.println("Words value: " + total);

    // Now remove the value of all the remaining letters in the PriorityQueues
    int unusedPoints = 0;
    for (PriorityQueue<Tile> p : q) {
      while (!p.isEmpty()) {
        unusedPoints += p.poll().value;
      }
    }

    if (DEBUG) System.out.println("Unused points " + unusedPoints);
    if (DEBUG) System.out.println("Solution: " + (total - unusedPoints));

    // Solution is the difference between the sum of used tiles and unused
    return (total - unusedPoints);
  }

  // Because I want to reuse the tiles for a subset of the dictionary,
  // I need to clone the available tiles, as not to destroy the original
  // between each case
  @SuppressWarnings("unchecked")
  public static PriorityQueue<Tile>[] clone(PriorityQueue<Tile>[] o) {
    PriorityQueue[] tmp = new PriorityQueue[26];
    for (int i = 0; i < 26; i++) {
      tmp[i] = new PriorityQueue(o[i]);
    }
    return tmp;
  }
}

class Tile implements Comparable<Tile> {
  public char id;
  public int value;

  public Tile(char id, int value) {
    this.id = id;
    this.value = value;
  }

  public Tile(Tile t) {
    this.id = t.id;
    this.value = t.value;
  }

  public int compareTo(Tile o) {
    return  o.value - this.value;
  }

  @Override
  public String toString() {
    return this.id + "(" + this.value + ")";
  }
}
