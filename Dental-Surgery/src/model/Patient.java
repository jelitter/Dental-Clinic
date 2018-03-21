package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Patient extends Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static int _id = 0;
	private int id;
	private ArrayList<Invoice> invoices;

	public Patient(String firstName, String lastName, String address, String email, String phoneNumber) {
		super(firstName, lastName, address, email, phoneNumber);
		this.setId(++_id);
		this.setInvoices(new ArrayList<Invoice> ());
	}
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
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
