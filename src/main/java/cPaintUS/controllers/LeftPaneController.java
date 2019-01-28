package cPaintUS.controllers;

import java.io.IOException;

import cPaintUS.controllers.popup.AddTextController;
import cPaintUS.models.BoundingBox;
import cPaintUS.models.DrawSettings;
import cPaintUS.models.LineWidth;
import cPaintUS.models.Point;
import cPaintUS.models.observable.IObserver;
import cPaintUS.models.observable.ObservableList;
import cPaintUS.models.shapes.Shape;
import cPaintUS.models.shapes.ShapeEditor;
import cPaintUS.models.shapes.ShapeType;
import cPaintUS.models.shapes.ShapesDict;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LeftPaneController implements IObserver {

	private DrawSettings drawSettings;
	private ShapesDict shapesDict;
	private ShapeEditor shapeEditor;
	private Shape shapeToEdit;
	private BoundingBox boundingBox;
	
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

	@FXML
	private ToolBar attributes;
	@FXML
	private Label attributesLabel;
	@FXML
	private ComboBox<String> editLineWidth;
	@FXML
	private ColorPicker editFillColor;
	@FXML
	private ColorPicker editStrokeColor;
	@FXML
	private TextField editText;
	@FXML
	private TextField editX;
	@FXML
	private TextField editY;
	@FXML
	private TextField editWidth;
	@FXML
	private TextField editHeight;
	@FXML
	private TextField rotate;
	
	public void setRoot(RootController rootController) {
	}
	
	public LeftPaneController() {
		shapesDict = ShapesDict.getInstance();
		shapesDict.register(this);
		drawSettings = DrawSettings.getInstance();
		shapeEditor = ShapeEditor.getInstance();
		boundingBox = BoundingBox.getInstance();
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
		fillColor.setDisable(true);
		fillColor.setValue(Color.BLACK);
		strokeColor.setValue(Color.BLACK);

		editLineWidth.getItems().setAll(LineWidth.getInstance().getStrings());
		attributes.setVisible(false);

		// Event listener when shape is selected
		shapeList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Shape>() {
			@Override
			public void changed(ObservableValue<? extends Shape> observable, Shape oldShape, Shape newShape) {
				boundingBox.setVisible(newShape != null);
				if (newShape == null) {
					attributes.setVisible(false);
					boundingBox.setVisible(false);
					return;
				}
				boundingBox.setOrigin(newShape.getX(), newShape.getY());
				if (newShape.getShapeType() == ShapeType.Line) {
					boundingBox.updateBoundingBox(new Point(newShape.getWidth(),
							newShape.getHeight()));
				} else {
					boundingBox.updateBoundingBox(new Point(newShape.getX() + newShape.getWidth(),
							newShape.getY() + newShape.getHeight()));
				}

				shapeToEdit = newShape;
				attributesLabel.setText(newShape.getShapeId() + " Attributes:");
				attributesLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
				editLineWidth.setValue(newShape.getLineWidth() + "px");	
				
				String fillCol = newShape.getFillColor();
				if (fillCol == null) {
					editFillColor.setDisable(true);
				} else {
					editFillColor.setDisable(false);
					editFillColor.setValue(Color.web(fillCol));
				}

				editStrokeColor.setValue(Color.web(newShape.getStrokeColor()));
				editX.setText(String.valueOf(newShape.getX()));
				editY.setText(String.valueOf(newShape.getY()));
				editWidth.setText(String.valueOf(newShape.getWidth()));
				editHeight.setText(String.valueOf(newShape.getHeight()));
				rotate.setText(String.valueOf(newShape.getRotation()));
				attributes.setVisible(true);
			}
		});
	}

	@FXML
	private void handleChangeShape() {
		drawSettings.setShape(shape.getValue());
		fillColor.setDisable(shape.getValue() == ShapeType.Line);
		if (shape.getValue() == ShapeType.Text) {
			handleTextAddClick();
		}
	}

	@FXML
	private void handleChangeLineWidth() {
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

	@FXML
	private void handleEditLineWidth() {
		if (!attributes.isVisible()) return;
		// Extract the integer in the string
		String widthStr = editLineWidth.getValue().replaceAll("[^0-9]", "");
		int newWidth = Integer.parseInt(widthStr);
		shapeToEdit.setLineWidth(newWidth);
		shapeEditor.edit(shapeToEdit);
	}

	@FXML
	private void handleEditFillColor() {
		if (!attributes.isVisible()) return;
		String color =  String.format(
			"#%02X%02X%02X",
			(int) (editFillColor.getValue().getRed() * 255),
			(int) (editFillColor.getValue().getGreen() * 255),
			(int) (editFillColor.getValue().getBlue() * 255));
		shapeToEdit.setFillColor(color);
		shapeEditor.edit(shapeToEdit);
	}

	@FXML
	private void handleEditStrokeColor() {
		if (!attributes.isVisible()) return;
		String color =  String.format(
			"#%02X%02X%02X",
			(int) (editStrokeColor.getValue().getRed() * 255),
			(int) (editStrokeColor.getValue().getGreen() * 255),
			(int) (editStrokeColor.getValue().getBlue() * 255));
		shapeToEdit.setStrokeColor(color);
		shapeEditor.edit(shapeToEdit);
	}
	
	@FXML
	private void handleEditText() {
		String editedText = editText.getText();
		//
	}
	
	private void onlyNumeric(TextField textField) {
		String newValue = textField.getText();
        if (!newValue.matches("\\d*")) {
            textField.setText(newValue.replaceAll("[^\\d]", ""));
        }
	}
	
	@FXML
	private void handleEditX() {
		if (!attributes.isVisible()) return;
		onlyNumeric(editX);
		if (editX.getText() == null || editX.getText().trim().isEmpty()) {
			editX.setText(String.valueOf(shapeToEdit.getX()));
			return;
		}
		int newX = Integer.parseInt(editX.getText());
		shapeToEdit.setX(newX);
		shapeEditor.edit(shapeToEdit);
	}
	
	@FXML
	private void handleEditY() {
		if (!attributes.isVisible()) return;
		onlyNumeric(editY);
		if (editY.getText() == null || editY.getText().trim().isEmpty()) {
			editY.setText(String.valueOf(shapeToEdit.getY()));
			return;
		}
		int newY = Integer.parseInt(editY.getText());
		shapeToEdit.setY(newY);
		shapeEditor.edit(shapeToEdit);
	}
	
	@FXML
	private void handleEditWidth() {
		if (!attributes.isVisible()) return;
		onlyNumeric(editWidth);
		if (editWidth.getText() == null || editWidth.getText().trim().isEmpty()) {
			editWidth.setText(String.valueOf(shapeToEdit.getWidth()));
			return;
		}
		int newWidth = Integer.parseInt(editWidth.getText());
		shapeToEdit.setWidth(newWidth);
		shapeEditor.edit(shapeToEdit);
	}
	
	@FXML
	private void handleEditHeight() {
		if (!attributes.isVisible()) return;
		onlyNumeric(editHeight);
		if (editHeight.getText() == null || editHeight.getText().trim().isEmpty()) {
			editHeight.setText(String.valueOf(shapeToEdit.getHeight()));
			return;
		}
		int newHeight = Integer.parseInt(editHeight.getText());
		shapeToEdit.setHeight(newHeight);
		shapeEditor.edit(shapeToEdit);
	}
	
	@FXML
	private void handleRotate() {
		if (!attributes.isVisible()) return;
		onlyNumeric(rotate);
		if (rotate.getText() == null || rotate.getText().trim().isEmpty()) {
			rotate.setText(String.valueOf(shapeToEdit.getRotation()));
			return;
		}
		int newRotation = Integer.parseInt(rotate.getText());
		shapeToEdit.setRotation(newRotation);
		shapeEditor.edit(shapeToEdit);
	}

	@Override
	public void update(ObservableList obs) {
		if (obs == ObservableList.SHAPES_UPDATED) {
			shapeList.getItems().clear();
			shapeList.getItems().addAll(shapesDict.getShapesList());
		}
	}

	private void handleTextAddClick() {

		drawSettings.setShape(ShapeType.Text);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cPaintUS/views/popup/AddText.fxml"));
		Parent parent;
		try {
			parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Add Text");
			stage.setScene(scene);
			stage.setResizable(false);

			AddTextController controller = fxmlLoader.getController();
			controller.setAddDialog(stage);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
