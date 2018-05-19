package controller.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.ClinicController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Clinic;
import model.Invoice;
import model.Patient;
import model.Payment;
import model.Procedure;
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

	// public Patient getPatientById(int id);

	@Override
	public Patient getPatientById(int id) {
		Patient pat = null;
		db.getDBConnection();
		db.QueryDB("SELECT * FROM Patients WHERE patientId=" + id);
		try {
			while (db.rs.next()) {
				int patientId = db.rs.getInt("patientId");
				String firstName = db.rs.getString("firstName");
				String lastName = db.rs.getString("lastName");
				String email = db.rs.getString("email");
				String address = db.rs.getString("address");
				String phone = db.rs.getString("phone");

				pat = new Patient(patientId, firstName, lastName, email, address, phone);
				pat.print();
			}
		} catch (SQLException e) {
			System.out.println("Error reading patient");
			e.printStackTrace();
		} finally {
			db.CloseDB();
		}
		return pat;
	}

	@Override
	public ProcedureType getProcedureTypeById(int id) {
		ProcedureType pt = null;
		db.getDBConnection();
		db.QueryDB("SELECT * FROM ProcedureTypes WHERE procedureTypeId=" + id);
		try {
			while (db.rs.next()) {
				int procedureTypeId = db.rs.getInt("procedureTypeId");
				String name = db.rs.getString("name");
				String description = db.rs.getString("description");
				double price = db.rs.getDouble("price");

				pt = new ProcedureType(procedureTypeId, name, description, price);
				pt.print();
			}
		} catch (SQLException e) {
			System.out.println("Error reading patient");
			e.printStackTrace();
		} finally {
			db.CloseDB();
		}
		return pt;
	}

	@Override
	public List<Patient> getPatients() {
		Patient pat = null;
		ObservableList<Patient> patients = FXCollections.observableArrayList();
		db.getDBConnection();
		db.QueryDB("SELECT * FROM Patients");
		try {
			while (db.rs.next()) {
				int patientId = db.rs.getInt("patientId");
				String firstName = db.rs.getString("firstName");
				String lastName = db.rs.getString("lastName");
				String email = db.rs.getString("email");
				String address = db.rs.getString("address");
				String phone = db.rs.getString("phone");

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
	
	@Override
	public List<Invoice> getInvoices(Patient pat) {
		Invoice inv = null;
		ObservableList<Invoice> invoices = FXCollections.observableArrayList();
		db.getDBConnection();
		db.QueryDB("SELECT * FROM Invoices WHERE patientId=" + pat.getId());
		try {
			while (db.rs.next()) {
				int invoiceId = db.rs.getInt("invoiceId");
				Double amount = db.rs.getDouble("amount");
				Double amountPaid = db.rs.getDouble("amountPaid");
				Date invoiceDate = db.rs.getDate("invoiceDate");

				inv = new Invoice(invoiceId);
				inv.setAmount(amount);
				inv.setAmountPaid(amountPaid);
				inv.setDate(invoiceDate);
				invoices.add(inv);
				inv.print();
			}
		} catch (SQLException e) {
			System.out.println("Error reading invoices for patientId " + pat.getId());
			e.printStackTrace();
		} finally {
			db.CloseDB();
		}
		return invoices;
	}
	
	@Override
	public List<Payment> getPayments(Invoice inv) {
		Payment paym = null;
		ObservableList<Payment> payments = FXCollections.observableArrayList();
		db.getDBConnection();
		db.QueryDB("SELECT * FROM Payments WHERE invoiceId=" + inv.getId());
		try {
			while (db.rs.next()) {
				int paymentId = db.rs.getInt("paymentId");
				int invoiceId = db.rs.getInt("invoiceId");
				Double amount = db.rs.getDouble("amount");
				Date date = db.rs.getDate("date");

				paym = new Payment(paymentId, invoiceId, amount, date);
				payments.add(paym);
				paym.print();
			}
		} catch (SQLException e) {
			System.out.println("Error reading payments for invoiceId " + inv.getId());
			e.printStackTrace();
		} finally {
			db.CloseDB();
		}
		return payments;
	}

	@Override
	public List<Procedure> getProcedures(Invoice inv) {
		Procedure proc = null;
		ObservableList<Procedure> procedures = FXCollections.observableArrayList();
		db.getDBConnection();
		db.QueryDB("SELECT * FROM Procedures WHERE invoiceId=" + inv.getId());
		try {
			while (db.rs.next()) {
				int procedureId = db.rs.getInt("procedureId");
				int invoiceId = db.rs.getInt("invoiceId");
				int procedureType =  db.rs.getInt("procedureType");

				
				proc = new Procedure(procedureId, invoiceId, procedureType);
				procedures.add(proc);
				proc.print();
			}
		} catch (SQLException e) {
			System.out.println("Error reading procedures for invoiceId " + inv.getId());
			e.printStackTrace();
		} finally {
			db.CloseDB();
		}
		return procedures;
	}
	
	@Override
	public	List<ProcedureType> getProcedureTypes() {
		ProcedureType pt = null;
		ObservableList<ProcedureType> ptypes = FXCollections.observableArrayList();

		db.getDBConnection();
		db.QueryDB("SELECT * FROM ProcedureTypes");
		try {
			while (db.rs.next()) {
				int procedureTypeId = db.rs.getInt("procedureTypeId");
				String name = db.rs.getString("name");
				String description = db.rs.getString("description");
				double price = db.rs.getDouble("price");

				pt = new ProcedureType(procedureTypeId, name, description, price);
				ptypes.add(pt);
				pt.print();
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
		
		Clinic.getInstance().setProcedureTypesList(new ArrayList<ProcedureType>(this.getProcedureTypes()));
		
		// Get all patients from Patients table
		// For every patient get invoices list
		//   For every invoice get payments
		//   For every invoice get procedures
		
		Clinic.getInstance().setPatientList(new ArrayList<Patient>(this.getPatients()));

		for (Patient pat : Clinic.getInstance().getPatients()) {
			pat.setInvoices(new ArrayList<Invoice>(this.getInvoices(pat)));
			for (Invoice inv : pat.getInvoices()) {
				inv.setPayments(new ArrayList<Payment>(this.getPayments(inv)));
				inv.setProcedures(new ArrayList<Procedure>(this.getProcedures(inv)));
			}
		}
		
		
	}
	
	@Override
	public void addPatient(Patient newPatient) {
		String query = "INSERT INTO Patients"
				 + " values(null, \"" + newPatient.getFirstName()
				 + "\", \""  + newPatient.getLastName()
				 + "\", \"" + newPatient.getEmail()
				 + "\", \"" + newPatient.getAddress()
				 + "\", \"" + newPatient.getPhoneNumber() + "\")";
		System.out.println("Inserting:\n" + query);
		db.getDBConnection();
		db.Execute(query);
		db.CloseDB();		
	}
	
	@Override
	public void updatePatient(Patient patient) {

		String query = "UPDATE Patients"
				 + " SET firstName=\"" + patient.getFirstName()
				 + "\", lastName=\""  + patient.getLastName()
				 + "\", email=\"" + patient.getEmail()
				 + "\", phone=\"" + patient.getPhoneNumber()
				 + "\", address=\"" + patient.getAddress()
				 + "\" WHERE patientId=" + patient.getId();
		System.out.println("Updating:\n" + query);
		db.getDBConnection();
		db.Execute(query);
		db.CloseDB();
	}

	@Override
	public void removePatient(Patient patient) {
		String query = "DELETE FROM Patients WHERE patientId=" + patient.getId();
		System.out.println("Deleting:\n" + query);
		db.getDBConnection();
		db.Execute(query);
		db.CloseDB();		
	}

	@Override
	public void addInvoice(Patient patient) {
		String query = "INSERT INTO Invoices (patientId) values(" + patient.getId() + ")";
		System.out.println("Invoices - Inserting:\n" + query);
		db.getDBConnection();
		db.Execute(query);
		db.CloseDB();
		
	}


	
}
