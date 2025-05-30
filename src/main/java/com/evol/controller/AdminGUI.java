package com.evol.controller;

import com.evol.model.Customer;
import com.evol.model.Rental;
import com.evol.model.Vehicle;
import com.evol.ui.frames.AddVehicle;
import com.evol.ui.frames.ListVehicles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

public class AdminGUI extends JFrame {

    private DefaultTableModel tableModel;
    private JTable table;

    Controller controller;

    public AdminGUI(Controller controller) {
        this.controller = controller;
        setTitle("Rental Car Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 3));

        JButton listCustomerButton = new JButton("List Customers");
        JButton listRentalButton = new JButton("List Rentals");
        JButton listVehicleButton = new JButton("List Vehicles");
        JButton addCustomerButton = new JButton("Add Customer");
        JButton addRentalButton = new JButton("Add Rental");
        JButton addVehicleButton = new JButton("Add Vehicle");

        // Add action listeners to the buttons
        listCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to handle listing customers
                listCustomers();
            }
        });

        listRentalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to handle listing rentals
                listRentals();
            }
        });

        listVehicleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to handle listing vehicles
                new ListVehicles(AdminGUI.this.controller);
            }
        });

        addCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to handle adding a customer
                addCustomer();
            }
        });

        addRentalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to handle adding a rental
                addRental();
            }
        });

        addVehicleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to handle adding a vehicle
                new AddVehicle();
            }
        });

        // Add buttons to the panel
        mainPanel.add(listCustomerButton);
        mainPanel.add(listRentalButton);
        mainPanel.add(listVehicleButton);
        mainPanel.add(addCustomerButton);
        mainPanel.add(addRentalButton);
        mainPanel.add(addVehicleButton);

        // Add the panel to the frame
        add(mainPanel);

        setVisible(true);
    }

    private void listCustomers() {
        // Add logic to list customers using Customer model
        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(new Customer("John Doe", "123 Main St", new Date(), "DL123456", "1234-5678-9012-3456"));

        // Create a new JFrame for listing customers
        JFrame listFrame = new JFrame("List Customers");
        listFrame.setSize(400, 300);
        listFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create table model and populate data
        String[] columnNames = {"Name", "Address", "Birth Date", "License Number", "Credit Card"};
        Object[][] data = new Object[customers.size()][columnNames.length];

        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            data[i][0] = customer.getName();
            data[i][1] = customer.getAddress();
            data[i][2] = customer.getBirthDate();
            data[i][3] = customer.getDrivingLicenseNumber();
            data[i][4] = customer.getCreditCardDetails();
        }

        tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        listFrame.add(scrollPane);
        listFrame.setVisible(true);
    }

    private void listRentals() {
        // Add logic to list rentals using Rental model
        ArrayList<Rental> rentals = new ArrayList<>();
        rentals.add(new Rental("John Doe", new Date(), 7, 250.0, "ABC123"));

        // Create a new JFrame for listing rentals
        JFrame listFrame = new JFrame("List Rentals");
        listFrame.setSize(400, 300);
        listFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create table model and populate data
        String[] columnNames = {"Customer Name", "Rental Date", "Duration", "Payment Amount", "Vehicle Registration"};
        Object[][] data = new Object[rentals.size()][columnNames.length];

        for (int i = 0; i < rentals.size(); i++) {
            Rental rental = rentals.get(i);
            data[i][0] = rental.getCustomerName();
            data[i][1] = rental.getRentalDate();
            data[i][2] = rental.getDurationDays();
            data[i][3] = rental.getPaymentAmount();
            data[i][4] = rental.getVehicleRegistrationNumber();
        }

        tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        listFrame.add(scrollPane);
        listFrame.setVisible(true);
    }

    private void addCustomer() {
        // Create a new JFrame for adding a customer
        JFrame addFrame = new JFrame("Add Customer");
        addFrame.setSize(300, 200);
        addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        formPanel.add(nameField);
        formPanel.add(new JLabel("Address:"));
        JTextField addressField = new JTextField();
        formPanel.add(addressField);
        formPanel.add(new JLabel("Birth Date:"));
        JTextField birthDateField = new JTextField();
        formPanel.add(birthDateField);
        formPanel.add(new JLabel("License Number:"));
        JTextField licenseNumberField = new JTextField();
        formPanel.add(licenseNumberField);
        formPanel.add(new JLabel("Credit Card:"));
        JTextField creditCardField = new JTextField();
        formPanel.add(creditCardField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to handle adding a customer
                String name = nameField.getText();
                String address = addressField.getText();
                // Similarly, collect other details from the input fields
                // Create a new Customer object with the collected details
                Customer newCustomer = new Customer(name, address, new Date(), "DL123456", "1234-5678-9012-3456");
                // Perform actions to add the new customer to the system
                // You may want to save it to a database or update an in-memory list
                JOptionPane.showMessageDialog(null, "Customer added successfully!");
                addFrame.dispose();
            }
        });

        formPanel.add(addButton);

        addFrame.add(formPanel);
        addFrame.setVisible(true);
    }

    private void addRental() {
        // Create a new JFrame for adding a rental
        JFrame addFrame = new JFrame("Add Rental");
        addFrame.setSize(300, 200);
        addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.add(new JLabel("Customer Name:"));
        JTextField customerNameField = new JTextField();
        formPanel.add(customerNameField);
        formPanel.add(new JLabel("Rental Date:"));
        JTextField rentalDateField = new JTextField();
        formPanel.add(rentalDateField);
        formPanel.add(new JLabel("Duration (days):"));
        JTextField durationField = new JTextField();
        formPanel.add(durationField);
        formPanel.add(new JLabel("Payment Amount:"));
        JTextField paymentAmountField = new JTextField();
        formPanel.add(paymentAmountField);
        formPanel.add(new JLabel("Vehicle Registration:"));
        JTextField vehicleRegistrationField = new JTextField();
        formPanel.add(vehicleRegistrationField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to handle adding a rental
                String customerName = customerNameField.getText();
                // Similarly, collect other details from the input fields
                // Create a new Rental object with the collected details
                Rental newRental = new Rental(customerName, new Date(), 7, 250.0, "ABC123");
                // Perform actions to add the new rental to the system
                // You may want to save it to a database or update an in-memory list
                JOptionPane.showMessageDialog(null, "Rental added successfully!");
                addFrame.dispose();
            }
        });

        formPanel.add(addButton);

        addFrame.add(formPanel);
        addFrame.setVisible(true);
    }

