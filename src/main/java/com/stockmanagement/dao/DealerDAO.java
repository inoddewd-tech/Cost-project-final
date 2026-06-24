package com.stockmanagement.dao;

import com.stockmanagement.db.DBConnection;
import com.stockmanagement.model.Dealer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DealerDAO {
    public void addDealer(Dealer dealer) throws SQLException {
        String query = "INSERT INTO dealer (business_name, phone, area) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, dealer.getBusinessName());
            stmt.setString(2, dealer.getPhone());
            stmt.setString(3, dealer.getArea());
            stmt.executeUpdate();
        }
    }

    public List<Dealer> getAllDealers() throws SQLException {
        List<Dealer> list = new ArrayList<>();
        String query = "SELECT * FROM dealer";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                list.add(new Dealer(rs.getInt("id"), rs.getString("business_name"), rs.getString("phone"), rs.getString("area")));
            }
        }
        return list;
    }
}