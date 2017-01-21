import java.util.*;

public class balloons {
  public static final boolean DEBUG = true;
  public static ArrayList<Team> teams;

  public static void main(String[] args) {

    // Input
    Scanner stdin = new Scanner(System.in);

    // Test case details
    int n, a, b;
    do {
      n = stdin.nextInt();  // Number of teams
      a = stdin.nextInt();  // Number of balloons in room A
      b = stdin.nextInt();  // Number of balloons in room B

      // Create a blank slate for the team for this test case
      teams = new ArrayList<Team>();

      for (int i = 0; i < n; i++) {
        int nNeededBalloons = stdin.nextInt();  // Current item count
        int distanceToA = stdin.nextInt();  // Team's distance from room A
        int distanceToB = stdin.nextInt();  // Team's distance from room B

        // Create the team and add it to the list of known teams
        Team tmpTeam = new Team(i, nNeededBalloons, distanceToA, distanceToB);
        teams.add(tmpTeam);

      }

      System.out.print(teams.toString());
      Collections.sort(teams);
      System.out.print(teams.toString());

      for (Team team : teams) {
        System.out.println(team.toString());
      }

    } while (n != 0);


  }
}


class Team implements Comparable<Team> {
  int id;
  int k;
  int da;
  int db;
  int totalDistance;

  public Team(int id, int k, int a, int b) {
    this.id = id;
    this.k = k;
    this.da = a;
    this.db = b;
    this.totalDistance = a + b;
  }

  public int compareTo(Team t) {
    if (t.totalDistance > this.totalDistance)
      return 1;
    else if ( t.totalDistance < this.totalDistance)
      return -1;
    else
      return 0;
  }

  public String toString() {
    return new String("\n\tTeam #" + (this.id + 1) + "\n\tBalloons needed: " + this.k + "\n\tDistance to A: " + this.da + "\n\tDistance to B: " + this.db + "\n\tTotal Distance: " + this.totalDistance + "\n");
  }

}
