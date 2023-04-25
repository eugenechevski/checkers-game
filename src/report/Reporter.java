package report;

import javax.swing.JLabel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class Reporter {
    private JLabel messageLabel;
    private File scoresFile;
    private File highestScoreFile;
    private Map<String, Integer> playerScores;
    private int highestScore;
    private String highestScorePlayer;

    public Reporter(JLabel messageLabel) {
        this.messageLabel = messageLabel;

        // Initialize the scores file
        scoresFile = new File("scores.txt");

        // Initialize the highest score file
        highestScoreFile = new File("highestScore.txt");

        // Initialize the player scores map
        playerScores = new HashMap<>();

        // Read the scores from the file if it exists
        if (scoresFile.exists()) {
            try (
                FileInputStream inputStream = new FileInputStream(scoresFile);
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);
            ) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] tokens = line.split(" ");
                    String playerName = tokens[0];
                    int playerScore = Integer.parseInt(tokens[1]);
                    playerScores.put(playerName, playerScore);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Read the highest score from the file if it exists
        if (highestScoreFile.exists()) {
            try (
                FileInputStream inputStream = new FileInputStream(highestScoreFile);
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);
            ) {
                String line = bufferedReader.readLine();
                if (line != null) {
                    String[] tokens = line.split(" ");
                    highestScore = Integer.parseInt(tokens[1]);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            highestScore = 0;
        }
    }

    public void report(String message) {
        messageLabel.setText(message);
    }

    public void reportScore(String playerName) {
        // Get the player's score
        // Increment the player's score
        int playerScore = playerScores.getOrDefault(playerName, 0) + 1;
    
        playerScores.put(playerName, playerScore);
    
        // Write the new score to the log file
        try (FileWriter writer = new FileWriter(scoresFile)) {
            for (Map.Entry<String, Integer> entry : playerScores.entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Check if the score is higher than the current highest score
        if (playerScore > highestScore) {
            // Update the highest score and player name
            highestScore = playerScore;
            highestScorePlayer = playerName;
    
            // Write the new highest score to the log file
            try (FileWriter writer = new FileWriter(highestScoreFile)) {
                writer.write(highestScorePlayer + " " + highestScore);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, Integer> getPlayerScores() {
        return playerScores;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public String getHighestScorePlayer() {
        return highestScorePlayer;
    }
}
