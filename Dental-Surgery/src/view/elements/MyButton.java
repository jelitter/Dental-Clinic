package view.elements;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class MyButton extends Button {

	static final private String FONT = "\"Century Gothic\"";

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
		this.setAlignment(Pos.BASELINE_LEFT);
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
		String style = "-fx-font: 18 " + FONT + "; ";

		if (type.equals("Generic")) {
			style += "-fx-base: DEEPSKYBLUE;";
		} else if (type.equals("Success")) {
			style += "-fx-base: LIMEGREEN;";
		} else if (type.equals("Warning")) {
			style += "-fx-base: LIGHTCORAL;";
		} else {
			style += "-fx-base: DEEPSKYBLUE;";
		}
		this.setStyle(style);
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
}
