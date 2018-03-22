package view;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import view.elements.MyTitle;

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
		MyTitle title = new MyTitle("Procedures");
		Label subtitle = new Label("This is for procedure management");
		pane.getChildren().addAll(title, subtitle);
	}
}
