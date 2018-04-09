package view;

import java.util.Arrays;
import java.util.function.Predicate;

import controller.ClinicController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import model.Invoice;
import model.Patient;
import model.Procedure;
import view.elements.MyButton;
import view.elements.MyTitle;

public class ReportsScreen extends Pane {

	private ClinicController controller;
	private static ReportsScreen instance;
	private TableView<Patient> reportTable;
	private VBox pane, resultTable;
	private MyTitle title;
	private MyButton btnRefresh;
	private CheckBox debtors;
	private TextField fldMonths;
	private ScrollPane resultText;
	private TableColumn<Patient, String> firstNameCol, lastNameCol;

	public ReportsScreen() {
		instance = this;
		go();
	}

	public static ReportsScreen getInstance() {
		if (instance == null) {
			return new ReportsScreen();
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
		title = new MyTitle("Reports");
		btnRefresh = new MyButton("Refresh");
		btnRefresh.setIcon("report.png");


		HBox control = new HBox(10);
		createReportControl(control);
		
		resultTable = new VBox(10);
		resultTable.setMinHeight(150);
		createReportTable(resultTable);
		
		resultText = new ScrollPane();
		createReportText(resultText);
		
		VBox.setVgrow(resultTable, Priority.ALWAYS);
		VBox.setVgrow(resultText, Priority.ALWAYS);
		pane.getChildren().addAll(title, control, resultTable, resultText, btnRefresh);
		
		btnRefresh.setOnAction(e -> {
			updateReport(resultText);
		});

		debtors.setOnAction(e -> {
			updateReport(resultText);
		});

	}

	private void updateReport(ScrollPane resultText) {
		setupReportFilter();
		createReportText(resultText);
		reportTable.refresh();
	}

	private void createReportText(ScrollPane resultText) {

		TextFlow reportText = new TextFlow();
		reportText.setStyle("-fx-background-color: WHITE;");
		resultText.setFitToWidth(true);
		resultText.setFitToHeight(true);
		resultText.setPadding(new Insets(20));
		reportText.setPadding(new Insets(40));
		
		Text mainTitle = new Text("Results report\n\n");
		Text title = new Text("");
		String months = fldMonths.getText().isEmpty() ? "0" : fldMonths.getText();
		
		if (debtors.isSelected()) {
			title.setText("\nPending payments, but none during the last " + months + " months.\n");
		} else {
			title.setText("\nAll patients, sorted by name\n");
		}
		
		setFontH1(mainTitle);
		setFontH1(title);
		
		reportText.getChildren().addAll(mainTitle);
		
		Text totalProcs = new Text(controller.TotalNumberOfProceduresProperty().get() + " total procedures: " + String.format("%.2f", controller.TotalAmountProperty().get())  + " EUR.\n");
		Text totalPayms = new Text(controller.TotalNumberOfPaymentsProperty().get() + " total payments  : " + String.format("%.2f", controller.TotalPaidProperty().get()) + " EUR.\n");
		Text totalPending = new Text("  Total pending   : " + String.format("%.2f", controller.TotalPendingProperty().get()) + " EUR.\n");
		setFontH2(totalProcs);
		setFontH2(totalPayms);
		setFontH2(totalPending);
		reportText.getChildren().addAll(totalProcs, totalPayms, totalPending, title);

		
		for (Patient pat : reportTable.getItems()) {
			Text name = new Text("\n" + pat.getId() + ". " + pat.getFirstName() + " " + pat.getLastName());
			setFontH2(name);
			reportText.getChildren().addAll(name);
			Text procs = new Text("\n\t" + pat.NumberOfProceduresProperty().get() + " procedures: " + String.format("%.2f", pat.TotalAmountProperty().get()) + " EUR.");
			Text payms = new Text("\n\t" + pat.NumberOfPaymentsProperty().get() + " payments  : " + String.format("%.2f", pat.TotalPaidProperty().get()) + " EUR.");
			Text total = new Text("\n\t  Pending   : " + String.format("%.2f", pat.TotalPendingProperty().get()) + " EUR.\n");
			Text procDetails = new Text("");
			setFontH4(procDetails);
			for (Invoice inv : pat.getInvoices()) {
				for (Procedure p : inv.getProcedures()) {
					procDetails.setText(procDetails.getText() + "\n\t\t" + p.getName());
				}
			}
			
			setFontH3(procs);
			setFontH3(payms);
			setFontH3(total);
			reportText.getChildren().addAll(procs, procDetails, payms, total);
		}
		resultText.setContent(reportText);
	}

	private void setFontH1(Text text) {
		text.setFont(Font.font("Arial", FontWeight.BLACK, 20));
	}
	private void setFontH2(Text text) {
		text.setFont(Font.font("Courier New", FontWeight.BOLD, 16));
	}
	private void setFontH3(Text text) {
		text.setFont(Font.font("Courier New", FontWeight.MEDIUM, 14));
	}
	private void setFontH4(Text text) {
		text.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 12));
	}
	
	private void createReportControl(HBox control) {
		debtors = new CheckBox("Show only patients with pending invoices and no payments during the last");
		fldMonths = new TextField("6");
		Text debtors2 = new Text("months.");
		fldMonths.setAlignment(Pos.CENTER);
		fldMonths.setPrefWidth(37);
		final Tooltip monthsTooltip = new Tooltip("Enter 1 to 3 digits");
		fldMonths.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d{0,3}")) {
				fldMonths.setText(oldValue);
				monthsTooltip.show(
						fldMonths, 
						fldMonths.localToScreen(fldMonths.getBoundsInLocal()).getMinX(), 
						fldMonths.localToScreen(fldMonths.getBoundsInLocal()).getMaxY()); 
				new Timeline(new KeyFrame(Duration.millis(1500), ae -> {
					monthsTooltip.hide();
				})).play();
				
			} else {
				monthsTooltip.hide();
			}
			fldMonths.setPrefWidth(30 + fldMonths.getText().length() * 6);
			updateReport(resultText);
	    });
	
		debtors.setFont(Font.font("Arial", 16));
		debtors2.setFont(Font.font("Arial", 16));

		    
		debtors.setPrefHeight(24);
		debtors2.prefHeight(24);
		fldMonths.setPrefHeight(24);
		control.getChildren().addAll(debtors, fldMonths, debtors2);
	}
	

	private void createReportTable(VBox results) {
		reportTable = new TableView<Patient>();
		
		TableColumn<Patient, Number> idCol = new TableColumn<Patient,Number>("Id");
		firstNameCol = new TableColumn<Patient,String>("First Name");
		lastNameCol = new TableColumn<Patient, String>("Last Name");
		TableColumn<Patient, Number> totalCol = new TableColumn<Patient, Number>("Total");
		TableColumn<Patient, Number> paidCol = new TableColumn<Patient, Number>("Paid");
		TableColumn<Patient, Number> pendingCol = new TableColumn<Patient, Number>("Pending");
		TableColumn<Patient, String> lastPaymentCol = new TableColumn<Patient, String>("Last Payment");
		
		idCol.setCellValueFactory(cellData -> cellData.getValue().IdProperty());
        firstNameCol.setCellValueFactory(cellData -> cellData.getValue().FirstNameProperty());
        lastNameCol.setCellValueFactory(cellData -> cellData.getValue().LastNameProperty());
        totalCol.setCellValueFactory(cellData -> cellData.getValue().TotalAmountProperty());
        paidCol.setCellValueFactory(cellData -> cellData.getValue().TotalPaidProperty());
        pendingCol.setCellValueFactory(cellData -> cellData.getValue().TotalPendingProperty());
        lastPaymentCol.setCellValueFactory(cellData -> cellData.getValue().LastPaymentProperty());
        
        reportTable.getColumns().addAll(Arrays.asList(idCol, firstNameCol, lastNameCol, totalCol, paidCol, pendingCol, lastPaymentCol));
        reportTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        reportTable.setItems(controller.patients);
        
        if (resultText != null)
        	VBox.setVgrow(resultText, Priority.ALWAYS);
        VBox.setVgrow(reportTable, Priority.ALWAYS);
        results.getChildren().addAll(reportTable);
        
        setupReportFilter();
        
		reportTable.setOnMouseClicked(e -> {
			Patient pat = tableItemSelected();
			if (pat != null) {
				if (e.getClickCount() == 2) {
					PatientsScreen.getInstance().editPatient(pat);
				}
			}
		});
		
		reportTable.setOnKeyReleased(e -> {
			KeyCode key = e.getCode();
			if (key.equals(KeyCode.UP) || key.equals(KeyCode.DOWN) || key.equals(KeyCode.PAGE_UP)
					|| key.equals(KeyCode.PAGE_DOWN) || key.equals(KeyCode.HOME) || key.equals(KeyCode.END)) {
				tableItemSelected();
			} else if (key.equals(KeyCode.ENTER)) {
				PatientsScreen.getInstance().editPatient(tableItemSelected());
			}
		});
        
	}
	
	private void setupReportFilter() {
		ObjectProperty<Predicate<Patient>> pendingPayments = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Patient>> noPaymentsLastMonths = new SimpleObjectProperty<>();

		pendingPayments.bind(Bindings.createObjectBinding(() -> patient -> patient.TotalPendingProperty().get() > 0));

		int num = fldMonths.getText().isEmpty() ? 0 : Integer.parseInt(fldMonths.getText());
		
		noPaymentsLastMonths.bind(Bindings.createObjectBinding(() -> patient -> patient
				.TotalPaidPropertyLastMonths(num).get() == 0.0,
				fldMonths.textProperty()));

		FilteredList<Patient> filteredItems = new FilteredList<>(FXCollections.observableList(controller.patients));
		SortedList<Patient> filteredSortedItems = new SortedList<>(filteredItems, (Patient p1, Patient p2) -> {
			return p1.getFirstName().compareTo(p2.getFirstName());
		});
		
		reportTable.setItems(filteredSortedItems);
		filteredSortedItems.comparatorProperty().bind(reportTable.comparatorProperty());
		
		reportTable.getSortOrder().addAll(Arrays.asList(firstNameCol, lastNameCol));
	
		if (debtors.isSelected()) {
			filteredItems.predicateProperty().bind(Bindings.createObjectBinding(
					() -> pendingPayments.get().and(noPaymentsLastMonths.get()), pendingPayments, noPaymentsLastMonths));
		}
	}

	private Patient tableItemSelected() {
		Patient pat = reportTable.getSelectionModel().getSelectedItem();
		if (pat != null) {
			MainScreen.getInstance().setStatusText("Double click, <ENTER> or Edit button to edit Patient Id " + pat.getId());
		}
		return pat;
	}


	public VBox getPane() {
		return pane;
	}
}
