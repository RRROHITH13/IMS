package IMS;

import DB.DB;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterPage extends JFrame {
    public RegisterPage() {
        setTitle("Register Page");
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
        JLabel headerLabel = new JLabel("Register New Account");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerPanel.add(headerLabel);

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2, 10, 10)); // 4 rows, 2 columns with gaps
        formPanel.setBackground(new Color(255, 255, 255));

        JLabel lblUsername = new JLabel("New Username:");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField txtUsername = new JTextField();

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField txtEmail = new JTextField();

        JLabel lblPassword = new JLabel("New Password:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JPasswordField txtPassword = new JPasswordField();

        JButton btnRegister = new JButton("Register");
        btnRegister.setBackground(new Color(0, 102, 102));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        btnRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add components to the form panel
        formPanel.add(lblUsername);
        formPanel.add(txtUsername);
        formPanel.add(lblEmail);
        formPanel.add(txtEmail);
        formPanel.add(lblPassword);
        formPanel.add(txtPassword);
        formPanel.add(btnRegister);

        // Add action listener for the register button
        btnRegister.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String email = txtEmail.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (registerUser (username, email, password)) {
                JOptionPane.showMessageDialog(this, "Registration Successful");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration Failed");
            }
        });

        // Add panels to the main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Add main panel to the frame
        add(mainPanel);
        setVisible(true);
    }

    private boolean registerUser (String username, String email, String password) {  //store the table  user in ims database
        String hashedPassword = PasswordUtil.hashPassword(password);
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, email, password) VALUES (?, ?, ?)")) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, hashedPassword);
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());   //if there were any  errors
        }
        return false;
    }
}