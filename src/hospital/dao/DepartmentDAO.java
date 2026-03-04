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

    // delete department by id
    public boolean deleteDepartment(int id) {
        String sql = "DELETE from department WHERE dept_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}