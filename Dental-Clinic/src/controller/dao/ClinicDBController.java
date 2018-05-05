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
		return getClinicFromDB();
	}

	@Override
	public void saveClinicToStorage(ClinicController clinicController) {
		// TODO Auto-generated method stub

	}
	
	private Clinic getClinicFromDB() {
		Clinic result = null;
		Patient pat = null;
		ProcedureType pt = null;
		
		db.getDBConnection();
		db.QueryDB("SELECT * FROM Patients");
		result = (Clinic) db.rs;
		
		try {
			while(db.rs.next()){
				//create your object from teh resultset here
//				pat = db.rs.n
			}
		} catch (SQLException e) {
			System.out.println("Error reading patient");
			e.printStackTrace();
		}
		
		
		db.CloseDB();
		return result;
	}
}
