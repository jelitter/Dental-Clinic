package controller.dao;

import java.util.ArrayList;
import java.util.List;

import controller.ClinicController;
import model.Clinic;
import model.Invoice;
import model.Patient;
import model.Payment;
import model.Procedure;
import model.ProcedureType;
import view.MainScreen;

public class ClinicFileController extends AbstractClinicStorageController {
	
	private static final String CLINICFILENAME = "src/data/clinic.ser";
	private SerialFileStorage sf;
	private Clinic clinic = null;
	
	public ClinicFileController() {
		sf = new SerialFileStorage();
		clinic = (Clinic) sf.readObject(CLINICFILENAME);
	}
	
	public Clinic getClinicFromStorage() {
		return clinic;
	}
	
	public void saveClinicToStorage(ClinicController cc) {
		ArrayList<Patient>       patientList   = cc.patientsAsList();
		ArrayList<ProcedureType> procedureList = cc.procedureTypesAsList();
		
		cc.getClinic().setPatientList(patientList);
		cc.getClinic().setProcedureTypesList(procedureList);
		
		try {
			sf.storeObject(cc.getClinic(), CLINICFILENAME);
		} catch (Exception ex) {
			// System.out.println("Error writting serial file - " + ex);
		}
	}

	@Override
	public Patient getPatientById(int id) {
		for (Patient p : clinic.getPatients()) {
			if (p.getId() == id) {
				return p;
			}
		}
		return null;
	}

	@Override
	public ProcedureType getProcedureTypeById(int id) {
		for (ProcedureType pt : clinic.getProcedureTypes()) {
			if (pt.getId() == id) {
				return pt;
			}
		}
		return null;
	}

	@Override
	public List<ProcedureType> getProcedureTypes() {
		return clinic.getProcedureTypes();
	}

	@Override
	public List<Patient> getPatients() {
		return clinic.getPatients();
	}

	@Override
	public void updatePatient(Patient patient) {
		for (Patient pat : clinic.getPatients()) {
			if (pat.getId() == patient.getId()) {
				pat = patient;
				break;
			}
		}
	}

	@Override
	public void addPatient(Patient newPatient) {
		clinic.getPatients().add(newPatient);
	}

	@Override
	public void removePatient(Patient patient) {
		clinic.getPatients().remove(patient);
	}

	@Override
	public void addInvoice(Patient patient) {
	}

	@Override
	public List<Invoice> getInvoices(Patient pat) {
		return null;
	}

	@Override
	public List<Payment> getPayments(Invoice inv) {
		return null;
	}

	@Override
	public List<Procedure> getProcedures(Invoice inv, List<ProcedureType> procedureTypes) { return null; }

	@Override
	public void removeInvoice(Invoice invoice) {}

	@Override
	public void updateInvoice(Invoice invoice) {}

	@Override
	public void addPayment(Payment payment) {}

	@Override
	public void removePayment(Payment payment) {}

	@Override
	public void updatePayment(Payment payment) {}

	@Override
	public void addProcedure(Procedure procedure) {}

	@Override
	public void removeProcedure(Procedure procedure) {}

	@Override
	public void updateProcedure(Procedure procedure) {}

}
