// 2D Point Class for Convex Hull

class Point2 implements Comparable<Point2> {

	// Reference point - set by getIndexMin
	public static int refX;
	public static int refY;

	public int x;
	public int y;

	public Point2(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// Returns the vector from this to other.
	public Point2 getVect(Point2 other) {
		return new Point2(other.x - x, other.y - y);
	}

	// Returns the distance between this and other.
	public double dist(Point2 other) {
		return Math.sqrt((other.x - x)*(other.x - x) + (other.y - y)*(other.y - y));
	}

	// Returns the magnitude ot this cross product other.
	public int crossProductMag(Point2 other) {
		return (this.x * other.y) - (other.x * this.y);
	}

	// returns true iff this to mid to next is a right turn (180 degree is considered right turn).
	public boolean isRightTurn(Point2 mid, Point2 next) {
		Point2 v1 = getVect(mid);
		Point2 v2 = mid.getVect(next);
		return v1.crossProductMag(v2) <= 0; // <= includes collinear, < does not
	}

	// returns true iff this to mid to next is a left turn (180 degree is considered left turn).
	public boolean isLeftTurn(Point2 mid, Point2 next) {
		Point2 v1 = getVect(mid);
		Point2 v2 = mid.getVect(next);
		return v1.crossProductMag(v2) >= 0; // >= includes collinear, > does not
	}

	// Returns true iff this point is the origin.
	public boolean isZero() {
		return x == 0 && y == 0;
	}

	public int compareTo(Point2 other) {

		Point2 myRef = new Point2(refX, refY);
		Point2 v1 = myRef.getVect(this);
		Point2 v2 = myRef.getVect(other);

		// To avoid 0 issues.
		if (v1.isZero()) return -1;
		if (v2.isZero()) return 1;

		// Angles are different, we are going clockwise here.
		if (v1.crossProductMag(v2) != 0)
			// return v1.crossProductMag(v2); // Clockwise check
			return -v1.crossProductMag(v2); // Counter-clockwise

		// This should work, smaller vectors come first.
		if (myRef.dist(v1) < myRef.dist(v2)) return -1;
		return 1;
	}

	public String toString() {
		return this.x + " " + this.y;
	}
}
