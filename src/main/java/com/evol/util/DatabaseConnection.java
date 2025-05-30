package com.evol.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/database";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initialize() throws SQLException {
        try (Connection connection = getConnection()) {
            // Create a Statement object for sending SQL statements to the database.
            Statement statement = connection.createStatement();

            // SQL statement to create a simple table
            String sql;
            sql = "CREATE TABLE IF NOT EXISTS users (" +
                  "id INT AUTO_INCREMENT PRIMARY KEY," +
                  "username VARCHAR(255) NOT NULL," +
                  "password VARCHAR(255) NOT NULL," +
                  "role INT DEFAULT 2 NOT NULL," +
                  "customer_id INT NULL" +
                  ");"
            ;
            statement.execute(sql);
            sql = "CREATE TABLE IF NOT EXISTS vehicles (\n" +
                    "    id INT AUTO_INCREMENT PRIMARY KEY,\n"+
                    "    registration_number VARCHAR(255) UNIQUE,\n" +
                    "    brand VARCHAR(255) NOT NULL,\n" +
                    "    model VARCHAR(255) NOT NULL,\n" +
                    "    color VARCHAR(255) NOT NULL,\n" +
                    "    autonomy_km INT NOT NULL,\n" +
                    "    vehicle_type VARCHAR(255) NOT NULL,\n" +
                    "    number_of_passengers INT NOT NULL,\n" +
                    "    daily_rental_cost DECIMAL(10, 2) NOT NULL,\n" +
                    "    daily_insurance_cost DECIMAL(10, 2) NOT NULL,\n" +
                    "    available BOOLEAN DEFAULT FALSE NOT NULL,\n" +
                    "    damaged BOOLEAN DEFAULT FALSE NOT NULL,\n" +
                    "    damaged_days INT DEFAULT 0 NOT NULL,\n" +
                    "    damaged_type INT DEFAULT 0 NOT NULL\n" +
                    ");\n";
            statement.execute(sql);
            sql = "CREATE TABLE IF NOT EXISTS customers (\n" +
            "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
            "    firstname VARCHAR(255) NOT NULL,\n" +
            "    lastname VARCHAR(255) NOT NULL,\n" +
            "    address VARCHAR(255) NOT NULL,\n" +
            "    birth_date DATE NOT NULL,\n" +
            "    driving_license_number VARCHAR(255),\n" +
            "    credit_card_details VARCHAR(255) NOT NULL\n" +
            ");\n";
            statement.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS rentals (\n" +
                "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    customer_id INT NOT NULL,\n" +
                "    vehicle_id INT NOT NULL,\n" +
                "    rental_date DATE NOT NULL,\n" +
                "    duration_days INT NOT NULL,\n" +
                "    payment_amount DECIMAL(10, 2) NOT NULL,\n" +
                "    paid_insurance BOOLEAN DEFAULT FALSE NOT NULL\n" +
//                    "    FOREIGN KEY (customer_name) REFERENCES customers(name),\n" +
//                    "    FOREIGN KEY (vehicle_registration_number) REFERENCES vehicles(registration_number)\n" +
                ");";
            statement.execute(sql);


            System.out.println("Database initialized successfully.");
        }
    }
}