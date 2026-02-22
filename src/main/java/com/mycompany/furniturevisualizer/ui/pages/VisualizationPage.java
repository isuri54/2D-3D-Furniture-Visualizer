package com.mycompany.furniturevisualizer.ui.pages;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VisualizationPage extends JPanel {

    private static final Color TEXT_DARK = Color.decode("#333333");
    private static final Color TEXT_LIGHT = Color.decode("#888888");

    public VisualizationPage() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel title = new JLabel("Room Visualization");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(TEXT_DARK);
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(Color.WHITE);
        JLabel placeholder = new JLabel("2D / 3D room design view will appear here");
        placeholder.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        placeholder.setForeground(TEXT_LIGHT);
        center.add(placeholder);
        add(center, BorderLayout.CENTER);
    }
}
