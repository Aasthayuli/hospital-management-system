package hospital.ui;

import hospital.dao.DepartmentDAO;
import hospital.dao.DoctorDAO;
import hospital.model.Department;
import hospital.model.Doctor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DepartmentUI extends JFrame {

    private JTextField nameField;
    private JTable table;
    private DefaultTableModel tableModel;
    private DepartmentDAO departmentDAO;
    private DoctorDAO drDAO;

    public DepartmentUI() {

        departmentDAO = new DepartmentDAO();
        drDAO = new DoctorDAO();

        setTitle("Department Management");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {

        // ------------------- TOP PANEL (Add/Delete) -------------------
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Manage Department"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Department Name:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(15);
        formPanel.add(nameField, gbc);

        gbc.gridx = 2;
        JButton addBtn = new JButton("Add");
        formPanel.add(addBtn, gbc);

        add(formPanel, BorderLayout.NORTH);

        // ---------------CENTER PANEL (Table ) ---------------
        String[] columns = { "Department ID", "Department Name" };
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("All Departments"));

        add(scrollPane, BorderLayout.CENTER);

        // -------------------------- BOTTOM PANEL --------------------------
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton deleteBtn = new JButton("Delete");
        bottomPanel.add(deleteBtn, gbc);
        JButton viewBtn = new JButton("Refresh");
        bottomPanel.add(viewBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        // -------------------------- ACTIONS --------------------------
        addBtn.addActionListener(e -> addDepartment());
        deleteBtn.addActionListener(e -> deleteDepartment());
        viewBtn.addActionListener(e -> refreshTable());
    }

    private void addDepartment() {
        String name = nameField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Department name cannot be empty.");
            return;
        }

        Department dept = new Department();
        dept.setDeptName(name);

        boolean success = departmentDAO.addDepartment(dept);

        if (success) {
            JOptionPane.showMessageDialog(this, "Department added successfully.");
            nameField.setText("");
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add Department.");
        }
    }

    private void deleteDepartment() {

        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select department from table to delete.");
            return;
        }

        int deptId = (int) tableModel.getValueAt(selectedRow, 0);

        List<Doctor> drs = drDAO.getDrByDept(deptId);
        if (drs.size() > 0) {
            JOptionPane.showMessageDialog(this, "Related Doctors exist. Can't Delete.");
            return;
        }

        boolean success = departmentDAO.deleteDepartment(deptId);

        if (success) {
            JOptionPane.showMessageDialog(this, "Department deleted.");
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Cannot delete department (may be linked to doctors).");
        }
    }

    private void refreshTable() {

        tableModel.setRowCount(0);

        List<Department> list = departmentDAO.getAllDepartments();

        for (Department dept : list) {
            tableModel.addRow(new Object[] {
                    dept.getDeptId(),
                    dept.getDeptName()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DepartmentUI::new);
    }
}