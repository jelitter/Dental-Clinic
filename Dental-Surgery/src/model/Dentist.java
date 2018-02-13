package model;

public class Dentist extends Person {
	
	private String username, password;

	public Dentist(String name, String address, String phoneNumber, String username, String password) {
		super(name, address, phoneNumber);
		this.setUsername(username);
		this.setPassword(password);
	}
	
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username;	}

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password;	}
}
