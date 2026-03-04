package hospital.dao;

import hospital.config.DBConnection;
import hospital.model.Ambulance;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class AmbulanceDAO {

    // add ambulance
    public boolean addAmbulance(Ambulance ambulance) {
        String sql = "INSERT INTO ambulance (driver_name, contact, vehicle_no, availability, location) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ambulance.getDriverName());
            stmt.setString(2, ambulance.getContact());
            stmt.setString(3, ambulance.getVehicleNo());
            stmt.setString(4, ambulance.getAvailability());
            stmt.setString(5, ambulance.getLocation());

            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // get all ambulance
    public List<Ambulance> getAllAmbulances() {
        String sql = "SELECT * FROM ambulance";
        List<Ambulance> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Ambulance(
                            rs.getInt("ambulance_id"),
                            rs.getString("driver_name"),
                            rs.getString("contact"),
                            rs.getString("vehicle_no"),
                            rs.getString("availability"),
                            rs.getString("location")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // delete ambulance
    public boolean deleteAmbulanceByVehicleNumber(String vehicle_no) {
        String sql = "DELETE FROM ambulance WHERE vehicle_no = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, vehicle_no);

            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
