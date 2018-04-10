package view;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import application.Main;
import controller.ClinicController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Patient;
import view.elements.MyTitle;

public class PatientsScreen extends Pane {

	private static PatientsScreen instance;
	private ClinicController controller;
	private TableView<Patient> tblPatients;
	
	private MyTitle title;
	private HBox buttons;
	private Button btnSearchPatient, btnEditPatient, btnRemovePatient, btnAddPatient, btnClear;
	private Region spacing;
	private VBox personalFields;
	private TextField fldId, fldName, fldLastName, fldEmail, fldAddress, fldPhoneNumber;
	private VBox pane;

	public static PatientsScreen getInstance() {
		if (instance == null) {
			return new PatientsScreen();
		} else {
			return instance;
		}
	}
	
	public PatientsScreen() {
		instance = this;
		go();
	}
	
	public void go() {
		controller = MainScreen.getInstance().getController();
		pane = new VBox(10);
		pane.getChildren().clear();
		pane.setPadding(new Insets(20));

		// Title
		title = new MyTitle("Patients");
		

		setupFields();

		//Table
		createPatientsTable();
		
		setupButtons();
		setButtonHandlers();
		setFieldHandlers();
		updateRemovePatientButton();
		updateClearButton();
		
		VBox.setVgrow(tblPatients, Priority.ALWAYS);
		pane.getChildren().addAll(title, tblPatients, personalFields, buttons);
	}

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
		btnEditPatient.setDisable(true);
	}

	private void createPatientsTable() {
		
		tblPatients = new TableView<Patient>();
		
		TableColumn<Patient, Number> idCol = new TableColumn<Patient,Number>("Id");
		TableColumn<Patient, String> firstNameCol = new TableColumn<Patient,String>("First Name");
		TableColumn<Patient, String> lastNameCol = new TableColumn<Patient, String>("Last Name");
		TableColumn<Patient, String> emailCol = new TableColumn<Patient, String>("Email");
		TableColumn<Patient, String> addressCol = new TableColumn<Patient, String>("Address");
		TableColumn<Patient, String> phoneCol = new TableColumn<Patient, String>("Phone No.");
		
		idCol.setCellValueFactory(cellData -> cellData.getValue().IdProperty());
        firstNameCol.setCellValueFactory(cellData -> cellData.getValue().FirstNameProperty());
        lastNameCol.setCellValueFactory(cellData -> cellData.getValue().LastNameProperty());
        emailCol.setCellValueFactory(cellData -> cellData.getValue().EmailProperty());
        addressCol.setCellValueFactory(cellData -> cellData.getValue().AddressProperty());
        phoneCol.setCellValueFactory(cellData -> cellData.getValue().PhoneProperty());
        
        
        tblPatients.getColumns().addAll(Arrays.asList(idCol, firstNameCol, lastNameCol, emailCol, addressCol, phoneCol));

        // Hiding horizontal scroll bar
        tblPatients.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        
        idCol.maxWidthProperty().set(50);
        idCol.minWidthProperty().set(50);
        idCol.prefWidthProperty().set(50);
        
        phoneCol.maxWidthProperty().set(130);
        phoneCol.minWidthProperty().set(130);
        phoneCol.prefWidthProperty().set(130);
        
		idCol.setStyle( "-fx-alignment: CENTER;");
		phoneCol.setStyle( "-fx-alignment: CENTER;");
        
        setupDataFilter();
		
		
		tblPatients.setOnMouseClicked(e -> {
			tableItemSelected();
			if (e.getClickCount() == 2) {
				editPatient();
			}
		});
		
		tblPatients.setOnKeyReleased(e -> {
			KeyCode key = e.getCode();
			if (key.equals(KeyCode.UP) || key.equals(KeyCode.DOWN) || key.equals(KeyCode.PAGE_UP)
					|| key.equals(KeyCode.PAGE_DOWN) || key.equals(KeyCode.HOME) || key.equals(KeyCode.END)) {
				tableItemSelected();
			} else if (key.equals(KeyCode.ENTER)) {
				editPatient();
			}
		});
	}

	private void setupDataFilter() {

		ObjectProperty<Predicate<Patient>> firstNameFilter = new SimpleObjectProperty<>();
        ObjectProperty<Predicate<Patient>> lastNameFilter = new SimpleObjectProperty<>();
        ObjectProperty<Predicate<Patient>> emailFilter = new SimpleObjectProperty<>();
        ObjectProperty<Predicate<Patient>> addressFilter = new SimpleObjectProperty<>();
        ObjectProperty<Predicate<Patient>> phoneFilter = new SimpleObjectProperty<>();

		firstNameFilter.bind(Bindings.createObjectBinding(
				() -> patient -> patient.getFirstName().toLowerCase().contains(fldName.getText().toLowerCase()),
				fldName.textProperty()));

		lastNameFilter.bind(Bindings.createObjectBinding(
				() -> patient -> patient.getLastName().toLowerCase().contains(fldLastName.getText().toLowerCase()),
				fldLastName.textProperty()));
		
		emailFilter.bind(Bindings.createObjectBinding(
				() -> patient -> patient.getEmail().toLowerCase().contains(fldEmail.getText().toLowerCase()),
				fldEmail.textProperty()));
		
		addressFilter.bind(Bindings.createObjectBinding(
				() -> patient -> patient.getAddress().toLowerCase().contains(fldAddress.getText().toLowerCase()),
				fldAddress.textProperty()));
		
		phoneFilter.bind(Bindings.createObjectBinding(
				() -> patient -> patient.getPhoneNumber().toLowerCase().contains(fldPhoneNumber.getText().toLowerCase()),
				fldPhoneNumber.textProperty()));

		// As FilteredLists are unmodifiable, cannot be sorted directly.
		// https://stackoverflow.com/questions/17958337/javafx-tableview-with-filteredlist-jdk-8-does-not-sort-by-column
		// We wrap the filtered list in a sorted list, so the sorting when clicking headers works again.
        FilteredList<Patient> filteredItems = new FilteredList<Patient>(FXCollections.observableList(controller.patients));
        SortedList<Patient> filteredSortedItems = new SortedList<>(filteredItems);
        tblPatients.setItems(filteredSortedItems);
        filteredSortedItems.comparatorProperty().bind(tblPatients.comparatorProperty());

        
		filteredItems.predicateProperty()
				.bind(Bindings.createObjectBinding(
						() -> firstNameFilter.get().and(lastNameFilter.get()).and(emailFilter.get())
								.and(addressFilter.get()).and(phoneFilter.get()),
						firstNameFilter, lastNameFilter, emailFilter, addressFilter, phoneFilter));

	}

	public void setPatientsTableItems() {
		tblPatients.setItems(controller.patients);
	}
	
	private void tableItemSelected() {
		Patient pat = tblPatients.getSelectionModel().getSelectedItem();

		if (pat != null) {
			MainScreen.getInstance().setStatusText("Double click, <ENTER> or Edit button to edit Patient Id " + pat.getId());
		}
		
		btnRemovePatient.setDisable(pat == null);
		btnEditPatient.setDisable(pat == null);
		updateClearButton();
	}


	public VBox getPane() {
		return pane;
	}

	private void removePatient() {
		try {
			Patient selectedPatient = tblPatients.getSelectionModel().getSelectedItem();

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
//				tblPatients.getItems().remove(selectedPatient);
				
				controller.patients.removeAll(selectedPatient);
				setPatientsTableItems();
			    controller.unsavedChanges();
//			    refreshTable();

			} 
		} catch (Exception e) {
			System.out.println(e);
			btnRemovePatient.setDisable(true);
		}
	}
	
	private void setButtonHandlers() {
		btnEditPatient.setOnMouseClicked(e -> editPatient() );
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
		btnEditPatient = new Button("⌨  Edit");
		btnAddPatient = new Button("➕  Add");
		btnClear = new Button("❌  Clear");
		
		btnSearchPatient.setPadding(new Insets(10, 20, 10, 20));
		btnRemovePatient.setPadding(new Insets(10, 20, 10, 20));
		btnEditPatient.setPadding(new Insets(10, 20, 10, 20));
		btnAddPatient.setPadding(new Insets(10, 20, 10, 20));
		btnClear.setPadding(new Insets(10, 20, 10, 20));
		
		btnSearchPatient.setMinWidth(120);
		btnEditPatient.setMinWidth(120);
		btnRemovePatient.setMinWidth(120);
		btnAddPatient.setMinWidth(120);
		btnClear.setMinWidth(120);
		
		btnSearchPatient.setPrefWidth(USE_COMPUTED_SIZE);
		btnEditPatient.setPrefWidth(USE_COMPUTED_SIZE);
		btnRemovePatient.setPrefWidth(USE_COMPUTED_SIZE);
		btnAddPatient.setPrefWidth(USE_COMPUTED_SIZE);
		btnClear.setPrefWidth(USE_COMPUTED_SIZE);

		btnSearchPatient.setStyle("-fx-base: DEEPSKYBLUE;");
		btnRemovePatient.setStyle("-fx-base: LIGHTCORAL;");
		btnEditPatient.setStyle("-fx-base: LIGHTGREEN;");
		btnAddPatient.setStyle("-fx-base: LIMEGREEN;");
		btnClear.setStyle("-fx-base: LIGHTGOLDENRODYELLOW;");
		
		spacing = new Region();
        HBox.setHgrow(spacing, Priority.ALWAYS);
        
        buttons = new HBox(10);
		buttons.prefWidthProperty().bind(pane.widthProperty());
		
		buttons.setAlignment(Pos.BASELINE_RIGHT);
		buttons.getChildren().addAll(btnClear, spacing, btnRemovePatient, btnEditPatient, btnAddPatient, btnSearchPatient);
	}

	private void setupFields() {
		// Fields
		personalFields = new VBox(10);
		HBox fields = new HBox(10);
		Text fieldsTitle = new Text("Filter or Add patient (* required fields)");
		fieldsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));

		fldId = new TextField("");
		fldName = new TextField("");
		fldLastName = new TextField("");
		fldEmail = new TextField("");
		fldAddress = new TextField("");
		fldPhoneNumber = new TextField("");
		fields.getChildren().addAll(fldId, fldName, fldLastName, fldEmail, fldAddress, fldPhoneNumber);
		
		personalFields.getChildren().addAll(fieldsTitle, fields);
		
		fldId.setMinWidth(50);
		fldId.setPrefWidth(50);
		fldId.setMaxWidth(50);
		
		fldPhoneNumber.setMinWidth(130);
		fldPhoneNumber.setPrefWidth(130);
		fldPhoneNumber.setMaxWidth(130);
		
		fldId.setPromptText("Id");
		fldName.setPromptText("First Name *");
		fldLastName.setPromptText("Last Name *");
		fldEmail.setPromptText("Email");
		fldAddress.setPromptText("Address");
		fldPhoneNumber.setPromptText("Phone No.");

		HBox.setHgrow(fldName, Priority.ALWAYS);
		HBox.setHgrow(fldLastName, Priority.ALWAYS);
		HBox.setHgrow(fldEmail, Priority.ALWAYS);
		HBox.setHgrow(fldAddress, Priority.ALWAYS);
	}

	
	private void updateClearButton() {

		Boolean updateIdField = fldName.getText().trim().isEmpty() && fldLastName.getText().trim().isEmpty()
				&& fldEmail.getText().trim().isEmpty() && fldAddress.getText().trim().isEmpty()
				&& fldPhoneNumber.getText().trim().isEmpty();
		fldId.setDisable(!updateIdField);

		Boolean updateAddButton = fldName.getText().trim().isEmpty() || fldLastName.getText().trim().isEmpty();
		btnAddPatient.setDisable(updateAddButton);

		Boolean updateClearButton = fldId.getText().trim().isEmpty() && updateIdField;

		btnClear.setDisable(updateClearButton);
		btnClear.setVisible(!updateClearButton);
		btnSearchPatient.setDisable(updateClearButton);
	}
	
	private void editPatient() {
		Patient selectedPatient = tblPatients.getSelectionModel().getSelectedItem();
		editPatient(selectedPatient);
	}

	public void editPatient(Patient selectedPatient) {
		try {
			EditPatientScreen editPatient = new EditPatientScreen(selectedPatient);
			editPatient.initOwner(MainScreen.getInstance().getStage());
			editPatient.initModality(Modality.APPLICATION_MODAL); 
			editPatient.showAndWait();
			
			if (editPatient.wasUpdated()) {
				refreshTable();
				clearFields();
				controller.unsavedChanges();
			} 			
			
		} catch (Exception e) {
			System.out.println("Error updating patient - " + e.getMessage());
		}
	}
	
	protected void refreshTable() {
		tblPatients.getColumns().get(0).setVisible(false);
		tblPatients.getColumns().get(0).setVisible(true);
	}

	private void updateRemovePatientButton() {
		Patient pat = tblPatients.getSelectionModel().getSelectedItem();
		btnRemovePatient.setDisable(pat == null);
		btnEditPatient.setDisable(pat == null);
	}
}
