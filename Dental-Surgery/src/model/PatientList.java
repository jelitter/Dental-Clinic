package model;

import java.io.Serializable;
import java.util.ArrayList;

public class PatientList implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<Patient> list;
	
	public PatientList(ArrayList<Patient> list) {
		this.set(list);
	}

	public void set(ArrayList<Patient> list) { this.list = list;	}
	public ArrayList<Patient> get() { return this.list;	}

	public void addPatient(Patient patient) { list.add(patient);	}
	public void removePatient(Patient patient) { list.remove(patient); }
}
