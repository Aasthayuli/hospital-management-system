package hospital.ui;

import hospital.dao.PatientDAO;
import hospital.model.Patient;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PatientDetailsUI extends JFrame {

    private JComboBox<Patient> patientCombo;
    private JTextArea detailsArea;
    private JTable admittedTable;
    private PatientDAO patientDAO;

    public PatientDetailsUI() {
        patientDAO = new PatientDAO();

        setTitle("Patient Management");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Patient Details", createDetailsPanel());
        tabbedPane.addTab("Admitted Patients", createAdmittedPanel());

        add(tabbedPane);
    }

    // PATIENT DETAILS PANEL
    private JPanel createDetailsPanel() {

        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));

        topPanel.add(new JLabel("Select Patient:"));

        patientCombo = new JComboBox<>();
        for (Patient p : patientDAO.getAllPatients()) {
            patientCombo.addItem(p);
        }

        topPanel.add(patientCombo);

        JButton searchBtn = new JButton("View Details");
        topPanel.add(searchBtn);

        panel.add(topPanel, BorderLayout.NORTH);

        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        panel.add(new JScrollPane(detailsArea), BorderLayout.CENTER);

        searchBtn.addActionListener(e -> showPatientDetails());

        return panel;
    }

    private void showPatientDetails() {

        Patient p = (Patient) patientCombo.getSelectedItem();

        if (p == null) {
            JOptionPane.showMessageDialog(this, "No patient selected.");
            return;
        }

        detailsArea.setText("");

        detailsArea.append("------------- PATIENT DETAILS -------------\n\n");
        detailsArea.append("    Patient ID      : " + p.getPatientId() + "\n");
        detailsArea.append("    Name            : " + p.getName() + "\n");
        detailsArea.append("    Age             : " + p.getAge() + "\n");
        detailsArea.append("    Gender          : " + p.getGender() + "\n");
        detailsArea.append("    Disease         : " + p.getDisease() + "\n");
        detailsArea.append("    Doctor ID       : " + p.getDoctorId() + "\n");
        detailsArea.append("    Room No         : " + p.getRoomNo() + "\n");
        detailsArea.append("    Phone           : " + p.getPhone() + "\n");
        detailsArea.append("    Address         : " + p.getAddress() + "\n");
        detailsArea.append("    Admitted At     : " + p.getAdmitTime() + "\n");

        if (p.getDischargeTime() != null) {
            detailsArea.append("    Discharged At   : " + p.getDischargeTime() + "\n");
        } else {
            detailsArea.append("    Status          : Currently Admitted\n");
        }
    }

    // ADMITTED PATIENTS PANEL
    private JPanel createAdmittedPanel() {

        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        String[] columns = {
                "ID", "Name", "Age", "Gender",
                "Disease", "Doctor ID", "Room",
                "Admitted At", "Status"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        admittedTable = new JTable(model);
        admittedTable.setRowHeight(25);

        loadAdmittedPatients(model);

        panel.add(new JScrollPane(admittedTable), BorderLayout.CENTER);

        JButton refreshBtn = new JButton("Refresh");
        panel.add(refreshBtn, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> {
            model.setRowCount(0);
            loadAdmittedPatients(model);
        });

        return panel;
    }

    private void loadAdmittedPatients(DefaultTableModel model) {

        for (Patient p : patientDAO.getAdmittedPatients()) {

            String status = (p.getDischargeTime() == null)
                    ? "Admitted"
                    : "Discharged";

            model.addRow(new Object[] {
                    p.getPatientId(),
                    p.getName(),
                    p.getAge(),
                    p.getGender(),
                    p.getDisease(),
                    p.getDoctorId(),
                    p.getRoomNo(),
                    p.getAdmitTime(),
                    status
            });
        }
    }

}