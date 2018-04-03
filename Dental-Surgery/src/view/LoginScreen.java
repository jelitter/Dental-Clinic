package view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.elements.MyButton;
import view.elements.MyPasswordTextField;
import view.elements.MyTextField;

public class LoginScreen {

	private static final int WIDTH = 500;
	private static final int HEIGHT = 300;

	private static LoginScreen instance;
	private Stage primaryStage;
	MyTextField fldUserName;
	MyPasswordTextField fldPassword;
	MyButton btnLogin, btnExit;

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


		double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
		double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

		primaryStage.setX(screenWidth / 2 - WIDTH / 2);
		primaryStage.setY(screenHeight / 2 - HEIGHT / 2);
		
		primaryStage.setResizable(false);

		VBox root = new VBox(10);
		root.setPadding(new Insets(20));
		Scene scene = new Scene(root, WIDTH, HEIGHT);

		fldUserName = new MyTextField();
		fldPassword = new MyPasswordTextField();
		Label lblTitle = new Label("Please login");

		btnLogin = new MyButton("Login");
		btnLogin.setDisable(true);
		btnExit = new MyButton("Exit", "Warning");
		
		btnLogin.setIcon("login.png");
		btnExit.setIcon("quit.png");

		fldUserName.setPromptText("User name");
		fldPassword.setPromptText("Password");

		HBox myButtons = new HBox(20);
		
		Region spacing = new Region();
        HBox.setHgrow(spacing, Priority.ALWAYS);
		
		myButtons.setAlignment(Pos.CENTER);
		myButtons.setPadding(new Insets(40, 0, 0, 0));
		myButtons.getChildren().addAll(btnLogin, spacing, btnExit);

		root.getChildren().addAll(lblTitle, fldUserName, fldPassword, myButtons);
		root.setAlignment(Pos.CENTER);

		primaryStage.setTitle("Dental Surgery Management");
		primaryStage.setScene(scene);
		primaryStage.show();

		fldUserName.setOnKeyReleased(e -> updateButtons(e));
		fldPassword.setOnKeyReleased(e -> updateButtons(e));

		btnLogin.setOnMouseClicked(e -> {
			if (isLoginAllowed()) {
				launchLogin();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("Please enter both username and password");
				alert.setHeaderText("Could not login");
				alert.setTitle("Login error");
				alert.show();
				fldUserName.setText("");
				fldPassword.setText("");
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
		String user, pwd;

		user = fldUserName.getText();
		pwd = fldPassword.getText();

		// System.out.println("User: " + user);
		// System.out.println("Pass: " + pwd);

		return (!user.isEmpty() && !pwd.isEmpty());
	}

	private void updateButtons(KeyEvent e) {
		// System.out.println("Key pressed: " + e.getCode());

		if (e.getCode() == KeyCode.ENTER) {
			if (isLoginAllowed())
				launchLogin();
		} else {
			btnLogin.setDisable(!isLoginAllowed());
		}
	}

	private void launchLogin() {
		this.end();
		LoadingScreen.getInstance();
	}

	private Stage getStage() {
		return this.primaryStage;
	}
}
