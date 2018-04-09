package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
	private int id;
	private double amount, amountPaid;
	private Date date; 
	private boolean isPaid;
	private ArrayList<Procedure> procedures;
	private ArrayList<Payment> payments;
	
	// Constructors
	
	public Invoice() {
		this.setId(++_id);
		this.setAmount(0.0);
		this.setAmountPaid(0.0);
		this.setDate(Calendar.getInstance().getTime());;
		this.setProcedures(new ArrayList<Procedure>());
		this.setPayments(new ArrayList<Payment>());
	}
	
	public Invoice(ArrayList<Procedure> procedures, ArrayList<Payment> payments) {
		this.setId(++_id);
		this.setAmount(this.getTotalAmount());
		this.setDate(Calendar.getInstance().getTime());;
		this.setProcedures(procedures);
		this.setPayments(payments);
		this.setAmountPaid(0.0);
	}
	
	// Add-Remove from Procedures and Payments lists
	
	public void addProcedure(Procedure procedure) {
		this.getProcedures().add(procedure);
	}
	public void removeProcedure(Procedure procedure) {
		this.getProcedures().remove(procedure);
	}
	
	public void addPayment(double amount) {
		this.getPayments().add(new Payment(amount));
	}

	public void addPayment(Payment payment) {
		this.getPayments().add(payment);
	}
	public void removePayment(Payment payment) {
		this.getPayments().remove(payment);
	}

	// Getters, Setters, Observers

	public IntegerProperty IdProperty() { return new SimpleIntegerProperty(id); }
	public int getId() { return id; }
	public void setId(int id) { this.id = id; } 
	public static void setMaxId(int i) { _id = i; }
	
	public DoubleProperty AmountProperty() { return new SimpleDoubleProperty(amount); }
	public double getAmount() { return amount; }
	public void setAmount(double amount) { this.amount = amount;	}
	
	public DoubleProperty AmountPaidProperty() { return new SimpleDoubleProperty(amountPaid); }
	public double getAmountPaid() { return amountPaid; }
	public void setAmountPaid(double amountPaid) { this.amountPaid = amountPaid; 	}

	public DoubleProperty AmountPendingProperty() { return new SimpleDoubleProperty(amount - amountPaid); }
	public double getAmountPending() { return amount - amountPaid; }
//	public void setAmountPending(double amountPaid) { this.amountPaid = amountPaid; 	}
	
	public StringProperty DateProperty() {
		return new SimpleStringProperty(new SimpleDateFormat("d-MMM-yy").format(date));
	}
	public Date getDate() { return date;	}
	public void setDate(Date date) { this.date = date; }

	public BooleanProperty IsPaidProperty() { return new SimpleBooleanProperty(TotalAmountProperty().get() <= TotalAmountPaidProperty().get()); }
	public boolean isPaid() { return (AmountProperty().get() <= AmountPaidProperty().get());	}
	public void setPaid() { this.isPaid = (AmountProperty().get() <= AmountPaidProperty().get()); }
	
	public ObservableList<Procedure> ProceduresProperty() { return FXCollections.observableArrayList(procedures); }
	public IntegerProperty ProcedureNumberProperty() { return new SimpleIntegerProperty(procedures.size()); }
	public ArrayList<Procedure> getProcedures() { return procedures; 	}
	public void setProcedures(ArrayList<Procedure> procedures) { this.procedures = procedures; }

	public ObservableList<Payment> PaymentsProperty() { return FXCollections.observableArrayList(payments); }
	public IntegerProperty PaymentsNumberProperty() { return new SimpleIntegerProperty(payments.size()); }
	public ArrayList<Payment> getPayments() { return payments; }
	public void setPayments(ArrayList<Payment> payments) { this.payments = payments; 	}
	
	// Methods
	
	public DoubleProperty TotalAmountProperty() { 
		return new SimpleDoubleProperty(getTotalAmount());
	}
	private double getTotalAmount() {
		double total = 0.;
		for (Procedure p: this.getProcedures()) {
			total += p.getPrice();
		}
		return total;
	}
	public StringProperty TotalAmountStringProperty() {
		return new SimpleStringProperty(String.format("%.2f", getTotalAmount()));
	}
	
	public DoubleProperty TotalAmountPaidProperty() { 
		return new SimpleDoubleProperty(getTotalPayments()); 
	}
	private double getTotalPayments() {
		double total = 0.;
		for (Payment p: this.getPayments()) {
			total += p.getAmount();
		}
		return total;
	}
	public StringProperty TotalAmountPaidStringProperty() {
		return new SimpleStringProperty(String.format("%.2f", getTotalPayments()));
	}
	
	public DoubleProperty TotalAmountPendingProperty() {
		return new SimpleDoubleProperty( TotalAmountProperty().get() - TotalAmountPaidProperty().get() ); 
	}
	public StringProperty TotalAmountPendingStringProperty() {
		return new SimpleStringProperty(String.format("%.2f", TotalAmountPendingProperty().get()));
	}
	
	
	
	public String toString() {
		return "Invoice " + getId() 
		+ " -> Total: " + TotalAmountProperty().get() 
		+ " EUR, Pending: " + TotalAmountPendingProperty().get()
		+ " EUR, Paid: " + TotalAmountPaidProperty().get() 
		+ " EUR. - getAmount: "+ getAmount() +"\n";
	}
}
