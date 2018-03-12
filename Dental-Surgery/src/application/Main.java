package application;

import javafx.application.Application;
import javafx.stage.Stage;
import view.LoginScreen;

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

//     TO-DO List:
// [ ] Dialog
// [x] Menu  
// [ ] allow user to remember login on system and bypass login
