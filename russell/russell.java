// Solution candidate to Russell for COP 4516, Spring 2017
// By Tim Parker

import java.util.*;

public class russell {
  public static final boolean DEBUG = true;
  public static boolean overlapFlag;
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

        if (DEBUG) System.out.println("Plane: " + plane);

        // Position of Russell's telescope
        Point3 s = new Point3(stdin.nextDouble(), stdin.nextDouble(), stdin.nextDouble());

        // Point to look at
        Point3 e = new Point3(stdin.nextDouble(), stdin.nextDouble(), stdin.nextDouble());

        // Make a line of sight out from telescope to point to look at
        Vector3 line = new Vector3(s, e);
        if (DEBUG) System.out.println("Line: " + s + " " + e);


        // Assume that the line does not lie on the plane
        overlapFlag = false;
        // Solve!
        Point3 answer = line.intersects(plane);
        // Output answer!
        // See if the line intersects the plane, if so, we need to check if the
        // intersection point actually lies on the provided line segment
        if (answer != null) {
          // Check if intersection point is on segment
          // If it is, the view is block!
          if (DEBUG) System.out.printf("The intersection is the point (%.1f, %.1f, %.1f).\n",answer.x, answer.y, answer.z);
          if (answer.liesOn(line)) {
            System.out.printf("NO! ~ (%.1f, %.1f, %.1f).\n",answer.x, answer.y, answer.z);
          // Otherwise, the view is not blocked, and we can seeeeeeee
          } else {
              System.out.println("Yes, we can see it.");
          }

        } else {
          if (overlapFlag) {
            System.out.println("The line lies on the plane.\n");
          } else {
            System.out.println("There is no intersection.\n");
          }
        }
        System.out.println();

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

  public boolean liesOn(Vector3 l) {
    // TODO(timp): Work out algebra and come up with formula to
    // check if a point is on a line segment
    // Something something... distance from point to both end points
    // sum is the length of the line means it's on the line?

    // Get magnitude of the line
    double AZ = l.l;
    double AK = Math.sqrt(Math.pow(x - l.pt.x, 2) + Math.pow(y - l.pt.y, 2) + Math.pow(z - l.pt.z, 2));
    double KZ = Math.sqrt(Math.pow(l.pt.x - x, 2) + Math.pow(l.pt.y - y, 2) + Math.pow(l.pt.z - z, 2));
    if (russell.DEBUG) System.out.println("Mag: " + AZ);
    if (russell.DEBUG) System.out.println("AK: " + AK);
    if (russell.DEBUG) System.out.println("KZ: " + KZ);
    if (russell.DEBUG) System.out.println("AK + KZ: " + (KZ + AK));
    return (AZ == AK + KZ);
  }

  public String toString() {
    return "("+x+", "+y+", "+z+")";
  }
}

class Vector3 {
  public double x, y, z, l;
  Point3 pt; // Some arbitrarily chosen point, so I picked first point provided

  // NOTE(timp): In case I need to copy a vector
  public Vector3(Vector3 v) {
    this.x = v.x; this.y = v.y; this.z = v.z; this.l = v.l;
  }

  public Vector3(double x, double y, double z) {
    this.x = x; this.y = y; this.z = z;
    this.l = Math.sqrt((this.x * this.x) + (this.y * this.y) + (this.z * this.z));
    if (russell.DEBUG) System.out.println("Leslie knope: " + this.l);
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
      // If the constant is the same as the actual magnitude of the plane's
      // normal vector, the line lies on the plane
      // Within a margin of error because rounding is a pain in the ass
      if (Math.abs(constantTerm - p.d) < 1e-6)
        russell.overlapFlag = true;

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
