package controller.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import controller.ClinicController;
import model.Clinic;
import model.Patient;
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

}
