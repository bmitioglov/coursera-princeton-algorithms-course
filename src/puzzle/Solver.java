package puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solver {
    private Board solution;
    private int minNumberOfMoves = 0;
    private boolean isSolvable = true;

    public Solver(Board initial) {
        MinPQ<Board> minPQ1 = new MinPQ<>(new ManhattanComparator());
        MinPQ<Board> minPQ2 = new MinPQ<>(new ManhattanComparator());

        Board current = initial;
        Board currentTwin = initial.twin();
        current.setPrev(null);
        currentTwin.setPrev(null);
        minPQ1.insert(current);
        minPQ2.insert(currentTwin);


        while (!current.isGoal() && !currentTwin.isGoal()) {

            current = minPQ1.delMin();
            currentTwin = minPQ2.delMin();

            for (Board board: current.neighbors()) {
                if (!board.equals(current.getPrev())) {
                    board.setPrev(current);
                    board.setNumberOfMoves(numberOfMovesForTheBoard(board));
                    minPQ1.insert(board);
                }
            }
            for (Board board: currentTwin.neighbors()) {
                if (!board.equals(currentTwin.getPrev())) {
                    board.setPrev(current);
                    board.setNumberOfMoves(numberOfMovesForTheBoard(board));
                    minPQ2.insert(board);
                }
            }
        }

        if (current.isGoal()) {
            solution = current;
            isSolvable = true;
        } else {
            solution = currentTwin;
            isSolvable = false;
        }
    }          // find a solution to the initial board (using the A* algorithm)

    private int numberOfMovesForTheBoard(Board board) {
        Board currentBoard = board;
        int numberOfMoves = 0;
        while (currentBoard != null) {
            currentBoard = currentBoard.getPrev();
            numberOfMoves++;
        }
        return numberOfMoves;
    }

    private class HammingComparator implements Comparator<Board> {
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
    }

    private class ManhattanComparator implements Comparator<Board> {
        @Override
        public int compare(Board o1, Board o2) {
            if (o1.manhattan() < o2.manhattan()) {
                return -1;
            } else if (o1.manhattan() == o2.manhattan()) {
                return 0;
            } else {
                return 1;
            }
        }
    }


    public boolean isSolvable() {
        return isSolvable;
    }           // is the initial board solvable?


    public int moves() {
        solution();
        return minNumberOfMoves - 1;
    }                     // min number of moves to solve initial board; -1 if unsolvable

    public Iterable<Board> solution() {
        List<Board> solutionList = new ArrayList<>();
        if (isSolvable()) {
            Board currentSolution = solution;
            solutionList.add(currentSolution);
            while (currentSolution.getPrev() != null) {
                solutionList.add(currentSolution.getPrev());
                currentSolution = currentSolution.getPrev();
            }
            minNumberOfMoves = solutionList.size();
            return solutionList;
        } else {
            minNumberOfMoves = 0;
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
                StdOut.println(board);
                i++;
            }
        }
    }
}
