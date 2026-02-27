package com.mycompany.furniturevisualizer.ui.pages;

import com.mycompany.furniturevisualizer.Dashboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class DashboardPage extends JPanel {

    private static final Color ORANGE_PRIMARY = Color.decode("#F25C05");
    private static final Color TEXT_DARK = Color.decode("#333333");
    private static final Color TEXT_LIGHT = Color.decode("#666666");
    private static final Color BG_BODY = Color.decode("#F5F7FA");
    private static final Color BORDER_COLOR = Color.decode("#E0E0E0");

    private final Dashboard dashboard;

    public DashboardPage(Dashboard dashboard) {
        this.dashboard = dashboard;
        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel mainContent = new FullWidthScrollablePanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBackground(Color.WHITE);

        mainContent.add(createHeader());
        mainContent.add(createNavBar());
        mainContent.add(createBodyPanel());

        JScrollPane scrollPane = new JScrollPane(mainContent);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(15, 60, 15, 60));
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        left.setBackground(Color.WHITE);

        JLabel logoLbl = new JLabel();
        java.net.URL imgURL = getClass().getResource("/com/mycompany/furniturevisualizer/icons8-furniture-50.png");
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            logoLbl.setIcon(new ImageIcon(img));
        } else {
            logoLbl.setText("LOGO");
            logoLbl.setForeground(ORANGE_PRIMARY);
        }

        JPanel texts = new JPanel(new GridLayout(2, 1));
        texts.setBackground(Color.WHITE);
        JLabel title = new JLabel("RoomDesigner Pro");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(TEXT_DARK);
        JLabel sub = new JLabel("Welcome, Demo Designer");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sub.setForeground(TEXT_LIGHT);
        texts.add(title);
        texts.add(sub);
        left.add(logoLbl);
        left.add(texts);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 10));
        right.setBackground(Color.WHITE);

        JLabel addFurniture = new JLabel("🛋 Add Furniture");
        addFurniture.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addFurniture.setForeground(ORANGE_PRIMARY);
        addFurniture.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addFurniture.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dashboard.showCard(Dashboard.CARD_MANAGE_FURNITURE);
            }
        });

        JLabel settings = new JLabel("⚙ Settings");
        settings.setFont(new Font("Segoe UI", Font.BOLD, 14));
        settings.setForeground(TEXT_DARK);
        settings.setCursor(new Cursor(Cursor.HAND_CURSOR));
        settings.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dashboard.showCard(Dashboard.CARD_SETTINGS);
            }
        });

        JLabel logout = new JLabel("↪ Logout");
        logout.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logout.setForeground(Color.RED);
        logout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dashboard.logout();
            }
        });

        right.add(addFurniture);
        right.add(settings);
        right.add(logout);
        header.add(left, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);
        return header;
    }

    private JPanel createNavBar() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        wrapper.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));

        JPanel nav = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 0));
        nav.setBackground(Color.WHITE);
        nav.setBorder(new EmptyBorder(0, 60, 0, 0));

        JLabel dash = new JLabel("Dashboard");
        dash.setFont(new Font("Segoe UI", Font.BOLD, 15));
        dash.setForeground(ORANGE_PRIMARY);
        dash.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, ORANGE_PRIMARY));
        dash.setPreferredSize(new Dimension(100, 43));
        dash.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel port = new JLabel("My Portfolio");
        port.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        port.setForeground(TEXT_LIGHT);
        port.setCursor(new Cursor(Cursor.HAND_CURSOR));
        port.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dashboard.showCard(Dashboard.CARD_CATALOG);
            }
        });

        nav.add(dash);
        nav.add(port);
        wrapper.add(nav, BorderLayout.WEST);
        return wrapper;
    }

    private JPanel createBodyPanel() {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        bodyPanel.setBackground(BG_BODY);
        bodyPanel.setBorder(new EmptyBorder(40, 60, 40, 60));
        bodyPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        bodyPanel.add(createActionCards());
        bodyPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        bodyPanel.add(createRecentDesignsSection());
        bodyPanel.add(Box.createVerticalGlue());

        return bodyPanel;
    }

    private JPanel createActionCards() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 30, 0));
        panel.setBackground(BG_BODY);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));
        panel.setPreferredSize(new Dimension(1000, 220));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel createCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ORANGE_PRIMARY);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
            }
        };
        createCard.setOpaque(false);
        createCard.setLayout(null);
        createCard.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createCard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dashboard.showCard(Dashboard.CARD_ROOM_SETUP);
            }
        });

        JLabel plus = new JLabel("+");
        plus.setFont(new Font("SansSerif", Font.PLAIN, 40));
        plus.setForeground(Color.WHITE);
        plus.setBounds(30, 30, 50, 50);

        JLabel cTitle = new JLabel("Create New Design");
        cTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        cTitle.setForeground(Color.WHITE);
        cTitle.setBounds(30, 100, 300, 30);

        JLabel cDesc = new JLabel("Start a new room design project for your customer");
        cDesc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cDesc.setForeground(new Color(255, 255, 255, 220));
        cDesc.setBounds(30, 135, 400, 20);
        createCard.add(plus);
        createCard.add(cTitle);
        createCard.add(cDesc);

        JPanel viewCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 15, 15));
                g2.setColor(BORDER_COLOR);
                g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 15, 15));
            }
        };
        viewCard.setOpaque(false);
        viewCard.setLayout(null);
        viewCard.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewCard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dashboard.showCard(Dashboard.CARD_CATALOG);
            }
        });

        JLabel folder = new JLabel("📂");
        folder.setFont(new Font("Segoe UI", Font.PLAIN, 35));
        folder.setBounds(30, 35, 50, 50);

        JLabel vTitle = new JLabel("View Portfolio");
        vTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        vTitle.setForeground(TEXT_DARK);
        vTitle.setBounds(30, 100, 300, 30);

        JLabel vDesc = new JLabel("Browse and manage all your design projects");
        vDesc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        vDesc.setForeground(TEXT_LIGHT);
        vDesc.setBounds(30, 135, 400, 20);
        viewCard.add(folder);
        viewCard.add(vTitle);
        viewCard.add(vDesc);

        panel.add(createCard);
        panel.add(viewCard);
        return panel;
    }

    private JPanel createRecentDesignsSection() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(BG_BODY);
        section.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel("Recent Designs");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(TEXT_DARK);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        section.add(title);
        section.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel grid = new JPanel(new GridLayout(1, 3, 30, 0));
        grid.setBackground(BG_BODY);
        grid.setAlignmentX(Component.LEFT_ALIGNMENT);

        grid.add(createDesignCard("Modern Living Room", "Sarah Johnson", "Feb 10, 2026", "room1.jpg"));
        grid.add(createDesignCard("Cozy Bedroom Design", "Michael Chen", "Feb 9, 2026", "room2.jpg"));
        grid.add(createDesignCard("Minimalist Office", "Emma Williams", "Feb 8, 2026", "room3.jpg"));
        grid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 340));

        section.add(grid);
        return section;
    }

    private JPanel createDesignCard(String title, String customer, String date, String imageName) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(0, 0, 10, 0)
        ));

        JLabel imgLabel = new JLabel();
        imgLabel.setOpaque(true);
        imgLabel.setBackground(Color.decode("#EEEEEE"));
        imgLabel.setPreferredSize(new Dimension(400, 200));
        imgLabel.setMaximumSize(new Dimension(Short.MAX_VALUE, 200));
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);

        java.net.URL imgUrl = getClass().getResource("/com/mycompany/furniturevisualizer/" + imageName);
        if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            Image scaledImg = icon.getImage().getScaledInstance(350, 200, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(scaledImg));
            imgLabel.setText("");
        } else {
            imgLabel.setText("No Image Found");
        }

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(15, 20, 10, 20));
        content.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 16));
        t.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel c = new JLabel("Customer: " + customer);
        c.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        c.setForeground(TEXT_LIGHT);
        c.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel d = new JLabel(date);
        d.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        d.setForeground(TEXT_LIGHT);
        d.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        btnRow.setBackground(Color.WHITE);
        btnRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton openBtn = new JButton("👁 Open");
        openBtn.setBackground(ORANGE_PRIMARY);
        openBtn.setForeground(Color.WHITE);
        openBtn.setBorderPainted(false);
        openBtn.setFocusPainted(false);
        openBtn.setPreferredSize(new Dimension(100, 32));
        openBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel editIcon = new JLabel("  ✎  ");
        editIcon.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        editIcon.setForeground(TEXT_LIGHT);
        editIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel delIcon = new JLabel("  🗑  ");
        delIcon.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        delIcon.setForeground(Color.RED);
        delIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnRow.add(openBtn);
        btnRow.add(Box.createRigidArea(new Dimension(15, 0)));
        btnRow.add(editIcon);
        btnRow.add(Box.createRigidArea(new Dimension(10, 0)));
        btnRow.add(delIcon);

        content.add(t);
        content.add(Box.createRigidArea(new Dimension(0, 5)));
        content.add(c);
        content.add(d);
        content.add(Box.createRigidArea(new Dimension(0, 15)));
        content.add(btnRow);
        card.add(imgLabel);
        card.add(content);
        return card;
    }
}

class FullWidthScrollablePanel extends JPanel implements Scrollable {

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 16;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return Math.max(visibleRect.height - 16, 16);
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
}
