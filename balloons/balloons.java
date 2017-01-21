import java.util.*;

public class balloons {
  public static final boolean DEBUG = false;
  public static ArrayList<Team> teams;

  public static void main(String[] args) {

    // Input
    Scanner stdin = new Scanner(System.in);

    // Test case details
    int n, a, b;
    n = stdin.nextInt();  // Number of teams
    a = stdin.nextInt();  // Number of balloons in room A
    b = stdin.nextInt();  // Number of balloons in room B
    do {
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

      // Sort the teams by the difference of distance between the rooms
      Collections.sort(teams);
      if (DEBUG) System.out.print(teams.toString());

      // Total min distance to be travel for all teams
      int totalMinDistance = 0;

      for (Team team : teams) {
        if (DEBUG) System.out.println(team.toString());

        int numToRemoveFromA, numToRemoveFromB;

        // If the distance to A is shorter, take balloons from that room first
        if (team.da < team.db) {
          // Get the number of balloons that can be removed from A
          // So just in case room A has fewer balloons than needed by the
          // team, just get the remaining balloons in that room
          numToRemoveFromA = Math.min(team.k, a);
          // Get the remaining necessary balloons from the other room
          numToRemoveFromB = team.k - numToRemoveFromA;

        // Otherwise, it is less distance to get to room B, so remove from
        // that room first. Also make sure to not remove more balloons than
        // are remaining in the room
        } else {
          numToRemoveFromB = Math.min(team.k, b);
          // Get the remaining necessary balloons from the other room
          numToRemoveFromA = team.k - numToRemoveFromB;
        }

        // Sum up the distance travelled by getting the ballons for the current
        // team
        totalMinDistance += (numToRemoveFromA * team.da) + (numToRemoveFromB * team.db);

        // Actually remove the balloons from the supply rooms
        a -= numToRemoveFromA;
        b -= numToRemoveFromB;
      }

      System.out.println(totalMinDistance);

      n = stdin.nextInt();  // Number of teams
      a = stdin.nextInt();  // Number of balloons in room A
      b = stdin.nextInt();  // Number of balloons in room B

    } while (n != 0);


  }
}


class Team implements Comparable<Team> {
  int id;
  int k;
  int da;
  int db;
  int roomDistance;

  public Team(int id, int k, int a, int b) {
    this.id = id;
    this.k = k;
    this.da = a;
    this.db = b;
    this.roomDistance = Math.abs(a - b);
  }

  public int compareTo(Team t) {
    if (t.roomDistance > this.roomDistance)
      return 1;
    else if ( t.roomDistance < this.roomDistance)
      return -1;
    else
      return 0;
  }

  public String toString() {
    return new String("\n\tTeam #" + (this.id + 1) + "\n\tBalloons needed: " + this.k + "\n\tDistance to A: " + this.da + "\n\tDistance to B: " + this.db + "\n\tRoom distance: " + this.roomDistance + "\n");
  }

}
