package com.stockmanagement.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    // Automatically creates the database if it doesn't exist yet
    private static final String URL = "jdbc:mysql://localhost:3306/stock_management?createDatabaseIfNotExist=true";
    private static final String USER = "root";
    private static final String PASSWORD = ""; 
    private static boolean isInitialized = false;

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found", e);
        }
        
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        
        if (!isInitialized) {
            initializeDatabase(conn);
            isInitialized = true;
        }
        
        return conn;
    }

    private static void initializeDatabase(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(50) NOT NULL UNIQUE, password VARCHAR(255) NOT NULL, email VARCHAR(100) NOT NULL, role VARCHAR(20) NOT NULL)");
            stmt.execute("CREATE TABLE IF NOT EXISTS admin_details (id INT AUTO_INCREMENT PRIMARY KEY, user_id INT NOT NULL, admin_level VARCHAR(50) NOT NULL, FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE)");
            stmt.execute("CREATE TABLE IF NOT EXISTS products (id INT AUTO_INCREMENT PRIMARY KEY, product_name VARCHAR(100) NOT NULL, price DECIMAL(10, 2) NOT NULL, quantity INT NOT NULL)");
            stmt.execute("CREATE TABLE IF NOT EXISTS supplier (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100) NOT NULL, contact VARCHAR(50) NOT NULL, email VARCHAR(100) NOT NULL)");
            stmt.execute("CREATE TABLE IF NOT EXISTS dealer (id INT AUTO_INCREMENT PRIMARY KEY, business_name VARCHAR(100) NOT NULL, phone VARCHAR(50) NOT NULL, area VARCHAR(100) NOT NULL)");
            stmt.execute("CREATE TABLE IF NOT EXISTS employee (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100) NOT NULL, role VARCHAR(50) NOT NULL, email VARCHAR(100) NOT NULL, salary DECIMAL(10, 2) NOT NULL)");
            
            // Insert default admin if it doesn't exist
            stmt.execute("INSERT IGNORE INTO users (id, username, password, email, role) VALUES (1, 'admin1', 'admin123', 'admin@stock.com', 'ADMIN')");
            
            // Cleanup duplicates caused by previous lack of unique constraint
            stmt.execute("DELETE FROM admin_details WHERE user_id = 1 AND id NOT IN (SELECT * FROM (SELECT MIN(id) FROM admin_details WHERE user_id = 1) AS t)");
            
            // Automatically insert missing admin_details for any ADMIN users (like Dewdun) that were created before the fix
            stmt.execute("INSERT INTO admin_details (user_id, admin_level) SELECT id, 'Standard Admin' FROM users WHERE role = 'ADMIN' AND id NOT IN (SELECT user_id FROM admin_details)");
            
        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
    }
}