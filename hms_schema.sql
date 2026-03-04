-- Schema
-- Run each query one by one, line by line.

CREATE DATABASE IF NOT EXISTS hms;
USE hms;

CREATE TABLE users(user_id INT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(50) UNIQUE NOT NULL, email VARCHAR(100) UNIQUE NOT NULL, password_hash VARCHAR(255) NOT NULL, role ENUM('ADMIN','RECEPTIONIST','DOCTOR') NOT NULL, created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);
CREATE TABLE department ( dept_id INT PRIMARY KEY AUTO_INCREMENT, dept_name VARCHAR(100) UNIQUE);
CREATE TABLE doctor ( doctor_id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100) NOT NULL, gender ENUM('MALE','FEMALE','OTHER'), phone VARCHAR(15), email VARCHAR(100), salary DECIMAL(10,2), dept_id INT, FOREIGN KEY (dept_id) REFERENCES department(dept_id) );
CREATE TABLE room ( room_no INT PRIMARY KEY, room_type ENUM('GENERAL','PRIVATE','ICU') NOT NULL, price_per_day DECIMAL(10,2) NOT NULL, availability ENUM('AVAILABLE','OCCUPIED','MAINTENANCE', 'INACTIVE') DEFAULT 'AVAILABLE' NOT NULL);
CREATE TABLE patient ( patient_id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100) NOT NULL, age INT NOT NULL, gender ENUM('MALE','FEMALE','OTHER'), phone VARCHAR(15), address TEXT, disease VARCHAR(255), doctor_id INT, room_no INT, admit_time DATETIME DEFAULT CURRENT_TIMESTAMP, discharge_time DATETIME NULL, status ENUM('ADMITTED','DISCHARGED') DEFAULT 'ADMITTED', deposit DECIMAL(10,2) DEFAULT 0, total_bill DECIMAL(10,2) DEFAULT 0, created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, FOREIGN KEY (doctor_id) REFERENCES doctor(doctor_id), FOREIGN KEY (room_no) REFERENCES room(room_no) );
CREATE TABLE billing_transaction ( transaction_id INT PRIMARY KEY AUTO_INCREMENT, patient_id INT, amount DECIMAL(10,2), type ENUM('DEPOSIT','CHARGE','REFUND'), transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (patient_id) REFERENCES patient(patient_id) );
CREATE TABLE ambulance ( ambulance_id INT PRIMARY KEY AUTO_INCREMENT, driver_name VARCHAR(100), contact VARCHAR(10) , vehicle_no VARCHAR(50) UNIQUE, availability ENUM('AVAILABLE','NOT_AVAILABLE') DEFAULT 'AVAILABLE', location VARCHAR(100) );

-- dummy values to insert
SELECT * FROM users; 
INSERT INTO users(user_id, username, email, password_hash, role) VALUES (1, "Admin", "admin123@gmail.com", "123456789", "ADMIN");
INSERT INTO users(user_id, username, email, password_hash, role) VALUES (2, "Aasthayuli", "aastha123@gmail.com", "123456", "DOCTOR");

SELECT * FROM department;
INSERT INTO department(dept_id, dept_name) VALUES (1, "Neurosurgery");
INSERT INTO department(dept_id, dept_name) VALUES (2, "Dentist");
INSERT INTO department(dept_id, dept_name) VALUES (3, "Physiotherapy");

SELECT * FROM doctor;
INSERT INTO doctor(doctor_id, name, gender, phone, email, salary, dept_id) VALUES (1, "Aasthayuli", "FEMALE", "9576357966", "aastha123@gmail.com", 10000000, 1);
INSERT INTO doctor(doctor_id, name, gender, phone, email, salary, dept_id) VALUES (2, "Animesh Kumar", "MALE", "1234568972", "animesh123@gmail.com", 20000000, 2);
INSERT INTO doctor(doctor_id, name, gender, phone, email, salary, dept_id) VALUES (3, "Animesh Singh", "MALE", "9234568972", "animesh1234@gmail.com", 20000000, 3);

SELECT * FROM room;
INSERT INTO room(room_no, room_type, price_per_day) VALUES (1, "PRIVATE", 1000);
INSERT INTO room(room_no, room_type, price_per_day, availability) VALUES (2, "PRIVATE", 1000, "MAINTENANCE");
INSERT INTO room(room_no, room_type, price_per_day) VALUES (3, "PRIVATE", 1000);
INSERT INTO room(room_no, room_type, price_per_day) VALUES (4, "ICU", 2000);
INSERT INTO room(room_no, room_type, price_per_day) VALUES (5, "GENERAL", 300);

SELECT * FROM patient;
INSERT INTO patient(patient_id, name, age, gender, phone, address, disease, doctor_id, room_no, status, deposit, total_bill) VALUES (1, "Rahul sharma", 20, "MALE", "7412589635", "Bus stand, Gurugram", "Asthma", 3, 5, "ADMITTED", 100, 500);
INSERT INTO patient(patient_id, name, age, gender, phone, address, disease, doctor_id, room_no, status, deposit, total_bill) VALUES (2, "Priya Mishra", 40, "FEMALE", "9412584535", "Sadar Bajar, Gurugram", "Typhoid", 3, 3, "ADMITTED", 500, 1500);

-- mark room occupied where patients have been alloted.
UPDATE room SET availability = "OCCUPIED" WHERE room_no = 5;
UPDATE room SET availability = "OCCUPIED" WHERE room_no = 3;

-- added the deposits in transaction table
SELECT * FROM billing_transaction;
INSERT INTO billing_transaction(transaction_id, patient_id, amount, type) VALUES (1, 1, 100, "DEPOSIT");
INSERT INTO billing_transaction(transaction_id, patient_id, amount, type) VALUES (2, 2, 500, "DEPOSIT");

SELECT * FROM ambulance;
INSERT INTO ambulance(ambulance_id, driver_name, contact, vehicle_no, location) VALUES (1, "Ratan Singh", "7533514862", "852201-SH-BH", "Gate no.-1");
INSERT INTO ambulance(ambulance_id, driver_name, contact, vehicle_no, location) VALUES (2, "Raman Singh", "7539514862", "852001-SH-BH", "Gate no.-4");