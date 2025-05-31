# Vehicle Rental Management System

A Java-based desktop application for managing a vehicle rental business. The system provides functionality for both administrators and customers to manage vehicle rentals, user accounts, and vehicle inventory.

## Features

### User Management
- User registration and authentication system
- Support for different user roles (Admin and Customer)
- Secure password handling

### Vehicle Management
- Add new vehicles to the inventory
- List all available vehicles
- Filter vehicles by type (Car, Motorcycle, Bicycle, Scooter)
- Search vehicles by various criteria
- Track vehicle availability status

### Rental Management
- Create new rental agreements
- Track rental duration and payment
- Calculate rental and insurance costs
- Manage rental history

### Customer Management
- Customer registration
- Store customer information (name, address, birth date, etc.)
- Track customer rental history
- Manage driving license and credit card information

## Technical Details

### Technology Stack
- **Programming Language**: Java
- **Database**: MySQL
- **GUI Framework**: Java Swing
- **Database Connectivity**: JDBC (MySQL Connector/J)

### System Architecture
- MVC (Model-View-Controller) design pattern
- Role-based access control
- Modular component design

### Database Schema
The system uses the following main tables:
- `users`: Stores user authentication information
- `customers`: Contains customer details
- `vehicles`: Manages vehicle inventory
- `rentals`: Tracks rental transactions

## Setup Instructions

1. **Database Setup**
   - Install MySQL server
   - Create a new database
   - Update the database connection details in `DatabaseConnection.java`:
     ```java
     private static final String URL = "jdbc:mysql://localhost:3306/database";
     private static final String USER = "root";
     private static final String PASSWORD = "";
     ```

2. **Project Dependencies**
   - MySQL Connector/J library
   - Java Development Kit (JDK)

3. **Running the Application**
   - Compile the Java files
   - Run the `Main.java` class
   - The system will automatically initialize the database tables on first run

## User Interface

### Admin Dashboard
- Vehicle management (Add/List/Update vehicles)
- Customer management
- Rental tracking
- System monitoring

### Customer Interface
- Vehicle browsing and filtering
- Rental booking
- Personal information management
- Rental history viewing

## Security Features

- Password-protected user accounts
- Role-based access control
- Secure storage of sensitive information
- Transaction management for data integrity

## Sample Usage

1. **Starting the Application**
   ```java
   public static void main(String[] args) throws SQLException {
       new Controller();
   }
   ```

2. **Adding a New Vehicle**
   ```java
   Vehicle vehicle = new Vehicle(
       "ABC123",        // Registration Number
       "Toyota",        // Brand
       "Camry",        // Model
       "Blue",         // Color
       500,            // Autonomy (km)
       "Sedan",        // Type
       5,              // Number of passengers
       40.0,           // Daily rental cost
       10.0           // Daily insurance cost
   );
   vehicle.insertToDB();
   ```
