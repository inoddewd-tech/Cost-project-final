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
    private JTextField txtName, txtQty, txtPrice, txtCategory;
    private JTable table;
    private DefaultTableModel model;
    private ProductDAO productDAO = new ProductDAO();

    public ProductForm() {
        setTitle("Product Control Workspace");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.add(new JLabel("Product Name:")); txtName = new JTextField(); formPanel.add(txtName);
        formPanel.add(new JLabel("Stock Quantity:")); txtQty = new JTextField(); formPanel.add(txtQty);
        formPanel.add(new JLabel("Unit Price:")); txtPrice = new JTextField(); formPanel.add(txtPrice);
        formPanel.add(new JLabel("Category:")); txtCategory = new JTextField(); formPanel.add(txtCategory);
        
        JButton btnAdd = new JButton("Add Item");
        formPanel.add(btnAdd);
        add(formPanel, BorderLayout.WEST);

        model = new DefaultTableModel(new String[]{"ID", "Name", "Quantity", "Price", "Category"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnAdd.addActionListener(e -> {
            if(ValidationUtils.isEmpty(txtName.getText()) || !ValidationUtils.isPositiveNumeric(txtPrice.getText())) {
                JOptionPane.showMessageDialog(this, "Invalid Entry Parameters!"); return;
            }
            try {
                productDAO.addProduct(new Product(0, txtName.getText(), Integer.parseInt(txtQty.getText()), Double.parseDouble(txtPrice.getText()), txtCategory.getText()));
                loadTable();
            } catch (SQLException ex) { ex.printStackTrace(); }
        });
        loadTable();
    }

    private void loadTable() {
        try {
            model.setRowCount(0);
            List<Product> products = productDAO.getAllProducts();
            for(Product p : products) model.addRow(new Object[]{p.getId(), p.getName(), p.getQuantity(), p.getPrice(), p.getCategory()});
        } catch(Exception ex) { ex.printStackTrace(); }
    }
}