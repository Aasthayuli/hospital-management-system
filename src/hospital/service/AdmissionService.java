package hospital.service;

import hospital.dao.PatientDAO;
import hospital.dao.RoomDAO;
import hospital.dao.DoctorDAO;

import hospital.model.Patient;
import hospital.model.Room;
import hospital.model.Doctor;

public class AdmissionService {

    private PatientDAO patientDAO;
    private RoomDAO roomDAO;
    private BillingService bs;
    private DoctorDAO drDAO;

    public AdmissionService() {
        patientDAO = new PatientDAO();
        roomDAO = new RoomDAO();
        drDAO = new DoctorDAO();
        bs = new BillingService();
    }

    // admit patient
    public boolean admitPatient(Patient patient) {

        // ->check if room exists and is available
        Room room = roomDAO.getRoomByNo(patient.getRoomNo());
        Doctor dr = drDAO.getDrById(patient.getDoctorId());

        if (room == null) {
            System.out.println("Room not found.");
            return false;
        }
        if (dr == null) {
            System.out.println("Doctor not found.");
            return false;
        }
        if (!room.isAvailable()) {
            System.out.println("Room not available.");
            return false;
        }

        // ->save patient
        boolean patientSaved = patientDAO.addPatient(patient);

        // add transaction
        boolean depositAdded = bs.addDeposit(patient.getPatientId(), patient.getDeposit());

        if (!patientSaved || !depositAdded) {
            System.out.println("Failed to admit patient.");
            return false;
        }

        // -> mark room as unavailable
        roomDAO.updateRoomAvailability(patient.getRoomNo(), "OCCUPIED");

        return true;
    }
}