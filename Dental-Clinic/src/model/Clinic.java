package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Clinic implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Clinic instance;
	private ArrayList<Patient> patients;
	private ArrayList<ProcedureType> procedureTypes;

	public static Clinic getInstance() {
		if (instance == null) {
			return new Clinic();
		} else {
			return instance;
		}
	}

	private Clinic() {
		instance = this;
		go();
	}

	public void go() {
		patients = new ArrayList<Patient>();
		procedureTypes = new ArrayList<ProcedureType>();
	}
	
	public ArrayList<Patient> getPatients() {
		return this.patients;
	}
	
	public ArrayList<ProcedureType> getProcedureTypes() {
		return this.procedureTypes;
	}

	public void setPatientList(ArrayList<Patient> patients) {
		this.patients = patients;
	}
	public void setProcedureTypesList(ArrayList<ProcedureType> procedureTypes) {
		this.procedureTypes = procedureTypes;
	}
}
