package hospital.ui;

import hospital.model.Patient;
import hospital.model.Room;
import hospital.model.Doctor;
import hospital.dao.RoomDAO;
import hospital.dao.DoctorDAO;

import hospital.service.AdmissionService;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class PatientAdmissionUI extends JFrame {

    private JTextField nameField, ageField, phoneField, addressField,
            diseaseField, depositedField, totalBillField;

    private JComboBox<String> genderBox;
    private JComboBox<Doctor> doctorBox;
    private JComboBox<Room> roomBox;

    private AdmissionService admissionService;
    private RoomDAO roomDAO;
    private DoctorDAO doctorDAO;

    public PatientAdmissionUI() {

        admissionService = new AdmissionService();
        roomDAO = new RoomDAO();
        doctorDAO = new DoctorDAO();

        setTitle("Patient Admission");
        setSize(980, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Admit Patient"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        // ===== PATIENT INFO =====
        nameField = new JTextField(15);
        ageField = new JTextField(5);
        phoneField = new JTextField(12);
        addressField = new JTextField(15);
        diseaseField = new JTextField(15);

        genderBox = new JComboBox<>(new String[] { "MALE", "FEMALE", "OTHER" });

        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        formPanel.add(ageField, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1;
        formPanel.add(genderBox, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        formPanel.add(addressField, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(new JLabel("Disease:"), gbc);
        gbc.gridx = 1;
        formPanel.add(diseaseField, gbc);

        // ===== DOCTOR & ROOM =====
        y++;
        doctorBox = new JComboBox<>();
        for (Doctor d : doctorDAO.getAllDr()) {
            doctorBox.addItem(d);
        }

        roomBox = new JComboBox<>();
        for (Room r : roomDAO.getAvailableRooms()) {
            roomBox.addItem(r);
        }

        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(new JLabel("Assign Doctor:"), gbc);
        gbc.gridx = 1;
        formPanel.add(doctorBox, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(new JLabel("Select Room:"), gbc);
        gbc.gridx = 1;
        formPanel.add(roomBox, gbc);

        // ===== BILLING =====
        y++;
        depositedField = new JTextField(10);
        totalBillField = new JTextField(10);

        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(new JLabel("Deposit Amount:"), gbc);
        gbc.gridx = 1;
        formPanel.add(depositedField, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(new JLabel("Total Estimated Bill:"), gbc);
        gbc.gridx = 1;
        formPanel.add(totalBillField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 10));

        JButton admitBtn = new JButton("Admit Patient");
        JButton viewBtn = new JButton("View Patients");
        JButton deleteBtn = new JButton("Discharge Patient");

        buttonPanel.add(admitBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(deleteBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        admitBtn.addActionListener(e -> admitPatient());
        viewBtn.addActionListener(e -> new PatientDetailsUI());
        deleteBtn.addActionListener(e -> new PatientDischargeUI());
    }

    private void admitPatient() {

        try {
            String name = nameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String gender = (String) genderBox.getSelectedItem();
            String phone = phoneField.getText().trim();
            String address = addressField.getText().trim();
            String disease = diseaseField.getText().trim();
            Doctor doctor = (Doctor) doctorBox.getSelectedItem();
            Room room = (Room) roomBox.getSelectedItem();
            BigDecimal deposit = new BigDecimal(depositedField.getText().trim());
            BigDecimal total = new BigDecimal(totalBillField.getText().trim());

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()
                    || disease.isEmpty() || doctor == null || room == null) {
                JOptionPane.showMessageDialog(this, "All fields are required.");
                return;
            }

            Patient patient = new Patient(
                    name, age, gender, phone, address, disease,
                    doctor.getDoctorId(), room.getRoomNo(),
                    deposit, total);

            boolean success = admissionService.admitPatient(patient);

            if (success) {
                JOptionPane.showMessageDialog(this, "Patient admitted successfully.");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Admission failed.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Age, Deposit and Total must be valid numbers.");
        }
    }

    private void clearFields() {
        nameField.setText("");
        ageField.setText("");
        phoneField.setText("");
        addressField.setText("");
        diseaseField.setText("");
        depositedField.setText("");
        totalBillField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PatientAdmissionUI::new);
    }
}