package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Clinic implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Clinic instance;
	private ArrayList<Patient> list;

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
		list = new ArrayList<Patient>();
	}
	
	public ArrayList<Patient> getList() {
		return this.list;
	}

	public void setList(ArrayList<Patient> list) {
		this.list = list;
	}

}
