package model;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	private StringProperty firstName, lastName, email, address, phoneNumber;
	
	public Person(String firstName, String lastName, String email, String address, String phoneNumber) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmail(email);
		this.setAddress(address);
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
	public void setFirstName(String name) { 
		if (this.firstName  == null)
			this.firstName = new SimpleStringProperty(name);
		this.firstName.set(name);	
	}
	
	public StringProperty getLastName() { return lastName; }
	public void setLastName(String name) {
		if (this.lastName  == null)
			this.lastName = new SimpleStringProperty(name);
		this.lastName.set(name);
	}
	
	public StringProperty getEmail() { return email;	}

	public void setEmail(String email) {
		if (this.email == null)
			this.email = new SimpleStringProperty(email);
		this.email.set(email);
	}

	public StringProperty getAddress() { return address;	}
	public void setAddress(String address) { 
		if (this.address == null)
			this.address = new SimpleStringProperty(address);
		this.address.set(address);
	}
	
	public StringProperty getPhoneNumber() { return phoneNumber;	}
	public void setPhoneNumber(String phoneNumber) { 
		if (this.phoneNumber == null)
			this.phoneNumber = new SimpleStringProperty(phoneNumber);
		this.phoneNumber.set(phoneNumber);
	}
	
	public String toString() {
		return String.format("Name: %s %s\nEmail: %s\nAddress: %s\nPhone: %s\n", 
//				this.getFirstName().toString(), this.getLastName().toString(), this.getEmail().toString(), this.getAddress().toString(), this.getPhoneNumber().toString()); 
		this.getFirstName().get(), this.getLastName().get(), this.getEmail().get(), this.getAddress().get(), this.getPhoneNumber().get()); 
	}
	
	public void print() {
		System.out.println(this);
	}
}
