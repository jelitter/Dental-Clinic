package view;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ProceduresScreen extends Pane {

	private static ProceduresScreen instance;

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
		VBox pane = MainScreen.getInstance().getLayout();
		pane.getChildren().clear();
		Label title = new Label("Procedures");
		title.setFont(new Font("Arial", 22));
		title.setWrapText(true);
		Label subtitle = new Label("This is for procedure management");
		pane.getChildren().addAll(title, subtitle);
	}
}
