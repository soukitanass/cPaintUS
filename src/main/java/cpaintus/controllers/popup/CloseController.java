package cpaintus.controllers.popup;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CloseController {

	private Stage newDialog;
	private boolean isYesClicked = false;
	private boolean isCancelClicked = false;
	private boolean isNoClicked = false;

	@FXML
	private Label title;
	@FXML
	private Button yesBtn;
	@FXML
	private Button noBtn;

	public void setNewDialog(Stage stage) {
		this.newDialog = stage;
	}
	
	public boolean isNoClicked() {
		return isNoClicked;
	}

	public boolean isYesClicked() {
		return this.isYesClicked;
	}
	
	public boolean isCancelClicked() {
		return this.isCancelClicked;
	}


	@FXML
	public void handleYesClick() {
		isYesClicked = true;
		newDialog.close();
	}

	@FXML
	public void handleNoClick() {
		newDialog.close();
		isNoClicked = true;
	}
	
	@FXML
	public void handleCancelClick() {
		isCancelClicked = true;
		newDialog.close();
	}
	
	@FXML
	public void initialize() {
		title.setStyle("-fx-font-size: 24px");
		title.setStyle("-fx-padding: 10 20 10 20");
	}
}
