package com.stockmanagement.ui;

import com.stockmanagement.dao.UserDAO;
import com.stockmanagement.util.ValidationUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class RegisterForm extends JFrame {
    private JTextField txtUsername;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JComboBox<String> cbRole;
    private JButton btnRegister;
    private JButton btnCancel;
    private UserDAO userDAO;

    public RegisterForm() {
        userDAO = new UserDAO();
        setTitle("Create Account");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        JLabel lblHeader = new JLabel("Create New Account");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerPanel.add(lblHeader);
        add(headerPanel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        panel.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        panel.add(txtUsername);

        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        panel.add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        panel.add(new JLabel("Role:"));
        cbRole = new JComboBox<>(new String[]{"ADMIN", "USER"});
        panel.add(cbRole);

        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        btnRegister = new JButton("Register");
        btnRegister.setBackground(new Color(46, 204, 113));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnCancel = new JButton("Cancel");
        btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        buttonPanel.add(btnRegister);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> {
            this.dispose();
            new LoginForm().setVisible(true);
        });

        btnRegister.addActionListener(e -> {
            String username = txtUsername.getText();
            String email = txtEmail.getText();
            String password = new String(txtPassword.getPassword());
            String role = (String) cbRole.getSelectedItem();

            if (ValidationUtils.isEmpty(username) || ValidationUtils.isEmpty(password)) {
                JOptionPane.showMessageDialog(this, "Username and Password cannot be empty!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!ValidationUtils.isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email address!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                boolean success = userDAO.registerUser(username, password, email, role);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Account Created Successfully! Please Login.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    new LoginForm().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to create account.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
