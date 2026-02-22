package com.mycompany.furniturevisualizer;

import com.mycompany.furniturevisualizer.ui.pages.CatalogPage;
import com.mycompany.furniturevisualizer.ui.pages.DashboardPage;
import com.mycompany.furniturevisualizer.ui.pages.VisualizationPage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Dashboard extends JFrame {

    private static final Color ORANGE_PRIMARY = Color.decode("#FF6D00");

    private static final String CARD_HOME = "home";
    private static final String CARD_CATALOG = "catalog";
    private static final String CARD_VISUALIZATION = "visualization";

    private final JPanel cardPanel;
    private final CardLayout cardLayout;

    public Dashboard() {
        setTitle("Dashboard - Furniture Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setMinimumSize(new Dimension(700, 500));
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        cardLayout = new CardLayout(0, 0);
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(Color.WHITE);
        cardPanel.add(new DashboardPage(), CARD_HOME);
        cardPanel.add(new CatalogPage(), CARD_CATALOG);
        cardPanel.add(new VisualizationPage(), CARD_VISUALIZATION);

        JPanel nav = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 12));
        nav.setBackground(Color.WHITE);
        nav.setBorder(new EmptyBorder(8, 16, 8, 16));

        JButton btnHome = createNavButton("Dashboard", () -> cardLayout.show(cardPanel, CARD_HOME));
        JButton btnCatalog = createNavButton("Catalog", () -> cardLayout.show(cardPanel, CARD_CATALOG));
        JButton btnViz = createNavButton("Visualization", () -> cardLayout.show(cardPanel, CARD_VISUALIZATION));

        nav.add(btnHome);
        nav.add(btnCatalog);
        nav.add(btnViz);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(nav, BorderLayout.NORTH);
        getContentPane().add(cardPanel, BorderLayout.CENTER);
    }

    private JButton createNavButton(String text, Runnable action) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        b.setForeground(ORANGE_PRIMARY);
        b.setBackground(Color.WHITE);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.addActionListener(e -> action.run());
        return b;
    }
}
