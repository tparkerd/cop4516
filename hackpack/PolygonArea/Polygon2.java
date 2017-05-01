import java.util.*;

public class Polygon2 {
  private ArrayList<Point2> pts;

  public Polygon2(ArrayList<Point2> vertices) {
    this.pts = new ArrayList<Point2>(vertices);
  }

  public double getArea() {
    // Base case: fewer than 3 points
    if (this.pts.size() < 3) return 0; // Is a line or point?

    double sum = 0;
    // Add up all the areas of each triangle that makes up the polygon
    for (int i = 0; i < this.pts.size() - 1; i++) {
      double a = pts.get(i).x * pts.get(i + 1).y;
      double b = pts.get(i).y * pts.get(i + 1).x;
      sum += a - b;
    }
    return sum / 2;
  }
}
