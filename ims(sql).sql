CREATE DATABASE IMS;
USE IMS;
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- Table: Product
CREATE TABLE Product (
    ProductID INT PRIMARY KEY AUTO_INCREMENT,
    ProductName VARCHAR(255) NOT NULL,
    UnitPrice DECIMAL(10, 2) NOT NULL,
    Category VARCHAR(100),
    Description TEXT
);
-- Table: CurrentStock
CREATE TABLE CurrentStock (
    StockID INT PRIMARY KEY AUTO_INCREMENT,
    ProductID INT NOT NULL,
    QuantityInStock INT NOT NULL,
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID)
);

-- Table: Customer
CREATE TABLE Customer (
    CustomerID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    ContactNumber VARCHAR(15),
    Address TEXT
);

-- Table: Supplier
CREATE TABLE Supplier (
    SupplierID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    ContactNumber VARCHAR(15),
    Address TEXT
);

-- Table: Sales
CREATE TABLE Sales (
    SaleID INT PRIMARY KEY AUTO_INCREMENT,
    ProductID INT NOT NULL,
    CustomerID INT NOT NULL,
    QuantitySold INT NOT NULL,
    SalePrice DECIMAL(10, 2) NOT NULL,
    SaleDate DATE NOT NULL,
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID),
    FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID)
);

-- Table: Purchase
CREATE TABLE Purchase (
    PurchaseID INT PRIMARY KEY AUTO_INCREMENT,
    ProductID INT NOT NULL,
    SupplierID INT NOT NULL,
    QuantityOrdered INT NOT NULL,
    PurchasePrice DECIMAL(10, 2) NOT NULL,
    OrderDate DATE NOT NULL,
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID),
    FOREIGN KEY (SupplierID) REFERENCES Supplier(SupplierID)
);
