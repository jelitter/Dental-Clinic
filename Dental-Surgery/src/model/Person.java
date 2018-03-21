package model;

import java.io.Serializable;

public class Person implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	private String firstName, lastName, address, email, phoneNumber;
	
	public Person(String firstName, String lastName, String address, String email, String phoneNumber) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setAddress(address);
		this.setEmail(email);
		this.setPhoneNumber(phoneNumber);
	}

	public String getFirstName() { return firstName; }
	public void setFirstName(String name) { this.firstName = name;	}
	
	public String getLastName() { return lastName; }
	public void setLastName(String name) { this.lastName = name;	}

	public String getAddress() { return address;	}
	public void setAddress(String address) { this.address = address;	}

	public String getEmail() { return email;	}
	public void setEmail(String email) { this.email = email;	}
	
	public String getPhoneNumber() { return phoneNumber;	}
	public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; 	}
}
