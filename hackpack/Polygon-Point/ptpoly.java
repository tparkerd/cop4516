import java.util.*;

public class ptpoly {
  public static void main(String[] args) {

    // int[] listOfXCoordinates
    //
    // int[] x = new int[]{0, 3, 3, 0};
    // int[] y = new int[]{0, 0, 3, 3};
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


    Polygon2 poly = new Polygon2();
    ArrayList<Point2> pts = new ArrayList<Point2>();
    pts.add(new Point2(0,0));
    pts.add(new Point2(3,0));
    pts.add(new Point2(3,3));
    pts.add(new Point2(0,3));
    pts.add(new Point2(1.5,0));
    for (Point2 p : pts)
      poly.add(p);
      System.out.println(poly.getPerimeter());

    poly = new Polygon2(pts);

    System.out.println(poly.getPerimeter());


  }
}
