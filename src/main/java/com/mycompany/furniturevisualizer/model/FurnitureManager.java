package com.mycompany.furniturevisualizer.model;

import java.util.ArrayList;
import java.util.List;

public class FurnitureManager {
    private static FurnitureManager instance;
    private List<FurnitureItem> furnitureItems;

    private FurnitureManager() {
        furnitureItems = new ArrayList<>();
        // Add some default items
        furnitureItems.add(new FurnitureItem("2-Seater Sofa", 599.99, "Sofas"));
        furnitureItems.add(new FurnitureItem("3-Seater Sofa", 799.99, "Sofas"));
        furnitureItems.add(new FurnitureItem("L-Shaped Sofa", 1299.99, "Sofas"));
        furnitureItems.add(new FurnitureItem("Sectional Sofa", 1499.99, "Sofas"));
    }

    public static FurnitureManager getInstance() {
        if (instance == null) {
            instance = new FurnitureManager();
        }
        return instance;
    }

    public void addFurniture(FurnitureItem item) {
        furnitureItems.add(item);
    }

    public void removeFurniture(FurnitureItem item) {
        furnitureItems.remove(item);
    }

    public List<FurnitureItem> getAllFurniture() {
        return new ArrayList<>(furnitureItems);
    }

    public List<FurnitureItem> getFurnitureByCategory(String category) {
        List<FurnitureItem> result = new ArrayList<>();
        for (FurnitureItem item : furnitureItems) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                result.add(item);
            }
        }
        return result;
    }
}
