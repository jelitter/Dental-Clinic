package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Dentist;

public class UserFileController {

	private static final String USERSFILENAME = "src/data/users.ser";
	private static final String LOGINFILENAME = "src/data/login.ser";
	private ArrayList<Dentist> users;

	public UserFileController() {
		users = new ArrayList<Dentist>();
	}

	public ArrayList<Dentist> getUsersFromSerial() {
		users = (ArrayList<Dentist>) FileStorage.readObject(USERSFILENAME);
		return users;
	}

	public void saveUsersToSerial(ArrayList<Dentist> users) {
		try {
			FileStorage.storeObject(users, USERSFILENAME);
			System.out.println("<- User data saved to: " + USERSFILENAME);
		} catch (Exception ex) {
			// System.out.println("Error writting serial file - " + ex);
		}
	}

	protected ArrayList<Dentist> getUserListFromCSV() {
		ArrayList<Dentist> userList = new ArrayList<Dentist>();
		String csvFile = "src/data/users.csv";
		String fieldDelimiter = "\\|";
		BufferedReader br;

		try {
			br = new BufferedReader(new FileReader(csvFile));
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println("User line: " + line);
				String[] fields = line.split(fieldDelimiter, -1);
				Dentist record = new Dentist(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5],
						fields[6]);
				userList.add(record);
			}
		} catch (FileNotFoundException ex) {
			System.out.println("Error - Serial file not found - " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("Error - Serial file IO exception - " + ex.getMessage());
		}
		return userList;
	}
	
	
	
	// Helper

	public <T> ObservableList<T> getObservableList(ArrayList<T> items) {
		return FXCollections.observableArrayList(items);
	}

	private <T> ArrayList<T> getArrayList(ObservableList<T> olist) {
		ArrayList<T> alist = (ArrayList<T>) olist.stream().collect(Collectors.toList());
		return alist;
	}

	public void saveLogin(String user, String pass) {
		Dentist d = new Dentist(user, pass);
		FileStorage.storeObject(d, LOGINFILENAME);
	}
	public Dentist loadLogin() {
		Dentist d = (Dentist) FileStorage.readObject(LOGINFILENAME);
		return d;
	}

}
