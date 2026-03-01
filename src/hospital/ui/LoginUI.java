package hospital.ui;

import hospital.dao.UserDAO;
import hospital.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginUI extends JFrame {

    // username field
    private JTextField usernameField;
    // password field
    private JPasswordField passwordField;
    // login button
    private JButton loginButton;

    // UserDAO object
    private UserDAO userDAO;

    // constructor
    public LoginUI() {
        userDAO = new UserDAO();

        setTitle("Hospital Management System - Login");
        setSize(400, 250); // =================================Currently hard coded===============
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {
        // panel to add all fields and button

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        panel.add(new JLabel("Username: "));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password: "));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        panel.add(new JLabel()); // empty space
        panel.add(loginButton);

        add(panel, BorderLayout.CENTER);

        loginButton.addActionListener(this::handleLogin);
    }

    private void handleLogin(ActionEvent e) {
        // username , password, JOptionPane
        String userName = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (userName.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        // getting username and password through UserDAO
        User user = userDAO.authenticate(userName, password);

        if (user == null) {
            JOptionPane.showMessageDialog(this, "Invalid credentials", "Login failed. ", JOptionPane.ERROR_MESSAGE);
        }

        // successful login -> close login window and opems dashboard
        JOptionPane.showMessageDialog(this, "Welcome " + user.getUsername());

        dispose();

        new DashboardUI(user);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginUI::new);
    }
}
