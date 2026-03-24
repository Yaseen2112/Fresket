CREATE DATABASE IF NOT EXISTS grocerydb;
USE grocerydb;

-- Drop and re-create table
DROP TABLE IF EXISTS items;
CREATE TABLE items (
    name VARCHAR(100) PRIMARY KEY,
    unit VARCHAR(32) DEFAULT 'pcs',
    quantity INT DEFAULT 0,
    price DECIMAL(10,2) DEFAULT 0.00,
    category VARCHAR(50) DEFAULT ''
);

-- Insert sample data
INSERT INTO items (name, unit, quantity, price, category) VALUES
('Milk',   'liter',  2, 55.00, 'Dairy'),
('Atta',   'kg',     5, 260.00, 'Grocery'),
('Apple',  'kg',     3, 180.00, 'Fruit'),
('Eggs',   'pcs',   12, 72.00, 'Poultry'),
('Sugar',  'kg',     2, 100.00, 'Kitchen');

-- Users login table (optional)
CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL
);

INSERT IGNORE INTO users (username, password) VALUES ('admin', 'admin123');
