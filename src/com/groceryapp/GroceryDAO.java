package com.groceryapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroceryDAO {
    public List<GroceryItem> getAllItems() {
        List<GroceryItem> list = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM items")) {
            while (rs.next()) {
                list.add(new GroceryItem(
                        rs.getString("name"),
                        rs.getString("unit"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("category")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addItem(String name, String unit, int quantity, double price, String category) {
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO items (name, unit, quantity, price, category) VALUES (?, ?, ?, ?, ?)")) {
            ps.setString(1, name);
            ps.setString(2, unit);
            ps.setInt(3, quantity);
            ps.setDouble(4, price);
            ps.setString(5, category);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteItem(String name) {
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("DELETE FROM items WHERE name = ?")) {
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateItem(String name, String unit, int quantity, double price, String category) {
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE items SET unit=?, quantity=?, price=?, category=? WHERE name=?")) {
            ps.setString(1, unit);
            ps.setInt(2, quantity);
            ps.setDouble(3, price);
            ps.setString(4, category);
            ps.setString(5, name);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<GroceryItem> searchItems(String keyword) {
        List<GroceryItem> list = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "SELECT * FROM items WHERE name LIKE ? OR category LIKE ?")) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new GroceryItem(
                        rs.getString("name"),
                        rs.getString("unit"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("category")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
