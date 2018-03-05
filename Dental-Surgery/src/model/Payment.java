package model;

import java.io.Serializable;
import java.util.Calendar;

public class Payment implements Serializable  {

	private static final long serialVersionUID = 1L;
	private Calendar date;
	private double amount;

	public Payment(double amount) {
		this.setAmount(amount);
		this.setDate(Calendar.getInstance());
	}

	public Calendar getDate() { return date;	}
	public void setDate(Calendar date) { this.date = date; }

	public double getAmount() { return amount; }
	public void setAmount(double amount) { this.amount = amount;	}
}
