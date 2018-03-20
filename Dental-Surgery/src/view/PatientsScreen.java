package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class PatientsScreen extends Pane {

	private static PatientsScreen instance;
	private TableView tblPatients;

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
		pane.setPadding(new Insets(20));

		// PERSONAL
		HBox personalFields = new HBox(10);

		Label title = new Label("Personal details");
		title.setFont(new Font("Arial", 18));
		title.setWrapText(true);
		TextField fldName = new TextField("");
		TextField fldLastName = new TextField("");
		TextField fldEmail = new TextField("");
		TextField fldAddress = new TextField("");
		TextField fldPhoneNumber = new TextField("");

		personalFields.getChildren().addAll(fldName, fldLastName, fldEmail, fldAddress, fldPhoneNumber);

		fldName.setPrefWidth(120);
		fldLastName.setPrefWidth(120);
		fldEmail.setPrefWidth(180);
		fldAddress.setPrefWidth(220);
		fldPhoneNumber.setPrefWidth(120);

		fldName.setPromptText("First Name");
		fldLastName.setPromptText("Last Name");
		fldEmail.setPromptText("Email");
		fldAddress.setPromptText("Address");
		fldPhoneNumber.setPromptText("Phone No.");

		HBox buttons = new HBox(10);
		Button btnSearchPatient = new Button("Search");
		Button btnAddPatient = new Button("Add Patient");
		btnSearchPatient.setPadding(new Insets(10, 20, 10, 20));
		btnAddPatient.setPadding(new Insets(10, 20, 10, 20));
		btnAddPatient.setPrefWidth(120);
		buttons.setAlignment(Pos.BASELINE_RIGHT);
		buttons.getChildren().addAll(btnSearchPatient, btnAddPatient);

		HBox.setHgrow(fldAddress, Priority.ALWAYS);

		tblPatients = createTable();

		pane.getChildren().addAll(title, tblPatients, personalFields, buttons);
		pane.setStyle("-fx-background-color: #C4CFDD");

	}

	private TableView createTable() {
		TableView table = new TableView();
		TableColumn firstNameCol = new TableColumn("First Name");
		TableColumn lastNameCol = new TableColumn("Last Name");
		TableColumn emailCol = new TableColumn("Email");
		TableColumn addressCol = new TableColumn("Address");
		TableColumn phoneCol = new TableColumn("Phone No.");

		table.getColumns().addAll(firstNameCol, lastNameCol, addressCol, phoneCol);
		return table;
	}

}
