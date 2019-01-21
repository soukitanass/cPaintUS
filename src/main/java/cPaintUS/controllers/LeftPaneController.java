package cPaintUS.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;

public class LeftPaneController {
	
	private RootController root;
	
	@FXML
	private TextField brushSize;
	@FXML
	private ColorPicker colorPicker;
	@FXML
	private Button eraseBtn;
	
	public void setRoot(RootController rootController) {
		root = rootController;
	}
	
	@FXML
	private void initialize() {
	}
	
	@FXML
	private void onEraseBtnClick() {
		root.getCenterPaneController().eraseAll();
	}
}
