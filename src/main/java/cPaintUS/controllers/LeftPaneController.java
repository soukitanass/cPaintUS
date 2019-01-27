package cPaintUS.controllers;

import cPaintUS.models.DrawSettings;
import cPaintUS.models.LineWidth;
import cPaintUS.models.observable.IObserver;
import cPaintUS.models.observable.ObservableList;
import cPaintUS.models.shapes.Shape;
import cPaintUS.models.shapes.ShapeType;
import cPaintUS.models.shapes.ShapesDict;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;

public class LeftPaneController implements IObserver {
	
	private DrawSettings drawSettings;
	private ShapesDict shapesDict;
	
	@FXML
	private ComboBox<ShapeType> shape;
	@FXML
	private ComboBox<String> lineWidth;
	@FXML
	private ColorPicker fillColor;
	@FXML
	private ColorPicker strokeColor;
	@FXML
	private Button eraseAllBtn;
	@FXML
	private ListView<Shape> shapeList;
	
	public void setRoot(RootController rootController) {
	}
	
	public LeftPaneController() {
		shapesDict = ShapesDict.getInstance();
		shapesDict.register(this);
		drawSettings = DrawSettings.getInstance();
	}
	
	@FXML
	private void initialize() {
		// Add possible shapes to the shape ComboBox
		shape.getItems().setAll(ShapeType.values());
		shape.getItems().remove(ShapeType.Picture);
		shape.setValue(ShapeType.Line);
		
		// Add possible brush sizes to the brushSize ComboBox
		lineWidth.getItems().setAll(LineWidth.getInstance().getStrings());
		lineWidth.setValue(LineWidth.getInstance().getDefaultString());
		
		// Set ColorPickers default value to black
		fillColor.setValue(Color.BLACK);
		strokeColor.setValue(Color.BLACK);
	}
	
	@FXML
	private void handleChangeShape() {
		drawSettings.setShape(shape.getValue());
	}
	
	@FXML
	private void handleChangeBrushSize() {
		// Extract the integer in the string
		String widthStr = lineWidth.getValue().replaceAll("[^0-9]", "");
		int newWidth = Integer.parseInt(widthStr);
		
		drawSettings.setLineWidth(newWidth);
	}
	
	@FXML
	private void handleChangeFillColor() {
		drawSettings.setFillColor(fillColor.getValue());
	}
	
	@FXML
	private void handleChangeStrokeColor() {
		drawSettings.setStrokeColor(strokeColor.getValue());
	}
	
	@FXML
	private void handleEraseAllClick() {
		SnapshotSingleton.getInstance().eraseAll();
	}

	@Override
	public void update(ObservableList obs) {
		if(obs == ObservableList.SHAPES_UPDATED) {
			shapeList.getItems().clear();
			shapeList.getItems().addAll(shapesDict.getShapesList());
		}
	}
	
	
}
