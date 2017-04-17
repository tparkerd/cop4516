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


    // How to find if
    // Calculate the centroid of the yield sign
    this.centroid = new Point3( (a.x + b.x + c.x) / 3, (a.y + b.y + c.y) / 3, (a.z + b.z + c.z) / 3  );
    if (russell.DEBUG) System.out.println("Centroid: " + this.centroid);
    // Add first point distance/magnitude
    double A = Math.sqrt( Math.pow(centroid.x - a.x, 2) + Math.pow(centroid.y - a.y, 2) + Math.pow(centroid.z - a.z, 2) );
    // Add second point distance/magnitude
    double B = Math.sqrt( Math.pow(centroid.x - b.x, 2) + Math.pow(centroid.y - b.y, 2) + Math.pow(centroid.z - b.z, 2) );
    // Add third point distance/magnitude
    if (russell.DEBUG) System.out.printf("Cent: %.2f, Cz: %.2f\n", centroid.z, c.z);

    double C = Math.sqrt( Math.pow(centroid.x - c.x, 2) + Math.pow(centroid.y - c.y, 2) + Math.pow(centroid.z - c.z, 2) );
    if (russell.DEBUG) System.out.printf("A: %.2f, B: %.2f, C: %.2f\n", A, B, C);
    this.lengthToCenter = A + B + C;
  }

  public double distanceToIntersectionPoint(Point3 p) {
    double distance = 0;
    // Add first point distance/magnitude
    distance += Math.sqrt( Math.pow(p.x - a.x, 2) + Math.pow(p.y - a.y, 2) + Math.pow(p.z - a.z, 2) );
    // Add second point distance/magnitude
    distance += Math.sqrt( Math.pow(p.x - b.x, 2) + Math.pow(p.y - b.y, 2) + Math.pow(p.z - b.z, 2) );
    // Add third point distance/magnitude
    distance += Math.sqrt( Math.pow(p.x - c.x, 2) + Math.pow(p.y - c.y, 2) + Math.pow(p.z - c.z, 2) );
    return distance;
  }

  public boolean contains(Point3 p) {
    // if (russell.DEBUG) System.out.println("Centroid distance: " + lengthToCenter);
    // if (russell.DEBUG) System.out.println("Intersect distance: " + this.distanceToIntersectionPoint(p));
    // return (Math.abs(lengthToCenter - this.distanceToIntersectionPoint(p)) < 1e-6);
    // // I have no idea what I'm doing at this point, but I thought the length of any point within the
    // // triangle would have to have a sum of segments bisecting the angles of each vertex, so
    // // the distance to the centroid would be equal to any point within the sign


    // TODO(timp): Figure out how to do a point-in-poly, one down the z-axis
    // and another down the x-axis, possibly a third down the y-axis

    // // Point in poly
    // // From front, ignoring the z-axis
    // double xyAngle = 0;
    // double yzAngle = 0;
    // // For each vertex of the yield sign...
    // double aAngle = Math.abs(Math.atan2(p.x - a.x, p.y - a.y));
    // double bAngle = Math.abs(Math.atan2(p.x - b.x, p.y - b.y));
    // double cAngle = Math.abs(Math.atan2(p.x - c.x, p.y - c.y));
    // if (russell.DEBUG) System.out.println("Ignore the Z");
    // if (russell.DEBUG) System.out.printf("∠ A %.3f\t∠ B %.3f\t∠ C %.3f\n", aAngle, bAngle, cAngle);
    // if (russell.DEBUG) System.out.println(aAngle + bAngle + cAngle);
    //
    // if (russell.DEBUG) System.out.println("Ignore the X");
    // aAngle = Math.abs(Math.atan2(p.y - a.y, p.z - a.z));
    // bAngle = Math.abs(Math.atan2(p.y - b.y, p.z - b.z));
    // cAngle = Math.abs(Math.atan2(p.y - c.y, p.z - c.z));
    // if (russell.DEBUG) System.out.printf("∠ A %.3f\t∠ B %.3f\t∠ C %.3f\n", aAngle, bAngle, cAngle);
    // if (russell.DEBUG) System.out.println(aAngle + bAngle + cAngle);
    //
    // if (russell.DEBUG) System.out.println("Ignore the Y");
    // aAngle = Math.abs(Math.atan2(p.x - a.x, p.z - a.z));
    // bAngle = Math.abs(Math.atan2(p.x - b.x, p.z - b.z));
    // cAngle = Math.abs(Math.atan2(p.x - c.x, p.z - c.z));
    // if (russell.DEBUG) System.out.printf("∠ A %.3f\t∠ B %.3f\t∠ C %.3f\n", aAngle, bAngle, cAngle);
    // if (russell.DEBUG) System.out.println(aAngle + bAngle + cAngle);


    // Let's try vector math!
    // Vector3 PA = new Vector3(p, this.a);
    // Vector3 PB = new Vector3(p, this.b);
    Vector3 PA = new Vector3(new Point3(0,0,0), new Point3(1,0,0));
    Vector3 PB = new Vector3(new Point3(0,0,0), new Point3(0,1,0));
    double dotProduct = PA.dot(PB);
    double PAmag = Math.sqrt(PA.magsq());
    double PBmag = Math.sqrt(PB.magsq());
    double theta = Math.acos(dotProduct/(PAmag * PBmag));

    if (russell.DEBUG) System.out.println("Θ = " + theta);

    return false;

  }

  public String toString() {
    return a+", "+b+", "+c;
  }
}
