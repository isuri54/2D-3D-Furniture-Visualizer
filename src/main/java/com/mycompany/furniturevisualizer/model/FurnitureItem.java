package com.mycompany.furniturevisualizer.model;

public class FurnitureItem {
    private String name;
    private double price;
    private String category;
    private String imagePath;

    public FurnitureItem(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.imagePath = null;
    }

    public FurnitureItem(String name, double price, String category, String imagePath) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return name + " - $" + String.format("%.2f", price);
    }
}
