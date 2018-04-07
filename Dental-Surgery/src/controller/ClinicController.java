package controller;

import model.Clinic;
import model.Invoice;
import model.Patient;
import model.Payment;
import model.Procedure;
import model.ProcedureType;
import view.MainScreen;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.WritableIntegerValue;
import javafx.collections.ObservableList;


public class ClinicController {

	/* --------------------------------
	 *       PROPERTIES
	 * -------------------------------*/
	private ClinicFileController fc;
	protected Clinic clinic;
	private Boolean isSaved;
	public ObservableList<ProcedureType> procedureTypes;
	public ObservableList<Patient> patients;

	
	/* --------------------------------
	 *       CONSTRUCTOR
	 * -------------------------------*/
	
	public ClinicController() {
		fc = new ClinicFileController();
		clinic = fc.getClinicFromSerial();

		if (clinic == null) {
			clinic = Clinic.getInstance();
			clinic.setPatientList(fc.getPatientListFromCSV());
			clinic.setProcedureTypesList(fc.getProcedureListFromCSV());
			patients = fc.getObservableList(clinic.getPatients());
			procedureTypes = fc.getObservableList(clinic.getProcedureTypes());
			System.out.println("  New database created from CSV with sample patients: " + patients.size());
			System.out.println("  and sample procedures: " + procedureTypes.size());
			fc.saveClinicToSerial(this);
		} else {
			patients = fc.getObservableList(clinic.getPatients());
			procedureTypes = fc.getObservableList(clinic.getProcedureTypes());
			System.out.println("-> Database loaded from serial file. Patients: " + patients.size() + ", Procedures: " + procedureTypes.size());
		}

		// Getting max. Ids for patients, procedure types, procedures, invoices and payments
		// so new ones don't get repeated Ids.
		Patient.setMaxId(getPatientMaxId());
		ProcedureType.setMaxId(getProcedureTypeMaxId());
		Invoice.setMaxId(getInvoiceMaxId());
		Procedure.setMaxId(getProcedureMaxId());
		Payment.setMaxId(getPaymentMaxId());
		setSaved(true);
	}
	

	/* --------------------------------
	 *       METHODS
	 * -------------------------------*/
	
	public ObservableBooleanValue getObservableSaved() {
		return new SimpleBooleanProperty(this.isSaved());
	}
	public Boolean isSaved() { return isSaved; }
	public void setSaved(Boolean b) { this.isSaved = b; }
	
	public void addPatient(Patient newPatient) {
		patients.add(newPatient);
		unsavedChanges();
	}
	
	public void addProcedure(ProcedureType newProcedure) {
		procedureTypes.add(newProcedure);
		unsavedChanges();
	}
	
	private int getPatientMaxId() {
		int id = 0;
		for (Patient p : patients) {
			if (p.getId() > id) { id = p.getId(); }
		}
		return id;
	}
	
	private int getProcedureTypeMaxId() {
		int id = 0;
		for (ProcedureType p : procedureTypes) {
			if (p.getId() > id) { id = p.getId(); }
		}
		return id;
	}
	
	private int getPaymentMaxId() {
		int id = 0;
		
		for (Patient pat : patients) {
			for (Invoice inv : pat.getInvoices()) {
				for (Payment pay : inv.getPayments()) {
					if (pay.getId() > id) {
						id = pay.getId();
					}
				}
			}
		}
		return id;
	}
	
	private int getProcedureMaxId() {
		int id = 0;
		
		for (Patient pat : patients) {
			for (Invoice inv : pat.getInvoices()) {
				for (Procedure proc : inv.getProcedures()) {
					if (proc.getId() > id) {
						id = proc.getId();
					}
				}
			}
		}
		return id;
	}
	
	private int getInvoiceMaxId() {
		int id = 0;

		for (Patient pat : patients) {
			for (Invoice inv : pat.getInvoices()) {
				if (inv.getId() > id) {
					id = inv.getId();
				}
			}
		}
		return id;
	}
	
	public void addPatientsFromCSV() {
		clinic.getPatients().addAll(fc.getPatientListFromCSV());
		patients = fc.getObservableList(clinic.getPatients());
		setSaved(false);
	}
	
	public void unsavedChanges() {
		setSaved(false);
		MainScreen.getInstance().getStage().setTitle(MainScreen.APP_TITLE + "  (Unsaved changes)");
		String statusText = MainScreen.getInstance().getStatusText();
		MainScreen.getInstance().setStatusText(statusText.trim() + " *");
		MainScreen.getInstance().showSaveButtons(!isSaved());
	}
	public void savedChanges() {
		setSaved(true);
		MainScreen.getInstance().getStage().setTitle(MainScreen.APP_TITLE);
		MainScreen.getInstance().setStatusText("All changes saved");
		MainScreen.getInstance().showSaveButtons(!isSaved());
	}

	public void save() {
		fc.saveClinicToSerial(this);
	}


	public IntegerProperty TotalNumberOfProceduresProperty() {
		int procs = 0;
		for (Patient pat : patients) {
			procs += pat.NumberOfProceduresProperty().get();
		}
		return new SimpleIntegerProperty(procs);
	}
	public IntegerProperty TotalNumberOfPaymentsProperty() {
		int payms = 0;
		for (Patient pat : patients) {
			payms += pat.NumberOfPaymentsProperty().get();
		}
		return new SimpleIntegerProperty(payms);
	}


	public DoubleProperty TotalAmountProperty() {
		double total = 0.;
		for (Patient pat : patients) { 
			for (Invoice inv : pat.getInvoices()) {
				total += inv.TotalAmountProperty().get();
			}
		}
		return new SimpleDoubleProperty(total);
	}
	
	public DoubleProperty TotalPaidProperty() {
		double paid = 0.;
		for (Patient pat : patients) { 
			for (Invoice inv : pat.getInvoices()) {
				paid += inv.TotalAmountPaidProperty().get();
			}
		}
		return new SimpleDoubleProperty(paid);
	}
	
	public DoubleProperty TotalPendingProperty() {
		double pending = 0.;
		for (Patient pat : patients) { 
			for (Invoice inv : pat.getInvoices()) {
				pending += inv.TotalAmountPendingProperty().get();
			}
		}
		return new SimpleDoubleProperty(pending);
	}



	
}
