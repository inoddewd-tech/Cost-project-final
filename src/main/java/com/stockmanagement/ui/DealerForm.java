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
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.add(new JLabel("Business Name:")); txtBizName = new JTextField(); formPanel.add(txtBizName);
        formPanel.add(new JLabel("Phone:")); txtPhone = new JTextField(); formPanel.add(txtPhone);
        formPanel.add(new JLabel("Operation Area:")); txtArea = new JTextField(); formPanel.add(txtArea);
        
        JButton btnAdd = new JButton("Save Dealer Profile");
        formPanel.add(btnAdd);
        add(formPanel, BorderLayout.WEST);

        model = new DefaultTableModel(new String[]{"ID", "Business Name", "Phone", "Area"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnAdd.addActionListener(e -> {
            if(ValidationUtils.isEmpty(txtBizName.getText()) || !ValidationUtils.isValidPhone(txtPhone.getText())) {
                JOptionPane.showMessageDialog(this, "Invalid Entry Parameters!"); return;
            }
            try {
                dealerDAO.addDealer(new Dealer(0, txtBizName.getText(), txtPhone.getText(), txtArea.getText()));
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