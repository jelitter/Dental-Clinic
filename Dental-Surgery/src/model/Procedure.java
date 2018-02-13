package model;

public class Procedure {

	private String name, description;
	private double price;
	
	public Procedure(String name, String description, double price) {
		
	}

	public String getName() { return name; }
	public void setName(String name) { this.name = name;	}

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description;	}

	public double getPrice() { return price;	}
	public void setPrice(double price) { this.price = price;	};	
}
