package cPaintUS.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LeftPaneController {
	@FXML private Button _testButton;
	
	public void onClickTest() {
		System.out.println(_testButton.getText());
	}
}
