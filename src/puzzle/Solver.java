package puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private BoardWrapper solution;
    private int minNumberOfMoves = 0;
    private boolean isSolvable = true;

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Board is null");

        MinPQ<BoardWrapper> minPQ1 = new MinPQ<>(new ManhattanComparator());
        MinPQ<BoardWrapper> minPQ2 = new MinPQ<>(new ManhattanComparator());

        BoardWrapper current = new BoardWrapper(initial, null, 0,
                initial.manhattan());
        BoardWrapper currentTwin = new BoardWrapper(initial.twin(), null, 0,
                initial.twin().manhattan());
        minPQ1.insert(current);
        minPQ2.insert(currentTwin);


        while (!current.getCurrentBoard().isGoal() && !currentTwin.getCurrentBoard().isGoal()) {

            current = minPQ1.delMin();
            currentTwin = minPQ2.delMin();

            for (Board board: current.getCurrentBoard().neighbors()) {
                if (current.getPrev() == null || !board.equals(current.getPrev().currentBoard)) {
                    minPQ1.insert(new BoardWrapper(board, current,
                            numberOfMovesForTheBoard(current) + 1, board.manhattan()));
                }
            }
            for (Board board: currentTwin.getCurrentBoard().neighbors()) {
                if (currentTwin.getPrev() == null || !board.equals(currentTwin.getPrev().currentBoard)) {
                    minPQ2.insert(new BoardWrapper(board, currentTwin,
                            numberOfMovesForTheBoard(currentTwin) + 1, board.manhattan()));
                }
            }
        }

        if (current.getCurrentBoard().isGoal()) {
            solution = current;
            isSolvable = true;
        } else {
            solution = currentTwin;
            isSolvable = false;
        }
    }          // find a solution to the initial board (using the A* algorithm)

    private class BoardWrapper {
        Board currentBoard;
        BoardWrapper prev;
        int numOfMoves;
        int manhattan;

        BoardWrapper(Board board, BoardWrapper prev, int numberOfMoves, int manhattan) {
            currentBoard = board;
            this.prev = prev;
            numOfMoves = numberOfMoves;
            this.manhattan = manhattan;
        }

        Board getCurrentBoard() {
            return currentBoard;
        }

        void setCurrentBoard(Board currentBoard) {
            this.currentBoard = currentBoard;
        }

        BoardWrapper getPrev() {
            return prev;
        }

        void setPrev(BoardWrapper prev) {
            this.prev = prev;
        }

        int getNumOfMoves() {
            return numOfMoves;
        }

        void setNumOfMoves(int numOfMoves) {
            this.numOfMoves = numOfMoves;
        }
    }

    private int numberOfMovesForTheBoard(BoardWrapper board) {
        BoardWrapper currentBoard = board;
        int numberOfMoves = 0;
        while (currentBoard != null) {
            currentBoard = currentBoard.getPrev();
            numberOfMoves++;
        }
        return numberOfMoves;
    }

    private class ManhattanComparator implements Comparator<BoardWrapper> {
        @Override
        public int compare(BoardWrapper o1, BoardWrapper o2) {
            int o1Priority = o1.manhattan + o1.getNumOfMoves();
            int o2Priority = o2.manhattan + o2.getNumOfMoves();
            if (o1Priority < o2Priority) {
                return -1;
            } else if (o1Priority == o2Priority) {
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
        Stack<Board> solutionStack = new Stack<>();
        if (isSolvable()) {
            BoardWrapper currentSolution = solution;
            solutionStack.push(currentSolution.currentBoard);
            while (currentSolution.getPrev() != null) {
                solutionStack.push(currentSolution.getPrev().currentBoard);
                currentSolution = currentSolution.getPrev();
            }
            minNumberOfMoves = solutionStack.size();
            return solutionStack;
        } else {
            minNumberOfMoves = 0;
            return null;
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
