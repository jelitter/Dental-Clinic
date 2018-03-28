package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Clinic;
import model.Patient;

public class ClinicController {

	private boolean isSaved;
	private static ClinicController instance;
	private Clinic clinic;

	public static ClinicController getInstance() {
		if (instance == null) { return new ClinicController(); } 
		else { return instance; }
	}
	
	public ClinicController() {
		instance = this;
		go();
	}
	
	public void go() {
		clinic = Clinic.getInstance(); 
		clinic.setList(getPatientsFromCSV());
	}

	
	/*
	 * METHODS
	 */
	
	public boolean isSaved() { return isSaved; }
	public void setSaved(Boolean b) { this.isSaved = b; }
	
	public void addPatient(Patient newPatient) {
		clinic.getList().add(newPatient);
	}
	
	public ObservableList<Patient> getPatients() {
		return clinic.getList();
	}
	private void setPatients(ObservableList<Patient> patients) {
		clinic.setList(patients);
	}

	public void save() {
		// TODO Auto-generated method stub
		setSaved(true);
		System.out.println("Controller - Data saved.");
	}
	
	public void loadPatientsFromCSV() {
		setPatients(getPatientsFromCSV());
		System.out.println("Data loaded from CSV file.");
		setSaved(false);
	}
	
	public void loadPatientsFromSerial() {
		setPatients(getPatientsFromSerial());
		System.out.println("Data loaded from serial file.");
		setSaved(false);
	}
	
	private ObservableList<Patient> getPatientsFromCSV() {
		ObservableList<Patient> plist = FXCollections.observableArrayList();
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
	}
	
	private ObservableList<Patient> getPatientsFromSerial() {
		ObservableList<Patient> plist = FXCollections.observableArrayList();
		try {
			plist = (ObservableList<Patient>) FileStorage.readObservableObject("src/data/patientData.ser");
		} catch (FileNotFoundException a) {
			System.out.println("Error reading from serial file - " + a.getMessage() + "\nReading from CSV");
		} 
		return plist;
	}
}
