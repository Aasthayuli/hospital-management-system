package hospital.model;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class Patient {
    private int patientId;
    private String name;
    private int age;
    private String gender;
    private String phone;
    private String address;
    private String disease;
    private int doctorId;
    private int roomNo;
    private LocalDateTime admitTime;
    private LocalDateTime dischargeTime;
    private String status;
    private BigDecimal deposit;
    private BigDecimal totalBill;

    public Patient() {
    }

    // constructor for new admission
    public Patient(String name, int age, String gender, String phone, String address, String disease, int doctorId,
            int roomNo, BigDecimal deposit, BigDecimal totalBill) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.disease = disease;
        this.doctorId = doctorId;
        this.roomNo = roomNo;
        this.deposit = deposit;
        this.status = "ADMITTED";
        this.admitTime = LocalDateTime.now();
        this.totalBill = totalBill;
    }

    // full constructor
    public Patient(int patientId, String name, int age, String gender, String phone, String address, String disease,
            int doctorId, int roomNo, LocalDateTime admitTime, LocalDateTime dischargeTime,
            String status, BigDecimal deposit, BigDecimal totalBill) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.disease = disease;
        this.doctorId = doctorId;
        this.roomNo = roomNo;
        this.admitTime = admitTime;
        this.dischargeTime = dischargeTime;
        this.status = status;
        this.deposit = deposit;
        this.totalBill = totalBill;
    }

    // getter and setters
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public LocalDateTime getAdmitTime() {
        return admitTime;
    }

    public void setAdmitTime(LocalDateTime admitTime) {
        this.admitTime = admitTime;
    }

    public LocalDateTime getDischargeTime() {
        return dischargeTime;
    }

    public void setDischargeTime(LocalDateTime dischargeTime) {
        this.dischargeTime = dischargeTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public BigDecimal getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(BigDecimal totalBill) {
        this.totalBill = totalBill;
    }
}