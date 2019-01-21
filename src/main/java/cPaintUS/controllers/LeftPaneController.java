package cPaintUS.controllers;

import java.util.ArrayList;
import java.util.List;
import cPaintUS.models.LineWidth;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;

public class LeftPaneController {
	
	private RootController root;
	
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
		// Add possible brush sizes to the ComboBox
		int[] widths = LineWidth.getInstance().getWidths();
		List<String> sizes = new ArrayList<String>();
		
		for (int i = 0; i < widths.length; i++) {
			sizes.add(widths[i] + "px");
		}
		
		brushSize.getItems().setAll(sizes);
		
		// Set ColorPickers default value to black
		fillColor.setValue(Color.BLACK);
		strokeColor.setValue(Color.BLACK);
	}
	
	@FXML
	private void handleChangeBrushSize() {
		// Extract the integer in the string
		String sizeStr = brushSize.getValue().replaceAll("[^0-9]", "");
		int size = Integer.parseInt(sizeStr);
		
		root.getCenterPaneController().setLineWidth(size);
	}
	
	@FXML
	private void handleChangeFillColor() {
		Color color = fillColor.getValue();
		root.getCenterPaneController().setFillColor(color);
	}
	
	@FXML
	private void handleChangeStrokeColor() {
		Color color = strokeColor.getValue();
		root.getCenterPaneController().setStrokeColor(color);
	}
	
	@FXML
	private void handleEraseAllClick() {
		root.getCenterPaneController().eraseAll();
	}
}
