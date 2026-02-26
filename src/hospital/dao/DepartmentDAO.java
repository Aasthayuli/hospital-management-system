package hospital.dao;

import hospital.config.DBConnection;
import hospital.model.Department;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class DepartmentDAO {

    // create or Insert a department
    public boolean addDepartment(Department dept) {
        String sql = "INSERT INTO department (dept_name) VALUES (?)";
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, dept.getDeptName());
            int affected = stmt.executeUpdate();
            if (affected == 1) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        dept.setDeptId(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    // get department by ID
    public Department getDepartmentById(int id) {
        String sql = "SELECT * FROM department WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Department(rs.getInt("dept_id"), rs.getString("dept_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // get all departments
    public List<Department> getAllDepartments() {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT * FROM Department";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {
                list.add(new Department(rs.getInt("dept_id"), rs.getString("dept_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // update department's name
    public boolean updateDepartment(Department dept) {
        String sql = "UPDATE department SET dept_name = ? WHERE dept_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dept.getDeptName());
            stmt.setInt(2, dept.getDeptId());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // delete department by name
    public boolean deleteDepartment(Department dept) {
        String sql = "DELETE from department WHERE dept_name = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dept.getDeptName());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}