package com.evol.ui.frames;


import com.evol.controller.Controller;
import com.evol.model.Vehicle;
import com.evol.util.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class ListVehicles extends JFrame {

    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField searchField;
    private JComboBox<String> categoryComboBox;
    private TableRowSorter<DefaultTableModel> sorter;

    Controller controller;
    public ListVehicles(Controller controller) {
        this.controller = controller;
        setTitle("List Vehicles");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create and set layout
        setLayout(new BorderLayout());

        // Create search panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        JLabel searchLabel = new JLabel("Search:");
        searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Create category filter panel
        JPanel categoryPanel = new JPanel(new FlowLayout());
        JLabel categoryLabel = new JLabel("Category:");
        String[] categories = {"All", "Car", "Motorcycle", "Bicycle", "Scooter"};
        categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performCategoryFilter();
            }
        });
        categoryPanel.add(categoryLabel);
        categoryPanel.add(categoryComboBox);

        // Fetch data from the database
        ArrayList<Vehicle> vehicles = getVehiclesFromDatabase();

        // Create table model and populate data
        String[] columnNames = {"Registration Number", "Brand", "Model", "Color", "Autonomy", "Type", "Passengers", "Rental Cost", "Insurance Cost", "Available", "Action"};
        Object[][] data = new Object[vehicles.size()][columnNames.length];

        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle vehicle = vehicles.get(i);
            data[i][0] = vehicle.getRegistrationNumber();
            data[i][1] = vehicle.getBrand();
            data[i][2] = vehicle.getModel();
            data[i][3] = vehicle.getColor();
            data[i][4] = vehicle.getAutonomyKm();
            data[i][5] = vehicle.getVehicleType();
            data[i][6] = vehicle.getNumberOfPassengers();
            data[i][7] = vehicle.getDailyRentalCost();
            data[i][8] = vehicle.getDailyInsuranceCost();
            data[i][9] = vehicle.getisAvailable();
            data[i][10] = "Rent";  // Action column with Rent button
        }

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Disable editing for all columns except the "Action" column (Rent button)
                return column == 10;
            }
        };
        table = new JTable(tableModel);

        // Set up sorter for table
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        // Set a custom renderer and editor for the button column
        table.getColumnModel().getColumn(10).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(10).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(table);

        // Add components to the JFrame
        add(searchPanel, BorderLayout.NORTH);
        add(categoryPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void performSearch() {
        String searchText = searchField.getText().toLowerCase();
        sorter.setRowFilter(RowFilter.regexFilter(searchText));
    }

    private void performCategoryFilter() {
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        if (selectedCategory.equals("All")) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter(selectedCategory, 5));
        }
    }

    private ArrayList<Vehicle> getVehiclesFromDatabase() {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM vehicles");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Vehicle vehicle = new Vehicle(
                        resultSet.getString("registration_number"),
                        resultSet.getString("brand"),
                        resultSet.getString("model"),
                        resultSet.getString("color"),
                        resultSet.getInt("autonomy_km"),
                        resultSet.getString("vehicle_type"),
                        resultSet.getInt("number_of_passengers"),
                        resultSet.getDouble("daily_rental_cost"),
                        resultSet.getDouble("daily_insurance_cost"),
                        resultSet.getBoolean("available")
                );
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    private class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    private class ButtonEditor extends DefaultCellEditor {

        private JButton button;
        private String label;
        private boolean isClicked;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            button.setText((value == null) ? "" : value.toString());
            label = (value == null) ? "" : value.toString();
            isClicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isClicked) {
                rentCar(row);
            }
            isClicked = false;
            return label;
        }


        @Override
        public boolean stopCellEditing() {
            isClicked = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

    private void rentCar(int row) {
        Vehicle vehicle = getVehicleAtRow(row);

        // Add the rented vehicle's information to the "rentals" section
        addRentalInfo(vehicle);

        // Update the GUI to reflect the change (optional)
        refreshTable();
    }

    private void addRentalInfo(Vehicle vehicle) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Insert rental information into the rentals table
            String sql = "INSERT INTO rentals (customer_id, vehicle_id, rental_date, duration_days, payment_amount, paid_insurance) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            // For the purpose of this example, assume customer_id and vehicle_id
            // need to be retrieved from the database based on customer and vehicle details.
            int customerId = this.controller.getCurrentUser().getId(); // You need to implement this method
            int vehicleId = getVehicleId(vehicle.getRegistrationNumber()); // You need to implement this method

            // Set rental details
            Date currentDate = new Date();
            int durationDays = 7; // Assume a 7-day rental for simplicity
            double paymentAmount = vehicle.getDailyRentalCost() * durationDays;
            boolean paidInsurance = false; // You can customize this based on your logic

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, customerId);
                statement.setInt(2, vehicleId);
                statement.setDate(3, new java.sql.Date(currentDate.getTime()));
                statement.setInt(4, durationDays);
                statement.setDouble(5, paymentAmount);
                statement.setBoolean(6, paidInsurance);

                // Execute the insert statement
                statement.executeUpdate();

                JOptionPane.showMessageDialog(this, "Car rented: " + vehicle.getRegistrationNumber());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void refreshTable() {
        // Update the GUI table to reflect the change
        SwingUtilities.invokeLater(() -> tableModel.fireTableDataChanged());
    }

    private int getCustomerId() {
        // Implement the logic to get the customer ID from the database

        int customerId = -1; // Default value in case of an error or no data
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id FROM customers LIMIT 1")) {

            if (resultSet.next()) {
                customerId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerId;
    }

    private int getVehicleId(String registrationNumber) {
        // Implement the logic to get the vehicle ID from the database

        int vehicleId = -1; // Default value in case of an error or no data
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id FROM vehicles WHERE registration_number = ?");
        ) {
            statement.setString(1, registrationNumber);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                vehicleId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicleId;
    }


    private Vehicle getVehicleAtRow(int row) {
        String registrationNumber = (String) tableModel.getValueAt(row, 0);
        for (Vehicle vehicle : getVehiclesFromDatabase()) {
            if (vehicle.getRegistrationNumber().equals(registrationNumber)) {
                return vehicle;
            }
        }
        return null;
    }
}
