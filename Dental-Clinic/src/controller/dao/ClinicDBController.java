package controller.dao;

import controller.ClinicController;
import model.Clinic;

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
		
		db.getDBConnection();
		db.QueryDB("SELECT * FROM Clinic");
		result = (Clinic) db.rs;
		db.CloseDB();
		return result;
	}
}
