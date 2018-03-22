package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.Patient;
import model.Person;
import view.elements.MyTitle;

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
		pane.getChildren().clear();
		pane.setPadding(new Insets(20));

		// PERSONAL
		HBox personalFields = new HBox(10);
	
		MyTitle title = new MyTitle("Patients");
		
		TextField fldId = new TextField("");
		TextField fldName = new TextField("");
		TextField fldLastName = new TextField("");
		TextField fldEmail = new TextField("");
		TextField fldAddress = new TextField("");
		TextField fldPhoneNumber = new TextField("");

		personalFields.getChildren().addAll(fldId, fldName, fldLastName, fldEmail, fldAddress, fldPhoneNumber);
		
		
		fldId.prefWidthProperty().set(40);
		fldName.prefWidthProperty().bind(personalFields.widthProperty().subtract(40).multiply(0.15));
		fldLastName.prefWidthProperty().bind(personalFields.widthProperty().subtract(40).multiply(0.15));
		fldEmail.prefWidthProperty().bind(personalFields.widthProperty().subtract(40).multiply(0.2));
		fldAddress.prefWidthProperty().bind(personalFields.widthProperty().subtract(40).multiply(0.3));
		fldPhoneNumber.prefWidthProperty().bind(personalFields.widthProperty().subtract(40).multiply(0.2));

		
		fldId.setPromptText("Id");
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
		btnSearchPatient.setPrefWidth(150);
		btnAddPatient.setPrefWidth(150);
		buttons.setAlignment(Pos.BASELINE_RIGHT);
		buttons.getChildren().addAll(btnSearchPatient, btnAddPatient);

		HBox.setHgrow(fldAddress, Priority.ALWAYS);

		tblPatients = createTable();

		pane.getChildren().addAll(title, tblPatients, personalFields, buttons);
//		pane.setStyle("-fx-background-color: #C4CFDD");
		pane.setStyle("-fx-background-color: #DDEEFF");
	}

	private TableView<Patient> createTable() {
		TableView<Patient> table = new TableView<Patient>();
		TableColumn<Patient, String> idCol = new TableColumn<Patient,String>("Id");
		TableColumn<Patient, String> firstNameCol = new TableColumn<Patient,String>("First Name");
		TableColumn<Patient, String> lastNameCol = new TableColumn<Patient, String>("Last Name");
		TableColumn<Patient, String> emailCol = new TableColumn<Patient, String>("Email");
		TableColumn<Patient, String> addressCol = new TableColumn<Patient, String>("Address");
		TableColumn<Patient, String> phoneCol = new TableColumn<Patient, String>("Phone No.");
		
		
	    ObservableList<Person> personData = FXCollections.observableArrayList();
		Patient testPatient1 = new Patient("John", "Smith", "jsmith@gmail.com", "23 Rock Ave.", "555-123-4431");
		Patient testPatient2 = new Patient("Sarah", "Connor", "sconnor@gmail.com", "1 Fate St.", "555-378-0101");
		Patient testPatient3 = new Patient("James", "Jameson", "jjameson@gmail.com", "12 Lake Road.", "555-199-3187");
		
		
		idCol.setCellValueFactory(cellData -> cellData.getValue().getId());
        firstNameCol.setCellValueFactory(cellData -> cellData.getValue().getFirstName());
        lastNameCol.setCellValueFactory(cellData -> cellData.getValue().getLastName());
        emailCol.setCellValueFactory(cellData -> cellData.getValue().getEmail());
        addressCol.setCellValueFactory(cellData -> cellData.getValue().getAddress());
        phoneCol.setCellValueFactory(cellData -> cellData.getValue().getPhoneNumber());
        table.getColumns().addAll(idCol, firstNameCol, lastNameCol, emailCol, addressCol, phoneCol);
        
        personData.add(testPatient1);
        personData.add(testPatient2);
        personData.add(testPatient3);
     		
		
        idCol.prefWidthProperty().set(40);
		firstNameCol.prefWidthProperty().bind(table.widthProperty().subtract(40).multiply(0.15));
		lastNameCol.prefWidthProperty().bind(table.widthProperty().subtract(40).multiply(0.15));
		emailCol.prefWidthProperty().bind(table.widthProperty().subtract(40).multiply(0.2));
		addressCol.prefWidthProperty().bind(table.widthProperty().subtract(40).multiply(0.3));
		phoneCol.prefWidthProperty().bind(table.widthProperty().subtract(40).multiply(0.195));

        
		table.getItems().add(testPatient1);
		table.getItems().add(testPatient2);
		table.getItems().add(testPatient3);
		return table;
	}
}
