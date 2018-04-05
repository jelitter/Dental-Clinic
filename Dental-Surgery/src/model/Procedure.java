package model;

import java.io.Serializable;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Procedure implements Serializable {

	private static final long serialVersionUID = 1L;
	private static int _id = 0;
	private int id;
	private String name, description;
	private Double price;

	public Procedure(String name, String description, double price) {
//		this.id = new SimpleIntegerProperty();
//		this.name = new SimpleStringProperty(name);
//		this.description = new SimpleStringProperty(description);
//		this.price= new SimpleDoubleProperty(price);
		
		this.setName(name);
		this.setDescription(description);
		this.setPrice(price);
		this.setId(++_id);
	}


	public IntegerProperty IdProperty() { return new SimpleIntegerProperty(id); }
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public static void setMaxId(int i) {	_id = i; }

	public StringProperty NameProperty() { return new SimpleStringProperty(name); }
	public String getName() {	return name; }
	public void setName(String name) { this.name = name;	}

	public StringProperty DescriptionProperty() { return new SimpleStringProperty(description); }
	public String getDescription() { 	return description; }
	public void setDescription(String description) { this.description = description; }

	public DoubleProperty PriceProperty() { return new SimpleDoubleProperty(price); }
	public double getPrice() { return price; }
	public void setPrice(double price) { this.price = price; }


	public String toString() {
		return String.format("Procedure: \t%s \nDescription: \t%s \nPrice: \t\t%s \n", this.getName(), this.getDescription(),
				String.format("%.2f", getPrice()));
	}
}
