package view;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

import controller.ClinicController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import model.Invoice;
import model.Patient;
import model.Payment;
import model.Procedure;
import model.ProcedureType;
import view.elements.MiniButton;
import view.elements.MyTitle;

public class EditPatientScreen extends Stage {
	
	private final static double WIDTH = 700;
	private final static double HEIGHT = 700;
	
	private ClinicController controller;
	private Button btnApply,btnCancel;
	private MiniButton btnOkPayment,btnCancelPayment, btnOkProc, btnCancelProc;
	private HBox patientDetails;
	private TextField fldFirstName, fldLastName, fldEmail, fldPhone, fldAddress;
	private boolean updated;

	public EditPatientScreen(Patient patient) {
		
		controller = MainScreen.getInstance().getController();
		updated = false;
		
//		setResizable(false);
		setMinWidth(WIDTH);
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
			

		HBox buttons = new HBox(10);
		btnApply = new Button("Apply");
		btnCancel = new Button("Cancel");
		buttons.getChildren().addAll(btnApply,btnCancel);
		HBox.setHgrow(btnApply, Priority.ALWAYS);
		HBox.setHgrow(btnCancel, Priority.ALWAYS);
		btnApply.setPadding(new Insets(10, 20, 10, 20));
		btnCancel.setPadding(new Insets(10, 20, 10, 20));
		btnApply.setMinWidth(120);
		btnCancel.setMinWidth(120);
		btnApply.setStyle("-fx-base: LIGHTGREEN;");
		btnCancel.setStyle("-fx-base: LIGHTCORAL;");
		buttons.setAlignment(Pos.BASELINE_RIGHT);
		buttons.setPadding(new Insets(20,0,0,0));

		patientDetails = getPatientDetails(patient);
		patientDetails.setPadding(new Insets(20,0,0,0));
		
		VBox.setVgrow(patientDetails, Priority.ALWAYS);
		root.getChildren().addAll(title, fullName, contact, address, patientDetails, buttons);
		
		getIcons().add(new Image("/assets/patient.png"));
		
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


	private HBox getPatientDetails(Patient patient) {
		
		TableView<Invoice> invoices = new TableView<>();
		TableView<Payment> payments = new TableView<>();
		TableView<Procedure> procedures = new TableView<>();
		
		payments.setEditable(true);
		
		HBox details = new HBox(10);
		
		VBox detailsLeft = new VBox(0);
		
		Text invTitle = new Text("INVOICES");
		invTitle.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		invoices.setPlaceholder(new Label("This patient has no invoices"));
		setInvoicesTableColumns(invoices);
		invoices.setItems(patient.InvoicesProperty());
		
		HBox invoicesButtons = new HBox(0);
		MiniButton btnRemoveInvoice = new MiniButton("Remove");
		MiniButton btnAddInvoice = new MiniButton("Add");
		HBox.setHgrow(btnRemoveInvoice, Priority.ALWAYS);
		HBox.setHgrow(btnAddInvoice, Priority.ALWAYS);
		btnRemoveInvoice.setMaxWidth(Double.MAX_VALUE);
		btnAddInvoice.setMaxWidth(Double.MAX_VALUE);
		invoicesButtons.getChildren().addAll(btnRemoveInvoice, btnAddInvoice);
		btnRemoveInvoice.disableProperty().bind(invoices.getSelectionModel().selectedItemProperty().isNull());
		btnRemoveInvoice.visibleProperty().bind(invoices.getSelectionModel().selectedItemProperty().isNotNull());
		
		VBox.setVgrow(invoices, Priority.ALWAYS);
		detailsLeft.getChildren().addAll(invTitle, invoices, invoicesButtons);
		
		invoices.setOnMouseClicked(e -> {
			invoiceItemSelected(invoices, payments, procedures);
		});
		
		invoices.setOnKeyReleased(e -> {
			KeyCode key = e.getCode();
			if (key.equals(KeyCode.UP) || key.equals(KeyCode.DOWN) || key.equals(KeyCode.PAGE_UP)
					|| key.equals(KeyCode.PAGE_DOWN) || key.equals(KeyCode.HOME) || key.equals(KeyCode.END)) {
				invoiceItemSelected(invoices, payments, procedures);
			}
		});
		
		btnAddInvoice.setOnAction(e -> {
			// patient.addInvoice(new Invoice(patient.getId()));
			 invoices.setItems(patient.InvoicesProperty());
			
			controller.addInvoice(patient);
			
			controller.unsavedChanges();
		});
		btnRemoveInvoice.setOnAction(e -> {
			patient.removeInvoice(invoices.getSelectionModel().getSelectedItem());
			invoices.setItems(patient.InvoicesProperty());
			procedures.setItems(null);
			payments.setItems(null);
			controller.unsavedChanges();
		});

		
		
		VBox detailsRight = new VBox(0);
		
		Text payTitle = new Text("PAYMENTS");
		payTitle.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		payments.setPlaceholder(new Label("Select an invoice"));
		setPaymentsTableColumns(payments);
		
		HBox paymentsButtons = new HBox(0);
		MiniButton btnRemovePayment = new MiniButton("Remove");
		MiniButton btnAddPayment = new MiniButton("Add");
		HBox.setHgrow(btnRemovePayment, Priority.ALWAYS);
		HBox.setHgrow(btnAddPayment, Priority.ALWAYS);
		btnRemovePayment.setMaxWidth(Double.MAX_VALUE);
		btnAddPayment.setMaxWidth(Double.MAX_VALUE);
		paymentsButtons.getChildren().addAll(btnRemovePayment, btnAddPayment);
		btnAddPayment.disableProperty().bind(invoices.getSelectionModel().selectedItemProperty().isNull());
		btnRemovePayment.disableProperty().bind(payments.getSelectionModel().selectedItemProperty().isNull());
		btnRemovePayment.visibleProperty().bind(payments.getSelectionModel().selectedItemProperty().isNotNull());
	
		btnAddPayment.setOnAction(e -> {
			Invoice inv = invoices.getSelectionModel().getSelectedItem();
			addNewPayment(inv);
			payments.setItems(inv.PaymentsProperty());
			refreshTable(invoices);
			controller.unsavedChanges();
		});
		btnRemovePayment.setOnAction(e -> {
			Invoice inv = invoices.getSelectionModel().getSelectedItem();
			Payment pay = payments.getSelectionModel().getSelectedItem();
			inv.removePayment(pay);
			payments.setItems(inv.PaymentsProperty());
			refreshTable(invoices);
			controller.unsavedChanges();
		});
		
		
		Text procTitle = new Text("PROCEDURES");
		procTitle.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		procedures.setPlaceholder(new Label("Select an invoice"));
		setProceduresTableColumns(procedures);
		
		HBox proceduresButtons = new HBox(0);
		MiniButton btnRemoveProcedure = new MiniButton("Remove");
		MiniButton btnAddProcedure = new MiniButton("Add");
		HBox.setHgrow(btnRemoveProcedure, Priority.ALWAYS);
		HBox.setHgrow(btnAddProcedure, Priority.ALWAYS);
		btnRemoveProcedure.setMaxWidth(Double.MAX_VALUE);
		btnAddProcedure.setMaxWidth(Double.MAX_VALUE);
		btnAddProcedure.disableProperty().bind(invoices.getSelectionModel().selectedItemProperty().isNull());
		proceduresButtons.getChildren().addAll(btnRemoveProcedure, btnAddProcedure);
		btnRemoveProcedure.disableProperty().bind(procedures.getSelectionModel().selectedItemProperty().isNull());
		btnRemoveProcedure.visibleProperty().bind(procedures.getSelectionModel().selectedItemProperty().isNotNull());
		
		btnAddProcedure.setOnAction(e -> {
			Invoice inv = invoices.getSelectionModel().getSelectedItem();
			addNewProcedure(inv);
			procedures.setItems(inv.ProceduresProperty());
			refreshTable(invoices);
			controller.unsavedChanges();
		});
		btnRemoveProcedure.setOnAction(e -> {
			Invoice inv = invoices.getSelectionModel().getSelectedItem();
			Procedure proc = procedures.getSelectionModel().getSelectedItem();
			inv.removeProcedure(proc);
			procedures.setItems(inv.ProceduresProperty());
			refreshTable(invoices);
			controller.unsavedChanges();
		});
		
		detailsRight.getChildren().addAll(procTitle, procedures, proceduresButtons, payTitle, payments, paymentsButtons);
		detailsLeft.setMaxHeight(Double.MAX_VALUE);
		VBox.setVgrow(detailsLeft, Priority.ALWAYS);
		details.getChildren().addAll(detailsLeft, detailsRight);
		HBox.setHgrow(detailsLeft, Priority.ALWAYS);
		HBox.setHgrow(detailsRight, Priority.ALWAYS);
		return details;
	}


	private void invoiceItemSelected(TableView<Invoice> invoices, TableView<Payment> payments,
			TableView<Procedure> procedures) {
		Invoice inv = invoices.getSelectionModel().getSelectedItem();
		if (inv != null) {

			procedures.setItems(inv.ProceduresProperty());
			if (inv.ProcedureNumberProperty().get() == 0) {
				procedures.setPlaceholder(new Label("No procedures in this invoice"));
			}

			payments.setItems(inv.PaymentsProperty());
			if (inv.PaymentsNumberProperty().get() == 0) {
				payments.setPlaceholder(new Label("No payments in this invoice"));
			}
		} else {
			procedures.setItems(null);
			payments.setItems(null);
		}
	}


	private void setInvoicesTableColumns(TableView<Invoice> invoices) {
		TableColumn<Invoice, Number> idCol = new TableColumn<Invoice,Number>("Id");
		TableColumn<Invoice, String> amountCol = new TableColumn<Invoice,String>("Total");
		TableColumn<Invoice, String> paidCol = new TableColumn<Invoice,String>("Paid");
		TableColumn<Invoice, String> dateCol = new TableColumn<Invoice, String>("Date");
		TableColumn<Invoice, Boolean> isPaidCol = new TableColumn<Invoice, Boolean>("Is paid");
		TableColumn<Invoice, Number> proceduresCol = new TableColumn<Invoice, Number>("Procedures");
		TableColumn<Invoice, Number> paymentsCol = new TableColumn<Invoice, Number>("Payments");
		
		idCol.setCellValueFactory(cellData -> cellData.getValue().IdProperty());
		amountCol.setCellValueFactory(cellData -> cellData.getValue().TotalAmountStringProperty());
		paidCol.setCellValueFactory(cellData -> cellData.getValue().TotalAmountPaidStringProperty());
		dateCol.setCellValueFactory(cellData -> cellData.getValue().DateProperty());
		isPaidCol.setCellValueFactory(cellData -> cellData.getValue().IsPaidProperty());
		proceduresCol.setCellValueFactory(cellData -> cellData.getValue().ProcedureNumberProperty());
		paymentsCol.setCellValueFactory(cellData -> cellData.getValue().PaymentsNumberProperty());
		

        // Table cell colouring (isPaid)
		isPaidCol.setCellFactory(new Callback<TableColumn<Invoice, Boolean>, TableCell<Invoice, Boolean>>() {
            @Override
            public TableCell<Invoice, Boolean> call(TableColumn<Invoice, Boolean> param) {
                return new TableCell<Invoice, Boolean>() {
                    @Override
                    public void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
						if (item != null) {
							if (item) {
								this.setTextFill(Color.GREEN);
								this.setText("✔");
							} else {
								this.setTextFill(Color.CORAL);
								this.setText("✖");
							}
						} else {
							this.setText("");
						}
                    }
                };
            }
        });
		
        
		idCol.maxWidthProperty().set(40);
        idCol.minWidthProperty().set(40);
        idCol.prefWidthProperty().set(40);
        
        amountCol.maxWidthProperty().set(60);
        amountCol.minWidthProperty().set(60);
        amountCol.prefWidthProperty().set(60);
        
        paidCol.maxWidthProperty().set(60);
        paidCol.minWidthProperty().set(60);
        paidCol.prefWidthProperty().set(60);
        
        isPaidCol.maxWidthProperty().set(50);
        isPaidCol.minWidthProperty().set(50);
        isPaidCol.prefWidthProperty().set(50);
        
        proceduresCol.maxWidthProperty().set(90);
        proceduresCol.minWidthProperty().set(70);
        proceduresCol.prefWidthProperty().set(70);
        
        paymentsCol.maxWidthProperty().set(90);
        paymentsCol.minWidthProperty().set(70);
        paymentsCol.prefWidthProperty().set(70);
        
        invoices.getColumns().addAll(Arrays.asList(idCol, amountCol, paidCol, dateCol, isPaidCol, proceduresCol, paymentsCol));
        invoices.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
        for (TableColumn<?, ?> col: invoices.getColumns()) {
        	col.setStyle( "-fx-alignment: CENTER;");
        }
	}
	
