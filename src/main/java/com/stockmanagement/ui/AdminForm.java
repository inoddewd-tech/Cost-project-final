package com.stockmanagement.ui;

import com.stockmanagement.dao.AdminDAO;
import com.stockmanagement.model.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class AdminForm extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private AdminDAO adminDAO = new AdminDAO();

    public AdminForm() {
        setTitle("Administrative Security Log");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 5, 20));
        JLabel lblHeader = new JLabel("Admin Access Control Log");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        topPanel.add(lblHeader);
        add(topPanel, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Username", "Email", "Admin Level"}, 0);
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        add(scrollPane, BorderLayout.CENTER);

        loadTable();
    }

    private void loadTable() {
        try {
            model.setRowCount(0);
            List<Admin> admins = adminDAO.getAllAdmins();
            for(Admin a : admins) {
                model.addRow(new Object[]{a.getId(), a.getUsername(), a.getEmail(), a.getAdminLevel()});
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
