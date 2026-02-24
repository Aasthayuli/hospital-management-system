package hospital.model;

import java.math.BigDecimal;

public class Doctor {

    private int doctorId;
    private String name;
    private String gender;
    private String phone;
    private String email;
    private BigDecimal salary;
    private int deptId;

    // Default Constructor
    public Doctor() {
    }

    // Constructor without ID (for insert)
    public Doctor(String name, String gender, String phone,
                  String email, BigDecimal salary, int deptId) {
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.salary = salary;
        this.deptId = deptId;
    }

    // Constructor with ID (for fetch from DB)
    public Doctor(int doctorId, String name, String gender,
                  String phone, String email,
                  BigDecimal salary, int deptId) {
        this.doctorId = doctorId;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.salary = salary;
        this.deptId = deptId;
    }

    // Getters and Setters

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        if (salary != null && salary.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Salary cannot be negative.");
        }
        this.salary = salary;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "doctorId=" + doctorId +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                ", deptId=" + deptId +
                '}';
    }
}
