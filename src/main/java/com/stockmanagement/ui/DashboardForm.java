package com.stockmanagement.ui;

import javax.swing.*;
import java.awt.*;

public class DashboardForm extends JFrame {
    private JPanel contentPanel;
    private JLabel lblCurrentModule;

    public DashboardForm() {
        setTitle("Stock Management Central System Hub");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ==========================================
        // 1. SIDEBAR NAVIGATION PANEL (COLORFUL)
        // ==========================================
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(18, 18, 18)); // Deep Black
        sidebarPanel.setPreferredSize(new Dimension(220, 0));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // System Title Header
        JLabel lblHeader = new JLabel("STOCK CONSOLE");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarPanel.add(lblHeader);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Navigation Buttons Array
        JButton btnProduct = createNavButton("Products");
        JButton btnSupplier = createNavButton("Suppliers");
        JButton btnDealer = createNavButton("Dealers");
        JButton btnEmployee = createNavButton("Employees");
        JButton btnAdmin = createNavButton("Security Audit");
        JButton btnLogout = createNavButton("Log Out");
        btnLogout.setBackground(new Color(192, 57, 43)); // Vibrant Red Alert

        sidebarPanel.add(btnProduct);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(btnSupplier);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(btnDealer);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(btnEmployee);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(btnAdmin);
        sidebarPanel.add(Box.createVerticalGlue()); // Push logout to bottom
        sidebarPanel.add(btnLogout);

        add(sidebarPanel, BorderLayout.WEST);

        // ==========================================
        // 2. MAIN WORKSPACE VIEW DISPLAY PANEL
        // ==========================================
        JPanel mainWorkspace = new JPanel(new BorderLayout());
        mainWorkspace.setBackground(new Color(30, 30, 30)); // Dark Grey Canvas

        // Module View Title Strip Bar
        JPanel topStrip = new JPanel(new BorderLayout());
        topStrip.setBackground(new Color(25, 25, 25));
        topStrip.setPreferredSize(new Dimension(0, 50));
        topStrip.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 20, 60))); // Crimson Red Border

        lblCurrentModule = new JLabel("  Welcome Dashboard Core Workspace");
        lblCurrentModule.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblCurrentModule.setForeground(new Color(220, 20, 60)); // Crimson Red Text
        topStrip.add(lblCurrentModule, BorderLayout.WEST);
        mainWorkspace.add(topStrip, BorderLayout.NORTH);

        // Content panel where sub-forms are nested swap-on-demand
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(30, 30, 30));
        mainWorkspace.add(contentPanel, BorderLayout.CENTER);

        add(mainWorkspace, BorderLayout.CENTER);

        // ==========================================
        // 3. ACTION HANDLERS (DOCKING INTERFACES)
        // ==========================================
        btnProduct.addActionListener(e -> switchView("Product Control Module", new ProductForm().getContentPane()));
        btnSupplier.addActionListener(e -> switchView("Supply Vendor Management", new SupplierForm().getContentPane()));
        btnDealer.addActionListener(e -> switchView("Dealer Distribution Panel", new DealerForm().getContentPane()));
        btnEmployee.addActionListener(e -> switchView("Staff Allocation Hub", new EmployeeForm().getContentPane()));
        btnAdmin.addActionListener(e -> switchView("Administrative Security Log", new AdminForm().getContentPane()));
        
        btnLogout.addActionListener(e -> {
            this.dispose();
            new LoginForm().setVisible(true); // Cycle system state back to security screen
        });
    }

    /**
     * Dynamically swaps out the content area grid with selected module's components
     */
    private void switchView(String title, Container freshContentPanel) {
        lblCurrentModule.setText("  " + title);
        contentPanel.removeAll();
        contentPanel.add(freshContentPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * Styles standard design system navigation elements
     */
    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(200, 40));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(30, 30, 30)); // Dark Grey
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}