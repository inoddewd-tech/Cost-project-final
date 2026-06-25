package com.stockmanagement.dao;

import com.stockmanagement.db.DBConnection;
import com.stockmanagement.model.Product; // Ensure this model exists
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // --- Core method used by your new DashboardForm ---
    public boolean insertProduct(String name, double price, int qty) throws SQLException {
        String sql = "INSERT INTO products (product_name, price, quantity) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, qty);
            return ps.executeUpdate() > 0;
        }
    }

    // --- Bridge method for ProductForm (Fixes Build Error 1) ---
    public boolean addProduct(Product p) throws SQLException {
        return insertProduct(p.getProductName(), p.getPrice(), p.getQuantity());
    }

    // --- Bridge method for ProductForm (Fixes Build Error 2) ---
    public List<Product> getAllProducts() throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT id, product_name, price, quantity FROM products";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("id"));
                p.setProductName(rs.getString("product_name"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                list.add(p);
            }
        }
        return list; 
    }
}