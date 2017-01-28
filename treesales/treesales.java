import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class treesales {
  // Quick references to all Person objects
  public static HashMap<String, Person> employees;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    int nCases = Integer.parseInt(stdin.nextLine());
    for (int i = 0; i < nCases; i++) {
      System.out.println("COMPANY " + (i + 1));

      // Create a new hash map for quick access to all persons
      employees = new HashMap<String, Person>();

      // Get the number of commands to execute
      int nCommands = Integer.parseInt(stdin.nextLine());
      for (int j = 0; j < nCommands; j++) {
        String tmpCmd = stdin.nextLine();

        // Parse the command
        String[] cmd = tmpCmd.split(" ");

        // Placeholder variable for creating new employees or holding a reference
        Person tmp;
        // Determine the type of command
        switch (cmd[0]) {
          case "ADD":
            // Find the boss
            Person boss = employees.get(cmd[1]);
            // If null, that means this is the CEO
            if (boss == null) {
              tmp = new Person(null, cmd[2]);
              employees.put(tmp.name, tmp);
            // Otherwise, this is a normal employee, so just add them
            // under the correct boss and get there address to hash back
            // into the hash map
            } else {
              // Hire them as a subordinate to the current person
              tmp = boss.add(cmd[2]);
              // Store a reference to them for quick access later
              employees.put(tmp.name, tmp);
            }
            break;
          case "SALE":
            // Get the person that made the sale
            tmp = employees.get(cmd[1]);
            // Recursively add this amount to their sale amount and their higher-ups
            tmp.sale(Integer.parseInt(cmd[2]));
            break;
          case "QUERY":
            // Get a reference and then access the person's sale amount
            System.out.println(employees.get(cmd[1]).sales);
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
