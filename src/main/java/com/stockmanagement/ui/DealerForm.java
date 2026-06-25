package com.stockmanagement.ui;

import com.stockmanagement.dao.DealerDAO;
import com.stockmanagement.model.Dealer;
import com.stockmanagement.util.ValidationUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class DealerForm extends JFrame {
    private JTextField txtBizName, txtPhone, txtArea;
    private JTable table;
    private DefaultTableModel model;
    private DealerDAO dealerDAO = new DealerDAO();

    public DealerForm() {
        setTitle("Dealer Distribution Console");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 15, 20));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(220, 20, 60)), "Dealer Details", 0, 0, new Font("Segoe UI", Font.BOLD, 14), new Color(220, 20, 60))
        ));

        JLabel lblBizName = new JLabel("Business Name:");
        lblBizName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblBizName);
        txtBizName = new JTextField();
        txtBizName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(txtBizName);

        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblPhone);
        txtPhone = new JTextField();
        txtPhone.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(txtPhone);

        JLabel lblArea = new JLabel("Operation Area:");
        lblArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblArea);
        txtArea = new JTextField();
        txtArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(txtArea);
        
        JButton btnAdd = new JButton("Save Dealer Profile");
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

        model = new DefaultTableModel(new String[]{"ID", "Business Name", "Phone", "Area"}, 0);
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));
        add(scrollPane, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> {
            if(ValidationUtils.isEmpty(txtBizName.getText()) || !ValidationUtils.isValidPhone(txtPhone.getText())) {
                JOptionPane.showMessageDialog(this, "Invalid Entry Parameters!"); return;
            }
            try {
                dealerDAO.addDealer(new Dealer(0, txtBizName.getText(), txtPhone.getText(), txtArea.getText()));
                JOptionPane.showMessageDialog(this, "Dealer Profile Saved!");
                txtBizName.setText(""); txtPhone.setText(""); txtArea.setText("");
                loadTable();
            } catch (SQLException ex) { ex.printStackTrace(); }
        });
        loadTable();
    }

    private void loadTable() {
        try {
            model.setRowCount(0);
            List<Dealer> dealers = dealerDAO.getAllDealers();
            for(Dealer d : dealers) model.addRow(new Object[]{d.getId(), d.getBusinessName(), d.getPhone(), d.getArea()});
        } catch(Exception ex) { ex.printStackTrace(); }
    }
}