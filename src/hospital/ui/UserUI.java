package hospital.ui;

import hospital.dao.UserDAO;
import hospital.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserUI extends JFrame {

    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleCombo;

    private JTable userTable;
    private DefaultTableModel tableModel;

    private UserDAO userDAO;

    public UserUI() {

        userDAO = new UserDAO();

        setTitle("User Management");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        mainPanel.add(createFormPanel(), BorderLayout.NORTH);
        mainPanel.add(createTablePanel(), BorderLayout.CENTER);

        add(mainPanel);
    }

    // FORM PANEL
    private JPanel createFormPanel() {

        JPanel formPanel = new JPanel(new GridLayout(2, 4, 15, 10));

        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Role:"));
        roleCombo = new JComboBox<>(new String[] {
                "ADMIN", "DOCTOR", "RECEPTIONIST"
        });
        formPanel.add(roleCombo);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton addBtn = new JButton("Add User");
        JButton deleteBtn = new JButton("Delete Selected");

        buttonPanel.add(addBtn);
        buttonPanel.add(deleteBtn);

        JPanel container = new JPanel(new BorderLayout());
        container.add(formPanel, BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.SOUTH);

        // Listeners
        addBtn.addActionListener(e -> addUser());
        deleteBtn.addActionListener(e -> deleteUser());

        return container;
    }

    // TABLE PANEL
    private JScrollPane createTablePanel() {

        String[] columns = { "ID", "Username", "Email", "Role" };

        tableModel = new DefaultTableModel(columns, 0);
        userTable = new JTable(tableModel);
        userTable.setRowHeight(25);

        loadUsers();

        return new JScrollPane(userTable);
    }

    private void loadUsers() {

        tableModel.setRowCount(0);

        List<User> users = userDAO.getAllUsers();

        for (User user : users) {
            tableModel.addRow(new Object[] {
                    user.getUserId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole()
            });
        }
    }

    private void addUser() {

        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String role = (String) roleCombo.getSelectedItem();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        boolean success = userDAO.addUser(user);

        if (success) {
            JOptionPane.showMessageDialog(this, "User added successfully.");
            clearFields();
            loadUsers();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add user.");
        }
    }

    private void deleteUser() {

        int selectedRow = userTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a user to delete.");
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this user?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION)
            return;

        User user = new User();
        user.setUserId(userId);

        boolean success = userDAO.deleteUser(user);

        if (success) {
            JOptionPane.showMessageDialog(this, "User deleted.");
            loadUsers();
        } else {
            JOptionPane.showMessageDialog(this, "Delete failed.");
        }
    }

    private void clearFields() {
        usernameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        roleCombo.setSelectedIndex(0);
    }

    // public static void main(String[] args) {
    // new UserUI();
    // }

}