import java.util.*;
import java.awt.geom.*;

public class birdman {
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();
    for (int i = 0; i < nCases; i++) {
      Point2D birdman = new Point2D.Double(stdin.nextInt(), stdin.nextInt());
      Point2D sun     = new Point2D.Double(stdin.nextInt(), stdin.nextInt());
      Point2D meStart = new Point2D.Double(stdin.nextInt(), stdin.nextInt());
      Point2D meEnd   = new Point2D.Double(stdin.nextInt(), stdin.nextInt());
      Line2D A = new Line2D.Double(birdman, sun);
      Line2D B = new Line2D.Double(meStart, meEnd);

      if (A.intersectsLine(B))
        System.out.println("Move to the left or right!");
      else
        System.out.println("Good picture, Birdman!");
    }
  }
}
