import java.util.*;

public class Polygon2 {
  private ArrayList<Point2> vertices;

  public Polygon2() { this.vertices = new ArrayList<Point2>(); }
  public Polygon2(ArrayList<Point2> pts) {
    this.vertices = new ArrayList<Point2>(pts);
    this.vertices.add(pts.get(0)); // Close polygon
  }

  public void add(Point2 p) {
    if (this.vertices.size() < 2) {
      this.vertices.add(p);
      if (this.vertices.size() == 2) this.vertices.add(vertices.get(0));
    } else {
      this.vertices.remove(vertices.size() - 1); // open up polygon
      this.vertices.add(p);               // Add new point
      this.vertices.add(vertices.get(0)); // close back up with starting point
    }
  }

  public double getPerimeter() {
    if (this.vertices.size() < 3) return 0.0; // Base case: point or line
    double sum = 0.0;
    for (int i = 0; i < vertices.size() - 1; i++)
      sum += vertices.get(i).distanceTo(vertices.get(i + 1));
    return sum;
  }
}
