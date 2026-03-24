package com.groceryapp;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterUI extends JFrame {
    public RegisterUI() {
        setTitle("Register - Grocery List Manager");
        setSize(420, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(32, 178, 170);
                Color color2 = new Color(224, 255, 255);
                int w = getWidth(), h = getHeight();
                g2d.setPaint(new GradientPaint(0, 0, color1, 0, h, color2));
                g2d.fillRect(0, 0, w, h);
            }
        };
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Create New Account", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.DARK_GRAY);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(title, gbc);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(userLabel, gbc);

        JTextField usernameField = new JTextField(18);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(usernameField, gbc);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passLabel, gbc);

        JPasswordField passwordField = new JPasswordField(18);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(passwordField, gbc);

        JLabel confirmLabel = new JLabel("Confirm Password:");
        confirmLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(confirmLabel, gbc);

        JPasswordField confirmField = new JPasswordField(18);
        confirmField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(confirmField, gbc);

        JButton registerBtn = new JButton("Register");
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        registerBtn.setBackground(new Color(0, 153, 76));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(registerBtn, gbc);

        JLabel statusLabel = new JLabel(" ", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        statusLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(statusLabel, gbc);

        registerBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirm = new String(confirmField.getPassword());

            if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                statusLabel.setText("All fields are required.");
                return;
            }
            if (!password.equals(confirm)) {
                statusLabel.setText("Passwords do not match.");
                return;
            }

            try (Connection con = DatabaseConnection.getConnection()) {
                if (con == null) {
                    statusLabel.setText("Cannot connect to database.");
                    return;
                }
                PreparedStatement check = con.prepareStatement("SELECT id FROM users WHERE username = ?");
                check.setString(1, username);
                ResultSet rs = check.executeQuery();
                if (rs.next()) {
                    statusLabel.setText("Username already exists.");
                    return;
                }
                PreparedStatement insert = con.prepareStatement("INSERT INTO users(username, password) VALUES (?, ?)");
                insert.setString(1, username);
                insert.setString(2, password); // For demo only!
                insert.executeUpdate();
                statusLabel.setForeground(new Color(0, 130, 0));
                statusLabel.setText("Registration successful. You can now log in.");
            } catch (SQLException ex) {
                statusLabel.setForeground(Color.RED);
                statusLabel.setText("DB error. Please try again.");
                ex.printStackTrace();
            }
        });

        setContentPane(panel);
        setVisible(true);
    }
}
