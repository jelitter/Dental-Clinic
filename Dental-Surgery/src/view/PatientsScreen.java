package view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Optional;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javafx.stage.Stage;
import model.Patient;
import view.elements.MyTitle;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PatientsScreen extends Pane {

	private static PatientsScreen instance;
	private MyTitle title;
	private TableView<Patient> tblPatients;
	private HBox buttons;
	private Button btnSearchPatient, btnUpdatePatient, btnRemovePatient, btnAddPatient, btnClear;
	private Region spacing;
	private HBox personalFields;
	private TextField fldId, fldName, fldLastName, fldEmail, fldAddress, fldPhoneNumber;
	private VBox pane;
	private TableView<Patient> table;
	private ObservableList<Patient> personData;

	public PatientsScreen() {
		instance = this;
		go();
	}

	public static PatientsScreen getInstance() {
		if (instance == null) {
			return new PatientsScreen();
		} else {
//			instance.go();
			return instance;
		}
	}

	public void go() {
		
//		pane = MainScreen.getInstance().getLayout();
		pane = new VBox(10);
		System.out.println("Patient pane created");
		pane.getChildren().clear();
		pane.setPadding(new Insets(20));

		// Title
		title = new MyTitle("Patients");
		
		//Table
		tblPatients = createTable();
		
		setupFields();
		setupButtons();
		setButtonHandlers();
		setFieldHandlers();
		updateRemovePatientButton();
		updateClearButton();
		
		pane.getChildren().addAll(title, tblPatients, personalFields, buttons);
		VBox.setVgrow(tblPatients, Priority.ALWAYS);
		pane.setStyle("-fx-background-color: #DDEEFF");
	}

	private void setupFields() {
		// Fields
		personalFields = new HBox(10);

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
		fldName.setPromptText("First Name *");
		fldLastName.setPromptText("Last Name *");
		fldEmail.setPromptText("Email");
		fldAddress.setPromptText("Address");
		fldPhoneNumber.setPromptText("Phone No.");

		HBox.setHgrow(fldAddress, Priority.ALWAYS);
	}

	private void setupButtons() {
		btnSearchPatient = new Button("🔎  Search");
		btnRemovePatient = new Button("➖  Remove");
		btnUpdatePatient = new Button("⌨  Update");
		btnAddPatient = new Button("➕  Add");
		btnClear = new Button("❌  Clear form");
		
		btnSearchPatient.setPadding(new Insets(10, 20, 10, 20));
		btnRemovePatient.setPadding(new Insets(10, 20, 10, 20));
		btnUpdatePatient.setPadding(new Insets(10, 20, 10, 20));
		btnAddPatient.setPadding(new Insets(10, 20, 10, 20));
		btnClear.setPadding(new Insets(10, 20, 10, 20));
		
		btnSearchPatient.setPrefWidth(100);
		btnUpdatePatient.setPrefWidth(100);
		btnRemovePatient.setPrefWidth(110);
		btnAddPatient.setPrefWidth(100);
		btnClear.setPrefWidth(100);

		btnSearchPatient.setStyle("-fx-base: DEEPSKYBLUE;");
		btnRemovePatient.setStyle("-fx-base: LIGHTCORAL;");
		btnUpdatePatient.setStyle("-fx-base: LIGHTGREEN;");
		btnAddPatient.setStyle("-fx-base: LIMEGREEN;");
		btnClear.setStyle("-fx-base: LIGHTGOLDENRODYELLOW;");
		
		spacing = new Region();
        HBox.setHgrow(spacing, Priority.ALWAYS);
        
        buttons = new HBox(10);
		buttons.prefWidthProperty().bind(pane.widthProperty());
		
		buttons.setAlignment(Pos.BASELINE_RIGHT);
		buttons.getChildren().addAll(btnClear, spacing, btnRemovePatient, btnUpdatePatient, btnAddPatient, btnSearchPatient);
	}

	private void setButtonHandlers() {
		btnUpdatePatient.setOnMouseClicked(e -> updatePatient());
		btnRemovePatient.setOnMouseClicked(e -> removePatient());
		btnAddPatient.setOnMouseClicked(e -> addPatient());
		btnClear.setOnMouseClicked(e -> clearFields());
	}

	private void addPatient() {
		String name = fldName.getText();
		String lastName = fldLastName.getText();
		String 	email = fldEmail.getText();
		String address = fldAddress.getText();
		String phone = fldPhoneNumber.getText();
		Patient newPatient = new Patient(name, lastName, email, address, phone);
		personData.add(newPatient);
		clearFields();
	}

	private void clearFields() {
		fldId.clear();
		fldName.clear();
		fldLastName.clear();
		fldEmail.clear();
		fldAddress.clear();
		fldPhoneNumber.clear();
		updateClearButton();
	}
	
	private void setFieldHandlers() {
		fldId.setOnKeyReleased(e -> updateClearButton());
		fldName.setOnKeyReleased(e -> updateClearButton());
		fldLastName.setOnKeyReleased(e -> updateClearButton());
		fldEmail.setOnKeyReleased(e -> updateClearButton());
		fldAddress.setOnKeyReleased(e -> updateClearButton());
		fldPhoneNumber.setOnKeyReleased(e -> updateClearButton());
	}
	
	private void updateRemovePatientButton() {
		Patient pat = null;
		try {
			pat = table.getSelectionModel().getSelectedItem();
		} catch (Exception e) {
			
		}
		btnRemovePatient.setDisable(pat == null);
		btnUpdatePatient.setDisable(pat == null);
	}
	
	private void updatePatient() {
		try {
			Patient selectedPatient = table.getSelectionModel().getSelectedItem();
		} catch (Exception e) {
			
		}
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
			

			// Centering dialog
			Double alertX = pane.getScene().getWindow().getX() + pane.getScene().getWindow().getWidth()/2 - 90;
			Double alertY = pane.getScene().getWindow().getY() + pane.getScene().getWindow().getHeight()/2 - 112;
			alert.setX(alertX);
			alert.setY(alertY);
		
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
		
		Boolean updateIdField = fldName.getText().trim().isEmpty()
				&& fldLastName.getText().trim().isEmpty() && fldEmail.getText().trim().isEmpty()
				&& fldAddress.getText().trim().isEmpty() && fldPhoneNumber.getText().trim().isEmpty();
		fldId.setDisable(!updateIdField);
		
		Boolean updateAddButton = fldName.getText().trim().isEmpty() || fldLastName.getText().trim().isEmpty();
		btnAddPatient.setDisable(updateAddButton);
		
		Boolean updateClearButton = fldId.getText().trim().isEmpty() && updateIdField;
	
		btnClear.setDisable(updateClearButton);
		btnSearchPatient.setDisable(updateClearButton);
		btnClear.setVisible(!updateClearButton);
	}

	
	private TableView<Patient> createTable() {
		table = new TableView<Patient>();
		TableColumn<Patient, String> idCol = new TableColumn<Patient,String>("Id");
		TableColumn<Patient, String> firstNameCol = new TableColumn<Patient,String>("First Name");
		TableColumn<Patient, String> lastNameCol = new TableColumn<Patient, String>("Last Name");
		TableColumn<Patient, String> emailCol = new TableColumn<Patient, String>("Email");
		TableColumn<Patient, String> addressCol = new TableColumn<Patient, String>("Address");
		TableColumn<Patient, String> phoneCol = new TableColumn<Patient, String>("Phone No.");
		
		idCol.setCellValueFactory(cellData -> cellData.getValue().getId());
        firstNameCol.setCellValueFactory(cellData -> cellData.getValue().getFirstName());
        lastNameCol.setCellValueFactory(cellData -> cellData.getValue().getLastName());
        emailCol.setCellValueFactory(cellData -> cellData.getValue().getEmail());
        addressCol.setCellValueFactory(cellData -> cellData.getValue().getAddress());
        phoneCol.setCellValueFactory(cellData -> cellData.getValue().getPhoneNumber());
        table.getColumns().addAll(idCol, firstNameCol, lastNameCol, emailCol, addressCol, phoneCol);
        		
        idCol.prefWidthProperty().set(40);
		firstNameCol.prefWidthProperty().bind(table.widthProperty().subtract(40).multiply(0.15));
		lastNameCol.prefWidthProperty().bind(table.widthProperty().subtract(40).multiply(0.15));
		emailCol.prefWidthProperty().bind(table.widthProperty().subtract(40).multiply(0.2));
		addressCol.prefWidthProperty().bind(table.widthProperty().subtract(40).multiply(0.3));
		phoneCol.prefWidthProperty().bind(table.widthProperty().subtract(40).multiply(0.2).subtract(2));

		idCol.setStyle( "-fx-alignment: CENTER;");
		phoneCol.setStyle( "-fx-alignment: CENTER;");
        
		personData = FXCollections.observableArrayList();
		
		loadDataToTable();
		
		table.setOnMouseClicked(e -> {
			try {
				Patient pat = table.getSelectionModel().getSelectedItem();
				fldId.setText(pat.getId().get());
				fldId.setDisable(true);
				fldName.setText(pat.getFirstName().get());
				fldLastName.setText(pat.getLastName().get());
				fldEmail.setText(pat.getEmail().get());
				fldAddress.setText(pat.getAddress().get());
				fldPhoneNumber.setText(pat.getPhoneNumber().get());
				
				btnRemovePatient.setDisable(pat == null);
				btnUpdatePatient.setDisable(pat == null);
			} catch (Exception ex) {};
		});
		
		return table;
	}

	private void loadDataToTable() {

		String CsvFile = "src/data/patients.csv";
		String FieldDelimiter = ",";

		BufferedReader br;

		try {
			br = new BufferedReader(new FileReader(CsvFile));

			String line;
			while ((line = br.readLine()) != null) {
				String[] fields = line.split(FieldDelimiter, -1);
//				Patient record = new Patient(fields[0], fields[1], fields[2], fields[3], fields[4]);
				Patient record = new Patient(fields[3], fields[4], fields[2], fields[0], fields[1]);
				personData.add(record);
			}

		} catch (FileNotFoundException ex) {
			System.out.println("Error - File not found - " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("Error - IO exception - " + ex.getMessage());
		}

		// Patient testPatient1 = new Patient("John", "Smith", "jsmith@gmail.com", "23
		// Rock Ave.", "555-123-4431");
		// Patient testPatient2 = new Patient("Sarah", "Connor", "sconnor@gmail.com", "1
		// Fate St.", "555-378-0101");
		// Patient testPatient3 = new Patient("James", "Jameson", "jjameson@gmail.com",
		// "12 Lake Road.", "555-199-3187");
		// personData.addAll(testPatient1, testPatient2, testPatient3);
		table.setItems(personData);
	}

	public VBox getPane() {
		return pane;
	}
}
