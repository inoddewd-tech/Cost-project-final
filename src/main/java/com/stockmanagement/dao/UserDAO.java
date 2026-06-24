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
}