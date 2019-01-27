package cPaintUS.controllers;

import cPaintUS.models.BoundingBox;
import cPaintUS.models.Pointer;
import cPaintUS.models.observable.IObserver;
import cPaintUS.models.observable.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BottomPaneController implements IObserver {

	@FXML
	private Label x;
	@FXML
	private Label y;
	@FXML
	private Label w;
	@FXML
	private Label h;

	private Pointer pointer;
	private BoundingBox boundingBox;

	public BottomPaneController() {
		pointer = Pointer.getInstance();
		pointer.register(this);
		boundingBox = BoundingBox.getInstance();
		boundingBox.register(this);
	}
	
	@FXML
	private void initialize() {
		w.setVisible(boundingBox.isVisible());
		h.setVisible(boundingBox.isVisible());
	}

	@Override
	public void update(ObservableList obs) {
		switch (obs) {
		case POINTER:
			displayX();
			displayY();			
			break;
		case BOUNDING_BOX:
			displayW();
			displayH();			
			break;
		default:
			break;
		}
	}

	private void displayX() {
		x.setText(x.getText().substring(0, 4) + pointer.getCursorPoint().getX());
	}

	private void displayY() {
		y.setText(y.getText().substring(0, 4) + pointer.getCursorPoint().getY());
	}
	
	private void displayW() {
		w.setVisible(boundingBox.isVisible());
		w.setText(w.getText().substring(0, 4) + (boundingBox.isVisible() ? boundingBox.getWidth() : ""));
	}

	private void displayH() {
		h.setVisible(boundingBox.isVisible());
		h.setText(h.getText().substring(0, 4) + (boundingBox.isVisible() ? boundingBox.getHeight() : ""));
	}
}
