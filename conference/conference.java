import java.util.Scanner;
import java.util.Collections;
import java.util.ArrayList;

public class conference {
  ArrayList<Conference> schedule;

  public static void main(String[] args) {

    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();
    for (int i = 0; i < nCases; i++) {
      // Get number of conferences
      int nConf = stdin.nextInt();
      for (int j = 0; j < nConf; j++) {
        int start = stdin.nextInt();
        int duration = stdin.nextInt();
        Conference tmp = new Conference(start, duration);
      }
    }


  }


}

abstract class Conference implements Comparable<Object> {
  int end;
  int duration;

  public Conference(int start, int duration) {
    this.duration = duration;
    this.end = start + duration;
  }

  public int compareTo(Conference c, Conference o) {
    if (c.end > o.end)
      return 1;
    else if (c.end < o.end)
      return -1;
    else
      return 0;
  }
}
