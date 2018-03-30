package view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import view.elements.MyTitle;

public class InvoicesScreen extends Pane {

	private static InvoicesScreen instance;
	private VBox pane;
	private MyTitle title;
	private Label subtitle;

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
		pane = new VBox(10);
		pane.setPadding(new Insets(20));
//		pane.setStyle("-fx-background-color: #DDEEFF");
		title = new MyTitle("Invoices");
		subtitle = new Label("This is for Invoice Management");
		pane.getChildren().addAll(title, subtitle);
	}
	public VBox getPane() {
		return pane;
	}
}
