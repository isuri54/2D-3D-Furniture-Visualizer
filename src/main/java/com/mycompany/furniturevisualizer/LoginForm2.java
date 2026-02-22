package com.mycompany.furniturevisualizer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginForm2 extends JFrame {

    // Figma Colors
    private final Color ORANGE_PRIMARY = Color.decode("#FF6D00");
    private final Color TEXT_DARK = Color.decode("#333333");
    private final Color TEXT_LIGHT = Color.decode("#888888");
    private final Color BORDER_GRAY = Color.decode("#E0E0E0");

    public LoginForm2() {
        setTitle("Designer Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 750);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        // Main Container
        JPanel container = new JPanel();
        container.setBackground(Color.WHITE);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(new EmptyBorder(40, 50, 40, 50));

        // --- 1. Logo & Header ---
        JLabel logoPlaceholder;
        java.net.URL imgURL = getClass().getResource("/com/mycompany/furniturevisualizer/icons8-furniture-50.png");

        if (imgURL != null) {
            logoPlaceholder = new JLabel(new ImageIcon(imgURL));
        } else {
            logoPlaceholder = new JLabel("APP LOGO");
            logoPlaceholder.setForeground(ORANGE_PRIMARY);
        }
        logoPlaceholder.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoPlaceholder.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel title = new JLabel("Designer Login");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(TEXT_DARK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        // Subtitle text split into two lines
        JLabel sub1 = new JLabel("Help customers visualize furniture in their rooms with");
        JLabel sub2 = new JLabel("stunning 2D and 3D design tools");
        sub1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sub2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sub1.setForeground(TEXT_LIGHT);
        sub2.setForeground(TEXT_LIGHT);
        sub1.setAlignmentX(Component.CENTER_ALIGNMENT);
        sub2.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- 2. Input Fields ---
        JPanel emailPanel = createLabeledField("Email or Username", "Enter your email");
        JPanel passPanel = createPasswordField("Password");

        // --- 3. Forgot Password ---
        JPanel forgotPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        forgotPanel.setBackground(Color.WHITE);
        forgotPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 30));

        JLabel forgotLbl = new JLabel("Forgot password?");
        forgotLbl.setForeground(ORANGE_PRIMARY);
        forgotLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        forgotLbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPanel.add(forgotLbl);

        // --- 4. Login Button ---
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(ORANGE_PRIMARY);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginBtn.setMaximumSize(new Dimension(Short.MAX_VALUE, 45));
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginBtn.addActionListener(e -> {
            new Dashboard().setVisible(true);
            dispose();
        });
        loginBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { loginBtn.setBackground(ORANGE_PRIMARY.darker()); }
            @Override
            public void mouseExited(MouseEvent e) { loginBtn.setBackground(ORANGE_PRIMARY); }
        });

        // --- 5. Register Link ---
        JPanel regPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        regPanel.setBackground(Color.WHITE);

        JLabel noAcc = new JLabel("Don't have an account? ");
        noAcc.setForeground(TEXT_LIGHT);

        JLabel regLink = new JLabel("Register now");
        regLink.setForeground(ORANGE_PRIMARY);
        regLink.setFont(new Font("Segoe UI", Font.BOLD, 13));
        regLink.setCursor(new Cursor(Cursor.HAND_CURSOR));

        regLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new RegisterForm().setVisible(true);
                dispose();
            }
        });

        regPanel.add(noAcc);
        regPanel.add(regLink);

        // --- Adding to Container ---
        container.add(Box.createRigidArea(new Dimension(0, 10)));
        container.add(logoPlaceholder);
        container.add(Box.createRigidArea(new Dimension(0, 15)));
        container.add(title);
        container.add(Box.createRigidArea(new Dimension(0, 5)));
        container.add(sub1);
        container.add(sub2);
        container.add(Box.createRigidArea(new Dimension(0, 40)));
        container.add(emailPanel);
        container.add(Box.createRigidArea(new Dimension(0, 15)));
        container.add(passPanel);
        container.add(forgotPanel);
        container.add(Box.createRigidArea(new Dimension(0, 20)));
        container.add(loginBtn);
        container.add(Box.createRigidArea(new Dimension(0, 20)));
        container.add(regPanel);

        add(new JScrollPane(container));
        setVisible(true);
    }

    private JPanel createLabeledField(String label, String placeholder) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.setMaximumSize(new Dimension(Short.MAX_VALUE, 70));

        JLabel l = new JLabel(label);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setBorder(new EmptyBorder(0, 0, 5, 0));

        JTextField tf = new JTextField(placeholder, 20);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setForeground(TEXT_LIGHT);
        tf.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_GRAY, 1, true),
                new EmptyBorder(10, 10, 10, 10)
        ));

        wrapper.add(l, BorderLayout.NORTH);
        wrapper.add(tf, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel createPasswordField(String label) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.setMaximumSize(new Dimension(Short.MAX_VALUE, 70));

        JLabel l = new JLabel(label);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setBorder(new EmptyBorder(0, 0, 5, 0));

        JPanel fieldContainer = new JPanel(new BorderLayout());
        fieldContainer.setBackground(Color.WHITE);
        fieldContainer.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_GRAY, 1, true),
                new EmptyBorder(5, 10, 5, 10)
        ));

        JPasswordField pf = new JPasswordField(20);
        pf.setEchoChar('•');
        pf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pf.setBorder(null);

        JLabel eyeIcon = new JLabel("👁");
        java.net.URL eyeUrl = getClass().getResource("/com/mycompany/furniturevisualizer/icons8-eye-24.png");
        if (eyeUrl != null) {
            eyeIcon = new JLabel(new ImageIcon(eyeUrl));
        }
        eyeIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        eyeIcon.setBorder(new EmptyBorder(0, 5, 0, 0));

        fieldContainer.add(pf, BorderLayout.CENTER);
        fieldContainer.add(eyeIcon, BorderLayout.EAST);

        wrapper.add(l, BorderLayout.NORTH);
        wrapper.add(fieldContainer, BorderLayout.CENTER);
        return wrapper;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // ignore
        }
        SwingUtilities.invokeLater(() -> new LoginForm2().setVisible(true));
    }
}
