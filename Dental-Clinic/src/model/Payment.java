package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Payment implements Serializable  {

	private static final long serialVersionUID = 1L;
	private static int _id = 0;
	private int id;
	private int invoiceId;
	private Date date;
	private Double amount;

	public Payment(int invoiceId, double amount) {
		// this.setId(++_id);
		// this.setAmount(amount);
		Date date = Calendar.getInstance().getTime();
//		this.setDate(Calendar.getInstance().getTime());
		new Payment(invoiceId, amount, date);
	}
	
	public Payment(int invoiceId, double amount, Date date) {
		this.setId(++_id);
		this.setInvoiceId(invoiceId);
		this.setAmount(amount);
		this.setDate(date);
	}
	
	public Payment(int paymentId, int invoiceId, double amount, Date date) {
		this.setId(paymentId);
		this.setInvoiceId(invoiceId);
		this.setAmount(amount);
		this.setDate(date);
	}
	
	private void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public IntegerProperty IdProperty() { return new SimpleIntegerProperty(id); }
	public int getId() { return id; }
	public void setId(int id) { this.id = id; } 
	public static void setMaxId(int i) { _id = i; }

	public StringProperty DateProperty() { 
		return new SimpleStringProperty(new SimpleDateFormat("d-MMM-yy").format(date));	
	}
	public Date getDate() { return date;	}
	public void setDate(Date date) { this.date = date; }

	public DoubleProperty AmountProperty() { return new SimpleDoubleProperty(amount); }
	public double getAmount() { return amount; }
	public void setAmount(double amount) { this.amount = amount;	}
	public StringProperty AmountStringProperty() { 
		return new SimpleStringProperty(String.format("%.2f", getAmount()));
	}

	public void print() {
		System.out.println(this.toString());
	}

	public int getInvoiceId() { return this.invoiceId; }
}
