import java.util.*;

public class letters {
  public static final boolean DEBUG = false;
  public static int answer;

  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = Integer.parseInt(stdin.nextLine());
    if (DEBUG) System.out.println(nCases);
    // For each case...
    for (int c = 0; c < nCases; c++) {
      // Create a new array of all the lists of tiles by type (letter face)
      // Always 26, b/c it's only lowercase letters
      PriorityQueue<Tile>[] tiles = new PriorityQueue[26];
      for (int t = 0; t < 26; t++)
        tiles[t] = new PriorityQueue<Tile>();
      // Get the number of tiles in the person's hand
      int nTilesTypes = Integer.parseInt(stdin.nextLine());
      // For each tile, add it to the list of tiles available
      for (int t = 0; t < nTilesTypes; t++) {
        String[] tileLine = stdin.nextLine().split(" ");
        int tileIndexByCharacter = tileLine[0].charAt(0) - 'a';
        Tile newTile = new Tile(tileLine[0].charAt(0), Integer.parseInt(tileLine[1]));
        tiles[tileIndexByCharacter].offer(newTile);
      }
      if (DEBUG) System.out.println("--------Tiles--------");
      for (PriorityQueue l : tiles)
        if (DEBUG) System.out.println(l);

      // Gather up all the words
      int nWords = Integer.parseInt(stdin.nextLine());
      String[] words = new String[nWords];
      for (int w = 0; w < nWords; w++)
        words[w] = stdin.nextLine();

      if (DEBUG) System.out.println(Arrays.toString(words));

      // For each combination of words..
      answer = 0;
      solve(tiles, words, new boolean[words.length], 0);
      System.out.println(answer);
    }
  }

  @SuppressWarnings("unchecked")
  public static void solve(PriorityQueue[] q, String[] words, boolean[] available, int k) {
    if (k >= available.length) {
      if (DEBUG) System.out.println("---------Dictionary---------");
      if (DEBUG) System.out.println(Arrays.toString(available));
      // System.out.println(process(clone(q), words, available));
      answer = Math.max(answer, process(clone(q), words, available));
      return;
    }
    for (int i = 0; i < 2; i++) {
      available[k] = (i % 2 == 0);
      solve(q, words, available, k + 1);
    }
  }

  @SuppressWarnings("unchecked")
  // Solves the actual board
  public static int process(PriorityQueue[] q, String[] words, boolean[] available) {
    int total = 0;
    // For each known word, try to create it
    for (int w = 0; w < words.length; w++) {
      // Check if the word is available for this combination of the dictionary
      if (!available[w]) continue;
      if (DEBUG) System.out.println("Case: " + words[w]);

      // Look up each letter in the array of PriorityQueues
      // Make sure to save each one to a temp ArrayList<Tile>
      // We're use said ArrayList to look back through if the word
      // cannot be formed. In other words, pop all the letters until
      // it is either formed or invalid.
      // If invalid, offer each of the letters back to the array of
      // PriorityQueues
      // Otherwise, just move on and add that score to the running total
      // For each letter in the word...

      Stack<Tile> processedTiles = new Stack<Tile>();
      int wordTotal = 0;
      for (int c = 0; c < words[w].length(); c++) {
        if (DEBUG) System.out.println("Look up: " + words[w].charAt(c));
        // Look up letter in the PriorityQueues, if found, add to
        // the temp ArrayList<Tile>
        // Get the first letter
        // Get letter less the 'a' value to get the index
        Tile letter = (Tile)q[words[w].charAt(c) - 'a'].poll();

        // Save the tile for later in case the word fails
        processedTiles.push(letter);

        // Case: Word impossible to create
        // If we cannot get another letter before finishing the word, it is invalid
        // So, we have to loop back through the ArrayList<Tile> to put them back and
        // break out of this loop with a total being 0
        if (letter == null) {
          // Remove the null reference at the end b/c it was added to check if null
          processedTiles.pop();

          // Put the wrongly used tiles back into their respective queues/wells
          // to make them available again
          while (!processedTiles.isEmpty()) {
            Tile tmpTile = processedTiles.pop();
            q[tmpTile.id - 'a'].offer(tmpTile);
          }
          if (DEBUG) System.out.println(processedTiles);
          wordTotal = 0;
          break;
        }

        // It's safe to process the letter
        // Increase the value of the word
        wordTotal += letter.value;
      } // End letter loop
      if (DEBUG) System.out.println("Word value: " + wordTotal);
      total += wordTotal;
    } // End words loop
    if (DEBUG) System.out.println("Words value: " + total);
    // If we get to this point, we processed the last word


    // Now remove the value of all the remaining letters in the PriorityQueues
    int unusedPoints = 0;
    for (PriorityQueue<Tile> p : q) {
      while (!p.isEmpty()) {
        unusedPoints += p.poll().value;
      }
    }
    if (DEBUG) System.out.println("Unused points " + unusedPoints);

    if (DEBUG) System.out.println("Solution: " + (total - unusedPoints));
    return (total - unusedPoints);
  }

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
