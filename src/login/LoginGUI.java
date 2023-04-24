package login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import game.GUI;
import login.Login;

public class LoginGUI extends JFrame {
    // Declare instance variables
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JPanel loginPanel;
    private JPanel registerPanel;
    private JLabel registrationMessageLabel;
    private JLabel registrationUsernameLabel;
    private JTextField registrationUsernameField;
    private JLabel registrationPasswordLabel;
    private JPasswordField registrationPasswordField;
    private JButton registrationButton;
    private Login login;

    public LoginGUI() {
        // Initialize instance variables
        login = new Login();
        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        // Add an action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (login.checkLogin(username, password)) {
                    // If the login is successful, close the login window and show the game window
                    JOptionPane.showMessageDialog(LoginGUI.this, "Login successful!");
                    dispose();
                    new GUI();
                } else {
                    JOptionPane.showMessageDialog(LoginGUI.this, "Login failed. Please try again.");
                }
            }
        });

        // Add an action listener to the register button
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRegisterPanel();
            }
        });

        // Create the login panel
        loginPanel = new JPanel(new GridLayout(3, 2));
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(registerButton);

        // Initialize the components of the registration panel
        registrationMessageLabel = new JLabel("Please enter your registration details:");
        registrationUsernameLabel = new JLabel("Username:");
        registrationUsernameField = new JTextField(20);
        registrationPasswordLabel = new JLabel("Password:");
        registrationPasswordField = new JPasswordField(20);
        registrationButton = new JButton("Register");

        // Add an action listener to the registration button
        registrationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = registrationUsernameField.getText();
                String password = new String(registrationPasswordField.getPassword());
                login.addUser(username, password);
                JOptionPane.showMessageDialog(LoginGUI.this, "Registration successful!");
                showLoginPanel();
            }
        });

        // Create the registration panel
        registerPanel = new JPanel(new GridLayout(4, 2));
        registerPanel.add(registrationMessageLabel);
        registerPanel.add(new JLabel());
        registerPanel.add(registrationUsernameLabel);
        registerPanel.add(registrationUsernameField);
        registerPanel.add(registrationPasswordLabel);
        registerPanel.add(registrationPasswordField);
        registerPanel.add(new JLabel());
        registerPanel.add(registrationButton);

        // Add the login panel to the frame's content pane
        add(loginPanel, BorderLayout.CENTER);

        // Set the frame's properties
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Show the login panel
    private void showLoginPanel() {
        getContentPane().removeAll();
        add(loginPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // Show the registration panel
    private void showRegisterPanel() {
        getContentPane().removeAll();
        add(registerPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        new LoginGUI();
    }
}