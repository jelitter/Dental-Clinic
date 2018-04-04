package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Invoice implements Serializable  {

	private static final long serialVersionUID = 1L;
	private static int _id = 0;
	private IntegerProperty id;
	private double amount;
	private Date date; 
	private boolean isPaid;
	private ArrayList<Procedure> procedures;
	private ArrayList<Payment> payments;
	
	// Constructors
	
	public Invoice() {
		this.setId(++_id);
		this.setAmount(0.0);
		this.setDate(Calendar.getInstance().getTime());;
		this.setPaid(false);
		this.setProcedures(new ArrayList<Procedure>());
		this.setPayments(new ArrayList<Payment>());
	}
	
	public Invoice(ArrayList<Procedure> procedures, ArrayList<Payment> payments) {
		this.setId(++_id);
		this.setAmount(this.getTotalAmount());
		this.setDate(Calendar.getInstance().getTime());;
		this.setPaid(false);
		this.setProcedures(procedures);
		this.setPayments(payments);
	}
	
	// Add-Remove from Procedures and Payments lists
	
	public void addProcedure(Procedure procedure) {
		this.getProcedures().add(procedure);
	}
	public void removeProcedure(Procedure procedure) {
		this.getProcedures().remove(procedure);
	}

	public void addPayment(Payment payment) {
		this.getPayments().add(payment);
	}
	public void removePayment(Payment payment) {
		this.getPayments().remove(payment);
	}

	// Getters, Setters, Observers

	public IntegerProperty IdProperty() { return id; }
	public int getId() { return id.get(); }
	public void setId(int id) { this.id.setValue(id); } 
	public static void setMaxId(int i) { _id = i; }
	
	public DoubleProperty AmountProperty() { return new SimpleDoubleProperty(amount); }
	public double getAmount() { return amount; }
	public void setAmount(double amount) { this.amount = amount;	}

	public StringProperty DateProperty() { return new SimpleStringProperty(date.toString()); }
	public Date getDate() { return date;	}
	public void setDate(Date date) { this.date = date; }

	public BooleanProperty PaidProperty() { return new SimpleBooleanProperty(isPaid()); }
	public boolean isPaid() { return isPaid;	}
	public void setPaid(boolean isPaid) { this.isPaid = isPaid; }
	
	public IntegerProperty ProcedureNumberProperty() { return new SimpleIntegerProperty(procedures.size()); }
	public ObservableList<Procedure> ProceduresProperty() { return FXCollections.observableArrayList(procedures); }
	public ArrayList<Procedure> getProcedures() { return procedures; 	}
	public void setProcedures(ArrayList<Procedure> procedures) { this.procedures = procedures; }

	public IntegerProperty PaymentNumberProperty() { return new SimpleIntegerProperty(payments.size()); }
	public ObservableList<Payment> PaymentsProperty() { return FXCollections.observableArrayList(payments); }
	public ArrayList<Payment> getPayments() { return payments; }
	public void setPayments(ArrayList<Payment> payments) { this.payments = payments; 	}
	
	// Methods
	
	private double getTotalAmount() {
		double total = 0.;
		for (Procedure p: this.getProcedures()) {
			total += p.getPrice();
		}
		return total;
	}
}
