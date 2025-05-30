INSERT INTO vehicles (registration_number, brand, model, color, autonomy_km, vehicle_type, number_of_passengers, daily_rental_cost, daily_insurance_cost) VALUES
('ABC123', 'Tesla', 'Model S', 'Red', 400, 'Sedan', 5, 100.00, 15.00),
('XYZ789', 'Nissan', 'Leaf', 'Blue', 150, 'Hatchback', 5, 60.00, 10.00),
-- Add more initial data...

INSERT INTO customers (name, address, birth_date, driving_license_number, credit_card_details) VALUES
('John Doe', '123 Main St', '1980-01-01', 'D12345678', '4111111111111111'),
('Jane Smith', '456 Elm St', '1990-02-02', 'S98765432', '4222222222222222'),
-- Add more initial data...

INSERT INTO rentals (customer_name, rental_date, duration_days, payment_amount, vehicle_registration_number) VALUES
('John Doe', '2023-01-01', 3, 315.00, 'ABC123'),
('Jane Smith', '2023-01-05', 2, 140.00, 'XYZ789'),
-- Add more initial data...