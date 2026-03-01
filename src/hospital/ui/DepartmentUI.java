package hospital.ui;

import hospital.dao.DepartmentDAO;
import hospital.model.Department;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DepartmentUI extends JFrame {

    private JTextField nameField;
    private JTextArea displayArea;
    private DepartmentDAO departmentDAO;

    public DepartmentUI() {
        departmentDAO = new DepartmentDAO();

        setTitle("Department Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        initializeUI();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void initializeUI() {

        // Top panel (Add department)
        JPanel topPanel = new JPanel(new FlowLayout(10, 10, 20));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        topPanel.add(new JLabel("Department Name: "));
        nameField = new JTextField(10);
        topPanel.add(nameField);

        JButton addBtn = new JButton("Add");
        JButton deleteBtn = new JButton("Delete");

        topPanel.add(addBtn);
        topPanel.add(deleteBtn);

        add(topPanel, BorderLayout.NORTH);

        // center panel(Display area)
        displayArea = new JTextArea(10, 20);
        displayArea.setEditable(false);

        // bottom Panel
        JButton viewBtn = new JButton("View All");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(new JScrollPane(displayArea));
        bottomPanel.add(viewBtn);
        add(bottomPanel);

        // button actions
        addBtn.addActionListener(e -> addDepartment());
        deleteBtn.addActionListener(e -> deleteDepartment());
        viewBtn.addActionListener(e -> viewDepartments());

    }

    private void addDepartment() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Department name cannot be empty.");
        }

        Department dept = new Department();
        dept.setDeptName(name);

        boolean success = departmentDAO.addDepartment(dept);
        if (success) {
            JOptionPane.showMessageDialog(this, "Department added successfully.");
            nameField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add Department.");
        }
    }

    private void deleteDepartment() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Department name to delete.");
        }

        Department dept = new Department();
        dept.setDeptName(name);

        boolean success = departmentDAO.deleteDepartment(dept);
        if (success) {
            JOptionPane.showMessageDialog(this, "Department deleted.");
            nameField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Department not found.");
        }
    }

    private void viewDepartments() {
        List<Department> list = departmentDAO.getAllDepartments();

        displayArea.setText("");

        // show list
        for (Department dept : list) {
            displayArea.append("   ID: " + dept.getDeptId() + " | Name: " + dept.getDeptName() + "\n");
        }
    }

    public static void main(String[] args) {
        new DepartmentUI();
    }

}