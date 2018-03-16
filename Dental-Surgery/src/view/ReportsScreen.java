package view;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ReportsScreen extends Pane {

	private static ReportsScreen instance;

	public ReportsScreen() {
		instance = this;
		go();
	}

	public static ReportsScreen getInstance() {
		if (instance == null) {
			return new ReportsScreen();
		} else {
			instance.go();			
			return instance;
		}
	}

	public void go() {
		VBox pane = MainScreen.getInstance().getLayout();
		Label title = new Label("Reports Screen");
		Label subtitle = new Label("This is for Report management");
		pane.getChildren().addAll(title, subtitle);
	}
}
