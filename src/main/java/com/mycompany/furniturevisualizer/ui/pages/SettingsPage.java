package com.mycompany.furniturevisualizer.ui.pages;

import com.mycompany.furniturevisualizer.Dashboard;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SettingsPage extends JPanel {

    private static final Color ORANGE_PRIMARY = Color.decode("#F25C05");
    private static final Color ORANGE_LIGHT = Color.decode("#FFF3E0");
    private static final Color TEXT_DARK = Color.decode("#333333");
    private static final Color TEXT_LIGHT = Color.decode("#666666");
    private static final Color BG_BODY = Color.decode("#F6F6F6");
    private static final Color BORDER_COLOR = Color.decode("#E0E0E0");

    private static final int CONTENT_MAX_WIDTH = 1040;

    public SettingsPage(Dashboard dashboard) {
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

        JLabel back = new JLabel("← Back to Dashboard");
        back.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        back.setForeground(TEXT_LIGHT);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dashboard.showCard(Dashboard.CARD_HOME);
            }
        });

        JLabel title = new JLabel("Settings & Help", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(TEXT_DARK);

        top.add(back, BorderLayout.WEST);
        top.add(title, BorderLayout.CENTER);
        // Keep title visually centered even with the back link on the left.
        top.add(Box.createHorizontalStrut(back.getPreferredSize().width), BorderLayout.EAST);

        return top;
    }

    private JComponent createScrollableContent() {
        JPanel body = new JPanel();
        body.setOpaque(true);
        body.setBackground(BG_BODY);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBorder(new EmptyBorder(28, 0, 60, 0));

        body.add(centered(createProfileCard()));
        body.add(Box.createRigidArea(new Dimension(0, 22)));
        body.add(centered(createChangePasswordCard()));
        body.add(Box.createRigidArea(new Dimension(0, 22)));
        body.add(centered(createApplicationSettingsCard()));
        body.add(Box.createRigidArea(new Dimension(0, 22)));
        body.add(centered(createHelpAndSupportCard()));
        body.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(body);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(BG_BODY);
        return scrollPane;
    }

    private JComponent centered(JComponent component) {
        JPanel wrap = new JPanel();
        wrap.setOpaque(false);
        wrap.setLayout(new BoxLayout(wrap, BoxLayout.X_AXIS));
        wrap.add(Box.createHorizontalGlue());

        component.setMaximumSize(new Dimension(CONTENT_MAX_WIDTH, Integer.MAX_VALUE));
        component.setPreferredSize(new Dimension(CONTENT_MAX_WIDTH, component.getPreferredSize().height));
        wrap.add(component);

        wrap.add(Box.createHorizontalGlue());
        return wrap;
    }

    private ShadowCard createCardPanel() {
        ShadowCard card = new ShadowCard(16, 12);
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(0, 0, 0, 0));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.getContent().setLayout(new BoxLayout(card.getContent(), BoxLayout.Y_AXIS));
        card.getContent().setBorder(new EmptyBorder(26, 30, 26, 30));
        return card;
    }

    private JPanel createProfileCard() {
        ShadowCard shell = createCardPanel();
        JPanel card = shell.getContent();

        card.add(createSectionHeader("👤", "Profile Settings"));
        card.add(Box.createRigidArea(new Dimension(0, 18)));

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 14, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel fullNameLabel = fieldLabel("Full Name");
        form.add(fullNameLabel, gbc);

        gbc.gridy++;
        JTextField fullNameField = textField("Demo Designer");
        fullNameField.setPreferredSize(new Dimension(10, 40));
        form.add(fullNameField, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 0, 0);
        form.add(Box.createVerticalStrut(6), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 14, 0);
        JLabel emailLabel = fieldLabel("Email");
        form.add(emailLabel, gbc);

        gbc.gridy++;
        JTextField emailField = textField("demo@designer.com");
        emailField.setPreferredSize(new Dimension(10, 40));
        form.add(emailField, gbc);

        card.add(form);
        card.add(Box.createRigidArea(new Dimension(0, 4)));

        PrimaryButton saveBtn = new PrimaryButton("💾  Save Profile");
        saveBtn.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "Profile saved (demo only).",
                "Profile",
                JOptionPane.INFORMATION_MESSAGE
        ));

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonRow.setOpaque(false);
        buttonRow.add(saveBtn);

        card.add(buttonRow);

        return shell;
    }

    private JPanel createChangePasswordCard() {
        ShadowCard shell = createCardPanel();
        JPanel card = shell.getContent();

        JLabel title = new JLabel("Change Password");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(TEXT_DARK);
        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 18)));

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 14, 0);

        // Current Password - full width
        JLabel currentPwdLabel = fieldLabel("Current Password");
        form.add(currentPwdLabel, gbc);

        gbc.gridy++;
        JPasswordField currentPwdField = new JPasswordField();
        currentPwdField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        currentPwdField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(10, 12, 10, 12)
        ));
        currentPwdField.setPreferredSize(new Dimension(10, 40));
        form.add(currentPwdField, gbc);

        gbc.gridy++;
        form.add(Box.createVerticalStrut(6), gbc);

        // New Password and Confirm New Password - side by side
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 14, 0);
        
        JPanel passwordRow = new JPanel(new GridLayout(1, 2, 16, 0));
        passwordRow.setOpaque(false);

        JPanel newPwdPanel = new JPanel();
        newPwdPanel.setOpaque(false);
        newPwdPanel.setLayout(new BoxLayout(newPwdPanel, BoxLayout.Y_AXIS));
        JLabel newPwdLabel = fieldLabel("New Password");
        JPasswordField newPwdField = new JPasswordField();
        newPwdField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        newPwdField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(10, 12, 10, 12)
        ));
        newPwdField.setPreferredSize(new Dimension(10, 40));
        newPwdPanel.add(newPwdLabel);
        newPwdPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        newPwdPanel.add(newPwdField);

        JPanel confirmPwdPanel = new JPanel();
        confirmPwdPanel.setOpaque(false);
        confirmPwdPanel.setLayout(new BoxLayout(confirmPwdPanel, BoxLayout.Y_AXIS));
        JLabel confirmPwdLabel = fieldLabel("Confirm New Password");
        JPasswordField confirmPwdField = new JPasswordField();
        confirmPwdField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        confirmPwdField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(10, 12, 10, 12)
        ));
        confirmPwdField.setPreferredSize(new Dimension(10, 40));
        confirmPwdPanel.add(confirmPwdLabel);
        confirmPwdPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        confirmPwdPanel.add(confirmPwdField);

        passwordRow.add(newPwdPanel);
        passwordRow.add(confirmPwdPanel);
        form.add(passwordRow, gbc);

        card.add(form);
        card.add(Box.createRigidArea(new Dimension(0, 14)));

        JButton changeBtn = new JButton("Change Password");
        changeBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        changeBtn.setForeground(Color.WHITE);
        changeBtn.setBackground(Color.decode("#3C332B"));
        changeBtn.setFocusPainted(false);
        changeBtn.setContentAreaFilled(true);
        changeBtn.setBorder(new EmptyBorder(12, 28, 12, 28));
        changeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        changeBtn.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "Password change logic can be implemented here.",
                "Change Password",
                JOptionPane.INFORMATION_MESSAGE
        ));

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonRow.setOpaque(false);
        buttonRow.add(changeBtn);

        card.add(buttonRow);

        return shell;
    }

    private JPanel createApplicationSettingsCard() {
        ShadowCard shell = createCardPanel();
        JPanel card = shell.getContent();

        JLabel title = new JLabel("Application Settings");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(TEXT_DARK);
        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 20)));

        // Theme
        JPanel themeSection = new JPanel();
        themeSection.setOpaque(false);
        themeSection.setLayout(new BoxLayout(themeSection, BoxLayout.Y_AXIS));

        JLabel themeLabel = new JLabel("🌞 Theme");
        themeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        themeLabel.setForeground(TEXT_DARK);
        themeSection.add(themeLabel);
        themeSection.add(Box.createRigidArea(new Dimension(0, 10)));

        JToggleButton lightBtn = createSegmentToggle("☀ Light Mode", true);
        JToggleButton darkBtn = createSegmentToggle("🌙 Dark Mode", false);
        ButtonGroup themeGroup = new ButtonGroup();
        themeGroup.add(lightBtn);
        themeGroup.add(darkBtn);

        JPanel themeRow = new JPanel(new GridLayout(1, 2, 12, 0));
        themeRow.setOpaque(false);
        themeRow.add(lightBtn);
        themeRow.add(darkBtn);
        themeSection.add(themeRow);

        card.add(themeSection);
        card.add(Box.createRigidArea(new Dimension(0, 22)));

        // Measurement unit
        JPanel unitSection = new JPanel();
        unitSection.setOpaque(false);
        unitSection.setLayout(new BoxLayout(unitSection, BoxLayout.Y_AXIS));

        JLabel unitLabel = new JLabel("📏 Default Measurement Unit");
        unitLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        unitLabel.setForeground(TEXT_DARK);
        unitSection.add(unitLabel);
        unitSection.add(Box.createRigidArea(new Dimension(0, 10)));

        JToggleButton metersBtn = createSegmentToggle("Meters (m)", true);
        JToggleButton feetBtn = createSegmentToggle("Feet (ft)", false);
        ButtonGroup unitGroup = new ButtonGroup();
        unitGroup.add(metersBtn);
        unitGroup.add(feetBtn);

        JPanel unitRow = new JPanel(new GridLayout(1, 2, 12, 0));
        unitRow.setOpaque(false);
        unitRow.add(metersBtn);
        unitRow.add(feetBtn);
        unitSection.add(unitRow);

        card.add(unitSection);
        card.add(Box.createRigidArea(new Dimension(0, 22)));

        // Default room template
        JPanel templateSection = new JPanel();
        templateSection.setOpaque(false);
        templateSection.setLayout(new BoxLayout(templateSection, BoxLayout.Y_AXIS));

        JLabel templateLabel = new JLabel("Default Room Template");
        templateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        templateLabel.setForeground(TEXT_DARK);
        templateSection.add(templateLabel);
        templateSection.add(Box.createRigidArea(new Dimension(0, 10)));

        JComboBox<String> templateCombo = new JComboBox<>(new String[]{
                "Blank Room",
                "Modern Living Room",
                "Cozy Bedroom",
                "Minimal Office"
        });
        templateCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        templateCombo.setBorder(new LineBorder(BORDER_COLOR, 1, true));
        templateCombo.setPreferredSize(new Dimension(10, 40));
        templateCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        templateSection.add(templateCombo);

        card.add(templateSection);
        card.add(Box.createRigidArea(new Dimension(0, 18)));

        JButton saveBtn = createPrimaryButton("💾 Save Settings");
        saveBtn.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "Settings saved (demo only).",
                "Settings",
                JOptionPane.INFORMATION_MESSAGE
        ));

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonRow.setOpaque(false);
        buttonRow.add(saveBtn);
        card.add(buttonRow);

        return shell;
    }

    private JPanel createHelpAndSupportCard() {
        ShadowCard shell = createCardPanel();
        JPanel card = shell.getContent();

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        header.setOpaque(false);

        JPanel iconCircle = new JPanel();
        iconCircle.setBackground(new Color(219, 234, 255));
        iconCircle.setPreferredSize(new Dimension(36, 36));
        iconCircle.setMaximumSize(new Dimension(36, 36));
        iconCircle.setBorder(new LineBorder(new Color(52, 144, 220), 1, true));
        iconCircle.setLayout(new BorderLayout());
        JLabel icon = new JLabel("?", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI", Font.BOLD, 16));
        icon.setForeground(new Color(52, 144, 220));
        iconCircle.add(icon, BorderLayout.CENTER);

        JLabel title = new JLabel("Help & Support");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(TEXT_DARK);

        header.add(iconCircle);
        header.add(title);

        card.add(header);
        card.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel faqTitle = new JLabel("Frequently Asked Questions");
        faqTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        faqTitle.setForeground(TEXT_DARK);
        card.add(faqTitle);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        card.add(createFaqItem(
                "How do I create a new room design?",
                "Click 'Create New Design' from the dashboard, enter room specifications, then use the 2D editor to arrange furniture.",
                false
        ));
        card.add(Box.createRigidArea(new Dimension(0, 8)));

        card.add(createFaqItem(
                "How do I use the 2D layout editor?",
                "Select furniture items from the left panel and place them onto the canvas. Use the properties panel to rotate, move, or delete items.",
                false
        ));
        card.add(Box.createRigidArea(new Dimension(0, 8)));

        card.add(createFaqItem(
                "How do I view my design in 3D?",
                "From the 2D editor, click the 'View in 3D' button to open the 3D preview once it is implemented.",
                false
        ));
        card.add(Box.createRigidArea(new Dimension(0, 8)));

        card.add(createFaqItem(
                "Can I save multiple designs?",
                "Yes, you will be able to save each room as a separate project and reopen it from your portfolio (to be implemented).",
                false
        ));

        card.add(Box.createRigidArea(new Dimension(0, 16)));
        card.add(createSupportStrip());
        card.add(Box.createRigidArea(new Dimension(0, 12)));
        card.add(createQuickTipsStrip());

        return shell;
    }

    private JPanel createFaqItem(String question, String answer, boolean expandedInitially) {
        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBackground(Color.WHITE);
        root.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(12, 14, 12, 14)
        ));
        root.setAlignmentX(Component.LEFT_ALIGNMENT);
        root.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel icon = new JLabel(expandedInitially ? "▼" : "▶");
        icon.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        icon.setForeground(TEXT_LIGHT);

        JLabel qLabel = new JLabel(question);
        qLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        qLabel.setForeground(TEXT_DARK);

        JPanel headerRow = new JPanel(new BorderLayout(8, 0));
        headerRow.setOpaque(false);
        headerRow.add(icon, BorderLayout.WEST);
        headerRow.add(qLabel, BorderLayout.CENTER);

        root.add(headerRow);

        JLabel answerLabel = new JLabel("<html><span style='color:#666666;'>" + answer + "</span></html>");
        answerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        answerLabel.setBorder(new EmptyBorder(8, 20, 0, 0));
        answerLabel.setVisible(expandedInitially);
        root.add(answerLabel);

        MouseAdapter toggleListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean visible = !answerLabel.isVisible();
                answerLabel.setVisible(visible);
                icon.setText(visible ? "▼" : "▶");
                root.revalidate();
                root.repaint();
            }
        };

        headerRow.addMouseListener(toggleListener);
        qLabel.addMouseListener(toggleListener);
        icon.addMouseListener(toggleListener);
        root.addMouseListener(toggleListener);

        return root;
    }

    private JPanel createSupportStrip() {
        JPanel strip = new JPanel(new BorderLayout(16, 0));
        strip.setBackground(new Color(255, 248, 235));
        strip.setBorder(new CompoundBorder(
                new LineBorder(new Color(255, 215, 143), 2, true),
                new EmptyBorder(18, 18, 18, 18)
        ));
        strip.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel textCol = new JPanel();
        textCol.setOpaque(false);
        textCol.setLayout(new BoxLayout(textCol, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Need More Help?");
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title.setForeground(TEXT_DARK);

        JLabel desc = new JLabel("Our support team is here to help you with any questions or issues.");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        desc.setForeground(TEXT_LIGHT);

        textCol.add(title);
        textCol.add(Box.createRigidArea(new Dimension(0, 6)));
        textCol.add(desc);

        JButton contactBtn = createPrimaryButton("✉ Contact Support");
        contactBtn.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "Support contact can be integrated here (e.g. email or link).",
                "Contact Support",
                JOptionPane.INFORMATION_MESSAGE
        ));

        strip.add(textCol, BorderLayout.CENTER);
        strip.add(contactBtn, BorderLayout.EAST);
        return strip;
    }

    private JPanel createQuickTipsStrip() {
        JPanel strip = new JPanel();
        strip.setLayout(new BoxLayout(strip, BoxLayout.Y_AXIS));
        strip.setBackground(new Color(230, 242, 255));
        strip.setBorder(new CompoundBorder(
                new LineBorder(new Color(187, 222, 251), 2, true),
                new EmptyBorder(16, 18, 16, 18)
        ));
        strip.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel("💡 Quick Tips");
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title.setForeground(TEXT_DARK);

        JLabel tips = new JLabel(
                "<html>" +
                        "<ul style='margin-top:6px;margin-bottom:0;padding-left:16px;'>" +
                        "<li>Use the grid snap feature in 2D editor for precise furniture placement</li>" +
                        "<li>Try different lighting presets in 3D view to show customers various ambiances</li>" +
                        "<li>Duplicate existing designs to quickly create variations</li>" +
                        "<li>Save your work regularly while consulting with customers</li>" +
                        "</ul>" +
                        "</html>"
        );
        tips.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tips.setForeground(TEXT_DARK);

        strip.add(title);
        strip.add(tips);
        return strip;
    }

    private JTextField createLabeledTextField(JPanel parent, String label, String placeholder) {
        JLabel l = fieldLabel(label);
        JTextField tf = textField(placeholder);

        parent.add(l);
        parent.add(Box.createRigidArea(new Dimension(0, 6)));
        parent.add(tf);
        parent.add(Box.createRigidArea(new Dimension(0, 14)));

        return tf;
    }

    private JPasswordField createLabeledPasswordField(JPanel parent, String label) {
        JLabel l = fieldLabel(label);

        JPasswordField pf = new JPasswordField();
        pf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pf.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(10, 12, 10, 12)
        ));
        pf.setPreferredSize(new Dimension(10, 40));

        parent.add(l);
        parent.add(Box.createRigidArea(new Dimension(0, 6)));
        parent.add(pf);

        return pf;
    }

    private JButton createPrimaryButton(String text) {
        return new PrimaryButton(text);
    }

    private JToggleButton createSegmentToggle(String text, boolean selected) {
        JToggleButton btn = new JToggleButton(text, selected);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new LineBorder(BORDER_COLOR, 1, true));
        btn.setBackground(selected ? new Color(255, 240, 220) : Color.WHITE);

        btn.addChangeListener(e -> {
            if (btn.isSelected()) {
                btn.setBackground(new Color(255, 240, 220));
                btn.setBorder(new LineBorder(ORANGE_PRIMARY, 2, true));
            } else {
                btn.setBackground(Color.WHITE);
                btn.setBorder(new LineBorder(BORDER_COLOR, 1, true));
            }
        });
        return btn;
    }

    private JLabel fieldLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        l.setForeground(TEXT_DARK);
        return l;
    }

    private JTextField textField(String value) {
        JTextField tf = new JTextField(value);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(10, 12, 10, 12)
        ));
        tf.setPreferredSize(new Dimension(10, 40));
        return tf;
    }

    private JComponent createSectionHeader(String iconText, String titleText) {
        JPanel row = new JPanel();
        row.setOpaque(false);
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));

        RoundedPanel badge = new RoundedPanel(10);
        badge.setBackground(ORANGE_LIGHT);
        badge.setPreferredSize(new Dimension(34, 34));
        badge.setMaximumSize(new Dimension(34, 34));
        badge.setLayout(new BorderLayout());
        JLabel icon = new JLabel(iconText, SwingConstants.CENTER);
        icon.setForeground(Color.decode("#D97706"));
        badge.add(icon, BorderLayout.CENTER);

        JLabel title = new JLabel(titleText);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(TEXT_DARK);

        row.add(badge);
        row.add(Box.createRigidArea(new Dimension(12, 0)));
        row.add(title);
        row.add(Box.createHorizontalGlue());
        return row;
    }

    private static final class PrimaryButton extends JButton {
        private PrimaryButton(String text) {
            super(text);
            setFont(new Font("Segoe UI", Font.BOLD, 13));
            setForeground(Color.WHITE);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setBorder(new EmptyBorder(10, 18, 10, 18));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int arc = 12;
                int w = getWidth();
                int h = getHeight();

                // Shadow
                g2.setColor(new Color(242, 92, 5, 55));
                g2.fillRoundRect(2, 4, w - 4, h - 4, arc, arc);

                // Button
                GradientPaint gp = new GradientPaint(0, 0, Color.decode("#F25C05"), 0, h, Color.decode("#E35A08"));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, w - 4, h - 4, arc, arc);

                super.paintComponent(g);
            } finally {
                g2.dispose();
            }
        }
    }

    private static final class RoundedPanel extends JPanel {
        private final int radius;

        private RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            } finally {
                g2.dispose();
            }
            super.paintComponent(g);
        }
    }

    private static final class ShadowCard extends JPanel {
        private final int radius;
        private final int shadowSize;
        private final JPanel content;

        private ShadowCard(int radius, int shadowSize) {
            this.radius = radius;
            this.shadowSize = shadowSize;
            setOpaque(false);
            setLayout(new BorderLayout());

            content = new JPanel();
            content.setOpaque(false);
            content.setLayout(new BorderLayout());
            add(content, BorderLayout.CENTER);

            setBorder(new EmptyBorder(shadowSize, shadowSize, shadowSize, shadowSize));
        }

        public JPanel getContent() {
            return content;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int x = shadowSize;
                int y = shadowSize;
                int w = getWidth() - shadowSize * 2;
                int h = getHeight() - shadowSize * 2;

                // Shadow (soft)
                for (int i = 0; i < shadowSize; i++) {
                    float alpha = (shadowSize - i) / (float) shadowSize;
                    alpha *= 0.08f;
                    g2.setColor(new Color(0, 0, 0, Math.round(alpha * 255)));
                    g2.drawRoundRect(x - i, y - i + 2, w + i * 2, h + i * 2, radius + i * 2, radius + i * 2);
                }

                // Card background
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(x, y, w, h, radius, radius);

                // Subtle border
                g2.setColor(Color.decode("#EFEFEF"));
                g2.drawRoundRect(x, y, w, h, radius, radius);
            } finally {
                g2.dispose();
            }
            super.paintComponent(g);
        }
    }
}

