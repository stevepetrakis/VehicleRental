CREATE TABLE vehicles (
    registration_number VARCHAR(255) PRIMARY KEY,
    brand VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    color VARCHAR(255) NOT NULL,
    autonomy_km INT NOT NULL,
    vehicle_type VARCHAR(255) NOT NULL,
    number_of_passengers INT NOT NULL,
    daily_rental_cost DECIMAL(10, 2) NOT NULL,
    daily_insurance_cost DECIMAL(10, 2) NOT NULL
);

CREATE TABLE customers (
    name VARCHAR(255) PRIMARY KEY,
    address VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    driving_license_number VARCHAR(255),
    credit_card_details VARCHAR(255) NOT NULL
);

CREATE TABLE rentals (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL,
    rental_date DATE NOT NULL,
    duration_days INT NOT NULL,
    payment_amount DECIMAL(10, 2) NOT NULL,
    vehicle_registration_number VARCHAR(255) NOT NULL,
    FOREIGN KEY (customer_name) REFERENCES customers(name),
    FOREIGN KEY (vehicle_registration_number) REFERENCES vehicles(registration_number)
);