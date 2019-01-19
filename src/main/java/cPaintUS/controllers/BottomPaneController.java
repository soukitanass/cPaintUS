package cPaintUS.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class BottomPaneController {
	
	@FXML private Label _X;
	@FXML private Label _Y;
	@FXML private Label _W;
	@FXML private Label _H;
	
	public BottomPaneController(){
	}
	
	private void onMouseMoved(MouseEvent event) {
		displayX(event.getX());
        displayY(event.getY());
	}
	
	private void displayX(double d) {
		_X.setText(_X.getText().substring(0, 4) + d);
	}
	
	private void displayY(double d) {
		_Y.setText(_Y.getText().substring(0, 4) + d);
	}
}
