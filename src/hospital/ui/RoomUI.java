package hospital.ui;

import hospital.dao.RoomDAO;
import hospital.model.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RoomUI extends JFrame {

    private JTextField roomNumField;
    private JComboBox<String> types;
    private JTextField priceField;

    private JTable roomTable;
    private DefaultTableModel tableModel;

    private JComboBox<Room> roomsAvailable;
    private JComboBox<String> availability;

    private RoomDAO rDAO;

    public RoomUI() {
        rDAO = new RoomDAO();

        setTitle("Room Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {

        // TOP PANEL (Form)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Add Room"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Room Number:"), gbc);

        gbc.gridx = 1;
        roomNumField = new JTextField(15);
        formPanel.add(roomNumField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Room Type:"), gbc);

        gbc.gridx = 1;
        types = new JComboBox<>();
        String[] list = { "PRIVATE", "ICU", "GENERAL" };
        for (String type : list) {
            types.addItem(type);
        }
        formPanel.add(types, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Price per Day:"), gbc);

        gbc.gridx = 1;
        priceField = new JTextField(15);
        formPanel.add(priceField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        JButton addBtn = new JButton("Add Room");
        formPanel.add(addBtn, gbc);

        add(formPanel, BorderLayout.NORTH);

        // --------------- MID PANEL (Table) ------------------------
        String[] columns = { "Room No", "Type", "Price", "Availability" };
        tableModel = new DefaultTableModel(columns, 0);
        roomTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(roomTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("All Rooms"));

        add(scrollPane, BorderLayout.CENTER);

        // ------------------BOTTOM PANEL (Update) --------------------------------
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Update Room Availability"));

        roomsAvailable = new JComboBox<>();
        loadRooms();

        JButton updateBtn = new JButton("Update");

        String[] lists = { "AVAILABLE", "OCCUPIED", "MAINTENANCE", "INACTIVE" };
        availability = new JComboBox<>();
        for (String str : lists) {
            availability.addItem(str);
        }
        bottomPanel.add(new JLabel("Select Room:"));
        bottomPanel.add(roomsAvailable);
        bottomPanel.add(availability);
        bottomPanel.add(updateBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        // Button Actions
        addBtn.addActionListener(e -> addRoom());
        updateBtn.addActionListener(e -> updateRoom());

        refreshTable();
    }

    private void loadRooms() {
        roomsAvailable.removeAllItems();
        for (Room r : rDAO.getAllRooms()) {
            roomsAvailable.addItem(r);
        }
    }

    private void addRoom() {
        try {
            int roomNo = Integer.parseInt(roomNumField.getText().trim());
            String type = (String) types.getSelectedItem();
            double price = Double.parseDouble(priceField.getText().trim());

            if (type.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.");
                return;
            }

            Room room = new Room();
            room.setRoomNo(roomNo);
            room.setRoomType(type);
            room.setPricePerDay(price);
            room.setAvailability("AVAILABLE");

            boolean success = rDAO.addRoom(room);

            if (success) {
                JOptionPane.showMessageDialog(this, "Room added successfully.");
                clearFields();
                refreshTable();
                loadRooms();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add room.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Room number and price must be numeric.");
        }
    }

    private void updateRoom() {
        Room room = (Room) roomsAvailable.getSelectedItem();
        String avail = (String) availability.getSelectedItem();

        if (room == null) {
            JOptionPane.showMessageDialog(this, "Select a room to delete.");
            return;
        }
        if (avail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mark Availability.");
            return;
        }

        boolean success = rDAO.updateRoomAvailability(room.getRoomNo(), avail);

        if (success) {
            JOptionPane.showMessageDialog(this, "Room Updated successfully.");
            refreshTable();
            loadRooms();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update room.");
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);

        List<Room> rooms = rDAO.getAllRooms();

        for (Room room : rooms) {
            tableModel.addRow(new Object[] {
                    room.getRoomNo(),
                    room.getRoomType(),
                    room.getPricePerDay(),
                    room.getAvailability()
            });
        }
    }

    private void clearFields() {
        roomNumField.setText("");
        priceField.setText("");
    }
}