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
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.add(new JLabel("Staff Name:")); txtName = new JTextField(); formPanel.add(txtName);
        formPanel.add(new JLabel("Designation:")); txtRole = new JTextField(); formPanel.add(txtRole);
        formPanel.add(new JLabel("Email:")); txtEmail = new JTextField(); formPanel.add(txtEmail);
        formPanel.add(new JLabel("Base Wage:")); txtSalary = new JTextField(); formPanel.add(txtSalary);
        
        JButton btnAdd = new JButton("Onboard Employee");
        formPanel.add(btnAdd);
        add(formPanel, BorderLayout.WEST);

        model = new DefaultTableModel(new String[]{"ID", "Name", "Role", "Email", "Salary"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnAdd.addActionListener(e -> {
            if(!ValidationUtils.isValidEmail(txtEmail.getText()) || !ValidationUtils.isPositiveNumeric(txtSalary.getText())) {
                JOptionPane.showMessageDialog(this, "Input verification parsing failure!"); return;
            }
            try {
                employeeDAO.addEmployee(new Employee(0, txtName.getText(), txtRole.getText(), txtEmail.getText(), Double.parseDouble(txtSalary.getText())));
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
