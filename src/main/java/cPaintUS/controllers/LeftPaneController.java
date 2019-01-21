package cPaintUS.controllers;

import java.util.ArrayList;
import java.util.List;
import cPaintUS.models.LineWidth;
import cPaintUS.models.shapes.ShapeType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;

public class LeftPaneController {
	
	private RootController root;
	
	@FXML
	private ComboBox<ShapeType> shape;
	@FXML
	private ComboBox<String> brushSize;
	@FXML
	private ColorPicker fillColor;
	@FXML
	private ColorPicker strokeColor;
	@FXML
	private Button eraseAllBtn;
	
	public void setRoot(RootController rootController) {
		root = rootController;
	}
	
	@FXML
	private void initialize() {
		// Add possible shapes to the shape ComboBox
		List<ShapeType> shapes = new ArrayList<ShapeType>();
		
		for (ShapeType s : ShapeType.values()) {
			shapes.add(s);
		}
		
		shape.getItems().setAll(shapes);
		
		// Add possible brush sizes to the brushSize ComboBox
		int[] widths = LineWidth.getInstance().getWidths();
		List<String> sizes = new ArrayList<String>();
		
		for (int w : widths) {
			sizes.add(w + "px");
		}
		
		brushSize.getItems().setAll(sizes);
		
		// Set ColorPickers default value to black
		fillColor.setValue(Color.BLACK);
		strokeColor.setValue(Color.BLACK);
	}
	
	@FXML
	private void handleChangeShape() {
		ShapeType newShape = shape.getValue();
		root.getCenterPaneController().setShape(newShape);
	}
	
	@FXML
	private void handleChangeBrushSize() {
		// Extract the integer in the string
		String sizeStr = brushSize.getValue().replaceAll("[^0-9]", "");
		int newSize = Integer.parseInt(sizeStr);
		
		root.getCenterPaneController().setLineWidth(newSize);
	}
	
	@FXML
	private void handleChangeFillColor() {
		Color newColor = fillColor.getValue();
		root.getCenterPaneController().setFillColor(newColor);
	}
	
	@FXML
	private void handleChangeStrokeColor() {
		Color newColor = strokeColor.getValue();
		root.getCenterPaneController().setStrokeColor(newColor);
	}
	
	@FXML
	private void handleEraseAllClick() {
		root.getCenterPaneController().eraseAll();
	}
}
