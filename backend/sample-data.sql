-- Sample Data for Sweet Shop Management System
-- This script can be run after the application creates the tables

-- Note: Passwords are bcrypt hashed
-- Plain text passwords for reference:
-- admin_user: admin123
-- regular_user: user123

-- Create users (passwords are hashed with BCrypt)
INSERT INTO users (username, password, role) VALUES
('admin', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQNkhksvxD/zxY9qNk9aW', 'ADMIN'),
('john_doe', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQNkhksvxD/zxY9qNk9aW', 'USER'),
('jane_smith', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQNkhksvxD/zxY9qNk9aW', 'USER');

-- Create sweets - Chocolate Category
INSERT INTO sweets (name, category, price, quantity) VALUES
('Dark Chocolate Bar', 'Chocolate', 2.99, 100),
('Milk Chocolate Bar', 'Chocolate', 2.50, 150),
('White Chocolate Bar', 'Chocolate', 2.75, 120),
('Chocolate Truffles', 'Chocolate', 8.99, 50),
('Chocolate Coins', 'Chocolate', 3.50, 200);

-- Create sweets - Candy Category
INSERT INTO sweets (name, category, price, quantity) VALUES
('Lollipops Mixed', 'Candy', 0.99, 300),
('Hard Candy Assortment', 'Candy', 4.99, 80),
('Candy Canes', 'Candy', 1.50, 150),
('Rock Candy', 'Candy', 2.99, 100),
('Sour Candy Mix', 'Candy', 3.99, 120);

-- Create sweets - Gummy Category
INSERT INTO sweets (name, category, price, quantity) VALUES
('Gummy Bears', 'Gummy', 2.49, 180),
('Gummy Worms', 'Gummy', 2.49, 160),
('Sour Gummy Bears', 'Gummy', 2.99, 140),
('Gummy Fruit Slices', 'Gummy', 3.49, 100),
('Gummy Cola Bottles', 'Gummy', 2.75, 110);

-- Create sweets - Licorice Category
INSERT INTO sweets (name, category, price, quantity) VALUES
('Black Licorice Twists', 'Licorice', 3.50, 80),
('Red Licorice Ropes', 'Licorice', 3.25, 90),
('Licorice Allsorts', 'Licorice', 4.50, 60);

-- Create sweets - Caramel Category
INSERT INTO sweets (name, category, price, quantity) VALUES
('Soft Caramels', 'Caramel', 4.99, 70),
('Caramel Popcorn', 'Caramel', 5.50, 50),
('Salted Caramels', 'Caramel', 6.99, 40);

-- Create sweets - Mint Category
INSERT INTO sweets (name, category, price, quantity) VALUES
('Peppermint Patties', 'Mint', 2.99, 100),
('After Dinner Mints', 'Mint', 3.50, 80),
('Mint Chocolates', 'Mint', 4.99, 60);

-- Create some out-of-stock items for testing
INSERT INTO sweets (name, category, price, quantity) VALUES
('Limited Edition Chocolate', 'Chocolate', 9.99, 0),
('Rare Gummy Mix', 'Gummy', 7.99, 0);

-- Summary
SELECT 'Users created: ' || COUNT(*) FROM users;
SELECT 'Sweets created: ' || COUNT(*) FROM sweets;
SELECT 'In-stock items: ' || COUNT(*) FROM sweets WHERE quantity > 0;
SELECT 'Out-of-stock items: ' || COUNT(*) FROM sweets WHERE quantity = 0;
