package cpaintus.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import cpaintus.controllers.popup.AddTextController;
import cpaintus.models.BoundingBox;
import cpaintus.models.DrawSettings;
import cpaintus.models.LineWidth;
import cpaintus.models.composite.ShapesGroup;
import cpaintus.models.observable.IObserver;
import cpaintus.models.observable.ObservableList;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.Shape2D;
import cpaintus.models.shapes.ShapeDimension;
import cpaintus.models.shapes.ShapeEditor;
import cpaintus.models.shapes.ShapeType;
import cpaintus.models.shapes.ShapesDictionnary;
import cpaintus.models.shapes.Text;
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
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class LeftPaneController implements IObserver {

	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private DrawSettings drawSettings;
	private ShapesDictionnary shapesDict;
	private ShapeEditor shapeEditor;
	private Shape shapeToEdit;
	private TreeItem<Shape> selectedTreeItem;
	private BoundingBox boundingBox;
	private Preferences prefs;
	private SelectShapesSingleton selectShapesSingleton;
	private ChangeListener<Integer> editZListener;
	private ChangeListener<TreeItem<Shape>> selectShapeListener;
	private boolean isGrouping;

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
	private Button editBtn;
	@FXML
	private Button selectBtn;
	@FXML
	private Button unselectBtn;
	@FXML
	private TreeView<Shape> tree;

	@FXML
	private ToolBar attributes;
	@FXML
	private Label attributesLabel;
	
	@FXML
	private HBox editLineWidthSection;
	@FXML
	private HBox editFillColorSection;
	@FXML
	private HBox editStrokeColorSection;
	@FXML
	private HBox editTextSection;
	@FXML
	private HBox editZSection;
	@FXML
	private HBox editWidthSection;
	@FXML
	private HBox editHeightSection;
	@FXML
	private HBox editRotateSection;
	
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
	private Spinner<Integer> editZ;
	@FXML
	private TextField editWidth;
	@FXML
	private TextField editHeight;
	@FXML
	private TextField rotate;

	public LeftPaneController() {
		shapesDict = ShapesDictionnary.getInstance();
		shapesDict.register(this);
		drawSettings = DrawSettings.getInstance();
		shapeEditor = ShapeEditor.getInstance();
		boundingBox = BoundingBox.getInstance();

		prefs = Preferences.userNodeForPackage(this.getClass());
		selectShapesSingleton = SelectShapesSingleton.getInstance();
		selectShapesSingleton.register(this);
	    
	    editZListener = new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				handleEditZ(newValue);
			}
		};
	    
	    selectShapeListener = new ChangeListener<TreeItem<Shape>>() {
			@Override
			public void changed(ObservableValue<? extends TreeItem<Shape>> observable, TreeItem<Shape> oldItem,
					TreeItem<Shape> newItem) {
				if (newItem == null) return;

				selectedTreeItem = newItem;
				handleSelectShape(newItem.getValue());
				
			}
	    };
	    
	    isGrouping = false;
	}

	@FXML
	private void initialize() {
		// Shape settings
		shape.getItems().setAll(ShapeType.values());
		shape.getItems().remove(ShapeType.PICTURE);
		shape.getItems().remove(ShapeType.GROUP);
		shape.setValue(ShapeType.valueOf(prefs.get("shape", "LINE")));

		// Line width setting
		lineWidth.getItems().setAll(LineWidth.getInstance().getStrings());
		lineWidth.setValue(prefs.get("linewidth", LineWidth.getInstance().getDefaultString()));
		
		// Color settings
		fillColor.setDisable(shape.getValue() == ShapeType.LINE);
		fillColor.setValue(Color.valueOf(prefs.get("fillcolor", "BLACK")));
		strokeColor.setValue(Color.valueOf(prefs.get("strokecolor", "BLACK")));

		// Attributes
		attributes.setVisible(false);
		editLineWidth.getItems().setAll(LineWidth.getInstance().getStrings());
		editX.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
		editY.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
		editWidth.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
		editHeight.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
		rotate.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
		
		// Bind managed to visibility		
		unselectBtn.managedProperty().bind(unselectBtn.visibleProperty());
		editLineWidthSection.managedProperty().bind(editLineWidthSection.visibleProperty());
		editStrokeColorSection.managedProperty().bind(editStrokeColorSection.visibleProperty());
		editZSection.managedProperty().bind(editZSection.visibleProperty());
		editWidthSection.managedProperty().bind(editWidthSection.visibleProperty());
		editHeightSection.managedProperty().bind(editHeightSection.visibleProperty());
		editRotateSection.managedProperty().bind(editRotateSection.visibleProperty());
		editFillColorSection.managedProperty().bind(editFillColorSection.visibleProperty());
		editTextSection.managedProperty().bind(editTextSection.visibleProperty());

		// Add event listeners
		editZ.valueProperty().addListener(editZListener);
		tree.getSelectionModel().selectedItemProperty().addListener(selectShapeListener);
	}

	@FXML
	private void handleChangeShape() {
		drawSettings.setShape(shape.getValue());
		fillColor.setDisable(shape.getValue() == ShapeType.LINE);
		if (shape.getValue() == ShapeType.TEXT) {
			handleTextAddClick();
		}
		prefs.put("shape", shape.getValue().name());
	}

	@FXML
	private void handleChangeLineWidth() {
		// Extract the integer in the string
		String widthStr = lineWidth.getValue().replaceAll("[^0-9]", "");
		int newWidth = Integer.parseInt(widthStr);

		drawSettings.setLineWidth(newWidth);
		prefs.put("linewidth", widthStr);
	}

	@FXML
	private void handleChangeFillColor() {
		drawSettings.setFillColor(fillColor.getValue());
		prefs.put("fillcolor", fillColor.getValue().toString());
	}

	@FXML
	private void handleChangeStrokeColor() {
		drawSettings.setStrokeColor(strokeColor.getValue());
		prefs.put("strokecolor", strokeColor.getValue().toString());
	}

	@FXML
	private void handleEraseAllClick() {
		SnapshotSingleton.getInstance().eraseAll();
	}
	
	private void handleSelectShape(Shape newShape) {		
		boundingBox.setVisible(newShape != null);
		if (newShape == null) {
			attributes.setVisible(false);
			return;
		}
		shapeToEdit = newShape;

		// Always shown attributes
		attributesLabel.setText(newShape.getShapeId() + " Attributes:");
		attributesLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
		editX.setText(String.valueOf((int) Math.round(newShape.getUpLeftCorner().getX())));
		editY.setText(String.valueOf((int) Math.round(newShape.getUpLeftCorner().getY())));

		// Attributes shown on specific ShapeTypes
		boolean isGroup = newShape.getShapeType() == ShapeType.GROUP;
		boolean showFillColor = !isGroup
				&& newShape.getShapeDimension() != ShapeDimension.SHAPE1D;
		boolean showText = !isGroup
				&& newShape.getShapeType() == ShapeType.TEXT;

		// Set the attributes visibility
		unselectBtn.setVisible(isGroup);
		editLineWidthSection.setVisible(!isGroup);
		editStrokeColorSection.setVisible(!isGroup);
		editZSection.setVisible(!isGroup);
		editWidthSection.setVisible(!isGroup);
		editHeightSection.setVisible(!isGroup);
		editRotateSection.setVisible(!isGroup);
		editFillColorSection.setVisible(showFillColor);
		editTextSection.setVisible(showText);

		// Set values if not a group
		if (!isGroup) {
			editLineWidth.setValue(newShape.getLineWidth() + "px");
			editStrokeColor.setValue(Color.web(newShape.getStrokeColor()));
			SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,
					shapesDict.getShapesList().size(), shapeToEdit.getZ());
			editZ.setValueFactory(valueFactory);
			editWidth.setText(String.valueOf((int) Math.round(newShape.getWidth())));
			editHeight.setText(String.valueOf((int) Math.round(newShape.getHeight())));
			rotate.setText(String.valueOf((int) Math.round(newShape.getRotation())));
			if (showFillColor) {
				editFillColor.setValue(Color.web(((Shape2D) newShape).getFillColor()));
			}
			if (showText) {
				editText.setText(((Text) newShape).getText());
			}
		}

		attributes.setVisible(true);
		shapeEditor.edit(shapeToEdit);
	}

	@FXML
	private void handleEditLineWidth() {
		if (!attributes.isVisible())
			return;
		// Extract the integer in the string
		String widthStr = editLineWidth.getValue().replaceAll("[^0-9]", "");
		int newWidth = Integer.parseInt(widthStr);
		shapeToEdit.setLineWidth(newWidth);
		shapeEditor.edit(shapeToEdit);
	}

	@FXML
	private void handleEditFillColor() {
		if (!attributes.isVisible() || shapeToEdit.getShapeDimension() != ShapeDimension.SHAPE2D)
			return;
		String color = String.format("#%02X%02X%02X", (int) (editFillColor.getValue().getRed() * 255),
				(int) (editFillColor.getValue().getGreen() * 255), (int) (editFillColor.getValue().getBlue() * 255));
		((Shape2D) shapeToEdit).setFillColor(color);
		shapeEditor.edit(shapeToEdit);
	}

	@FXML
	private void handleEditStrokeColor() {
		if (!attributes.isVisible())
			return;
		String color = String.format("#%02X%02X%02X", (int) (editStrokeColor.getValue().getRed() * 255),
				(int) (editStrokeColor.getValue().getGreen() * 255),
				(int) (editStrokeColor.getValue().getBlue() * 255));
		shapeToEdit.setStrokeColor(color);
		shapeEditor.edit(shapeToEdit);
	}

	@FXML
	private void handleEditText() {
		if (!attributes.isVisible())
			return;
		if (editText.getText() == null || editText.getText() == "") {
			editText.setText(((Text) shapeToEdit).getText());
			return;
		}
		String editedText = editText.getText();
		((Text) shapeToEdit).setText(editedText);
		shapeEditor.edit(shapeToEdit);
	}

	@FXML
	private void handleEditX() {
		if (!attributes.isVisible())
			return;
		if (editX.getText() == null || editX.getText().trim().isEmpty()) {
			editX.setText(String.valueOf((int) Math.round(shapeToEdit.getUpLeftCorner().getX())));
			return;
		}
		int newX = Integer.parseInt(editX.getText());
		shapeToEdit.setUpLeftCornerX(newX);
		shapeEditor.edit(shapeToEdit);
	}

	@FXML
	private void handleEditY() {
		if (!attributes.isVisible())
			return;
		if (editY.getText() == null || editY.getText().trim().isEmpty()) {
			editY.setText(String.valueOf((int) Math.round(shapeToEdit.getUpLeftCorner().getY())));
			return;
		}
		int newY = Integer.parseInt(editY.getText());
		shapeToEdit.setUpLeftCornerY(newY);
		shapeEditor.edit(shapeToEdit);
	}

	private void handleEditZ(int newZ) {
		if (!attributes.isVisible())
			return;
		if (shapeToEdit.getZ() == newZ)
			return;
		shapeToEdit.setZ(newZ);
		shapeEditor.editZ(shapeToEdit);
		
		// Update list order and select edited shape
		updateList();
	}

	@FXML
	private void handleEditWidth() {
		if (!attributes.isVisible())
			return;
		if (editWidth.getText() == null || editWidth.getText().trim().isEmpty()) {
			editWidth.setText(String.valueOf((int) Math.round(shapeToEdit.getWidth())));
			return;
		}
		int newWidth = Integer.parseInt(editWidth.getText());
		shapeToEdit.setWidth(newWidth);
		shapeEditor.edit(shapeToEdit);
	}

	@FXML
	private void handleEditHeight() {
		if (!attributes.isVisible())
			return;
		if (editHeight.getText() == null || editHeight.getText().trim().isEmpty()) {
			editHeight.setText(String.valueOf((int) Math.round(shapeToEdit.getHeight())));
			return;
		}
		int newHeight = Integer.parseInt(editHeight.getText());
		shapeToEdit.setHeight(newHeight);
		shapeEditor.edit(shapeToEdit);
	}

	@FXML
	private void handleRotate() {
		if (!attributes.isVisible())
			return;
		if (rotate.getText() == null || rotate.getText().trim().isEmpty()) {
			rotate.setText(String.valueOf((int) Math.round(shapeToEdit.getRotation())));
			return;
		}
		int newRotation = Integer.parseInt(rotate.getText());
		shapeToEdit.setRotation(newRotation);
		shapeEditor.edit(shapeToEdit);
	}

	@Override
	public void update(ObservableList obs) {
		switch (obs) {
		case SHAPE_ADDED:
			updateList();
			selectLastItem(true);
			isGrouping = false;
			break;
		case SHAPES_LOADED:
		case SHAPE_REMOVED:
			updateList();
			break;
		case UNSELECT_SHAPE:
			selectLastItem(false);
			break;
		default:
			break;
		}
	}

	private void updateList() {
		if (tree.getRoot() == null) {
			tree.setRoot(new TreeItem<Shape>());
			tree.setShowRoot(false);
		}
		
		tree.getRoot().getChildren().clear();
		buildTree(tree.getRoot(), shapesDict.getShapesList());
	}
	
	private void buildTree(TreeItem<Shape> root, List<Shape> shapes) {
		List<Shape> shallowCopy = shapes.subList(0, shapes.size());
		Collections.sort(shallowCopy, new Comparator<Shape>() {
			@Override
			public int compare(Shape s1, Shape s2) {
				return s2.getZ() - s1.getZ();
			}
		});
		
		List<TreeItem<Shape>> children = root.getChildren();
		for (Shape shape : shapes) {
			TreeItem<Shape> item = new TreeItem<Shape>(shape);
			if (shape.getShapeType() == ShapeType.GROUP)
				buildTree(item, ((ShapesGroup) shape).getShapes());
			children.add(item);
		}
	}

	private void selectLastItem(boolean shouldSelect) {
		if (!tree.getRoot().getChildren().isEmpty() && shouldSelect) {
			if (isGrouping) {
				Shape lastGroupCreated = shapesDict.getLastCreatedShape();
				//tree.getSelectionModel().select(lastGroupCreated);		
			} else {
				tree.getSelectionModel().select(tree.getRoot().getChildren().get(0));				
			}
		} else {
			tree.getSelectionModel().select(null);
		}
	}

	private void handleTextAddClick() {
		drawSettings.setShape(ShapeType.TEXT);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cpaintus/views/popup/AddText.fxml"));
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
			LOGGER.log(Level.INFO, "Error while opening the file ", e);
		}

	}
	
	@FXML
	private void handleSelectClick() {
		isGrouping = true;
		selectShapesSingleton.notifyAllObservers();
	}

	@FXML
	private void handleUnSelectClick() {
		selectShapesSingleton.setSelectedShape(tree.getSelectionModel().getSelectedItem().getValue());
		selectShapesSingleton.notifyUngroupObservers();
		attributes.setVisible(false);
	}
}
