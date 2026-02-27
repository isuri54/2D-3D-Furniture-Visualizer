package com.mycompany.furniturevisualizer;

import com.mycompany.furniturevisualizer.ui.pages.CatalogPage;
import com.mycompany.furniturevisualizer.ui.pages.DashboardPage;
import com.mycompany.furniturevisualizer.ui.pages.ManageFurniturePage;
import com.mycompany.furniturevisualizer.ui.pages.RoomSetupPage;
import com.mycompany.furniturevisualizer.ui.pages.VisualizationPage;
import com.mycompany.furniturevisualizer.ui.pages.SettingsPage;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    public static final String CARD_HOME = "home";
    public static final String CARD_CATALOG = "catalog";
    public static final String CARD_ROOM_SETUP = "roomSetup";
    public static final String CARD_VISUALIZATION = "visualization";
    public static final String CARD_MANAGE_FURNITURE = "manageFurniture";
    public static final String CARD_SETTINGS = "settings";

    private final JPanel cardPanel;
    private final CardLayout cardLayout;

    public Dashboard() {
        setTitle("RoomDesigner Pro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        cardLayout = new CardLayout(0, 0);
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(Color.WHITE);
        cardPanel.add(new DashboardPage(this), CARD_HOME);
        cardPanel.add(new CatalogPage(this), CARD_CATALOG);
        cardPanel.add(new RoomSetupPage(this), CARD_ROOM_SETUP);
        cardPanel.add(new VisualizationPage(this), CARD_VISUALIZATION);
        cardPanel.add(new ManageFurniturePage(this), CARD_MANAGE_FURNITURE);
        cardPanel.add(new SettingsPage(this), CARD_SETTINGS);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(cardPanel, BorderLayout.CENTER);
    }

    /** Switch to a card (CARD_HOME, CARD_CATALOG, CARD_VISUALIZATION). */
    public void showCard(String cardName) {
        cardLayout.show(cardPanel, cardName);
    }

    /** Logout: show login and close dashboard. */
    public void logout() {
        new LoginForm2().setVisible(true);
        
        dispose();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // ignore
        }
        SwingUtilities.invokeLater(() -> new Dashboard().setVisible(true));
    }
}
