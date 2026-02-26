package com.mycompany.furniturevisualizer.ui.pages;

import com.mycompany.furniturevisualizer.Dashboard;
import com.mycompany.furniturevisualizer.model.FurnitureItem;
import com.mycompany.furniturevisualizer.model.FurnitureManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class ManageFurniturePage extends JPanel {

    private static final Color ORANGE_PRIMARY = Color.decode("#F25C05");
    private static final Color TEXT_DARK = Color.decode("#333333");
    private static final Color TEXT_LIGHT = Color.decode("#666666");
    private static final Color BG_BODY = Color.decode("#F5F7FA");
    private static final Color BORDER_COLOR = Color.decode("#E0E0E0");

    private final Dashboard dashboard;
    private JTable furnitureTable;
    private DefaultTableModel tableModel;

    public ManageFurniturePage(Dashboard dashboard) {
        this.dashboard = dashboard;
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        add(createHeader(), BorderLayout.NORTH);
        add(createBodyPanel(), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(20, 40, 20, 40));

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

        JLabel title = new JLabel("Manage Furniture", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(TEXT_DARK);

        header.add(back, BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);

        return header;
    }

    private JPanel createBodyPanel() {
        JPanel body = new JPanel(new BorderLayout(20, 20));
        body.setBackground(BG_BODY);
        body.setBorder(new EmptyBorder(20, 40, 40, 40));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BG_BODY);

        JLabel subtitle = new JLabel("Add and manage furniture items for your shop");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(TEXT_LIGHT);

        JButton addBtn = new JButton("+ Add New Furniture");
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addBtn.setBackground(ORANGE_PRIMARY);
        addBtn.setForeground(Color.WHITE);
        addBtn.setBorderPainted(false);
        addBtn.setFocusPainted(false);
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addBtn.setPreferredSize(new Dimension(180, 40));
        addBtn.addActionListener(e -> showAddFurnitureDialog());

        topPanel.add(subtitle, BorderLayout.WEST);
        topPanel.add(addBtn, BorderLayout.EAST);

        body.add(topPanel, BorderLayout.NORTH);
        body.add(createTablePanel(), BorderLayout.CENTER);

        return body;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new LineBorder(BORDER_COLOR, 1));

        String[] columns = {"Image", "Name", "Category", "Price", "Actions"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        furnitureTable = new JTable(tableModel);
        furnitureTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        furnitureTable.setRowHeight(60);
        furnitureTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        furnitureTable.getTableHeader().setBackground(BG_BODY);
        furnitureTable.getTableHeader().setForeground(TEXT_DARK);
        furnitureTable.setSelectionBackground(new Color(255, 240, 220));
        furnitureTable.setGridColor(BORDER_COLOR);

        furnitureTable.getColumn("Image").setPreferredWidth(80);
        furnitureTable.getColumn("Image").setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            JLabel label = new JLabel();
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setBackground(isSelected ? new Color(255, 240, 220) : Color.WHITE);
            label.setOpaque(true);

            if (value != null && !value.toString().isEmpty()) {
                try {
                    ImageIcon icon = new ImageIcon(value.toString());
                    Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(img));
                } catch (Exception e) {
                    label.setText("🖼");
                    label.setFont(new Font("Segoe UI", Font.PLAIN, 24));
                }
            } else {
                label.setText("🖼");
                label.setFont(new Font("Segoe UI", Font.PLAIN, 24));
            }

            return label;
        });

        furnitureTable.getColumn("Actions").setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
            actionPanel.setBackground(isSelected ? new Color(255, 240, 220) : Color.WHITE);

            JButton editBtn = new JButton("✎ Edit");
            editBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            editBtn.setBackground(Color.WHITE);
            editBtn.setForeground(ORANGE_PRIMARY);
            editBtn.setBorder(new LineBorder(ORANGE_PRIMARY, 1));
            editBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            editBtn.setFocusPainted(false);

            JButton deleteBtn = new JButton("🗑 Delete");
            deleteBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            deleteBtn.setBackground(Color.WHITE);
            deleteBtn.setForeground(Color.RED);
            deleteBtn.setBorder(new LineBorder(Color.RED, 1));
            deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            deleteBtn.setFocusPainted(false);

            actionPanel.add(editBtn);
            actionPanel.add(deleteBtn);

            return actionPanel;
        });

        furnitureTable.getColumn("Actions").setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            private JPanel panel;

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
                panel.setBackground(Color.WHITE);

                JButton editBtn = new JButton("✎ Edit");
                editBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                editBtn.setBackground(Color.WHITE);
                editBtn.setForeground(ORANGE_PRIMARY);
                editBtn.setBorder(new LineBorder(ORANGE_PRIMARY, 1));
                editBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                editBtn.setFocusPainted(false);
                editBtn.addActionListener(e -> editFurniture(row));

                JButton deleteBtn = new JButton("🗑 Delete");
                deleteBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                deleteBtn.setBackground(Color.WHITE);
                deleteBtn.setForeground(Color.RED);
                deleteBtn.setBorder(new LineBorder(Color.RED, 1));
                deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                deleteBtn.setFocusPainted(false);
                deleteBtn.addActionListener(e -> deleteFurniture(row));

                panel.add(editBtn);
                panel.add(deleteBtn);

                return panel;
            }

            @Override
            public Object getCellEditorValue() {
                return "";
            }
        });

        loadFurnitureData();

        JScrollPane scrollPane = new JScrollPane(furnitureTable);
        scrollPane.setBorder(null);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void loadFurnitureData() {
        tableModel.setRowCount(0);
        for (FurnitureItem item : FurnitureManager.getInstance().getAllFurniture()) {
            tableModel.addRow(new Object[]{
                item.getImagePath(),
                item.getName(),
                item.getCategory(),
                "$" + String.format("%.2f", item.getPrice()),
                ""
            });
        }
    }

    private void showAddFurnitureDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add New Furniture", true);
        dialog.setSize(550, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(30, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Furniture Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);

        JTextField nameField = new JTextField(20);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        nameField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(nameField, gbc);

        JLabel priceLabel = new JLabel("Price ($):");
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(priceLabel, gbc);

        JTextField priceField = new JTextField(20);
        priceField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        priceField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(priceField, gbc);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(categoryLabel, gbc);

        JComboBox<String> categoryCombo = new JComboBox<>(new String[]{
            "Sofas", "Chairs", "Tables", "Beds", "Desks", "Other"
        });
        categoryCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(categoryCombo, gbc);

        JLabel imageLabel = new JLabel("Image:");
        imageLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(imageLabel, gbc);

        JPanel imagePanel = new JPanel(new BorderLayout(10, 0));
        imagePanel.setBackground(Color.WHITE);

        JLabel imagePathLabel = new JLabel("No image selected");
        imagePathLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        imagePathLabel.setForeground(TEXT_LIGHT);

        JButton browseBtn = new JButton("Browse...");
        browseBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        browseBtn.setBackground(Color.WHITE);
        browseBtn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            new EmptyBorder(6, 15, 6, 15)
        ));
        browseBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        browseBtn.setFocusPainted(false);

        final String[] selectedImagePath = {null};

        browseBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Furniture Image");
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Image Files", "jpg", "jpeg", "png", "gif", "bmp");
            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(dialog);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedImagePath[0] = selectedFile.getAbsolutePath();
                imagePathLabel.setText(selectedFile.getName());
                imagePathLabel.setForeground(ORANGE_PRIMARY);
            }
        });

        imagePanel.add(imagePathLabel, BorderLayout.CENTER);
        imagePanel.add(browseBtn, BorderLayout.EAST);

        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(imagePanel, gbc);

        JLabel previewLabel = new JLabel();
        previewLabel.setPreferredSize(new Dimension(150, 100));
        previewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        previewLabel.setBorder(new LineBorder(BORDER_COLOR, 1));
        previewLabel.setBackground(BG_BODY);
        previewLabel.setOpaque(true);
        previewLabel.setText("Preview");
        previewLabel.setForeground(TEXT_LIGHT);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 10, 12, 10);
        formPanel.add(previewLabel, gbc);

        browseBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Furniture Image");
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Image Files", "jpg", "jpeg", "png", "gif", "bmp");
            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(dialog);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedImagePath[0] = selectedFile.getAbsolutePath();
                imagePathLabel.setText(selectedFile.getName());
                imagePathLabel.setForeground(ORANGE_PRIMARY);

                try {
                    ImageIcon icon = new ImageIcon(selectedImagePath[0]);
                    Image img = icon.getImage().getScaledInstance(140, 90, Image.SCALE_SMOOTH);
                    previewLabel.setIcon(new ImageIcon(img));
                    previewLabel.setText("");
                } catch (Exception ex) {
                    previewLabel.setText("Preview failed");
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        buttonPanel.setBackground(Color.WHITE);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cancelBtn.setBackground(Color.WHITE);
        cancelBtn.setForeground(TEXT_DARK);
        cancelBtn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            new EmptyBorder(10, 20, 10, 20)
        ));
        cancelBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelBtn.setFocusPainted(false);
        cancelBtn.addActionListener(e -> dialog.dispose());

        JButton addBtn = new JButton("Add Furniture");
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addBtn.setBackground(ORANGE_PRIMARY);
        addBtn.setForeground(Color.WHITE);
        addBtn.setBorder(new EmptyBorder(10, 25, 10, 25));
        addBtn.setBorderPainted(false);
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addBtn.setFocusPainted(false);
        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String priceText = priceField.getText().trim();
            String category = (String) categoryCombo.getSelectedItem();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please enter furniture name", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double price = Double.parseDouble(priceText);
                if (price < 0) {
                    JOptionPane.showMessageDialog(dialog, "Price must be positive", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                FurnitureItem item = new FurnitureItem(name, price, category, selectedImagePath[0]);
                FurnitureManager.getInstance().addFurniture(item);
                loadFurnitureData();
                JOptionPane.showMessageDialog(dialog, "Furniture added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid price", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(cancelBtn);
        buttonPanel.add(addBtn);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void editFurniture(int row) {
        String imagePath = (String) tableModel.getValueAt(row, 0);
        String name = (String) tableModel.getValueAt(row, 1);
        String category = (String) tableModel.getValueAt(row, 2);
        String priceStr = (String) tableModel.getValueAt(row, 3);
        double price = Double.parseDouble(priceStr.replace("$", ""));

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Furniture", true);
        dialog.setSize(550, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(30, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Furniture Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);

        JTextField nameField = new JTextField(name, 20);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        nameField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(nameField, gbc);

        JLabel priceLabel = new JLabel("Price ($):");
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(priceLabel, gbc);

        JTextField priceField = new JTextField(String.valueOf(price), 20);
        priceField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        priceField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(priceField, gbc);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(categoryLabel, gbc);

        JComboBox<String> categoryCombo = new JComboBox<>(new String[]{
            "Sofas", "Chairs", "Tables", "Beds", "Desks", "Other"
        });
        categoryCombo.setSelectedItem(category);
        categoryCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(categoryCombo, gbc);

        JLabel imageLabel = new JLabel("Image:");
        imageLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(imageLabel, gbc);

        JPanel imagePanel = new JPanel(new BorderLayout(10, 0));
        imagePanel.setBackground(Color.WHITE);

        JLabel imagePathLabel = new JLabel(imagePath != null && !imagePath.isEmpty() ? 
            new File(imagePath).getName() : "No image selected");
        imagePathLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        imagePathLabel.setForeground(imagePath != null && !imagePath.isEmpty() ? ORANGE_PRIMARY : TEXT_LIGHT);

        JButton browseBtn = new JButton("Browse...");
        browseBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        browseBtn.setBackground(Color.WHITE);
        browseBtn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            new EmptyBorder(6, 15, 6, 15)
        ));
        browseBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        browseBtn.setFocusPainted(false);

        final String[] selectedImagePath = {imagePath};

        imagePanel.add(imagePathLabel, BorderLayout.CENTER);
        imagePanel.add(browseBtn, BorderLayout.EAST);

        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(imagePanel, gbc);

        JLabel previewLabel = new JLabel();
        previewLabel.setPreferredSize(new Dimension(150, 100));
        previewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        previewLabel.setBorder(new LineBorder(BORDER_COLOR, 1));
        previewLabel.setBackground(BG_BODY);
        previewLabel.setOpaque(true);

        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                ImageIcon icon = new ImageIcon(imagePath);
                Image img = icon.getImage().getScaledInstance(140, 90, Image.SCALE_SMOOTH);
                previewLabel.setIcon(new ImageIcon(img));
            } catch (Exception e) {
                previewLabel.setText("Preview failed");
                previewLabel.setForeground(TEXT_LIGHT);
            }
        } else {
            previewLabel.setText("Preview");
            previewLabel.setForeground(TEXT_LIGHT);
        }

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 10, 12, 10);
        formPanel.add(previewLabel, gbc);

        browseBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Furniture Image");
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Image Files", "jpg", "jpeg", "png", "gif", "bmp");
            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(dialog);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedImagePath[0] = selectedFile.getAbsolutePath();
                imagePathLabel.setText(selectedFile.getName());
                imagePathLabel.setForeground(ORANGE_PRIMARY);

                try {
                    ImageIcon icon = new ImageIcon(selectedImagePath[0]);
                    Image img = icon.getImage().getScaledInstance(140, 90, Image.SCALE_SMOOTH);
                    previewLabel.setIcon(new ImageIcon(img));
                    previewLabel.setText("");
                } catch (Exception ex) {
                    previewLabel.setText("Preview failed");
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        buttonPanel.setBackground(Color.WHITE);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cancelBtn.setBackground(Color.WHITE);
        cancelBtn.setForeground(TEXT_DARK);
        cancelBtn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            new EmptyBorder(10, 20, 10, 20)
        ));
        cancelBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelBtn.setFocusPainted(false);
        cancelBtn.addActionListener(e -> dialog.dispose());

        JButton saveBtn = new JButton("Save Changes");
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveBtn.setBackground(ORANGE_PRIMARY);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setBorder(new EmptyBorder(10, 25, 10, 25));
        saveBtn.setBorderPainted(false);
        saveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveBtn.setFocusPainted(false);
        saveBtn.addActionListener(e -> {
            String newName = nameField.getText().trim();
            String priceText = priceField.getText().trim();
            String newCategory = (String) categoryCombo.getSelectedItem();

            if (newName.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please enter furniture name", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double newPrice = Double.parseDouble(priceText);
                if (newPrice < 0) {
                    JOptionPane.showMessageDialog(dialog, "Price must be positive", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                FurnitureItem oldItem = FurnitureManager.getInstance().getAllFurniture().get(row);
                FurnitureManager.getInstance().removeFurniture(oldItem);
                FurnitureItem newItem = new FurnitureItem(newName, newPrice, newCategory, selectedImagePath[0]);
                FurnitureManager.getInstance().addFurniture(newItem);
                loadFurnitureData();
                JOptionPane.showMessageDialog(dialog, "Furniture updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid price", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(cancelBtn);
        buttonPanel.add(saveBtn);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void deleteFurniture(int row) {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this furniture item?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            FurnitureItem item = FurnitureManager.getInstance().getAllFurniture().get(row);
            FurnitureManager.getInstance().removeFurniture(item);
            loadFurnitureData();
            JOptionPane.showMessageDialog(this, "Furniture deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
