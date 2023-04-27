package game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import report.Reporter;

// This class is used to create the toolbar
public class Toolbar extends JToolBar {
    private JDialog rulesDialog;
    private JDialog highScoreDialog;

    public Toolbar(Reporter reporter, JFrame frame) {
        super();

        JButton rulesButton = new JButton("Rules");
        JButton highScoreButton = new JButton("See High Score");
        JButton quitButton = new JButton("Quit");

        // adds the rules option
        rulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rulesDialog == null) {
                    rulesDialog = new JDialog();
                    rulesDialog.setTitle("Checkers Rules");
                    rulesDialog.setSize(700, 300);

                    JTextArea rulesTextArea = new JTextArea(getCheckersRules());

                    rulesTextArea.setEditable(false);

                    rulesDialog.add(rulesTextArea);
                    rulesDialog.setLocationRelativeTo(rulesButton);
                }
                rulesDialog.setVisible(true);
            }
        });

        // adds the highest score option
        highScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (highScoreDialog == null) {
                    int highScore = reporter.getHighestScore();
                    String highestPlayer = reporter.getHighestScorePlayer();

                    highScoreDialog = new JDialog();
                    highScoreDialog.setTitle("High Score");
                    highScoreDialog.setSize(300, 200);

                    JLabel highScoreLabel = new JLabel(
                            "The current high score is: " + highScore + " by " + highestPlayer);

                    highScoreDialog.add(highScoreLabel);
                    highScoreDialog.setLocationRelativeTo(highScoreButton);
                }
                highScoreDialog.setVisible(true);
            }
        });

        // adds the quit option
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // present a dialog for confirmation
                int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?", "Quit",
                        JOptionPane.YES_NO_OPTION);

                // if the user clicks yes, exit the program
                if (result == JOptionPane.YES_OPTION) {
                    frame.dispose();
                }
            }
        });

        add(rulesButton);
        add(highScoreButton);
        add(quitButton);
    }

    private String getCheckersRules() {
        return "Checkers Rules:\n\n" +
                "1. Each player starts with 12 pieces on the dark squares of the board.\n\n" +
                "2. Pieces move diagonally and can only move forward.\n\n" +
                "3. If a piece reaches the last row on the opponent's side, it becomes a 'king' and can move backwards.\n\n"
                +
                "4. Pieces capture by jumping over an opponent's piece.\n\n" +
                "5. A player wins by capturing all of the opponent's pieces, or blocking their opponent's pieces so they can't move.";
    }
}
