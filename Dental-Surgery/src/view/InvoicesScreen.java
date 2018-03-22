package view;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import view.elements.MyTitle;

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
		MyTitle title = new MyTitle("Invoices");
		Label subtitle = new Label("This is for Invoice management");
		pane.getChildren().addAll(title, subtitle);
	}
}
