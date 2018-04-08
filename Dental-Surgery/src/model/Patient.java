package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.WritableDoubleValue;
import javafx.beans.value.WritableIntegerValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class Patient extends Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static int _id = 0;
	private int id;
	private ArrayList<Invoice> invoices;

	public Patient(String firstName, String lastName, String email, String address, String phone) {
		super(firstName, lastName, email, address, phone);
		this.setId(++_id);
		this.setInvoices(new ArrayList<Invoice> ());
	}
	
	public Patient(String firstName, String lastName) {
		super(firstName, lastName);
		this.setId(++_id);
		this.setInvoices(new ArrayList<Invoice> ());
	}
	
	public IntegerProperty IdProperty() { return new SimpleIntegerProperty(id); }
	public int getId() { return id; }
	public void setId(int id) { this.id = id; } 
	public static void setMaxId(int i) { _id = i; }
	
	public ObservableList<Invoice> InvoicesProperty() { return FXCollections.observableArrayList(invoices); }
	public ArrayList<Invoice> getInvoices() { return invoices; }
	public void setInvoices(ArrayList<Invoice> invoices) { this.invoices = invoices; 	}
	public void addInvoice(Invoice invoice) { this.invoices.add(invoice); }
	public void removeInvoice(Invoice invoice) { this.invoices.remove(invoice); }

	
	public DoubleProperty TotalAmountProperty() {
		return new SimpleDoubleProperty(getTotalAmount());
	}
	private Double getTotalAmount() {
		Double total = 0.;
		for (Invoice inv : getInvoices()) {
			total += inv.TotalAmountProperty().get();
		}
		return total;
	}
	
	public DoubleProperty TotalPaidProperty() {
		return new SimpleDoubleProperty(getTotalPaid());
	}
	private Double getTotalPaid() {
		Double total = 0.;
		for (Invoice inv : getInvoices()) {
			total += inv.TotalAmountPaidProperty().get();
		}
		return total;
	}
	
	public DoubleProperty TotalPaidPropertyLastMonths(int months) {

		Calendar today = new GregorianCalendar();
		today.setTime(new Date());
		Calendar payDay = new GregorianCalendar();
		
		Double total = 0.;
		for (Invoice inv : getInvoices()) {
			for (Payment pay : inv.getPayments()) {
				payDay.setTime(pay.getDate());
				int diffYear = today.get(Calendar.YEAR) - payDay.get(Calendar.YEAR);
				int diffMonth = diffYear * 12 + today.get(Calendar.MONTH) - payDay.get(Calendar.MONTH);
				if (diffMonth <= months) {
					total += pay.getAmount();
				}
			}
		}
		return new SimpleDoubleProperty(total);
	}
	
	public DoubleProperty TotalPendingProperty() {
		return new SimpleDoubleProperty(getPendingPayments());
	}
	private Double getPendingPayments() {
		Double total = 0.;
		for (Invoice inv : getInvoices()) {
			total += inv.TotalAmountPendingProperty().get();
		}
		return total;
	}

	public StringProperty LastPaymentProperty() {
		return new SimpleStringProperty(new SimpleDateFormat("d-MMM-yy").format(getLastPaymentDate()));
	}
	private Date getLastPaymentDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(1970, Calendar.JANUARY, 1, 0, 0, 0); // Jan 1st 1970 @00:00:00
		Date date = cal.getTime();
		
		for (Invoice inv: getInvoices()) {
			for (Payment p : inv.getPayments()) {
				if (date.before(p.getDate())) {
					date = p.getDate();
				}
			}
		}
		return date;
	}

	public IntegerProperty NumberOfProceduresProperty() {
		int procs = 0;
		for (Invoice inv : getInvoices()) {
			procs += inv.getProcedures().size();
		}
		return new SimpleIntegerProperty(procs);
	}
	public IntegerProperty NumberOfPaymentsProperty() {
		int procs = 0;
		for (Invoice inv : getInvoices()) {
			procs += inv.getPayments().size();
		}
		return new SimpleIntegerProperty(procs);
	}

	

}
