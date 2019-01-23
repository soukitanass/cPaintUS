package cPaintUS.controllers;

import java.util.ArrayList;
import java.util.List;
import cPaintUS.models.LineWidth;
import cPaintUS.models.DrawSettings;
import cPaintUS.models.shapes.ShapeType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;

public class LeftPaneController {
	
	private RootController root;
	private DrawSettings drawSettings;
	
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
	
	public void setRoot(RootController rootController) {
		root = rootController;
	}
	
	@FXML
	private void initialize() {
		drawSettings = DrawSettings.getInstance();

		// Add possible shapes to the shape ComboBox
		shape.getItems().setAll(ShapeType.values());
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
		root.getCenterPaneController().eraseAll();
	}
}
