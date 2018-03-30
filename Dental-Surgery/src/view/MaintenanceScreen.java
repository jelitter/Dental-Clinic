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
import model.Procedure;
import view.elements.MyTitle;

public class MaintenanceScreen extends Pane {

	private static final double ID_WIDTH = 50.0;
	
	private static MaintenanceScreen instance;
	private ClinicController controller;
	private TableView<Procedure> tblProcedures;
	
	private HBox buttons;
	private Button btnUpdateProcedure, btnRemoveProcedure, btnAddProcedure, btnClear;
	private Region spacing;
	private HBox fields;
	private TextField fldId, fldName, fldDescription, fldPrice;
	
	private VBox pane;
	private MyTitle title;

	public MaintenanceScreen() {
		instance = this;
		go();
	}

	public static MaintenanceScreen getInstance() {
		if (instance == null) {
			return new MaintenanceScreen();
		} else {
			instance.go();			
			return instance;
		}
	}

	public void go() {
		
		controller = MainScreen.getInstance().getController();
		
		pane = new VBox(10);
		pane.setPadding(new Insets(20));
		pane.setStyle("-fx-background-color: #DDEEFF");

		title = new MyTitle("Procedure List");
		//Table
		createProceduresTable();
		setupFields();
		setupButtons();
		setButtonHandlers();
		setFieldHandlers();
		updateRemoveProcedureButton();
		updateClearButton();
		
		
		pane.getChildren().addAll(title, tblProcedures, fields, buttons);
		VBox.setVgrow(tblProcedures, Priority.ALWAYS);
		pane.setStyle("-fx-background-color: #DDDDFF");
		
	}

	private void updateRemoveProcedureButton() {
		Procedure proc = tblProcedures.getSelectionModel().getSelectedItem();
		btnRemoveProcedure.setDisable(proc == null);
		btnUpdateProcedure.setDisable(proc == null);
	}

	private void setButtonHandlers() {
		btnUpdateProcedure.setOnMouseClicked(e -> updateProcedure());
		btnRemoveProcedure.setOnMouseClicked(e -> removeProcedure());
		btnAddProcedure.setOnMouseClicked(e -> addProcedure());
		btnClear.setOnMouseClicked(e -> clearFields());
	}
	
	private void addProcedure() {
		String name = fldName.getText();
		String description = fldDescription.getText();
		String 	price = fldPrice.getText();
		Procedure newProcedure = new Procedure(name, description, Double.parseDouble(price));
		controller.addProcedure(newProcedure);
		clearFields();
	}
	
	private void updateProcedure() {
		try {
			Procedure selectedProcedure = tblProcedures.getSelectionModel().getSelectedItem();
			
			String name = fldName.getText();
			String 	description = fldDescription.getText();
			String price = fldPrice.getText();
			
			selectedProcedure.setName(name);
			selectedProcedure.setDescription(description);
			selectedProcedure.setPrice(Double.parseDouble(price));
			
			refreshTable();
			clearFields();
			controller.unsavedChanges();
			
		} catch (Exception e) {
			System.out.println("Error updating patient - " + e.getMessage());
		}
	}
	
	private void removeProcedure() {
		try {
			Procedure selectedProcedure = tblProcedures.getSelectionModel().getSelectedItem();

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Dental Surgery");
			alert.setHeaderText("Remove Procedure from data base?");
			alert.setContentText(selectedProcedure.toString());
			
			ButtonType yes = new ButtonType("_Yes, remove", ButtonData.FINISH);
			ButtonType no = new ButtonType("_No, keep procedure", ButtonData.CANCEL_CLOSE);
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
				tblProcedures.getItems().remove(selectedProcedure);
			    controller.unsavedChanges();
				System.out.println("Procedure removed");

			} else if (result.get() == no) {
				System.out.println("Procedure *not* removed");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			btnRemoveProcedure.setDisable(true);
		}
	}
	
	protected void refreshTable() {
		tblProcedures.getColumns().get(0).setVisible(false);
		tblProcedures.getColumns().get(0).setVisible(true);
	}
	
	private void setFieldHandlers() {
		fldId.setOnKeyReleased(e -> updateClearButton());
		fldName.setOnKeyReleased(e -> updateClearButton());
		fldDescription.setOnKeyReleased(e -> updateClearButton());
		fldPrice.setOnKeyReleased(e -> updateClearButton());
	}
	
	private void clearFields() {
		fldId.clear();
		fldName.clear();
		fldDescription.clear();
		fldPrice.clear();
		updateClearButton();
	}
	
private void updateClearButton() {
		
		Boolean updateIdField = fldName.getText().trim().isEmpty()
				&& fldDescription.getText().trim().isEmpty() && fldPrice.getText().trim().isEmpty();
		fldId.setDisable(!updateIdField);
		
		Boolean updateAddButton = fldName.getText().trim().isEmpty() || fldPrice.getText().trim().isEmpty();
		btnAddProcedure.setDisable(updateAddButton);
		
		Boolean updateClearButton = fldId.getText().trim().isEmpty() && updateIdField;
	
		btnClear.setDisable(updateClearButton);
		btnClear.setVisible(!updateClearButton);
	}

