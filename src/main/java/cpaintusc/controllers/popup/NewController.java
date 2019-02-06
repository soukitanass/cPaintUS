package cpaintusc.controllers.popup;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NewController {
	private Stage newDialog;
	private boolean isYesClicked = false;

	@FXML
	private Label title;
	@FXML
	private HBox buttonsHBox;
	@FXML
	private Button yesBtn;
	@FXML
	private Button noBtn;
	
	public void setNewDialog(Stage stage) {
		this.newDialog = stage;
	}
	
	public boolean isYesClicked() {
		return this.isYesClicked;
	}
	
	@FXML
	public void handleYesClick() {
		isYesClicked = true;
		newDialog.close();
	}
	
	@FXML
	public void handleNoClick() {
		newDialog.close();
	}
	
	@FXML
	public void initialize() {
		title.setPadding(new Insets(10, 20, 10, 20));
		VBox.setMargin(buttonsHBox, new Insets(10));
	}
}
