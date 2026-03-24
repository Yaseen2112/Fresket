package com.groceryapp;

import javax.swing.*;
import java.awt.*;

public class WelcomeUI extends JFrame {
    public WelcomeUI(String username) {
        setTitle("Welcome to Grocery List Manager");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(0, 153, 204);
                Color color2 = new Color(204, 229, 255);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        gradientPanel.setLayout(new BorderLayout(20, 20));
        add(gradientPanel);

        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);
        gradientPanel.add(welcomeLabel, BorderLayout.NORTH);

        JTextArea introArea = new JTextArea(
                "Manage your grocery list with ease!\n\n" +
                        "Features:\n" +
                        "- Add, Update, Delete items\n" +
                        "- Search and Sort functionality\n" +
                        "- User-based login authentication\n\n" +
                        "Click Continue to start managing your groceries.");
        introArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        introArea.setBackground(new Color(0, 0, 0, 0));
        introArea.setForeground(Color.WHITE);
        introArea.setEditable(false);
        introArea.setOpaque(false);
        introArea.setFocusable(false);
        introArea.setMargin(new Insets(10, 20, 10, 20));
        gradientPanel.add(introArea, BorderLayout.CENTER);

        JButton continueButton = new JButton("Continue");
        continueButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        continueButton.setBackground(new Color(0, 102, 204));
        continueButton.setForeground(Color.WHITE);
        continueButton.setFocusPainted(false);
        continueButton.setPreferredSize(new Dimension(120, 40));
        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        btnPanel.add(continueButton);
        gradientPanel.add(btnPanel, BorderLayout.SOUTH);

        continueButton.addActionListener(e -> {
            this.dispose();
            new GroceryUI();
        });

        setVisible(true);
    }
}
