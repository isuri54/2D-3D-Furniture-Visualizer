package com.mycompany.furniturevisualizer.ui.pages;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardPage extends JPanel {

    private static final Color ORANGE_PRIMARY = Color.decode("#FF6D00");
    private static final Color TEXT_DARK = Color.decode("#333333");
    private static final Color TEXT_LIGHT = Color.decode("#888888");

    public DashboardPage() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel welcome = new JLabel("Welcome to Furniture Visualizer");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcome.setForeground(TEXT_DARK);
        add(welcome, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(Color.WHITE);
        JLabel hint = new JLabel("Use the menu above to open Catalog or Visualization");
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        hint.setForeground(TEXT_LIGHT);
        center.add(hint);
        add(center, BorderLayout.CENTER);
    }
}
