// Tim Parker
// Original by Arup Guha
// Adapted to solve Convex Hull of Lattice Points for ACM 2009

import java.util.*;
import java.io.*;

public class convex {
	public static final boolean DEBUG = false;
	public static int caseNumber;

	public static void main(String[] args) {

		// Read in the points.
		Scanner stdin = new Scanner(System.in);
		int nCases = stdin.nextInt();

		// For each case...
		for (int i = 1; i <= nCases; i++) {
			caseNumber = stdin.nextInt();
			// Number of points
			int n = stdin.nextInt();

			pt[] pts = new pt[n];
			// For each point...
			for (int j = 0; j < n; j++) {
				int x = stdin.nextInt();
				int y = stdin.nextInt();
				pts[j] = new pt(x, y);
			}
			if (DEBUG) System.out.println(Arrays.toString(pts));

			// Set the reference point.
			int refIndex = getIndexMin(pts, n);
			pt.refX = pts[refIndex].x;
			pt.refY = pts[refIndex].y;

			if (DEBUG) System.out.println("Starting point: (" + pt.refX + ", " + pt.refY + ")");

			// Solve!
			grahamScan(pts, n);
		}
	}

	// Returns the point in pts with minimum y breaking tie by minimum x.
	public static int getIndexMin(pt[] pts, int n) {
		int res = 0;
		for (int i=1; i<n; i++)
			// NOTE(timp): changed to have a greater Y instead to get top-left point
			if (pts[i].y > pts[res].y || (pts[i].y == pts[res].y && pts[i].x < pts[res].x))
				res = i;
		return res;
	}

	public static void grahamScan(pt[] pts, int n) {

		// Sort the points by angle with reference point.
		Arrays.sort(pts);

		if (DEBUG) System.out.println("Sorted: " + Arrays.toString(pts));

		// Push first two points on.
		Stack<pt> myStack = new Stack<pt>();
		myStack.push(pts[0]);
		myStack.push(pts[1]);

		// Go through the rest of the points.
		for (int i=2; i<n; i++) {

			// Get last three pts.
			pt cur = pts[i];
			pt mid = myStack.pop();
			pt prev = myStack.pop();

			// Pop off the left turns.
			// NOTE(timp): This is my work around for the collinear points,
			// the degenerate case. If the stack is empty, we've run through
			// a bunch of collinear points until the end, so we have to bail out.
			boolean collinearFlag = false;
			while (!prev.isRightTurn(mid, cur)) {
				mid = prev;
				// Bail out if degenerate
				if (myStack.isEmpty()) {
					collinearFlag = true;
					break;
				}
				prev = myStack.pop();
			}

			// Push back the last right turn.
			myStack.push(prev);
			if (!collinearFlag) // If a degenerate case, don't put the mid point back
			myStack.push(mid);
			myStack.push(cur);
		}

		// Output the case number and the number of vertices in the hull
		System.out.printf("%d %d\n", caseNumber, myStack.size());


		ArrayList<pt> list = new ArrayList<pt>();
		// Add up distances around the hull.
		double res = 0;
		pt cur = pts[0];
		while (myStack.size() > 0) {
			pt next = myStack.pop();
			res += cur.dist(next);
			cur = next;
			list.add(next);
		}

		// Print off the stack in reverse order
		for (int i = list.size() - 1; i >= 0; i--)
			System.out.println(list.get(i));
	}
}

class pt implements Comparable<pt> {

	// Stores reference pt
	public static int refX;
	public static int refY;

	public int x;
	public int y;

	public pt(int myx, int myy) {
		x = myx;
		y = myy;
	}

	// Returns the vector from this to other.
	public pt getVect(pt other) {
		return new pt(other.x-x, other.y-y);
	}

	// Returns the distance between this and other.
	public double dist(pt other) {
		return Math.sqrt((other.x-x)*(other.x-x) + (other.y-y)*(other.y-y));
	}

	// Returns the magnitude ot this cross product other.
	public int crossProductMag(pt other) {
		return this.x*other.y - other.x*this.y;
	}

	// returns true iff this to mid to next is a right turn (180 degree is considered right turn).
	public boolean isRightTurn(pt mid, pt next) {
		pt v1 = getVect(mid);
		pt v2 = mid.getVect(next);
		return v1.crossProductMag(v2) < 0; // NOTE(timp): Changed to get next clockwise angle
	}

	// Returns true iff this pt is the origin.
	public boolean isZero() {
		return x == 0 && y == 0;
	}

	public int compareTo(pt other) {

		pt myRef = new pt(refX, refY);
		pt v1 = myRef.getVect(this);
		pt v2 = myRef.getVect(other);

		// To avoid 0 issues.
		if (v1.isZero()) return -1;
		if (v2.isZero()) return 1;

		// Angles are different, we are going clockwise here.
		if (v1.crossProductMag(v2) != 0)
			return v1.crossProductMag(v2); //NOTE(timp): no longer negative!

		// This should work, smaller vectors come first.
		if (myRef.dist(v1) < myRef.dist(v2)) return -1;
		return 1;
	}

	// Added for quick printing from the grahamScan method
	public String toString() {
		return this.x+" "+this.y;
	}
}
