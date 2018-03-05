package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class Invoice implements Serializable  {

	private static final long serialVersionUID = 1L;
	private static int _id = 0;
	
	private int id;
	private double amount;
	private Calendar date; 
	private boolean isPaid;
	private ArrayList<Procedure> procedures;
	private ArrayList<Payment> payments;
	
	public Invoice(ArrayList<Procedure> procedures, ArrayList<Payment> payments) {
		this.setId(++_id);
		this.setAmount(this.getTotalAmount());
		this.setDate(Calendar.getInstance());;
		this.setPaid(false);
		this.setProcedures(procedures);
		this.setPayments(payments);
	}

	private double getTotalAmount() {
		double total = 0.;
		for (Procedure p: this.getProcedures()) {
			total += p.getPrice();
		}
		return total;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
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
