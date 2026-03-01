package hospital.ui;

import hospital.dao.DoctorDAO;
import hospital.dao.DepartmentDAO;
import hospital.model.Department;
import hospital.model.Doctor;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class DoctorUI extends JFrame {

    JTextField nameField;
    JTextField genderField;
    JTextField salaryField;
    JTextField emailField;
    JTextField phoneField;
    JComboBox<Department> deptDropdown;
    JComboBox<Doctor> drDropdown;
    JTextArea displayArea;

    DoctorDAO drDAO;
    DepartmentDAO deptDAO;

    public DoctorUI() {
        drDAO = new DoctorDAO();
        deptDAO = new DepartmentDAO();
        nameField = new JTextField(10);
        genderField = new JTextField(10);
        salaryField = new JTextField(10);
        emailField = new JTextField(10);
        phoneField = new JTextField(10);

        setTitle("Doctor Management");
        setLayout(new FlowLayout());
        initializeUI();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeUI() {

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        topPanel.add(new JLabel("Doctor Name: "));
        topPanel.add(nameField);
        topPanel.add(new JLabel("Gender: "));
        topPanel.add(genderField);
        topPanel.add(new JLabel("Salary: "));
        topPanel.add(salaryField);
        topPanel.add(new JLabel("Email: "));
        topPanel.add(emailField);
        topPanel.add(new JLabel("Phone: "));
        topPanel.add(phoneField);

        List<Department> list = deptDAO.getAllDepartments();
        deptDropdown = new JComboBox<>();
        for (Department dept : list) {
            deptDropdown.addItem(dept);
        }
        List<Doctor> list2 = drDAO.getAllDr();
        drDropdown = new JComboBox<>();
        for (Doctor doc : list2) {
            drDropdown.addItem(doc);
        }
        topPanel.add(deptDropdown);
        topPanel.add(drDropdown);

        JButton addBtn = new JButton("Add Doctor");
        JButton deleteBtn = new JButton("Delete Doctor");

        add(topPanel);

        displayArea = new JTextArea(10, 40);
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea));

        // bottom Panel
        JButton viewBtn = new JButton("View All Doctors");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(addBtn);
        bottomPanel.add(deleteBtn);
        bottomPanel.add(viewBtn);
        add(bottomPanel);

        // button actions
        addBtn.addActionListener(e -> addDoctor());
        deleteBtn.addActionListener(e -> deleteDoctor());
        viewBtn.addActionListener(e -> viewDoctors());
    }

    private void addDoctor() {
        String name = nameField.getText().trim();
        String gender = genderField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        if (name.isEmpty() || gender.isEmpty() || phone.isEmpty()
                || email.isEmpty() || salaryField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        BigDecimal salary;
        try {
            salary = new BigDecimal(salaryField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid salary format.");
            return;
        }

        Department selectedDept = (Department) deptDropdown.getSelectedItem();
        int deptId = selectedDept.getDeptId();

        Doctor dr = new Doctor();
        dr.setName(name);
        dr.setGender(gender);
        dr.setPhone(phone);
        dr.setEmail(email);
        dr.setSalary(salary);
        dr.setDeptId(deptId);

        boolean success = drDAO.addDoctor(dr);

        if (success) {
            JOptionPane.showMessageDialog(this, "Doctor added Successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add Doctor.");
        }
    }

    private void deleteDoctor() {
        Doctor dr = (Doctor) drDropdown.getSelectedItem();
        boolean success = drDAO.deleteDoctor(dr.getDoctorId());

        if (success) {
            JOptionPane.showMessageDialog(this, "Doctor deleted.");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete Doctor.");
        }

    }

    private void viewDoctors() {
        List<Doctor> list = drDAO.getAllDr();
        displayArea.setText("");

        for (Doctor doc : list) {
            displayArea.append("    ID: " + doc.getDoctorId()
                    + " | Name: " + doc.getName()
                    + " | Department ID: " + doc.getDeptId()
                    + " | Phone no.: " + doc.getPhone()
                    + "\n");
        }
    }

    public static void main(String[] args) {
        new DoctorUI();
    }
}