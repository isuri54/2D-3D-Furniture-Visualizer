package com.mycompany.furniturevisualizer.ui.pages;

import com.mycompany.furniturevisualizer.RegisterForm;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends JPanel {

    private static final Color ORANGE_PRIMARY = Color.decode("#FF6D00");
    private static final Color TEXT_DARK = Color.decode("#333333");

    public LoginPage() {
        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        JLabel title = new JLabel("Furniture Visualizer");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(TEXT_DARK);
        gbc.gridy = 0;
        add(title, gbc);

        JButton createAccountBtn = new JButton("Create Designer Account");
        createAccountBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        createAccountBtn.setForeground(ORANGE_PRIMARY);
        createAccountBtn.setBackground(Color.WHITE);
        createAccountBtn.setBorderPainted(false);
        createAccountBtn.setFocusPainted(false);
        createAccountBtn.setContentAreaFilled(false);
        createAccountBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createAccountBtn.addActionListener(e -> {
            Window parent = SwingUtilities.getWindowAncestor(LoginPage.this);
            RegisterForm form = new RegisterForm(parent instanceof Frame ? (Frame) parent : null);
            form.setVisible(true);
            form.toFront();
            form.requestFocus();
        });
        gbc.gridy = 1;
        add(createAccountBtn, gbc);
    }
}
