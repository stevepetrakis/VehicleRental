package com.evol.model;

import com.evol.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Vehicle extends Model {
    private String registrationNumber;
    private String brand;
    private String model;
    private String color;
    private int autonomyKm;
    private String vehicleType;
    private int numberOfPassengers;
    private double dailyRentalCost;
    private double dailyInsuranceCost;

    private boolean available;

    public Vehicle(String registrationNumber, String brand, String model, String color, int autonomyKm, String vehicleType, int numberOfPassengers, double dailyRentalCost, double dailyInsuranceCost, boolean available) {
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.autonomyKm = autonomyKm;
        this.vehicleType = vehicleType;
        this.numberOfPassengers = numberOfPassengers;
        this.dailyRentalCost = dailyRentalCost;
        this.dailyInsuranceCost = dailyInsuranceCost;
        this.available=available;
    }

    public Vehicle(String registrationNumber, String brand, String model, String color, int autonomyKm, String vehicleType, int numberOfPassengers, double dailyRentalCost, double dailyInsuranceCost) {
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.autonomyKm = autonomyKm;
        this.vehicleType = vehicleType;
        this.numberOfPassengers = numberOfPassengers;
        this.dailyRentalCost = dailyRentalCost;
        this.dailyInsuranceCost = dailyInsuranceCost;
        this.available = false;
    }

    public static List<Vehicle> selectAll() throws SQLException {
        String sql = "SELECT * FROM vehicles";
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String registrationNumber = rs.getString("registration_number");
                String brand = rs.getString("brand");
                String model = rs.getString("model");
                String color = rs.getString("color");
                int autonomyKm = rs.getInt("autonomy_km");
                String vehicleType = rs.getString("vehicle_type");
                int numberOfPassengers = rs.getInt("number_of_passengers");
                double dailyRentalCost = rs.getDouble("daily_rental_cost");
                double dailyInsuranceCost = rs.getDouble("daily_insurance_cost");
                vehicles.add(new Vehicle(registrationNumber, brand, model, color, autonomyKm, vehicleType, numberOfPassengers, dailyRentalCost, dailyInsuranceCost));
            }
        }
        return vehicles;
    }

    @Override
    public void insertToDB() throws SQLException {
        String sql = "INSERT INTO vehicles (registration_number, brand, model, color, autonomy_km, vehicle_type, number_of_passengers, daily_rental_cost, daily_insurance_cost) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, this.getRegistrationNumber());
            preparedStatement.setString(2, this.getBrand());
            preparedStatement.setString(3, this.getModel());
            preparedStatement.setString(4, this.getColor());
            preparedStatement.setInt(5, this.getAutonomyKm());
            preparedStatement.setString(6, this.getVehicleType());
            preparedStatement.setInt(7, this.getNumberOfPassengers());
            preparedStatement.setDouble(8, this.getDailyRentalCost());
            preparedStatement.setDouble(9, this.getDailyInsuranceCost());
            preparedStatement.executeUpdate();
        }
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getAutonomyKm() {
        return autonomyKm;
    }

    public void setAutonomyKm(int autonomyKm) {
        this.autonomyKm = autonomyKm;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public double getDailyRentalCost() {
        return dailyRentalCost;
    }

    public void setDailyRentalCost(double dailyRentalCost) {
        this.dailyRentalCost = dailyRentalCost;
    }

    public double getDailyInsuranceCost() {
        return dailyInsuranceCost;
    }

    public void setDailyInsuranceCost(double dailyInsuranceCost) {
        this.dailyInsuranceCost = dailyInsuranceCost;
    }

    public boolean getisAvailable() {
        return available;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "registrationNumber='" + registrationNumber + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", autonomyKm=" + autonomyKm +
                ", vehicleType='" + vehicleType + '\'' +
                ", numberOfPassengers=" + numberOfPassengers +
                ", dailyRentalCost=" + dailyRentalCost +
                ", dailyInsuranceCost=" + dailyInsuranceCost +
                '}';
    }


}