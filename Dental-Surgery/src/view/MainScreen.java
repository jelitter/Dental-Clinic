package view;

import controller.ClinicController;
import java.util.ArrayList;
import java.util.Optional;
import application.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableStringValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.elements.MyButton;

public class MainScreen {

	private static final int WIDTH = 1000;
	private static final int HEIGHT = 700;

	private ClinicController controller;
	private Stage primaryStage;
	private static MainScreen instance;
	private MyButton btnPatients, btnProcedures, btnInvoices, btnReports, btnSave, btnSaveQuit, btnExit;
	private MyButton activeButton;
	private VBox mainAreaLeft;
	private Pane mainAreaRight;
	private Label statusBar;
	private MenuBar menuBar;
	private VBox root;
	private Group mainGroup;
	private VBox patientPane;
	private VBox procedurePane;
	private VBox invoicePane;
	private VBox reportPane;
	private HBox mainArea;
	private Scene scene;
	ObservableStringValue saved;
	
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

		controller = new ClinicController();
		root = new VBox(0);
		scene = new Scene(root, WIDTH, HEIGHT);
		
		setupStage();
		setupMenu();
		setupMainArea();
		setupStatusBar();
		root.getChildren().addAll(menuBar, mainArea,statusBar);
		setEventHandlers();
		show();
		
		activatePane(patientPane, btnPatients);
		
