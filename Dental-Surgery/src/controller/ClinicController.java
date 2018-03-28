package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Clinic;
import model.Patient;

public class ClinicController implements Serializable {

	private static final long serialVersionUID = 1L;
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
//		clinic.setList(getPatientsFromCSV());
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
		ObservableList<Patient> oList = FXCollections.observableArrayList(clinic.getList());
		return oList;
		
	}
	private void setPatients(ArrayList<Patient> patients) {
		clinic.setList(patients);
	}


	
	/**
	 * LOADING
	 */
	
	public void loadPatientsFromCSV() {
		ArrayList<Patient> list = getPatientsFromCSV();
		clinic.setList(list);
		System.out.println("Data loaded from CSV file. Items: " + list.size());
		setSaved(true);
	}
	
	public void loadPatientsFromSerial() {
		ArrayList<Patient> list = getPatientsFromSerial();
		setPatients(list);
		System.out.println("Data loaded from serial file. Items: " + list.size());
		setSaved(true);
	}
	
	private ArrayList<Patient> getPatientsFromCSV() {
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
	}
	
	private ArrayList<Patient> getPatientsFromSerial() {
		ArrayList<Patient> list = null;
		try {
			list = (ArrayList<Patient>) FileStorage.readObject("src/data/patientData.ser");
		} catch (Exception ex) {
			System.out.println("Error reading from serial file - " + ex);
		} 
		return list;
	}
	
	/**
	 * SAVING
	 */
	
	public void savePatientsToSerial() {
		ArrayList<Patient> list = clinic.getList();
		System.out.println("Tryng to save list with " + list.size() + " items to serial file...");
		try {
			FileStorage.storeObject(list, "src/data/patientData.ser");
		} catch (Exception ex) {
			System.out.println("Error writting serial file - " + ex);
		}
	}
}
