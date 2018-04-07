package view;

import java.util.Arrays;

import controller.ClinicController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Duration;
import model.Invoice;
import model.Patient;
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
		
		VBox results = new VBox(10);
		createReportTable(results);
		
		VBox.setVgrow(results, Priority.ALWAYS);
		pane.getChildren().addAll(title, control, results, refresh);
		
		refresh.setOnAction(e -> {
			reportTable.refresh();
		});

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
        
	}

	public VBox getPane() {
		return pane;
	}
}
