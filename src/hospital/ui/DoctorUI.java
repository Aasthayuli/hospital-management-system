package hospital.ui;

import hospital.dao.DoctorDAO;
import hospital.dao.DepartmentDAO;
import hospital.dao.PatientDAO;
import hospital.model.Department;
import hospital.model.Patient;
import hospital.model.Doctor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class DoctorUI extends JFrame {

    private JTextField nameField, salaryField, emailField, phoneField;
    private JComboBox<Department> deptDropdown;
    private JComboBox<String> genders;
    private JTable doctorTable;
    private DefaultTableModel tableModel;

    private DoctorDAO drDAO;
    private DepartmentDAO deptDAO;
    private PatientDAO pDAO;

    public DoctorUI() {

        drDAO = new DoctorDAO();
        deptDAO = new DepartmentDAO();
        pDAO = new PatientDAO();

        setTitle("Doctor Management");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {

        // ---------------------- FORM PANEL ----------------------
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Add Doctor"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameField = new JTextField(15);
        genders = new JComboBox<>();
        String[] list = { "MALE", "FEMALE", "OTHER" };
        for (String gen : list) {
            genders.addItem(gen);
        }
        salaryField = new JTextField(10);
        emailField = new JTextField(15);
        phoneField = new JTextField(12);

        deptDropdown = new JComboBox<>();
        loadDepartments();

        int y = 0;

        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 3;
        formPanel.add(genders, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(new JLabel("Salary:"), gbc);
        gbc.gridx = 1;
        formPanel.add(salaryField, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 3;
        formPanel.add(emailField, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Department:"), gbc);
        gbc.gridx = 3;
        formPanel.add(deptDropdown, gbc);

        y++;
        gbc.gridx = 3;
        gbc.gridy = y;
        JButton addBtn = new JButton("Add Doctor");
        formPanel.add(addBtn, gbc);

        add(formPanel, BorderLayout.NORTH);

        // ---------------------- TABLE PANEL ----------------------
        String[] columns = { "ID", "Name", "Gender", "Department", "Phone", "Salary" };
        tableModel = new DefaultTableModel(columns, 0);
        doctorTable = new JTable(tableModel);
        doctorTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(doctorTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("All Doctors"));

        add(scrollPane, BorderLayout.CENTER);

        // ---------------------- BOTTOM PANEL ----------------------
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton deleteBtn = new JButton("Delete Selected");
        bottomPanel.add(deleteBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        // ---------------------- ACTIONS ----------------------
        addBtn.addActionListener(e -> addDoctor());
        deleteBtn.addActionListener(e -> deleteDoctor());

        refreshTable();
    }

    private void loadDepartments() {
        deptDropdown.removeAllItems();
        List<Department> list = deptDAO.getAllDepartments();
        for (Department dept : list) {
            deptDropdown.addItem(dept);
        }
    }

    private void addDoctor() {

        String name = nameField.getText().trim();
        String gender = (String) genders.getSelectedItem();
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
        if (selectedDept == null) {
            JOptionPane.showMessageDialog(this, "Select a department.");
            return;
        }

        Doctor dr = new Doctor();
        dr.setName(name);
        dr.setGender(gender);
        dr.setPhone(phone);
        dr.setEmail(email);
        dr.setSalary(salary);
        dr.setDeptId(selectedDept.getDeptId());

        boolean success = drDAO.addDoctor(dr);

        if (success) {
            JOptionPane.showMessageDialog(this, "Doctor added successfully.");
            clearFields();
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add Doctor.");
        }
    }

    private void deleteDoctor() {

        int selectedRow = doctorTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select doctor from table to delete.");
            return;
        }

        int doctorId = (int) tableModel.getValueAt(selectedRow, 0);

        List<Patient> list = pDAO.getPatientsRelDoc(doctorId);
        if (list.size() > 0) {
            JOptionPane.showMessageDialog(this, "Cannot delete doctor (may be assigned to patients).");
            return;
        }

        boolean success = drDAO.deleteDoctor(doctorId);

        if (success) {
            JOptionPane.showMessageDialog(this, "Doctor deleted.");
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete Doctor.");
        }
    }

    private void refreshTable() {

        tableModel.setRowCount(0);

        List<Doctor> list = drDAO.getAllDr();

        for (Doctor doc : list) {
            tableModel.addRow(new Object[] {
                    doc.getDoctorId(),
                    doc.getName(),
                    doc.getGender(),
                    doc.getDeptId(),
                    doc.getPhone(),
                    doc.getSalary()
            });
        }
    }

    private void clearFields() {
        nameField.setText("");
        salaryField.setText("");
        emailField.setText("");
        phoneField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DoctorUI::new);
    }
}