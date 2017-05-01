/*

Input
  Two lines, four points, two coordinates each.
  int AxStart = 5; int AyStart = 0; AxEnd = 5; AyEnd = 100;
  int BxStart = 3; int ByStart = 2; BxEnd = 7; ByEnd = 2;

  Java handles the basics operations for two, 2D lines.
  It allows for int, float, or doubles as the coordinates of each point.

  Two types of lines and points: float and double

  Point2D startA = new Point2D.Double(INPUT_X, INPUT_Y);
  Point2D endA     = new Point2D.Double(INPUT_X, INPUT_Y);
  Point2D startB = new Point2D.Double(INPUT_X, INPUT_Y);
  Point2D endB   = new Point2D.Double(INPUT_X, INPUT_Y);
  Line2D A = new Line2D.Double(startA, endA);
  Line2D B = new Line2D.Double(startB, endB);

  Boolean -- return true if line intersects the other.
  *Shared end points are considered intersecting
  A.intersectsLine(B)

  You can also use a class method:
  Boolean -- return true if lines intersect
  Line2D.linesIntersect(x1, y1, x2, y2, x3, y3, x4, y4);

  *A.contains(Point2D p) -- always returns false b/c it is based on area and a line has no area
  Refer to 3D code and alternate 2D intersection code for point on line operations.

*/


Point3 start = new Point3(stdin.nextInt(), stdin.nextInt(), stdin.nextInt());
Point3 end   = new Point3(stdin.nextInt(), stdin.nextInt(), stdin.nextInt());
Vector3 line = new Vector3(start, end); // This is the direction vector of the line

Point3 a     = new Point3(stdin.nextInt(), stdin.nextInt(), stdin.nextInt());
Point3 b     = new Point3(stdin.nextInt(), stdin.nextInt(), stdin.nextInt());
Point3 c     = new Point3(stdin.nextInt(), stdin.nextInt(), stdin.nextInt());
Plane3 plane = new Plane3(a, b, c);

class Point2 {
  public int x, y;

  public Point2(int x, int y) {
    this.x = x; this.y = y;
  }

  public double crossProduct(Point2 o) {
    return ((this.x * o.y) - (o.x * this.y));
  }
}

class Line2 {
  Point2 a, b;

  public Line2(Point2 a, Point2 b) {
    this.a = a;
    this.b = b;
  }

  public boolean intersects(Line2 o) {

  }
}

class Vector2 {
  double x, y;
  Point2 pt; // arbitrary point on vector

  public Vector2(Point2 a, Point2 b) {
    this.x = b.x - a.x;
    this.y = b.y - a.y;
  }
}






class Point3 {
  double x, y, z;
  public Point3(Point3 p) {
    this.x = p.x; this.y = p.y; this.z = p.z;
  }
  public Point3(double x, double y, double z) {
    this.x = x; this.y = y; this.z = z;
  }
}

class Vector3 {
  public boolean liesOnPlane; // stores whether the parallel to plane
  public double x, y, z;
  Point3 pt; // Some arbitrarily chosen point, so I picked first point provided

  public Vector3(double x, double y, double z) {
    this.x = x; this.y = y; this.z = z; this.liesOnPlane = false;
  }

  public Vector3(Point3 a, Point3 b) {
    this.x = b.x - a.x; this.y = b.y - a.y; this.z = b.z - a.z;
    this.liesOnPlane = false;
    this.pt = new Point3(a);
  }

  // Finds intersection point of vector in a plane
  // Returns null if it is an degenerate call. If this happens,
  // make sure to see what type it is. liesOnPlane will contain will be true
  // if the vector is on the plane itself, false if just parallel
  public Point3 intersects(Plane3 p) {
    // Gather all constant terms from the plane equation
    double constantTerm = (p.v.x * pt.x) + (p.v.y * pt.y) + (p.v.z * pt.z);
    // Gather all λ terms
    double lambdaCoefficient = (p.v.x * this.x) + (p.v.y * this.y) + (p.v.z * this.z);

    // If the coefficient for lambda is zero, we have a degenerate case
    if (lambdaCoefficient == 0) {
      // If the constant is the same as the actual magnitude of the plane's
      // normal vector, the line lies on the plane
      // Within a margin of error because rounding is a pain in the ass
      if (Math.abs(constantTerm - p.d) < 1e-6)
        this.liesOnPlane = true;
      return null;
    }

    // Solve for the unknown, λ.
    double lambda = (p.d - constantTerm) / lambdaCoefficient;
    double newX = lambda * this.x + pt.x;
    double newY = lambda * this.y + pt.y;
    double newZ = lambda * this.z + pt.z;
    return new Point3(newX, newY, newZ); // Intersection point
  }

  public Vector3 cross(Vector3 v) {
    return new Vector3((y * v.z - z * v.y), (z * v.x - x * v.z), (x * v.y-y * v.x));
  }

  public double magsq() {
    return x*x + y*y + z*z;
  }

  public double dot(Vector3 v) {
    return x*v.x + y*v.y + z*v.z;
  }
}

class Plane3 {
  public Point3 a, b, c;
  Vector3 v; // perpendicular vector to plane
  double d; // distance from the origin

  public Plane3(Point3 a, Point3 b, Point3 c) {
    this.a = a; this.b = b; this.c = c;
    Vector3 AB = new Vector3(a, b);
    Vector3 AC = new Vector3(a, c);
    this.v = AB.cross(AC);
    this.d = Math.sqrt(this.v.magsq()); // Sqrt(x*x + y*y + z*z)
    // Make vector a unit vector
    this.v.x /= this.d;
    this.v.y /= this.d;
    this.v.z /= this.d;
     // Scale down the magnitude to match the unit vector
    this.d = a.x * this.v.x + a.y * this.v.y + a.z * this.v.z;
  }
}
