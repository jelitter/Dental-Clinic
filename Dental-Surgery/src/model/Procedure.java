package model;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Procedure implements Serializable {

	private static final long serialVersionUID = 1L;
	private static int _id = 0;
	private int id;
	private String name, description;
	private double price;
	
	public Procedure(String name, String description, double price) {
		this.setName(name);
		this.setDescription(description);
		this.setPrice(price);
	}
	
	public int getId() { return id; }
	public StringProperty getIdProperty() { return new SimpleStringProperty(Integer.toString(id)); }
	public void setId(int _id2) { this.id = _id2; } 
	public static void setMaxId(int i) { _id = i; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name;	}

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description;	}

	public double getPrice() { return price;	}
	public void setPrice(double price) { this.price = price;	};	
}
