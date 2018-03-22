package view;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import view.elements.MyTitle;

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
		pane.getChildren().clear();
		MyTitle title = new MyTitle("Reports");
		Label subtitle = new Label("This is for Report management");
		pane.getChildren().addAll(title, subtitle);
	}
}