		saved = new SimpleStringProperty("Saved: " + String.valueOf(controller.isSaved())); 
		statusBar.textProperty().bind(saved);
	}
	
	public ClinicController getController() {
		return controller;
	}
	
	/**
	 * Setup Main Area containing left button bar and right panes
	 */
	private void setupMainArea() {
		mainArea = new HBox(10);
		mainArea.setPadding(new Insets(10));
		setupMainAreaLeft();
		setupMainAreaRight();
		mainArea.getChildren().add(mainAreaLeft);
		mainArea.getChildren().add(mainAreaRight);
		mainArea.setStyle("-fx-base: #CCCCCC;");
		VBox.setVgrow(mainArea, Priority.ALWAYS);
		HBox.setHgrow(mainArea, Priority.ALWAYS);
	}

	/**
	 * Setup panes for Patients, Procedures, Invoices and Reports
	 */
	private void setupMainAreaRight() {
		mainAreaRight = new Pane();
		mainAreaRight.setStyle("-fx-font-smoothing-type: gray; -fx-base: #CCCCDD;");
		mainAreaRight.prefWidthProperty().bind(mainArea.widthProperty().subtract(230));
		mainAreaRight.prefHeightProperty().bind(mainArea.heightProperty());
		
		mainGroup = new Group();
		patientPane = PatientsScreen.getInstance().getPane();
		procedurePane = ProceduresScreen.getInstance().getPane();
		invoicePane = InvoicesScreen.getInstance().getPane();
		reportPane = ReportsScreen.getInstance().getPane();
		patientPane.prefWidthProperty().bind(mainAreaRight.widthProperty());
		patientPane.prefHeightProperty().bind(mainAreaRight.heightProperty());
		procedurePane.prefWidthProperty().bind(mainAreaRight.widthProperty());
		procedurePane.prefHeightProperty().bind(mainAreaRight.heightProperty());
		invoicePane.prefWidthProperty().bind(mainAreaRight.widthProperty());
		invoicePane.prefHeightProperty().bind(mainAreaRight.heightProperty());
		reportPane.prefWidthProperty().bind(mainAreaRight.widthProperty());
		reportPane.prefHeightProperty().bind(mainAreaRight.heightProperty());
		mainGroup.getChildren().addAll(patientPane, procedurePane, invoicePane, reportPane);
		
		mainAreaRight.getChildren().add(mainGroup);
	}

	private void setupMainAreaLeft() {
		mainAreaLeft = new VBox(10);
		setupButtons();
		Region spacing = new Region();
        VBox.setVgrow(spacing, Priority.ALWAYS);
		mainAreaLeft.getChildren().addAll(btnPatients, btnProcedures, btnInvoices, btnReports, spacing, btnSave, btnSaveQuit, btnExit);
		BackgroundImage bgImage = new BackgroundImage(
				new Image("/assets/background.png"), 
				BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, 
				BackgroundSize.DEFAULT
				);
		
		Background bg = new Background(bgImage);
		mainAreaLeft.setBackground(bg);
	}

	private void setupStatusBar() {
		statusBar = new Label("Status text");
		statusBar.setPadding(new Insets(0, 10, 5, 10));
	}

	private void setupStage() {
		primaryStage = new Stage();
		primaryStage.getIcons().add(new Image("/assets/icon.png"));
		primaryStage.setMinWidth(WIDTH);
		primaryStage.setMinHeight(HEIGHT);
		primaryStage.setWidth(WIDTH);
		primaryStage.setHeight(HEIGHT);
		double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
		double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
		primaryStage.setX(screenWidth / 2 - WIDTH / 2);
		primaryStage.setY(screenHeight / 2 - HEIGHT / 2);
		primaryStage.setTitle("Dental Surgery Management");
		primaryStage.setScene(scene);
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
		
		// Centering dialog
		Double alertX = primaryStage.getX() + primaryStage.getWidth()/2 - 90;
		Double alertY = primaryStage.getY() + primaryStage.getHeight()/2 - 112;
		alert.setX(alertX);
		alert.setY(alertY);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonQuit) {
			Platform.exit();
		} else if (result.get() == buttonSaveAndQuit) {
			save(btnSaveQuit);
		}
	}

	private void save(MyButton btn) {
		root.setDisable(true);
		btn.setIcon("spinner.gif");
		btn.setText("Saving...");
//		setStatusText("Saving to database...");
//		FileStorage.storeObservableObject(new ArrayList<Patient>(PatientsScreen.getInstance().getPatientsData()), "src/data/patientData.ser");
		
//		ClinicController.getInstance().save();
		controller.saveClinicToSerial();
		
		new Timeline(new KeyFrame(Duration.millis(2000), ae -> {
			btn.setIcon("done.png");
			btn.setText("Saved!");
//			setStatusText("Saving to database done!");
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
//			setStatusText("App Ready");
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
		activeButton = btnExit;

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
		final Menu menuOptions = new Menu("Options");
		final Menu menuHelp = new Menu("Help");
		
		MenuItem loadFromCSV = new MenuItem("Load Clinic data from CSV file");
		MenuItem loadFromSerial = new MenuItem("Load Clinic data from Serial file"); 
		loadFromCSV.setOnAction(e -> { 
			controller.addPatientsFromCSV();
//			PatientsScreen.getInstance().setTableItems();
			
		});
		loadFromSerial.setOnAction(e -> {
//			controller.loadClinicFromSerial();
//			PatientsScreen.getInstance().setTableItems();
		});
		

		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(e -> quit());
		
		menuFile.getItems().addAll(loadFromCSV, loadFromSerial, exit);

		menuItems.add(menuFile);
		menuItems.add(menuOptions);
		menuItems.add(menuHelp);

		menuBar = new MenuBar();

		for (Menu m : menuItems) {
			menuBar.getMenus().add(m);
		}
	}

	private void activatePane(VBox p, MyButton b) {
		activeButton.deActivate();
		b.activate();
		activeButton = b;
		p.toFront();
	}

	private void setEventHandlers() {
		btnPatients.setOnMouseClicked(e -> activatePane(patientPane, btnPatients));
		btnProcedures.setOnMouseClicked(e -> activatePane(procedurePane, btnProcedures));
		btnInvoices.setOnMouseClicked(e -> activatePane(invoicePane, btnInvoices));
		btnReports.setOnMouseClicked(e -> activatePane(reportPane, btnReports));

		btnSave.setOnMouseClicked(e -> save(btnSave));
		btnSaveQuit.setOnMouseClicked(e -> save(btnSaveQuit));
		btnExit.setOnMouseClicked(e -> quit());

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
	
	public Stage getStage() { return primaryStage; }
 }
