package cPaintUS.controllers;

import java.io.IOException;

import cPaintUS.models.DrawSettings;
import cPaintUS.models.LineWidth;
import cPaintUS.models.observable.IObserver;
import cPaintUS.models.observable.ObservableList;
import cPaintUS.models.shapes.Ellipse;
import cPaintUS.models.shapes.Heart;
import cPaintUS.models.shapes.Pokeball;
import cPaintUS.models.shapes.Rectangle;
import cPaintUS.models.shapes.Shape;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.ToolBar;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
	private Button addTextBtn;
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
	private Spinner<Integer> editX;
	@FXML
	private Spinner<Integer> editY;
	@FXML
	private Spinner<Integer> editWidth;
	@FXML
	private Spinner<Integer> editHeight;
	@FXML
	private Spinner<Integer> rotate;

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
		fillColor.setDisable(true);
		fillColor.setValue(Color.BLACK);
		strokeColor.setValue(Color.BLACK);

		editLineWidth.getItems().setAll(LineWidth.getInstance().getStrings());
		attributes.setVisible(false);

		// Event listener when shape is selected
		shapeList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Shape>() {
			@Override
			public void changed(ObservableValue<? extends Shape> observable, Shape oldShape, Shape newShape) {
				if (newShape == null) {
					attributes.setVisible(false);
					return;
				}
				attributes.setVisible(true);
				attributesLabel.setText(newShape.getShapeId() + " Attributes:");
				attributesLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
				editLineWidth.setValue(newShape.getLineWidth() + "px");
				editFillColor.setDisable(false);

				switch (newShape.getShapeType()) {
				case Ellipse:
					editFillColor.setValue(Color.web(((Ellipse) newShape).getFillColor()));
					break;
				case Heart:
					editFillColor.setValue(Color.web(((Heart) newShape).getFillColor()));
					break;
				case Pokeball:
					editFillColor.setValue(Color.web(((Pokeball) newShape).getFillColor()));
					break;
				case Rectangle:
					editFillColor.setValue(Color.web(((Rectangle) newShape).getFillColor()));
					break;
				default:
					editFillColor.setDisable(true);
					break;
				}

				editStrokeColor.setValue(Color.web(newShape.getStrokeColor()));
				editX.getValueFactory().setValue((int) newShape.getX());
				editY.getValueFactory().setValue((int) newShape.getY());
				editWidth.getValueFactory().setValue((int) newShape.getWidth());
				editHeight.getValueFactory().setValue((int) newShape.getHeight());
			}
		});
	}

	@FXML
	private void handleChangeShape() {
		drawSettings.setShape(shape.getValue());
		fillColor.setDisable(shape.getValue() == ShapeType.Line);
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
		// Extract the integer in the string
		String widthStr = lineWidth.getValue().replaceAll("[^0-9]", "");
		int newWidth = Integer.parseInt(widthStr);

		//
	}

	@FXML
	private void handleEditFillColor() {
		//
	}

	@FXML
	private void handleEditStrokeColor() {
		//
	}

	@Override
	public void update(ObservableList obs) {
		if (obs == ObservableList.SHAPES_UPDATED) {
			shapeList.getItems().clear();
			shapeList.getItems().addAll(shapesDict.getShapesList());
		}
	}

	@FXML
	public void handleTextAddClick() {

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
