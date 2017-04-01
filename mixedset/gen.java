import java.util.*;

public class gen {
  public static void main(String[] args) {
    Random r = new Random();
    int numCases = r.nextInt(10) + 4;
    System.out.println(numCases);
    System.out.printf("3 2 3\n8 4 1\n14 4 15\n");

    for (int i = 0; i < numCases - 3; i++) {
      int n, s, k;
      n = r.nextInt(50) + 1;
      s = r.nextInt(9) + 1;
      k = r.nextInt(100000) + 1;
      System.out.printf("%d %d %d\n", n, s, 1);

    }
  }
}
