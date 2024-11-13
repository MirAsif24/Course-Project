/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cse215project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInterface extends JFrame {
    private UserManager userManager;

    public UserInterface() {
        userManager = new UserManager();
        setTitle("Inventory Management System");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new GridLayout(3, 1)); // 3 rows, 1 column

        // Main panel with three buttons
        JPanel mainPanel = new JPanel();
        JButton signupButton = new JButton("Signup");        
        JButton loginButton = new JButton("Login");       
        JButton exitButton = new JButton("Exit");
        signupButton.setBounds(200, 100, 50, 60);
        loginButton.setBounds(200, 120, 50, 60);
        exitButton.setBounds(200, 150, 50, 60);
        mainPanel.add(signupButton);
        mainPanel.add(loginButton);
        mainPanel.add(exitButton);
        add(mainPanel);

        // Action for Signup button
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSignupFrame();
            }
        });

        // Action for Login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoginFrame();
            }
        });

        // Action for Exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });
    }

    // Method to open the Signup frame
    private void openSignupFrame() {
        JFrame signupFrame = new JFrame("Signup");
        signupFrame.setSize(400, 250);
        signupFrame.setLocationRelativeTo(null);
        signupFrame.setLayout(new GridLayout(4, 2));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField userTypeField = new JTextField();
        JButton signupSubmitButton = new JButton("Submit");

        signupFrame.add(new JLabel("Username:"));
        signupFrame.add(usernameField);
        signupFrame.add(new JLabel("Password:"));
        signupFrame.add(passwordField);
        signupFrame.add(new JLabel("User Type (farmer/customer/retailer):"));
        signupFrame.add(userTypeField);
        signupFrame.add(new JLabel("")); // empty cell
        signupFrame.add(signupSubmitButton);

        signupSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String userType = userTypeField.getText().toLowerCase();

                if (userManager.signup(username, password, userType)) {
                    JOptionPane.showMessageDialog(signupFrame, "Signup successful! User ID assigned.");
                    userManager.saveUsersToFile("users.txt");
                    signupFrame.dispose(); // Close signup frame after success
                } else {
                    JOptionPane.showMessageDialog(signupFrame, "Signup failed. Please try again.");
                }
            }
        });

        signupFrame.setVisible(true);
    }

    // Method to open the Login frame
    private void openLoginFrame() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(400, 250);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setLayout(new GridLayout(4, 2));

        JTextField usernameField = new JTextField();
        JTextField userIdField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginSubmitButton = new JButton("Submit");

        loginFrame.add(new JLabel("Username:"));
        loginFrame.add(usernameField);
        loginFrame.add(new JLabel("User ID:"));
        loginFrame.add(userIdField);
        loginFrame.add(new JLabel("Password:"));
        loginFrame.add(passwordField);
        loginFrame.add(new JLabel("")); // empty cell
        loginFrame.add(loginSubmitButton);

        loginSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                int userId;
                try {
                    userId = Integer.parseInt(userIdField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid User ID. Please enter a number.");
                    return;
                }
                String password = new String(passwordField.getPassword());

                userManager.loadUsersFromFile("users.txt");
                if (userManager.login(username, userId, password)) {
                    JOptionPane.showMessageDialog(loginFrame, "Login successful, " + username + "!");
                    loginFrame.dispose(); // Close login frame after success
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Login failed. Please try again.");
                }
            }
        });

        loginFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UserInterface ui = new UserInterface();
                ui.setVisible(true);
            }
        });
    }
}


