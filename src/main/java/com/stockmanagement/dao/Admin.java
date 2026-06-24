package com.stockmanagement.dao;

import com.stockmanagement.db.DBConnection;
import com.stockmanagement.model.Admin;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    public List<Admin> getAllAdmins() throws SQLException {
        List<Admin> admins = new ArrayList<>();
        String query = "SELECT u.id, u.username, u.email, a.admin_level FROM users u " +
                       "JOIN admin_details a ON u.id = a.user_id WHERE u.role = 'ADMIN'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                admins.add(new Admin(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("admin_level")));
            }
        }
        return admins;
    }
}
