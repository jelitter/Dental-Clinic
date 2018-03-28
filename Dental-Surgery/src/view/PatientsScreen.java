package view;

import java.util.Optional;
import application.Main;
import controller.ClinicController;
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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Patient;
import view.elements.MyTitle;

public class PatientsScreen extends Pane {

	private static PatientsScreen instance;
	public static PatientsScreen getInstance() {
		if (instance == null) {
			return new PatientsScreen();
		} else {
//			instance.go();
			return instance;
		}
	}
	private MyTitle title;
	private TableView<Patient> tblPatients;
	private HBox buttons;
	private Button btnSearchPatient, btnUpdatePatient, btnRemovePatient, btnAddPatient, btnClear;
	private Region spacing;
	private HBox personalFields;
	private TextField fldId, fldName, fldLastName, fldEmail, fldAddress, fldPhoneNumber;
	private VBox pane;
	private TableView<Patient> table;
	private ClinicController controller;

//	private ObservableList<Patient> patientsData;
//	ClinicController controller;
//	private FilteredList<Patient> filteredData;

	public PatientsScreen() {
		instance = this;
		go();
	}

//	public ObservableList<Patient> getPatientsData() { return patientsData; }
//	public void setPatientsData(ObservableList<Patient> patientsData) { this.patientsData = patientsData; }


	private void addPatient() {
		String name = fldName.getText();
		String lastName = fldLastName.getText();
		String 	email = fldEmail.getText();
		String address = fldAddress.getText();
		String phone = fldPhoneNumber.getText();
		Patient newPatient = new Patient(name, lastName, email, address, phone);
		controller.addPatient(newPatient);
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

	private TableView<Patient> createTable() {
		
		table = new TableView<Patient>();
		
		TableColumn<Patient, String> idCol = new TableColumn<Patient,String>("Id");
		TableColumn<Patient, String> firstNameCol = new TableColumn<Patient,String>("First Name");
		TableColumn<Patient, String> lastNameCol = new TableColumn<Patient, String>("Last Name");
		TableColumn<Patient, String> emailCol = new TableColumn<Patient, String>("Email");
		TableColumn<Patient, String> addressCol = new TableColumn<Patient, String>("Address");
		TableColumn<Patient, String> phoneCol = new TableColumn<Patient, String>("Phone No.");
		
		idCol.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
        firstNameCol.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        lastNameCol.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        emailCol.setCellValueFactory(cellData -> cellData.getValue().getEmailProperty());
        addressCol.setCellValueFactory(cellData -> cellData.getValue().getAddressProperty());
        phoneCol.setCellValueFactory(cellData -> cellData.getValue().getPhoneNumberProperty());
        table.getColumns().addAll(idCol, firstNameCol, lastNameCol, emailCol, addressCol, phoneCol);
        		
        idCol.prefWidthProperty().set(50);
        
		firstNameCol.prefWidthProperty().bind(table.widthProperty().subtract(idCol.getWidth()).multiply(0.15));
		lastNameCol.prefWidthProperty().bind(table.widthProperty().subtract(idCol.getWidth()).multiply(0.15));
		emailCol.prefWidthProperty().bind(table.widthProperty().subtract(idCol.getWidth()).multiply(0.2));
		addressCol.prefWidthProperty().bind(table.widthProperty().subtract(idCol.getWidth()).multiply(0.3));
		phoneCol.prefWidthProperty().bind(table.widthProperty().subtract(idCol.getWidth()).multiply(0.2).subtract(16));

		idCol.setStyle( "-fx-alignment: CENTER;");
		phoneCol.setStyle( "-fx-alignment: CENTER;");
        
		setTableItems();
//        setupDataFilter();
		
		table.setOnMouseClicked(e ->tableItemSelected());
		table.setOnKeyReleased(e -> {
			KeyCode key = e.getCode();
			if (key.equals(KeyCode.UP) || key.equals(KeyCode.DOWN) || key.equals(KeyCode.PAGE_UP)
					|| key.equals(KeyCode.PAGE_DOWN) || key.equals(KeyCode.HOME) || key.equals(KeyCode.END)) {
				tableItemSelected();
			}
		});
		
		return table;
	}

//	private void setupDataFilter() {
//		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
//		filteredData = new FilteredList<Patient>(patientsData, p -> true);
//		// 2. Set the filter Predicate whenever the filter changes.
//        fldName.textProperty().addListener((observable, oldValue, newValue) -> {
//            filteredData.setPredicate(patient -> {
//                // If filter text is empty, display all persons.
//                if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//
//                // Compare first name and last name of every person with filter text.
//                String lowerCaseFilter = newValue.toLowerCase();
//
//                if (patient.getFirstName().get().toLowerCase().contains(lowerCaseFilter)) {
//                    return true; // Filter matches first name.
//                } else if (patient.getLastName().get().toLowerCase().contains(lowerCaseFilter)) {
//                    return true; // Filter matches last name.
//                }
//                return false; // Does not match.
//            });
//        });
//        
//        // 3. Add sorted (and filtered) data to the table.
//        table.setItems(filteredData);
//	}

	public void setTableItems() {
		table.setItems(controller.patients);
	}
	
	private void tableItemSelected() {
		try {
			Patient pat = table.getSelectionModel().getSelectedItem();
			fldId.setText(pat.getIdProperty().get());
			fldId.setDisable(true);
			fldName.setText(pat.getFirstName());
			fldLastName.setText(pat.getLastName());
			fldEmail.setText(pat.getEmail());
			fldAddress.setText(pat.getAddress());
			fldPhoneNumber.setText(pat.getPhoneNumber());
			
			btnRemovePatient.setDisable(pat == null);
			btnUpdatePatient.setDisable(pat == null);
			updateClearButton();
		} catch (Exception ex) {};
	}

	public VBox getPane() {
		return pane;
	}

	public void go() {
		controller = MainScreen.getInstance().getController();
		pane = new VBox(10);
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
			    controller.setSaved(false);
				System.out.println("Patient removed");

			} else if (result.get() == no) {
				System.out.println("Patient *not* removed");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			btnRemovePatient.setDisable(true);
		}
	}
	
