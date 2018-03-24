package model;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Patient extends Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static int _id = 0;
	private int id;
	private ArrayList<Invoice> invoices;

	public Patient(String firstName, String lastName, String email, String address, String phoneNumber) {
		super(firstName, lastName, email, address, phoneNumber);
		this.setId(++_id);
		this.setInvoices(new ArrayList<Invoice> ());
	}
	
	public Patient(String firstName, String lastName) {
		super(firstName, lastName);
		this.setId(++_id);
		this.setInvoices(new ArrayList<Invoice> ());
	}
	
	public StringProperty getId() { return new SimpleStringProperty(Integer.toString(id)); }
	public void setId(int _id2) { this.id = _id2; } 
	
	public ArrayList<Invoice> getInvoices() { return invoices; }
	public void setInvoices(ArrayList<Invoice> invoices) { this.invoices = invoices; 	}
	public void addInvoice(Invoice invoice) { this.invoices.add(invoice); }
	
	

}
