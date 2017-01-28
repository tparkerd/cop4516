import java.util.Scanner;

public class monkey {
  public final static boolean DEBUG = false;
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    int nCases = Integer.parseInt(stdin.nextLine());
    // For each case...
    for (int i = 0; i < nCases; i++) {
      // Get the vines as a string
      String caseStr = stdin.nextLine();
      // Show test case number
      System.out.printf("%d ", i + 1);
      // if (DEBUG) System.out.print(caseStr);
      // System.out.println(vineJunctions);

      // Now how to parse the string to insert the nodes correctly...
      // Let's try finding the tallest branch from just the string itself
      String tmp = caseStr;
      // if (DEBUG) System.out.println("Things: " + tmp.indexOf("["));
      int maxDepth = 0;
      int depth = 0;

      do {
        if (tmp.equals(new String()) && depth == 0) {
          maxDepth = 0;
          break;
        }

        if (DEBUG) System.out.println("Depth: " + depth);
        if (DEBUG) System.out.println(tmp);
        if (tmp.charAt(0) == '[')
        depth++;
        else if (tmp.charAt(0) == ']') {
          maxDepth = Math.max(maxDepth, depth);
          depth--;
        }

        // Peel off the leading character, the one that was just processed
        tmp = tmp.substring(1);
        if (DEBUG) System.out.println(tmp);
      } while (tmp.length() > 0);
      if (DEBUG) System.out.println(maxDepth);

      System.out.println(Math.round(Math.pow(2, maxDepth)));

    }
  }
}
