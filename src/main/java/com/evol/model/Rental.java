package com.evol.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.evol.util.DatabaseConnection;

public class Rental extends Model {
    private String customerName;
    private Date rentalDate;
    private int durationDays;
    private double paymentAmount;
    private String vehicleRegistrationNumber;

    public Rental(String customerName, Date rentalDate, int durationDays, double paymentAmount, String vehicleRegistrationNumber) {
        this.customerName = customerName;
        this.rentalDate = rentalDate;
        this.durationDays = durationDays;
        this.paymentAmount = paymentAmount;
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
    }

    public static List<Rental> selectAll() throws SQLException{
        String sql = "SELECT * FROM rentals";
        List<Rental> rentals = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String customerName = rs.getString("customer_name");
                java.util.Date rentalDate = new java.util.Date(rs.getDate("rental_date").getTime());
                int durationDays = rs.getInt("duration_days");
                double paymentAmount = rs.getDouble("payment_amount");
                String vehicleRegistrationNumber = rs.getString("vehicle_registration_number");
                rentals.add(new Rental(customerName, rentalDate, durationDays, paymentAmount, vehicleRegistrationNumber));
            }
        }
        return rentals;
    }

    public void insertToDB() throws SQLException {
        String sql = "INSERT INTO rentals (customer_name, rental_date, duration_days, payment_amount, vehicle_registration_number) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, this.getCustomerName());
            preparedStatement.setDate(2, new java.sql.Date(this.getRentalDate().getTime()));
            preparedStatement.setInt(3, this.getDurationDays());
            preparedStatement.setDouble(4, this.getPaymentAmount());
            preparedStatement.setString(5, this.getVehicleRegistrationNumber());
            preparedStatement.executeUpdate();
        }
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getVehicleRegistrationNumber() {
        return vehicleRegistrationNumber;
    }

    public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "customerName='" + customerName + '\'' +
                ", rentalDate=" + rentalDate +
                ", durationDays=" + durationDays +
                ", paymentAmount=" + paymentAmount +
                ", vehicleRegistrationNumber='" + vehicleRegistrationNumber + '\'' +
                '}';
    }
}