	private void setupButtons() {
		btnRemoveProcedure = new Button("➖  Remove");
		btnUpdateProcedure = new Button("⌨  Update");
		btnAddProcedure = new Button("➕  Add");
		btnClear = new Button("❌  Clear");
		
		btnRemoveProcedure.setPadding(new Insets(10, 20, 10, 20));
		btnUpdateProcedure.setPadding(new Insets(10, 20, 10, 20));
		btnAddProcedure.setPadding(new Insets(10, 20, 10, 20));
		btnClear.setPadding(new Insets(10, 20, 10, 20));
		
		btnRemoveProcedure.setPrefWidth(USE_COMPUTED_SIZE);
		btnUpdateProcedure.setPrefWidth(USE_COMPUTED_SIZE);
		btnAddProcedure.setPrefWidth(USE_COMPUTED_SIZE);
		btnClear.setPrefWidth(USE_COMPUTED_SIZE);

		btnRemoveProcedure.setStyle("-fx-base: LIGHTCORAL;");
		btnUpdateProcedure.setStyle("-fx-base: LIGHTGREEN;");
		btnAddProcedure.setStyle("-fx-base: LIMEGREEN;");
		btnClear.setStyle("-fx-base: LIGHTGOLDENRODYELLOW;");
		
		spacing = new Region();
        HBox.setHgrow(spacing, Priority.ALWAYS);
        
        buttons = new HBox(10);
		buttons.prefWidthProperty().bind(pane.widthProperty());
		
		buttons.setAlignment(Pos.BASELINE_RIGHT);
		buttons.getChildren().addAll(btnClear, spacing, btnRemoveProcedure, btnUpdateProcedure, btnAddProcedure);
	}

	private void setupFields() {
		fields = new HBox(10);
		fldId = new TextField("");
		fldName = new TextField("");
		fldDescription = new TextField("");
		fldPrice = new TextField("");
		
		fldId.setPromptText("Id");
		fldName.setPromptText("Name *");
		fldDescription.setPromptText("Description");
		fldPrice.setPromptText("Price *");
		
		fldId.prefWidthProperty().set(ID_WIDTH );
		fldPrice.prefWidthProperty().set(100);
		fldName.prefWidthProperty().set(180);
		
		fields.getChildren().addAll(fldId, fldName, fldDescription, fldPrice);
		HBox.setHgrow(fldDescription, Priority.ALWAYS);
	}

	public VBox getPane() {
		return pane;
	}

	private void createProceduresTable() {

		tblProcedures = new TableView<Procedure>();

		TableColumn<Procedure, String> idCol = new TableColumn<Procedure, String>("Id");
		TableColumn<Procedure, String> nameCol = new TableColumn<Procedure, String>("Name");
		TableColumn<Procedure, String> descriptionCol = new TableColumn<Procedure, String>("Description");
		TableColumn<Procedure, String> priceCol = new TableColumn<Procedure, String>("Price");

		idCol.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
		nameCol.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		descriptionCol.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
		priceCol.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty());
		tblProcedures.getColumns().addAll(idCol, nameCol, descriptionCol, priceCol);

		// This hide the horizontal scrollbar, but has the side-efect of making all
		// columns the same width
		tblProcedures.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		idCol.maxWidthProperty().set(ID_WIDTH);
		idCol.minWidthProperty().set(ID_WIDTH);
		idCol.prefWidthProperty().set(ID_WIDTH);

		priceCol.maxWidthProperty().set(100);
		priceCol.minWidthProperty().set(100);
		priceCol.prefWidthProperty().set(100);

		nameCol.minWidthProperty().set(200);
		nameCol.prefWidthProperty().set(200);
		nameCol.maxWidthProperty().set(200);

		idCol.setStyle("-fx-alignment: CENTER;");
		priceCol.setStyle("-fx-alignment: CENTER;");

		setProcedureTableItems();
		// setupDataFilter();

		 tblProcedures.setOnMouseClicked(e ->tableItemSelected());
		 tblProcedures.setOnKeyReleased(e -> {
		 KeyCode key = e.getCode();
		 if (key.equals(KeyCode.UP) || key.equals(KeyCode.DOWN) ||
		 key.equals(KeyCode.PAGE_UP)
		 || key.equals(KeyCode.PAGE_DOWN) || key.equals(KeyCode.HOME) ||
		 key.equals(KeyCode.END)) {
		 tableItemSelected();
		 }
		 });

	}

	public void setProcedureTableItems() {
		tblProcedures.setItems(controller.procedures);
	}
	
	private void tableItemSelected() {
		try {
			Procedure proc = tblProcedures.getSelectionModel().getSelectedItem();
			fldId.setText(proc.getIdProperty().get());
			fldId.setDisable(true);
			fldName.setText(proc.getName());
			fldDescription.setText(proc.getDescription());
			fldPrice.setText(Double.toString(proc.getPrice()));
			
			btnRemoveProcedure.setDisable(proc == null);
			btnUpdateProcedure.setDisable(proc == null);
			updateClearButton();
		} catch (Exception ex) {};
	}
	
	
}
