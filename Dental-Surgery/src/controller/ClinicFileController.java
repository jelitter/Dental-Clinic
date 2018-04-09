package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Clinic;
import model.Patient;
import model.ProcedureType;

public class ClinicFileController {
	
	private static final String CLINICFILENAME = "src/data/clinic.ser";

	public ClinicFileController() {
		
	}
	
	protected ArrayList<Patient> getPatientListFromCSV() {
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
	
	protected ArrayList<ProcedureType> getProcedureListFromCSV() {
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
	}
	
	protected Clinic getClinicFromSerial() {
		Clinic clinic = (Clinic) FileStorage.readObject(CLINICFILENAME);
		return clinic;
	}
	
	protected void saveClinicToSerial(ClinicController cc) {
		ArrayList<Patient> patientList = ObservableListToArrayList(cc.patients);
		ArrayList<ProcedureType> procedureList = ObservableListToArrayList(cc.procedureTypes);
		
		cc.clinic.setPatientList(patientList);
		cc.clinic.setProcedureTypesList(procedureList);
		
		try {
			FileStorage.storeObject(cc.clinic, CLINICFILENAME);
			// System.out.println("<- Data saved to: " + CLINICFILENAME);
		} catch (Exception ex) {
			// System.out.println("Error writting serial file - " + ex);
		}
	}
	
	
	private <T> ObservableList<T> ArrayListToObservableList(ArrayList<T> alist) {
			return FXCollections.observableArrayList(alist);
	}
	
	private <T> ArrayList<T> ObservableListToArrayList(ObservableList<T> olist) {
		ArrayList<T> alist = (ArrayList<T>) olist.stream().collect(Collectors.toList());
		return alist;
	}

	public <T> ObservableList<T> getObservableList(ArrayList<T> items) {
		return ArrayListToObservableList(items);
	}
	
}
