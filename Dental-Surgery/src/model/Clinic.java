package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Clinic implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Clinic instance;
	private ArrayList<Patient> patients;
	private ArrayList<ProcedureType> procedures;

	public static Clinic getInstance() {
		if (instance == null) {
			return new Clinic();
		} else {
			return instance;
		}
	}

	public Clinic() {
		instance = this;
		go();
	}

	public void go() {
		patients = new ArrayList<Patient>();
		procedures = new ArrayList<ProcedureType>();
	}
	
	public ArrayList<Patient> getPatients() {
		return this.patients;
	}
	
	public ArrayList<ProcedureType> getProcedures() {
		return this.procedures;
	}

	public void setPatientList(ArrayList<Patient> patients) {
		this.patients = patients;
	}
	public void setProcedureList(ArrayList<ProcedureType> procedures) {
		this.procedures = procedures;
	}
}
