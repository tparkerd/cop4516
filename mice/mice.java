import java.util.*;
import java.awt.geom.*;

public class mice {
  public static final boolean DEBUG = false;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();
    // For each case...
    for (int i = 0; i < nCases; i++) {
      int rPond = stdin.nextInt();
      int rHole = stdin.nextInt();
      int xHole = stdin.nextInt();
      int yHole = stdin.nextInt();

      // I'm assuming this is the new X that Arup suggested in his announcement
      // I mean, it's the only thing I can calculate from the cut in the ice
      double x = Math.sqrt((xHole * xHole) + (yHole * yHole)) - rHole;

      // Ugly as hell, but it seems to work
      double newWidth = (Math.sqrt(4*(x*x-2*(x*x-rPond*rPond))) + 2 * x)/4;

      // Find if the circle cuts into the rectangle
      Rectangle2D rect = createRectangle(rPond);
      Point2D intercept = findPotentialIntercept(xHole, yHole, rHole);

      if (!rect.contains(intercept)) {
        System.out.printf("Pond #%d:\nICE CLEAR!!! %.2f\n\n", i + 1, 1.6 * rPond * rPond);
      } else {
        System.out.printf("Pond #%d:\nOBSTRUCTION! %.2f\n\n", i + 1, 2 * newWidth * newWidth);
      }
    }
  }

  // Gets point directly between cut and center of pond
  public static Point2D findPotentialIntercept(double a, double b, double rHole) {
    // In case the point is at the origin b/c dividing by zero is illegal is
    // all but three countries /s
    if (a == 0 && b == 0) return new Point2D.Double(0, 0);
    double theta = Math.atan2(b, a);
    double x = Math.cos(theta) * (Math.sqrt(a*a+b*b) - rHole);
    double y = Math.sin(theta) * (Math.sqrt(a*a+b*b) - rHole);
    return new Point2D.Double(x, y);
  }

  // Intermediate class to create a rectangle from the pond radius
  public static Rectangle2D createRectangle(double r) {
    double x, y, w, l;
    w = Math.sqrt(0.8*r*r);
    l = 2 * w;
    x = -l / 2;
    y = -w / 2;
    return new Rectangle2D.Double(x, y, l, w);
  }
}
