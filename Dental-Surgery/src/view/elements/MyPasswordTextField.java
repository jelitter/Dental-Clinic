package view.elements;

import javafx.scene.control.PasswordField;
import javafx.scene.text.Font;

public class MyPasswordTextField extends PasswordField {
	public MyPasswordTextField() {
		this.setFont(new Font("Arial", 22));
		this.setHeight(60);
		this.setWidth(300);
		this.setMinWidth(300);
	}
}
