package kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> pointSet;

    public         PointSET(){
        pointSet = new TreeSet<>();
    }// construct an empty set of points
    public           boolean isEmpty() {
        return pointSet.isEmpty();
    }                      // is the set empty?
    public               int size() {
        return pointSet.size();
    }                        // number of points in the set
    public              void insert(Point2D p)     {
        if (p == null) throw new IllegalArgumentException("point is null");
        pointSet.add(p);
    }          // add the point to the set (if it is not already in the set)
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("point is null");
        return pointSet.contains(p);
    }            // does the set contain point p?
    public              void draw() {
        for (Point2D p: pointSet) {
            StdDraw.point(p.x(), p.y());
        }
    }                        // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("rect is null");

        List<Point2D> pointsInRange = new ArrayList<>();
        for (Point2D p: pointSet) {
            if (p.x() <= rect.xmax() && p.y() <= rect.ymax() && p.x() >= rect.xmin() && p.y() >= rect.ymin()) {
                pointsInRange.add(p);
            }
        }
        return pointsInRange;
    }            // all points that are inside the rectangle (or on the boundary)
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("point is null");
        double min = Integer.MAX_VALUE;
        Point2D minPoint = null;
        for (Point2D currentPoint: pointSet) {
            double distance = currentPoint.distanceSquaredTo(p);
            if (distance < min) {
                min = distance;
                minPoint = currentPoint;
            }
        }
        return minPoint;
    }             // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {

    }                  // unit testing of the methods (optional)
}