package controller.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controller.ClinicController;
import model.Clinic;
import model.Invoice;
import model.Patient;
import model.Payment;
import model.Procedure;
import model.ProcedureType;

/**
 * Interface for ClinicFileController and ClinicDBController
 */
public abstract class AbstractClinicStorageController {
	
	public abstract Clinic getClinicFromStorage();
	public abstract void saveClinicToStorage(ClinicController clinicController);
	
	public final ArrayList<Patient> getPatientListFromCSV() {
		ArrayList<Patient> plist = new ArrayList<Patient>();
		String csvFile = "src/data/patients.csv";
		String fieldDelimiter = ",";

		BufferedReader br;

		try {
			br = new BufferedReader(new FileReader(csvFile));
			String line;
			while ((line = br.readLine()) != null) {
				String[] fields = line.split(fieldDelimiter, -1);
				Patient record = new Patient(fields[4], fields[3], fields[2], fields[0], fields[1]);
				plist.add(record);
			}
		} catch (FileNotFoundException ex) {
			System.out.println("Error - Serial file not found - " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("Error - Serial file IO exception - " + ex.getMessage());
		}
		return plist;		
	};
	public final ArrayList<ProcedureType> getProcedureListFromCSV() {
		ArrayList<ProcedureType> procList = new ArrayList<ProcedureType>();
		String csvFile = "src/data/procedures.csv";
		String fieldDelimiter = "\\|";

		BufferedReader br;

		try {
			br = new BufferedReader(new FileReader(csvFile));
			String line;
			while ((line = br.readLine()) != null) {
				String[] fields = line.split(fieldDelimiter, -1);
				ProcedureType proc = new ProcedureType(fields[0].trim(), fields[1].trim(), Double.parseDouble(fields[2].trim()));
				procList.add(proc);
			}
		} catch (FileNotFoundException ex) {
			System.out.println("Error - Serial file not found - " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("Error - Serial file IO exception - " + ex.getMessage());
		}
		return procList;
	};

	public abstract Patient getPatientById(int id);

	public abstract ProcedureType getProcedureTypeById(int id);

	public abstract List<ProcedureType> getProcedureTypes();
	public abstract List<Patient> getPatients();
	public abstract List<Invoice> getInvoices(Patient pat);
	public abstract List<Payment> getPayments(Invoice inv);
	public abstract List<Procedure> getProcedures(Invoice inv, List<ProcedureType> procedureTypes);

	public abstract void addPatient(Patient newPatient);
	public abstract void removePatient(Patient newPatient);
	public abstract  void updatePatient(Patient patient);
	
	public abstract void addInvoice(Patient patient);
	public abstract void removeInvoice(Invoice invoice);
	public abstract void updateInvoice(Invoice invoice);
	
	public abstract void addPayment(Payment payment);
	public abstract void removePayment(Payment payment);
	public abstract void updatePayment(Payment payment);
	
	public abstract void addProcedure(Procedure procedure);
	public abstract void removeProcedure(Procedure procedure);
	public abstract void updateProcedure(Procedure procedure);

}













