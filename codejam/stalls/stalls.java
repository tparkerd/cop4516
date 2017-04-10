import java.util.*;

public class stalls {
  public static Stall[] a;
  public static final boolean DEBUG = false;
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();

    // For each case...
    for (int i = 1; i <= nCases; i++) {
      int nStalls = stdin.nextInt() + 2;
      int nPeople = stdin.nextInt();
      // Build the starting state of the bathroom
      init(nStalls);

      // Let them enter!
      for (int j = 0; j < nPeople; j++) {
        goPotty();
      }
    }
  }

  // Gets distance to next occupied stall to the left
  public static int getLeft(int index) {
    int l = index - 1;
    while (l > 0 && !a[l].used) l--;
    return l;
  }
  // Gets distance to next occupied stall to the right
  public static int getRight(int index) {
    int r = index + 1;
    while (r < a.length - 1 && !a[r].used) r++;
    return r;
  }
  // Builds the initial state of the board
  public static void init(int n) {
    // Starting state, only guards on the sides
    a = new Stall[n];
    for (int j = 0; j < n; j++)
      a[j] = new Stall();
    // Place the guards in their respective stalls
    a[0].used = true;
    a[n - 1].used = true;

    for (int i = 1; i <= n - 2; i++) {
      // Base case: skip used stalls
      if (a[i].used) i++;

      // Check
      int l = i - 1; // Left used index
      int r = i + 1; // Right used index
      while (l > 0 && !a[l].used) l--;
      while (r < a.length - 1 && !a[r].used) r++;
      a[i].dist = Math.min(i - l - 1, r - i - 1);
    }


  }
  // Places people in the appropriate stall
  // returns the distance to the next nearest occupied stall
  public static void goPotty() {
    ArrayList<Integer> list = getMaxIndex();
    // Case 1: One maximal point (easy)
    if (list.size() == 1) {
      // Mark as used
      a[list.get(0)].dist = -1;
      a[list.get(0)].used = true;
    }
    else {
      // Case 2: More than one maximal point (hard)
      ArrayList<Integer> maximal = new ArrayList<Integer>();
      // Go through the list and mark which index has the longest
      // consecutive sequence of numbers
      // I.e., [1, 2] & [4, 5, 6], choose index 4 because its list
      // list longer

      // Assume the first is the max
      int cIndex = 0;
      int cCount = 0;
      int lastIndex = list.get(0);
      while (list.get(cIndex + 1) == lastIndex) {
        cCount++;
        cIndex++;
        lastIndex = list.get(cIndex);
      }
      a[list.get(0)].dist = -1;
      a[list.get(0)].used = true;

      // Case 3: Go left! (easy?)
      else {
        a[list.get(0)].dist = -1;
        a[list.get(0)].used = true;
      }
    }

    // After placing the person, update the distances for all stalls

    for (int i = 1; i <= a.length - 2; i++) {
      // Base case: skip used stalls
      if (a[i].used) i++;

      // Check
      int l = i - 1; // Left used index
      int r = i + 1; // Right used index
      while (l > 0 && !a[l].used) l--;
      while (r < a.length - 1 && !a[r].used) r++;
      a[i].dist = Math.min(i - l - 1, r - i - 1);
    }
  }

  public static ArrayList<Integer> getMaxIndex() {
    ArrayList<Integer> list = new ArrayList<Integer>();
    int max = a[1].dist;

    // Find what the max distance is
    for (int i = 0; i < a.length; i++)
      if (max < a[i].dist)
        max = a[i].dist;

    // Get all instances of the max distance
    for (int i = 0; i < a.length; i++) {
      if (a[i].dist == max)
        list.add(i);
    }
    return list;
  }

  // public static void findMaxIndex(Stall[] a) {
  //   int maxIndex = 0;
  //   int maxDist = a[0].dist;
  //   for (int i = 1; i < a.length; i++) {
  //     if (maxDist < a[i].dist) {
  //       maxDist = a[i].dist;
  //       maxIndex = i;
  //     }
  //   }
  //   if (a[maxIndex].dist < 2)
  //     System.out.printf("%d ", a[maxIndex].dist);
  //   else
  //     System.out.printf("%d ", a[maxIndex].dist - 1);
  //   a[maxIndex].used = true;
  //   a[maxIndex].dist = -1;
  //   if (DEBUG) System.out.println("Max index: " + maxIndex);
  // }
}


class Stall {
  public boolean used;
  int dist;
  public Stall() {
    this.used = false;
    this.dist = -1;
  }

  public String toString() {
    if (this.used) {
      return "-";
    } else {
      return this.dist + "";
    }
  }
}
