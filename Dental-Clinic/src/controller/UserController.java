package controller;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import model.Clinic;
import model.Dentist;
import model.Invoice;
import model.Patient;
import model.Payment;
import model.Procedure;
import model.ProcedureType;

public class UserController implements Serializable {

	/* --------------------------------
	 *       PROPERTIES
	 * -------------------------------*/
	private static final long serialVersionUID = 1L;
	private UserFileController fc;
	public ArrayList<Dentist> users;

	
	/* --------------------------------
	 *       CONSTRUCTOR
	 * -------------------------------*/
	
	public UserController() {
		fc = new UserFileController();
		users = fc.getUsersFromSerial();

		if (users == null) {
			users = fc.getUserListFromCSV();
			fc.saveUsersToSerial(users);
		} 

		// Getting max. Ids for users
		// so new ones don't get repeated Ids.
		Dentist.setMaxId(getDentistMaxId());
	}


	private int getDentistMaxId() {
		int id = -1;
		for (Dentist d: users ) {
			if (d.getId() > id) {
				id = d.getId();
			}
		}
		return id;
	}


	public boolean validateLogin(String user, String pass) {
		for (Dentist d: users ) {
			if (d.getUsername().equals(user) && d.getPassword().equals(pass)) {
				return true;
			}
		}
		return false;
	}

	public void saveLogin(String user, String pass) {
		fc.saveLogin(user, pass);
	} 
	public Dentist loadLogin() {
		return fc.loadLogin();
	}
	
}
