class LineSegment3 {
  Point3 a; // Start
  Point3 b; // End
  double length;
  Vector3 v;

  public LineSegment3(LineSegment3 l) {
    this.a = l.a; this.b = l.b; this.length = l.length;
  }

  public LineSegment3(Point3 a, Point3 b) {
    this.a = a; this.b = b;
    this.v = new Vector3(this.a, this.b);
    this.length =
      Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2) + Math.pow(a.z - b.z, 2));
  }

  public String toString() {
    return "LineSegment3( "+a+", "+b+" )";
  }

  // The functional method of this program!
  public Point3 intersects(Plane3 p) {
    // All the constants will be the position vector, i.e., some point on the line
    if (russell.DEBUG) System.out.println("Constant Terms: <" + this.a + ">");

    // λ coefficients will be all those in the direction vector of the line
    if (russell.DEBUG) System.out.println("λ coefficients: " + this);

    // Plane equation is each component multiplied by the respective normal
    // vector of the plane
    if (russell.DEBUG) System.out.printf("Plane equation: %.1f(%.1f + %.1fλ) + %.1f(%.1f + %.1fλ) + %.1f(%.1f + %.1fλ) = %.3f\n", p.v.x, a.x, this.x, p.v.y, a.y, this.y, p.v.z, a.z, this.z, p.d);

    // Gather all constant terms from the plane equation
    double constantTerm = (p.v.x * a.x) + (p.v.y * a.y) + (p.v.z * a.z);
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
    double newX = lambda * this.x + a.x;
    double newY = lambda * this.y + a.y;
    double newZ = lambda * this.z + a.z;
    return new Point3(newX, newY, newZ);
  }
}
