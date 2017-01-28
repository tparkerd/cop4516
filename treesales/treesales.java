import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class treesales {
  public static final boolean DEBUG = false;
  public static final String ADD   = "ADD";
  public static final String SALE  = "SALE";
  public static final String QUERY = "QUERY";
  public static final String ROOT  = "ROOT";
  public static Person root;
  public static HashMap<String, Person> myHash;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    int nCases = Integer.parseInt(stdin.nextLine());
    for (int i = 0; i < nCases; i++) {
      System.out.println("COMPANY " + (i + 1));
      // Create an empty tree to act upon
      root = null;

      // Create a new hash map for quick access to all persons
      myHash = new HashMap<String, Person>();

      // Get the number of commands to execute
      int nCommands = Integer.parseInt(stdin.nextLine());
      for (int j = 0; j < nCommands; j++) {
        String tmpCmd = stdin.nextLine();
        // if (DEBUG) System.out.println(tmpCmd);

        // Parse the command
        String[] cmd = tmpCmd.split(" ");

        // Determine the type of command
        Person tmp;
        switch (cmd[0]) {
          case ADD:
            // Find the boss
            Person boss = myHash.get(cmd[1]);
            // If null, that means this is the CEO
            if (boss == null) {
              if (DEBUG) System.out.println("Create CEO");
              root = new Person(null, cmd[2]);
              myHash.put(root.name, root);
            // Otherwise, this is a normal employee, so just add them
            // under the correct boss and get there address to hash back
            // into the hash map
            } else {
              if (DEBUG) System.out.println("Add user: " + cmd[2]);
              tmp = boss.add(cmd[2]);
              myHash.put(tmp.name, tmp);
              if (DEBUG) System.out.println("Hire " + cmd[2] + " under " + myHash.get(cmd[2]).boss.name);
            }
            break;
          case SALE:
            if (DEBUG) System.out.println("New SALE: " + cmd[1] + " sold " + cmd[2]);
            // Get the person that made the sale
            tmp = myHash.get(cmd[1]);
            tmp.sale(Integer.parseInt(cmd[2]));
            break;
          case QUERY:
            if (DEBUG) System.out.println("New QUERY: " + cmd[1]);
            // Get a reference to the person
            tmp = myHash.get(cmd[1]);
            System.out.println(tmp.sales);
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

  public Person(Person boss, String name) {
    this.name = name;
    this.sales = 0;
    this.boss = boss;
    this.subordinates = new ArrayList<Person>();
  }

  public Person add(String name) {
    // Unnecessary, but see if they already exist in the hash map
    // TODO(timp): see if they are already in the hash map

    // Create a new person
    Person tmp = new Person(this, name);
    // Hire them under the current person
    this.subordinates.add(tmp);

    // Return a reference to the newly created person
    return tmp;
  }

  public void sale(int amount) {
    this.sales += amount;

    // Traverse up the tree
    if (this.boss != null) {
      this.boss.sale(amount);
    }
  }
}
