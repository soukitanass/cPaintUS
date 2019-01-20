package cPaintUS.controllers;

import cPaintUS.models.BoundingBox;
import cPaintUS.models.observable.IObserver;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BottomPaneController implements IObserver {
	
	@FXML private Label _X;
	@FXML private Label _Y;
	@FXML private Label _W;
	@FXML private Label _H;
	
	private BoundingBox _boundingBox;
	
	public BottomPaneController(){
		_boundingBox = BoundingBox.getInstance();
		_boundingBox.register(this);
	}
	
	@Override
	public void update() {
		displayX(_boundingBox.getCursorPoint().getX());
        displayY(_boundingBox.getCursorPoint().getY());
	}
	
	private void displayX(double d) {
		_X.setText(_X.getText().substring(0, 4) + d);
	}
	
	private void displayY(double d) {
		_Y.setText(_Y.getText().substring(0, 4) + d);
	}

	
}
