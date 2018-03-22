package view.elements;

import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MyButton extends Button {

	static final Map<String, String> buttonColors = new HashMap<String, String>();

	static final private String FONT = "\"Century Gothic\"";
	private String type;

	public MyButton(String string) {
		this(string, "Generic");
	}

	public MyButton(String string, String type) {
		super(string);
		setButtonColors();
		addHandlers();
		this.setHeight(60);
		this.setWidth(300);
		this.setMinWidth(200);
		this.setType(type);
		this.deActivate();
		this.setAlignment(Pos.BASELINE_LEFT);
	}

	private void setButtonColors() {
		buttonColors.put("ACTIVE", "#DDEEFF");
		buttonColors.put("GENERIC", "DEEPSKYBLUE");
		buttonColors.put("SUCCESS", "LIMEGREEN");
		buttonColors.put("WARNING", "LIGHTCORAL");
	}

	private void setDefaultColor() {
		String style = "-fx-base: " + buttonColors.get(this.getType().toUpperCase()) + ";";
		this.setStyle(style);
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
		this.type = type;
	}

	private String getType() {
		return this.type;
	}

	public void setIcon(String iconName) {
		Image img = new Image("/assets/" + iconName);
		ImageView imgv = new ImageView();
		StackPane pane = new StackPane();
		imgv.setImage(img);
		imgv.setFitHeight(36);
		imgv.setPreserveRatio(true);
		imgv.setSmooth(true);
		imgv.setCache(true);
		pane.getChildren().add(imgv);
		pane.setPadding(new Insets(5, 10, 5, 0));
		this.setGraphic(pane);
		this.setContentDisplay(ContentDisplay.LEFT);
	}

	public void activate() {
		this.setFont(Font.font(FONT, FontWeight.BOLD, 18));
		String style = "-fx-base: " + buttonColors.get("ACTIVE") + ";";
		this.setStyle(style);
	}

	public void deActivate() {
		this.setFont(Font.font(FONT, FontWeight.NORMAL, 18));
		this.setDefaultColor();
	}
}
