package view;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

import controller.ClinicController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Invoice;
import model.Patient;
import model.Payment;
import model.Procedure;
import view.elements.MiniButton;
import view.elements.MyTitle;

public class EditPatientScreen extends Stage {
	
	private final static double WIDTH = 700;
	private final static double HEIGHT = 700;
	
	private ClinicController controller;
	private Button btnSave,btnCancel,btnOkPayment,btnCancelPayment;
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
		
		
		root.getChildren().addAll(title, fullName, contact, address, patientDetails, buttons);
		VBox.setVgrow(patientDetails, Priority.ALWAYS);
		
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
		
		VBox details1 = new VBox(0);
		
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
		
		details1.getChildren().addAll(invTitle, invoices, invoicesButtons);
		
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
			patient.addInvoice(new Invoice());
			invoices.setItems(patient.InvoicesProperty());
		});
		btnRemoveInvoice.setOnAction(e -> {
			patient.removeInvoice(invoices.getSelectionModel().getSelectedItem());
			invoices.setItems(patient.InvoicesProperty());
		});

		
		
		VBox details2 = new VBox(0);
		
		Text payTitle = new Text("PAYMENTS");
		payTitle.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		payments.setPlaceholder(new Label("Select an invoice"));
		setPaymentsTableColumns(payments);
		
		HBox paymentsButtons = new HBox(0);
		MiniButton btnRemovePayment = new MiniButton("Remove");
		MiniButton btnEditPayment = new MiniButton("Edit");
		MiniButton btnAddPayment = new MiniButton("Add");
		HBox.setHgrow(btnRemovePayment, Priority.ALWAYS);
		HBox.setHgrow(btnEditPayment, Priority.ALWAYS);
		HBox.setHgrow(btnAddPayment, Priority.ALWAYS);
		btnRemovePayment.setMaxWidth(Double.MAX_VALUE);
		btnEditPayment.setMaxWidth(Double.MAX_VALUE);
		btnAddPayment.setMaxWidth(Double.MAX_VALUE);
		paymentsButtons.getChildren().addAll(btnRemovePayment, btnEditPayment, btnAddPayment);
		btnAddPayment.disableProperty().bind(invoices.getSelectionModel().selectedItemProperty().isNull());
		btnRemovePayment.disableProperty().bind(payments.getSelectionModel().selectedItemProperty().isNull());
		btnEditPayment.disableProperty().bind(payments.getSelectionModel().selectedItemProperty().isNull());
		btnRemovePayment.visibleProperty().bind(payments.getSelectionModel().selectedItemProperty().isNotNull());
		btnEditPayment.visibleProperty().bind(payments.getSelectionModel().selectedItemProperty().isNotNull());
		
	
		btnAddPayment.setOnAction(e -> {
			Invoice inv = invoices.getSelectionModel().getSelectedItem();
			getNewPayment(inv);
			payments.setItems(inv.PaymentsProperty());
			refreshTable(invoices);
		});
		btnRemovePayment.setOnAction(e -> {
			Invoice inv = invoices.getSelectionModel().getSelectedItem();
			Payment pay = payments.getSelectionModel().getSelectedItem();
			inv.removePayment(pay);
			payments.setItems(inv.PaymentsProperty());
			refreshTable(invoices);
		});
		
		
		Text procTitle = new Text("PROCEDURES");
		procTitle.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		procedures.setPlaceholder(new Label("Select an invoice"));
		setProceduresTableColumns(procedures);
		
		HBox proceduresButtons = new HBox(0);
		MiniButton btnRemoveProcedure = new MiniButton("Remove");
		MiniButton btnEditProcedure = new MiniButton("Edit");
		MiniButton btnAddProcedure = new MiniButton("Add");
		HBox.setHgrow(btnRemoveProcedure, Priority.ALWAYS);
		HBox.setHgrow(btnEditProcedure, Priority.ALWAYS);
		HBox.setHgrow(btnAddProcedure, Priority.ALWAYS);
		btnRemoveProcedure.setMaxWidth(Double.MAX_VALUE);
		btnEditProcedure.setMaxWidth(Double.MAX_VALUE);
		btnAddProcedure.setMaxWidth(Double.MAX_VALUE);
		btnAddProcedure.disableProperty().bind(invoices.getSelectionModel().selectedItemProperty().isNull());
		proceduresButtons.getChildren().addAll(btnRemoveProcedure, btnEditProcedure, btnAddProcedure);
		btnRemoveProcedure.disableProperty().bind(procedures.getSelectionModel().selectedItemProperty().isNull());
		btnEditProcedure.disableProperty().bind(procedures.getSelectionModel().selectedItemProperty().isNull());
		btnRemoveProcedure.visibleProperty().bind(procedures.getSelectionModel().selectedItemProperty().isNotNull());
		btnEditProcedure.visibleProperty().bind(procedures.getSelectionModel().selectedItemProperty().isNotNull());
		
		btnAddProcedure.setOnAction(e -> {
			Invoice inv = invoices.getSelectionModel().getSelectedItem();
			Procedure p = new Procedure("Test proc.", "Test desc.", 90.00);
			inv.addProcedure(p);
			procedures.setItems(inv.ProceduresProperty());
			refreshTable(invoices);
		});
		btnRemoveProcedure.setOnAction(e -> {
			Invoice inv = invoices.getSelectionModel().getSelectedItem();
			Procedure proc = procedures.getSelectionModel().getSelectedItem();
			inv.removeProcedure(proc);
			procedures.setItems(inv.ProceduresProperty());
			refreshTable(invoices);
		});
		
		
		
		details2.getChildren().addAll(procTitle, procedures, proceduresButtons, payTitle, payments, paymentsButtons);

		
		
		
		
		details1.setMaxHeight(Double.MAX_VALUE);
		VBox.setVgrow(details1, Priority.ALWAYS);
		details.getChildren().addAll(details1, details2);
		
		
		HBox.setHgrow(details1, Priority.ALWAYS);
		HBox.setHgrow(details2, Priority.ALWAYS);
		
