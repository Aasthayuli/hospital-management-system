package hospital.ui;

import hospital.model.Patient;
import hospital.model.Room;
import hospital.model.Doctor;
import hospital.dao.RoomDAO;
import hospital.dao.DoctorDAO;
import hospital.dao.PatientDAO;
import hospital.service.AdmissionService;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

import java.util.*;
import java.util.List;

public class PatientAdmissionUI extends JFrame {

    private JTextField nameField;
    private JTextField ageField;
    private JComboBox<String> genders;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField diseaseField;
    private JComboBox<Doctor> doctors;
    private JComboBox<Room> rooms;
    private JTextField deposited;
    private JTextField totalBill;

    RoomDAO rDAO;
    DoctorDAO drDAO;
    PatientDAO pDAO;

    // AdmissionService
    private AdmissionService AdService;

    public PatientAdmissionUI() {
        AdService = new AdmissionService();
        rDAO = new RoomDAO();
        drDAO = new DoctorDAO();
        pDAO = new PatientDAO();

        setTitle("Patient Admission");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setLayout(new GridLayout());
        initializeUI();
        setVisible(true);

    }

    private void initializeUI() {
        JPanel panel = new JPanel(new FlowLayout());
        // panel.setBorder();

        panel.add(new JLabel("Patient Name: "));
        nameField = new JTextField(10);
        panel.add(nameField);

        panel.add(new JLabel("Age: "));
        ageField = new JTextField(5);
        panel.add(ageField);

        panel.add(new JLabel("Gender: "));
        List<String> list = new ArrayList<>();
        list.add("MALE");
        list.add("FEMALE");
        list.add("OTHER");
        genders = new JComboBox<>();
        for (String str : list) {
            genders.addItem(str);
        }
        panel.add(genders);

        panel.add(new JLabel("Contact: "));
        phoneField = new JTextField(10);
        panel.add(phoneField);

        panel.add(new JLabel("Address: "));
        addressField = new JTextField(10);
        panel.add(addressField);

        panel.add(new JLabel("Disease: "));
        diseaseField = new JTextField(10);
        panel.add(diseaseField);

        doctors = new JComboBox<>();
        for (Doctor dr : drDAO.getAllDr()) {
            doctors.addItem(dr);
        }
        panel.add(doctors);

        panel.add(new JLabel("Select available rooms: "));
        rooms = new JComboBox<>();
        for (Room r : rDAO.getAvailableRooms()) {
            rooms.addItem(r);
        }
        panel.add(rooms);

        panel.add(new JLabel("Deposited: "));
        deposited = new JTextField(10);
        panel.add(deposited);

        panel.add(new JLabel("Total Bill: "));
        totalBill = new JTextField(10);
        panel.add(totalBill);

        JButton addBtn = new JButton("ADMIT");
        JButton viewAdmittedBtn = new JButton("VIEW ADMITTED PATIENTS");

        panel.add(addBtn);
        panel.add(viewAdmittedBtn);

        addBtn.addActionListener(e -> admitPatient());
        viewAdmittedBtn.addActionListener((e) -> {
            new AdmittedPatientsUI();
        });

        add(panel);

    }

    private void admitPatient() {
        String name = nameField.getText().trim();
        String ageText = ageField.getText().trim();
        String gender = (String) genders.getSelectedItem();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();
        String disease = diseaseField.getText().trim();
        Doctor dr = (Doctor) doctors.getSelectedItem();
        String depositText = deposited.getText().trim();
        String totalText = totalBill.getText().trim();
        Room rm = (Room) rooms.getSelectedItem();

        if (name.isEmpty() || ageText.isEmpty() || gender.isEmpty()
                || phone.isEmpty() || address.isEmpty() || disease.isEmpty() ||
                dr == null || depositText.isEmpty() || totalText.isEmpty() || rm == null) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        int age;
        BigDecimal deposit, total;
        try {
            age = Integer.parseInt(ageText);
            deposit = new BigDecimal(depositText);
            total = new BigDecimal(totalText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Age, Deposit and total bill must be number.");
            return;
        }

        Patient p = new Patient(name, age, gender, phone, address, disease, dr.getDoctorId(), rm.getRoomNo(), deposit,
                total);

        boolean success = AdService.admitPatient(p);
        if (success) {
            JOptionPane.showMessageDialog(this, "Patient admitted Successfully.");
            nameField.setText("");
            ageField.setText("");
            phoneField.setText("");
            addressField.setText("");
            diseaseField.setText("");
            deposited.setText("");
            totalBill.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to Admit Patient.");
        }

    }

    public static void main(String[] args) {
        new PatientAdmissionUI();
    }
}