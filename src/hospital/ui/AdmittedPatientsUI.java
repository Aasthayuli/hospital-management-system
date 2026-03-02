package hospital.ui;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import hospital.model.Patient;
import hospital.dao.PatientDAO;

public class AdmittedPatientsUI extends JFrame {
    private JTextArea displayArea;
    private PatientDAO pDAO;

    public AdmittedPatientsUI() {
        pDAO = new PatientDAO();
        displayArea = new JTextArea(10, 10);
        add(displayArea);

        showAdmittedPatients();

        setTitle("Admitted patients");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setVisible(true);

    }

    private void showAdmittedPatients() {
        displayArea.setText("");
        for (Patient p : pDAO.getAdmittedPatients()) {
            displayArea.append(
                    "    ID: " +
                            p.getPatientId() +
                            " | Name: " +
                            p.getName() +
                            " | Age: " +
                            p.getAge() +
                            " | Gender: " +
                            p.getGender() +
                            " | Disease: " +
                            p.getDisease() +
                            " | Doctor ID: " +
                            p.getDoctorId() +
                            " | Room: " +
                            p.getRoomNo() +
                            " | Admitted at: " +
                            p.getAdmitTime() +
                            " | " +
                            (p.getDischargeTime() == null ? "Not discharged"
                                    : p.getDischargeTime().toString())
                            +
                            " | Address: " +
                            p.getAddress() +
                            " | Phone: " +
                            p.getPhone()
                            + "    \n");
        }
    }
}
