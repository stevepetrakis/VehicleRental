package com.evol.controller;

import com.evol.model.Customer;
import com.evol.model.Rental;
import com.evol.model.Vehicle;
import com.evol.storage.CurrentUser;
import com.evol.util.DatabaseConnection;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static java.lang.System.exit;

public class Controller {
    private CurrentUser currentUser;
    public void initializeDatabase() throws SQLException {
        DatabaseConnection.initialize();
    }

    public Controller() throws SQLException {
        this.initializeDatabase();
        this.currentUser = new CurrentUser();
        new LoginGUI(this);
    }

    public CurrentUser getCurrentUser() {
        return currentUser;
    }

    public boolean loginUser(String username,String password) {
        return this.currentUser.login(username,password);
    }
}