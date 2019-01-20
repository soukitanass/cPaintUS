package cPaintUS.controllers;

import cPaintUS.models.BoundingBox;
import cPaintUS.models.observable.IObserver;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BottomPaneController implements IObserver {
	
	@FXML private Label x;
	@FXML private Label y;
	@FXML private Label w;
	@FXML private Label h;
	
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
		x.setText(x.getText().substring(0, 4) + d);
	}
	
	private void displayY(double d) {
		y.setText(y.getText().substring(0, 4) + d);
	}

	
}
