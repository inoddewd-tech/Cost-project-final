package com.stockmanagement.ui;

import com.stockmanagement.dao.EmployeeDAO;
import com.stockmanagement.model.Employee;
import com.stockmanagement.util.ValidationUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class EmployeeForm extends JFrame {
    private JTextField txtName, txtRole, txtEmail, txtSalary;
    private JTable table;
    private DefaultTableModel model;
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    public EmployeeForm() {
        setTitle("Staff Allocation Hub");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 15, 20));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(220, 20, 60)), "Employee Details", 0, 0, new Font("Segoe UI", Font.BOLD, 14), new Color(220, 20, 60))
        ));

        JLabel lblName = new JLabel("Staff Name:");
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblName);
        txtName = new JTextField();
        txtName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(txtName);

        JLabel lblRole = new JLabel("Designation:");
        lblRole.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblRole);
        txtRole = new JTextField();
        txtRole.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(txtRole);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblEmail);
        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(txtEmail);

        JLabel lblSalary = new JLabel("Base Wage:");
        lblSalary.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblSalary);
        txtSalary = new JTextField();
        txtSalary.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(txtSalary);
        
        JButton btnAdd = new JButton("Onboard Employee");
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

        model = new DefaultTableModel(new String[]{"ID", "Name", "Role", "Email", "Salary"}, 0);
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));
        add(scrollPane, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> {
            if(!ValidationUtils.isValidEmail(txtEmail.getText()) || !ValidationUtils.isPositiveNumeric(txtSalary.getText())) {
                JOptionPane.showMessageDialog(this, "Input verification parsing failure!"); return;
            }
            try {
                employeeDAO.addEmployee(new Employee(0, txtName.getText(), txtRole.getText(), txtEmail.getText(), Double.parseDouble(txtSalary.getText())));
                JOptionPane.showMessageDialog(this, "Employee Onboarded!");
                txtName.setText(""); txtRole.setText(""); txtEmail.setText(""); txtSalary.setText("");
                loadTable();
            } catch (SQLException ex) { ex.printStackTrace(); }
        });
        loadTable();
    }

    private void loadTable() {
        try {
            model.setRowCount(0);
            List<Employee> employees = employeeDAO.getAllEmployees();
            for(Employee e : employees) model.addRow(new Object[]{e.getId(), e.getName(), e.getRole(), e.getEmail(), e.getSalary()});
        } catch(Exception ex) { ex.printStackTrace(); }
    }
}
