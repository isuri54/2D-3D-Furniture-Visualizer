package com.mycompany.furniturevisualizer.ui.pages;

import com.mycompany.furniturevisualizer.Dashboard;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CatalogPage extends JPanel {

    private static final Color TEXT_DARK = Color.decode("#333333");
    private static final Color TEXT_LIGHT = Color.decode("#666666");
    private static final Color ORANGE_PRIMARY = Color.decode("#F25C05");
    private static final Color BG_BODY = Color.decode("#F6F6F6");
    private static final Color BORDER_COLOR = Color.decode("#E0E0E0");

    public CatalogPage(Dashboard dashboard) {
        setLayout(new BorderLayout());
        setBackground(BG_BODY);

        add(createTopBar(dashboard), BorderLayout.NORTH);
        add(createScrollableContent(), BorderLayout.CENTER);
    }

    private JComponent createTopBar(Dashboard dashboard) {
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#EAEAEA")),
                new EmptyBorder(14, 30, 14, 30)
        ));

        JLabel back = new JLabel("← Dashboard");
        back.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        back.setForeground(TEXT_LIGHT);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dashboard.showCard(Dashboard.CARD_HOME);
            }
        });

        JLabel title = new JLabel("My Design Portfolio", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(TEXT_DARK);

        JButton newDesignBtn = new JButton("+ New Design");
        newDesignBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        newDesignBtn.setForeground(Color.WHITE);
        newDesignBtn.setBackground(ORANGE_PRIMARY);
        newDesignBtn.setBorderPainted(false);
        newDesignBtn.setFocusPainted(false);
        newDesignBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        newDesignBtn.setBorder(new EmptyBorder(8, 16, 8, 16));
        newDesignBtn.addActionListener(e -> dashboard.showCard(Dashboard.CARD_ROOM_SETUP));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.add(newDesignBtn);

        top.add(back, BorderLayout.WEST);
        top.add(title, BorderLayout.CENTER);
        top.add(rightPanel, BorderLayout.EAST);

        return top;
    }

    private JComponent createScrollableContent() {
        JPanel body = new JPanel();
        body.setOpaque(true);
        body.setBackground(BG_BODY);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBorder(new EmptyBorder(28, 30, 40, 30));

        // Search and filter section
        JPanel searchPanel = new JPanel(new BorderLayout(16, 0));
        searchPanel.setOpaque(false);
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField searchField = new JTextField("Search by room name or customer...");
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchField.setForeground(TEXT_LIGHT);
        searchField.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(10, 12, 10, 12)
        ));
        searchField.setPreferredSize(new Dimension(400, 40));

        JComboBox<String> filterCombo = new JComboBox<>(new String[]{"All Rooms", "Living", "Bedroom", "Dining", "Kitchen", "Office"});
        filterCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        filterCombo.setBorder(new LineBorder(BORDER_COLOR, 1, true));
        filterCombo.setPreferredSize(new Dimension(120, 40));

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(filterCombo, BorderLayout.EAST);

        body.add(searchPanel);
        body.add(Box.createRigidArea(new Dimension(0, 16)));

        // Results count
        JLabel resultsLabel = new JLabel("Showing 6 of 6 designs");
        resultsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        resultsLabel.setForeground(TEXT_LIGHT);
        resultsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(resultsLabel);
        body.add(Box.createRigidArea(new Dimension(0, 16)));

        // Design cards grid
        JPanel grid = new JPanel(new GridLayout(2, 3, 20, 20));
        grid.setOpaque(false);
        grid.setAlignmentX(Component.LEFT_ALIGNMENT);

        grid.add(createDesignCard("Modern Living Room", "Sarah Johnson", "Feb 10, 2026", "Living"));
        grid.add(createDesignCard("Cozy Bedroom Design", "Michael Chen", "Feb 9, 2026", "Bedroom"));
        grid.add(createDesignCard("Minimalist Office", "Emma Williams", "Feb 8, 2026", "Office"));
        grid.add(createDesignCard("Elegant Dining Room", "James Anderson", "Feb 7, 2026", "Dining"));
        grid.add(createDesignCard("Contemporary Kitchen", "Lisa Martinez", "Feb 6, 2026", "Kitchen"));
        grid.add(createDesignCard("Scandinavian Living", "David Thompson", "Feb 5, 2026", "Living"));

        body.add(grid);
        body.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(body);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(BG_BODY);
        return scrollPane;
    }

    private JPanel createDesignCard(String title, String customer, String date, String category) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new LineBorder(BORDER_COLOR, 1, true));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Image section with tag
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(Color.decode("#EEEEEE"));
        imagePanel.setPreferredSize(new Dimension(250, 150));
        imagePanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 150));

        JLabel imgLabel = new JLabel();
        imgLabel.setOpaque(true);
        imgLabel.setBackground(Color.decode("#EEEEEE"));
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imgLabel.setText("Room Image");
        imgLabel.setForeground(TEXT_LIGHT);

        imagePanel.add(imgLabel, BorderLayout.CENTER);

        // Category tag
        JLabel tagLabel = new JLabel(category);
        tagLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        tagLabel.setForeground(Color.WHITE);
        tagLabel.setBackground(ORANGE_PRIMARY);
        tagLabel.setOpaque(true);
        tagLabel.setBorder(new EmptyBorder(4, 8, 4, 8));
        JPanel tagPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        tagPanel.setOpaque(false);
        tagPanel.add(tagLabel);
        imagePanel.add(tagPanel, BorderLayout.NORTH);

        card.add(imagePanel);

        // Content section
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(14, 14, 12, 14));
        content.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel cardTitle = new JLabel(title);
        cardTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cardTitle.setForeground(TEXT_DARK);
        cardTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel customerLabel = new JLabel("Customer: " + customer);
        customerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        customerLabel.setForeground(TEXT_LIGHT);
        customerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel dateLabel = new JLabel(date);
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        dateLabel.setForeground(TEXT_LIGHT);
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        content.add(cardTitle);
        content.add(Box.createRigidArea(new Dimension(0, 4)));
        content.add(customerLabel);
        content.add(dateLabel);
        content.add(Box.createRigidArea(new Dimension(0, 10)));

        // Button row
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        btnRow.setBackground(Color.WHITE);
        btnRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton openBtn = new JButton("👁 Open");
        openBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        openBtn.setBackground(ORANGE_PRIMARY);
        openBtn.setForeground(Color.WHITE);
        openBtn.setBorderPainted(false);
        openBtn.setFocusPainted(false);
        openBtn.setPreferredSize(new Dimension(80, 32));
        openBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel copyIcon = new JLabel("📋");
        copyIcon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        copyIcon.setForeground(TEXT_LIGHT);
        copyIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel deleteIcon = new JLabel("🗑");
        deleteIcon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        deleteIcon.setForeground(Color.RED);
        deleteIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnRow.add(openBtn);
        btnRow.add(copyIcon);
        btnRow.add(deleteIcon);

        content.add(btnRow);
        card.add(content);
        return card;
    }
}
