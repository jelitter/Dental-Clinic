package model;

import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = 1L;
	private String firstName, lastName, email, address, phoneNumber;

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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String toString() {
		return String.format("Name: %s %s\nEmail: %s\nAddress: %s\nPhone: %s\n", this.getFirstName(),
				this.getLastName(), this.getEmail(), this.getAddress(), this.getPhoneNumber());
	}

	public void print() {
		System.out.println(this);
	}
}
