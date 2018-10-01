package union_find;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private Integer n;
    private Integer[] openclosedarray;
    private WeightedQuickUnionUF weightedQuickUnionUF;

    public Percolation(int n) {
        this.n = n;
        weightedQuickUnionUF = new WeightedQuickUnionUF(n*n);

        openclosedarray = new Integer[n*n];

        for (int i=0; i < n * n; i++) {
            openclosedarray[i] = 0;
        }
    }// create n-by-n grid, with all sites blocked

    public void open(int row, int col) {
        Integer arrRow = row - 1;
        Integer arrCol = col - 1;
        if (!checkIndex(row) || !checkIndex(col)) {
            throw new IllegalArgumentException("Wrong col, row index");
        }
        if (openclosedarray[(row-1)*n - col] != 1) {
            openclosedarray[(row-1)*n - col] = 1;
            //TODO: Union with the neighbors
        }
    }   // open site (row, col) if it is not open already

    private boolean checkIndex(int value) {
        return value >= 1 && value <= n;
    }

    public boolean isOpen(int row, int col) {

    }  // is site (row, col) open?
    public boolean isFull(int row, int col) {

    } // is site (row, col) full?
    public     int numberOfOpenSites() {

    }      // number of open sites
    public boolean percolates() {

    }              // does the system percolate?

    public static void main(String[] args) {

    }   // test client (optional)
}
