package com.groceryapp;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GroceryUI extends JFrame {
    private GroceryDAO dao = new GroceryDAO();
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField nameField, unitField, quantityField, priceField, categoryField, searchField;

    public GroceryUI() {
        setTitle("Grocery List Manager");
        setSize(900, 540);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 195, 255), 0, h, new Color(235, 247, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        setContentPane(mainPanel);

        // Top bar
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 9));
        topBar.setOpaque(false);
        JButton addBtn = createStyledButton("Add", new Color(0, 204, 102), new Color(46, 168, 82));
        JButton updateBtn = createStyledButton("Update", new Color(255, 153, 0), new Color(215, 121, 0));
        JButton deleteBtn = createStyledButton("Delete", new Color(255, 51, 51), new Color(189, 34, 59));
        JButton resetBtn = createStyledButton("Reset", new Color(140, 140, 180), new Color(110, 110, 160));
        JButton searchBtn = createStyledButton("Search", new Color(0, 102, 204), new Color(2, 63, 130));
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        searchLabel.setForeground(new Color(0, 63, 120));
        searchField = new JTextField(14);
        topBar.add(addBtn);
        topBar.add(updateBtn);
        topBar.add(deleteBtn);
        topBar.add(Box.createHorizontalStrut(24));
        topBar.add(searchLabel);
        topBar.add(searchField);
        topBar.add(searchBtn);
        topBar.add(resetBtn);

        mainPanel.add(topBar, BorderLayout.NORTH);

        // Table, NO ID
        tableModel = new DefaultTableModel(new Object[] { "Name", "Unit", "Quantity", "Price", "Category" }, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row))
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(226, 241, 255));
                return c;
            }
        };
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(0, 102, 204));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Input fields at bottom
        JPanel inputBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 10));
        inputBar.setOpaque(false);
        inputBar.add(new JLabel("Name:"));
        nameField = new JTextField(10);
        inputBar.add(nameField);
        inputBar.add(new JLabel("Unit:"));
        unitField = new JTextField(7);
        inputBar.add(unitField);
        inputBar.add(new JLabel("Quantity:"));
        quantityField = new JTextField(6);
        inputBar.add(quantityField);
        inputBar.add(new JLabel("Price:"));
        priceField = new JTextField(7);
        inputBar.add(priceField);
        inputBar.add(new JLabel("Category:"));
        categoryField = new JTextField(12);
        inputBar.add(categoryField);
        mainPanel.add(inputBar, BorderLayout.SOUTH);

        // Data
        loadItems();

        // Actions
        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim(), unit = unitField.getText().trim();
            String qtyText = quantityField.getText().trim(), priceText = priceField.getText().trim();
            String category = categoryField.getText().trim();
            if (name.isEmpty() || unit.isEmpty() || qtyText.isEmpty() || priceText.isEmpty() || category.isEmpty()) {
                showPopup("Enter all fields!");
                return;
            }
            try {
                int qty = Integer.parseInt(qtyText);
                double price = Double.parseDouble(priceText);
                dao.addItem(name, unit, qty, price, category);
                showPopup("Added!");
                loadItems();
                nameField.setText("");
                unitField.setText("");
                quantityField.setText("");
                priceField.setText("");
                categoryField.setText("");
            } catch (NumberFormatException ex) {
                showPopup("Quantity & Price must be numbers.");
            }
        });

        updateBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                showPopup("Select an item to update.");
                return;
            }
            String name = nameField.getText().trim(), unit = unitField.getText().trim();
            String qtyText = quantityField.getText().trim(), priceText = priceField.getText().trim();
            String category = categoryField.getText().trim();
            if (name.isEmpty() || unit.isEmpty() || qtyText.isEmpty() || priceText.isEmpty() || category.isEmpty()) {
                showPopup("Enter all fields!");
                return;
            }
            try {
                int qty = Integer.parseInt(qtyText);
                double price = Double.parseDouble(priceText);
                dao.updateItem(name, unit, qty, price, category);
                showPopup("Updated!");
                loadItems();
            } catch (NumberFormatException ex) {
                showPopup("Quantity & Price must be numbers.");
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                showPopup("Select an item to delete.");
                return;
            }
            String name = (String) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Delete selected item?", "Confirm",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dao.deleteItem(name);
                showPopup("Deleted!");
                loadItems();
                nameField.setText("");
                unitField.setText("");
                quantityField.setText("");
                priceField.setText("");
                categoryField.setText("");
            }
        });

        searchBtn.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            if (!keyword.isEmpty())
                loadItems(keyword);
            else
                loadItems();
        });

        resetBtn.addActionListener(e -> {
            searchField.setText("");
            loadItems();
        });

        // Table row selection autofills input fields
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                nameField.setText(tableModel.getValueAt(row, 0).toString());
                unitField.setText(tableModel.getValueAt(row, 1).toString());
                quantityField.setText(tableModel.getValueAt(row, 2).toString());
                priceField.setText(tableModel.getValueAt(row, 3).toString());
                categoryField.setText(tableModel.getValueAt(row, 4).toString());
            }
        });

        setVisible(true);
    }

    private JButton createStyledButton(String text, Color col1, Color col2) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBackground(col1);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(col2);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(col1);
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn.setBackground(col2.darker());
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btn.setBackground(col2);
            }
        });
        btn.setPreferredSize(new Dimension(102, 36));
        return btn;
    }

    private void loadItems() {
        tableModel.setRowCount(0);
        List<GroceryItem> items = dao.getAllItems();
        for (GroceryItem item : items) {
            tableModel.addRow(new Object[] { item.getName(), item.getUnit(), item.getQuantity(), item.getPrice(),
                    item.getCategory() });
        }
    }

    private void loadItems(String keyword) {
        tableModel.setRowCount(0);
        List<GroceryItem> items = dao.searchItems(keyword);
        for (GroceryItem item : items) {
            tableModel.addRow(new Object[] { item.getName(), item.getUnit(), item.getQuantity(), item.getPrice(),
                    item.getCategory() });
        }
    }

    private void showPopup(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}
