package cPaintUS.models;

import cPaintUS.models.observable.IObserver;
import cPaintUS.models.observable.Observable;
import cPaintUS.models.observable.ObservableList;
import cPaintUS.models.shapes.ShapeType;
import cPaintUS.models.shapes.ShapesDict;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;

public class DrawSettings extends Observable<IObserver>{
	private DrawSettings instance;
	private ShapeType shape;
	private int lineWidth;
	private Color fillColor;
	private Color strokeColor;
	
	private DrawSettings() {
	}

	private static class DrawSettingsInstance {
		private static final DrawSettings instance = new DrawSettings();
	}
	
	public static DrawSettings getInstance() {
		return DrawSettingsInstance.instance;
	}
	
	public void setShape(ShapeType selectedShape) {
		shape = selectedShape;
		notifyAllObservers();
	}
	
	public ShapeType getShape() {
		return shape;
	}
	
	public void setLineWidth(int selectedLineWidth) {
		lineWidth = selectedLineWidth;
		notifyAllObservers();
	}
	
	public int getLineWidth() {
		return lineWidth;
	}
	
	public void setFillColor(Color selectedFillColor) {
		fillColor = selectedFillColor;
		notifyAllObservers();
	}
	
	public Color getFillColor() {
		return fillColor;
	}
	
	public void setStrokeColor(Color selectedStrokeColor) {
		strokeColor = selectedStrokeColor;
		notifyAllObservers();
	}
	
	public Color getStrokeColor() {
		return strokeColor;
	}

	@Override
	public void notifyAllObservers() {
		for (IObserver obs : this.getObserverList()) {
			obs.update(ObservableList.DRAW_SETTINGS);
		}
	}
}