//		details.setStyle("-fx-background-color: YELLOW;");
		
		
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
		}
	}


	private void setInvoicesTableColumns(TableView<Invoice> invoices) {
		TableColumn<Invoice, Number> idCol = new TableColumn<Invoice,Number>("Id");
		TableColumn<Invoice, Number> amountCol = new TableColumn<Invoice,Number>("Total");
		TableColumn<Invoice, Number> paidCol = new TableColumn<Invoice,Number>("Paid");
		TableColumn<Invoice, String> dateCol = new TableColumn<Invoice, String>("Date");
		TableColumn<Invoice, Boolean> isPaidCol = new TableColumn<Invoice, Boolean>("Is paid");
		TableColumn<Invoice, Number> proceduresCol = new TableColumn<Invoice, Number>("Procedures");
		TableColumn<Invoice, Number> paymentsCol = new TableColumn<Invoice, Number>("Payments");
		
		idCol.setCellValueFactory(cellData -> cellData.getValue().IdProperty());
		amountCol.setCellValueFactory(cellData -> cellData.getValue().TotalAmountProperty());
		paidCol.setCellValueFactory(cellData -> cellData.getValue().TotalAmountPaidProperty());
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
								this.setTextFill(Color.RED);
								this.setText("✖");
							}
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
		TableColumn<Payment, Number> amountCol = new TableColumn<Payment,Number>("Amount");
		idCol.setCellValueFactory(cellData -> cellData.getValue().IdProperty());
		dateCol.setCellValueFactory(cellData -> cellData.getValue().DateProperty());
		amountCol.setCellValueFactory(cellData -> cellData.getValue().AmountProperty());
		
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
		TableColumn<Procedure, Number> priceCol = new TableColumn<Procedure, Number>("Price");

		idCol.setCellValueFactory(cellData -> cellData.getValue().IdProperty());
		nameCol.setCellValueFactory(cellData -> cellData.getValue().NameProperty());
		descCol.setCellValueFactory(cellData -> cellData.getValue().DescriptionProperty());
		priceCol.setCellValueFactory(cellData -> cellData.getValue().PriceProperty());

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
	
	private void getNewPayment(Invoice inv) {

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
		fldAmount.setText(Double.toString(inv.TotalAmountProperty().get() - inv.TotalAmountPaidProperty().get())); // Default to due amount
		amountBox.getChildren().addAll(txtAmount, fldAmount);
		
		btnOkPayment = new Button("OK");
		btnCancelPayment = new Button("Cancel");
		HBox.setHgrow(btnOkPayment, Priority.ALWAYS);
		HBox.setHgrow(btnCancelPayment, Priority.ALWAYS);
		buttons.getChildren().addAll(btnOkPayment, btnCancelPayment);
		buttons.setAlignment(Pos.BOTTOM_RIGHT);

		root.getChildren().addAll(paymentTitle, dateBox, amountBox, buttons);
		
		stage.getIcons().add(new Image("/assets/payment.png"));
		stage.initOwner(this);
		stage.initModality(Modality.APPLICATION_MODAL); 
		stage.setTitle("Add Payment - Max: " + inv.getAmount() + " EUR.");
		
		
		btnOkPayment.setOnAction(e -> {
			Date retDate = Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
			Double amount = Double.parseDouble(fldAmount.getText());
			Payment p = new Payment(amount, retDate);
			inv.addPayment(p);
			stage.close();
		});
		btnCancelPayment.setOnAction(e -> {
			stage.close();
		});
		
		stage.showAndWait();
		
	}
	
	protected void refreshTable(TableView<?> table) {
		table.getColumns().get(0).setVisible(false);
		table.getColumns().get(0).setVisible(true);
	}

}
