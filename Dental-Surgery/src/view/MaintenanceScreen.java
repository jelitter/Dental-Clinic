package view;

import controller.ClinicController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Procedure;
import view.elements.MyTitle;

public class MaintenanceScreen extends Pane {

	private static MaintenanceScreen instance;
	private ClinicController controller;
	private TableView<Procedure> tblProcedures;
	
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

		title = new MyTitle("Maintenance");
		//Table
		createProceduresTable();
		pane.getChildren().addAll(title, tblProcedures);
		VBox.setVgrow(tblProcedures, Priority.ALWAYS);
		pane.setStyle("-fx-background-color: #DDDDFF");
		
	}
	public VBox getPane() {
		return pane;
	}
	
private void createProceduresTable() {
		
		tblProcedures = new TableView<Procedure>();
		
		TableColumn<Procedure, String> idCol = new TableColumn<Procedure,String>("Id");
		TableColumn<Procedure, String> nameCol = new TableColumn<Procedure,String>("Name");
		TableColumn<Procedure, String> descriptionCol = new TableColumn<Procedure, String>("Description");
		TableColumn<Procedure, String> priceCol = new TableColumn<Procedure, String>("Price");
		
		idCol.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
		nameCol.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		descriptionCol.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
		priceCol.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty());
		tblProcedures.getColumns().addAll(idCol, nameCol, descriptionCol, priceCol);  
		
        // This hide the horizontal scrollbar, but has the side-efect of making all columns the same width
		 tblProcedures.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
        
        idCol.maxWidthProperty().set(50);
        idCol.minWidthProperty().set(50);
        idCol.prefWidthProperty().set(50);
        
        priceCol.maxWidthProperty().set(100);
        priceCol.minWidthProperty().set(100);
        priceCol.prefWidthProperty().set(100);
        
        nameCol.minWidthProperty().set(200);
        nameCol.prefWidthProperty().set(200);
        nameCol.maxWidthProperty().set(350);
       

		idCol.setStyle( "-fx-alignment: CENTER;");
		priceCol.setStyle( "-fx-alignment: CENTER;");
        
		setProcedureTableItems();
//        setupDataFilter();
		
		
//		tblProcedures.setOnMouseClicked(e ->tableItemSelected());
//		tblProcedures.setOnKeyReleased(e -> {
//			KeyCode key = e.getCode();
//			if (key.equals(KeyCode.UP) || key.equals(KeyCode.DOWN) || key.equals(KeyCode.PAGE_UP)
//					|| key.equals(KeyCode.PAGE_DOWN) || key.equals(KeyCode.HOME) || key.equals(KeyCode.END)) {
//				tableItemSelected();
//			}
//		});
		
	}
	public void setProcedureTableItems() {
		tblProcedures.setItems(controller.procedures);
	}
}
