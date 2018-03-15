package view;

import application.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoadingScreen {

	private static final int WIDTH = 500;
	private static final int HEIGHT = 300;

	private Stage primaryStage;
	private static LoadingScreen instance;
	private Label lblStatus;

	public LoadingScreen() {
		instance = this;
		lblStatus = new Label();
		go();
	}

	public static LoadingScreen getInstance() {
		if (instance == null)
			return new LoadingScreen();
		else {
			instance.show();
			return instance;
		}
	}

	private void go() {

		primaryStage = new Stage();
		primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream( "/assets/icon.png" )));


		BorderPane root = new BorderPane();
		root.setPadding(new Insets(40));

		primaryStage.setMinWidth(WIDTH);
		primaryStage.setMinHeight(HEIGHT);
		primaryStage.setWidth(WIDTH);
		primaryStage.setHeight(HEIGHT);

		double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
		double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

		Scene scene = new Scene(root, WIDTH, HEIGHT);
		primaryStage.setX(screenWidth / 2 - WIDTH / 2);
		primaryStage.setY(screenHeight / 2 - HEIGHT / 2);

		Text title = new Text("Dental Surgery v1.0");
		title.setStyle("-fx-font: 22 Arial; -fx-base: #dd8800;");

		VBox loading = new VBox(20);
		lblStatus.setText("Loading database...");
		lblStatus.autosize();

		loading.getChildren().addAll(title, lblStatus);

		root.setTop(title);
		root.setLeft(loading);

		primaryStage.setTitle("Dental Surgery Management");
		primaryStage.setScene(scene);
		show();

	}

	public void show() {
		primaryStage.show();

		new Timeline(new KeyFrame(Duration.millis(1500), ae -> {
			setStatus("Organizing data...");
		})).play();

		new Timeline(new KeyFrame(Duration.millis(3000), ae -> {
			setStatus("Preparing to launch...");
		})).play();

		new Timeline(new KeyFrame(Duration.millis(4500), ae -> {
			primaryStage.close();
			MainScreen.getInstance();
		})).play();
	}

	public void setStatus(String text) {
		this.lblStatus.setText(text);
	}

}
