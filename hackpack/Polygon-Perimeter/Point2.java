public class Point2 {
  double x, y;
  public Point2(double x, double y) {
    this.x = x; this.y = y;
  }
  public Point2(Point2 p) {
    this.x = p.x; this.y = p.y;
  }
  public double distanceTo(Point2 p) {
    return Math.sqrt((this.x - p.x) * (this.x - p.x) + (this.y - p.y) * (this.y - p.y));
  }
}
