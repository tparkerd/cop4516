import java.util.*;

public class intersect {
  public static boolean overlapFlag;

  public static final boolean DEBUG = true;
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();

    // For each case...
    for (int i = 1; i <= nCases; i++) {
      System.out.printf("Data Set #%d:\n", i);

      // Get line input
      Point3 start = new Point3(stdin.nextInt(), stdin.nextInt(), stdin.nextInt());
      Point3 end   = new Point3(stdin.nextInt(), stdin.nextInt(), stdin.nextInt());
      Line3  line  = new Line3(start, end);

      if (DEBUG) System.out.println(line);

      // Create a direction vector of the line
      Vector3 directionVectorOfLine = new Vector3(line);

      if (DEBUG) System.out.println(directionVectorOfLine);

      // Get plane input
      Point3 a     = new Point3(stdin.nextInt(), stdin.nextInt(), stdin.nextInt());
      Point3 b     = new Point3(stdin.nextInt(), stdin.nextInt(), stdin.nextInt());
      Point3 c     = new Point3(stdin.nextInt(), stdin.nextInt(), stdin.nextInt());
      Plane3 plane = new Plane3(a, b, c);

      if (DEBUG) System.out.println(plane);

      // // Assume that the line does not lie on the plane
      // Point3 answer = line.intersects(plane);
      // overlapFlag = false;
      // if (overlapFlag) {
      // } else if (answer != null) {
      //   System.out.println("The intersection is the point " + answer.toString());
      // } else {
      //   System.out.println("There is no intersection.");
      // }

    }
  }
}


class Point3 {
  float x, y, z;

  public Point3(Point3 p) {
    this.x = p.x; this.y = p.y; this.z = p.z;
  }

  public Point3(int x, int y, int z) {
    this.x = x; this.y = y; this.z = z;
  }

  public String toString() {
    return "Point3("+x+", "+y+", "+z+")";
  }
}

class Line3 {
  Point3 a; // Start
  Point3 b; // End
  Vector3 v;

  public Line3(Line3 l) {
    this.a = l.a; this.b = l.b;
  }

  public Line3(Point3 a, Point3 b) {
    this.a = a; this.b = b;
    this.v = new Vector3(this.a, this.b);
  }

  public String toString() {
    return "Line3( "+a+", "+b+" )";
  }

  public Point3 intersects(Plane3 p) {

    return new Point3(-1,2,1);

    // return null;
  }
}

class Vector3 {
  public float x, y, z;

  public Vector3(float x, float y, float z) {
    this.x = x; this.y = y; this.z = z;
  }

  public Vector3(Point3 a, Point3 b) {
    this.x = a.x - b.x;
    this.y = a.y - b.y;
    this.z = a.z - b.z;
  }

  public Vector3(Line3 l) {
    this.x = l.a.x - l.b.x;
    this.y = l.a.y - l.b.y;
    this.z = l.a.z - l.b.z;
  }

  public Vector3 cross(Vector3 o) {
    return new Vector3((y * o.z - z * o.y), (z * o.x - x * o.z), (x * o.y-y * o.x));
  }

  public float magsq() {
    return x*x + y*y + z*z;
  }

  public float dot(Vector3 other) {
    return x*other.x + y*other.y + z*other.z;
  }

  public String toString() {
    return "<"+x+", "+y+", "+z+">";
  }
}

class Plane3 {
  public Point3 a, b, c;
  // TODO(timp) : Need to figure how how to convert the points to a vector
  Vector3 v; // Need to figure how how to convert the plane to have just
             // the vector and its distance from the origin

  public Plane3(Plane3 p) {
    this.a = p.a; this.b = p.b; this.c = p.c;
  }

  public Plane3(Point3 a, Point3 b, Point3 c) {
    this.a = a; this.b = b; this.c = c;
  }
  public String toString() {
    return "Plane3( "+a+", "+b+", "+c+" )";
  }
}
