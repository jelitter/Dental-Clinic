package model;

import java.io.Serializable;
import java.util.Calendar;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Payment implements Serializable  {

	private static final long serialVersionUID = 1L;
	private Calendar date;
	private DoubleProperty amount;

	public Payment(double amount) {
		this.setAmount(amount);
		this.setDate(Calendar.getInstance());
	}

	public StringProperty DateProperty() { return new SimpleStringProperty(date.toString()); }
	public Calendar getDate() { return date;	}
	public void setDate(Calendar date) { this.date = date; }

	public DoubleProperty AmountProperty() { return amount; }
	public double getAmount() { return amount.get(); }
	public void setAmount(double amount) { this.amount.set(amount);	}
}
