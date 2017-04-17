// Solution candidate to Russell for COP 4516, Spring 2017

import java.util.*;

public class russell {
  public static final boolean DEBUG = true;
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int counter = 1;
    while (stdin.hasNext()) {
      if (DEBUG) System.out.println("Case #" + counter++);

        // Galactic Yield Sign (a plane)
        Point3 a     = new Point3(stdin.nextDouble(), stdin.nextDouble(), stdin.nextDouble());
        Point3 b     = new Point3(stdin.nextDouble(), stdin.nextDouble(), stdin.nextDouble());
        Point3 c     = new Point3(stdin.nextDouble(), stdin.nextDouble(), stdin.nextDouble());
        Plane3 plane = new Plane3(a, b, c);

        // Position of Russell's telescope
        Point3 s = new Point3(stdin.nextDouble(), stdin.nextDouble(), stdin.nextDouble());
        // Point to look at
        Point3 e = new Point3(stdin.nextDouble(), stdin.nextDouble(), stdin.nextDouble());

        // Make a line of sight out from telescope to point to look at
        Vector3 directionVector = new Vector3(s, e);
        Line3 segment = new Line3(s, e);

        if (DEBUG) System.out.println("Line: " + s + " " + e);
        if (DEBUG) System.out.println("Plane: " + plane);

        // Solve!
        Point3 intersectionPoint = directionVector.intersects(plane);
        boolean yieldSignContainsPoint = plane.contains(intersectionPoint);
        boolean intersectionPointOnSegment = intersectionPoint.liesOn(segment);

        if (intersectionPoint != null)
          if (DEBUG) System.out.println("intersectionPoint: " + intersectionPoint);

        if (yieldSignContainsPoint && intersectionPointOnSegment) {
          System.out.println("No");
        } else {
          System.out.println("Yes");
        }

        if (DEBUG) System.out.println();
        if (DEBUG) System.out.println();

    }
  }
}


class Point3 {
  double x, y, z;

  // NOTE(timp): In case I need to copy a point
  public Point3(Point3 p) {
    this.x = p.x; this.y = p.y; this.z = p.z;
  }

  public Point3(double x, double y, double z) {
    this.x = x; this.y = y; this.z = z;
  }

  public boolean liesOn(Line3 l) {
    // TODO(timp): Work out algebra and come up with formula to
    // check if a point is on a line segment
    // Something something... distance from point to both end points
    // sum is the length of the line means it's on the line?

    // Get magnitude of the line
    // Line segments
    double AC = l.mag;
    double AB = Math.sqrt(Math.pow(x - l.a.x, 2) + Math.pow(y - l.a.y, 2) + Math.pow(z - l.a.z, 2));
    double BC = Math.sqrt(Math.pow(l.b.x - x, 2) + Math.pow(l.b.y - y, 2) + Math.pow(l.b.z - z, 2));
    if (russell.DEBUG) System.out.println("Mag: " + AC);
    if (russell.DEBUG) System.out.println("AB: " + AB);
    if (russell.DEBUG) System.out.println("BC: " + BC);
    if (russell.DEBUG) System.out.println("AB + BC: " + (BC + AB));
    return (Math.abs(AC - AB - BC) < 1e-5);
  }

  public String toString() {
    return "("+x+", "+y+", "+z+")";
  }
}

class Vector3 {
  double x, y, z;
  // double mag;
  Point3 pt; // Some arbitrarily chosen point, so I picked first point provided

  // NOTE(timp): In case I need to copy a vector
  public Vector3(Vector3 v) {
    this.x = v.x; this.y = v.y; this.z = v.z;
  }

  public Vector3(double x, double y, double z) {
    this.x = x; this.y = y; this.z = z;
  }

