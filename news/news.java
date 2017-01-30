import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Arrays;

public class news {

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


class Person implements Comparable<Person> {

  int id;
  Person boss;
  int callTime;
  ArrayList<Person> subordinates;

  public Person(int id) {
    this.id = id;
    this.boss = null;
    this.callTime = 0;
    this.subordinates = new ArrayList<Person>();
  }

  public void hires(Person p) {
    this.subordinates.add(p);
    p.boss = this;
  }

  public int minCallTime() {
    if (this.subordinates.size() <= 0) return 0;
    ArrayList<Person> sub = this.subordinates;
    int n = sub.size();

    // We have some subordinates, let's find the max time it takes for each
    // of them to inform their own subordinates
    Integer[] list = new Integer[n];
    for (int i = 0; i < n; i++) {
      list[i] = sub.get(i).minCallTime();
    }

    // Sort them from greater time to wait to least
    Arrays.sort(list, Collections.reverseOrder());

    // Increment those times by the time it would take for the current
    // person to inform the subordinates that take more time first
    for (int i = 0; i < n; i++) {
      list[i] += i + 1;
    }

    // Since there could have been several subordinates with the same
    // call time waiting longer, they may be greater now
    // make sure they are the actual max
    int max = 0;
    for (int i = 0; i < list.length; i++) {
      if (list[i] > max)
        max = list[i];
    }

    return max;
  }

  @Override
  public int compareTo(Person p) {
    if (this.callTime > p.callTime)
      return 1;
    else if (this.callTime < p.callTime)
      return -1;
    else
      return 0;
  }

  @Override
  public String toString() {
    return new String("  " + this.boss.id + "\t  " + this.id);
  }
}
