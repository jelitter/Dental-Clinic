package view;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PatientsScreen extends Pane {

	private static PatientsScreen instance;

	public PatientsScreen() {
		instance = this;
		go();
	}

	public static PatientsScreen getInstance() {
		if (instance == null) {
			return new PatientsScreen();
		} else {
			instance.go();			
			return instance;
		}
	}

	public void go() {
		VBox pane = MainScreen.getInstance().getLayout();
		Label title = new Label("Patients Screen");
		Label subtitle = new Label("This is for patient management");
		pane.getChildren().addAll(title, subtitle);
	}
}
