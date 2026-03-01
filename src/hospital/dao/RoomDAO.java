package hospital.dao;

import hospital.config.DBConnection;
import hospital.model.Room;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class RoomDAO {

    // Add room
    public boolean addRoom(Room room) {
        String sql = "INSERT INTO room (room_no, room_type, price_per_day, availability) VALUES (?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, room.getRoomNo());
            stmt.setString(2, room.getRoomType());
            stmt.setDouble(3, room.getPricePerDay());
            stmt.setString(4, room.getAvailability());

            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get room by number
    public Room getRoomByNo(int roomNo) {
        String sql = "SELECT * FROM room WHERE room_no = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roomNo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Room(
                            rs.getInt("room_no"),
                            rs.getString("room_type"),
                            rs.getDouble("price_per_day"),
                            rs.getString("availability"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // get all rooms
    public List<Room> getAllRooms() {
        String sql = "SELECT * FROM room";
        List<Room> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(
                            new Room(
                                    rs.getInt("room_no"),
                                    rs.getString("room_type"),
                                    rs.getDouble("price_per_day"),
                                    rs.getString("availability")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // get only available rooms
    public List<Room> getAvailableRooms() {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM room WHERE availability = 'AVAILABLE'";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Room(rs.getInt("room_no"),
                        rs.getString("room_type"),
                        rs.getDouble("price_per_day"),
                        rs.getString("availability")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // update room
    public boolean updateRoom(Room room) {
        String sql = "UPDATE room SET room_type = ?, price_per_day = ?, availability = ? WHERE room_no = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, room.getRoomType());
            stmt.setDouble(2, room.getPricePerDay());
            stmt.setString(3, room.getAvailability());
            stmt.setInt(4, room.getRoomNo());

            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Update room availability only (used during admission/ discharge)
    public boolean updateRoomAvailability(int roomNo, String availability) {
        String sql = "UPDATE room SET availability = ? WHERE room_no = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, availability);
            stmt.setInt(2, roomNo);

            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // delete room
    public boolean deleteRoom(int roomNo) {
        String sql = "DELETE FROM room WHERE room_no = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roomNo);

            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}