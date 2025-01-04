package IMS;

import DB.DB;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginPage extends JFrame {
    public LoginPage() {
        setTitle("Login Page");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);

        // Main panel with a border layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(255, 255, 255));

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 102));
        JLabel headerLabel = new JLabel("Welcome to IMS");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerPanel.add(headerLabel);

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2, 10, 10)); // 4 rows, 2 columns with gaps
        formPanel.setBackground(new Color(255, 255, 255));

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField txtUsername = new JTextField();
        
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JPasswordField txtPassword = new JPasswordField();

        JButton btnLogin = new JButton("Login");
        btnLogin.setBackground(new Color(0, 102, 102));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogin.setIcon(new ImageIcon("path/to/login_icon.png")); // Add your icon path here
        btnLogin.setHorizontalTextPosition(SwingConstants.RIGHT); // Position text to the right of the icon

        JButton btnRegister = new JButton("Register");
        btnRegister.setIcon(new ImageIcon("path/to/register_icon.png")); // Add your icon path here
        btnRegister.setHorizontalTextPosition(SwingConstants.RIGHT);

        JButton btnForgotPassword = new JButton("Forgot Password");
        btnForgotPassword.setIcon(new ImageIcon("path/to/forgot_password_icon.png")); // Add your icon path here
        btnForgotPassword.setHorizontalTextPosition(SwingConstants.RIGHT);

        // Add components to the form panel
        formPanel.add(lblUsername);
        formPanel.add(txtUsername);
        formPanel.add(lblPassword);
        formPanel.add(txtPassword);
        formPanel.add(btnLogin);
        formPanel.add(btnRegister);
        formPanel.add(btnForgotPassword);

        // Add action listeners
        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();

            // Input validation
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (authenticateUser (username, password)) {
                JOptionPane.showMessageDialog(this, "Login Successful");
                new Home().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials");
            }
        });

        btnRegister.addActionListener(e -> new RegisterPage());
        btnForgotPassword.addActionListener(e -> new ChangePasswordPage());

        // Add panels to the main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Add main panel to the frame
        add(mainPanel);
        setVisible(true);
    }

    private boolean authenticateUser (String username, String password) {
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT password FROM users WHERE username = ?")) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                return PasswordUtil.checkPassword(password, hashedPassword);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginPage::new);  //invoke  later until that it waits in queue
    }
}