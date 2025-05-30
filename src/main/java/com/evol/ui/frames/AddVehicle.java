package com.evol.ui.frames;

import com.evol.model.Vehicle;
import com.evol.util.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddVehicle extends JFrame {
    public AddVehicle() {
        setTitle("Add Vehicle");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel formPanel = new JPanel(new GridLayout(10, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Registration Number:"));
        JTextField registrationNumberField = new JTextField();
        formPanel.add(registrationNumberField);

        formPanel.add(new JLabel("Brand:"));
        JTextField brandField = new JTextField();
        formPanel.add(brandField);

        formPanel.add(new JLabel("Model:"));
        JTextField modelField = new JTextField();
        formPanel.add(modelField);

        formPanel.add(new JLabel("Color:"));
        JTextField colorField = new JTextField();
        formPanel.add(colorField);

        formPanel.add(new JLabel("Autonomy (km):"));
        JTextField autonomyField = new JTextField();
        formPanel.add(autonomyField);

        formPanel.add(new JLabel("Type:"));
        JTextField typeField = new JTextField();
        formPanel.add(typeField);

        formPanel.add(new JLabel("Passengers:"));
        JTextField passengersField = new JTextField();
        formPanel.add(passengersField);

        formPanel.add(new JLabel("Rental Cost:"));
        JTextField rentalCostField = new JTextField();
        formPanel.add(rentalCostField);

        formPanel.add(new JLabel("Insurance Cost:"));
        JTextField insuranceCostField = new JTextField();
        formPanel.add(insuranceCostField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Connection connection = DatabaseConnection.getConnection()) {
                    String sql = "INSERT INTO vehicles (registration_number, brand, model, color, autonomy_km, vehicle_type, " +
                            "number_of_passengers, daily_rental_cost, daily_insurance_cost, available) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, registrationNumberField.getText());
                        statement.setString(2, brandField.getText());
                        statement.setString(3, modelField.getText());
                        statement.setString(4, colorField.getText());
                        statement.setInt(5, Integer.parseInt(autonomyField.getText()));
                        statement.setString(6, typeField.getText());
                        statement.setInt(7, Integer.parseInt(passengersField.getText()));
                        statement.setDouble(8, Double.parseDouble(rentalCostField.getText()));
                        statement.setDouble(9, Double.parseDouble(insuranceCostField.getText()));
                        statement.setBoolean(10, true); // Assuming the vehicle is available by default

                        statement.executeUpdate();
                    }

                    JOptionPane.showMessageDialog(null, "Vehicle added successfully!");
                    dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error adding vehicle to the database");
                }
            }
        });

        formPanel.add(new JLabel()); // Empty label for spacing
        formPanel.add(addButton);

        add(formPanel);
        setVisible(true);
    }
}
