package view;

import controller.UserController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Dentist;
import view.elements.MyButton;
import view.elements.MyPasswordTextField;
import view.elements.MyTextField;

public class LoginScreen {

	private static final int WIDTH = 500;
	private static final int HEIGHT = 330;

	private UserController uc;
	private static LoginScreen instance;
	private Stage primaryStage;
	private MyTextField fldUserName;
	private MyPasswordTextField fldPassword;
	private MyButton btnLogin, btnExit;
	private CheckBox remember;
	private Label status;

	public LoginScreen() {
		instance = this;
		go();
	}

	public static LoginScreen getInstance() {
		if (instance == null)
			return new LoginScreen();
		return instance;
	}

	private void go() {

		primaryStage = new Stage();
		primaryStage.setMinWidth(WIDTH);
		primaryStage.setMinHeight(HEIGHT);
		primaryStage.getIcons().add(new Image("/assets/icon.png" ));


		uc = new UserController();
		
		
		double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
		double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

		primaryStage.setX(screenWidth / 2 - WIDTH / 2);
		primaryStage.setY(screenHeight / 2 - HEIGHT / 2);
		
		primaryStage.setResizable(false);

		VBox root = new VBox(10);
		root.setPadding(new Insets(20));
		root.setStyle(
				"-fx-background-image: url(" + "'/assets/background.png'" + "); -fx-background-size: cover;");
		Scene scene = new Scene(root, WIDTH, HEIGHT);

		fldUserName = new MyTextField();
		fldPassword = new MyPasswordTextField();

		btnLogin = new MyButton("Login");
		btnExit = new MyButton("Exit", "Warning");
		
		btnLogin.setIcon("login.png");
		btnExit.setIcon("quit.png");

		fldUserName.setPromptText("User name");
		fldPassword.setPromptText("Password");

		HBox loginOptions = new HBox(10);
		remember = new CheckBox("Remember me");
		Pane separator = new Pane();
		
		VBox dataSource = new VBox(10);
		ToggleGroup radioGroup = new ToggleGroup();
		RadioButton dataSource1 = new RadioButton("Serial File");
		RadioButton dataSource2 = new RadioButton("Database (soon!)");
		dataSource1.setSelected(true);
		dataSource2.setDisable(true);
		dataSource1.setToggleGroup(radioGroup);
		dataSource2.setToggleGroup(radioGroup);
		dataSource.getChildren().addAll(dataSource1, dataSource2);
		dataSource.setPrefWidth(200);
		loginOptions.setAlignment(Pos.TOP_CENTER);
		loginOptions.setPadding(new Insets(10,0,0,0));
		HBox.setHgrow(separator, Priority.ALWAYS);
		loginOptions.getChildren().addAll(remember, separator, dataSource);
		
		Dentist saved = uc.loadLogin();
		if (saved != null) {
			if (!saved.getUsername().isEmpty() && !saved.getPassword().isEmpty()) {
				fldUserName.setText(saved.getUsername());
				fldPassword.setText(saved.getPassword());
				remember.setSelected(true);
			}
		}
		btnLogin.setDisable(!isLoginAllowed());
		
		HBox myButtons = new HBox(20);
		
		status = new Label("");
		setStatus("Please login", Color.GREEN);
		
		Region spacing = new Region();
        HBox.setHgrow(spacing, Priority.ALWAYS);
		
		myButtons.setAlignment(Pos.CENTER);
		myButtons.setPadding(new Insets(20, 0, 0, 0));
		myButtons.getChildren().addAll(btnLogin, spacing, btnExit);

		root.getChildren().addAll(status, fldUserName, fldPassword, loginOptions, myButtons);
		root.setAlignment(Pos.CENTER);
		remember.setAlignment(Pos.BOTTOM_RIGHT);

		primaryStage.setTitle("Dental Clinic");
		primaryStage.setScene(scene);
		primaryStage.show();

		fldUserName.setOnKeyReleased(e -> updateButtons(e));
		fldPassword.setOnKeyReleased(e -> updateButtons(e));

		btnLogin.setOnMouseClicked(e -> {
			if (isLoginAllowed()) {
				launchLogin();
			} 
		});
		
		remember.setOnAction(e -> {
			if (!remember.isSelected()) {
				if (!saved.getUsername().isEmpty() && !saved.getPassword().isEmpty()) {
					uc.saveLogin("", "");
				}
			}
		});

		btnExit.setOnMouseClicked(e -> {
			Platform.exit();
		});
		

	}

	public void end() {
		this.getStage().close();
	}

	private boolean isLoginAllowed() {
		String user, pass;
		user = fldUserName.getText();
		pass = fldPassword.getText();
		return (!user.isEmpty() && !pass.isEmpty());
	}

	private void updateButtons(KeyEvent e) {

		if (e.getCode() == KeyCode.ENTER) {
			if (isLoginAllowed()) {
				launchLogin();
			}
		} else {
			btnLogin.setDisable(!isLoginAllowed());
			
			if (!isLoginAllowed()) {
				setStatus("Enter both username and password", Color.ORANGE);
			} else {
				setStatus("Please login", Color.GREEN);
			}
		}
	}

	private void launchLogin() {
		String user, pass;
		user = fldUserName.getText();
		pass = fldPassword.getText();
		if (uc.validateLogin(user, pass)) {
			if (remember.isSelected()) {
				uc.saveLogin(user,pass);
			}
			this.end();
			LoadingScreen.getInstance();
		} else {
			setStatus("Invalid username or password", Color.CORAL);
		}
	}

	private Stage getStage() {
		return this.primaryStage;
	}
	
	private void setStatus(String text, Color color) {
		DropShadow shadow = new DropShadow();
		shadow.setOffsetY(0.0f);
		shadow.setOffsetX(0.0f);
		shadow.setColor(color.brighter());
		
		status.setEffect(shadow);
		status.setTextFill(color);
		status.setPadding(new Insets(10,20,10,20));
		status.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		status.setText(text);
	}
}
