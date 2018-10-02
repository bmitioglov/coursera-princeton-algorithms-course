package union_find;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] fractions;
    private int trials;

    public PercolationStats(int n, int trials) {
        this.trials = trials;
        fractions = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                p.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
            }
            fractions[i] = (double) p.numberOfOpenSites() / (n * n);
        }
    }   // perform trials independent experiments on an n-by-n grid
    public double mean() {
        return StdStats.mean(fractions);
    }                          // sample mean of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractions);
    }                        // sample standard deviation of percolation threshold
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(trials));
    }                  // low  endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(trials));
    }                  // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        PercolationStats pstats = new PercolationStats(200, 10000);
        System.out.println("mean\t\t\t\t\t = " + pstats.mean());
        System.out.println("stddev\t\t\t\t\t = " + pstats.stddev());
        System.out.println("95% confidence interval\t = [" + pstats.confidenceLo() + ", " + pstats.confidenceHi() + "]");
    }        // test client (described below)
}
