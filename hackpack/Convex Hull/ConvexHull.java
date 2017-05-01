// Team CapriSun
// Adapted Graham Scan Algorithm
// Original by Arup Guha

import java.util.*;

public class ConvexHull {
	private Point2[] pts;
	private ArrayList<Point2> pointList;

	public ConvexHull(Point2[] pts) {
		int refPt = getIndexMin(pts);
		// Set the reference point
		Point2.refX = pts[refPt].x;
		Point2.refY = pts[refPt].y;
		this.pts = pts.clone();
		this.pointList = new ArrayList<Point2>();
	}

	public ArrayList<Point2> getVertices() {
		grahamScan(this.pts);
		return this.pointList;
	}

	// Returns the index of the reference point in the list of points
	public static int getIndexMin(Point2[] pts) {
		int m = 0;
		for (int i = 1; i < pts.length; i++)
			if (pts[i].y < pts[m].y || (pts[i].y == pts[m].y && pts[i].x < pts[m].x)) // Counter-clockwise
			// if (pts[i].y > pts[m].y || (pts[i].y == pts[m].y && pts[i].x < pts[m].x)) // Clockwise
				m = i;
		return m;
	}

	public void grahamScan(Point2[] pts) {
		// Sort the points by angle with reference point.
		Arrays.sort(pts);

		// Push first two points on.
		Stack<Point2> stk = new Stack<Point2>();
		stk.push(pts[0]);
		stk.push(pts[1]);

		// Process the remaining points
		for (int i = 2; i < pts.length; i++) {

			// Get last three pts.
			Point2 cur = pts[i];
			Point2 mid = stk.pop();
			Point2 prev = stk.pop();

			// Pop off the left turns.
			// Collinearity check
			boolean collinearFlag = false;
			// while (!prev.isRightTurn(mid, cur)) { // Clockwise
			while (!prev.isLeftTurn(mid, cur)) { // Counter-clockwise
				mid = prev;
				// Bail out if degenerate
				if (stk.isEmpty()) {
					collinearFlag = true;
					break;
				}
				prev = stk.pop();
			}

			// Push back the last right turn.
			stk.push(prev);
			if (!collinearFlag) // If a degenerate case, don't put the mid point back
			stk.push(mid);
			stk.push(cur);
		}

		ArrayList<Point2> list = new ArrayList<Point2>();
		// Add up distances around the hull.
		double res = 0;
		Point2 cur = pts[0];
		while (stk.size() > 0) {
			Point2 next = stk.pop();
			res += cur.dist(next);
			cur = next;
			list.add(next);
		}

		// Reverse the list b/c it's a stack
		// Add this point to the list of points that make up the hull
		for (int i = list.size() - 1; i >= 0; i--) {
			this.pointList.add(list.get(i));
		}
	}
}
