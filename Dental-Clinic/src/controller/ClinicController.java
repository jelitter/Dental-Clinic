package controller;

import model.Clinic;
import model.Invoice;
import model.Patient;
import model.Payment;
import model.Procedure;
import model.ProcedureType;
import view.MainScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import controller.dao.ClinicFileController;
import controller.dao.AbstractClinicStorageController;
import controller.dao.ClinicDBController;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClinicController {

	/*
	 * -------------------------------- PROPERTIES -------------------------------
	 */

	public final int SERIAL = 0;
	public final int DATABASE = 1;

	private static int datasource = -1;

	private AbstractClinicStorageController fc;
	private Clinic clinic;
	private Boolean isSaved;
	public ObservableList<ProcedureType> procedureTypes;
	public ObservableList<Patient> patients;

	/*
	 * -------------------------------- CONSTRUCTOR -------------------------------
	 */

	public ClinicController() {

		// 0: Serial File, 1: Database
		fc = (getDataSource() == 0) ? new ClinicFileController() : new ClinicDBController();

		clinic = fc.getClinicFromStorage();

		if (clinic == null) {
			clinic = Clinic.getInstance();
			clinic.setPatientList(fc.getPatientListFromCSV());
			clinic.setProcedureTypesList(fc.getProcedureListFromCSV());
			patients = getObservableList(clinic.getPatients());
			procedureTypes = getObservableList(clinic.getProcedureTypes());
			// System.out.println(" New database created from CSV with sample patients: " +
			// patients.size());
			// System.out.println(" and sample procedures: " + procedureTypes.size());
			fc.saveClinicToStorage(this);
		} else {
			patients = getObservableList(clinic.getPatients());
			procedureTypes = getObservableList(clinic.getProcedureTypes());
			// System.out.println("-> Database loaded from serial file. Patients: " +
			// patients.size() + ", Procedures: " + procedureTypes.size());
		}

		// Getting max. Ids for patients, procedure types, procedures, invoices and
		// payments
		// so new ones don't get repeated Ids.
		Patient.setMaxId(getPatientMaxId());
		ProcedureType.setMaxId(getProcedureTypeMaxId());
		Invoice.setMaxId(getInvoiceMaxId());
		Procedure.setMaxId(getProcedureMaxId());
		Payment.setMaxId(getPaymentMaxId());
		setSaved(true);
	}

	/*
	 * -------------------------------- METHODS -------------------------------
	 */

	public ObservableBooleanValue getObservableSaved() {
		return new SimpleBooleanProperty(this.isSaved());
	}

	public Boolean isSaved() {
		return isSaved;
	}

	public void setSaved(Boolean b) {
		this.isSaved = b;
	}

	private int getPatientMaxId() {
		int id = 0;
		for (Patient p : patients) {
			if (p.getId() > id) {
				id = p.getId();
			}
		}
		return id;
	}

	private int getProcedureTypeMaxId() {
		int id = 0;
		for (ProcedureType p : procedureTypes) {
			if (p.getId() > id) {
				id = p.getId();
			}
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
		patients = getObservableList(clinic.getPatients());
		setSaved(false);
	}

	public void unsavedChanges() {
		if (ClinicController.getDataSource() == 0) {
			setSaved(false);
			MainScreen.getInstance().getStage().setTitle(MainScreen.APP_TITLE + " [Data source: " + ClinicController.getDataSourceString() + "] (Unsaved changes)");

			String statusText = MainScreen.getInstance().getStatusText();
			MainScreen.getInstance().setStatusText(statusText.trim() + " *");
			MainScreen.getInstance().showSaveButtons(!isSaved());
		}
	}

	public void savedChanges() {
		if (ClinicController.getDataSource() == 0) {
			setSaved(true);
			MainScreen.getInstance().getStage().setTitle(MainScreen.APP_TITLE + " [Data source: " + ClinicController.getDataSourceString() + "]");
			MainScreen.getInstance().setStatusText("All changes saved");
			MainScreen.getInstance().showSaveButtons(!isSaved());
		}
	}

	public void save() {
		fc.saveClinicToStorage(this);
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

	public <T> ObservableList<T> getObservableList(ArrayList<T> items) {
		return ArrayListToObservableList(items);
	}

	private <T> ObservableList<T> ArrayListToObservableList(ArrayList<T> alist) {
		return FXCollections.observableArrayList(alist);
	}

	private <T> ArrayList<T> ObservableListToArrayList(ObservableList<T> olist) {
		ArrayList<T> alist = (ArrayList<T>) olist.stream().collect(Collectors.toList());
		return alist;
	}

	public ArrayList<Patient> patientsAsList() {
		return ObservableListToArrayList(this.patients);
	}

	public ArrayList<ProcedureType> procedureTypesAsList() {
		return ObservableListToArrayList(this.procedureTypes);
	}

	public Clinic getClinic() {
		return this.clinic;
	}

	// "Bridge" to set data source from login screen
	public static void setDataSource(int source) {
		ClinicController.datasource = source;
		// 0 - Serial File
		// 1 - Database
	}

	public static int getDataSource() {
		return ClinicController.datasource;
	}
	public static String getDataSourceString() {
		return ClinicController.datasource == 0 ? "Serial File" : "Database";
	}

	public void addPatient(Patient newPatient) {
		patients.add(newPatient);
		fc.addPatient(newPatient);
	}
	
	public void removePatient(Patient patient) {
		patients.remove(patient);
		fc.removePatient(patient);
	}

	public void updatePatient(Patient patient) {
		fc.updatePatient(patient);
	}

	
	public void addInvoice(Patient patient) {
		patient.addInvoice(new Invoice(patient.getId()));
		fc.addInvoice(patient);
	}
	
	public void removeInvoice(Invoice inv) {
		fc.removeInvoice(inv);
	}
	
	public ProcedureType getProcTypeById(int id) {
		for (ProcedureType pt : this.procedureTypesAsList()) {
			if (pt.getId() == id) {
				return pt;
			}
		}
		return null;
	}

	public void addProcedureType(ProcedureType newProcedureType) {
		procedureTypes.add(newProcedureType);
	}

	
	public void addPayment(Payment paym) {
		fc.addPayment(paym);
	}
	public void removePayment(Payment paym) {
		fc.removePayment(paym);
	}

	
	public void addProcedure(Procedure proc) {
		fc.addProcedure(proc);
	}

	public void removeProcedure(Procedure proc) {
		fc.removeProcedure(proc);
	}

	

	
}





