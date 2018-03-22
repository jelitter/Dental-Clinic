package view.elements;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MyTitle extends Text {

	public MyTitle(String text) {
		super(text);

		DropShadow ds = new DropShadow();
		ds.setOffsetY(0.0f);
		ds.setOffsetX(0.0f);
		ds.setColor(Color.BLACK);

		this.setEffect(ds);
		this.setFill(Color.WHITE);
		this.setFont(Font.font(null, FontWeight.BOLD, 30));
	}
}
