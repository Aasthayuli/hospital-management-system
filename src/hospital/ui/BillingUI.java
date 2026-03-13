package hospital.ui;

import hospital.service.BillingService;
import hospital.dao.BillingTransactionDAO;
import hospital.dao.PatientDAO;
import hospital.model.Patient;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigDecimal;

public class BillingUI extends JFrame {

    private JComboBox<Patient> patients;
    private JTextField amountField;
    private JTextArea summaryArea;

    private BillingService billingService;
    private BillingTransactionDAO bsDAO;
    private PatientDAO pDao;

    public BillingUI() {

        billingService = new BillingService();
        bsDAO = new BillingTransactionDAO();
        pDao = new PatientDAO();

        setTitle("Billing Management");
        setSize(980, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        mainPanel.add(createFormPanel(), BorderLayout.NORTH);
        mainPanel.add(createSummaryPanel(), BorderLayout.CENTER);

        add(mainPanel);
    }

    // ---------------- FORM PANEL ----------------
    private JPanel createFormPanel() {

        JPanel formPanel = new JPanel(new GridLayout(2, 0, 0, 10));

        formPanel.add(new JLabel("Patient ID:"));
        patients = new JComboBox<>();
        for (Patient p : pDao.getAllPatients()) {
            patients.addItem(p);
        }
        formPanel.add(patients);

        formPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        formPanel.add(amountField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton depositBtn = new JButton("Add Deposit");
        JButton chargeBtn = new JButton("Add Extra Charge");
        JButton summaryBtn = new JButton("View Summary");

        buttonPanel.add(depositBtn);
        buttonPanel.add(chargeBtn);
        buttonPanel.add(summaryBtn);

        JPanel container = new JPanel(new BorderLayout());
        container.add(formPanel, BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.SOUTH);

        depositBtn.addActionListener(e -> addDeposit());
        chargeBtn.addActionListener(e -> addCharge());
        summaryBtn.addActionListener(e -> showSummary());

        return container;
    }

    // ---------------- SUMMARY PANEL ----------------
    private JScrollPane createSummaryPanel() {

        summaryArea = new JTextArea();
        summaryArea.setEditable(false);
        summaryArea.setFont(new Font("Monospaced", Font.PLAIN, 15));

        return new JScrollPane(summaryArea);
    }

    private int getPatientId() {
        Patient p = (Patient) patients.getSelectedItem();
        return p.getPatientId();
    }

    private BigDecimal getAmount() {
        return new BigDecimal(amountField.getText().trim());
    }

    private void addDeposit() {

        try {
            int patientId = getPatientId();
            BigDecimal amount = getAmount();

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be greater than zero.");
                return;
            }

            boolean success = billingService.addDeposit(patientId, amount);

            if (success) {
                JOptionPane.showMessageDialog(this, "Deposit added successfully.");
                amountField.setText("");
                showSummary();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add deposit.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Patient ID or Amount.");
        }
    }

    private void addCharge() {

        try {
            int patientId = getPatientId();
            BigDecimal amount = getAmount();

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be greater than zero.");
                return;
            }

            boolean success = billingService.addCharge(patientId, amount);

            if (success) {
                JOptionPane.showMessageDialog(this, "Charge added successfully.");
                amountField.setText("");
                showSummary();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add charge.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Patient ID or Amount.");
        }
    }

    private void showSummary() {

        try {
            int patientId = getPatientId();

            BigDecimal deposits = bsDAO.getTotalByType(patientId, "DEPOSIT");
            BigDecimal charges = bsDAO.getTotalByType(patientId, "CHARGE");
            BigDecimal refunds = bsDAO.getTotalByType(patientId, "REFUND");
            BigDecimal balance = billingService.calculateBalance(patientId);

            summaryArea.setText("");

            summaryArea.append("=========== BILL SUMMARY ===========\n\n");
            summaryArea.append(String.format("%-20s : %s\n", "Total Deposits", deposits));
            summaryArea.append(String.format("%-20s : %s\n", "Extra Charges", charges));
            summaryArea.append(String.format("%-20s : %s\n", "Total Refunds made", refunds));
            summaryArea.append("-------------------------------------\n");
            summaryArea.append(String.format("%-20s : %s\n", "Remaining Balance", balance));

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Patient ID.");
        }
    }

}