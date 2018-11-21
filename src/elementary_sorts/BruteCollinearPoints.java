package elementary_sorts;

import java.util.ArrayList;

public class BruteCollinearPoints {

    private ArrayList<LineSegment> segments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Empty list of points");
        for (int i = 0; i < points.length-3; i++) {
            for (int j = i+1; j < points.length-2; j++) {
                for (int k = j+1; k < points.length-1; k++) {
                    for (int z = k+1; z < points.length; z++) {
                        double slope1 = points[i].slopeTo(points[j]);
                        double slope2 = points[i].slopeTo(points[k]);
                        double slope3 = points[i].slopeTo(points[z]);
                        if (slope1 == Double.NEGATIVE_INFINITY
                                || slope2 == Double.NEGATIVE_INFINITY
                                || slope3 == Double.NEGATIVE_INFINITY) {
                            throw new IllegalArgumentException("Duplicated points");
                        }
                        if (slope1 == slope2 && slope2 == slope3) {
                            LineSegment lineSegment = new LineSegment(points[i], points[z]);
                            segments.add(lineSegment);
                        }
                    }
                }
            }
        }
    }   // finds all line segments containing 4 points



    public int numberOfSegments() {
        return segments.size();
    }       // the number of line segments



    public LineSegment[] segments() {
        LineSegment[] segmentsArray = new LineSegment[segments.size()];
        return segments.toArray(segmentsArray);
    }               // the line segments
}