package com.stockmanagement.dao;

import com.stockmanagement.db.DBConnection;
import com.stockmanagement.model.User;
import java.sql.*;

public class UserDAO {
    public User login(String username, String password) throws SQLException {
        String query = "SELECT id, username, email, role FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password); 
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("role"));
                }
            }
        }
        return null;
    }

    public boolean registerUser(String username, String password, String email, String role) throws SQLException {
        String query = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setString(4, role);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                if ("ADMIN".equalsIgnoreCase(role)) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            long userId = generatedKeys.getLong(1);
                            String adminQuery = "INSERT INTO admin_details (user_id, admin_level) VALUES (?, ?)";
                            try (PreparedStatement adminStmt = conn.prepareStatement(adminQuery)) {
                                adminStmt.setLong(1, userId);
                                adminStmt.setString(2, "Standard Admin"); // Default level
                                adminStmt.executeUpdate();
                            }
                        }
                    }
                }
                return true;
            }
            return false;
        }
    }
}