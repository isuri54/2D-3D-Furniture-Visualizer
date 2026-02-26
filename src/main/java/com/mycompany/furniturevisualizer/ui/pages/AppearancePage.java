package com.mycompany.furniturevisualizer.ui.pages;

import com.mycompany.furniturevisualizer.Dashboard;
import com.mycompany.furniturevisualizer.ui.pages.AppearanceSettingsPage;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AppearancePage extends JPanel {

    private static final Color ORANGE = Color.decode("#F25C05");
    private static final Color BG = Color.decode("#F4F4F4");
    private static final Color CARD_BG = Color.WHITE;
    private static final Color BORDER = Color.decode("#E5E5E5");
    private static final Color TEXT_DARK = Color.decode("#333333");

    private String selectedTarget = "Room";
    private Color selectedColor = Color.decode("#F5F5DC");
    private String selectedMaterial = "Smooth";
    private String selectedShading = "Realistic";

    private JLabel targetValue;
    private JLabel colorValue;
    private JLabel materialValue;
    private JLabel shadingValue;

    private JPanel previewPanel;

    public AppearancePage(Dashboard dashboard) {

        setLayout(new BorderLayout());
        setBackground(BG);

        JPanel left = createLeftSide();
        JPanel right = createRightSide();

        JScrollPane leftScroll = new JScrollPane(left);
        leftScroll.setBorder(null);
        leftScroll.getVerticalScrollBar().setUnitIncrement(16);
        leftScroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        right.setMaximumSize(new Dimension(
                right.getPreferredSize().width,
                right.getPreferredSize().height
        ));

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        container.setBackground(BG);
        container.setBorder(new EmptyBorder(30, 40, 30, 40));

        container.add(leftScroll);
        container.add(Box.createRigidArea(new Dimension(30, 0)));
        container.add(right);

        add(container, BorderLayout.CENTER);
    }

    // ================= LEFT SIDE =================

    private JPanel createLeftSide() {

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(BG);

        JPanel target = createTargetCard();
        JPanel color = createColorCard();
        JPanel material = createMaterialCard();
        JPanel shading = createShadingCard();
        JPanel actions = createActionButtons();

        target.setAlignmentX(Component.LEFT_ALIGNMENT);
        color.setAlignmentX(Component.LEFT_ALIGNMENT);
        material.setAlignmentX(Component.LEFT_ALIGNMENT);
        shading.setAlignmentX(Component.LEFT_ALIGNMENT);
        actions.setAlignmentX(Component.LEFT_ALIGNMENT);

        left.add(target);
        left.add(Box.createRigidArea(new Dimension(0, 20)));

        left.add(color);
        left.add(Box.createRigidArea(new Dimension(0, 20)));

        left.add(material);
        left.add(Box.createRigidArea(new Dimension(0, 30))); // gap between material & shading

        left.add(shading);
        left.add(Box.createRigidArea(new Dimension(0, 25)));

        left.add(actions);

        return left;
    }

    private JPanel createCard(String title) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_BG);
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setBorder(new CompoundBorder(
                new LineBorder(BORDER, 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setForeground(TEXT_DARK);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(lbl);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        return card;
    }

    // ================= TARGET =================

    private JPanel createTargetCard() {

        JPanel card = createCard("Select Target");

        JToggleButton roomBtn = createBigToggle("Entire Room", true);
        JToggleButton itemBtn = createBigToggle("Selected Item", false);

        ButtonGroup group = new ButtonGroup();
        group.add(roomBtn);
        group.add(itemBtn);

        roomBtn.addActionListener(e -> {
            selectedTarget = "Room";
            updateSettings();
        });

        itemBtn.addActionListener(e -> {
            selectedTarget = "Selected Item";
            updateSettings();
        });

        JPanel grid = new JPanel(new GridLayout(1, 2, 15, 0));
        grid.setBackground(CARD_BG);
        grid.add(roomBtn);
        grid.add(itemBtn);

        card.add(grid);
        return card;
    }

    private JToggleButton createBigToggle(String text, boolean selected) {

        JToggleButton btn = new JToggleButton(text, selected);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(200, 80));

        styleToggle(btn);
        return btn;
    }

    private void styleToggle(JToggleButton btn) {
        btn.addChangeListener(e -> {
            if (btn.isSelected()) {
                btn.setBorder(new LineBorder(ORANGE, 2, true));
                btn.setBackground(new Color(255, 240, 230));
            } else {
                btn.setBorder(new LineBorder(BORDER, 1, true));
                btn.setBackground(Color.WHITE);
            }
        });
    }

    // ================= COLOR =================

    private JPanel createColorCard() {

        JPanel card = createCard("Color");

        JButton customColor = new JButton();
        customColor.setBackground(selectedColor);
        customColor.setPreferredSize(new Dimension(60, 40));

        JTextField hexField = new JTextField("#F5F5DC");
        hexField.setPreferredSize(new Dimension(150, 40));

        customColor.addActionListener(e -> {
            Color c = JColorChooser.showDialog(this, "Pick Color", selectedColor);
            if (c != null) {
                selectedColor = c;
                customColor.setBackground(c);
                hexField.setText("#" + Integer.toHexString(c.getRGB()).substring(2).toUpperCase());
                updateSettings();
                previewPanel.repaint();
            }
        });

        JPanel customRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        customRow.setBackground(CARD_BG);
        customRow.add(customColor);
        customRow.add(hexField);

        card.add(new JLabel("Custom Color"));
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(customRow);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(new JLabel("Preset Colors"));
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel presets = new JPanel(new GridLayout(2, 6, 10, 10));
        presets.setBackground(CARD_BG);

        Color[] presetColors = {
                Color.decode("#F5F5DC"), Color.LIGHT_GRAY, Color.GRAY,
                Color.decode("#C2A477"), Color.decode("#8B6F47"), Color.decode("#9C7B5F"),
                Color.decode("#5C4033"), Color.decode("#4A5568"), Color.decode("#2D3748"),
                Color.decode("#1A202C"), Color.decode("#C05656"), Color.decode("#718096")
        };

        for (Color c : presetColors) {
            JPanel box = new JPanel();
            box.setBackground(c);
            box.setBorder(new LineBorder(BORDER, 1, true));
            box.setCursor(new Cursor(Cursor.HAND_CURSOR));
            box.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    selectedColor = c;
                    customColor.setBackground(c);
                    previewPanel.repaint();
                    updateSettings();
                }
            });
            presets.add(box);
        }

        card.add(presets);
        return card;
    }

    // ================= MATERIAL =================

    private JPanel createMaterialCard() {

        JPanel card = createCard("Material");

        JPanel grid = new JPanel(new GridLayout(3, 2, 15, 15));
        grid.setBackground(CARD_BG);

        grid.add(createMaterialBox("Wood", new Color(139, 69, 19)));
        grid.add(createMaterialBox("Metal", Color.GRAY));
        grid.add(createMaterialBox("Fabric", new Color(210, 180, 140)));
        grid.add(createMaterialBox("Leather", new Color(92, 64, 51)));
        grid.add(createMaterialBox("Glass", Color.CYAN));
        grid.add(createMaterialBox("Smooth", new Color(232, 232, 232)));

        card.add(grid);
        return card;
    }

    private JPanel createMaterialBox(String name, Color color) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_BG);
        panel.setBorder(new LineBorder(BORDER, 1, true));
        panel.setPreferredSize(new Dimension(200, 100));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel top = new JPanel();
        top.setBackground(color);
        top.setPreferredSize(new Dimension(200, 60));

        JLabel lbl = new JLabel(name, SwingConstants.CENTER);

        panel.add(top, BorderLayout.NORTH);
        panel.add(lbl, BorderLayout.CENTER);

        panel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                selectedMaterial = name;
                updateSettings();
            }
        });

        return panel;
    }

    // ================= SHADING =================

    private JPanel createShadingCard() {

        JPanel card = createCard("Shading Style");

        card.add(createShadingOption("Flat", "No shading, solid colors"));
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(createShadingOption("Smooth", "Smooth gradient shading"));
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(createShadingOption("Realistic", "Advanced lighting and shadows"));

        return card;
    }

    private JPanel createShadingOption(String title, String description) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_BG);
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBorder(new CompoundBorder(
                new LineBorder(BORDER, 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel descLbl = new JLabel(description);
        descLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLbl.setForeground(Color.GRAY);

        panel.add(titleLbl);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(descLbl);

        panel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                selectedShading = title;
                updateSettings();
                highlightSelectedShading(panel);
            }
        });

        return panel;
    }

    private void highlightSelectedShading(JPanel selectedPanel) {

        Container parent = selectedPanel.getParent();

        if (parent != null) {
            for (Component comp : parent.getComponents()) {
                if (comp instanceof JPanel) {
                    JPanel panel = (JPanel) comp;
                    panel.setBackground(CARD_BG);
                    panel.setBorder(new CompoundBorder(
                            new LineBorder(BORDER, 1, true),
                            new EmptyBorder(15, 15, 15, 15)
                    ));
                }
            }
        }

        selectedPanel.setBackground(new Color(255, 240, 230));
        selectedPanel.setBorder(new CompoundBorder(
                new LineBorder(ORANGE, 2, true),
                new EmptyBorder(15, 15, 15, 15)
        ));
    }

    // ================= RIGHT SIDE =================

    private JPanel createRightSide() {

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBackground(BG);

        right.add(createPreviewCard());
        right.add(Box.createRigidArea(new Dimension(0, 20)));
        right.add(createSettingsCard());

        return right;
    }

    private JPanel createPreviewCard() {

        JPanel card = createCard("Preview");

        previewPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(selectedColor);
                g.fillRoundRect(180, 90, 120, 100, 20, 20);
            }
        };

        previewPanel.setPreferredSize(new Dimension(450, 260));
        previewPanel.setBackground(CARD_BG);
        previewPanel.setBorder(new LineBorder(BORDER, 1, true));

        card.add(previewPanel);
        return card;
    }

    private JPanel createSettingsCard() {

        JPanel card = createCard("Current Settings");

        targetValue = new JLabel(selectedTarget);
        colorValue = new JLabel("#F5F5DC");
        materialValue = new JLabel(selectedMaterial);
        shadingValue = new JLabel(selectedShading);

        card.add(createSettingRow("Target:", targetValue));
        card.add(createSettingRow("Color:", colorValue));
        card.add(createSettingRow("Material:", materialValue));
        card.add(createSettingRow("Shading:", shadingValue));

        return card;
    }

    private JPanel createSettingRow(String label, JLabel value) {

        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(CARD_BG);
        row.setBorder(new EmptyBorder(5, 0, 5, 0));

        row.add(new JLabel(label), BorderLayout.WEST);
        row.add(value, BorderLayout.EAST);

        return row;
    }

    private JPanel createActionButtons() {

        JPanel panel = new JPanel(new GridLayout(1, 2, 15, 0));
        panel.setBackground(BG);

        JButton previewBtn = new JButton("Preview Changes");
        previewBtn.addActionListener(e -> previewPanel.repaint());

        JButton applyBtn = new JButton("✓ Apply");
        applyBtn.setBackground(ORANGE);
        applyBtn.setForeground(Color.WHITE);

        applyBtn.addActionListener(e -> applyChanges());

        panel.add(previewBtn);
        panel.add(applyBtn);

        return panel;
    }

    private void updateSettings() {
        targetValue.setText(selectedTarget);
        colorValue.setText("#" + Integer.toHexString(selectedColor.getRGB()).substring(2).toUpperCase());
        materialValue.setText(selectedMaterial);
        shadingValue.setText(selectedShading);
    }

    private void applyChanges() {
        System.out.println("Appearance Applied:");
        System.out.println("Target: " + selectedTarget);
        System.out.println("Color: " + selectedColor);
        System.out.println("Material: " + selectedMaterial);
        System.out.println("Shading: " + selectedShading);
    }

    public AppearanceSettingsPage getCurrentSettings() {
        return new AppearanceSettingsPage(
                selectedTarget,
                selectedColor,
                selectedMaterial,
                selectedShading
        );
    }
}

/* in 3D page
* Appearance appearancePage = new Appearance(dashboard);

AppearanceSettings settings = appearancePage.getCurrentSettings();

Color color = settings.getColor();
String shading = settings.getShading(); */