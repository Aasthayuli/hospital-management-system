package hospital.service;

import hospital.dao.PatientDAO;
import hospital.dao.RoomDAO;

import hospital.model.Patient;
import hospital.model.Room;

public class AdmissionService {

    private PatientDAO patientDAO;
    private RoomDAO roomDAO;
    private BillingService bs;

    public AdmissionService() {
        patientDAO = new PatientDAO();
        roomDAO = new RoomDAO();
        bs = new BillingService();
    }

    // admit patient
    public boolean admitPatient(Patient patient) {

        // ->check if room exists and is available
        Room room = roomDAO.getRoomByNo(patient.getRoomNo());

        if (room == null) {
            System.out.println("Room not found.");
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