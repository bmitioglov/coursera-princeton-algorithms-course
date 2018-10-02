package union_find;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int n;
    private int[] openclosedarray;
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private int numberOfOpenSites;

    public Percolation(int n) {
        this.n = n;
        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);

        openclosedarray = new int[n * n + 2];

        for (int i = 0; i < n * n + 2; i++) {
            openclosedarray[i] = 0;
        }

        // open start and end vertexes
        openclosedarray[0] = 1;
        openclosedarray[n * n + 1] = 1;

    }// create n-by-n grid, with all sites blocked

    public void open(int row, int col) {
        if (wrongIndex(row) || wrongIndex(col)) {
            throw new IllegalArgumentException("Wrong col, row index");
        }
        if (openclosedarray[getIndex(row, col)] != 1) {
            openclosedarray[getIndex(row, col)] = 1;
            unionLeftNeighbor(row, col);
            unionRightNeighbor(row, col);
            unionUpNeighbor(row, col);
            unionDownNeighbor(row, col);
            numberOfOpenSites++;
        }
    }

    private void unionLeftNeighbor(int row, int col) {
        if (col != 1 && isOpen(row, col - 1))
            weightedQuickUnionUF.union(getIndex(row, col), getIndex(row, col - 1));
    }
    private void unionRightNeighbor(int row, int col) {
        if (col != n && isOpen(row, col + 1))
            weightedQuickUnionUF.union(getIndex(row, col), getIndex(row, col + 1));
    }
    private void unionUpNeighbor(int row, int col) {
        if (row != 1 && isOpen(row - 1, col))
            weightedQuickUnionUF.union(getIndex(row, col), getIndex(row - 1, col));
        if (row == 1)
            //connecting with upper extra vertex
            weightedQuickUnionUF.union(getIndex(row, col), 0);
    }
    private void unionDownNeighbor(int row, int col) {
        if (row != n && isOpen(row + 1, col))
            weightedQuickUnionUF.union(getIndex(row, col), getIndex(row + 1, col));
        if (row == n)
            //connecting with bottom extra vertex
            weightedQuickUnionUF.union(getIndex(row, col), n * n + 1);
    }

    private boolean wrongIndex(int value) {
        return value < 1 || value > n;
    }

    private int getIndex(int row, int col) {
        return (row - 1) * n + col;
    }

    public boolean isOpen(int row, int col) {
        return openclosedarray[getIndex(row, col)] == 1;

    }  // is site (row, col) open?
    public boolean isFull(int row, int col) {
        return weightedQuickUnionUF.connected(getIndex(row, col), 0);
    } // is site (row, col) full?

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }      // number of open sites

    public boolean percolates() {
        // checking connection between upper and bottom extra vertexes
        return weightedQuickUnionUF.connected(0, n * n + 1);
    }              // does the system percolate?

    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        p.open(1, 2);
        System.out.println(p.percolates());
        p.open(2, 2);
        System.out.println(p.percolates());
        p.open(3, 3);
        System.out.println(p.percolates());
        System.out.println("isFull? = " + p.isFull(3, 3));
        p.open(2, 3);
        System.out.println(p.percolates());

    }   // test client (optional)
}
