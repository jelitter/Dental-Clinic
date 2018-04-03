package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Patient;
import view.elements.MyTitle;

public class EditPatientScreen extends Stage {
	
	private final static double WIDTH = 600;
	private final static double HEIGHT = 400;
	
	private Button btnSave,btnCancel;
	private TextField fldFirstName, fldLastName, fldEmail, fldPhone, fldAddress;
	private boolean updated;

	public EditPatientScreen(Patient patient) {
		
		updated = false;
		
		setResizable(false);
		setWidth(WIDTH);
		setHeight(HEIGHT);
		
		VBox root = new VBox(10);
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		
		root.setPadding(new Insets(20));
		root.setStyle(
				"-fx-background-image: url(" + "'/assets/background.png'" + "); " + "-fx-background-size: cover;");
		
		MyTitle title = new MyTitle("Edit Patient");
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
			
		Pane separator = new Pane();
		
		HBox buttons = new HBox(10);
		btnSave = new Button("Save");
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
		
		root.getChildren().addAll(title, fullName, contact, address, separator, buttons);
		VBox.setVgrow(separator, Priority.ALWAYS);
		
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
