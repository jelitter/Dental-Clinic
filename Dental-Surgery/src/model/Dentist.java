package model;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Dentist extends Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static int _id = 0;
	private int id;
	private String username, password;

	public Dentist(String firstName, String lastName, String address, String email, String phone, String username, String password) {
		super(firstName, lastName, address, email, phone);
		this.setId(++_id);
		this.setUsername(username);
		this.setPassword(password);
	}
	
	public IntegerProperty IdProperty() { return new SimpleIntegerProperty(id); }
	public int getId() { return id; }
	public void setId(int id) { this.id = id; } 
	public static void setMaxId(int i) { _id = i; }
	
	public StringProperty UsernameProperty() { return new SimpleStringProperty(username); }
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username;	}

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password;	}
}
