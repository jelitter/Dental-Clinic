package model;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	private StringProperty firstName, lastName, address, email, phoneNumber;
	
	public Person(String firstName, String lastName, String address, String email, String phoneNumber) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setAddress(address);
		this.setEmail(email);
		this.setPhoneNumber(phoneNumber);
	}
	
	public Person(String firstName, String lastName) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setAddress("");
		this.setEmail("");
		this.setPhoneNumber("");
	}

	public StringProperty getFirstName() { return firstName; }
	public void setFirstName(String name) { this.firstName = new SimpleStringProperty(name);	}
	
	public StringProperty getLastName() { return lastName; }
	public void setLastName(String name) { this.lastName = new SimpleStringProperty(name);	}

	public StringProperty getAddress() { return address;	}
	public void setAddress(String address) { this.address = new SimpleStringProperty(address);	}

	public StringProperty getEmail() { return email;	}
	public void setEmail(String email) { this.email = new SimpleStringProperty(email);	}
	
	public StringProperty getPhoneNumber() { return phoneNumber;	}
	public void setPhoneNumber(String phoneNumber) { this.phoneNumber = new SimpleStringProperty(phoneNumber); 	}
}
