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
	private IntegerProperty id;
	private Date date;
	private DoubleProperty amount;

	public Payment(double amount) {
		this.id = new SimpleIntegerProperty();
		this.setId(++_id);
		this.amount = new SimpleDoubleProperty();
		this.setAmount(amount);
		this.setDate(Calendar.getInstance().getTime());
	}
	
	public IntegerProperty IdProperty() { return id; }
	public int getId() { return id.get(); }
	public void setId(int id) { this.id.setValue(id); } 
	public static void setMaxId(int i) { _id = i; }

	public StringProperty DateProperty() { return new SimpleStringProperty(date.toString()); }
	public Date getDate() { return date;	}
	public void setDate(Date date) { this.date = date; }

	public DoubleProperty AmountProperty() { return amount; }
	public double getAmount() { return amount.get(); }
	public void setAmount(double amount) { this.amount.set(amount);	}
}
