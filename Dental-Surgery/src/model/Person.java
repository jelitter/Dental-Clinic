package model;

import java.io.Serializable;

public class Person implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	private String name, address, phoneNumber;
	
	public Person(String name, String address, String phoneNumber) {
		this.setName(name);
		this.setAddress(address);
		this.setPhoneNumber(phoneNumber);
	}

	public String getName() { return name; }
	public void setName(String name) { this.name = name;	}

	public String getAddress() { return address;	}
	public void setAddress(String address) { this.address = address;	}

	public String getPhoneNumber() { return phoneNumber;	}
	public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; 	}
}
