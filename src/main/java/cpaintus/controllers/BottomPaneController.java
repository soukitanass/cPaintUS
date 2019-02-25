package cpaintus.controllers;

import cpaintus.models.BoundingBox;
import cpaintus.models.Pointer;
import cpaintus.models.ZoomSingleton;
import cpaintus.models.observable.IObserver;
import cpaintus.models.observable.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class BottomPaneController implements IObserver {

	@FXML
	private Label x;
	@FXML
	private Label y;
	@FXML
	private Label w;
	@FXML
	private Label h;
	@FXML
	private Slider zoomSlider;

	private Pointer pointer;
	private BoundingBox boundingBox;
	private ZoomSingleton zoomSingleton;

	public BottomPaneController() {
		pointer = Pointer.getInstance();
		pointer.register(this);
		boundingBox = BoundingBox.getInstance();
		boundingBox.register(this);
		zoomSingleton = ZoomSingleton.getInstance();
		zoomSingleton.register(this);
	}
	
	@FXML
	private void initialize() {
		w.setVisible(boundingBox.isVisible());
		h.setVisible(boundingBox.isVisible());
		zoomSlider.setValue(zoomSingleton.getZoom());
		zoomSlider.valueProperty().addListener((obs,oldValue,newValue)->{
			zoomSingleton.setOldZoom(oldValue.doubleValue());
			zoomSingleton.setZoom(newValue.doubleValue());
		});
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
		case ZOOM:
			zoomSlider.valueProperty().set(zoomSingleton.getZoom());	
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
