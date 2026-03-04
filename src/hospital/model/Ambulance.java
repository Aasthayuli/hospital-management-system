package hospital.model;

public class Ambulance {

    private int ambulanceId;
    private String driverName;
    private String contact;
    private String vehicleNo;
    private String availability; // AVAILABLE, OCCUPIED
    private String location;

    // Default Constructor
    public Ambulance() {
    }

    // Constructor for insert (without ID)
    public Ambulance(String driverName, String contact,
            String vehicleNo, String availability,
            String location) {

        this.driverName = driverName;
        this.contact = contact;
        this.vehicleNo = vehicleNo;
        this.availability = availability;
        this.location = location;
    }

    // Constructor with ID (for fetching from DB)
    public Ambulance(int ambulanceId, String driverName, String contact,
            String vehicleNo, String availability,
            String location) {

        this.ambulanceId = ambulanceId;
        this.driverName = driverName;
        this.contact = contact;
        this.vehicleNo = vehicleNo;
        this.availability = availability;
        this.location = location;
    }

    // Getters and Setters

    public int getAmbulanceId() {
        return ambulanceId;
    }

    public void setAmbulanceId(int ambulanceId) {
        this.ambulanceId = ambulanceId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Utility
    public boolean isAvailable() {
        return "AVAILABLE".equalsIgnoreCase(availability);
    }

    @Override
    public String toString() {
        return "vehicle No. " + vehicleNo;

    }
}
