package view.elements;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

public class MyButton extends Button {

	public MyButton(String string) {
		this(string, "Generic");
	}

	public MyButton(String string, String type) {
		super(string);
		addHandlers();
		this.setHeight(60);
		this.setWidth(300);
		this.setMinWidth(200);
		this.setType(type);
	}

	private void addHandlers() {
		this.addEventHandler(MouseEvent.MOUSE_ENTERED, (e) -> {
			this.setEffect(new DropShadow());
		});

		this.addEventHandler(MouseEvent.MOUSE_EXITED, (e) -> {
			this.setEffect(null);
		});
	}

	private void setType(String type) {
		if (type.equals("Generic")) {
			this.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
		} else if (type.equals("Info")) {
			this.setStyle("-fx-font: 22 arial; -fx-base: #b6c9e7;");
		} else if (type.equals("Warning")) {
			this.setStyle("-fx-font: 22 arial; -fx-base: #e7b6c9;");
		}
	}
}
