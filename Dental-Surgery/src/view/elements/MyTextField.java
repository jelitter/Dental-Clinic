package view.elements;

import javafx.scene.control.TextField;

public class MyTextField extends TextField {
	public MyTextField() {
		this.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
		this.setHeight(60);
		this.setWidth(300);
		this.setMinWidth(300);
	}
}
