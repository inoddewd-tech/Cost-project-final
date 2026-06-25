package com.stockmanagement.ui;

import com.stockmanagement.dao.ProductDAO;
import com.stockmanagement.model.Product;
import com.stockmanagement.util.ValidationUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ProductForm extends JFrame {

    private ProductDAO productDAO = new ProductDAO();
    private JTextField txtName, txtPrice, txtQty;
    private JTable table;
    private DefaultTableModel model;

    public ProductForm() {
        setTitle("Product Control Module");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 15, 20));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(220, 20, 60)), "Product Details", 0, 0, new Font("Segoe UI", Font.BOLD, 14), new Color(220, 20, 60))
        ));

        JLabel lblName = new JLabel("Product Name:");
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblName);
        
        txtName = new JTextField();
        txtName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(txtName);

        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblPrice);
        
        txtPrice = new JTextField();
        txtPrice.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(txtPrice);

        JLabel lblQty = new JLabel("Quantity:");
        lblQty.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblQty);
        
        txtQty = new JTextField();
        txtQty.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(txtQty);

        JButton btnAdd = new JButton("Add Product");
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAdd.setBackground(new Color(220, 20, 60));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        formPanel.add(new JLabel("")); // spacer
        formPanel.add(btnAdd);

        JPanel topWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topWrapper.add(formPanel);
        topWrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(topWrapper, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Name", "Price", "Quantity"}, 0);
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));
        add(scrollPane, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> {
            if(ValidationUtils.isEmpty(txtName.getText()) || !ValidationUtils.isPositiveNumeric(txtPrice.getText()) || !ValidationUtils.isPositiveNumeric(txtQty.getText())) {
                JOptionPane.showMessageDialog(this, "Invalid Entry Parameters!"); return;
            }
            addNewProduct(txtName.getText(), Double.parseDouble(txtPrice.getText()), Integer.parseInt(txtQty.getText()));
            refreshProductList();
        });
        
        refreshProductList();
    }

    public void addNewProduct(String name, double price, int qty) {
        Product p = new Product();
        p.setProductName(name);
        p.setPrice(price);
        p.setQuantity(qty);

        try {
            productDAO.addProduct(p);
            JOptionPane.showMessageDialog(this, "Product added successfully!");
            txtName.setText(""); txtPrice.setText(""); txtQty.setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    public void refreshProductList() {
        try {
            model.setRowCount(0);
            List<Product> products = productDAO.getAllProducts();
            for(Product p : products) {
                model.addRow(new Object[]{p.getProductId(), p.getProductName(), p.getPrice(), p.getQuantity()});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}