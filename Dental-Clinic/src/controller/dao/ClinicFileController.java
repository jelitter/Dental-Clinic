package controller.dao;

import java.util.ArrayList;

import controller.ClinicController;
import model.Clinic;
import model.Patient;
import model.ProcedureType;

public class ClinicFileController extends AbstractClinicStorageController {
	
	private static final String CLINICFILENAME = "src/data/clinic.ser";
	private SerialFileStorage sf;
	
	public ClinicFileController() {
		sf = new SerialFileStorage();
	}
	
	public Clinic getClinicFromStorage() {
		Clinic clinic = (Clinic) sf.readObject(CLINICFILENAME);
		return clinic;
	}
	
	public void saveClinicToStorage(ClinicController cc) {
		ArrayList<Patient>       patientList   = cc.patientsAsList();
		ArrayList<ProcedureType> procedureList = cc.procedureTypesAsList();
		
		cc.getClinic().setPatientList(patientList);
		cc.getClinic().setProcedureTypesList(procedureList);
		
		try {
			sf.storeObject(cc.getClinic(), CLINICFILENAME);
			// System.out.println("<- Data saved to: " + CLINICFILENAME);
		} catch (Exception ex) {
			// System.out.println("Error writting serial file - " + ex);
		}
	}

}
