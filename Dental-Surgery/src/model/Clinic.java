package model;

import java.io.Serializable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Clinic implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Clinic instance;
	private ObservableList<Patient> list;
	
	public static Clinic getInstance() {
		if (instance == null) { return new Clinic(); } 
		else { return instance; }
	}
	
	public Clinic() {
		instance = this;
		go();
	}

	public void go() {
		list = FXCollections.observableArrayList();
//		
	}

	public ObservableList<Patient> getList() { return list; }
	public void setList(ObservableList<Patient> list) { this.list = list;	}


	
	
}
