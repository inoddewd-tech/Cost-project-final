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
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.add(new JLabel("Supplier Name:")); txtName = new JTextField(); formPanel.add(txtName);
        formPanel.add(new JLabel("Contact:")); txtContact = new JTextField(); formPanel.add(txtContact);
        formPanel.add(new JLabel("Email Address:")); txtEmail = new JTextField(); formPanel.add(txtEmail);
        
        JButton btnAdd = new JButton("Register Vendor");
        formPanel.add(btnAdd);
        add(formPanel, BorderLayout.WEST);

        model = new DefaultTableModel(new String[]{"ID", "Name", "Contact", "Email"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnAdd.addActionListener(e -> {
            if(!ValidationUtils.isValidEmail(txtEmail.getText())) {
                JOptionPane.showMessageDialog(this, "Bad Email Parameter Syntax!"); return;
            }
            try {
                supplierDAO.addSupplier(new Supplier(0, txtName.getText(), txtContact.getText(), txtEmail.getText()));
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