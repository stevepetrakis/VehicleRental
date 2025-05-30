package com.evol.storage;

import com.evol.config.Role;
import com.evol.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrentUser {
    private int id;
    private String username;

    private int role;

    public CurrentUser(int id,String username) {
        this.id = id;
        this.username = username;
    }

    public CurrentUser() {}

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public int getId() {
        return this.id;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public boolean login(String username, String password) {
//        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
//        try (Connection connection = DatabaseConnection.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//
//            preparedStatement.setString(1, username);
//            preparedStatement.setString(2, password);
//
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                if(resultSet.next()){
//                    CurrentUser.this.id = resultSet.getInt("id");
//                    CurrentUser.this.username = resultSet.getString("username");
//                    CurrentUser.this.role = resultSet.getInt("role");
//                    return resultSet.next();
//                }
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) { // Move to the first row before retrieving data
                    this.id = resultSet.getInt("id");
                    this.username = resultSet.getString("username");
                    this.role = resultSet.getInt("role");
                    return true; // Authentication is successful if there is a matching record
                } else {
                    return false; // No matching record found
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
