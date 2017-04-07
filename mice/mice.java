import java.util.*;
import java.awt.geom.*;

public class mice {
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();
    // For each case...
    for (int i = 0; i < nCases; i++) {
      int radiusPond = stdin.nextInt();
      int radiusHole = stdin.nextInt();
      int xHole      = stdin.nextInt();
      int yHole      = stdin.nextInt();

      System.out.printf("Case #%d: R(%d), r(%d), (%d, %d)\n", i + 1, radiusPond, radiusHole, xHole, yHole);
      System.out.println(findArea(radiusPond));
      Rectangle2D rect = createRectangle(radiusPond);
      System.out.println(rect);
      Point2D intercept = findPotentialIntercept(xHole, yHole, radiusHole);
      System.out.println(intercept);

      System.out.println(rect.contains(50,20));
    }
  }

  public static double findArea(double radius) {
    // Constant is 8/5
    // Width is sqrt((4r^2)/5)
    return 1.6 * radius * radius;
  }

  public static Point2D findPotentialIntercept(double a, double b, double rHole) {
    double theta = Math.atan2(b, a);
    System.out.println("Theta: " + theta);
    double x = Math.cos(theta) * (Math.sqrt(a*a+b*b) - rHole);
    System.out.println("Cos: " + x);
    double y = Math.sin(theta) * (Math.sqrt(a*a+b*b) - rHole);
    System.out.println("Sin: " + y);
    return new Point2D.Double(x, y);
  }

  public static Rectangle2D createRectangle(double r) {
    double x, y, w, l;
    w = Math.sqrt(0.8*r*r);
    l = 2 * w;
    x = -l / 2;
    y = -w / 2;

    return new Rectangle2D.Double(x, y, l, w);
  }
}


class Rectangle2  {
  double x, y, w, l;
  public Rectangle2(double r) {
    this.w = Math.sqrt(0.8*r*r);
    this.l = 2 * this.w;
    this.x = -this.l / 2;
    this.y = -this.w / 2;
  }

  public String toString() {
    return "W("+this.w+") L("+this.l+") @("+this.x+", "+this.y+")";
  }
}
