import java.util.*;

public class conference {
  public static final boolean DEBUG = true;
  public static ArrayList<Conference> schedule;

  public static void main(String[] args) {

    Long tmp1 = Long.MAX_VALUE();

    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();
    for (int i = 0; i < nCases; i++) {
      schedule = new ArrayList<Conference>();


      // Get number of conferences
      int nConf = stdin.nextInt();
      for (int j = 0; j < nConf; j++) {
        int start = stdin.nextInt();
        int duration = stdin.nextInt();
        Conference tmp = new Conference(start, duration);
        schedule.add(tmp);
      }
      // Sort by starting time
      Collections.sort(schedule);
      if (DEBUG) System.out.println(schedule.toString());


      // Process case
      // Assume the earliest starting conference goes first
      long total = 0;
      int lastEndingTime = 0;

      // Go through conferences
      for (Conference c : schedule) {
        if (c.start >= lastEndingTime) {
          total += c.worth;
        }
      }

      if (DEBUG) System.out.println("Earned: " + total);

    }
  }
}

class Conference implements Comparable<Conference> {
  int start;
  int end;
  int duration;
  long worth;

  public Conference(int start, int duration) {
    this.start = start;
    this.duration = duration;
    this.end = start + duration;
    this.worth = 0;
    for (int i = start; i <= duration; i++) {
      this.worth += (2e29) / (2 * i);
      // this.worth += this.duration;
    }
  }

  @Override
  public int compareTo(Conference o) {
    if (o.start > this.start)
      return -1;
    else if (o.start < this.start)
      return 1;
    else
      return 0;
  }

  public String toString() {
    return new String("\nStart: " + this.start + "\nEnd: " + this.end +
                      "\nDuration: " + this.duration + "\nWorth: " +
                      this.worth + "\n");
  }
}
