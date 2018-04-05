package controller;

import model.Clinic;
import model.Patient;
import model.Procedure;
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
	public ObservableList<Procedure> procedures;
	public ObservableList<Patient> patients;

	
	/* --------------------------------
	 *       CONSTRUCTOR
	 * -------------------------------*/
	
	public ClinicController() {
		clinic = getClinicFromSerial();

		if (clinic == null) {
			clinic = Clinic.getInstance();
			clinic.setPatientList(getPatientListFromCSV());
			clinic.setProcedureList(getProcedureListFromCSV());
			patients = ArrayListToObservableList(clinic.getPatients());
			procedures = ArrayListToObservableList(clinic.getProcedures());
			System.out.println("  New database created from CSV with sample patients: " + patients.size());
			System.out.println("  and sample procedures: " + procedures.size());
			saveClinicToSerial();
		} else {
			patients = ArrayListToObservableList(clinic.getPatients());
			procedures = ArrayListToObservableList(clinic.getProcedures());
			System.out.println("-> Database loaded from serial file. Patients: " + patients.size() + ", Procedures: " + procedures.size());
		}

		// Getting patient max. Id so new patients don't overwrite previous ones
		Patient.setMaxId(getPatientMaxId());
		Procedure.setMaxId(getProcedureMaxId());
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
	
	public void addProcedure(Procedure newProcedure) {
		procedures.add(newProcedure);
		unsavedChanges();
	}
	
	private int getPatientMaxId() {
		int id = -1;
		for (Patient p : patients) {
			if (p.getId() > id) { id = p.getId(); }
		}
		return id;
	}
	
	private int getProcedureMaxId() {
		int id = -1;
		for (Procedure p : procedures) {
			if (p.getId() > id) { id = p.getId(); }
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
	
	private ArrayList<Procedure> getProcedureListFromCSV() {
		ArrayList<Procedure> procList = new ArrayList<Procedure>();
		String csvFile = "src/data/procedures.csv";
		String fieldDelimiter = "\\|";
//		String fieldDelimiter = ",";

		BufferedReader br;

		try {
			br = new BufferedReader(new FileReader(csvFile));
			String line;
			while ((line = br.readLine()) != null) {
//				System.out.println("Line:\n" + line);
				String[] fields = line.split(fieldDelimiter, -1);
				
//				System.out.println(Arrays.toString(fields));
				
				Procedure proc = new Procedure(fields[0].trim(), fields[1].trim(), Double.parseDouble(fields[2].trim()));
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
//		System.out.println("Read clinic from serial file: " + (clinic != null));
		return clinic;
	}
	
	/**
	 * SAVING
	 */
	
	public void saveClinicToSerial() {
		ArrayList<Patient> patientList = ObservableListToArrayList(patients);
		ArrayList<Procedure> procedureList = ObservableListToArrayList(procedures);
		
		clinic.setPatientList(patientList);
		clinic.setProcedureList(procedureList);
		
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
