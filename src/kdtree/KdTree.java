package kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
            size++;
        } else {
            insertInKdTree(tree, p);
        }
    }            // add the point to the set (if it is not already in the set)

    private void insertInKdTree(Node tree, Point2D point) {
        if (tree.point.equals(point)) return;
        if (tree.alignment == Alignment.Vertical) {
            if (point.x() < tree.point.x()) {
                if (tree.left == null) {
                    tree.left = new Node(point, Alignment.Horizontal);
                    size++;
                 } else {
                    insertInKdTree(tree.left, point);
                }
            } else {
                if (tree.right == null) {
                    tree.right = new Node(point, Alignment.Horizontal);
                    size++;
                } else {
                    insertInKdTree(tree.right, point);
                }
            }
        } else {
            if (point.y() < tree.point.y()) {
                if (tree.left == null) {
                    tree.left = new Node(point, Alignment.Vertical);
                    size++;
                } else {
                    insertInKdTree(tree.left, point);
                }
            } else {
                if (tree.right == null) {
                    tree.right = new Node(point, Alignment.Vertical);
                    size++;
                } else {
                    insertInKdTree(tree.right, point);
                }
            }
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point is null");
        if (size == 0) return false;
        return find(tree, p);
    }         // does the set contain point p?

    private boolean find(Node tree, Point2D point) {
//        System.out.println("Checking point = " + tree.point);
        if (tree.point.equals(point)) return true;
        if (tree.alignment == Alignment.Vertical) {
            if (point.x() < tree.point.x()) {
                if (tree.left == null) {
                    return false;
                } else {
                    return find(tree.left, point);
                }
            } else {
                if (tree.right == null) {
                    return false;
                } else {
                    return find(tree.right, point);
                }
            }
        } else {
            if (point.y() < tree.point.y()) {
                if (tree.left == null) {
                    return false;
                } else {
                    return find(tree.left, point);
                }
            } else {
                if (tree.right == null) {
                    return false;
                } else {
                    return find(tree.right, point);
                }
            }
        }
    }


    /**
     * Draw functions have been taken from https://gist.github.com/taiwotman/9810619
     */
    public void draw()
    {
        RectHV drawTable = new  RectHV(0, 0, 1, 1);
        StdDraw.setScale(0, 1);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        drawTable.draw();

        draw(tree, drawTable);
    }

    private void draw(final Node tree, final RectHV rect)
    {
        if (tree == null) return;

        // draw the point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        new Point2D(tree.point.x(), tree.point.y()).draw();

        // min and max points of division line
        Point2D min, max;
        if (tree.alignment == Alignment.Vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            min = new Point2D(tree.point.x(), rect.ymin());
            max = new Point2D(tree.point.x(), rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            min = new Point2D(rect.xmin(), tree.point.y());
            max = new Point2D(rect.xmax(), tree.point.y());
        }

        // draw that division line
        StdDraw.setPenRadius();
        min.drawTo(max);

        // recursively draw children
        draw(tree.left, leftRect(rect, tree));
        draw(tree.right, rightRect(rect, tree));
    }
    private RectHV leftRect(final RectHV rect, final Node tree)
    {
        if (tree.alignment == Alignment.Vertical)
            return new RectHV(rect.xmin(), rect.ymin(), tree.point.x(), rect.ymax());
        else
            return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), tree.point.y());
    }
    private RectHV rightRect(final RectHV rect, final Node tree)
    {
        if (tree.alignment == Alignment.Vertical)
            return new RectHV(tree.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
        else
            return new RectHV(rect.xmin(), tree.point.y(), rect.xmax(), rect.ymax());
    }




    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Rectangle is null");
        List<Point2D> pointsInRange = new ArrayList<>();
        return findInRange(tree, rect, pointsInRange);
    }            // all points that are inside the rectangle (or on the boundary)

    private List<Point2D> findInRange(Node tree, RectHV rect, List<Point2D> pointsInRange) {
//        System.out.println("Checking point for rectangle = " + tree.point);
        if (rect.contains(tree.point)) pointsInRange.add(tree.point);
        if (tree.alignment == Alignment.Vertical) {
            if (rect.xmin() <= tree.point.x() && rect.xmax() >= tree.point.x()) {
                if (tree.left != null) findInRange(tree.left, rect, pointsInRange);
                if (tree.right != null) findInRange(tree.right, rect, pointsInRange);
                return pointsInRange;
            } else if (rect.xmax() < tree.point.x()) {
                if (tree.left == null) {
                    return Collections.emptyList();
                } else {
//                    System.out.println("Checking point = " + tree.point);
                    findInRange(tree.left, rect, pointsInRange);
                    return pointsInRange;
                }
            } else if (rect.xmin() > tree.point.x()) {
                if (tree.right == null) {
                    return Collections.emptyList();
                } else {
                    findInRange(tree.right, rect, pointsInRange);
                    return pointsInRange;
                }
            } else {
//                System.out.println("wrong...");
                return Collections.emptyList();
            }
        } else {
            if (rect.ymin() <= tree.point.y() && rect.ymax() >= tree.point.y()) {
                if (tree.left != null) findInRange(tree.left, rect, pointsInRange);
                if (tree.right != null) findInRange(tree.right, rect, pointsInRange);
                return pointsInRange;
            } else if (rect.ymax() < tree.point.y()) {
                if (tree.left == null) {
                    return Collections.emptyList();
                } else {
                    findInRange(tree.left, rect, pointsInRange);
                    return pointsInRange;
                }
            } else if (rect.ymin() > tree.point.y()) {
                if (tree.right == null) {
                    return Collections.emptyList();
                } else {
                    findInRange(tree.right, rect, pointsInRange);
                    return pointsInRange;
                }
            } else {
//                System.out.println("wrong...");
                return Collections.emptyList();
            }
        }
    }



    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point is null");

        return findNearest(tree, p, tree.point);
    }            // a nearest neighbor in the set to point p; null if the set is empty

    private Point2D findNearest(Node tree, Point2D point, Point2D currentChampion) {
        if (tree == null) return currentChampion;

//        System.out.println("Checking point = " + tree.point + ", current champion = " + currentChampion);

        if (point.distanceTo(tree.point) < point.distanceTo(currentChampion)) {
            currentChampion = tree.point;
        }

        if (tree.alignment == Alignment.Vertical) {
            if (point.x() < tree.point.x()) {
                Point2D championInLeftBranch = findNearest(tree.left, point, currentChampion);
                if (point.distanceTo(championInLeftBranch) < point.distanceTo(currentChampion)) {
                    currentChampion = championInLeftBranch;

                    if (point.distanceTo(championInLeftBranch) < point.distanceTo(new Point2D(tree.point.x(), point.y()))) {
                        return currentChampion;
                    } else {
                        return findNearest(tree.right, point, currentChampion);
                    }
                } else {
                    return findNearest(tree.right, point, currentChampion);
                }
            } else {
                Point2D championInRightBranch = findNearest(tree.right, point, currentChampion);
                if (point.distanceTo(championInRightBranch) < point.distanceTo(currentChampion)) {
                    currentChampion = championInRightBranch;

                    if (point.distanceTo(championInRightBranch) < point.distanceTo(new Point2D(tree.point.x(), point.y()))) {
                        return currentChampion;
                    } else {
                        return findNearest(tree.left, point, currentChampion);
                    }
                } else {
                    return findNearest(tree.left, point, currentChampion);
                }
            }
        } else {
            if (point.y() < tree.point.y()) {
                Point2D championInLeftBranch = findNearest(tree.left, point, currentChampion);
                if (point.distanceTo(championInLeftBranch) < point.distanceTo(currentChampion)) {
                    currentChampion = championInLeftBranch;

                    if (point.distanceTo(championInLeftBranch) < point.distanceTo(new Point2D(tree.point.y(), point.x()))) {
                        return currentChampion;
                    } else {
                        return findNearest(tree.right, point, currentChampion);
                    }
                } else {
                    return findNearest(tree.right, point, currentChampion);
                }
            } else {
                Point2D championInRightBranch = findNearest(tree.right, point, currentChampion);
                if (point.distanceTo(championInRightBranch) < point.distanceTo(currentChampion)) {
                    currentChampion = championInRightBranch;

                    if (point.distanceTo(championInRightBranch) < point.distanceTo(new Point2D(tree.point.y(), point.x()))) {
                        return currentChampion;
                    } else {
                        return findNearest(tree.left, point, currentChampion);
                    }
                } else {
                    return findNearest(tree.left, point, currentChampion);
                }
            }
        }
    }

    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(1, 1));
        kdTree.insert(new Point2D(1, 2));
        kdTree.insert(new Point2D(2, 2));
        kdTree.insert(new Point2D(0, 1));
        kdTree.insert(new Point2D(0, 0));
        kdTree.insert(new Point2D(-1, 0));
        kdTree.insert(new Point2D(0, 2));
//        System.out.println(kdTree.contains(new Point2D(2, 2)));
//        System.out.println(kdTree.range(new RectHV(1, 0, 2, 2)));
        System.out.println(kdTree.nearest(new Point2D(0.7, 2)));
        System.out.println(kdTree.nearest(new Point2D(-1, -1)));


    }                 // unit testing of the methods (optional)
}