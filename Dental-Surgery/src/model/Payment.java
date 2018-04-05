package model;

import java.io.Serializable;
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
	private Date date;
	private Double amount;

	public Payment(double amount) {
//		this.id = new SimpleIntegerProperty();
		this.setId(++_id);
//		this.amount = new SimpleDoubleProperty();
		this.setAmount(amount);
		this.setDate(Calendar.getInstance().getTime());
	}
	
	public IntegerProperty IdProperty() { return new SimpleIntegerProperty(id); }
	public int getId() { return id; }
	public void setId(int id) { this.id = id; } 
	public static void setMaxId(int i) { _id = i; }

	public StringProperty DateProperty() { return new SimpleStringProperty(date.toString()); }
	public Date getDate() { return date;	}
	public void setDate(Date date) { this.date = date; }

	public DoubleProperty AmountProperty() { return new SimpleDoubleProperty(amount); }
	public double getAmount() { return amount; }
	public void setAmount(double amount) { this.amount = amount;	}
}
