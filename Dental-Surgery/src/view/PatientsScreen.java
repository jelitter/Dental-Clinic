package view;

import java.util.Optional;

import application.Main;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Patient;
import model.Person;
import view.elements.MyTitle;

public class PatientsScreen extends Pane {

	private static PatientsScreen instance;
	private TableView<Patient> tblPatients;
	private Button btnSearchPatient, btnRemovePatient, btnAddPatient, btnClear;
	private TextField fldId, fldName, fldLastName, fldEmail, fldAddress, fldPhoneNumber;
	TableView<Patient> table;

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
		
		fldId = new TextField("");
		fldName = new TextField("");
		fldLastName = new TextField("");
		fldEmail = new TextField("");
		fldAddress = new TextField("");
		fldPhoneNumber = new TextField("");

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
		buttons.prefWidthProperty().bind(pane.widthProperty());
		
		
		btnSearchPatient = new Button("🔎  Search");
		btnRemovePatient = new Button("➖  Remove Patient");
		btnAddPatient = new Button("➕  Add Patient");
		btnClear = new Button("❌  Clear form");
		
		btnSearchPatient.setPadding(new Insets(10, 20, 10, 20));
		btnRemovePatient.setPadding(new Insets(10, 20, 10, 20));
		btnAddPatient.setPadding(new Insets(10, 20, 10, 20));
		btnClear.setPadding(new Insets(10, 20, 10, 20));
		
		btnSearchPatient.setPrefWidth(150);
		btnRemovePatient.setPrefWidth(170);
		btnAddPatient.setPrefWidth(150);
		btnClear.setPrefWidth(150);
		
		btnSearchPatient.setStyle("-fx-base: DEEPSKYBLUE;");
		btnRemovePatient.setStyle("-fx-base: LIGHTCORAL;");
		btnAddPatient.setStyle("-fx-base: LIMEGREEN;");
		btnClear.setStyle("-fx-base: LIGHTGOLDENRODYELLOW;");
		
		Region spacing = new Region();
        HBox.setHgrow(spacing, Priority.ALWAYS);
		
		buttons.setAlignment(Pos.BASELINE_RIGHT);
		buttons.getChildren().addAll(btnClear, spacing, btnRemovePatient, btnSearchPatient, btnAddPatient);
		
		setButtonHandlers();
		setFieldHandlers();
		updateRemovePatientButton();
		updateClearButton();
		
		HBox.setHgrow(fldAddress, Priority.ALWAYS);

		tblPatients = createTable();

		pane.getChildren().addAll(title, tblPatients, personalFields, buttons);
//		pane.setStyle("-fx-background-color: #C4CFDD");
		pane.setStyle("-fx-background-color: #DDEEFF");
	}

	private void setButtonHandlers() {

		btnAddPatient.setOnMouseClicked(e -> {
			String name = fldName.getText();
			String lastName = fldLastName.getText();
			String 	email = fldEmail.getText();
			String address = fldAddress.getText();
			String phone = fldPhoneNumber.getText();
			Patient newPatient = new Patient(name, lastName, email, address, phone);
			
			
			if (name.isEmpty() || lastName.isEmpty()) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Dental Surgery");
				alert.setHeaderText("First and last name are required");
				alert.setContentText("Please provide at least first and last name to add a patient.");
				
				ButtonType ok = new ButtonType("_Ok", ButtonData.FINISH);
				alert.getButtonTypes().setAll(ok);
				
				// Adding icon to Quit dialog
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(Main.class.getResourceAsStream("/assets/icon.png")));
				alert.showAndWait();
			} else {
				table.getItems().add(newPatient);
			}
		});
		
		btnClear.setOnMouseClicked(e -> {
			fldId.clear();
			fldName.clear();
			fldLastName.clear();
			fldEmail.clear();
			fldAddress.clear();
			fldPhoneNumber.clear();
			updateClearButton();
		});
		
		btnRemovePatient.setOnMouseClicked(e -> {
			removePatient();
		});

	}
	
	private void setFieldHandlers() {
		fldName.setOnKeyReleased(e -> {
			updateClearButton();
		});
		fldLastName.setOnKeyReleased(e -> {
			updateClearButton();
		});
		fldEmail.setOnKeyReleased(e -> {
			updateClearButton();
		});
		fldAddress.setOnKeyReleased(e -> {
			updateClearButton();
		});
		fldPhoneNumber.setOnKeyReleased(e -> {
			updateClearButton();
		});
		fldId.setOnKeyReleased(e -> {
			updateClearButton();
		});
	}
	
	private void updateRemovePatientButton() {
		Patient pat = null;
		try {
			pat = table.getSelectionModel().getSelectedItem();
		} catch (Exception e) {
			
		}
		btnRemovePatient.setDisable(pat == null);
	}
 	
	private void removePatient() {
		try {
			Patient selectedPatient = table.getSelectionModel().getSelectedItem();

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Dental Surgery");
			alert.setHeaderText("Remove Patient from data base?");
			alert.setContentText(selectedPatient.toString());
			
			ButtonType yes = new ButtonType("_Yes, remove", ButtonData.FINISH);
			ButtonType no = new ButtonType("_No, keep patient", ButtonData.CANCEL_CLOSE);
			alert.getButtonTypes().setAll(yes, no);
			
			// Adding icon to Quit dialog
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(Main.class.getResourceAsStream("/assets/icon.png")));
			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == yes) {
			    table.getItems().remove(selectedPatient);
				System.out.println("Patient removed");

			} else if (result.get() == no) {
				System.out.println("Patient *not* removed");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			btnRemovePatient.setDisable(true);
		}
	}

	private void updateClearButton() {
		Boolean update = fldId.getText().trim().isEmpty() && fldName.getText().trim().isEmpty()
				&& fldLastName.getText().trim().isEmpty() && fldEmail.getText().trim().isEmpty()
				&& fldAddress.getText().trim().isEmpty() && fldPhoneNumber.getText().trim().isEmpty();
	
		btnClear.setDisable(update);
		btnClear.setVisible(!update);
	}

	private TableView<Patient> createTable() {
		table = new TableView<Patient>();
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

		idCol.setStyle( "-fx-alignment: CENTER;");
		phoneCol.setStyle( "-fx-alignment: CENTER;");
        
		table.getItems().add(testPatient1);
		table.getItems().add(testPatient2);
		table.getItems().add(testPatient3);
		
		
		table.setOnMouseClicked(e -> {
			Patient pat = table.getSelectionModel().getSelectedItem();
			btnRemovePatient.setDisable(pat == null);
		});
		
		return table;
	}
}
