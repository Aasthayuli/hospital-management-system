package hospital.ui;

import hospital.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DashboardUI extends JFrame {

        private User currentUser;

        public DashboardUI(User user) {
                this.currentUser = user;

                setTitle("Hospital Management System - Dashboard");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setSize(900, 600);
                setLocationRelativeTo(null);
                setLayout(new BorderLayout(15, 15));

                initializeUI();
                setVisible(true);
        }

        private void initializeUI() {

                // --------------------HEADER --------------------
                JPanel headerPanel = new JPanel(new BorderLayout());
                headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

                JLabel welcomeLabel = new JLabel(
                                "Welcome, " + currentUser.getUsername() +
                                                "  |  Role: " + currentUser.getRole(),
                                JLabel.LEFT);

                welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

                JButton logoutBtn = new JButton("Logout");

                headerPanel.add(welcomeLabel, BorderLayout.WEST);
                headerPanel.add(logoutBtn, BorderLayout.EAST);

                add(headerPanel, BorderLayout.NORTH);

                JPanel dashboardPanel = new JPanel(new GridBagLayout());
                dashboardPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 40, 40));

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(15, 25, 15, 25);
                gbc.anchor = GridBagConstraints.CENTER;

                Dimension btnSize = new Dimension(170, 45);

                JButton departmentBtn = new JButton("Departments");
                JButton doctorBtn = new JButton("Doctors");
                JButton roomBtn = new JButton("Rooms");
                JButton userBtn = new JButton("Users");
                JButton patientBtn = new JButton("Admit Patient");
                JButton billingBtn = new JButton("Billing");
                JButton dischargeBtn = new JButton("Discharge Patient");
                JButton ambulanceBtn = new JButton("Ambulance");

                JButton[] buttons = {
                                departmentBtn, doctorBtn, roomBtn, userBtn,
                                patientBtn, billingBtn, dischargeBtn, ambulanceBtn
                };

                for (JButton btn : buttons) {
                        btn.setPreferredSize(btnSize);
                }

                // Row 1
                gbc.gridx = 0;
                gbc.gridy = 0;
                dashboardPanel.add(departmentBtn, gbc);
                gbc.gridx = 1;
                dashboardPanel.add(doctorBtn, gbc);
                gbc.gridx = 2;
                dashboardPanel.add(roomBtn, gbc);

                // Row 2
                gbc.gridx = 0;
                gbc.gridy = 1;
                dashboardPanel.add(userBtn, gbc);
                gbc.gridx = 1;
                dashboardPanel.add(patientBtn, gbc);
                gbc.gridx = 2;
                dashboardPanel.add(billingBtn, gbc);

                // Row 3
                gbc.gridx = 0;
                gbc.gridy = 2;
                dashboardPanel.add(dischargeBtn, gbc);
                gbc.gridx = 1;
                dashboardPanel.add(ambulanceBtn, gbc);

                add(dashboardPanel, BorderLayout.CENTER);

                // ---------------------- ACTIONS ----------------------
                departmentBtn.addActionListener(e -> new DepartmentUI());
                doctorBtn.addActionListener(e -> new DoctorUI());
                roomBtn.addActionListener(e -> new RoomUI());
                userBtn.addActionListener(e -> new UserUI());
                patientBtn.addActionListener(e -> new PatientAdmissionUI());
                billingBtn.addActionListener(e -> new BillingUI());
                dischargeBtn.addActionListener(e -> new PatientDischargeUI());
                ambulanceBtn.addActionListener(e -> new AmbulanceUI());
                logoutBtn.addActionListener(this::handleLogout);

                // ---------------------- ROLE RESTRICTION ----------------------
                if (!currentUser.getRole().equalsIgnoreCase("ADMIN")) {
                        departmentBtn.setEnabled(false);
                        doctorBtn.setEnabled(false);
                        roomBtn.setEnabled(false);
                        userBtn.setEnabled(false);
                }
        }

        private void handleLogout(ActionEvent e) {
                dispose();
                new LoginUI();
        }
}