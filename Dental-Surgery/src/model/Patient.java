package model;

import java.util.ArrayList;

public class Patient {
	
	private static int _id;
	
	private int id;
	private String name, address, phoneNumber;
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

	public String getName() { return name; }
	public void setName(String name) { this.name = name;	}

	public String getAddress() { return address;	}
	public void setAddress(String address) { this.address = address;	}

	public String getPhoneNumber() { return phoneNumber;	}
	public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
	
	
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
