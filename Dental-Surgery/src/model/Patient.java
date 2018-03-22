package model;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Patient extends Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static IntegerProperty _id = new SimpleIntegerProperty(0);
	private IntegerProperty id;
	private ArrayList<Invoice> invoices;

	public Patient(String firstName, String lastName, String address, String email, String phoneNumber) {
		super(firstName, lastName, address, email, phoneNumber);
		_id.add(1);
		this.setId(_id);
		this.setInvoices(new ArrayList<Invoice> ());
	}
	
	public Patient(String firstName, String lastName) {
		super(firstName, lastName);
		this.setId(_id);
		this.setInvoices(new ArrayList<Invoice> ());
	}
	
	public IntegerProperty getId() { return id; }
	public void setId(IntegerProperty _id2) { this.id = _id2; }
	
	public ArrayList<Invoice> getInvoices() { return invoices; }
	public void setInvoices(ArrayList<Invoice> invoices) { this.invoices = invoices; 	}
	public void addInvoice(Invoice invoice) { this.invoices.add(invoice); }
	
	public String toString() {
		return String.format("Name: %s %s\nAddress: %s\nPhone: %s\n", 
				this.getFirstName(), this.getLastName(), this.getAddress(), this.getPhoneNumber()); 
	}
	
	public void print() {
		System.out.println(this);
	}

}
