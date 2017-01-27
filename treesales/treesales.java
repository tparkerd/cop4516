import java.util.Scanner;

public class treesales {
  public static final boolean DEBUG = true;
  public static final String ADD   = "ADD";
  public static final String SALE  = "SALE";
  public static final String QUERY = "QUERY";
  public static final String ROOT  = "ROOT";

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    int nCases = Integer.parseInt(stdin.nextLine());
    for (int i = 0; i < nCases; i++) {
      // Get the number of commands to execute
      int nCommands = Integer.parseInt(stdin.nextLine());
      for (int j = 0; j < nCommands; j++) {
        String tmpCmd = stdin.nextLine();
        // if (DEBUG) System.out.println(tmpCmd);

        // Parse the command
        String[] cmd = tmpCmd.split(" ");

        // Determine the type of command
        switch (cmd[0]) {
          case ADD:
          // Check to see if the we are adding the root
            if (cmd[1].equals(ROOT)) {
              if (DEBUG) System.out.println("New ROOT: " + cmd[2]);
            }
            else {
              if (DEBUG) System.out.println("New USER: " + cmd[2]);
            }
            break;
          case SALE:
            if (DEBUG) System.out.println("New SALE: " + cmd[1] + " sold " + cmd[2]);
            break;
          case QUERY:
            if (DEBUG) System.out.println("New QUERY: " + cmd[1]);
            break;
          default:
            if (DEBUG) System.out.println("ERROR. INVALID COMMAND");
            break;
        }

      }
    }
  }
}
class Node {
  String name;
  int sales;
}
