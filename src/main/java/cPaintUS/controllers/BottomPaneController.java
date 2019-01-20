package cPaintUS.controllers;

import cPaintUS.models.Pointer;
import cPaintUS.models.observable.IObserver;
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

	public BottomPaneController() {
		pointer = Pointer.getInstance();
		pointer.register(this);
	}

	@Override
	public void update() {
		displayX(pointer.getCursorPoint().getX());
		displayY(pointer.getCursorPoint().getY());
	}

	private void displayX(double d) {
		x.setText(x.getText().substring(0, 4) + d);
	}

	private void displayY(double d) {
		y.setText(y.getText().substring(0, 4) + d);
	}

}
