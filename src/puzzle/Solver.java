package puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

public class Solver {
    List<Board> solution;
    int minNumberOfModes = 0;
    boolean isSolvable = true;

    public Solver(Board initial) {
        MinPQ<Board> minPQ1 = new MinPQ<>(new Comparator<Board>() {
            @Override
            public int compare(Board o1, Board o2) {
                if (o1.hamming() < o2.hamming()) {
                    return -1;
                } else if (o1.hamming() == o2.hamming()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });

        MinPQ<Board> minPQ2 = new MinPQ<>(new Comparator<Board>() {
            @Override
            public int compare(Board o1, Board o2) {
                if (o1.hamming() < o2.hamming()) {
                    return -1;
                } else if (o1.hamming() == o2.hamming()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });


        List<Board> solution1 = new ArrayList<Board>();
        List<Board> solution2 = new ArrayList<Board>();
        List<Board> prevBoards1 = new ArrayList<>();
        List<Board> prevBoards2 = new ArrayList<>();

        Board current = initial;
        Board currentTwin = initial.twin();
        solution1.add(current);
        solution2.add(currentTwin);

        while (!current.isGoal() && !currentTwin.isGoal()) {
            for (Board board: current.neighbors()) {
                if (!prevBoards1.contains(board)) {
                    minPQ1.insert(board);
                    prevBoards1.add(board);
                }
            }
            for (Board board: currentTwin.neighbors()) {
                if (!prevBoards2.contains(board)) {
                    minPQ2.insert(board);
                    prevBoards2.add(board);
                }
            }
            current = minPQ1.delMin();
            currentTwin = minPQ2.delMin();
            solution1.add(current);
            solution2.add(currentTwin);
            minNumberOfModes++;
        }

        if (current.isGoal()) {
            solution = solution1;
            isSolvable = true;
        } else {
            solution = solution2;
            isSolvable = false;
        }
    }          // find a solution to the initial board (using the A* algorithm)

    public boolean isSolvable() {
        return isSolvable;
    }           // is the initial board solvable?


    public int moves() {
        return minNumberOfModes;
    }                     // min number of moves to solve initial board; -1 if unsolvable

    public Iterable<Board> solution() {
        if (isSolvable()) {
            return solution;
        } else {
            return Collections.emptyList();
        }
    }      // sequence of boards in a shortest solution; null if unsolvable

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            int i = 0;
            for (Board board : solver.solution()) {
                System.out.println("step = " + i); //TODO: Remove before test
                StdOut.println(board);
                i++;
            }
        }
    }
}
