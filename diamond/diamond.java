import java.util.*;
import java.math.*;

public class diamond {
  public static final boolean DEBUG = false;

  public static DiamondCase case1;
  public static DiamondCase case2;

  public static void main(String[] args) {
    // Read in the data
    Scanner stdin = new Scanner(System.in);
    // Number of cases
    int nCases = stdin.nextInt();

    // For each case...
    for (int i = 0; i < nCases; i++) {
      case1 = new DiamondCase();
      case2 = new DiamondCase();
      // Number of diamonds
      int nDiamonds = stdin.nextInt();
      // Max size difference
      int k = stdin.nextInt();
      // For each diamond...
      int[] list = new int[nDiamonds];
      for (int j = 0; j < nDiamonds; j++) {
        list[j] = stdin.nextInt();
      }

      // Process the diamonds
      for (int j = 0; j < nDiamonds; j++) {
        int diamond = list[j];
        // If no diamond has been placed, place the first one in the first case
        if (j == 0) {
            case1.list.add(diamond);
            continue; // to go the next case, although this is unnecessary
        }

        // Otherwise, start testing who goes where
        // If the diamond can fit in the first case, add( it there, otherwise
        // try case 2, if not, skip that diamond
        if (case1.canFit(diamond, k)) {
          case1.list.add(diamond);
        } else if (case2.canFit(diamond, k)) {
          case2.list.add(diamond);
        }
      }

      int diamondsPlaced = case1.list.size() + case2.list.size();
      System.out.println(diamondsPlaced);

      if (DEBUG) System.out.println(Arrays.toString(list));
    }


  }
}

class DiamondCase {
  ArrayList<Integer> list;

  public DiamondCase() {
    list = new ArrayList<Integer>();
  }

  // See if the current diamond can fit in
  public boolean canFit(int n, int k) {
    boolean result = true;
    // See if the new diamond is within K values of each diamond already in the case
    for (int i = 0; i < this.list.size(); i++) {
      // If a diamond exceeds K difference between them, this cannot be added
      if (Math.abs(list.get(i) - n) > k) result = false;
    }
    return result;
  }
}
