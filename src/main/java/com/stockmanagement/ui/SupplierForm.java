package com.stockmanagement.ui;

import com.stockmanagement.dao.SupplierDAO;
import com.stockmanagement.model.Supplier;
import com.stockmanagement.util.ValidationUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class SupplierForm extends JFrame {
    private JTextField txtName, txtContact, txtEmail;
    private JTable table;
    private DefaultTableModel model;
    private SupplierDAO supplierDAO = new SupplierDAO();

    public SupplierForm() {
        setTitle("Supply Vendor Management");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 15, 20));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(220, 20, 60)), "Supplier Info", 0, 0, new Font("Segoe UI", Font.BOLD, 14), new Color(220, 20, 60))
        ));

        JLabel lblName = new JLabel("Supplier Name:");
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblName);
        txtName = new JTextField();
        txtName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(txtName);

        JLabel lblContact = new JLabel("Contact:");
        lblContact.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblContact);
        txtContact = new JTextField();
        txtContact.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(txtContact);

        JLabel lblEmail = new JLabel("Email Address:");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblEmail);
        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(txtEmail);
        
        JButton btnAdd = new JButton("Register Vendor");
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

        model = new DefaultTableModel(new String[]{"ID", "Name", "Contact", "Email"}, 0);
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));
        add(scrollPane, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> {
            if(!ValidationUtils.isValidEmail(txtEmail.getText())) {
                JOptionPane.showMessageDialog(this, "Bad Email Parameter Syntax!"); return;
            }
            try {
                supplierDAO.addSupplier(new Supplier(0, txtName.getText(), txtContact.getText(), txtEmail.getText()));
                JOptionPane.showMessageDialog(this, "Supplier Added!");
                txtName.setText(""); txtContact.setText(""); txtEmail.setText("");
                loadTable();
            } catch (SQLException ex) { ex.printStackTrace(); }
        });
        loadTable();
    }

    private void loadTable() {
        try {
            model.setRowCount(0);
            List<Supplier> suppliers = supplierDAO.getAllSuppliers();
            for(Supplier s : suppliers) model.addRow(new Object[]{s.getId(), s.getName(), s.getContact(), s.getEmail()});
        } catch(Exception ex) { ex.printStackTrace(); }
    }
} 