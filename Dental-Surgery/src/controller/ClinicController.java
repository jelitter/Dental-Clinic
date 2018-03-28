package controller;

import model.Clinic;
import model.Patient;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ClinicController {

	/* --------------------------------
	 *       PROPERTIES
	 * -------------------------------*/
	private static final String CLINICFILENAME = "src/data/patients.ser";
	private Clinic clinic;
	private boolean isSaved;
	public ObservableList<Patient> patients;

	
	/* --------------------------------
	 *       CONSTRUCTOR
	 * -------------------------------*/
	
	public ClinicController() {
		clinic = getClinicFromSerial();

		if (clinic == null) {
			clinic = Clinic.getInstance();
			clinic.setList(getPatientListFromCSV());
			patients = ArrayListToObservableList(clinic.getList());
			System.out.println("  New database created. Patients: " + patients.size());
		} else {
			patients = ArrayListToObservableList(clinic.getList());
			System.out.println("  Database loaded from serial file. Patients: " + patients.size());
		}
		Patient.setMaxId(getMaxId());
		System.out.println("Patient max Id: " + getMaxId());
		setSaved(true);
	}
	

	
	/* --------------------------------
	 *       METHODS
	 * -------------------------------*/
	
	public boolean isSaved() { return isSaved; }
	public void setSaved(Boolean b) { this.isSaved = b; }
	
	public void addPatient(Patient newPatient) {
		patients.add(newPatient);
		setSaved(false);
	}
	
	private int getMaxId() {
		int id = -1;
		for (Patient p : patients) {
			if (p.getId() > id) { id = p.getId(); }
		}
		return id;
	}
	
	public void addPatientsFromCSV() {
		clinic.getList().addAll(getPatientListFromCSV());
		patients = ArrayListToObservableList(clinic.getList());
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
	
	/**
	 * Returns serialized Clinic object
	 * @return clinic: Clinic object
	 */
	private Clinic getClinicFromSerial() {
		Clinic clinic = (Clinic) FileStorage.readObject(CLINICFILENAME);
		System.out.println("Read clinic from serial file: " + (clinic != null));
		return clinic;
	}
	
	/**
	 * SAVING
	 */
	
	public void saveClinicToSerial() {
		ArrayList<Patient> list = ObservableListToArrayList(patients);
		clinic.setList(list);
		
		System.out
				.println("Tryng to save Clinic with a list of " + clinic.getList().size() + " items to serial file...");
		try {
			FileStorage.storeObject(this.clinic, CLINICFILENAME);
		} catch (Exception ex) {
			System.out.println("Error writting serial file - " + ex);
		}
	}
	
	
	private ObservableList<Patient> ArrayListToObservableList(ArrayList<Patient> alist) {
			return FXCollections.observableArrayList(alist);
	}
	
	private ArrayList<Patient> ObservableListToArrayList(ObservableList<Patient> olist) {
		ArrayList<Patient> alist = (ArrayList<Patient>) olist.stream().collect(Collectors.toList());
		return alist;
	}
	
}
