package login;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

// This class is used to check if a user is valid or not
// It also stores the users in a file
public class Login {
    private HashMap<String, String> users;

    public Login() {
        users = new HashMap<>();

        try {
            // Check if the file exists
            File file = new File("users.txt");

            if (file.exists() && file.isFile()) {
                Scanner scanner = new Scanner(file);

                // Read the users from the file
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(":");
                    users.put(parts[0], parts[1]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addUser(String username, String password) {
        users.put(username, password);

        // Write the users to the file
        try {
            FileWriter writer = new FileWriter("users.txt");
            for (String key : users.keySet()) {
                writer.write(key + ":" + users.get(key) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkLogin(String username, String password) {
        String storedPassword = users.get(username);
    
        return storedPassword != null && storedPassword.equals(password);
    }
}
