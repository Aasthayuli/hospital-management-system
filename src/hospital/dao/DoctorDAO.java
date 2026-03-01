package hospital.dao;

import hospital.config.DBConnection;
import hospital.model.Doctor;

import java.sql.*;

import java.util.List;
import java.util.ArrayList;

public class DoctorDAO {

    // Add doctor
    public boolean addDoctor(Doctor dr) {
        String sql = "INSERT INTO doctor (name, gender, phone, email, salary, dept_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, dr.getName());
            stmt.setString(2, dr.getGender());
            stmt.setString(3, dr.getPhone());
            stmt.setString(4, dr.getEmail());
            stmt.setBigDecimal(5, dr.getSalary());
            stmt.setInt(6, dr.getDeptId());

            int affected = stmt.executeUpdate();

            if (affected == 1) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        dr.setDoctorId(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // get doctor by id
    public Doctor getDrById(int id) {
        String sql = "SELECT * FROM doctor WHERE doctor_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Doctor(
                            rs.getInt("doctor_id"),
                            rs.getString("name"),
                            rs.getString("gender"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getBigDecimal("salary"),
                            rs.getInt("dept_id"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // get all doctors
    public List<Doctor> getAllDr() {
        String sql = "SELECT * FROM doctor";
        List<Doctor> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Doctor(
                            rs.getInt("doctor_id"),
                            rs.getString("name"),
                            rs.getString("gender"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getBigDecimal("salary"),
                            rs.getInt("dept_id")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Update doctor
    public boolean updateDoctor(Doctor dr) {
        String sql = "UPDATE doctor SET name = ?, gender = ?, phone = ?, email = ?, salary = ?, dept_id = ? WHERE doctor_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dr.getName());
            stmt.setString(2, dr.getGender());
            stmt.setString(3, dr.getPhone());
            stmt.setString(4, dr.getEmail());
            stmt.setBigDecimal(5, dr.getSalary());
            stmt.setInt(6, dr.getDeptId());

            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // delete doctor
    public boolean deleteDoctor(int id) {
        String sql = "DELETE FROM doctor WHERE doctor_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            System.out.println(id);

            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // get doctors by department
    public List<Doctor> getDrByDept(int deptId) {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT * FROM doctor WHERE dept_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, deptId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Doctor(
                            rs.getInt("doctor_id"),
                            rs.getString("name"),
                            rs.getString("gender"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getBigDecimal("salary"),
                            rs.getInt("dept_id")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}