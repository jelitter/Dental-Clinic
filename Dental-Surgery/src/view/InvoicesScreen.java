package view;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class InvoicesScreen extends Pane {

	private static InvoicesScreen instance;

	public InvoicesScreen() {
		instance = this;
		go();
	}

	public static InvoicesScreen getInstance() {
		if (instance == null) {
			return new InvoicesScreen();
		} else {
			instance.go();			
			return instance;
		}
	}

	public void go() {
		VBox pane = MainScreen.getInstance().getLayout();
		pane.getChildren().clear();
		Label title = new Label("Invoices");
		title.setFont(new Font("Arial", 22));
		title.setWrapText(true);
		Label subtitle = new Label("This is for Invoice management");
		pane.getChildren().addAll(title, subtitle);
	}
}
