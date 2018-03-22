package view;

import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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
	private MyButton btnPatients, btnProcedures, btnInvoices, btnReports, btnSave, btnSaveQuit, btnExit;
	private MyButton activeButton;
	private VBox mainAreaLeft, mainAreaRight;
	private Label statusBar;
	private MenuBar menuBar;
	VBox root;

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

		root = new VBox();

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

		setupMenu();

		statusBar = new Label("Status text");
		statusBar.setPadding(new Insets(5, 0, 5, 10));

		mainAreaLeft = new VBox(10);
		setupButtons();

		mainAreaLeft.getChildren().addAll(btnPatients, btnProcedures, btnInvoices, btnReports, btnSave, btnSaveQuit, btnExit);

		mainAreaRight = new VBox(10);
		mainAreaRight.setStyle("-fx-font-smoothing-type: gray; -fx-base: #CCCCDD;");
		mainAreaRight.setPrefWidth(WIDTH - mainAreaLeft.getWidth() -60);

		
//		mainArea.maxWidthProperty().bind(primaryStage.widthProperty());
//		mainArea.minWidthProperty().bind(primaryStage.widthProperty());
//		mainArea.maxHeightProperty().bind(primaryStage.heightProperty());
//		mainArea.minHeightProperty().bind(primaryStage.heightProperty());

		BackgroundImage bgImage = new BackgroundImage(
				new Image("/assets/background.png"), 
				BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, 
				BackgroundSize.DEFAULT
				);
		
		Background bg = new Background(bgImage);
		mainAreaRight.setBackground(bg);

		mainArea.getChildren().add(mainAreaLeft);
		mainArea.getChildren().add(mainAreaRight);
		mainArea.setStyle("-fx-base: #CCCCCC;");
		
		mainArea.prefWidthProperty().bind(root.widthProperty().multiply(1));


		root.getChildren().add(menuBar);
		root.getChildren().add(mainArea);
		root.getChildren().add(statusBar);

		VBox.setVgrow(mainArea, Priority.ALWAYS);
		HBox.setHgrow(mainArea, Priority.ALWAYS);

		Scene scene = new Scene(root, WIDTH, HEIGHT);

		primaryStage.setTitle("Dental Surgery Management");
		primaryStage.setScene(scene);

		setEventHandlers();
		show();
		PatientsScreen.getInstance();
		btnPatients.activate();
		activeButton = btnPatients;
	}

	public void show() {
		primaryStage.show();
	}

	public void quit() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Quit Dental Surgery Management");
		alert.setHeaderText("Quit program");
		alert.setContentText("Are you sure you want to quit without saving changes?");

		ButtonType buttonSaveAndQuit = new ButtonType("_Save and quit");
		ButtonType buttonQuit = new ButtonType("_Quit without saving");
		ButtonType buttonTypeCancel = new ButtonType("_Cancel", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonSaveAndQuit, buttonQuit, buttonTypeCancel);

		// Adding icon to Quit dialog
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(Main.class.getResourceAsStream("/assets/icon.png")));

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonQuit) {
			Platform.exit();

		} else if (result.get() == buttonSaveAndQuit) {
			save(btnSaveQuit);
//			Platform.exit();
		}
	}

	private void save(MyButton btn) {
		root.setDisable(true);
		btn.setIcon("spinner.gif");
		btn.setText("Saving...");
		setStatusText("Saving to database...");
		new Timeline(new KeyFrame(Duration.millis(2000), ae -> {
			btn.setIcon("done.png");
			btn.setText("Saved!");
			setStatusText("Saving to database done!");
		})).play();
		new Timeline(new KeyFrame(Duration.millis(3500), ae -> {
			
			if (btn == this.btnSave) {
				root.setDisable(false);
				btn.setIcon("save.png");
				btn.setText("Save");
			} else {
				btn.setText("Closing...");
				new Timeline(new KeyFrame(Duration.millis(750), an -> {
					Platform.exit();
				})).play();
			}
			setStatusText("App Ready");
		})).play();
	}

	private void setupButtons() {
		btnPatients = new MyButton("Patients");
		btnProcedures = new MyButton("Procedures");
		btnInvoices = new MyButton("Invoices");
		btnReports = new MyButton("Reports");
		btnSave = new MyButton("Save", "Success");
		btnSaveQuit = new MyButton("Save and Exit", "Success");
		btnExit = new MyButton("Exit", "Warning");

		btnPatients.setIcon("patient.png");
		btnProcedures.setIcon("procedure.png");
		btnInvoices.setIcon("invoice.png");
		btnReports.setIcon("report.png");
		btnSave.setIcon("save.png");
		btnSaveQuit.setIcon("savequit.png");
		btnExit.setIcon("exit.png");
	}

	private void setupMenu() {
		final ArrayList<Menu> menuItems = new ArrayList<Menu>();
		final Menu menuFile = new Menu("File");
		final Menu menuPatients = new Menu("Patients");
		final Menu menuProcedures = new Menu("Procedures");
		final Menu menuInvoices = new Menu("Invoices");
		final Menu menuReports = new Menu("Reports");
		final Menu menuOptions = new Menu("Options");
		final Menu menuHelp = new Menu("Help");

		menuItems.add(menuFile);
		menuItems.add(menuPatients);
		menuItems.add(menuProcedures);
		menuItems.add(menuInvoices);
		menuItems.add(menuReports);
		menuItems.add(menuOptions);
		menuItems.add(menuHelp);

		menuBar = new MenuBar();

		for (Menu m : menuItems) {
			menuBar.getMenus().add(m);
		}
	}

	private void setEventHandlers() {

		btnPatients.setOnMouseClicked(e -> {
			activeButton.deActivate();
			btnPatients.activate();
			activeButton = btnPatients;
			PatientsScreen.getInstance();
		});

		btnProcedures.setOnMouseClicked(e -> {
			activeButton.deActivate();
			btnProcedures.activate();
			activeButton = btnProcedures;
			ProceduresScreen.getInstance();
		});

		btnInvoices.setOnMouseClicked(e -> {
			activeButton.deActivate();
			btnInvoices.activate();
			activeButton = btnInvoices;
			InvoicesScreen.getInstance();
		});

		btnReports.setOnMouseClicked(e -> {
			activeButton.deActivate();
			btnReports.activate();
			activeButton = btnReports;
			ReportsScreen.getInstance();
		});

		btnSave.setOnMouseClicked(e -> {
			save(btnSave);
		});
		
		btnSaveQuit.setOnMouseClicked(e -> {
			save(btnSaveQuit);
		});

		btnExit.setOnMouseClicked(e -> {
			quit();
		});

		primaryStage.setOnCloseRequest(e -> {
			quit();
			// Consuming the event to avoid it bubbling to application if Quit dialog is
			// cancelled.
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
