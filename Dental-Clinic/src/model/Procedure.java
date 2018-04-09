package model;

import java.io.Serializable;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Procedure implements Serializable {

	private static final long serialVersionUID = 1L;
	private static int _id = 0;
	private int id;
	private ProcedureType proc;

	
	public Procedure(ProcedureType proc) {
		setType(proc);
		setId(++_id);
	}
	
	public IntegerProperty IdProperty() { return new SimpleIntegerProperty(id); }
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public static void setMaxId(int i) {	_id  = i; }

	public ProcedureType getType() { return proc; }
	public void setType(ProcedureType type) { this.proc = type; }
	
	
	public StringProperty NameProperty() { return getType().NameProperty(); }
	public String getName() {	return getType().getName(); }
	public void setName(String name) { this.getType().setName(name);	}

	public StringProperty DescriptionProperty() { return getType().DescriptionProperty(); }
	public String getDescription() { 	return getType().getDescription(); }
	public void setDescription(String description) { getType().setDescription(description); }

	public DoubleProperty PriceProperty() { return getType().PriceProperty(); }
	public double getPrice() { return getType().getPrice(); }
	public void setPrice(double price) { getType().setPrice(price); }
	public StringProperty PriceStringProperty() {
		return new SimpleStringProperty(String.format("%.2f", getPrice()));
	}
}
