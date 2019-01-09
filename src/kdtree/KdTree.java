package kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {

    private enum Alignment {
        Horizontal,
        Vertical
    }

    private Node tree;
    private int size;

    private class Node {
        private Alignment alignment;
        private Point2D point;
        private Node left;
        private Node right;

        private Node(Point2D point, Alignment alignment) {
            this.point = point;
            this.alignment = alignment;
        }
    }

    public KdTree() {

    }                              // construct an empty set of points
    public           boolean isEmpty()  {
        return size == 0;
    }                    // is the set empty?
    public               int size()  {
        return size;
    }                       // number of points in the set

    public void insert(Point2D p)  {
        if (size == 0) {
            tree = new Node(p, Alignment.Vertical);
        } else {
            insertInKdTree(tree, p);
        }
    }            // add the point to the set (if it is not already in the set)

    private void insertInKdTree(Node tree, Point2D point) {
        if (tree.alignment == Alignment.Vertical) {
            if (point.x() < tree.point.x()) {
                if (tree.left == null) {
                    tree.left = new Node(point, Alignment.Horizontal);
                    size++;
                 } else {
                    insertInKdTree(tree.left, point);
                }
            } else {
                insertInKdTree(tree.right, point);
            }
        } else {
            if (point.y() < tree.point.y()) {
                if (tree.right == null) {
                    tree.right = new Node(point, Alignment.Vertical);
                    size++;
                } else {
                    insertInKdTree(tree.left, point);
                }
            } else {
                insertInKdTree(tree.right, point);
            }
        }
    }

    public           boolean contains(Point2D p) {

    }         // does the set contain point p?
    public              void draw() {

    }                        // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect) {

    }            // all points that are inside the rectangle (or on the boundary)
    public           Point2D nearest(Point2D p) {

    }            // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {

    }                 // unit testing of the methods (optional)
}