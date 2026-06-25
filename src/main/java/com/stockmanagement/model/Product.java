package com.stockmanagement.model;

public class Product {
    private int productId;
    private String productName;
    private double price;
    private int quantity;

    // Default Constructor
    public Product() {}

    // Getters
    public int getProductId() { return productId; }
    public String getProductName() { return productName; } // <--- This fixes your error
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    // Setters
    public void setProductId(int productId) { this.productId = productId; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}