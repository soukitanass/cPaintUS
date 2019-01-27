package cPaintUS.controllers.popup;


import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AboutController {
	private Stage newDialog;
	
	@FXML
	private Label description;
	@FXML
	private Button closeBtn;
	
	public void setNewDialog(Stage stage) {
		this.newDialog = stage;
	}
	
	@FXML
	private void initialize() {
		this.description.setPadding(new Insets(10, 20, 10, 20));
		this.description.setWrapText(true);
		this.description.setText("Created by cPaintUS team members:\nJean Bilski\nCorentin Chatelin\nNoemie Landry-Boisvert\nSoukaina Nassib\nMarc-Eric Pelletier");
		this.closeBtn.setAlignment(Pos.BOTTOM_CENTER);
		VBox.setMargin(this.closeBtn, new Insets(10));
	}
	
	@FXML
	private void handleCloseClick() {
		newDialog.close();
	}
	
}
