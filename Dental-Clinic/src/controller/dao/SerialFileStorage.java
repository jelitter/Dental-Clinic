package controller.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerialFileStorage {

	public void storeObject(Object o, String filename) {
		try {
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(o);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			storeObject(i,"IOException.ser");
		}
	}

	public Object readObject(String filename) {
		Object obj = null;
		try {
		
			FileInputStream fileIn = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			obj = in.readObject();
			in.close();
			fileIn.close();
		} catch (FileNotFoundException a) {
			System.out.println(a.getMessage());
//			storeObject(a,"FileNotFound.ser");
		} catch (IOException i) {
			System.out.println(i.getMessage());
//			storeObject(i,"IOException.ser");
		} catch (ClassNotFoundException c) {
			System.out.println(c.getMessage());
//			storeObject(c,"ClassNotFoundException.ser");
		}
		return obj;
	}
}