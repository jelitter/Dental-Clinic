package view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import view.elements.MyTitle;

public class MaintenanceScreen extends Pane {

	private static MaintenanceScreen instance;
	private VBox pane;
	private MyTitle title;
	private Label subtitle;

	public MaintenanceScreen() {
		instance = this;
		go();
	}

	public static MaintenanceScreen getInstance() {
		if (instance == null) {
			return new MaintenanceScreen();
		} else {
			instance.go();			
			return instance;
		}
	}

	public void go() {
		pane = new VBox(10);
		pane.setPadding(new Insets(20));
		pane.setStyle("-fx-background-color: #DDEEFF");
		title = new MyTitle("Maintenance");
		subtitle = new Label("This is for procedures Maintenance");
		pane.getChildren().addAll(title, subtitle);
	}
	public VBox getPane() {
		return pane;
	}
}
