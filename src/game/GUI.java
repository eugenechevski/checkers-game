package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// This class represents a checkers game. It contains a 2D array of characters to represent the board.
public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton[][] boardButtons;
    private JLabel messageLabel;
    private Board board;
    private Player[] players;
    private Player currentPlayer;
    private Boolean isPlaying;

    public GUI() {
        isPlaying = true;
        boardButtons = new JButton[8][8];
        board = new Board();
        players = new Player[]{new Player("White", Color.WHITE), new Player("Black", Color.BLACK)};    
        currentPlayer = players[0];

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
        messageLabel = new JLabel("Player " + currentPlayer.getName() + "'s turn");
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        add(messageLabel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void switchPlayers() {
        if (currentPlayer == players[0]) {
            currentPlayer = players[1];
        } else {
            currentPlayer = players[0];
        }
    }

    // Update the text and color of a button based on the state of the corresponding
    // square on the board
    private void updateButton(int row, int col) {
        // If the row or column is invalid, do nothing
        if (row < 0 || row >= 8 || col < 0 || col >= 8) {
            return;
        }

        Cell cell = board.getCell(row, col);
        JButton button = boardButtons[row][col];

        button.setBackground(cell.getColor());
        if (cell.isSelected()) {
            button.setBorder(BorderFactory.createLineBorder(cell.getSelectedColor(), 3));
        } else if (cell.hasPlayer() && cell.getPlayer().isKing()) {
            button.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
        } else {
            button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
        }
    }

    // Updates the selection of a button
    // Repaiting the previous selection and the new selection
    // Also the selection variables are updated
    public void updateSelection(int row, int col) {
        Cell currentCell = board.getSelectedCell();
        
        board.removeSelection();
        
        if (currentCell != null) {
            updateButton(currentCell.getRow(), currentCell.getCol());
        }
        
        board.selectCell(row, col);
        updateButton(row, col);
    }

    // This class represents a listener for the board buttons
    private class ButtonListener implements ActionListener {
        private int row;
        private int col;

        public ButtonListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void movedLegally() {
            Cell captured = board.move(row, col);
            
            updateSelection(row, col);

            // If the game is over, display a message
            // stop the game
            if (board.isGameOver()) { // If the game is over, display a message
                isPlaying = false;
                messageLabel.setText("Game over! Player " + currentPlayer.getName() + " wins!");
                return;
            } 
            
            if (captured != null) {
                updateButton(captured.getRow(), captured.getCol());
            }

            // If the player did not capture, change the player
            // or if the player captured but cannot capture again, change the player
            if (captured == null || !board.canCapture(currentPlayer, row, col)) {
                board.removeSelection();
                updateButton(row, col);
                switchPlayers();

                messageLabel.setText("Player " + currentPlayer.getName() + "'s turn");
            } else { // If the player captured and can capture again, do not change the player
                messageLabel.setText("Player " + currentPlayer.getName() + " captured! Select the piece to move again.");
            }
        }

        public void actionPerformed(ActionEvent e) {
            if (!isPlaying) {
                return;
            }

            Cell clickedCell = board.getCell(row, col);

            // If the player changes its piece, change the selected piece
            if (clickedCell.hasPlayer() && clickedCell.getPlayer().getName() == currentPlayer.getName()) {
                updateSelection(row, col);

                messageLabel.setText("Select the square to move to.");
                return;
            } else if (board.getSelectedCell() != null && board.isLegalMove(row, col)) {
                movedLegally();
            } else {
                messageLabel.setText("Invalid move! Choose a different cell.");
            } 
        }
    }

    public static void main(String[] args) {
        new GUI();
    }
}
