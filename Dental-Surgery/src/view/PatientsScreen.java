package view;


import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class PatientsScreen extends Pane {

	private static PatientsScreen instance;

	public PatientsScreen() {
		instance = this;
		go();
	}

	public static PatientsScreen getInstance() {
		if (instance == null) {
			return new PatientsScreen();
		} else {
			instance.go();			
			return instance;
		}
	}

	public void go() {
		VBox pane = MainScreen.getInstance().getLayout();
			
		// PERSONAL
		VBox personalDetails = new VBox(10);
		Label title = new Label("Personal details");
		title.setFont(new Font("Arial", 18));
		title.setWrapText(true);
		Label lblName = new Label("First Name");
		TextField fldName = new TextField("");
		Label lblSurname = new Label("Last Name");
		TextField fldSurname = new TextField("");
		personalDetails.getChildren().addAll(title, lblName, fldName, lblSurname, fldSurname);
		
		// CRUD
		HBox crud = new HBox(10);
		HBox.setHgrow(crud, Priority.ALWAYS);
		crud.setAlignment(Pos.CENTER);
		Label crudCreate = new Label("Add patient");
		Label crudUpdate = new Label("Modify patient");
		Label crudDelete = new Label("Delete patient");
		crudCreate.setFont(new Font("Arial", 18));
		crudUpdate.setFont(new Font("Arial", 18));
		crudDelete.setFont(new Font("Arial", 18));
		crud.getChildren().addAll(crudCreate, crudUpdate, crudDelete);

		pane.getChildren().addAll(personalDetails, crud);
	}
}
