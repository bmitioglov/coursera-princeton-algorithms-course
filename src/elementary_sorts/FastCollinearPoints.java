package elementary_sorts;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private ArrayList<LineSegment> segments = new ArrayList<>();


    public FastCollinearPoints(Point[] points) {
        Point[] pointsCopy = points.clone();
        for (Point point: points) {
            Arrays.sort(pointsCopy, point.slopeOrder());
            Point start = pointsCopy[0];
            Point currentEndPoint = pointsCopy[1];
            int currentLength = 0;
            boolean newSegment = true;
            double previousSlope = 0; //default value just to avoid java warning
            for (int i = 1; i < pointsCopy.length; i++) {
                double slope = start.slopeTo(points[i]);
                if (newSegment) {
                    currentEndPoint = points[i];
                    currentLength++;
                    newSegment = false;
                    previousSlope = slope;
                } else {
                    if (previousSlope == slope) {
                        currentLength++;
                        previousSlope = slope;
                        currentEndPoint = points[i];
                    }
                    if (previousSlope != slope && currentLength >= 3) {
                        segments.add(new LineSegment(start, currentEndPoint));
                        currentLength = 1;
                        newSegment = true;
                    }
                }
            }
        }
    }     // finds all line segments containing 4 or more points
    public           int numberOfSegments() {
        return segments.size();
    }        // the number of line segments
    public LineSegment[] segments() {
        LineSegment[] segmentsArray = new LineSegment[segments.size()];
        return segments.toArray(segmentsArray);
    }                // the line segments
}
