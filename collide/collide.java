import java.util.*;

public class collide {
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    int nPlanets = stdin.nextInt();
    int cCase = 1;
    while (nPlanets != 0) {
      // For each planet, find its LCM with the previous calculated LCM
      long[] planets = new long[nPlanets];
      for (int i = 0; i < nPlanets; i++) {
        planets[i] = stdin.nextLong();
      }
      long lcm = lcm(planets);
      System.out.printf("%d: ", cCase);
      if (lcm == -1)
        System.out.println("NOT TO WORRY");
      else
        System.out.printf("THE WORLD ENDS IN %d DAYS\n", lcm);

      // Get next case
      nPlanets = stdin.nextInt();
      cCase++;
    }
  }


  public static long lcm(long a, long b) {
    return a * (b / gcd(a, b));
  }

  public static long lcm(long[] a) {
    long result = a[0];
    for (int i = 1; i < a.length; i++) {
      result = lcm(result, a[i]);
      if (result > Integer.MAX_VALUE)
        return -1;
    }

    return result;
  }

  public static long gcd(long a, long b) {
    while (b > 0) {
      long tmp = b;
      b = a % b;
      a = tmp;
    }
    return a;
  }

  public static long gcd(long[] a) {
    long result = a[0];
    for (int i = 1; i < a.length; i++) {
      result = gcd(result, a[i]);
    }
    return result;
  }

}
