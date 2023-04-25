package game;

import java.awt.Color;

public class Board {
    private Cell[][] board;
    private int fromRow, fromCol;

    public Board() {
        // Initialize the selected cell coordinates
        fromRow = fromCol = -1;

        // Initialize the board
        board = new Cell[8][8];

        // Initialize the board with the starting positions of the pieces
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 0 && row < 3) {
                    board[row][col] = new Cell(new Player("Black", Color.BLACK), Color.BLACK, row, col);
                } else if ((row + col) % 2 == 0 && row > 4) {
                    board[row][col] = new Cell(new Player("White", Color.WHITE), Color.WHITE, row, col);
                } else {
                    board[row][col] = new Cell(null, Color.LIGHT_GRAY, row, col);
                }
            }
        }
    }

    public Cell getSelectedCell() {
        if (fromRow == -1 || fromCol == -1) {
            return null;
        }

        return board[fromRow][fromCol];
    }

    public void selectCell(int row, int col) {
        // Deselect the previously selected cell
        if (fromRow != -1 && fromCol != -1) {
            board[fromRow][fromCol].deselect();
        }

        // Select the new cell
        board[row][col].select();
        fromRow = row;
        fromCol = col;
    }

    public void removeSelection() {
        if (fromRow != -1 && fromCol != -1) {
            board[fromRow][fromCol].deselect();
        }

        fromRow = fromCol = -1;
    }

    public Cell getCell(int row, int col) {
        return board[row][col];
    }

    public boolean isLegalMove(int toRow, int toCol) {
        Cell from = board[fromRow][fromCol];
        Cell to = board[toRow][toCol];

        Player player = from.getPlayer();

        if (player == null) {
            return false;
        }

        // Check that the piece being moved belongs to the current player
        if (from.hasPlayer() && from.getPlayer().getName() != player.getName()) {
            return false;
        }

        // Check that the destination square is empty
        if (to.hasPlayer()) {
            return false;
        }

        // Check that the piece is not jumping over another piece
        if (Math.abs(fromRow - toRow) == 2 && Math.abs(fromCol - toCol) == 2) {
            int jumpedRow = (fromRow + toRow) / 2;
            int jumpedCol = (fromCol + toCol) / 2;
            Cell jumped = board[jumpedRow][jumpedCol];

            if (!jumped.hasPlayer()) {
                return false;
            }

            return true;
        }

        // Check that the piece is moving diagonally
        if (Math.abs(fromRow - toRow) != 1 || Math.abs(fromCol - toCol) != 1) {
            return false;
        }

        // Check that the piece is moving forward if it's not a king
        if (!player.isKing() && (player.getName() == "White" && fromRow <= toRow
                || player.getName() == "Black" && fromRow >= toRow)) {
            return false;
        }

        return true;
    }

    public Cell move(int toRow, int toCol) {
        Cell captured = null;

        // Get the cells
        Cell from = board[fromRow][fromCol];
        Cell to = board[toRow][toCol];
        Player player = from.getPlayer();

        // Move the piece
        to.setPlayer(player);
        from.setPlayer(null);
        from.deselect();

        // Determine if a piece was captured
        if (Math.abs(fromRow - toRow) == 2 && Math.abs(fromCol - toCol) == 2) {
            int jumpedRow = (fromRow + toRow) / 2;
            int jumpedCol = (fromCol + toCol) / 2;

            captured = board[jumpedRow][jumpedCol];
            captured.setPlayer(null);
        }

        // If the piece reaches the last row of the opponent's side, promote it to a
        // king
        if (toRow == 0 && player.getName() == "White" ||
                toRow == 7 && player.getName() == "Black") {
            player.setKing();
        }

        return captured;
    }

    // Determine if the player can capture any pieces around
    public boolean canCapture(Player player, int row, int col) {
        // Array of possible capture moves
        int[][] captureMoves = {
                { -1, -1 }, // top left
                { -1, 1 }, // top right
                { 1, -1 }, // bottom left
                { 1, 1 } // bottom right
        };

        // Iterate through all possible capture moves
        for (int[] move : captureMoves) {
            int newRow = row + move[0];
            int newCol = col + move[1];
            int captureRow = row + 2 * move[0];
            int captureCol = col + 2 * move[1];

            // Check if the move is within the bounds of the board
            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8 &&
                    captureRow >= 0 && captureRow < 8 && captureCol >= 0 && captureCol < 8) {
                // Check if the piece at the move location is an opponent's piece and the
                // capture location is empty
                if (board[newRow][newCol].hasPlayer() && board[newRow][newCol].getPlayer().getName() != player.getName()
                        &&
                        !board[captureRow][captureCol].hasPlayer()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isGameOver() {
        // Check if there are any pieces left for either player
        boolean white = false;
        boolean black = false;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col].hasPlayer()) {
                    if (board[row][col].getPlayer().getName() == "White") {
                        white = true;
                    } else {
                        black = true;
                    }
                }
            }
        }

        return !(white && black);
    }
}
