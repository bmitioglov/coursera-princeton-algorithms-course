package puzzle;

import java.util.Arrays;

public class Board {

    private int[][] board;
    private int[][] goalBoard = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    private int numberOfMoves = 0;

    public Board(int[][] blocks) {
        board = blocks;
    }           // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)

    public int dimension() {
        return board.length;
    }                 // board dimension n

    public int hamming() {
        int outOfPlaceCounter = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != getGlobalIndex(i+1, j+1)) outOfPlaceCounter++;
            }
        }
        return outOfPlaceCounter + numberOfMoves;
    }                   // number of blocks out of place


    public int manhattan() {
        int distanceCounter = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != getGlobalIndex(i+1, j+1)) distanceCounter+=getDistance(i, j, board[i][j]);
            }
        }
        return distanceCounter + numberOfMoves;
    }                // sum of Manhattan distances between blocks and goal
    public boolean isGoal() {
        return Arrays.equals(board, goalBoard);
    }                // is this board the goal board?

    public Board twin() {
        int[][] copyArray = Arrays.copyOf(board, board.length*board.length);
        int temp = copyArray[0][0];
        copyArray[0][0] = copyArray[0][1];
        copyArray[0][1] = temp;
        return new Board(copyArray);
    }                   // a board that is obtained by exchanging any pair of blocks
    public boolean equals(Object y) {
        return Arrays.equals(board, (int[][]) y);
    }        // does this board equal y?

    public Iterable<Board> neighbors() {
        int oneBasedRow = 0;
        int oneBasedCol = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    oneBasedRow = i;
                    oneBasedCol = j;
                    break;
                }
            }
        }
        if (oneBasedCol == 1) {
            Board copyBoard1 = new Board(board);
            if (oneBasedRow == 1) {

            } else if (oneBasedRow == 2) {

            } else if (oneBasedRow == 3) {

            }
        } else if (oneBasedCol == 2) {
            if (oneBasedRow == 1) {

            } else if (oneBasedRow == 2) {

            } else if (oneBasedRow == 3) {

            }
        } else if (oneBasedCol == 3) {
            if (oneBasedRow == 1) {

            } else if (oneBasedRow == 2) {

            } else if (oneBasedRow == 3) {

            }
        }
    }     // all neighboring boards

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] aBoard : board) {
            for (int j = 0; j < board.length; j++) {
                sb.append(aBoard[j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }               // string representation of this board (in the output format specified below)

    private int getGlobalIndex(int row, int col) {
        return (row - 1) * board.length + col;
    }

    //receives zero based row and column
    private int getDistance(int row, int col, int value) {
        int oneBasedRow = row + 1;
        int oneBasedCol = col + 1;
        int whole = value / board.length;
        int remainder = value % board.length;
        int targetRow = whole + (remainder > 0 ? 1 : 0);
        int targetColumn = remainder + (remainder == 0 ? 3: 0);
        return Math.abs(targetRow - oneBasedRow) + Math.abs(targetColumn - oneBasedCol);
    }


    public static void main(String[] args) {

    } // unit tests (not graded)
}
