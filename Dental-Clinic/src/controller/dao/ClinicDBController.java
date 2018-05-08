package controller.dao;

import java.sql.SQLException;

import controller.ClinicController;
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
	
	private void getClinicFromDB() {
		Patient pat = null;
		ProcedureType pt = null;
		
		db.getDBConnection();
		db.QueryDB("SELECT * FROM Patients");
		try {
			while(db.rs.next()){
				int patientId = db.rs.getInt("patientId");
				String firstName = db.rs.getString("firstName");
				String lastName = db.rs.getString("lastName");
				String email = db.rs.getString("email");
				String address = db.rs.getString("address");
				String phone = db.rs.getString("phone");
				
				pat = new Patient(firstName, lastName, email, address, phone);
				Clinic.getInstance().getPatients().add(pat);
				pat.print();
				
			}
		} catch (SQLException e) {
			System.out.println("Error reading patient");
			e.printStackTrace();
		}
		
		db.QueryDB("SELECT * FROM ProcedureTypes");
		try {
			while(db.rs.next()){
				int procedureTypeId = db.rs.getInt("procedureTypeId");
				String name = db.rs.getString("name");
				String description = db.rs.getString("description");
				double price= db.rs.getDouble("price");
				
				pt = new ProcedureType(name, description, price);
				Clinic.getInstance().getProcedureTypes().add(pt);
				System.out.println(pt.toString());
			}
		} catch (SQLException e) {
			System.out.println("Error reading patient");
			e.printStackTrace();
		}
		
		
		db.CloseDB();
//		return result;
	}
}
