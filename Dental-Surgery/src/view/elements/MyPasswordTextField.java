package view.elements;

import javafx.scene.control.PasswordField;

public class MyPasswordTextField extends PasswordField {
	public MyPasswordTextField() {
		this.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
		this.setHeight(60);
		this.setWidth(300);
		this.setMinWidth(300);
	}
}
