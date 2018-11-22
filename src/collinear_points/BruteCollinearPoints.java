package collinear_points;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Brute force algorithm for finding all line segments containing 4 or more collinear points from a set.
 */
public class BruteCollinearPoints {

    private LineSegment[] lineSegments;

    /**
     * Finds all line segments containing 4 points
     *
     * @param points the input points to analyze
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Points array is null.");
        }

        this.analyze(Arrays.copyOf(points, points.length));
    }

    /**
     * Get number of line segments formed by these points.
     *
     * @return the number of line segments
     */
    public int numberOfSegments() {
        return this.lineSegments.length;
    }

    /**
     * Get line segments formed by these points.
     *
     * @return the line segments
     */
    public LineSegment[] segments() {
        return Arrays.copyOf(this.lineSegments, this.lineSegments.length);
    }

    /**
     * Analise these points and find all segments.
     *
     * @param points the points to analyze
     */
    private void analyze(Point[] points) {
        List<LineSegment> tempSegments = new LinkedList<>();
        Point[] tempPoints = new Point[4];

        for (int i = 0; i < points.length; i++) {
            Point point0 = points[i];
            tempPoints[0] = point0;

            if (point0 == null) {
                throw new IllegalArgumentException("Point is null.");
            }

            for (int j = i + 1; j < points.length; j++) {
                Point point1 = points[j];
                tempPoints[1] = point1;

                if (point1 == null) {
                    throw new IllegalArgumentException("Point is null.");
                } else if (point1.compareTo(point0) == 0) {
                    throw new IllegalArgumentException("Point is identical.");
                }

                double slope1 = point0.slopeTo(point1);

                for (int k = j + 1; k < points.length; k++) {
                    Point point2 = points[k];
                    tempPoints[2] = point2;

                    if (point2 == null) {
                        throw new IllegalArgumentException("Point is null.");
                    } else if (point2.compareTo(point0) == 0 || point2.compareTo(point1) == 0) {
                        throw new IllegalArgumentException("Point is identical.");
                    }

                    double slope2 = point0.slopeTo(point2);

                    for (int q = k + 1; q < points.length; q++) {
                        Point point3 = points[q];
                        tempPoints[3] = point3;

                        if (point3 == null) {
                            throw new IllegalArgumentException("Point is null.");
                        } else if (point3.compareTo(point0) == 0 || point3.compareTo(point1) == 0 || point3.compareTo(point2) == 0) {
                            throw new IllegalArgumentException("Point is identical.");
                        }

                        double slope3 = point0.slopeTo(point3);

                        if (Double.compare(slope1, slope2) != 0 || Double.compare(slope1, slope3) != 0) {
                            continue;
                        }

                        Arrays.sort(tempPoints);

                        tempSegments.add(new LineSegment(tempPoints[0], tempPoints[3]));
                    }
                }
            }
        }

        this.lineSegments = tempSegments.toArray(new LineSegment[0]);
    }

}
