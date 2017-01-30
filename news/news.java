import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Arrays;

public class news {

  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";
  public static final boolean DEBUG = true;
  public static HashMap<Integer, Person> myHash;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    myHash = new HashMap<Integer, Person>();

    // For each case...
    int nCases = stdin.nextInt();
    for (int i = 0; i < nCases; i++) {
      // Make the CEO, the boss, me!
      // Store a quick reference to my 'pointer'
      Person root = new Person(0);
      myHash.put(0, root);
      // Get the number of employees
      int nEmployees = stdin.nextInt();
      // For each employee...
      for (int j = 1; j < nEmployees; j++) {
        Person tmp = new Person(j);
        // Store a quick reference
        myHash.put(j, tmp);
        // Assign their boss
        Person boss = myHash.get(stdin.nextInt());
        boss.hires(tmp);
      }
      System.out.println(root.minCallTime());
    }
  }
}


class Person {

  int id;
  Person boss;
  ArrayList<Person> subordinates;

  public Person(int id) {
    this.id = id;
    this.boss = null;
    this.subordinates = new ArrayList<Person>();
  }

  public void hires(Person p) {
    this.subordinates.add(p);
    p.boss = this;
  }

  public int minCallTime() {
    return 0;
  }

  @Override
  public String toString() {
    return new String("  " + this.boss.id + "\t  " + this.id);
  }
}
