package com.stockmanagement.ui;

import com.stockmanagement.dao.UserDAO;
import com.stockmanagement.model.User;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LoginForm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnCreateAccount;
    private UserDAO userDAO;

    public LoginForm() {
        userDAO = new UserDAO();
        setTitle("System Login");
        setSize(450, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        JLabel lblTitle = new JLabel("Welcome Back");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(lblTitle);
        add(headerPanel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(lblUsername);
        
        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(txtUsername);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(lblPassword);
        
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(txtPassword);

        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(new Color(52, 152, 219));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setPreferredSize(new Dimension(120, 40));

        btnCreateAccount = new JButton("Create Account");
        btnCreateAccount.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCreateAccount.setBackground(new Color(46, 204, 113));
        btnCreateAccount.setForeground(Color.WHITE);
        btnCreateAccount.setFocusPainted(false);
        btnCreateAccount.setPreferredSize(new Dimension(150, 40));

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnCreateAccount);
        add(buttonPanel, BorderLayout.SOUTH);

        btnCreateAccount.addActionListener(e -> {
            this.dispose();
            new RegisterForm().setVisible(true);
        });

        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());
            try {
                User user = userDAO.login(username, password);
                if (user != null) {
                    this.dispose(); // Close login window
                    DashboardForm dashboard = new DashboardForm();
                    dashboard.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid account credentials!", "Auth Failure", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database failure: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}