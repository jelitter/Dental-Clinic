package model;

import java.util.ArrayList;

public class Patient extends Person {
	
	private static int _id = 0;
	private int id;
	private ArrayList<Invoice> invoices;

	//  Constructor  ------------------------------------------------------
	
	public Patient(String name, String address, String phoneNumber) {
		this.setId(++_id);
		this.setName(name);
		this.setAddress(address);
		this.setPhoneNumber(phoneNumber);
		this.setInvoices(new ArrayList<Invoice> ());
	}
	//  -------------------------------------------------------------------
	
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	
	//  Invoices  ---------------------------------------------------------
	public ArrayList<Invoice> getInvoices() {
		return invoices;
	}
	public void setInvoices(ArrayList<Invoice> invoices) {
		this.invoices = invoices;
	}
	public void addInvoice(Invoice invoice) {
		this.invoices.add(invoice);
	}
	
	//  -------------------------------------------------------------------

}
