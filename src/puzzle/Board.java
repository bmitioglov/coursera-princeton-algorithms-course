package puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board {

    private int[][] board;
    private int[][] goalBoard = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    private int numberOfMoves = 0;

    public Board(int[][] blocks) {
        board = copyArray(blocks);
    }           // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)

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
        return outOfPlaceCounter + numberOfMoves;
    }                   // number of blocks out of place


    public int manhattan() {
        int distanceCounter = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != getGlobalIndex(i+1, j+1) && board[i][j] != 0)
                    distanceCounter+=getDistance(i, j, board[i][j]);
            }
        }
        return distanceCounter + numberOfMoves;
    }                // sum of Manhattan distances between blocks and goal
    public boolean isGoal() {
        return Arrays.deepEquals(board, goalBoard);
    }                // is this board the goal board?

    public Board twin() {
        int[][] copyArray = copyArray(board);

        int oneBasedRow = 0;
        int oneBasedCol = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    oneBasedRow = i+1;
                    oneBasedCol = j+1;
                    break;
                }
            }
        }

        int candidate1Row = 0;
        int candidate1Column = 0;
        while (candidate1Row + 1 == oneBasedRow && candidate1Column + 1 == oneBasedCol) {
            candidate1Row = new Random().nextInt(3);
            candidate1Column = new Random().nextInt(3);
        }

        int candidate2Row = 0;
        int candidate2Column = 0;
        while (candidate2Row + 1 == oneBasedRow && candidate2Column + 1 == oneBasedCol) {
            candidate2Row = new Random().nextInt(3);
            candidate2Column = new Random().nextInt(3);
        }


        int temp = copyArray[candidate1Row][candidate1Column];
        copyArray[candidate1Row][candidate1Column] = copyArray[candidate2Row][candidate2Column];
        copyArray[candidate2Row][candidate2Column] = temp;
        return new Board(copyArray);
    }                   // a board that is obtained by exchanging any pair of blocks


    public Iterable<Board> neighbors() {
        int oneBasedRow = 0;
        int oneBasedCol = 0;
        List<Board> neighbors = new ArrayList<Board>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    oneBasedRow = i+1;
                    oneBasedCol = j+1;
                    break;
                }
            }
        }
        if (oneBasedCol == 1) {
            if (oneBasedRow == 1) {
                Board copyBoard1 = new Board(board);
                swap(copyBoard1.board, 1, 1, 1, 2);
                neighbors.add(copyBoard1);
                Board copyBoard2 = new Board(board);
                swap(copyBoard2.board, 1, 1 , 2, 1);
                neighbors.add(copyBoard2);
            } else if (oneBasedRow == 2) {
                Board copyBoard1 = new Board(board);
                swap(copyBoard1.board, 2, 1, 1, 1);
                neighbors.add(copyBoard1);
                Board copyBoard2 = new Board(board);
                swap(copyBoard2.board, 2, 1 , 2, 2);
                neighbors.add(copyBoard2);
                Board copyBoard3 = new Board(board);
                swap(copyBoard3.board, 2, 1 , 3, 1);
                neighbors.add(copyBoard3);
            } else if (oneBasedRow == 3) {
                Board copyBoard1 = new Board(board);
                swap(copyBoard1.board, 3, 1, 2, 1);
                neighbors.add(copyBoard1);
                Board copyBoard2 = new Board(board);
                swap(copyBoard2.board, 3, 1 , 3, 2);
                neighbors.add(copyBoard2);
            }
        } else if (oneBasedCol == 2) {
            if (oneBasedRow == 1) {
                Board copyBoard1 = new Board(board);
                swap(copyBoard1.board, 1, 2, 1, 1);
                neighbors.add(copyBoard1);
                Board copyBoard2 = new Board(board);
                swap(copyBoard2.board, 1, 2, 2, 2);
                neighbors.add(copyBoard2);
                Board copyBoard3 = new Board(board);
                swap(copyBoard3.board, 1, 2, 1, 3);
                neighbors.add(copyBoard3);
            } else if (oneBasedRow == 2) {
                Board copyBoard1 = new Board(board);
                swap(copyBoard1.board, 2, 2, 2, 1);
                neighbors.add(copyBoard1);
                Board copyBoard2 = new Board(board);
                swap(copyBoard2.board, 2, 2, 1, 2);
                neighbors.add(copyBoard2);
                Board copyBoard3 = new Board(board);
                swap(copyBoard3.board, 2, 2, 3, 2);
                neighbors.add(copyBoard3);
                Board copyBoard4 = new Board(board);
                swap(copyBoard4.board, 2, 2, 2, 3);
                neighbors.add(copyBoard4);
            } else if (oneBasedRow == 3) {
                Board copyBoard1 = new Board(board);
                swap(copyBoard1.board, 3, 2, 3, 1);
                neighbors.add(copyBoard1);
                Board copyBoard2 = new Board(board);
                swap(copyBoard2.board, 3, 2, 2, 2);
                neighbors.add(copyBoard2);
                Board copyBoard3 = new Board(board);
                swap(copyBoard3.board, 3, 2, 3, 3);
                neighbors.add(copyBoard3);
            }
        } else if (oneBasedCol == 3) {
            if (oneBasedRow == 1) {
                Board copyBoard1 = new Board(board);
                swap(copyBoard1.board, 1, 3, 1, 2);
                neighbors.add(copyBoard1);
                Board copyBoard2 = new Board(board);
                swap(copyBoard2.board, 1, 3, 2, 3);
                neighbors.add(copyBoard2);
            } else if (oneBasedRow == 2) {
                Board copyBoard1 = new Board(board);
                swap(copyBoard1.board, 2, 3, 1, 3);
                neighbors.add(copyBoard1);
                Board copyBoard2 = new Board(board);
                swap(copyBoard2.board, 2, 3, 2, 2);
                neighbors.add(copyBoard2);
                Board copyBoard3 = new Board(board);
                swap(copyBoard3.board, 2, 3, 3, 3);
                neighbors.add(copyBoard3);
            } else if (oneBasedRow == 3) {
                Board copyBoard1 = new Board(board);
                swap(copyBoard1.board, 3, 3, 2, 3);
                neighbors.add(copyBoard1);
                Board copyBoard2 = new Board(board);
                swap(copyBoard2.board, 3, 3, 3, 2);
                neighbors.add(copyBoard2);
            }
        }
        return neighbors;
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

    //receives 1-based indexes
    private void swap(int[][] boardArg, int row1, int col1, int row2, int col2) {
        int temp = boardArg[row1-1][col1-1];
        boardArg[row1-1][col1-1] = boardArg[row2-1][col2-1];
        boardArg[row2-1][col2-1] = temp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board1 = (Board) o;

        return Arrays.deepEquals(board, board1.board);
    }

//    @Override
//    public int hashCode() {
//        return Arrays.deepHashCode(board);
//    }

    public static void main(String[] args) {
//        int[][] testBoard = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        int[][] testBoard2 = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        Board board = new Board(testBoard2);
        System.out.println(board.manhattan());
    } // unit tests (not graded)
}
