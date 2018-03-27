package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Patient;

public class FileStorage {

	public static void storeObject(Object o, String filename) {
		try {
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(o);
			out.close();
			fileOut.close();
			System.out.println("  Data saved to: " + filename);
		} catch (IOException i) {
			storeObject(i, "IOException.ser");
		}
	}

	public static Object readObject(String filename) {
		Object obj = null;
		try {

			FileInputStream fileIn = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			obj = in.readObject();
			in.close();
			fileIn.close();
		} catch (FileNotFoundException a) {
			System.out.println(a.getMessage());
			// storeObject(a,"FileNotFound.ser");
		} catch (IOException i) {
			System.out.println(i.getMessage());
			// storeObject(i,"IOException.ser");
		} catch (ClassNotFoundException c) {
			System.out.println(c.getMessage());
			// storeObject(c,"ClassNotFoundException.ser");
		}
		return obj;
	}

	public static void storeObservableObject(ObservableList<Patient> o, String filename) {
		try {
			ArrayList<Patient> list = new ArrayList<Patient>(o);
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(list);
			out.close();
			fileOut.close();
			System.out.println("  Data saved to: " + filename);
		} catch (IOException i) {
			//			storeObject(i, "IOException.ser");
			System.out.println("IO Exception when storing ObservableObject: " + i.getMessage());
		}
	}
	
	public static Object readObservableObject(String filename) throws FileNotFoundException {
		List<Patient> list = null;
		ObservableList<Patient> observableList = null;
		try {

			FileInputStream fileIn = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			list = (List<Patient>) in.readObject();
			observableList = FXCollections.observableArrayList(list);
			in.close();
			fileIn.close();
			
		} catch (FileNotFoundException a) {
			System.out.println(a.getMessage());
			throw a;
			// storeObject(a,"FileNotFound.ser");
		} catch (IOException i) {
			System.out.println(i.getMessage());
			// storeObject(i,"IOException.ser");
		} catch (ClassNotFoundException c) {
			System.out.println(c.getMessage());
			// storeObject(c,"ClassNotFoundException.ser");
		}
		return observableList;
	}
}