//    private void addVehicle() {
//        // Create a new JFrame for adding a vehicle
//        JFrame addFrame = new JFrame("Add Vehicle");
//        addFrame.setSize(300, 200);
//        addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//        JPanel formPanel = new JPanel(new GridLayout(9, 2));
//        formPanel.add(new JLabel("Registration Number:"));
//        JTextField registrationNumberField = new JTextField();
//        formPanel.add(registrationNumberField);
//        formPanel.add(new JLabel("Brand:"));
//        JTextField brandField = new JTextField();
//        formPanel.add(brandField);
//        formPanel.add(new JLabel("Model:"));
//        JTextField modelField = new JTextField();
//        formPanel.add(modelField);
//        formPanel.add(new JLabel("Color:"));
//        JTextField colorField = new JTextField();
//        formPanel.add(colorField);
//        formPanel.add(new JLabel("Autonomy (km):"));
//        JTextField autonomyField = new JTextField();
//        formPanel.add(autonomyField);
//        formPanel.add(new JLabel("Type:"));
//        JTextField typeField = new JTextField();
//        formPanel.add(typeField);
//        formPanel.add(new JLabel("Passengers:"));
//        JTextField passengersField = new JTextField();
//        formPanel.add(passengersField);
//        formPanel.add(new JLabel("Rental Cost:"));
//        JTextField rentalCostField = new JTextField();
//        formPanel.add(rentalCostField);
//        formPanel.add(new JLabel("Insurance Cost:"));
//        JTextField insuranceCostField = new JTextField();
//        formPanel.add(insuranceCostField);
//
//        JButton addButton = new JButton("Add");
//        addButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Add logic to handle adding a vehicle
//                String registrationNumber = registrationNumberField.getText();
//                // Similarly, collect other details from the input fields
//                // Create a new Vehicle object with the collected details
//                Vehicle newVehicle = new Vehicle(registrationNumber, "Toyota", "Camry", "Blue", 500, "Sedan", 5, 40.0, 10.0, true);
//                // Perform actions to add the new vehicle to the system
//                // You may want to save it to a database or update an in-memory list
//                JOptionPane.showMessageDialog(null, "Vehicle added successfully!");
//                addFrame.dispose();
//            }
//        });
//
//        formPanel.add(addButton);
//
//        addFrame.add(formPanel);
//        addFrame.setVisible(true);
//    }

}
