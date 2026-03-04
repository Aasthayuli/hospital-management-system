package hospital.ui;

import hospital.dao.AmbulanceDAO;
import hospital.model.Ambulance;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AmbulanceUI extends JFrame {

    private JTextField vehicleNumberField;
    private JTextField driverNameField;
    private JTextField contactField;
    private JTextField locationField;
    private JComboBox<String> availabilityCombo;

    private JTable ambulanceTable;
    private DefaultTableModel tableModel;

    private AmbulanceDAO ambulanceDAO;

    public AmbulanceUI() {

        ambulanceDAO = new AmbulanceDAO();

        setTitle("Ambulance Management");
        setSize(950, 550);
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

    // ---------------- FORM PANEL ----------------
    private JPanel createFormPanel() {

        JPanel formPanel = new JPanel(new GridLayout(3, 5, 3, 10));

        formPanel.add(new JLabel("Vehicle Number:"));
        vehicleNumberField = new JTextField();
        formPanel.add(vehicleNumberField);

        formPanel.add(new JLabel("Driver Name:"));
        driverNameField = new JTextField();
        formPanel.add(driverNameField);

        formPanel.add(new JLabel("Contact:"));
        contactField = new JTextField();
        formPanel.add(contactField);

        formPanel.add(new JLabel("Location:"));
        locationField = new JTextField();
        formPanel.add(locationField);

        formPanel.add(new JLabel("Availability:"));
        availabilityCombo = new JComboBox<>(new String[] { "AVAILABLE", "UNAVAILABLE" });
        formPanel.add(availabilityCombo);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton addBtn = new JButton("Add Ambulance");
        JButton deleteBtn = new JButton("Delete Selected");

        buttonPanel.add(addBtn);
        buttonPanel.add(deleteBtn);

        JPanel container = new JPanel(new BorderLayout());
        container.add(formPanel, BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addAmbulance());
        deleteBtn.addActionListener(e -> deleteAmbulance());

        return container;
    }

    // ---------------- TABLE PANEL ----------------
    private JScrollPane createTablePanel() {

        String[] columns = {
                "ID", "Vehicle No", "Driver", "Contact",
                "Location", "Availability"
        };

        tableModel = new DefaultTableModel(columns, 0);
        ambulanceTable = new JTable(tableModel);
        ambulanceTable.setRowHeight(25);

        loadAmbulances();

        return new JScrollPane(ambulanceTable);
    }

    private void loadAmbulances() {

        tableModel.setRowCount(0);

        List<Ambulance> list = ambulanceDAO.getAllAmbulances();

        for (Ambulance amb : list) {
            tableModel.addRow(new Object[] {
                    amb.getAmbulanceId(),
                    amb.getVehicleNo(),
                    amb.getDriverName(),
                    amb.getContact(),
                    amb.getLocation(),
                    amb.isAvailable() ? "AVAILABLE" : "UNAVAILABLE"
            });
        }
    }

    private void addAmbulance() {

        String vehicleNumber = vehicleNumberField.getText().trim();
        String driverName = driverNameField.getText().trim();
        String contact = contactField.getText().trim();
        String location = locationField.getText().trim();
        String availability = (String) availabilityCombo.getSelectedItem();

        if (vehicleNumber.isEmpty() || driverName.isEmpty()
                || contact.isEmpty() || location.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        Ambulance ambulance = new Ambulance();
        ambulance.setVehicleNo(vehicleNumber);
        ambulance.setDriverName(driverName);
        ambulance.setContact(contact);
        ambulance.setLocation(location);
        ambulance.setAvailability(availability);

        boolean success = ambulanceDAO.addAmbulance(ambulance);

        if (success) {
            JOptionPane.showMessageDialog(this, "Ambulance added successfully.");
            clearFields();
            loadAmbulances();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add ambulance.");
        }
    }

    private void deleteAmbulance() {

        int selectedRow = ambulanceTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select ambulance to delete.");
            return;
        }

        String vehicleNumber = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this ambulance?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION)
            return;

        boolean success = ambulanceDAO.deleteAmbulanceByVehicleNumber(vehicleNumber);

        if (success) {
            JOptionPane.showMessageDialog(this, "Ambulance deleted.");
            loadAmbulances();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete ambulance.");
        }
    }

    private void clearFields() {
        vehicleNumberField.setText("");
        driverNameField.setText("");
        contactField.setText("");
        locationField.setText("");
        availabilityCombo.setSelectedIndex(0);
    }

}