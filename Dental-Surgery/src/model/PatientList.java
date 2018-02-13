package model;

import java.util.ArrayList;

public class PatientList {

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
