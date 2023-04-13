package main;
public class CheckersBoard {
    private String[][] board;

    public CheckersBoard() {
        board = new String[8][8];
        // Initialize the board with the starting positions of the pieces
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 0 && row < 3) {
                    board[row][col] = "Black";
                } else if ((row + col) % 2 == 0 && row > 4) {
                    board[row][col] = "White";
                } else {
                    board[row][col] = " ";
                }
            }
        }
    }

    public String getPiece(int row, int col) {
        return board[row][col];
    }

    public boolean isLegalMove(int fromRow, int fromCol, int toRow, int toCol, String player) {
        // Check that the piece being moved belongs to the current player
        if (board[fromRow][fromCol] != player) {
            return false;
        }

        // Check that the destination square is empty
        if (board[toRow][toCol] != " ") {
            return false;
        }

        // Check that the piece is not jumping over another piece
        if (Math.abs(fromRow - toRow) == 2 && Math.abs(fromCol - toCol) == 2) {
            int jumpedRow = (fromRow + toRow) / 2;
            int jumpedCol = (fromCol + toCol) / 2;

            if (board[jumpedRow][jumpedCol] == " ") {
                return false;
            }

            return true;
        }

        // Check that the piece is moving diagonally
        if (Math.abs(fromRow - toRow) != 1 || Math.abs(fromCol - toCol) != 1) {
            return false;
        }

        // Check that the piece is moving forward if it's not a king
        if (player == "White" && fromRow <= toRow || player == "Black" && fromRow >= toRow) {
            return false;
        }

        // Check that the move is legal for kings
        if (board[fromRow][fromCol] == "King") {
            if (Math.abs(fromRow - toRow) != 1 || Math.abs(fromCol - toCol) != 1) {
                return false;
            }
        }
        return true;
    }

    public int[] move(int fromRow, int fromCol, int toRow, int toCol) {
        int[] captured = new int[]{-1, -1};

        // Move the piece
        board[toRow][toCol] = board[fromRow][fromCol];
        board[fromRow][fromCol] = " ";

        // Determine if a piece was captured
        if (Math.abs(fromRow - toRow) == 2 && Math.abs(fromCol - toCol) == 2) {
            int jumpedRow = (fromRow + toRow) / 2;
            int jumpedCol = (fromCol + toCol) / 2;
            board[jumpedRow][jumpedCol] = " ";
            captured = new int[]{jumpedRow, jumpedCol};
        }

        // If the piece reaches the last row of the opponent's side, promote it to a king
        if (toRow == 0 && board[toRow][toCol] == "Black") {
            board[toRow][toCol] = "King";
        }
        if (toRow == 7 && board[toRow][toCol] == "White") {
            board[toRow][toCol] = "King";
        }

        return captured;
    }

    public boolean isGameOver() {
        // Check if there are any pieces left for either player
        boolean xFound = false;
        boolean oFound = false;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == "White") {
                    xFound = true;
                }
                if (board[row][col] == "Black") {
                    oFound = true;
                }
            }
        }
        return !xFound || !oFound;
    }
}

