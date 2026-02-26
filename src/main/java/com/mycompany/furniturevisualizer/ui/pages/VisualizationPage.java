package com.mycompany.furniturevisualizer.ui.pages;

import com.mycompany.furniturevisualizer.Dashboard;
import com.mycompany.furniturevisualizer.model.FurnitureItem;
import com.mycompany.furniturevisualizer.model.FurnitureManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class VisualizationPage extends JPanel {

    private static final Color TEXT_DARK = Color.decode("#333333");
    private static final Color TEXT_LIGHT = Color.decode("#888888");
    private static final Color ORANGE_PRIMARY = Color.decode("#F25C05");
    private static final Color BG_LIGHT = Color.decode("#F5F5F5");
    private static final Color BORDER_COLOR = Color.decode("#E0E0E0");

    private JPanel furnitureListPanel;

    public VisualizationPage(Dashboard dashboard) {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout(0, 0));

        add(createTopBar(dashboard), BorderLayout.NORTH);

        JPanel mainContent = new JPanel(new BorderLayout(0, 0));
        mainContent.setBackground(Color.WHITE);
        mainContent.add(createLeftSidebar(), BorderLayout.WEST);
        mainContent.add(createCenterCanvas(), BorderLayout.CENTER);
        mainContent.add(createRightPanel(), BorderLayout.EAST);

        add(mainContent, BorderLayout.CENTER);
    }

    private JPanel createTopBar(Dashboard dashboard) {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(new EmptyBorder(15, 20, 15, 20));

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

        JLabel title = new JLabel("2D Layout Editor", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(TEXT_DARK);

        JButton view3DBtn = new JButton("👁 View in 3D");
        view3DBtn.setBackground(ORANGE_PRIMARY);
        view3DBtn.setForeground(Color.WHITE);
        view3DBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        view3DBtn.setBorderPainted(false);
        view3DBtn.setFocusPainted(false);
        view3DBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        view3DBtn.setPreferredSize(new Dimension(130, 35));

        topBar.add(back, BorderLayout.WEST);
        topBar.add(title, BorderLayout.CENTER);
        topBar.add(view3DBtn, BorderLayout.EAST);

        return topBar;
    }

    private JPanel createLeftSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setBackground(BG_LIGHT);
        sidebar.setPreferredSize(new Dimension(200, 600));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, BORDER_COLOR));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_LIGHT);
        header.setBorder(new EmptyBorder(15, 15, 10, 15));

        JLabel headerLabel = new JLabel("Furniture Items");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        headerLabel.setForeground(TEXT_DARK);

        JLabel subtitle = new JLabel("Click to add to canvas");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subtitle.setForeground(TEXT_LIGHT);

        JPanel headerTexts = new JPanel();
        headerTexts.setLayout(new BoxLayout(headerTexts, BoxLayout.Y_AXIS));
        headerTexts.setBackground(BG_LIGHT);
        headerTexts.add(headerLabel);
        headerTexts.add(subtitle);

        header.add(headerTexts, BorderLayout.CENTER);
        sidebar.add(header, BorderLayout.NORTH);

        furnitureListPanel = new JPanel();
        furnitureListPanel.setLayout(new BoxLayout(furnitureListPanel, BoxLayout.Y_AXIS));
        furnitureListPanel.setBackground(BG_LIGHT);
        furnitureListPanel.setBorder(new EmptyBorder(5, 10, 10, 10));

        loadFurnitureItems();

        JScrollPane scrollPane = new JScrollPane(furnitureListPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        sidebar.add(scrollPane, BorderLayout.CENTER);

        return sidebar;
    }

    private void loadFurnitureItems() {
        furnitureListPanel.removeAll();
        List<FurnitureItem> items = FurnitureManager.getInstance().getAllFurniture();

        String currentCategory = "";
        for (FurnitureItem item : items) {
            if (!item.getCategory().equals(currentCategory)) {
                currentCategory = item.getCategory();
                JPanel categoryHeader = createCategoryHeader(currentCategory);
                furnitureListPanel.add(categoryHeader);
                furnitureListPanel.add(Box.createVerticalStrut(5));
            }

            JPanel itemPanel = createFurnitureItemPanel(item);
            furnitureListPanel.add(itemPanel);
            furnitureListPanel.add(Box.createVerticalStrut(5));
        }

        furnitureListPanel.revalidate();
        furnitureListPanel.repaint();
    }

    private JPanel createCategoryHeader(String category) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panel.setBackground(BG_LIGHT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel icon = new JLabel("🛋");
        icon.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel label = new JLabel(category);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(TEXT_DARK);

        JLabel arrow = new JLabel("▼");
        arrow.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        arrow.setForeground(TEXT_LIGHT);

        panel.add(icon);
        panel.add(label);
        panel.add(arrow);

        return panel;
    }

    private JPanel createFurnitureItemPanel(FurnitureItem item) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(8, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            new EmptyBorder(8, 8, 8, 8)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel iconLabel = new JLabel();
        iconLabel.setPreferredSize(new Dimension(50, 50));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setVerticalAlignment(SwingConstants.CENTER);
        iconLabel.setBorder(new LineBorder(BORDER_COLOR, 1));
        iconLabel.setBackground(BG_LIGHT);
        iconLabel.setOpaque(true);

        if (item.getImagePath() != null && !item.getImagePath().isEmpty()) {
            try {
                ImageIcon icon = new ImageIcon(item.getImagePath());
                Image img = icon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
                iconLabel.setIcon(new ImageIcon(img));
            } catch (Exception e) {
                iconLabel.setText("🛋");
                iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
            }
        } else {
            iconLabel.setText("🛋");
            iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        }

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(item.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        nameLabel.setForeground(TEXT_DARK);

        JLabel priceLabel = new JLabel("$" + String.format("%.2f", item.getPrice()));
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        priceLabel.setForeground(ORANGE_PRIMARY);

        JLabel sizeLabel = new JLabel("• Standard");
        sizeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        sizeLabel.setForeground(TEXT_LIGHT);

        textPanel.add(nameLabel);
        textPanel.add(Box.createVerticalStrut(2));
        textPanel.add(priceLabel);

        panel.add(iconLabel, BorderLayout.WEST);
        panel.add(textPanel, BorderLayout.CENTER);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(new Color(255, 245, 235));
                textPanel.setBackground(new Color(255, 245, 235));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(Color.WHITE);
                textPanel.setBackground(Color.WHITE);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(VisualizationPage.this,
                    "Added " + item.getName() + " to canvas",
                    "Furniture Added",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel createCenterCanvas() {
        JPanel canvas = new JPanel(new BorderLayout());
        canvas.setBackground(Color.WHITE);
        canvas.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        toolbar.setBackground(Color.WHITE);

        JButton undoBtn = createToolbarButton("↶ Undo");
        JButton redoBtn = createToolbarButton("↷ Redo");
        JButton resetBtn = createToolbarButton("Reset Layout");
        JButton snapBtn = createToolbarButton("⊞ Snap to Grid");
        snapBtn.setBackground(new Color(255, 230, 200));

        toolbar.add(undoBtn);
        toolbar.add(redoBtn);
        toolbar.add(resetBtn);
        toolbar.add(snapBtn);

        JPanel drawingArea = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();

                g2.setColor(Color.decode("#FAFAFA"));
                g2.fillRect(0, 0, w, h);

                g2.setColor(Color.decode("#E8E8E8"));
                int gridSize = 40;
                for (int x = 0; x < w; x += gridSize) {
                    g2.drawLine(x, 0, x, h);
                }
                for (int y = 0; y < h; y += gridSize) {
                    g2.drawLine(0, y, w, y);
                }

                g2.setColor(Color.decode("#D0D0D0"));
                g2.setStroke(new BasicStroke(2));
                g2.drawRect(100, 80, 400, 320);

                g2.setColor(Color.decode("#8B4513"));
                g2.fillRect(200, 200, 160, 80);
                g2.setColor(TEXT_DARK);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                g2.drawString("Sofa", 260, 245);

                g2.setColor(TEXT_LIGHT);
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                g2.drawString("Room: 5m × 4m", 110, 70);
            }
        };
        drawingArea.setBackground(Color.WHITE);
        drawingArea.setBorder(new LineBorder(BORDER_COLOR, 1));

        canvas.add(toolbar, BorderLayout.NORTH);
        canvas.add(drawingArea, BorderLayout.CENTER);

        return canvas;
    }

    private JButton createToolbarButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setBackground(Color.WHITE);
        btn.setForeground(TEXT_DARK);
        btn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            new EmptyBorder(5, 12, 5, 12)
        ));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setPreferredSize(new Dimension(250, 600));
        rightPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, BORDER_COLOR));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(15, 15, 10, 15));

        JLabel headerLabel = new JLabel("Item Properties");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        headerLabel.setForeground(TEXT_DARK);
        header.add(headerLabel, BorderLayout.NORTH);

        JPanel propertiesPanel = new JPanel();
        propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
        propertiesPanel.setBackground(Color.WHITE);
        propertiesPanel.setBorder(new EmptyBorder(10, 15, 10, 15));

        propertiesPanel.add(createPropertySection("Sofa", "Currently selected"));
        propertiesPanel.add(Box.createVerticalStrut(15));
        propertiesPanel.add(createPropertyField("Position", "X (m)", "1.5", "Y (m)", "2.0"));
        propertiesPanel.add(Box.createVerticalStrut(10));
        propertiesPanel.add(createPropertyField("Size", "Width (m)", "2.0", "Height (m)", "1.0"));
        propertiesPanel.add(Box.createVerticalStrut(10));
        propertiesPanel.add(createRotationSection());
        propertiesPanel.add(Box.createVerticalStrut(15));
        propertiesPanel.add(createActionButtons());
        propertiesPanel.add(Box.createVerticalStrut(20));
        propertiesPanel.add(createStatusSection());

        JScrollPane scrollPane = new JScrollPane(propertiesPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        rightPanel.add(header, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        return rightPanel;
    }

    private JPanel createPropertySection(String title, String subtitle) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(TEXT_DARK);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subtitleLabel.setForeground(ORANGE_PRIMARY);

        panel.add(titleLabel);
        panel.add(subtitleLabel);

        return panel;
    }

    private JPanel createPropertyField(String label, String field1, String val1, String field2, String val2) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel labelText = new JLabel(label);
        labelText.setFont(new Font("Segoe UI", Font.BOLD, 12));
        labelText.setForeground(TEXT_DARK);
        panel.add(labelText);
        panel.add(Box.createVerticalStrut(5));

        JPanel fieldsPanel = new JPanel(new GridLayout(1, 4, 5, 0));
        fieldsPanel.setBackground(Color.WHITE);
        fieldsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel label1 = new JLabel(field1);
        label1.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        JTextField text1 = new JTextField(val1);
        text1.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        JLabel label2 = new JLabel(field2);
        label2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        JTextField text2 = new JTextField(val2);
        text2.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        fieldsPanel.add(label1);
        fieldsPanel.add(text1);
        fieldsPanel.add(label2);
        fieldsPanel.add(text2);

        panel.add(fieldsPanel);

        return panel;
    }

    private JPanel createRotationSection() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel("Rotation: 0°");
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(TEXT_DARK);
        panel.add(label);
        panel.add(Box.createVerticalStrut(8));

        JButton rotateBtn = new JButton("↻ Rotate 90°");
        rotateBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        rotateBtn.setBackground(ORANGE_PRIMARY);
        rotateBtn.setForeground(Color.WHITE);
        rotateBtn.setBorderPainted(false);
        rotateBtn.setFocusPainted(false);
        rotateBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rotateBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        rotateBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        panel.add(rotateBtn);

        return panel;
    }

    private JPanel createActionButtons() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton deleteBtn = new JButton("🗑 Delete Item");
        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        deleteBtn.setBackground(Color.decode("#DC3545"));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setBorderPainted(false);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        deleteBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        panel.add(deleteBtn);

        return panel;
    }

    private JPanel createStatusSection() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel statusLabel = new JLabel("Status");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusLabel.setForeground(TEXT_DARK);

        JLabel itemsLabel = new JLabel("Total items: 1");
        itemsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        itemsLabel.setForeground(TEXT_LIGHT);

        JLabel gridLabel = new JLabel("Grid: On");
        gridLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        gridLabel.setForeground(TEXT_LIGHT);

        panel.add(statusLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(itemsLabel);
        panel.add(gridLabel);

        return panel;
    }
}
