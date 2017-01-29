import java.util.Scanner;
import java.util.ArrayList;

public class news {
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    // For each case...
    int nCases = stdin.nextInt();
    for (int i = 0; i < nCases; i++) {
      // Make the CEO, the boss, me!
      Person root = new Person(0);
      // Get the number of employees
      int nEmployees = stdin.nextInt();
      // For each employee...
      for (int j = 0; j < nEmployees; j++) {

      }
    }
  }
}


class Person {
  int id;
  ArrayList<Person> subordinates;

  public Person(int id) {
    this.id = id;
    this.subordinates = new ArrayList<Person>();
  }
}
