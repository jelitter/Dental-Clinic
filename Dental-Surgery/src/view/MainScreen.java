package view;

import java.util.Optional;

import application.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.elements.MyButton;

public class MainScreen {

	private static final int WIDTH = 1200;
	private static final int HEIGHT = 800;

	private Stage primaryStage;
	private static MainScreen instance;
	private MyButton btnPatients, btnProcedures, btnInvoices, btnReports, btnSave, btnExit;
	private VBox mainAreaRight;
	private Label statusBar;

	public MainScreen() {
		instance = this;
		go();
	}

	public static MainScreen getInstance() {
		if (instance == null)
			return new MainScreen();
		else {
			instance.show();
			return instance;
		}
	}

	private void go() {

		primaryStage = new Stage();
		primaryStage.getIcons().add(new Image("/assets/icon.png"));

		VBox root = new VBox();

		HBox mainArea = new HBox(10);
		mainArea.setPadding(new Insets(10));

		primaryStage.setMinWidth(WIDTH);
		primaryStage.setMinHeight(HEIGHT);
		primaryStage.setWidth(WIDTH);
		primaryStage.setHeight(HEIGHT);
		double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
		double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
		primaryStage.setX(screenWidth / 2 - WIDTH / 2);
		primaryStage.setY(screenHeight / 2 - HEIGHT / 2);

		final Menu menu1 = new Menu("File");
		final Menu menu2 = new Menu("Options");
		final Menu menu3 = new Menu("Help");
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(menu1, menu2, menu3);

		statusBar = new Label("Status text");
		statusBar.setPadding(new Insets(5, 0, 5, 10));

		VBox options = new VBox(10);

		setupButtons();

		options.getChildren().addAll(btnPatients, btnProcedures, btnInvoices, btnReports, btnSave, btnExit);

		mainAreaRight = new VBox(10);
		mainAreaRight.getChildren().add(new Label("Test Main Area"));

		mainArea.getChildren().add(options);
		mainArea.getChildren().add(mainAreaRight);
		mainArea.setStyle("-fx-base: #CCCCCC;");

		root.getChildren().add(menuBar);
		root.getChildren().add(mainArea);
		root.getChildren().add(statusBar);

		VBox.setVgrow(mainArea, Priority.ALWAYS);

		Scene scene = new Scene(root, WIDTH, HEIGHT);

		primaryStage.setTitle("Dental Surgery Management");
		primaryStage.setScene(scene);
		
		setEventHandlers();
		show();
	}

	public void show() {
		primaryStage.show();
	}

	public void quit() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Quit Dental Surgery Management");
		alert.setHeaderText("Quit program");
		alert.setContentText("Are you sure you want to quit without saving changes?");

		ButtonType buttonSaveAndQuit = new ButtonType("Save and quit");
		ButtonType buttonQuit = new ButtonType("Quit without saving");
		ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonSaveAndQuit, buttonQuit, buttonTypeCancel);
		
		// Adding icon to Quit dialog
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(Main.class.getResourceAsStream("/assets/icon.png")));
		


		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonQuit) {
			Platform.exit();

		} else if (result.get() == buttonSaveAndQuit) {
			// Save here.
			Platform.exit();
		}
	}

	private void save() {
		btnSave.setDisable(true);
		btnSave.setIcon("spinner.gif");
		btnSave.setText("Saving...");
		setStatusText("Saving to database...");
		new Timeline(new KeyFrame(Duration.millis(2000), ae -> {
			// setStatus("Preparing stuff...");
			btnSave.setIcon("done.png");
			btnSave.setText("Saved!");
			setStatusText("Saving to database done!");
		})).play();
		new Timeline(new KeyFrame(Duration.millis(3500), ae -> {
			// setStatus("Preparing stuff...");
			btnSave.setIcon("save.png");
			btnSave.setText("Save");
			setStatusText("App Ready");
			btnSave.setDisable(false);
		})).play();
	}
	
	private void setupButtons() {
		btnPatients = new MyButton("Patients");
		btnProcedures = new MyButton("Procedures");
		btnInvoices = new MyButton("Invoices");
		btnReports = new MyButton("Reports");
		btnSave = new MyButton("Save", "Success");
		btnExit = new MyButton("Exit", "Warning");

		btnPatients.setIcon("patient.png");
		btnProcedures.setIcon("procedure.png");
		btnInvoices.setIcon("invoice.png");
		btnReports.setIcon("report.png");
		btnSave.setIcon("save.png");
		btnExit.setIcon("exit.png");		
	}
	
	private void setEventHandlers() {
		
		btnPatients.setOnMouseClicked(e -> {
			mainAreaRight.getChildren().clear();
			PatientsScreen.getInstance();
		});
		
		btnProcedures.setOnMouseClicked(e -> {
			mainAreaRight.getChildren().clear();
			ProceduresScreen.getInstance();
		});
		
		btnInvoices.setOnMouseClicked(e -> {
			mainAreaRight.getChildren().clear();
			InvoicesScreen.getInstance();
		});
		
		btnReports.setOnMouseClicked(e -> {
			mainAreaRight.getChildren().clear();
			ReportsScreen.getInstance();
		});
		
		btnSave.setOnMouseClicked(e -> {
			save();
		});

		btnExit.setOnMouseClicked(e -> {
			quit();
		});

		primaryStage.setOnCloseRequest(e -> {
			quit();
			// Consuming the event to avoid it bubbling to application if Quit dialog is cancelled.
			e.consume();
		});
	}
	
	private void setStatusText(String text) {
		statusBar.setText(text);
	}
	
	public VBox getLayout() {
		return this.mainAreaRight;
	}
}
