package hospital.ui;

import hospital.dao.RoomDAO;
import hospital.model.Room;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RoomUI extends JFrame {

    private JTextField roomNumField;
    private JTextField typeField;
    private JTextField priceField;
    private JTextArea displayArea;
    private RoomDAO rDAO;

    public RoomUI() {

        rDAO = new RoomDAO();

        setTitle("Hospital's Room Management");
        setSize(550, 450);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {

        JPanel topPanel = new JPanel(new FlowLayout());
        // topPanel.setBorder()

        topPanel.add(new JLabel("Room Number: "));
        roomNumField = new JTextField(10);
        topPanel.add(roomNumField);

        topPanel.add(new JLabel("Room Type: "));
        typeField = new JTextField(10);
        topPanel.add(typeField);

        topPanel.add(new JLabel("Price per Day: "));
        priceField = new JTextField(10);
        topPanel.add(priceField);

        topPanel.add(new JLabel("View All Rooms: "));
        displayArea = new JTextArea(10, 20);
        topPanel.add(displayArea);

        JButton addBtn = new JButton("ADD");
        JButton deleteBtn = new JButton("DELETE");
        JButton viewBtn = new JButton("VIEW");

        topPanel.add(addBtn);
        topPanel.add(deleteBtn);
        topPanel.add(viewBtn);

        addBtn.addActionListener(e -> addRoom());
        deleteBtn.addActionListener(e -> deleteRoom());
        viewBtn.addActionListener(e -> viewRooms());

        add(topPanel);
    }

    private void addRoom() {
        String room_no = roomNumField.getText().trim();
        String type = typeField.getText().trim();
        String price = priceField.getText().trim();

        if (room_no.isEmpty() || type.isEmpty() || price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        Room room = new Room();
        room.setRoomNo(Integer.parseInt(room_no));
        room.setRoomType(type);
        room.setPricePerDay(Double.parseDouble(price));
        room.setAvailability("AVAILABLE");

        boolean success = rDAO.addRoom(room);
        if (success) {
            JOptionPane.showMessageDialog(this, "Room added.");
            roomNumField.setText("");
            typeField.setText("");
            priceField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add Room.");
        }

    }

    private void deleteRoom() {
        String room_no = roomNumField.getText().trim();
        if (room_no.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter room number to delete.");
            return;
        }

        boolean success = rDAO.deleteRoom(Integer.parseInt(room_no));
        if (success) {
            JOptionPane.showMessageDialog(this, "Room Deleted.");
            roomNumField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete Room.");
        }
    }

    private void viewRooms() {
        List<Room> rooms = rDAO.getAllRooms();

        displayArea.setText("");

        for (Room room : rooms) {
            displayArea.append(
                    "   Room No.: " +
                            room.getRoomNo() +
                            " | Type: " +
                            room.getRoomType() +
                            " | Price per Day: " +
                            room.getPricePerDay() +
                            " | Availability: " +
                            (room.isAvailable() ? "Yes" : "No") +
                            "\n");
        }
    }

    public static void main(String[] args) {
        new RoomUI();
    }
}