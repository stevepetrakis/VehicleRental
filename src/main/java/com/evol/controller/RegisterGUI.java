package com.evol.controller;

import com.evol.util.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegisterGUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField; // Change to JPasswordField
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField addressField;
    private JTextField birthDateField;
    private JTextField licenseNumberField;
    private JTextField creditCardField;

    public RegisterGUI() {
        setTitle("Customer Registration");
        setSize(400, 400);
        setLocationRelativeTo(null);

        initializeComponents();
        setVisible(true);
    }

    private void initializeComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel lastNameLabel = new JLabel("Last Name:");
        JLabel addressLabel = new JLabel("Address:");
        JLabel birthDateLabel = new JLabel("Birth Date (YYYY-MM-DD):");
        JLabel licenseNumberLabel = new JLabel("Driving License Number:");
        JLabel creditCardLabel = new JLabel("Credit Card Details:");

        usernameField = new JTextField();
        passwordField = new JPasswordField(); // Use JPasswordField for password
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        addressField = new JTextField();
        birthDateField = new JTextField();
        licenseNumberField = new JTextField();
        creditCardField = new JTextField();

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword()); // Retrieve password from JPasswordField
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String address = addressField.getText();
                String birthDate = birthDateField.getText();
                String licenseNumber = licenseNumberField.getText();
                String creditCard = creditCardField.getText();

                if (registerCustomer(username, password, firstName, lastName, address, birthDate, licenseNumber, creditCard)) {
                    JOptionPane.showMessageDialog(RegisterGUI.this, "Customer registered successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(RegisterGUI.this, "Error registering customer", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(firstNameLabel);
        panel.add(firstNameField);
        panel.add(lastNameLabel);
        panel.add(lastNameField);
        panel.add(addressLabel);
        panel.add(addressField);
        panel.add(birthDateLabel);
        panel.add(birthDateField);
        panel.add(licenseNumberLabel);
        panel.add(licenseNumberField);
        panel.add(creditCardLabel);
        panel.add(creditCardField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(registerButton);

        add(panel);
    }


    private boolean registerCustomer(String username, String password, String firstName, String lastName,
                                            String address, String birthDate, String licenseNumber, String creditCard) {
        String userInsertQuery = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        String customerInsertQuery = "INSERT INTO customers (firstname, lastname, address, birth_date, driving_license_number, credit_card_details) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement userStatement = connection.prepareStatement(userInsertQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement customerStatement = connection.prepareStatement(customerInsertQuery)) {

            connection.setAutoCommit(false);

            // Insert user
            userStatement.setString(1, username);
            userStatement.setString(2, password);
            userStatement.setInt(3, 2); // Assuming role 2 for customers
            int userRowsAffected = userStatement.executeUpdate();

            if (userRowsAffected == 0) {
                connection.rollback();
                return false; // User registration failed
            }

            // Retrieve the generated user ID
            try (ResultSet generatedKeys = userStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);

                    // Insert customer with the generated user ID
                    customerStatement.setString(1, firstName);
                    customerStatement.setString(2, lastName);
                    customerStatement.setString(3, address);
                    customerStatement.setString(4, birthDate);
                    customerStatement.setString(5, licenseNumber);
                    customerStatement.setString(6, creditCard);
                    int customerRowsAffected = customerStatement.executeUpdate();

                    if (customerRowsAffected == 0) {
                        connection.rollback();
                        return false; // Customer registration failed
                    }

                    // Update the user with the linked customer ID
                    String updateUserQuery = "UPDATE users SET customer_id = ? WHERE id = ?";
                    try (PreparedStatement updateUserStatement = connection.prepareStatement(updateUserQuery)) {
                        updateUserStatement.setInt(1, userId);
                        updateUserStatement.setInt(2, userId);
                        int updateRowsAffected = updateUserStatement.executeUpdate();

                        if (updateRowsAffected == 0) {
                            connection.rollback();
                            return false; // User update failed
                        }
                    }

                    connection.commit();
                    return true; // Registration successful
                } else {
                    connection.rollback();
                    return false; // No user ID generated
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
