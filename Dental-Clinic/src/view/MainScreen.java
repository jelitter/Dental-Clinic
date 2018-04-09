package view;

import controller.ClinicController;
import java.util.ArrayList;
import java.util.Optional;
import application.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.elements.MyButton;

public class MainScreen {

	public static final String APP_TITLE = "Dental Clinic";
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 700;

	private ClinicController controller;
	private Stage primaryStage;
	private static MainScreen instance;
	private MyButton btnPatients, btnReports, btnMaintenance; 
	private MyButton btnSave, btnSaveQuit, btnQuit;
	private MenuItem menuFileSave, menuFileSaveQuit, menuFileQuit;
	private MenuItem menuOptionsSerial, menuOptionsDB;
	private MyButton activeButton;
	private VBox mainAreaLeft;
	private Pane mainAreaRight;
	private Label statusBar;
	private MenuBar menuBar;
	private VBox root;
	private Group mainGroup;
	private VBox patientPane;
	private VBox reportPane;
	private VBox maintenancePane;
	private HBox mainArea;
	private Scene scene;
	
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
		
		root.setStyle("-fx-background-color: WHITE");
		
		setupStage();
		setupMenu();
		setupMainArea();
		setupStatusBar();
		root.getChildren().addAll(menuBar, mainArea,statusBar);
		setEventHandlers();
		showSaveButtons(false);
		show();
		
		activatePane(patientPane, btnPatients);
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
		reportPane = ReportsScreen.getInstance().getPane();
		maintenancePane = MaintenanceScreen.getInstance().getPane();
		
		patientPane.prefWidthProperty().bind(mainAreaRight.widthProperty());
		patientPane.prefHeightProperty().bind(mainAreaRight.heightProperty());
		reportPane.prefWidthProperty().bind(mainAreaRight.widthProperty());
		reportPane.prefHeightProperty().bind(mainAreaRight.heightProperty());
		maintenancePane.prefWidthProperty().bind(mainAreaRight.widthProperty());
		maintenancePane.prefHeightProperty().bind(mainAreaRight.heightProperty());
		mainGroup.getChildren().addAll(patientPane, reportPane, maintenancePane);
		
