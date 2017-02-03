import java.util.Scanner;
import java.util.Arrays;

public class sameletters {
  public static final String EOF = "END";
  public static final boolean DEBUG = false;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    // Get the string to compare
    String topString = stdin.nextLine();;
    String botString = stdin.nextLine();
    int caseNumber = 1;
    while (stdin.hasNext() && (!topString.equals(EOF) || !botString.equals(EOF))) {

      // Split them into charArrays and sort them
      char[] topSort = topString.toCharArray();
      char[] botSort = botString.toCharArray();

      if (DEBUG) System.out.println(Arrays.toString(topSort));
      if (DEBUG) System.out.println(Arrays.toString(botSort));

      // Sort them
      Arrays.sort(topSort);
      Arrays.sort(botSort);
      if (DEBUG) System.out.println(Arrays.toString(topSort));
      if (DEBUG) System.out.println(Arrays.toString(botSort));


      boolean ans = compare(topSort, botSort);

      System.out.printf("Case %d: ", caseNumber++);

      if (ans)
        System.out.println("same");
      else
        System.out.println("different");

      topString = stdin.nextLine();
      botString = stdin.nextLine();

    }
  }

  public static boolean compare(char[] a, char[] b) {
    // If a.length is longer than b.length, they are automatically
    // not the same
    if (a.length != b.length)
      return false;

    boolean result = true;
    for (int i = 0; i < a.length; i++) {
      if (a[i] != b[i])
        result = false;
    }

    return result;
  }

}
