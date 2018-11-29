package queues;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class PriorityQueueTaxicab implements Comparable<PriorityQueueTaxicab> {
    private final int i;
    private final int j;
    private final long sum;   // i^3 + j^3, cached to avoid recomputation

    // create a new tuple (i, j, i^3 + j^3)
    public PriorityQueueTaxicab(int i, int j) {
        this.sum = (long) i*i*i + (long) j*j*j;
        this.i = i;
        this.j = j;
    }

    // compare by i^3 + j^3, breaking ties by i
    public int compareTo(PriorityQueueTaxicab that) {
        if      (this.sum < that.sum) return -1;
        else if (this.sum > that.sum) return +1;
        else if (this.i < that.i)     return -1;
        else if (this.i > that.i)     return +1;
        else                          return  0;
    }

    public String toString() {
        return i + "^3 + " + j + "^3";
    }


    public static void main(String[] args) {

        // find a^3 + b^3 = c^3 + d^3, where a, b, c, d <= n
        int n = Integer.parseInt(args[0]);

        // initialize priority queue
        MinPQ<PriorityQueueTaxicab> pq = new MinPQ<PriorityQueueTaxicab>();
        for (int i = 1; i <= n; i++) {
            pq.insert(new PriorityQueueTaxicab(i, i));
        }

        // enumerate sums in ascending order, look for repeated sums
        int run = 1;
        PriorityQueueTaxicab prev = new PriorityQueueTaxicab(0, 0);   // sentinel
        while (!pq.isEmpty()) {
            PriorityQueueTaxicab curr = pq.delMin();

            // current sum is same as previous sum
            if (prev.sum == curr.sum) {
                run++;
                if (run == 2) StdOut.print(prev.sum + " = " + prev);
                StdOut.print(" = " + curr);
            }
            else {
                if (run > 1) StdOut.println();
                run = 1;
            }
            prev = curr;

            if (curr.j < n) pq.insert(new PriorityQueueTaxicab(curr.i, curr.j + 1));
        }
        if (run > 1) StdOut.println();
    }

}