	private void setButtonHandlers() {
		btnUpdatePatient.setOnMouseClicked(e -> updatePatient());
		btnRemovePatient.setOnMouseClicked(e -> removePatient());
		btnAddPatient.setOnMouseClicked(e -> addPatient());
		btnClear.setOnMouseClicked(e -> clearFields());
	}
	
	private void setFieldHandlers() {
		fldId.setOnKeyReleased(e -> updateClearButton());
		fldName.setOnKeyReleased(e -> updateClearButton());
		fldLastName.setOnKeyReleased(e -> updateClearButton());
		fldEmail.setOnKeyReleased(e -> updateClearButton());
		fldAddress.setOnKeyReleased(e -> updateClearButton());
		fldPhoneNumber.setOnKeyReleased(e -> updateClearButton());
	}
 	
	private void setupButtons() {
		btnSearchPatient = new Button("🔎  Search");
		btnRemovePatient = new Button("➖  Remove");
		btnUpdatePatient = new Button("⌨  Update");
		btnAddPatient = new Button("➕  Add");
		btnClear = new Button("❌  Clear");
		
		btnSearchPatient.setPadding(new Insets(10, 20, 10, 20));
		btnRemovePatient.setPadding(new Insets(10, 20, 10, 20));
		btnUpdatePatient.setPadding(new Insets(10, 20, 10, 20));
		btnAddPatient.setPadding(new Insets(10, 20, 10, 20));
		btnClear.setPadding(new Insets(10, 20, 10, 20));
		
		btnSearchPatient.setPrefWidth(USE_COMPUTED_SIZE);
		btnUpdatePatient.setPrefWidth(USE_COMPUTED_SIZE);
		btnRemovePatient.setPrefWidth(USE_COMPUTED_SIZE);
		btnAddPatient.setPrefWidth(USE_COMPUTED_SIZE);
		btnClear.setPrefWidth(USE_COMPUTED_SIZE);

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
		
		fldId.prefWidthProperty().set(60);
				
		fldName.prefWidthProperty().bind(personalFields.widthProperty().subtract(fldId.getWidth()).multiply(0.15));
		fldLastName.prefWidthProperty().bind(personalFields.widthProperty().subtract(fldId.getWidth()).multiply(0.15));
		fldEmail.prefWidthProperty().bind(personalFields.widthProperty().subtract(fldId.getWidth()).multiply(0.2));
		fldAddress.prefWidthProperty().bind(personalFields.widthProperty().subtract(fldId.getWidth()).multiply(0.3));
		fldPhoneNumber.prefWidthProperty().bind(personalFields.widthProperty().subtract(fldId.getWidth()).multiply(0.2).subtract(16));
		
		fldId.setPromptText("Id");
		fldName.setPromptText("First Name *");
		fldLastName.setPromptText("Last Name *");
		fldEmail.setPromptText("Email");
		fldAddress.setPromptText("Address");
		fldPhoneNumber.setPromptText("Phone No.");

		HBox.setHgrow(fldAddress, Priority.ALWAYS);
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
		btnClear.setVisible(!updateClearButton);
		btnSearchPatient.setDisable(updateClearButton);
	}

	private void updatePatient() {
		try {
			Patient selectedPatient = table.getSelectionModel().getSelectedItem();
			
			String name = fldName.getText();
			String lastName = fldLastName.getText();
			String 	email = fldEmail.getText();
			String address = fldAddress.getText();
			String phone = fldPhoneNumber.getText();
			
			selectedPatient.setFirstName(name);
			selectedPatient.setLastName(lastName);
			selectedPatient.setEmail(email);
			selectedPatient.setAddress(address);
			selectedPatient.setPhoneNumber(phone);
			
//			Patient updatedPatient = new Patient(name, lastName, email, address, phone);
//			personData.add(newPatient);
			
			table.getColumns().get(0).setVisible(false);
			table.getColumns().get(0).setVisible(true);
			 
//			controller.addPatient(selectedPatient);
			
			clearFields();
			
			
		} catch (Exception e) {
			System.out.println("Error updating patient - " + e.getMessage());
		}
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


}
