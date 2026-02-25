package com.mycompany.furniturevisualizer.ui.pages;

import com.mycompany.furniturevisualizer.Dashboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class RoomSetupPage extends JPanel {

    private static final Color ORANGE_PRIMARY = Color.decode("#F25C05");
    private static final Color TEXT_DARK = Color.decode("#333333");
    private static final Color TEXT_LIGHT = Color.decode("#666666");
    private static final Color BORDER_COLOR = Color.decode("#E0E0E0");
    private static final Color BG_PREVIEW = Color.decode("#F5F5DC"); // beige

    private final Dashboard dashboard;
    private JTextField lengthField;
    private JTextField widthField;
    private JTextField heightField;
    private JTextField wallColorField;
    private JPanel previewPanel;
    private JPanel colorSwatchPanel;
    private String selectedShape = "Rectangle";
    private Color currentWallColor = BG_PREVIEW;
    private JToggleButton[] shapeButtonsRef;

    public RoomSetupPage(Dashboard dashboard) {
        this.dashboard = dashboard;
        setBackground(Color.WHITE);
        setLayout(new BorderLayout(0, 0));
        setBorder(new EmptyBorder(24, 40, 24, 40));

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel back = new JLabel("← Back to Dashboard");
        back.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        back.setForeground(ORANGE_PRIMARY);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RoomSetupPage.this.dashboard.showCard(Dashboard.CARD_HOME);
            }
        });
        top.add(back, BorderLayout.WEST);

        JLabel title = new JLabel("Room Setup", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(TEXT_DARK);
        top.add(title, BorderLayout.CENTER);
        top.add(Box.createHorizontalStrut(back.getPreferredSize().width), BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        JPanel content = new JPanel(new GridLayout(1, 2, 40, 0));
        content.setBackground(Color.WHITE);

        content.add(createSpecsPanel());
        content.add(createPreviewPanel());

        add(content, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(Color.WHITE);
        JLabel help = new JLabel("?");
        help.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        help.setForeground(TEXT_LIGHT);
        help.setCursor(new Cursor(Cursor.HAND_CURSOR));
        footer.add(help);
        add(footer, BorderLayout.SOUTH);
    }

    private JPanel createSpecsPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createTitledBorder(
            new LineBorder(BORDER_COLOR, 1),
            "Room Specifications",
            0, 0,
            new Font("Segoe UI", Font.BOLD, 16),
            TEXT_DARK
        ));

        JPanel dims = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        dims.setBackground(Color.WHITE);
        dims.add(new JLabel("Length:"));
        lengthField = new JTextField("5", 4);
        lengthField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updatePreview(); }
            public void removeUpdate(DocumentEvent e) { updatePreview(); }
            public void changedUpdate(DocumentEvent e) { updatePreview(); }
        });
        dims.add(lengthField);
        dims.add(new JLabel("Width:"));
        widthField = new JTextField("4", 4);
        widthField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updatePreview(); }
            public void removeUpdate(DocumentEvent e) { updatePreview(); }
            public void changedUpdate(DocumentEvent e) { updatePreview(); }
        });
        dims.add(widthField);
        dims.add(new JLabel("Height:"));
        heightField = new JTextField("3", 4);
        dims.add(heightField);
        dims.add(new JLabel("Unit:"));
        JComboBox<String> unitCombo = new JComboBox<>(new String[] { "Meters", "Feet" });
        dims.add(unitCombo);
        p.add(dims);

        p.add(Box.createVerticalStrut(16));
        JLabel shapeLbl = new JLabel("Room Shape:");
        shapeLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(shapeLbl);
        p.add(Box.createVerticalStrut(6));

        JPanel shapePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        shapePanel.setBackground(Color.WHITE);
        shapePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        String[] shapes = { "Rectangle", "Square", "L-Shape", "U-Shape" };
        ButtonGroup shapeGroup = new ButtonGroup();
        shapeButtonsRef = new JToggleButton[shapes.length];
        for (int i = 0; i < shapes.length; i++) {
            JToggleButton b = new JToggleButton(shapes[i]);
            b.setSelected(i == 0);
            shapeButtonsRef[i] = b;
            styleShapeButton(b, shapeButtonsRef);
            shapeGroup.add(b);
            shapePanel.add(b);
        }
        refreshShapeButtons(shapeButtonsRef);
        p.add(shapePanel);

        p.add(Box.createVerticalStrut(16));
        JPanel wallPanel = new JPanel();
        wallPanel.setLayout(new BoxLayout(wallPanel, BoxLayout.Y_AXIS));
        wallPanel.setBackground(Color.WHITE);
        JPanel wallRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        wallRow.setBackground(Color.WHITE);
        wallRow.add(new JLabel("Wall Color:"));
        colorSwatchPanel = new JPanel();
        colorSwatchPanel.setBackground(currentWallColor);
        colorSwatchPanel.setPreferredSize(new Dimension(28, 28));
        colorSwatchPanel.setBorder(new LineBorder(BORDER_COLOR, 1));
        wallRow.add(colorSwatchPanel);
        wallColorField = new JTextField("#F5F5DC", 8);
        DocumentListener docSync = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { syncColorFromField(); }
            public void removeUpdate(DocumentEvent e) { syncColorFromField(); }
            public void changedUpdate(DocumentEvent e) { syncColorFromField(); }
        };
        wallColorField.getDocument().addDocumentListener(docSync);
        wallRow.add(wallColorField);
        wallPanel.add(wallRow);
        wallPanel.add(Box.createVerticalStrut(8));
        JPanel palettePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        palettePanel.setBackground(Color.WHITE);
        palettePanel.add(new JLabel("Colour palette:"));
        Color[] palette = new Color[] {
            Color.decode("#F5F5DC"), Color.decode("#FFFFFF"), Color.decode("#FFF8DC"),
            Color.decode("#FAEBD7"), Color.decode("#FFE4C4"), Color.decode("#DEB887"),
            Color.decode("#D2B48C"), Color.decode("#BC8F8F"), Color.decode("#F4A460"),
            Color.decode("#D2691E"), Color.decode("#8B4513"), Color.decode("#A0522D"),
            Color.decode("#CD853F"), Color.decode("#DAA520"), Color.decode("#B8860B")
        };
        for (Color c : palette) {
            JPanel chip = new JPanel();
            chip.setPreferredSize(new Dimension(22, 22));
            chip.setBackground(c);
            chip.setBorder(new LineBorder(TEXT_LIGHT, 1));
            chip.setCursor(new Cursor(Cursor.HAND_CURSOR));
            chip.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    setWallColor(c);
                }
            });
            palettePanel.add(chip);
        }
        JButton moreColors = new JButton("More...");
        moreColors.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        moreColors.setCursor(new Cursor(Cursor.HAND_CURSOR));
        moreColors.addActionListener(e -> {
            Color c = JColorChooser.showDialog(RoomSetupPage.this, "Choose wall colour", currentWallColor);
            if (c != null) setWallColor(c);
        });
        palettePanel.add(moreColors);
        wallPanel.add(palettePanel);
        p.add(wallPanel);

        p.add(Box.createVerticalStrut(12));
        JPanel floorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        floorPanel.setBackground(Color.WHITE);
        floorPanel.add(new JLabel("Floor Type:"));
        JComboBox<String> floorCombo = new JComboBox<>(new String[] { "Hardwood", "Tile", "Carpet", "Laminate" });
        floorPanel.add(floorCombo);
        p.add(floorPanel);

        p.add(Box.createVerticalStrut(24));
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        buttons.setBackground(Color.WHITE);
        JButton saveBtn = new JButton("💾 Save");
        saveBtn.setBackground(Color.WHITE);
        saveBtn.setForeground(TEXT_DARK);
        saveBtn.setBorder(new LineBorder(BORDER_COLOR, 1));
        saveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JButton continueBtn = new JButton("Continue to 2D Layout →");
        continueBtn.setBackground(ORANGE_PRIMARY);
        continueBtn.setForeground(Color.WHITE);
        continueBtn.setBorderPainted(false);
        continueBtn.setFocusPainted(false);
        continueBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        continueBtn.addActionListener(e -> dashboard.showCard(Dashboard.CARD_VISUALIZATION));
        buttons.add(saveBtn);
        buttons.add(continueBtn);
        p.add(buttons);

        return p;
    }

    private void styleShapeButton(JToggleButton b, JToggleButton[] all) {
        b.setPreferredSize(new Dimension(120, 36));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        b.setContentAreaFilled(true);
        b.setBorderPainted(true);
        b.addItemListener(e -> {
            for (JToggleButton btn : all) {
                if (btn.isSelected()) {
                    selectedShape = btn.getText();
                    break;
                }
            }
            refreshShapeButtons(all);
            updatePreview();
        });
    }

    private void refreshShapeButtons(JToggleButton[] all) {
        for (JToggleButton b : all) {
            b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(b.isSelected() ? ORANGE_PRIMARY : BORDER_COLOR, 2),
                new EmptyBorder(4, 8, 4, 8)
            ));
            b.setBackground(b.isSelected() ? new Color(255, 230, 210) : Color.WHITE);
        }
    }

    private void setWallColor(Color c) {
        currentWallColor = c;
        wallColorField.setText(String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue()));
        if (colorSwatchPanel != null) colorSwatchPanel.setBackground(c);
        updatePreview();
    }

    private void syncColorFromField() {
        try {
            String hex = wallColorField.getText().trim();
            if (hex.startsWith("#")) hex = hex.substring(1);
            if (hex.length() == 6) {
                currentWallColor = Color.decode("#" + hex);
                if (colorSwatchPanel != null) colorSwatchPanel.setBackground(currentWallColor);
                updatePreview();
            }
        } catch (Exception ignored) { }
    }

    private int getRoomLength() {
        try { return Math.max(1, Integer.parseInt(lengthField.getText().trim())); } catch (Exception e) { return 5; }
    }

    private int getRoomWidth() {
        try { return Math.max(1, Integer.parseInt(widthField.getText().trim())); } catch (Exception e) { return 4; }
    }

    private JPanel createPreviewPanel() {
        JPanel p = new JPanel(new BorderLayout(12, 12));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createTitledBorder(
            new LineBorder(BORDER_COLOR, 1),
            "Room Preview",
            0, 0,
            new Font("Segoe UI", Font.BOLD, 16),
            TEXT_DARK
        ));

        previewPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int w = getWidth();
                int h = getHeight();
                int len = getRoomLength();
                int wid = getRoomWidth();
                String shape = selectedShape != null ? selectedShape : "Rectangle";
                Color fillColor = currentWallColor != null ? currentWallColor : BG_PREVIEW;

                g.setColor(Color.WHITE);
                g.fillRect(0, 0, w, h);
                g.setColor(BORDER_COLOR);
                g.drawRect(0, 0, w - 1, h - 1);

                int pad = 20;
                int drawW = w - 2 * pad;
                int drawH = h - 2 * pad;
                if (drawW <= 0 || drawH <= 0) return;

                double scaleX = (double) drawW / Math.max(len, 1);
                double scaleY = (double) drawH / Math.max(wid, 1);
                double scale = Math.min(scaleX, scaleY);
                int roomPxW = (int) (len * scale);
                int roomPxH = (int) (wid * scale);
                int x0 = pad + (drawW - roomPxW) / 2;
                int y0 = pad + (drawH - roomPxH) / 2;

                g.setColor(fillColor);
                g.fillRect(x0, y0, roomPxW, roomPxH);
                g.setColor(BORDER_COLOR);

                switch (shape) {
                    case "Square": {
                        int s = Math.min(roomPxW, roomPxH);
                        g.setColor(Color.WHITE);
                        g.fillRect(x0, y0, roomPxW, roomPxH);
                        g.setColor(fillColor);
                        g.fillRect(x0 + (roomPxW - s) / 2, y0 + (roomPxH - s) / 2, s, s);
                        g.setColor(BORDER_COLOR);
                        g.drawRect(x0 + (roomPxW - s) / 2, y0 + (roomPxH - s) / 2, s, s);
                        break;
                    }
                    case "L-Shape": {
                        g.setColor(Color.WHITE);
                        g.fillRect(x0, y0, roomPxW, roomPxH);
                        int halfW = roomPxW / 2;
                        int halfH = roomPxH / 2;
                        g.setColor(fillColor);
                        g.fillRect(x0, y0, roomPxW, halfH);
                        g.fillRect(x0, y0 + halfH, halfW, halfH);
                        g.setColor(BORDER_COLOR);
                        g.drawRect(x0, y0, roomPxW, halfH);
                        g.drawRect(x0, y0 + halfH, halfW, halfH);
                        g.drawLine(x0 + halfW, y0 + halfH, x0 + halfW, y0 + roomPxH);
                        g.drawLine(x0 + halfW, y0 + halfH, x0 + roomPxW, y0 + halfH);
                        break;
                    }
                    case "U-Shape": {
                        g.setColor(Color.WHITE);
                        g.fillRect(x0, y0, roomPxW, roomPxH);
                        int thirdW = roomPxW / 3;
                        int thirdH = roomPxH / 3;
                        g.setColor(fillColor);
                        g.fillRect(x0, y0, roomPxW, thirdH);
                        g.fillRect(x0, y0 + thirdH, thirdW, 2 * thirdH);
                        g.fillRect(x0 + 2 * thirdW, y0 + thirdH, thirdW, 2 * thirdH);
                        g.fillRect(x0, y0 + 2 * thirdH, roomPxW, thirdH);
                        g.setColor(BORDER_COLOR);
                        g.drawRect(x0, y0, roomPxW, thirdH);
                        g.drawRect(x0, y0 + thirdH, thirdW, 2 * thirdH);
                        g.drawRect(x0 + 2 * thirdW, y0 + thirdH, thirdW, 2 * thirdH);
                        g.drawRect(x0, y0 + 2 * thirdH, roomPxW, thirdH);
                        break;
                    }
                    default: // Rectangle
                        g.drawRect(x0, y0, roomPxW, roomPxH);
                        int cell = Math.min(roomPxW / Math.max(1, len), roomPxH / Math.max(1, wid));
                        if (cell > 4) {
                            for (int i = 1; i < len; i++) g.drawLine(x0 + i * cell, y0, x0 + i * cell, y0 + roomPxH);
                            for (int i = 1; i < wid; i++) g.drawLine(x0, y0 + i * cell, x0 + roomPxW, y0 + i * cell);
                        }
                        break;
                }
            }
        };
        previewPanel.setBackground(Color.WHITE);
        previewPanel.setPreferredSize(new Dimension(400, 280));

        p.add(previewPanel, BorderLayout.CENTER);

        JLabel hint = new JLabel("This is a top-down view of your room. The grid lines represent meters.");
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        hint.setForeground(TEXT_LIGHT);
        p.add(hint, BorderLayout.SOUTH);

        return p;
    }

    private void updatePreview() {
        if (previewPanel != null) previewPanel.repaint();
    }
}