		mainAreaRight.getChildren().add(mainGroup);
	}

	private void setupMainAreaLeft() {
		mainAreaLeft = new VBox(10);
		setupButtons();
		Region spacing = new Region();
        VBox.setVgrow(spacing, Priority.ALWAYS);
		mainAreaLeft.getChildren().addAll(btnPatients, btnReports, btnMaintenance, spacing, btnSave, btnSaveQuit, btnQuit);
		BackgroundImage bgImage = new BackgroundImage(
				new Image("/assets/background.png"), 
				BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, 
				BackgroundSize.DEFAULT
				);
		
		Background bg = new Background(bgImage);
		mainAreaLeft.setBackground(bg);
		root.setBackground(bg);
	}

	private void setupStatusBar() {
		statusBar = new Label("Ready");
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
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(scene);
	}

	public void show() {
		primaryStage.show();
	}
	
	

	public void quit() {
		
		if (controller.isSaved()) {
			Platform.exit();
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Quit " + APP_TITLE);
			alert.setHeaderText("Warning: There are unsaved changes");
			alert.setContentText("Are you sure you want to quit without saving these changes?\n ");
			
			ButtonType buttonSaveAndQuit = new ButtonType("_Save and quit");
			ButtonType buttonQuit = new ButtonType("_Quit without saving");
			ButtonType buttonTypeCancel = new ButtonType("_Cancel", ButtonData.CANCEL_CLOSE);
			
			alert.getButtonTypes().setAll(buttonSaveAndQuit, buttonQuit, buttonTypeCancel);
			
			// Adding icon to Quit dialog
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(Main.class.getResourceAsStream("/assets/quit.png")));
			
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
	}

	private void save(MyButton btn) {
		root.setDisable(true);
		btn.setIcon("spinner.gif");
		btn.setText("Saving...");
		controller.save();

		// Adding some artificial delay to display some saving progress
		new Timeline(new KeyFrame(Duration.millis(1000), ae -> {
			btn.setIcon("done.png");
			btn.setText("Saved!");
		})).play();
		
		new Timeline(new KeyFrame(Duration.millis(2000), ae -> {
			
			if (btn == this.btnSave) {
				root.setDisable(false);
				btn.setIcon("save.png");
				btn.setText("Save");
				controller.savedChanges();				
			} else {
				btn.setText("Closing...");
				new Timeline(new KeyFrame(Duration.millis(750), an -> {
					Platform.exit();
				})).play();
			}
		})).play();
	}

	private void setupButtons() {
		btnPatients = new MyButton("Patients");
		btnReports = new MyButton("Reports");
		btnMaintenance = new MyButton("Maintenance");
		btnSave = new MyButton("Save", "Success");
		btnSaveQuit = new MyButton("Save and Quit", "Success");
		btnQuit = new MyButton("Quit", "Warning");
		activeButton = btnQuit;

		btnPatients.setIcon("patient.png");
		btnReports.setIcon("report.png");
		btnMaintenance.setIcon("maintenance.png");
		btnSave.setIcon("save.png");
		btnSaveQuit.setIcon("savequit.png");
		btnQuit.setIcon("quit.png");
	}

	private void setupMenu() {
		final ArrayList<Menu> menuItems = new ArrayList<Menu>();
		final Menu menuFile = new Menu("File");
		final Menu menuOptions = new Menu("Options");
		final Menu menuHelp = new Menu("Help");
		
		menuFileSave= new MenuItem("Save");
		menuFileSaveQuit = new MenuItem("Save and Quit   "); 
		menuFileQuit = new MenuItem("Quit");
		setMenuIcon(menuFileSave, "save.png");
		setMenuIcon(menuFileSaveQuit, "savequit.png");
		setMenuIcon(menuFileQuit, "quit.png");

		menuOptionsSerial = new MenuItem("Use serial file    ✔");
		menuOptionsDB = new MenuItem("Use database");
		setMenuIcon(menuOptionsSerial, "serial.png");
		setMenuIcon(menuOptionsDB, "db.png");
		menuOptionsSerial.setDisable(true);
		menuOptionsDB.setDisable(true);
		
		menuFileSaveQuit.setDisable(true);
		
		menuFileSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
		menuFileSaveQuit.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN));
		menuFileQuit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));

		menuFileSave.setOnAction(e -> save(btnSave));
		menuFileSaveQuit.setOnAction(e -> save(btnSaveQuit));
		menuFileQuit.setOnAction(e -> quit());
		
		menuFile.getItems().addAll(menuFileSave, menuFileSaveQuit, menuFileQuit);
		menuOptions.getItems().addAll(menuOptionsSerial, menuOptionsDB);
		
		menuItems.add(menuFile);
		menuItems.add(menuOptions);
		menuItems.add(menuHelp);

		menuBar = new MenuBar();

		for (Menu m : menuItems) {
			menuBar.getMenus().add(m);
		}
	}
	
	private void setMenuIcon(MenuItem menu, String fileName) {
		Image img = new Image("/assets/" + fileName);
		ImageView imgv = new ImageView();
		StackPane pane = new StackPane();
		imgv.setImage(img);
		imgv.setFitHeight(20);
		imgv.setPreserveRatio(true);
		imgv.setSmooth(true);
		imgv.setCache(true);
		pane.getChildren().add(imgv);
		pane.setPadding(new Insets(0,6,0,0));
		menu.setGraphic(pane);
	}

	private void activatePane(VBox p, MyButton b) {
			
		activeButton.deActivate();
		b.activate();
		activeButton = b;
		p.toFront();
		
		// Setting visible the active pane and invisible the rest
		for (Node n : p.getParent().getChildrenUnmodifiable()) {
			n.setVisible(n.equals(p));
		}
	}

	private void setEventHandlers() {
		btnPatients.setOnMouseClicked(e -> activatePane(patientPane, btnPatients));
		btnReports.setOnMouseClicked(e -> activatePane(reportPane, btnReports));
		btnMaintenance.setOnMouseClicked(e -> activatePane(maintenancePane, btnMaintenance));

		btnSave.setOnMouseClicked(e -> save(btnSave));
		btnSaveQuit.setOnMouseClicked(e -> save(btnSaveQuit));
		btnQuit.setOnMouseClicked(e -> quit());

		primaryStage.setOnCloseRequest(e -> {
			quit();
			// Consuming the event to avoid it bubbling to application if Quit dialog is
			// cancelled.
			e.consume();
		});
	}

	public void setStatusText(String text) {
		statusBar.setText(text);
	}
		
	public String getStatusText() { return statusBar.getText(); }
	
	public void showSaveButtons(Boolean b) {
		btnSave.setVisible(b);
		btnSaveQuit.setVisible(b);
		btnSave.setDisable(!b);
		btnSaveQuit.setDisable(!b);
		menuFileSave.setDisable(!b);
		menuFileSaveQuit.setDisable(!b);
	}
	
	public Stage getStage() { return primaryStage; }
 }
