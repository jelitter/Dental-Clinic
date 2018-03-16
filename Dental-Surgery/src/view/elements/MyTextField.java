package view.elements;

import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class MyTextField extends TextField {
	public MyTextField() {
		this.setFont(new Font("Arial", 22));
		this.setHeight(60);
		this.setWidth(300);
		this.setMinWidth(300);
	}
}
