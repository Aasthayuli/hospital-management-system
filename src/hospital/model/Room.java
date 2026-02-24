package hospital.model;

public class Room {

    private int roomNo;
    private String roomType;
    private double pricePerDay;
    private String availability;

    public Room() {
    }

    public Room(int roomNo, String roomType, double pricePerDay, String availability) {
        this.roomNo = roomNo;
        this.roomType = roomType;
        this.pricePerDay = pricePerDay;
        this.availability = availability;
    }

    // getter and setters

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        if (pricePerDay < 0) {
            throw new IllegalArgumentException("Price per day cannot be negative.");
        }
        this.pricePerDay = pricePerDay;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public boolean isAvailable() {
        return "AVAILABLE".equalsIgnoreCase(this.availability);
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNo=" + roomNo +
                ", roomType='" + roomType + '\'' +
                ", pricePerDay='" + pricePerDay +
                ", availability='" + availability + '\'' +
                '}';
    }
}