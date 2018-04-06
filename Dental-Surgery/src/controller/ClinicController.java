package controller;

import model.Clinic;
import model.Invoice;
import model.Patient;
import model.Payment;
import model.Procedure;
import model.ProcedureType;
import view.MainScreen;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ClinicController {

	/* --------------------------------
	 *       PROPERTIES
	 * -------------------------------*/
	private static final String CLINICFILENAME = "src/data/clinic.ser";
	private Clinic clinic;
	private Boolean isSaved;
	public ObservableList<ProcedureType> procedureTypes;
	public ObservableList<Patient> patients;

	
	/* --------------------------------
	 *       CONSTRUCTOR
	 * -------------------------------*/
	
	public ClinicController() {
		clinic = getClinicFromSerial();

		if (clinic == null) {
			clinic = Clinic.getInstance();
			clinic.setPatientList(getPatientListFromCSV());
			clinic.setProcedureTypesList(getProcedureListFromCSV());
			patients = ArrayListToObservableList(clinic.getPatients());
			procedureTypes = ArrayListToObservableList(clinic.getProcedureTypes());
			System.out.println("  New database created from CSV with sample patients: " + patients.size());
			System.out.println("  and sample procedures: " + procedureTypes.size());
			saveClinicToSerial();
		} else {
			patients = ArrayListToObservableList(clinic.getPatients());
			procedureTypes = ArrayListToObservableList(clinic.getProcedureTypes());
			System.out.println("-> Database loaded from serial file. Patients: " + patients.size() + ", Procedures: " + procedureTypes.size());
		}

		// Getting max. Ids for patients, procedure types, procedures, invoices and payments
		// so new ones don't get repeated Ids.
		Patient.setMaxId(getPatientMaxId());
		ProcedureType.setMaxId(getProcedureTypeMaxId());
		Invoice.setMaxId(getInvoiceMaxId());
		Procedure.setMaxId(getProcedureMaxId());
		Payment.setMaxId(getPaymentMaxId());
		
		int a = getPatientMaxId();
		int b = getProcedureTypeMaxId();
		int c = getInvoiceMaxId();
		int d = getProcedureMaxId();
		int e = getPaymentMaxId();
		
		System.out.println("Max Id Patients: " + a);
		System.out.println("Max Id Procedure Types: " + b);
		System.out.println("Max Id Invoices: " + c);
		System.out.println("Max Id Procedures: " + d);
		System.out.println("Max Id Payments: " + e);
		
		setSaved(true);
	}
	

	/* --------------------------------
	 *       METHODS
	 * -------------------------------*/
	public ObservableBooleanValue getObservableSaved() {
		return new SimpleBooleanProperty(this.isSaved());
	}
	public Boolean isSaved() { return isSaved; }
	public void setSaved(Boolean b) { this.isSaved = b; }
	
	public void addPatient(Patient newPatient) {
		patients.add(newPatient);
		unsavedChanges();
	}
	
	public void addProcedure(ProcedureType newProcedure) {
		procedureTypes.add(newProcedure);
		unsavedChanges();
	}
	
	private int getPatientMaxId() {
		int id = 0;
		for (Patient p : patients) {
			if (p.getId() > id) { id = p.getId(); }
		}
		return id;
	}
	
	private int getProcedureTypeMaxId() {
		int id = 0;
		for (ProcedureType p : procedureTypes) {
			if (p.getId() > id) { id = p.getId(); }
		}
		return id;
	}
	
	private int getPaymentMaxId() {
		int id = 0;
		
		for (Patient pat : patients) {
			for (Invoice inv : pat.getInvoices()) {
				for (Payment pay : inv.getPayments()) {
					if (pay.getId() > id) {
						id = pay.getId();
					}
				}
			}
		}
		return id;
	}
	
	private int getProcedureMaxId() {
		int id = 0;
		
		for (Patient pat : patients) {
			for (Invoice inv : pat.getInvoices()) {
				for (Procedure proc : inv.getProcedures()) {
					if (proc.getId() > id) {
						id = proc.getId();
					}
				}
			}
		}
		return id;
	}
	
	private int getInvoiceMaxId() {
		int id = 0;

		for (Patient pat : patients) {
			for (Invoice inv : pat.getInvoices()) {
				if (inv.getId() > id) {
					id = inv.getId();
				}
			}
		}
		return id;
	}
	
	public void addPatientsFromCSV() {
		clinic.getPatients().addAll(getPatientListFromCSV());
		patients = ArrayListToObservableList(clinic.getPatients());
		setSaved(false);
	}
	
	private ArrayList<Patient> getPatientListFromCSV() {
		ArrayList<Patient> plist = new ArrayList<Patient>();
		String csvFile = "src/data/patients.csv";
		String fieldDelimiter = ",";

		BufferedReader br;

		try {
			br = new BufferedReader(new FileReader(csvFile));
			String line;
			while ((line = br.readLine()) != null) {
//				System.out.println("Line: " + line);
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
	}
	
	private ArrayList<ProcedureType> getProcedureListFromCSV() {
		ArrayList<ProcedureType> procList = new ArrayList<ProcedureType>();
		String csvFile = "src/data/procedures.csv";
		String fieldDelimiter = "\\|";
//		String fieldDelimiter = ",";

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
	}
	
	/**
	 * Returns serialized Clinic object
	 * @return clinic: Clinic object
	 */
	private Clinic getClinicFromSerial() {
		Clinic clinic = (Clinic) FileStorage.readObject(CLINICFILENAME);
		return clinic;
	}
	
	/**
	 * SAVING
	 */
	
	public void saveClinicToSerial() {
		ArrayList<Patient> patientList = ObservableListToArrayList(patients);
		ArrayList<ProcedureType> procedureList = ObservableListToArrayList(procedureTypes);
		
		clinic.setPatientList(patientList);
		clinic.setProcedureTypesList(procedureList);
		
		try {
			FileStorage.storeObject(this.clinic, CLINICFILENAME);
			System.out.println("<- Data saved to: " + CLINICFILENAME);
		} catch (Exception ex) {
//			System.out.println("Error writting serial file - " + ex);
		}
	}
	
	
	private <T> ObservableList<T> ArrayListToObservableList(ArrayList<T> alist) {
			return FXCollections.observableArrayList(alist);
	}
	
	private <T> ArrayList<T> ObservableListToArrayList(ObservableList<T> olist) {
		ArrayList<T> alist = (ArrayList<T>) olist.stream().collect(Collectors.toList());
		return alist;
	}
	
	
	
	public void unsavedChanges() {
		setSaved(false);
		MainScreen.getInstance().getStage().setTitle(MainScreen.APP_TITLE + "  (Unsaved changes)");
		String statusText = MainScreen.getInstance().getStatusText();
		MainScreen.getInstance().setStatusText(statusText.trim() + " *");
		MainScreen.getInstance().showSaveButtons(!isSaved());
	}
	public void savedChanges() {
		setSaved(true);
		MainScreen.getInstance().getStage().setTitle(MainScreen.APP_TITLE);
		MainScreen.getInstance().setStatusText("All changes saved");
		MainScreen.getInstance().showSaveButtons(!isSaved());
	}




	
}
