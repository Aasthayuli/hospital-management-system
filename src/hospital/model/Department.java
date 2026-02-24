package hospital.model;

public class Department {

    private int deptId;
    private String deptName;

    // Default Constructor
    public Department() {
    }

    // Constructor for insert (without ID)
    public Department(String deptName) {
        this.deptName = deptName;
    }

    // Constructor with ID (for fetching from DB)
    public Department(int deptId, String deptName) {
        this.deptId = deptId;
        this.deptName = deptName;
    }

    // Getters and Setters

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        if (deptName == null || deptName.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be empty.");
        }
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        return "Department{" +
                "deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                '}';
    }
}
