package hospital.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BillingTransaction {

    private int patient_id;
    private BigDecimal amount;
    private String type;
    private LocalDateTime transaction_time;

    public BillingTransaction() {
    }

    public BillingTransaction(int patient_id, BigDecimal amount, String type) {
        this.patient_id = patient_id;
        this.amount = amount;
        this.type = type; // DEPOSIT, CHARGE, REFUND
    }

    // getter and setter
    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patientId) {
        this.patient_id = patientId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getTransaction_time() {
        return transaction_time;
    }

    public void setTransaction_time(LocalDateTime transactionTime) {
        this.transaction_time = transactionTime;
    }
}