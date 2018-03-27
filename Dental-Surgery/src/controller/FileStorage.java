package controller;

import java.io.File;
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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Patient;
import view.MainScreen;

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

	public static void storeObservableObject(ArrayList<Patient> o, String filename) {
		// try {
		// FileOutputStream fileOut = new FileOutputStream(filename);
		// ObjectOutputStream out = new ObjectOutputStream(fileOut);
		// out.writeObject(new ArrayList<Patient>(o));
		// out.close();
		// fileOut.close();
		// System.out.println(" Data saved to: " + filename);
		// } catch (IOException i) {
		// // storeObject(i, "IOException.ser");
		// System.out.println("IO Exception when storing ObservableObject: " +
		// i.getMessage());
		// }

		
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
			out.writeObject(o);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		
//		FileChooser chooser = new FileChooser();
//		chooser.getExtensionFilters().add(new ExtensionFilter("Patients files", "*.ser"));
//
//		File file = chooser.showSaveDialog(MainScreen.getInstance().getStage());
//		if (file != null) {
//			try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
//				out.writeObject(new ArrayList<Patient>(o));
//			} catch (Exception exc) {
//				exc.printStackTrace();
//			}
//		}
	}
	
	public static Object readObservableObject(String filename) throws FileNotFoundException {
//		List<Patient> list;
		ObservableList<Patient> obList = FXCollections.observableArrayList() ;

		try {

			FileInputStream fileIn = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(fileIn);
//			list = (List<Patient>) in.readObject();
			ArrayList<Patient> loadedPatients = (ArrayList<Patient>) in.readObject() ;
			obList.setAll(loadedPatients);
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
		return obList;
	}
}
