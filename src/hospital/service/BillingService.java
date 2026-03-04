package hospital.service;

import hospital.dao.BillingTransactionDAO;
import hospital.dao.PatientDAO;
import hospital.model.BillingTransaction;
import hospital.model.Patient;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BillingService {

    // constructot
    private BillingTransactionDAO billingDAO;
    private PatientDAO pDao;

    public BillingService() {
        billingDAO = new BillingTransactionDAO();
        pDao = new PatientDAO();
    }

    // add deposit
    public boolean addDeposit(int patient_id, BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Invalid deposit amount.");
        }

        Patient pt = pDao.getPatientById(patient_id);

        BillingTransaction bt = new BillingTransaction();
        bt.setAmount(amount);
        bt.setPatient_id(patient_id);
        bt.setTransaction_time(LocalDateTime.now());
        bt.setType("DEPOSIT");

        return billingDAO.addTransaction(bt) && pDao.updateDeposits(patient_id, pt.getDeposit().add(amount));
    }

    // add charge
    public boolean addCharge(int patient_id, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Invalid Charge amount.");
        }

        Patient pt = pDao.getPatientById(patient_id);
        pt.setTotalBill(pt.getTotalBill().add(amount));

        BillingTransaction bt = new BillingTransaction();
        bt.setAmount(amount);
        bt.setPatient_id(patient_id);
        bt.setTransaction_time(LocalDateTime.now());
        bt.setType("CHARGE");

        return billingDAO.addTransaction(bt) && pDao.updateTotalBill(pt);
    }

    // calculate balance
    public BigDecimal calculateBalance(int patient_id) {

        Patient p = pDao.getPatientById(patient_id);
        BigDecimal deposits = p.getDeposit();
        BigDecimal total = p.getTotalBill();

        return total.subtract(deposits);
    }

    public boolean refund(int patient_id, BigDecimal amount) {
        BillingTransaction bt = new BillingTransaction();
        bt.setAmount(amount);
        bt.setPatient_id(patient_id);
        bt.setTransaction_time(LocalDateTime.now());
        bt.setType("REFUND");

        return billingDAO.addTransaction(bt);

    }
}