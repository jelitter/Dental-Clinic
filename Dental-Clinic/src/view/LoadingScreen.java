package view;

import controller.ClinicController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.elements.MyTitle;

public class LoadingScreen {

	private static final int WIDTH = 500;
	private static final int HEIGHT = 400;

	private Stage primaryStage;
	private static LoadingScreen instance;
	private Label lblStatus;
	private ProgressBar progress;
	private ImageView imgv;

	private LoadingScreen() {
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
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image("/assets/icon.png" ));

		// Image
		Image icon = new Image("/assets/icon.png");
		imgv = new ImageView();
		imgv.setImage(icon);
		imgv.setFitWidth(100);
		imgv.setPreserveRatio(true);
		imgv.setSmooth(true);
		imgv.setCache(true);
		DropShadow ds = new DropShadow();
		ds.setOffsetY(0.0f);
		ds.setOffsetX(0.0f);
		ds.setColor(Color.BLACK);
		imgv.setEffect(ds);

		BorderPane root = new BorderPane();
		VBox vbox = new VBox(10);

		root.setPrefWidth(WIDTH -80);
		root.setTop(vbox);
		root.setBottom(progress);
		root.setStyle(
				"-fx-background-image: url(" + "'/assets/background.png'" + "); -fx-background-size: cover;");


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

		MyTitle title = new MyTitle("Dental Clinic v2.0\nNow with DB!");

		lblStatus.setText("Loading database...");
		lblStatus.autosize();

		progress.setMinWidth(root.getWidth() -80);

		vbox.getChildren().add(title);
		vbox.getChildren().add(imgv);

		vbox.getChildren().add(new Label("Loading data from " + ClinicController.getDataSourceString()));
		vbox.getChildren().add(lblStatus);

		vbox.setAlignment(Pos.TOP_CENTER);

		primaryStage.setTitle("Dental Clinic");
		primaryStage.setScene(scene);
		show();

	}

	public void show() {
		primaryStage.show();
		
		Timeline tl = new Timeline(new KeyFrame(Duration.millis(33), ae -> {
			imgv.setRotate(imgv.getRotate() + 2);
		}));
		tl.setCycleCount(Timeline.INDEFINITE);
		tl.play();

		new Timeline(new KeyFrame(Duration.millis(1000), ae -> {
			setStatus("Organizing data...");
		})).play();

		new Timeline(new KeyFrame(Duration.millis(2000), ae -> {
			setStatus("Preparing stuff...");
		})).play();

		new Timeline(new KeyFrame(Duration.millis(3000), ae -> {
			setStatus("Ready!");
			setProgress(1F);
			imgv.setRotate(0);
			tl.stop();
		})).play();

		new Timeline(new KeyFrame(Duration.millis(4000), ae -> {
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
