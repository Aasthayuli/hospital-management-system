## Project Structure

```
└── 📁HMS
    └── 📁lib
        ├── mysql-connector-j-9.6.0.jar
    └── 📁src
        └── 📁hospital
            └── 📁config
                ├── DBConnection.java
            └── 📁dao
                ├── AmbulanceDAO.java
                ├── BillingTransactionDAO.java
                ├── DepartmentDAO.java
                ├── DoctorDAO.java
                ├── PatientDAO.java
                ├── RoomDAO.java
                ├── UserDAO.java
            └── 📁model
                ├── Ambulance.java
                ├── BillingTransaction.java
                ├── Department.java
                ├── Doctor.java
                ├── Patient.java
                ├── Room.java
                ├── User.java
            └── 📁service
                ├── AdmissionService.java
                ├── BillingService.java
                ├── DischargeService.java
            └── 📁ui
                ├── AmbulanceUI.java
                ├── BillingUI.java
                ├── DashboardUI.java
                ├── DepartmentUI.java
                ├── DoctorUI.java
                ├── LoginUI.java
                ├── PatientAdmissionUI.java
                ├── PatientDetailsUI.java
                ├── PatientDischargeUI.java
                ├── RoomUI.java
                ├── UserUI.java
        ├── db.properties
    ├── .gitignore
    ├── hms_schema.sql
    └── README.md
```
