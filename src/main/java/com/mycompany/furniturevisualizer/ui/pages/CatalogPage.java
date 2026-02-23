package com.mycompany.furniturevisualizer.ui.pages;

import com.mycompany.furniturevisualizer.Dashboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CatalogPage extends JPanel {

    private static final Color TEXT_DARK = Color.decode("#333333");
    private static final Color TEXT_LIGHT = Color.decode("#888888");
    private static final Color ORANGE_PRIMARY = Color.decode("#F25C05");

    public CatalogPage(Dashboard dashboard) {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(40, 40, 40, 40));

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        JLabel back = new JLabel("← Back to Dashboard");
        back.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        back.setForeground(ORANGE_PRIMARY);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dashboard.showCard(Dashboard.CARD_HOME);
            }
        });
        top.add(back, BorderLayout.WEST);
        JLabel title = new JLabel("Furniture Catalog");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(TEXT_DARK);
        top.add(title, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(Color.WHITE);
        JLabel placeholder = new JLabel("Browse and add furniture items here");
        placeholder.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        placeholder.setForeground(TEXT_LIGHT);
        center.add(placeholder);
        add(center, BorderLayout.CENTER);
    }
}
