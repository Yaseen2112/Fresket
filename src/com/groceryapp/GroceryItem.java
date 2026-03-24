package com.groceryapp;

public class GroceryItem {
    private String name, unit, category;
    private int quantity;
    private double price;

    public GroceryItem(String name, String unit, int quantity, double price, String category) {
        this.name = name;
        this.unit = unit;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
}
