package controller.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.ClinicController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Clinic;
import model.Patient;
import model.ProcedureType;

public class ClinicDBController extends AbstractClinicStorageController {

	private DatabaseStorage db;

	public ClinicDBController() {
		this.db = new DatabaseStorage();
	}

	@Override
	public Clinic getClinicFromStorage() {
		getClinicFromDB();
		return Clinic.getInstance();
	}

	 @Override
	 public void saveClinicToStorage(ClinicController clinicController) {
	 // TODO Auto-generated method stub
	
	 }

	public List<Patient> getPatients() {
		Patient pat = null;
		ObservableList<Patient> patients = FXCollections.observableArrayList();
		db.getDBConnection();
		db.QueryDB("SELECT * FROM Patients");
		try {
			while (db.rs.next()) {
				int patientId    = db.rs.getInt("patientId");
				String firstName = db.rs.getString("firstName");
				String lastName  = db.rs.getString("lastName");
				String email     = db.rs.getString("email");
				String address   = db.rs.getString("address");
				String phone     = db.rs.getString("phone");

				pat = new Patient(patientId, firstName, lastName, email, address, phone);
				patients.add(pat);
				pat.print();
			}
		} catch (SQLException e) {
			System.out.println("Error reading patient");
			e.printStackTrace();
		} finally {
			db.CloseDB();
		}
		return patients;
	}

	List<ProcedureType> getProcedureTypes() {
		ProcedureType pt = null;
		ObservableList<ProcedureType> ptypes = FXCollections.observableArrayList();

		db.getDBConnection();
		db.QueryDB("SELECT * FROM ProcedureTypes");
		try {
			while (db.rs.next()) {
				int procedureTypeId = db.rs.getInt("procedureTypeId");
				String name          = db.rs.getString("name");
				String description  = db.rs.getString("description");
				double price         = db.rs.getDouble("price");

				pt = new ProcedureType(procedureTypeId, name, description, price);
				ptypes.add(pt);
				System.out.println(pt.toString());
			}
		} catch (SQLException e) {
			System.out.println("Error reading patient");
			e.printStackTrace();
		} finally {
			db.CloseDB();
		}
		return ptypes;
	}

	private void getClinicFromDB() {
		Clinic.getInstance().setPatientList(new ArrayList<Patient>(this.getPatients()));
		Clinic.getInstance().setProcedureTypesList(new ArrayList<ProcedureType>(this.getProcedureTypes()));
	}
}
