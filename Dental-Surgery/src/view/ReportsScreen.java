package view;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

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
		Label title = new Label("Reports");
		title.setFont(new Font("Arial", 22));
		title.setWrapText(true);
		Label subtitle = new Label("This is for Report management");
		pane.getChildren().addAll(title, subtitle);
	}
}
