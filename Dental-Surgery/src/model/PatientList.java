package model;

import java.io.Serializable;
import java.util.ArrayList;

public class PatientList implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<Patient> list;
	
	public PatientList(ArrayList<Patient> list) {
		this.setList(list);
	}

	public ArrayList<Patient> getList() { return list; }
	public void setList(ArrayList<Patient> list) { this.list = list;	}
	
	public void addPatient(Patient patient) {
		
	}

	public void removePatient(Patient patient) {
		
	}
	
	public void save() {
		//	TO-DO: Save to serial / DB
	}
	
}
