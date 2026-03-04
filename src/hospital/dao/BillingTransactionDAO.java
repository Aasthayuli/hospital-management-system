package hospital.dao;

import hospital.config.DBConnection;
import hospital.model.BillingTransaction;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import java.math.BigDecimal;

public class BillingTransactionDAO {

    // Add Transaction(DEPOSIT/ CHARGE / REFUND)
    public boolean addTransaction(BillingTransaction bt) {
        String sql = "INSERT INTO billing_transaction (patient_id, amount, type) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, bt.getPatient_id());
            stmt.setBigDecimal(2, bt.getAmount());
            stmt.setString(3, bt.getType());

            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // get all transactions for a patient
    // =========currently not in use !===============
    public List<BillingTransaction> getAllTransactions(int patient_id) {
        String sql = "SELECT * FROM billing_transactions WHERE patient_id  = ? ORDER BY transaction_time";
        List<BillingTransaction> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patient_id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    BillingTransaction transaction = new BillingTransaction();
                    transaction.setPatient_id(rs.getInt("patient_id"));
                    transaction.setAmount(rs.getBigDecimal("amount"));
                    transaction.setType(rs.getString("type"));
                    Timestamp time = rs.getTimestamp("transaction_time");
                    if (time != null) {
                        transaction.setTransaction_time(time.toLocalDateTime());
                    }
                    list.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // calculate total charges for a patient
    public BigDecimal getTotalByType(int patient_id, String type) {
        String sql = "SELECT SUM(amount) FROM billing_transaction WHERE patient_id = ? AND type = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patient_id);
            stmt.setString(2, type);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    BigDecimal total = rs.getBigDecimal(1);
                    return total != null ? total : BigDecimal.ZERO;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return BigDecimal.ZERO;
    }

}