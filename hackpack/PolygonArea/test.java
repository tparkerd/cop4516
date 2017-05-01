import java.util.*;

public class test {
  public static void main(String[] args) {
    int[] x = new int[]{0, 3, 3, 0};
    int[] y = new int[]{0, 0, 3, 3};

    // Square
    ArrayList<Point2> pts = new ArrayList<Point2>();
    pts.add(new Point2(0,0));
    pts.add(new Point2(3,0));
    pts.add(new Point2(3,3));
    pts.add(new Point2(1,2));
    // pts.add(new Point2(0,3));
    Polygon2 poly = new Polygon2(pts);

    // Solution
    poly.getArea();

    // Polygon poly = new Polygon(x, y, 4);
    // Point2D insidePoint = new Point2D.Float(1, 1);
    // Point2D outsidePoint = new Point2D.Float(5, 5);
    // System.out.println(poly.contains(insidePoint));
    // System.out.println(poly.contains(outsidePoint));
    // int[] x2 = new int[]{0, 3, 3};
    // int[] y2 = new int[]{0, 0, 3};
    // Polygon tri = new Polygon(x2, y2, 3);
    // System.out.println(poly.contains(new Point2D.Float(0.5f, 0.4f)));
    // System.out.println(tri.getBounds());
  }
}