  public Vector3(Point3 a, Point3 b) {
    this.x = b.x - a.x;
    this.y = b.y - a.y;
    this.z = b.z - a.z;
    this.pt = new Point3(a);
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

  // Find the intersection point a vector has with a plane
  public Point3 intersects(Plane3 p) {
    // All the constants will be the position vector, i.e., some point on the line
    if (russell.DEBUG) System.out.println("Constant Terms: <" + this.pt + ">");

    // λ coefficients will be all those in the direction vector of the line
    if (russell.DEBUG) System.out.println("λ coefficients: " + this);

    // Plane equation is each component multiplied by the respective normal
    // vector of the plane
    if (russell.DEBUG) System.out.printf("Plane equation: %.1f(%.1f + %.1fλ) + %.1f(%.1f + %.1fλ) + %.1f(%.1f + %.1fλ) = %.3f\n", p.v.x, pt.x, this.x, p.v.y, pt.y, this.y, p.v.z, pt.z, this.z, p.d);

    // Gather all constant terms from the plane equation
    double constantTerm = (p.v.x * pt.x) + (p.v.y * pt.y) + (p.v.z * pt.z);
    if (russell.DEBUG) System.out.printf("K: %.2f\t", constantTerm);

    // Gather all λ terms
    double lambdaCoefficient = (p.v.x * this.x) + (p.v.y * this.y) + (p.v.z * this.z);
    if (russell.DEBUG) System.out.printf("K_λ: %.1f\n", lambdaCoefficient);

    // If the coefficient for lambda is zero, we have a degenerate case
    if (lambdaCoefficient == 0) {
      return null;
    }

    // Solve for the unknown, λ.
    double lambda = (p.d - constantTerm) / lambdaCoefficient;
    if (russell.DEBUG) System.out.printf("λ: %.2f\n", lambda);

    // Plug in λ into the parametric equations for the line to find
    // point of intersection
    double newX = lambda * this.x + pt.x;
    double newY = lambda * this.y + pt.y;
    double newZ = lambda * this.z + pt.z;
    return new Point3(newX, newY, newZ);
  }

  public String toString() {
    return "<"+x+", "+y+", "+z+">";
  }
}

class Line3 {
  Point3 a, b; // start and end points
  double mag; // magnitude
  Vector3 unitVector;

  public Line3(Point3 a, Point3 b) {
    this.a = a; this.b = b;
    this.unitVector = new Vector3(a, b);
    this.mag = Math.sqrt(this.unitVector.magsq());
  }
}

class Plane3 {
  public Point3 a, b, c;
  Vector3 v; // perpendicular vector to plane
  Point3 centroid;
  double lengthToCenter;
  double d; // distance from the origin

  // NOTE(timp): In case I need to copy a plane
  public Plane3(Plane3 p) {
    this.a = p.a; this.b = p.b; this.c = p.c;
    this.v = p.v; this.d = p.d;
    this.centroid = p.centroid;
    this.lengthToCenter = p.lengthToCenter;
  }

  public Plane3(Point3 a, Point3 b, Point3 c) {
    this.a = a; this.b = b; this.c = c;
    // Get the vectors from one of the points, arbitrarily chosen to always
    // be A. From this, we can get an perpendicular vector to the plane.
    // In short, the vector that points outward from the face of our
    // 'piece of paper', like discussed in lecture.
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

  public boolean contains(Point3 p) {
    // TODO(timp): Figure out how to do a point-in-poly, one down the z-axis
    // and another down the x-axis, possibly a third down the y-axis

    // Let's try vector math!
    // Ignore the Z axis and find the angle between the intersection point and
    // and the polygon (yield sign)
    // God damn it Arup. I only know how to do this in 2D, not 3D, so now
    // I have to make a Vector2 D:<
    Vector3 PA = new Vector3(p, this.a);
    Vector3 PB = new Vector3(p, this.b);
    Vector3 PC = new Vector3(p, this.c);
    double ABtheta, BCtheta, CAtheta, theta;
    theta = Math.acos(PA.dot(PB)/(Math.sqrt(PA.magsq()) * Math.sqrt(PB.magsq())));
    if (russell.DEBUG) System.out.println("Θ = " + theta);

    // Kinda works! Now to create all the points and vectors in 2D so I can
    // ignore one of the dimensions.

    // TODO(timp):


    return false;

  }

  public String toString() {
    return a+", "+b+", "+c;
  }
}

class Vector2 {

    public double x;
    public double y;

    public Vector2(double myx, double myy) {
        x = myx;
        y = myy;
    }

    public Vector2(Point2 start, Point2 end) {
        x = end.x - start.x;
        y = end.y - start.y;
    }

    public double dot(Vector2 other) {
        return this.x*other.x + this.y*other.y;
    }

    public double mag() {
        return Math.sqrt(x*x+y*y);
    }

    // Thjs formula comes from using the relationship between the dot product and
    // the cosine of the included angle.
    public double angle(Vector2 other) {
        return Math.acos(this.dot(other)/mag()/other.mag());
    }

    public double signedCrossMag(Vector2 other) {
        return this.x*other.y-other.x*this.y;
    }

    public double crossMag(Vector2 other) {
        return Math.abs(signedCrossMag(other));
    }
}
