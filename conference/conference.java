import java.util.Scanner;
import java.util.Collections;

public class conference {
  public static void main(String[] args) {

    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();
    for (int i = 0; i < nCases; i++) {
      // Get number of conferences
      int nConf = stdin.nextInt();

    }


  }


}

abstract class Conference implements Comparable<Conference> {
  int end;
  int duration;

  public Conference(int start, int end) {
    this.end = end;
    this.duration = end - start;
  }

  public void compareTo(Conference c, Conference o) {
    if (c.end > o.end)
      ;
  }
}
