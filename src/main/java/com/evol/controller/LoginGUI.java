package com.evol.controller;

import com.evol.util.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginGUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    Controller controller;

    public LoginGUI(Controller controller) {
        this.controller = controller;
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();
        setVisible(true);
    }

    private void initializeComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Replace these with your actual database authentication logic
                String enteredUsername = usernameField.getText();
                String enteredPassword = new String(passwordField.getPassword());

                if (authenticateUser(enteredUsername, enteredPassword)) {
                    // Open the new GUI when authentication is successful
                    openNewGUI();
                } else {
                    // Display an error message for incorrect credentials
                    JOptionPane.showMessageDialog(LoginGUI.this, "Incorrect username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the RegisterGUI when the "Register" button is clicked
                openRegisterGUI();
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);
    }

    private boolean authenticateUser(String username, String password) {
//        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
//        try (Connection connection = DatabaseConnection.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//
//            preparedStatement.setString(1, username);
//            preparedStatement.setString(2, password);
//
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                return resultSet.next(); // If there is a matching record, authentication is successful
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
        boolean test = this.controller.loginUser(username,password);
        System.out.println(test);
        return test;
    }

    private void openNewGUI() {
        // Open the new GUI here
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdminGUI(LoginGUI.this.controller).setVisible(true);
                // You may also dispose of the current login GUI if needed
                dispose();
            }
        });
    }

    private void openRegisterGUI() {
        // Open the RegisterGUI here
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RegisterGUI().setVisible(true);
            }
        });
    }
}
