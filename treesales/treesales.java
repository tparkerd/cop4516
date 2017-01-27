import java.util.Scanner;
import java.util.ArrayList;

public class treesales {
  public static final boolean DEBUG = true;
  public static final String ADD   = "ADD";
  public static final String SALE  = "SALE";
  public static final String QUERY = "QUERY";
  public static final String ROOT  = "ROOT";
  public static Person root;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    int nCases = Integer.parseInt(stdin.nextLine());
    for (int i = 0; i < nCases; i++) {
      System.out.println("COMPANY " + (i + 1));
      // Create an empty tree to act upon
      root = null;

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

class Person implements Comparable<Person> {
  String name;
  int sales;
  ArrayList<Person> subordinates;

  public Person(String name, Person boss) {
    this.name = name;
    this.sales = 0;
    subordinates = new ArrayList<Person>();
  }

  @Override
  public int compareTo(Person o) {
    return 0;
  }

  public void insert (String name, String boss) {
    // Find boss
  }

  public void makesSale(Person p, int amount) {
    p.sales += amount;
  }

  public int totalSales(Person p) {
    int result = 0;
    for (Person sub : p.subordinates) {
      result += totalSales(sub);
    }
    return result + p.sales;
  }

  public Person search(Person root, String name) {
    // Is this a null node
    if (root == null) return null;

    // Is the current root the person we're looking for?
    if (root.name.equals(name))
      return root;

    // Try finding them in the person's subordinates
    for (Person sub : root.subordinates) {
      Person tmp = search(sub, name);
      if (tmp != null)
        return tmp;
    }

    // Otherwise, it was never found
    return null;
  }

}
