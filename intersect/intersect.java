// Solution Candidate to Intersect for COP 4516 Spring 2017
// By Tim Parker

import java.util.*;

public class intersect {
  // Flag to test for degenerate case: lines lies on plane
  public static boolean overlapFlag;

  public static final boolean DEBUG = false;
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();

    // For each case...
    for (int i = 1; i <= nCases; i++) {
      System.out.printf("Data Set #%d:\n", i);

      // Get line input
      Point3 start = new Point3(stdin.nextInt(), stdin.nextInt(), stdin.nextInt());
      Point3 end   = new Point3(stdin.nextInt(), stdin.nextInt(), stdin.nextInt());
      Vector3 line = new Vector3(start, end); // This is the direction vector of the line
      if (DEBUG) System.out.println("Line vector: " + line);

      // Get plane input
      Point3 a     = new Point3(stdin.nextInt(), stdin.nextInt(), stdin.nextInt());
      Point3 b     = new Point3(stdin.nextInt(), stdin.nextInt(), stdin.nextInt());
      Point3 c     = new Point3(stdin.nextInt(), stdin.nextInt(), stdin.nextInt());
      Plane3 plane = new Plane3(a, b, c);

      if (DEBUG) System.out.println("Points of Plane [" + plane + "]");
      if (DEBUG) System.out.println("Normal Vector of Plane " + plane.v);
      if (DEBUG) System.out.println("Magnitude of Normal Vector of Plane: " + plane.d);

      // Assume that the line does not lie on the plane
      overlapFlag = false;
      // Solve!
      Point3 answer = line.intersects(plane);
      // Output answer!
      if (answer != null) {
        System.out.printf("The intersection is the point (%.1f, %.1f, %.1f).\n\n",answer.x, answer.y, answer.z);
      } else {
        if (overlapFlag) {
          System.out.println("The line lies on the plane.\n");
        } else {
          System.out.println("There is no intersection.\n");
        }
      }
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

  public String toString() {
    return "("+x+", "+y+", "+z+")";
  }
}

class Vector3 {
  public double x, y, z;
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

  // The functional method of this program!
  public Point3 intersects(Plane3 p) {
    // All the constants will be the position vector, i.e., some point on the line
    if (intersect.DEBUG) System.out.println("Constant Terms: <" + this.pt + ">");

    // λ coefficients will be all those in the direction vector of the line
    if (intersect.DEBUG) System.out.println("λ coefficients: " + this);

    // Plane equation is each component multiplied by the respective normal
    // vector of the plane
    if (intersect.DEBUG) System.out.printf("Plane equation: %.1f(%.1f + %.1fλ) + %.1f(%.1f + %.1fλ) + %.1f(%.1f + %.1fλ) = %.3f\n", p.v.x, pt.x, this.x, p.v.y, pt.y, this.y, p.v.z, pt.z, this.z, p.d);

    // Gather all constant terms from the plane equation
    double constantTerm = (p.v.x * pt.x) + (p.v.y * pt.y) + (p.v.z * pt.z);
    if (intersect.DEBUG) System.out.printf("K: %.2f\t", constantTerm);

    // Gather all λ terms
    double lambdaCoefficient = (p.v.x * this.x) + (p.v.y * this.y) + (p.v.z * this.z);
    if (intersect.DEBUG) System.out.printf("K_λ: %.1f\n", lambdaCoefficient);

    // If the coefficient for lambda is zero, we have a degenerate case
    if (lambdaCoefficient == 0) {
      // If the constant is the same as the actual magnitude of the plane's
      // normal vector, the line lies on the plane
      // Within a margin of error because rounding is a pain in the ass
      if (Math.abs(constantTerm - p.d) < 1e-6)
        intersect.overlapFlag = true;

      return null;
    }

    // Solve for the unknown, λ.
    double lambda = (p.d - constantTerm) / lambdaCoefficient;
    if (intersect.DEBUG) System.out.printf("λ: %.2f\n", lambda);

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

class Plane3 {
  public Point3 a, b, c;
  Vector3 v; // perpendicular vector to plane
  double d; // distance from the origin

  // NOTE(timp): In case I need to copy a plane
  public Plane3(Plane3 p) {
    this.a = p.a; this.b = p.b; this.c = p.c; this.v = p.v; this.d = p.d;
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
  public String toString() {
    return a+", "+b+", "+c;
  }
}
