package IMS;

import DB.DB;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ChangePasswordPage extends JFrame {
    public ChangePasswordPage() {
        setTitle("Change Password Page");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);

        // Main panel with a border layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(255, 255, 255));

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 102));
        JLabel headerLabel = new JLabel("Change Password");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerPanel.add(headerLabel);

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 2, 10, 10)); // 3 rows, 2 columns with gaps
        formPanel.setBackground(new Color(255, 255, 255));

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField txtEmail = new JTextField();

        JLabel lblNewPassword = new JLabel("New Password:");
        lblNewPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JPasswordField txtNewPassword = new JPasswordField();

        JButton btnChangePassword = new JButton("Change Password");
        btnChangePassword.setBackground(new Color(0, 102, 102));
        btnChangePassword.setForeground(Color.WHITE);
        btnChangePassword.setFocusPainted(false);
        btnChangePassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add components to the form panel
        formPanel.add(lblEmail);
        formPanel.add(txtEmail);
        formPanel.add(lblNewPassword);
        formPanel.add(txtNewPassword);
        formPanel.add(btnChangePassword);

        // Add action listener for the change password button
        btnChangePassword.addActionListener(e -> {
            String email = txtEmail.getText().trim();
            String newPassword = new String(txtNewPassword.getPassword()).trim();

            if (email.isEmpty() || newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (resetPassword(email, newPassword)) {
                JOptionPane.showMessageDialog(this, "Password Changed Successfully");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Password Change Failed");
            }
        });

        // Add panels to the main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Add main panel to the frame
        add(mainPanel);
        setVisible(true);
    }

    private boolean resetPassword(String email, String newPassword) {
        String hashedPassword = PasswordUtil.hashPassword(newPassword);
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE users SET password = ? WHERE email = ?")) {
            stmt.setString(1, hashedPassword);
            stmt.setString(2, email);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
        return false;
    }
}