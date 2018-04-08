package view;

import java.util.Arrays;

import controller.ClinicController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
	private VBox pane;
	private MyTitle title;
	private MyButton refresh;

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
		refresh = new MyButton("Refresh");
		refresh.setIcon("report.png");


		HBox control = new HBox(10);
		createReportControl(control);
		
		VBox resultTable = new VBox(10);
		resultTable.setMinHeight(150);
		createReportTable(resultTable);
		
		ScrollPane resultText = new ScrollPane();
		createReportText(resultText);
		
		VBox.setVgrow(resultTable, Priority.ALWAYS);
		VBox.setVgrow(resultText, Priority.ALWAYS);
		pane.getChildren().addAll(title, control, resultTable, resultText, refresh);
		
		refresh.setOnAction(e -> {
			reportTable.refresh();
			createReportText(resultText);
		});

	}

	private void createReportText(ScrollPane resultText) {

		TextFlow reportText = new TextFlow();
		reportText.setStyle("-fx-background-color: WHITE;");
		resultText.setFitToWidth(true);
		resultText.setFitToHeight(true);
		resultText.setPadding(new Insets(20));
		reportText.setPadding(new Insets(40));
		
		Text title = new Text("Results report - " + 6 + " months.\n\n");
		setFontH1(title);
		
		reportText.getChildren().addAll(title);
		
		Text totalProcs = new Text(controller.TotalNumberOfProceduresProperty().get() + " total procedures: " + controller.TotalAmountProperty().get()  + " EUR.\n");
		Text totalPayms = new Text(controller.TotalNumberOfPaymentsProperty().get() + " total payments  : " + controller.TotalPaidProperty().get() + " EUR.\n");
		Text totalPending = new Text("  Total pending   : " + controller.TotalPendingProperty().get() + " EUR.\n");
		setFontH2(totalProcs);
		setFontH2(totalPayms);
		setFontH2(totalPending);
		reportText.getChildren().addAll(totalProcs, totalPayms, totalPending);

		
		for (Patient pat : reportTable.getItems()) {
			Text name = new Text("\n" + pat.getId() + ". " + pat.getFirstName() + " " + pat.getLastName());
			setFontH2(name);
			reportText.getChildren().addAll(name);
			Text procs = new Text("\n\t" + pat.NumberOfProceduresProperty().get() + " procedures: " + pat.TotalAmountProperty().get() + " EUR.");
			Text payms = new Text("\n\t" + pat.NumberOfPaymentsProperty().get() + " payments  : " + pat.TotalPaidProperty().get() + " EUR.");
			Text total = new Text("\n\t  Pending   : " + pat.TotalPendingProperty().get() + " EUR.\n");
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
		CheckBox debtors = new CheckBox("Show only patients with pending invoices and no payments during the last");
		TextField months = new TextField("6");
		Text debtors2 = new Text("months.");
		months.setAlignment(Pos.CENTER);
		months.setPrefWidth(37);
		final Tooltip monthsTooltip = new Tooltip("Enter only digits");
		months.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				months.setText(oldValue);
				monthsTooltip.show(
						months, 
						months.localToScreen(months.getBoundsInLocal()).getMinX(), 
						months.localToScreen(months.getBoundsInLocal()).getMaxY()); 
				new Timeline(new KeyFrame(Duration.millis(1500), ae -> {
					monthsTooltip.hide();
				})).play();
				
			} else {
				monthsTooltip.hide();
			}
			months.setPrefWidth(30 + months.getText().length() * 6);
	    });
	
		debtors.setFont(Font.font("Arial", 16));
		debtors2.setFont(Font.font("Arial", 16));

		    
		debtors.setPrefHeight(24);
		debtors2.prefHeight(24);
		months.setPrefHeight(24);
		control.getChildren().addAll(debtors, months, debtors2);
	}
	

	private void createReportTable(VBox results) {
		reportTable = new TableView<Patient>();
		
		TableColumn<Patient, Number> idCol = new TableColumn<Patient,Number>("Id");
		TableColumn<Patient, String> firstNameCol = new TableColumn<Patient,String>("First Name");
		TableColumn<Patient, String> lastNameCol = new TableColumn<Patient, String>("Last Name");
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
        
        VBox.setVgrow(reportTable, Priority.ALWAYS);
        results.getChildren().addAll(reportTable);
        
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
