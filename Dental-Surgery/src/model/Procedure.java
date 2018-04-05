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
	private IntegerProperty id;
	private StringProperty name, description;
	private DoubleProperty price;

	public Procedure(String name, String description, double price) {
		this.id = new SimpleIntegerProperty();
		this.name = new SimpleStringProperty(name);
		this.description = new SimpleStringProperty(description);
		this.price= new SimpleDoubleProperty(price);
		this.setId(++_id);
	}


	public IntegerProperty IdProperty() { return id; }
	public int getId() { return id.get(); }
	public void setId(int id) { this.id.set(id); }
	public static void setMaxId(int i) {	_id = i; }

	public StringProperty NameProperty() { return name; }
	public String getName() {	return name.get(); }
	public void setName(String name) { this.name.setValue(name);	}

	public StringProperty DescriptionProperty() { return description; 	}
	public String getDescription() { 	return description.get(); }
	public void setDescription(String description) { this.description.setValue(description);	}

//	public StringProperty getPriceProperty() {
//		return new SimpleStringProperty(String.format("%.2f", price));
//	}

	public DoubleProperty PriceProperty() { return price; }
	public double getPrice() { return price.get(); }
	public void setPrice(double price) { this.price.setValue(price); }


	public String toString() {
		return String.format("Procedure: \t%s \nDescription: \t%s \nPrice: \t\t%s \n", this.getName(), this.getDescription(),
				String.format("%.2f", getPrice()));
	}
}
