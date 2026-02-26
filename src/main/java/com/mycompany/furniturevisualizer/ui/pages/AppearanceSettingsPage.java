package com.mycompany.furniturevisualizer.ui.pages;

import java.awt.Color;

public class AppearanceSettingsPage {

    private String target;
    private Color color;
    private String material;
    private String shading;

    public AppearanceSettingsPage(String target, Color color,
                              String material, String shading) {
        this.target = target;
        this.color = color;
        this.material = material;
        this.shading = shading;
    }

    public String getTarget() { return target; }
    public Color getColor() { return color; }
    public String getMaterial() { return material; }
    public String getShading() { return shading; }
}
