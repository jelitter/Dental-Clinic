package view.elements;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class MiniButton extends Button {

	public MiniButton(String text) {
		super(text);
		setFont(Font.font("Arial", 10));
		setMinWidth(USE_COMPUTED_SIZE);
		setMinHeight(USE_COMPUTED_SIZE);
		setPadding(new Insets(5));
		
		if (text.equals("Add")) {
			this.setStyle("-fx-base: LIMEGREEN; -fx-background-radius: 0;");
		} else if (text.equals("Remove")) {
			this.setStyle("-fx-base: LIGHTCORAL; -fx-background-radius: 0;");
		} if (text.equals("Edit")) {
			this.setStyle("-fx-base: DEEPSKYBLUE; -fx-background-radius: 0;");
		} 
	}
}
