package view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import view.elements.MyTitle;

public class ReportsScreen extends Pane {

	private static ReportsScreen instance;
	private VBox pane;
	private MyTitle title;
	private Label subtitle;

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
		pane = new VBox(10);
		pane.setPadding(new Insets(20));
		pane.setStyle("-fx-background-color: #DDEEFF");
		title = new MyTitle("Reports");
		subtitle = new Label("This is for Report Management");
		pane.getChildren().addAll(title, subtitle);
	}
	public VBox getPane() {
		return pane;
	}
}
