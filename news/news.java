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
      if (DEBUG) System.out.println("CASE #" + (i + 1));
      if (DEBUG) System.out.printf(" BOSS\tHIREE\n");
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
        int bossId = stdin.nextInt();
        Person boss = myHash.get(bossId);
        boss.hires(tmp);
      }
      if (news.DEBUG) System.out.println();
      if (news.DEBUG) System.out.println("\tID\tMAX\tSUM");
      System.out.println(root.minCallTime());
    }
  }
}


class Person implements Comparable<Person> {


    public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";

  int id;
  Person boss;
  int callTime;
  ArrayList<Person> subordinates;

  public Person(int id) {
    this.id = id;
    this.callTime = 0;
    this.boss = null;
    this.subordinates = new ArrayList<Person>();
  }

  public void hires(Person p) {
    this.subordinates.add(p);
    p.boss = this;
    this.callTime++;
    if (news.DEBUG) System.out.println(p.toString());
  }

  public void callUpdate() {
    if (this != null) {
      this.callTime++;
      this.boss.callUpdate();
    }
  }

  public int minCallTime() {
    if (news.DEBUG) System.out.printf(ANSI_RED + "Call:   %d\n" + ANSI_RESET, this.id);
    // If I have no subordinates, it just takes a minute to inform me
    if (this.subordinates == null) return 0;

    // Get the call time for each of my subordinates
    int[] list = new int[this.subordinates.size()];

    // For each subordinate...
    for (int i = 0; i < this.subordinates.size(); i++) {
      // Get the call time without maxes
      list[i] = this.subordinates.get(i).minCallTime();
    }

    if (news.DEBUG) System.out.println("Raw " + Arrays.toString(list));

    // Sort the subordinates
    Arrays.sort(list);
    if (news.DEBUG) System.out.println("Sorted " + Arrays.toString(list));

    // Increment by how long each subordinate has to wait to be called by its boss
    for (int i = 0; i < list.length; i++) {
      list[i] += (i + 1);
    }

    Arrays.sort(list);

    return list[0];
  }

  @Override
  public int compareTo(Person p) {
    int thisMin = this.minCallTime();
    int thatMin = p.minCallTime();
    if (thisMin > thatMin)
      return 1;
    else if (thisMin < thatMin)
      return -1;
    else return 0;
  }

  @Override
  public String toString() {
    return new String("  " + this.boss.id + "\t  " + this.id);
  }
}
