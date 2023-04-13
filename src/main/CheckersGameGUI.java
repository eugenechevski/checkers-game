package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// This class represents a checkers game. It contains a 2D array of characters to represent the board.
public class CheckersGameGUI extends JFrame {
    private JButton[][] boardButtons;
    private JLabel messageLabel;
    private CheckersBoard board;
    private String player;
    private int fromRow, fromCol;
    private Boolean isPlaying;

    public CheckersGameGUI() {
        isPlaying = true;
        boardButtons = new JButton[8][8];
        board = new CheckersBoard();
        player = "White";
        fromRow = fromCol = -1;

        // This code will close the window when the exit button is pressed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Checkers Game");

        // Create the board buttons and add them to the GUI
        JPanel boardPanel = new JPanel(new GridLayout(8, 8));
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boardButtons[row][col] = new JButton();
                boardButtons[row][col].setPreferredSize(new Dimension(60, 60));
                boardButtons[row][col].addActionListener(new ButtonListener(row, col));
                boardPanel.add(boardButtons[row][col]);
                updateButton(row, col);
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        // Create the message label and add it to the GUI
        messageLabel = new JLabel("Player " + player + "'s turn");
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        add(messageLabel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Update the text and color of a button based on the state of the corresponding
    // square on the board
    private void updateButton(int row, int col) {
        // If the row or column is invalid, do nothing
        if (row < 0 || row >= 8 || col < 0 || col >= 8) {
            return;
        }

        // sets a black border of a button if it is selected
        // otherwise, the border is dark gray
        if (row == fromRow && col == fromCol) {
            Color borderColor = board.getPiece(row, col) == "White" ? Color.BLACK : Color.WHITE;
            boardButtons[row][col].setBorder(BorderFactory.createLineBorder(borderColor, 3));
        } else {
            // remove the old border
            boardButtons[row][col].setBorder(null);
            boardButtons[row][col].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        }

        // sets the color of the button white if "White" and black if "Black"
        // otherwise, the button is gray
        if (board.getPiece(row, col) == "White") {
            boardButtons[row][col].setBackground(Color.WHITE);
        } else if (board.getPiece(row, col) == "Black") {
            boardButtons[row][col].setBackground(Color.BLACK);
        } else {
            boardButtons[row][col].setBackground(Color.LIGHT_GRAY);
        }
    }

    // Updates the selection of a button
    // Repaiting the previous selection and the new selection
    public void updateSelection(int row, int col) {
        // save the old row and column
        int oldRow = fromRow;
        int oldCol = fromCol;

        fromRow = row;
        fromCol = col;

        // update the buttons
        updateButton(oldRow, oldCol);
        updateButton(fromRow, fromCol);
    }
    
    // Removes the selection of a button
    public void removeSelection(int row, int col) {
      boardButtons[row][col].setBorder(null);
    }

    // This class represents a listener for the board buttons
    private class ButtonListener implements ActionListener {
        private int row;
        private int col;

        public ButtonListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void actionPerformed(ActionEvent e) {
            if (!isPlaying) {
                return;
            }

            // If the player changes its piece, change the selected piece
            if (board.getPiece(row, col) == player) {
                updateSelection(row, col);

                messageLabel.setText("Select the square to move to.");
                return;
            } else if (fromRow != -1) { // If the player selects a square to move to
                if (board.isLegalMove(fromRow, fromCol, row, col, player)) {
                    int[] captured = board.move(fromRow, fromCol, row, col);
                    
                    updateSelection(row, col);

                    if (board.isGameOver()) { // If the game is over, display a message
                        isPlaying = false;
                        messageLabel.setText("Game over! Player " + player + " wins!");
                    } else if (captured[0] == -1) { // If the player did not capture, change the player
                        removeSelection(row, col);
                        
                        fromRow = fromCol = -1;
                        player = player == "White" ? "Black" : "White";
                        
                        messageLabel.setText("Player " + player + "'s turn");
                    } else {
                        // If the player captured, update the board and display a message
                        updateButton(captured[0], captured[1]);
                        messageLabel.setText("Select the next square to capture to.");
                    }
                } else {
                    messageLabel.setText("Illegal move. Try again.");
                }
            }

        }
    }

    public static void main(String[] args) {
        new CheckersGameGUI();
    }
}
