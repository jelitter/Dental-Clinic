package model;

import java.util.ArrayList;

public class Invoice {

	
	private ArrayList<Procedure> procedures;
	private ArrayList<Payment> payments;
	
	public Invoice() {
		
	}

	public ArrayList<Procedure> getProcedures() {
		return procedures;
	}

	public void setProcedures(ArrayList<Procedure> procedures) {
		this.procedures = procedures;
	}

	public ArrayList<Payment> getPayments() {
		return payments;
	}

	public void setPayments(ArrayList<Payment> payments) {
		this.payments = payments;
	}
	
	
}
