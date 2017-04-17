// Solution candidate to Russell for COP 4516, Spring 2017

import java.util.*;

public class russell {
  public static final boolean DEBUG = false;
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    while (stdin.hasNext()) {
      // Galactic Yield Sign (a plane)
      Point3 a     = new Point3(stdin.nextDouble(), stdin.nextDouble(), stdin.nextDouble());
      Point3 b     = new Point3(stdin.nextDouble(), stdin.nextDouble(), stdin.nextDouble());
      Point3 c     = new Point3(stdin.nextDouble(), stdin.nextDouble(), stdin.nextDouble());
      Plane3 plane = new Plane3(a, b, c);

      // Position of Russell's telescope (s) & point to look at (e)
      Point3 s = new Point3(stdin.nextDouble(), stdin.nextDouble(), stdin.nextDouble());
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

      // Show the point of intersection for the vector and the plane
      if (intersectionPoint != null)
        if (DEBUG) System.out.println("intersectionPoint: " + intersectionPoint);

      // Check if it's within the polygon but ALSO, that the point of intersection
      // is also on the line segment that defines the line of sight from
      // Russell's telescope to the point of interest
      if (yieldSignContainsPoint && intersectionPointOnSegment) {
        System.out.println("No");
      } else {
        System.out.println("Yes");
      }
    }
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

  public boolean liesOn(Line3 l) {
    // Get magnitude of the line
    // Get the magnitude of the line segment and the segments
    // created by 'splitting' it at the intersection point
    double AC = l.mag;
    double AB = Math.sqrt(Math.pow(x - l.a.x, 2) + Math.pow(y - l.a.y, 2) + Math.pow(z - l.a.z, 2));
    double BC = Math.sqrt(Math.pow(l.b.x - x, 2) + Math.pow(l.b.y - y, 2) + Math.pow(l.b.z - z, 2));
    return (Math.abs(AC - AB - BC) < 1e-9);
  }

  public String toString() {
    return "("+x+", "+y+", "+z+")";
  }
}

class Vector3 {
  double x, y, z;
  Point3 pt; // Some arbitrarily chosen point, so I picked first point provided

  public Vector3(Vector3 v) {
    this.x = v.x; this.y = v.y; this.z = v.z; this.pt = v.pt;
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
  double mag;  // magnitude
  Vector3 unitVector;

  public Line3(Point3 a, Point3 b) {
    this.a = a; this.b = b;
    this.unitVector = new Vector3(a, b);
    this.mag = Math.sqrt(this.unitVector.magsq());
  }
}

class Plane3 {
  public Point3 a, b, c; // Vertices that define the plane
  Vector3 v; // perpendicular vector to plane
  double d; // distance from the origin

  // NOTE(timp): In case I need to copy a plane
  public Plane3(Plane3 p) {
    this.a = p.a; this.b = p.b; this.c = p.c;
    this.v = p.v; this.d = p.d;
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

  // Determines if a point lies within a polygon
  // Right now it's hardcoded to be a triangle, but using an arraylist
  // of points, we could repurpose this to be any polygon
  public boolean contains(Point3 p) {
    // Let's try vector math!
    // Sum the angles of the vectors from the intersection point to the vertices
    // of the yield sign
    // P = intersection point
    Vector3 PA = new Vector3(p, this.a); // Intersection point to A
    Vector3 PB = new Vector3(p, this.b); // Intersection point to B
    Vector3 PC = new Vector3(p, this.c); // Intersection point to C
    double ABtheta, BCtheta, CAtheta, theta;
    // Find the angles of ΔPAB, ΔPBC, and ΔPCA
    theta = Math.acos(PA.dot(PB)/(Math.sqrt(PA.magsq()) * Math.sqrt(PB.magsq())));
    theta += Math.acos(PB.dot(PC)/(Math.sqrt(PB.magsq()) * Math.sqrt(PC.magsq())));
    theta += Math.acos(PC.dot(PA)/(Math.sqrt(PC.magsq()) * Math.sqrt(PA.magsq())));
    if (russell.DEBUG) System.out.println("Θ = " + theta);

    // The angles between each of the point to vertex sum to 2π, it's within
    // the polygon
    return (2 * Math.PI - theta) < 1e-9;
  }

  public String toString() {
    return a+", "+b+", "+c;
  }
}
