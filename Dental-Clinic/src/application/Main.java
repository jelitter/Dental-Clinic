package application;

import javafx.application.Application;
import javafx.stage.Stage;
import view.LoadingScreen;
import view.LoginScreen;
import view.MainScreen;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			LoginScreen.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}