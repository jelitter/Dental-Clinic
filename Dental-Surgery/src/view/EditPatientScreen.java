package view;

import java.util.ArrayList;
import java.util.Arrays;

import controller.ClinicController;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Invoice;
import model.Patient;
import model.Payment;
import model.Procedure;
import view.elements.MyTitle;

public class EditPatientScreen extends Stage {
	
	private final static double WIDTH = 600;
	private final static double HEIGHT = 600;
	
	private ClinicController controller;
	private Button btnSave,btnCancel;
	private Pane patientDetails;
	private TextField fldFirstName, fldLastName, fldEmail, fldPhone, fldAddress;
	private boolean updated;

	public EditPatientScreen(Patient patient) {
		
		controller = MainScreen.getInstance().getController();
		updated = false;
		
//		setResizable(false);
		setWidth(WIDTH);
		setMinHeight(HEIGHT);
		
		VBox root = new VBox(10);
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		
		root.setPadding(new Insets(20));
		root.setStyle(
				"-fx-background-image: url(" + "'/assets/background.png'" + "); " + "-fx-background-size: cover;");
		
		MyTitle title = new MyTitle("");
		HBox fullName = new HBox(10);
		HBox contact = new HBox(10);
		
		
		fldFirstName = new TextField(patient.getFirstName());
		fldFirstName.setPromptText("First Name");
		TitledPane firstName = new TitledPane("First Name", fldFirstName);
		firstName.setCollapsible(false);

		fldLastName = new TextField(patient.getLastName());
		fldLastName.setPromptText("Last Name");
		TitledPane lastName = new TitledPane("Last Name", fldLastName);
		lastName.setCollapsible(false);
		HBox.setHgrow(firstName, Priority.ALWAYS);
		HBox.setHgrow(lastName, Priority.ALWAYS);
		fullName.getChildren().addAll(firstName, lastName);
		
		title.textProperty().bind(fldFirstName.textProperty().concat(" ").concat(fldLastName.textProperty()));

		fldEmail = new TextField(patient.getEmail());
		fldEmail.setPromptText("Email");
		TitledPane email = new TitledPane("Email", fldEmail);
		email.setCollapsible(false);
		
		fldPhone= new TextField(patient.getPhoneNumber());
		fldPhone.setPromptText("Phone no.");
		TitledPane phone = new TitledPane("Phone no.", fldPhone);
		phone.setCollapsible(false);
		
		HBox.setHgrow(email, Priority.ALWAYS);
		HBox.setHgrow(phone, Priority.ALWAYS);
		contact.getChildren().addAll(email, phone);
		
		fldAddress = new TextField(patient.getAddress());
		fldPhone.setPromptText("Phone no.");
		TitledPane address= new TitledPane("Address", fldAddress);
		address.setCollapsible(false);
			
		Pane sep1 = new Pane();
		Pane sep2= new Pane();
		
		HBox buttons = new HBox(10);
		btnSave = new Button("Apply");
		btnCancel = new Button("Cancel");
		buttons.getChildren().addAll(btnSave,btnCancel);
		HBox.setHgrow(btnSave, Priority.ALWAYS);
		HBox.setHgrow(btnCancel, Priority.ALWAYS);
		btnSave.setPadding(new Insets(10, 20, 10, 20));
		btnCancel.setPadding(new Insets(10, 20, 10, 20));
		btnSave.setMinWidth(120);
		btnCancel.setMinWidth(120);
		btnSave.setStyle("-fx-base: LIGHTGREEN;");
		btnCancel.setStyle("-fx-base: LIGHTCORAL;");
		buttons.setAlignment(Pos.BASELINE_RIGHT);

		patientDetails = getPatientDetails(patient);
		
		
		root.getChildren().addAll(title, fullName, contact, address, sep1, patientDetails, sep2, buttons);
		VBox.setVgrow(sep1, Priority.ALWAYS);
		VBox.setVgrow(sep2, Priority.ALWAYS);
		
		getIcons().add(new Image("/assets/icon.png"));
		
		double mainWidth = MainScreen.getInstance().getStage().getWidth();
		double mainX = MainScreen.getInstance().getStage().getX();
		double mainHeight = MainScreen.getInstance().getStage().getHeight();
		double mainY = MainScreen.getInstance().getStage().getY();
		
		setX(mainX + mainWidth / 2 - WIDTH / 2);
		setY(mainY + mainHeight / 2 - HEIGHT / 2);
		setTitle("Edit Patient");
		setScene(scene);
		
		setButtonHandlers(patient);
	}


	private Pane getPatientDetails(Patient patient) {
		HBox details = new HBox(10);
		
		VBox details1 = new VBox(10);
		Text invTitle = new Text("Invoices");
		TableView<Invoice> invoices = new TableView<>();
		
		details1.getChildren().addAll(invTitle, invoices);
		
		VBox details2 = new VBox(10);
		
		Text payTitle = new Text("Payments");
		TableView<Payment> payments = new TableView<>();
		
		Text procTitle = new Text("Procedures");
		TableView<Procedure> procedures = new TableView<>();
		
		details2.getChildren().addAll(payTitle, payments, procTitle, procedures);
		
		invoices.setItems(FXCollections.observableArrayList(patient.getInvoices()));
//		payments.setItems(FXCollections.observableArrayList(invoices.getSelectionModel().getSelectedItem().getPayments()));
		
		
		details.getChildren().addAll(details1, details2);
		
		HBox.setHgrow(details1, Priority.ALWAYS);
		HBox.setHgrow(details2, Priority.ALWAYS);
		
		return details;
	}


	private void setButtonHandlers(Patient patient) {
		
		btnSave.setOnAction(e -> {
			patient.setFirstName(fldFirstName.getText());
			patient.setLastName(fldLastName.getText());
			patient.setEmail(fldEmail.getText());
			patient.setPhoneNumber(fldPhone.getText());
			patient.setAddress(fldAddress.getText());
			updated = true;
			this.close();
		});
		
		btnCancel.setOnAction(e -> {
			this.close();
		});
		
	}
	
	public boolean wasUpdated() {
		return updated;
	}

}
