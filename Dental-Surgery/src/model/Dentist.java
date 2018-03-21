package model;

import java.io.Serializable;

public class Dentist extends Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String username, password;

	public Dentist(String name, String address, String email, String phoneNumber, String username, String password) {
		super(name, address, email, phoneNumber);
		this.setUsername(username);
		this.setPassword(password);
	}
	
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username;	}

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password;	}
}
