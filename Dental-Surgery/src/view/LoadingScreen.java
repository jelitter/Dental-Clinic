package view;

import application.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoadingScreen {

	private static final int WIDTH = 500;
	private static final int HEIGHT = 400;

	private Stage primaryStage;
	private static LoadingScreen instance;
	private Label lblStatus;
	private ProgressBar progress;

	public LoadingScreen() {
		instance = this;
		lblStatus = new Label();
		progress = new ProgressBar();
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
		
		// Image
		Image icon = new Image("/assets/icon.png");
		ImageView imgv = new ImageView();
		imgv.setImage(icon);
		imgv.setFitWidth(100);
		imgv.setPreserveRatio(true);
		imgv.setSmooth(true);
		imgv.setCache(true);
				
		
		VBox root = new VBox(10);
		VBox.setVgrow(root, Priority.ALWAYS);
		root.setMinWidth(WIDTH);
		
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

		lblStatus.setText("Loading database...");
		lblStatus.autosize();
		
		progress.setProgress(0.0F);
		progress.minWidth(WIDTH);
		

		root.getChildren().add(title);
		root.getChildren().add(imgv);
		
		root.getChildren().add(new Label("(Simulating DB connection)"));
		root.getChildren().add(lblStatus);
		root.getChildren().add(progress);
		
		
		root.setAlignment(Pos.TOP_CENTER);


		primaryStage.setTitle("Dental Surgery Management");
		primaryStage.setScene(scene);
		show();

	}

	public void show() {
		primaryStage.show();

		new Timeline(new KeyFrame(Duration.millis(2000), ae -> {
			setStatus("Organizing data...");
			setProgress(0.25F);
		})).play();

		new Timeline(new KeyFrame(Duration.millis(4000), ae -> {
			setStatus("Preparing stuff...");
			setProgress(0.5F);
		})).play();
		
		new Timeline(new KeyFrame(Duration.millis(6000), ae -> {
			setStatus("Almost done...");
			setProgress(1F);
		})).play();

		new Timeline(new KeyFrame(Duration.millis(7000), ae -> {
			setProgress(1F);
			primaryStage.close();
			MainScreen.getInstance();
		})).play();
	}

	public void setStatus(String text) {
		this.lblStatus.setText(text);
	}
	
	public void setProgress(double val) {
		this.progress.setProgress(val);
	}

}