package com.evol.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.evol.util.DatabaseConnection;

public class Customer extends Model {
    private String name;
    private String address;
    private Date birthDate;
    private String drivingLicenseNumber;
    private String creditCardDetails;

    public Customer(String name, String address, Date birthDate, String drivingLicenseNumber, String creditCardDetails) {
        this.name = name;
        this.address = address;
        this.birthDate = birthDate;
        this.drivingLicenseNumber = drivingLicenseNumber;
        this.creditCardDetails = creditCardDetails;
    }

    public Customer() {}

    public static List<Customer> selectAll() throws SQLException {
        String sql = "SELECT * FROM customers";
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String address = rs.getString("address");
                java.util.Date birthDate = new java.util.Date(rs.getDate("birth_date").getTime());
                String drivingLicenseNumber = rs.getString("driving_license_number");
                String creditCardDetails = rs.getString("credit_card_details");
                customers.add(new Customer(name, address, birthDate, drivingLicenseNumber, creditCardDetails));
            }
        }
        return customers;
    }
    public void insertToDB() throws SQLException{
        String sql = "INSERT INTO customers (name, address, birth_date, driving_license_number, credit_card_details) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, this.getName());
            preparedStatement.setString(2, this.getAddress());
            preparedStatement.setDate(3, new java.sql.Date(this.getBirthDate().getTime()));
            preparedStatement.setString(4, this.getDrivingLicenseNumber());
            preparedStatement.setString(5, this.getCreditCardDetails());
            preparedStatement.executeUpdate();
        }
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public void setDrivingLicenseNumber(String drivingLicenseNumber) {
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    public String getCreditCardDetails() {
        return creditCardDetails;
    }

    public void setCreditCardDetails(String creditCardDetails) {
        this.creditCardDetails = creditCardDetails;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", birthDate=" + birthDate +
                ", drivingLicenseNumber='" + drivingLicenseNumber + '\'' +
                ", creditCardDetails='" + creditCardDetails + '\'' +
                '}';
    }
}