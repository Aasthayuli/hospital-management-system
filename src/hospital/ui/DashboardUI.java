package hospital.ui;

import hospital.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DashboardUI extends JFrame {

        // current user
        // private User currentUser;

        // constructor taking User
        public DashboardUI(User user) {
                // this.currentUser = user;

                setTitle("Hospital Management System - Dashboard");
                setSize(500, 400);// ==========================================hard coded==========
                setLocationRelativeTo(null);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLayout(new GridLayout());

                initializeUI();
                setVisible(true);
        }

        private void initializeUI() {
                // welcome lable
                JLabel welcomeLabel = new JLabel(
                                "Welcome, " + "currentUser.getUsername()" +
                                                " (" + "currentUser.getRole()" + ")" +
                                                JLabel.CENTER);
                welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
                add(welcomeLabel, BorderLayout.NORTH);

                // panel
                JPanel panel = new JPanel();
                panel.setLayout(new FlowLayout());
                // panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

                JButton departmentBtn = new JButton("Departments");
                JButton doctorBtn = new JButton("Doctors");
                JButton roomBtn = new JButton("Rooms");
                JButton patientBtn = new JButton("Admit Patient");
                JButton billingBtn = new JButton("Billing");
                JButton dischargeBtn = new JButton("Discharge Patient");
                JButton ambulanceBtn = new JButton("Ambulance");
                JButton logoutBtn = new JButton("Logout");

                panel.add(departmentBtn);
                panel.add(doctorBtn);
                panel.add(roomBtn);
                panel.add(patientBtn);
                panel.add(billingBtn);
                panel.add(dischargeBtn);
                panel.add(ambulanceBtn);
                panel.add(logoutBtn);

                add(panel);

                // btn actions
                departmentBtn.addActionListener(
                                e -> new DepartmentUI());
                doctorBtn.addActionListener(
                                e -> new DoctorUI());
                roomBtn.addActionListener(
                                e -> new RoomUI());
                patientBtn.addActionListener(
                                e -> new PatientAdmissionUI());
                billingBtn.addActionListener(
                                e -> new BillingUI());
                dischargeBtn.addActionListener(
                                e -> new PatientDischargeUI());
                ambulanceBtn.addActionListener(
                                e -> new AmbulanceUI());
                logoutBtn.addActionListener(this::handleLogout);

                // role restriction example
                // if (!currentUser.getRole().equalsIgnoreCase("ADMIN")) {
                // departmentBtn.setEnabled(false);
                // doctorBtn.setEnabled(false);
                // roomBtn.setEnabled(false);
                // }
        }

        private void handleLogout(ActionEvent e) {
                dispose();
                new LoginUI();
        }

        public static void main(String[] args) {
                User user = new User();
                new DashboardUI(user);
        }
}