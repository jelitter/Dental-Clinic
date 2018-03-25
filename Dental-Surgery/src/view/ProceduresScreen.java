package view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import view.elements.MyTitle;

public class ProceduresScreen extends Pane {

	private static ProceduresScreen instance;
	private VBox pane;
	private MyTitle title;
	private Label subtitle;

	public ProceduresScreen() {
		instance = this;
		go();
	}

	public static ProceduresScreen getInstance() {
		if (instance == null) {
			return new ProceduresScreen();
		} else {
			instance.go();			
			return instance;
		}
	}

	public void go() {
		pane = new VBox(10);
		pane.setPadding(new Insets(20));
		pane.setStyle("-fx-background-color: #DDEEFF");
		title = new MyTitle("Procedures");
		subtitle = new Label("This is for procedure management");
		pane.getChildren().addAll(title, subtitle);
	}
	public VBox getPane() {
		return pane;
	}
}
