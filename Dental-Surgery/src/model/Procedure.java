package model;

import java.io.Serializable;

public class Procedure implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name, description;
	private double price;
	
	public Procedure(String name, String description, double price) {
		this.setName(name);
		this.setDescription(description);
		this.setPrice(price);
	}

	public String getName() { return name; }
	public void setName(String name) { this.name = name;	}

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description;	}

	public double getPrice() { return price;	}
	public void setPrice(double price) { this.price = price;	};	
}
