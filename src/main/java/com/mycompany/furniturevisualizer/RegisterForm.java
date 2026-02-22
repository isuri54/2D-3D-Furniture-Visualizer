package com.mycompany.furniturevisualizer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class RegisterForm extends JFrame {

    // Figma Colors
    private final Color ORANGE_PRIMARY = Color.decode("#FF6D00");
    private final Color TEXT_DARK = Color.decode("#333333");
    private final Color TEXT_LIGHT = Color.decode("#888888");
    private final Color BORDER_GRAY = Color.decode("#E0E0E0");
    private final Color REQ_BOX_BG = Color.decode("#F9F9F9");

    public RegisterForm() {
        this(null);
    }

    public RegisterForm(Frame parent) {
        super("Create Designer Account");
        if (parent != null) {
            setLocationRelativeTo(parent);
        } else {
            setLocationRelativeTo(null);
        }
        setTitle("Create Designer Account");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 880);
        setMinimumSize(new Dimension(400, 600));

        getContentPane().setBackground(Color.WHITE);

        final int formWidth = 420;
        JPanel container = new JPanel();
        container.setBackground(Color.WHITE);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(new EmptyBorder(40, 50, 40, 50));
        container.setMaximumSize(new Dimension(formWidth, Short.MAX_VALUE));
        container.setPreferredSize(new Dimension(formWidth, 700));

        // --- 1. Header Section ---
        JLabel logoPlaceholder;
        java.net.URL imgURL = getClass().getResource("/com/mycompany/furniturevisualizer/icons8-furniture-50.png");

        if (imgURL != null) {
            logoPlaceholder = new JLabel(new ImageIcon(imgURL));
        } else {
            logoPlaceholder = new JLabel("FURNITURE APP");
            logoPlaceholder.setFont(new Font("Segoe UI", Font.BOLD, 18));
            logoPlaceholder.setForeground(ORANGE_PRIMARY);
        }
        logoPlaceholder.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoPlaceholder.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel title = new JLabel("Create Designer Account");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(TEXT_DARK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitle = new JLabel("Join our platform to create stunning room designs");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(TEXT_LIGHT);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);

        // --- 2. Profile Picture Section ---
        JPanel profileContainer = new JPanel(null);
        profileContainer.setBackground(Color.WHITE);
        profileContainer.setPreferredSize(new Dimension(120, 130));
        profileContainer.setMaximumSize(new Dimension(120, 130));

        JPanel circle = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.decode("#F0F0F0"));
                g2d.fill(new Ellipse2D.Double(0, 0, 100, 100));
                g2d.setColor(BORDER_GRAY);
                g2d.draw(new Ellipse2D.Double(0, 0, 99, 99));
            }
        };
        circle.setBounds(10, 0, 100, 100);

        JButton uploadBtn = new JButton("↑") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ORANGE_PRIMARY);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
                g2.setColor(Color.WHITE);
                g2.setFont(getFont().deriveFont(16f));
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth("↑")) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString("↑", x, y);
            }
        };
        uploadBtn.setBounds(75, 70, 32, 32);
        uploadBtn.setContentAreaFilled(false);
        uploadBtn.setBorderPainted(false);
        uploadBtn.setFocusPainted(false);
        uploadBtn.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.WHITE, 2),
                new EmptyBorder(0, 0, 0, 0)));
        uploadBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        uploadBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        uploadBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(RegisterForm.this) == JFileChooser.APPROVE_OPTION) {
                // Profile image selection - can be wired later
                JOptionPane.showMessageDialog(RegisterForm.this, "Profile image selected.");
            }
        });

        profileContainer.add(uploadBtn);
        profileContainer.add(circle);
        profileContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- 3. Form Fields (fixed width formWidth) ---
        JPanel row1 = createSplitRow("Full Name *", "Email *", formWidth);
        JPanel row2 = createField("Password *", true, formWidth);
        JPanel row3 = createField("Confirm Password *", true, formWidth);

        // --- 4. Register Button (custom paint so text/background always show on all L&F) ---
        JButton registerBtn = new JButton("Create Account") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ORANGE_PRIMARY);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                String text = getText();
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(text, x, y);
            }
        };
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerBtn.setContentAreaFilled(false);
        registerBtn.setBorderPainted(false);
        registerBtn.setFocusPainted(false);
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setPreferredSize(new Dimension(220, 44));
        registerBtn.setMinimumSize(new Dimension(220, 44));
        registerBtn.setMaximumSize(new Dimension(320, 44));
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(RegisterForm.this, "Account creation can be wired here.");
        });

        // --- 5. Login link ---
        JLabel loginLink = new JLabel("Already have an account? Login");
        loginLink.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        loginLink.setForeground(ORANGE_PRIMARY);
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> new LoginForm2().setVisible(true));
            }
        });

        // --- Assemble ---
        container.add(Box.createVerticalStrut(10));
        container.add(logoPlaceholder);
        container.add(Box.createVerticalStrut(12));
        container.add(title);
        container.add(Box.createVerticalStrut(6));
        container.add(subtitle);
        container.add(Box.createVerticalStrut(24));
        container.add(profileContainer);
        container.add(Box.createVerticalStrut(28));
        container.add(row1);
        container.add(Box.createVerticalStrut(16));
        container.add(row2);
        container.add(Box.createVerticalStrut(16));
        container.add(row3);
        container.add(Box.createVerticalStrut(28));
        container.add(registerBtn);
        container.add(Box.createVerticalStrut(20));
        container.add(loginLink);

        JScrollPane scroll = new JScrollPane(container);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scroll);
    }

    private static final int FIELD_HEIGHT = 40;
    private static final int GAP = 10;

    private JPanel createSplitRow(String label1, String label2, int formWidth) {
        int half = (formWidth - GAP) / 2;
        JPanel row = new JPanel(new GridLayout(1, 2, GAP, 0));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(formWidth, 76));
        row.setPreferredSize(new Dimension(formWidth, 76));

        JPanel left = createLabelAndField(label1, half);
        JPanel right = createLabelAndField(label2, half);
        row.add(left);
        row.add(right);
        return row;
    }

    private JPanel createLabelAndField(String label, int fieldWidth) {
        JPanel p = new JPanel(new BorderLayout(0, 6));
        p.setBackground(Color.WHITE);
        JLabel l = new JLabel(label);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        l.setForeground(TEXT_DARK);
        JTextField tf = new JTextField(20);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setPreferredSize(new Dimension(fieldWidth, FIELD_HEIGHT));
        tf.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_GRAY, 1),
                new EmptyBorder(8, 12, 8, 12)));
        tf.setBackground(REQ_BOX_BG);
        p.add(l, BorderLayout.NORTH);
        p.add(tf, BorderLayout.CENTER);
        return p;
    }

    private JPanel createField(String label, boolean isPassword, int formWidth) {
        JPanel p = new JPanel(new BorderLayout(0, 6));
        p.setBackground(Color.WHITE);
        p.setMaximumSize(new Dimension(formWidth, 76));
        p.setPreferredSize(new Dimension(formWidth, 76));
        JLabel l = new JLabel(label);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        l.setForeground(TEXT_DARK);
        JComponent field = isPassword ? new JPasswordField(20) : new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(formWidth, FIELD_HEIGHT));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_GRAY, 1),
                new EmptyBorder(8, 12, 8, 12)));
        field.setOpaque(true);
        field.setBackground(REQ_BOX_BG);
        p.add(l, BorderLayout.NORTH);
        p.add(field, BorderLayout.CENTER);
        return p;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegisterForm form = new RegisterForm();
            form.setVisible(true);
        });
    }
}
