package login;

import java.util.HashMap;

public class Login {
    private HashMap<String, String> users;

    public Login() {
        users = new HashMap<>();
    }

    public void addUser(String username, String password) {
        users.put(username, password);
    }

    public boolean checkLogin(String username, String password) {
        String storedPassword = users.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }
}

