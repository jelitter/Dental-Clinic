package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.elements.MyButton;

public class MainScreen {

	private Stage primaryStage;
	private static MainScreen instance;

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

		BorderPane root = new BorderPane();
		root.setPadding(new Insets(40));

		double screenWidth  = Screen.getPrimary().getVisualBounds().getWidth();
		double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

		Scene scene = new Scene(root,4*screenWidth/5,4*screenHeight/5);
		primaryStage.setX(screenWidth/10);
		primaryStage.setY(screenHeight/10);
		Text title = new Text("Dental Surgery Management");

		title.setStyle("-fx-font: 22 Georgia; -fx-base: #dd8800;"); 

		VBox options = new VBox(10);

		MyButton b1 = new MyButton("Patients");
		MyButton b2 = new MyButton("Procedures");
		MyButton b3 = new MyButton("Invoices");

		options.getChildren().addAll(b1,b2,b3);

		BorderPane.setAlignment(title, Pos.CENTER);
		root.setTop(title);
		root.setLeft(options);		

		primaryStage.setTitle("Dental Surgery Management");
		primaryStage.setScene(scene);
//		primaryStage.show();
		show();

	}
	
	public void show() {
		primaryStage.show();
	}
}