	private void setPaymentsTableColumns(TableView<Payment> payments) {
		TableColumn<Payment, Number> idCol = new TableColumn<Payment,Number>("Id");
		TableColumn<Payment, String> dateCol = new TableColumn<Payment, String>("Date");
		TableColumn<Payment, String> amountCol = new TableColumn<Payment,String>("Amount");
		idCol.setCellValueFactory(cellData -> cellData.getValue().IdProperty());
		dateCol.setCellValueFactory(cellData -> cellData.getValue().DateProperty());
		amountCol.setCellValueFactory(cellData -> cellData.getValue().AmountStringProperty());
		
		idCol.setStyle( "-fx-alignment: CENTER;");
		dateCol.setStyle( "-fx-alignment: CENTER;");
		amountCol.setStyle( "-fx-alignment: CENTER;");
		
		idCol.maxWidthProperty().set(30);
		idCol.minWidthProperty().set(30);
		idCol.prefWidthProperty().set(30);
		
		amountCol.maxWidthProperty().set(50);
		amountCol.minWidthProperty().set(50);
		amountCol.prefWidthProperty().set(50);
		
		payments.getColumns().addAll(Arrays.asList(idCol, dateCol, amountCol));
		payments.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	
	private void setProceduresTableColumns(TableView<Procedure> procedures) {
		TableColumn<Procedure, Number> idCol = new TableColumn<Procedure,Number>("Id");
		TableColumn<Procedure, String> nameCol = new TableColumn<Procedure, String>("Name");
		TableColumn<Procedure, String> descCol = new TableColumn<Procedure, String>("Desc.");
		TableColumn<Procedure, String> priceCol = new TableColumn<Procedure, String>("Price");

		idCol.setCellValueFactory(cellData -> cellData.getValue().IdProperty());
		nameCol.setCellValueFactory(cellData -> cellData.getValue().NameProperty());
		descCol.setCellValueFactory(cellData -> cellData.getValue().DescriptionProperty());
		priceCol.setCellValueFactory(cellData -> cellData.getValue().PriceStringProperty());

		idCol.setStyle( "-fx-alignment: CENTER;");
		priceCol.setStyle( "-fx-alignment: CENTER;");

		
		idCol.maxWidthProperty().set(30);
		idCol.minWidthProperty().set(30);
		idCol.prefWidthProperty().set(30);
		
		priceCol.maxWidthProperty().set(50);
		priceCol.minWidthProperty().set(50);
		priceCol.prefWidthProperty().set(50);
		
		procedures.getColumns().addAll(Arrays.asList(idCol, nameCol, descCol, priceCol));
		procedures.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}


	private void setButtonHandlers(Patient patient) {
		
		btnApply.setOnAction(e -> {
			String firstName = fldFirstName.getText(),
					lastName = fldLastName.getText(),
					email = fldEmail.getText(),
					phone = fldPhone.getText(),
					address = fldAddress.getText();

			patient.setFirstName(firstName);
			patient.setLastName(lastName);
			patient.setEmail(email);
			patient.setPhoneNumber(phone);
			patient.setAddress(address);
			
			if (ClinicController.getDataSource() == 1) {
				// Database
				controller.updatePatient(patient);
			} else {
				// Serial File
				updated = true;
			}
			
			this.close();
		});
		
		btnCancel.setOnAction(e -> {
			this.close();
		});
		
		
		
	}
	
	public boolean wasUpdated() {
		return updated;
	}
	
	private void addNewPayment(Invoice inv) {

		Stage stage = new Stage();
		VBox root = new VBox(10);
		int width = 300;
		int height = 350;
		
		Scene scene = new Scene(root, width, height);
		stage.setScene(scene);
		stage.setResizable(false);
		
		stage.setWidth(width);
		stage.setHeight(height);
		root.setPadding(new Insets(30));
		root.setStyle(
				"-fx-background-image: url(" + "'/assets/background.png'" + "); " + "-fx-background-size: cover;");
		
		MyTitle paymentTitle = new MyTitle("New Payment");
		
		VBox dateBox = new VBox(10);
		VBox amountBox = new VBox(10);
		HBox buttons = new HBox(10);
		
		Text txtDate = new Text("Date");
		DatePicker date = new DatePicker();
		date.setValue(LocalDate.now());
		dateBox.getChildren().addAll(txtDate, date);
		
		Text txtAmount = new Text("Amount:");
		TextField fldAmount = new TextField();
		fldAmount.setPromptText("Amount paid");
		fldAmount.setText(inv.TotalAmountPendingStringProperty().get()); // Default to due amount
		amountBox.getChildren().addAll(txtAmount, fldAmount);
		
		btnOkPayment = new MiniButton("Ok");
		btnCancelPayment = new MiniButton("Cancel");
		HBox.setHgrow(btnOkPayment, Priority.ALWAYS);
		HBox.setHgrow(btnCancelPayment, Priority.ALWAYS);
		buttons.getChildren().addAll(btnOkPayment, btnCancelPayment);
		buttons.setAlignment(Pos.BOTTOM_RIGHT);

		Pane separator = new Pane();
		
		VBox.setVgrow(separator, Priority.ALWAYS);
		root.getChildren().addAll(paymentTitle, amountBox, dateBox, separator, buttons);
		
		stage.getIcons().add(new Image("/assets/payment.png"));
		stage.initOwner(this);
		stage.initModality(Modality.APPLICATION_MODAL); 
		stage.setTitle("Add Payment - Due: " + inv.TotalAmountPendingStringProperty().get() + " EUR.");
		
		
		// Listener to force Amount values to be numeric
		final Tooltip tt = new Tooltip("Enter only a valid int\nor float number");
		fldAmount.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*(\\.\\d*)?")) {
				fldAmount.setText(oldValue);
				tt.show(
						fldAmount, 
						fldAmount.localToScreen(fldAmount.getBoundsInLocal()).getMinX(), 
						fldAmount.localToScreen(fldAmount.getBoundsInLocal()).getMaxY()); 
				new Timeline(new KeyFrame(Duration.millis(1500), ae -> {
					tt.hide();
				})).play();
			}
		});
		
		btnOkPayment.setOnAction(e -> {
			Date retDate = Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
			Double amount = Double.parseDouble(fldAmount.getText());
			Payment p = new Payment(inv.getId(), amount, retDate);
			inv.addPayment(p);
			stage.close();
		});
		btnCancelPayment.setOnAction(e -> {
			stage.close();
		});
		
		stage.showAndWait();
		
	}
	
	private void addNewProcedure(Invoice inv) {
		
		Stage stage = new Stage();
		VBox root = new VBox(10);
		int width = 250;
		int height = 300;
		
		Scene scene = new Scene(root, width, height);
		stage.setScene(scene);
		stage.setResizable(false);
		
		stage.setWidth(width);
		stage.setHeight(height);
		root.setPadding(new Insets(20));
		root.setStyle(
				"-fx-background-image: url(" + "'/assets/background.png'" + "); " + "-fx-background-size: cover;");
		
		MyTitle procedureTitle = new MyTitle("New Procedure");
		
		ComboBox<ProcedureType> procsCombo = new ComboBox<ProcedureType>(controller.procedureTypes);
		procsCombo.getSelectionModel().select(0);
		
		HBox buttons = new HBox(10);
		btnOkProc = new MiniButton("Ok");
		btnCancelProc= new MiniButton("Cancel");
		HBox.setHgrow(btnOkProc, Priority.ALWAYS);
		HBox.setHgrow(btnCancelProc, Priority.ALWAYS);
		buttons.getChildren().addAll(btnOkProc, btnCancelProc);
		buttons.setAlignment(Pos.BOTTOM_RIGHT);
		
		root.getChildren().addAll(procedureTitle, procsCombo, buttons);
		
		stage.getIcons().add(new Image("/assets/procedure.png"));
		stage.initOwner(this);
		stage.initModality(Modality.APPLICATION_MODAL); 
		stage.setTitle("Add Procedure");
		
		
		btnOkProc.setOnAction(e -> {
			ProcedureType pt = procsCombo.getSelectionModel().getSelectedItem();
			Procedure p = new Procedure(inv.getId(), pt);
			inv.addProcedure(p);
			stage.close();
		});
		
		btnCancelProc.setOnAction(e -> {
			stage.close();
		});
		
		stage.showAndWait();
	}
	
	protected void refreshTable(TableView<?> table) {
		table.getColumns().get(0).setVisible(false);
		table.getColumns().get(0).setVisible(true);
	}

}
