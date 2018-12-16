 package puzzle;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private int[][] board;


    public Board(int[][] blocks) {
        board = copyArray(blocks);
    }           // construct a board from an n-by-n array of blocks

    private int[][] copyArray(int[][] old) {
        int[][] copy = new int[old.length][old.length];
        for(int i=0; i<old.length; i++)
            for(int j=0; j<old.length; j++)
                copy[i][j] = old[i][j];
        return copy;
    }

    public int dimension() {
        return board.length;
    }                 // board dimension n

    public int hamming() {
        int outOfPlaceCounter = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != getGlobalIndex(i+1, j+1) && board[i][j] != 0) outOfPlaceCounter++;
            }
        }
        return outOfPlaceCounter;
    }                   // number of blocks out of place


    public int manhattan() {
        int distanceCounter = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != getGlobalIndex(i+1, j+1) && board[i][j] != 0)
                    distanceCounter+=getDistance(i, j, board[i][j], board.length);
            }
        }
        return distanceCounter;
    }                // sum of Manhattan distances between blocks and goal
    public boolean isGoal() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != getGlobalIndex(i+1, j+1) && board[i][j] != 0) {
                    return false;
                }
            }
        }
        return board[board.length-1][board.length-1] == 0;
    }                // is this board the goal board?


    private int[] spaceLocation() {
        for (int row = 0; row < board.length; row++)
            for (int col = 0; col < board.length; col++)
                if (isSpace(board[row][col])) {
                    int[] location = new int[2];
                    location[0] = row;
                    location[1] = col;

                    return location;
                }
        throw new RuntimeException();
    }

    private boolean isSpace(int block) {
        int SPACE = 0;
        return block == SPACE;
    }


    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();

        int[] location = spaceLocation();
        int spaceRow = location[0];
        int spaceCol = location[1];

        if (spaceRow > 0)               neighbors.add(new Board(swap(spaceRow, spaceCol, spaceRow - 1, spaceCol)));
        if (spaceRow < dimension() - 1) neighbors.add(new Board(swap(spaceRow, spaceCol, spaceRow + 1, spaceCol)));
        if (spaceCol > 0)               neighbors.add(new Board(swap(spaceRow, spaceCol, spaceRow, spaceCol - 1)));
        if (spaceCol < dimension() - 1) neighbors.add(new Board(swap(spaceRow, spaceCol, spaceRow, spaceCol + 1)));

        return neighbors;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dimension() + "\n");
        for (int[] aBoard : board) {
            for (int j = 0; j < board.length; j++) {
                sb.append(String.format("%2d ", aBoard[j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }               // string representation of this board (in the output format specified below)

    private int getGlobalIndex(int row, int col) {
        return (row - 1) * board.length + col;
    }

    //receives zero based row and column
    private int getDistance(int row, int col, int value, int boardLength) {
        int oneBasedRow = row + 1;
        int oneBasedCol = col + 1;
        int whole = value / board.length;
        int remainder = value % board.length;
        int targetRow = whole + (remainder > 0 ? 1 : 0);
        int targetColumn = remainder + (remainder == 0 ? boardLength: 0);
        return Math.abs(targetRow - oneBasedRow) + Math.abs(targetColumn - oneBasedCol);
    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Board board1 = (Board) o;
//
//        if (!Arrays.deepEquals(board, board1.board)) return false;
//        return predecessor != null ? predecessor.equals(board1.predecessor) : board1.predecessor == null;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board1 = (Board) o;

        return Arrays.deepEquals(board, board1.board);
    }

    public Board twin() {
        for (int row = 0; row < board.length; row++)
            for (int col = 0; col < board.length - 1; col++)
                if (!isSpace(board[row][col]) && !isSpace(board[row][col + 1]))
                    return new Board(swap(row, col, row, col + 1));
        throw new RuntimeException();
    }

    private int[][] swap(int row1, int col1, int row2, int col2) {
        int[][] copy = copyArray(board);
        int tmp = copy[row1][col1];
        copy[row1][col1] = copy[row2][col2];
        copy[row2][col2] = tmp;

        return copy;
    }


    public static void main(String[] args) {
        int[][] testBoard2 = {{1, 5, 2}, {7, 0, 4}, {8, 6, 3}};
        Board board = new Board(testBoard2);
//        System.out.println(board.manhattan());
    } // unit tests (not graded)
}
