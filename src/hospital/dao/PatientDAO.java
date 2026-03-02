package hospital.dao;

import hospital.config.DBConnection;
import hospital.model.Patient;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import java.util.ArrayList;
import java.math.BigDecimal;

public class PatientDAO {

    // Admit patient (Insert)
    public boolean addPatient(Patient patient) {
        String sql = "INSERT INTO patient "
                + "(name, age, gender, phone, address, disease, doctor_id, room_no, discharge_time, deposit, total_bill)"
                + "VALUES (?, ?, ?,?,?,?,?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, patient.getName());
            stmt.setInt(2, patient.getAge());
            stmt.setString(3, patient.getGender());
            stmt.setString(4, patient.getPhone());
            stmt.setString(5, patient.getAddress());
            stmt.setString(6, patient.getDisease());
            stmt.setInt(7, patient.getDoctorId());
            stmt.setInt(8, patient.getRoomNo());
            stmt.setTimestamp(9, null);
            stmt.setBigDecimal(10, patient.getDeposit());
            stmt.setBigDecimal(11, patient.getTotalBill());

            int affected = stmt.executeUpdate();
            if (affected == 1) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        patient.setPatientId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // get patient by Id
    public Patient getPatientById(int id) {
        String sql = "SELECT * FROM patient WHERE patient_id  = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractPatient(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // get all patients
    public List<Patient> getAllPatients() {
        String sql = "SELECT * FROM patient";
        List<Patient> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                {
                    list.add(extractPatient(rs));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // get only admitted patients
    public List<Patient> getAdmittedPatients() {
        String sql = "SELECT * FROM patient WHERE status = 'ADMITTED'";
        List<Patient> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {
                list.add(extractPatient(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // update discharge info
    public boolean dischargePatient(int patientId, LocalDateTime dischargeTime, BigDecimal totalBill) { // =================??total
                                                                                                        // Bill won't be
                                                                                                        // used??????????
        String sql = "UPDATE patient SET discharge_time = ?, status = 'DISCHARGED', total_bill = ? WHERE patient_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(dischargeTime));
            stmt.setBigDecimal(2, totalBill);
            stmt.setInt(3, patientId);

            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // helper method to map ResultSet to patient
    private Patient extractPatient(ResultSet rs) throws SQLException {
        Timestamp admitTs = rs.getTimestamp("admit_time");
        Timestamp dischargeTs = rs.getTimestamp("discharge_time");
        return new Patient(
                rs.getInt("patient_id"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getString("gender"),
                rs.getString("phone"),
                rs.getString("address"),
                rs.getString("disease"),
                rs.getInt("doctor_id"),
                rs.getInt("room_no"),
                admitTs != null ? admitTs.toLocalDateTime() : null,
                dischargeTs != null ? dischargeTs.toLocalDateTime() : null,
                rs.getString("status"),
                rs.getBigDecimal("deposit"),
                rs.getBigDecimal("total_bill"));

    }

}