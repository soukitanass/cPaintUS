package cPaintUS.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddTextController {

	private Stage addDialog;

	@FXML
	private TextField addText;

	@FXML
	private Button addBtn;

	@FXML
	private Button cancelBtn;

	@FXML
	private HBox buttonsHBox;

	private RootController rootController;

	public void setAddDialog(Stage stage) {
		this.addDialog = stage;
	}

	public void setRoot(RootController rootController) {
		this.rootController = rootController;
	}

	@FXML
	public void initialize() {
		VBox.setMargin(buttonsHBox, new Insets(10));
	}

	@FXML
	public void handleAddTextClick() {
		CenterPaneController center = rootController.getCenterPaneController();
		center.setText(addText.getText());
		center.draw(true);
		addDialog.close();
	}

	@FXML
	public void handleCancelClick() {
		addDialog.close();
	}

}
