package view.elements;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

public class MyButton extends Button {

	public MyButton(String string) {
		super(string);
		addHandlers();
		this.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
		this.setHeight(60);
		this.setWidth(300);
		this.setMinWidth(200);
	}
	
	private void addHandlers() {
		this.addEventHandler(MouseEvent.MOUSE_ENTERED, (e) -> {
			this.setEffect(new DropShadow());
			this.setStyle("-fx-font: 24 arial; -fx-base: #b6e7c9;");
		});
		
		this.addEventHandler(MouseEvent.MOUSE_EXITED, (e) -> {
			this.setEffect(null);
			this.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
		});
	}
}
