package hospital.service;

import hospital.dao.PatientDAO;
import hospital.dao.RoomDAO;
import hospital.model.Patient;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DischargeService {

	private PatientDAO pDao;
	private RoomDAO rDao;
	private BillingService bs;

	// comstructor
	public DischargeService() {
		pDao = new PatientDAO();
		rDao = new RoomDAO();
		bs = new BillingService();
	}

	// discharege patient
	public boolean dischargePatient(int patient_id) {

		// -> get patient
		Patient patient = pDao.getPatientById(patient_id);

		if (patient == null) {
			System.out.println("Patient not found.");
			return false;
		}

		if (patient.getDischargeTime() != null) {
			System.out.println("Patient already discharged.");
			return false;
		}

		// -> check final balance
		BigDecimal balance = bs.calculateBalance(patient_id);

		if (balance.compareTo(BigDecimal.ZERO) > 0) {
			System.out.println("Pending amount must be cleared before discharge.");
			return false;
		}

		boolean refunded = false;
		if (balance.compareTo(BigDecimal.ZERO) < 0) {
			refunded = bs.refund(patient_id, BigDecimal.ZERO.subtract(balance));
		}

		if (refunded) {
			System.out.println("Refunded extra deposits.");
		}

		// -> update discharge date
		boolean updated = pDao.dischargePatient(patient_id, LocalDateTime.now());

		if (!updated) {
			System.out.println("Failed to discharge patient.");
			return false;
		}

		// > free the room
		int roomNo = patient.getRoomNo();
		rDao.updateRoomAvailability(roomNo, "AVAILABLE");

		return true;
	}
}