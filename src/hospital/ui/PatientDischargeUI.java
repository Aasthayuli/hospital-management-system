package hospital.ui;

import hospital.service.DischargeService;
import hospital.model.Patient;
import hospital.dao.PatientDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PatientDischargeUI extends JFrame {

    private JComboBox<Patient> patientCombo;
    private JTextArea infoArea;
    private DischargeService dischargeService;
    private PatientDAO patientDAO;

    public PatientDischargeUI() {

        patientDAO = new PatientDAO();
        dischargeService = new DischargeService();

        setTitle("Patient Discharge Management");
        setSize(900, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // ---------- TOP SECTION ----------
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));

        topPanel.add(new JLabel("Select Admitted Patient:"));

        patientCombo = new JComboBox<>();
        loadPatients();
        topPanel.add(patientCombo);

        JButton viewBtn = new JButton("View Info");
        topPanel.add(viewBtn);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // ---------- CENTER SECTION ----------
        infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        mainPanel.add(new JScrollPane(infoArea), BorderLayout.CENTER);

        // ---------- BOTTOM SECTION ----------
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton dischargeBtn = new JButton("Discharge Patient");
        bottomPanel.add(dischargeBtn);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Listeners
        viewBtn.addActionListener(e -> showPatientInfo());
        dischargeBtn.addActionListener(e -> dischargePatient());
    }

    private void loadPatients() {
        patientCombo.removeAllItems();
        for (Patient p : patientDAO.getAdmittedPatients()) {
            patientCombo.addItem(p);
        }
    }

    private void showPatientInfo() {

        Patient p = (Patient) patientCombo.getSelectedItem();

        if (p == null) {
            JOptionPane.showMessageDialog(this, "No patient selected.");
            return;
        }

        infoArea.setText("");

        infoArea.append("=========== PATIENT INFO ===========\n\n");
        infoArea.append("Patient ID     : " + p.getPatientId() + "\n");
        infoArea.append("Name           : " + p.getName() + "\n");
        infoArea.append("Age            : " + p.getAge() + "\n");
        infoArea.append("Gender         : " + p.getGender() + "\n");
        infoArea.append("Disease        : " + p.getDisease() + "\n");
        infoArea.append("Doctor ID      : " + p.getDoctorId() + "\n");
        infoArea.append("Room No        : " + p.getRoomNo() + "\n");
        infoArea.append("Admitted At    : " + p.getAdmitTime() + "\n");

        infoArea.append("\n------------------------------------\n");
        infoArea.append("Make sure bill is cleared before discharge.\n");
    }

    private void dischargePatient() {

        Patient p = (Patient) patientCombo.getSelectedItem();

        if (p == null) {
            JOptionPane.showMessageDialog(this, "No patient selected.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to discharge this patient?",
                "Confirm Discharge",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION)
            return;

        boolean success = dischargeService.dischargePatient(p.getPatientId());

        if (success) {
            JOptionPane.showMessageDialog(this,
                    "Patient discharged successfully.");

            infoArea.setText("");
            loadPatients();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Discharge failed. Check pending balance or patient status.");
        }
    }

    public static void main(String[] args) {
        new PatientDischargeUI();
    }
}