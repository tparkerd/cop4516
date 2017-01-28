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
              root = new Person(cmd[2]);
              if (DEBUG) System.out.println("New ROOT: " + cmd[2]);
            }
            else {
              if (DEBUG) System.out.println("New USER: " + cmd[2]);
              root.add(cmd[1], cmd[2]);
            }
            break;
          case SALE:
            if (DEBUG) System.out.println("New SALE: " + cmd[1] + " sold " + cmd[2]);
            // this.sale();
            break;
          case QUERY:
            if (DEBUG) System.out.println("New QUERY: " + cmd[1]);
            // Get a reference to the person
            // TODO(timp): find the person in the hashmap of all employees
            // Person tmp = root.search()
            // System.out.println()
            break;
          default:
            if (DEBUG) System.out.println("ERROR. INVALID COMMAND");
            break;
        }
      }
    }
  }
}

class Person {
  String name;
  int sales;
  Person boss;
  ArrayList<Person> subordinates;

  public Person(String name) {
    this.name = name;
    this.sales = 0;
    this.boss = null; // Maybe use a hash map to look up a reference to this one
    this.subordinates = new ArrayList<Person>();
  }

  public void add(String boss, String name) {
    // NOTE(timp): Assume that the tree will always have a CEO

    // See if the current employee is the boss of the new employee
    // If it is, add them as a subordinate
    if (this.name.equals(boss)) {
      this.subordinates.add(new Person(name));
    // Otherwise, try their subordinates to see if one of them are the boss
    } else {
      for (Person sub : this.subordinates) {
        sub.add(boss, name);
      }
    }
    if (true) System.out.println("Added " + name + " under " + boss);
  }

  public void sale(Person p, int amount) {
    p.sales += amount;
  }

  public int query(Person p) {
    int result = 0;
    for (Person sub : p.subordinates) {
      result += query(sub);
    }
    return result + p.sales;
  }

  public void hashAdd(String boss, String name) {
    // Check to see if boss is null, if so, that means it's the CEO
    if (boss.equals(new String())) {

    }
  }


}
