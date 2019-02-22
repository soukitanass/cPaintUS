package cpaintus.controllers;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import cpaintus.controllers.command.Command;
import cpaintus.controllers.command.EditCommand;
import cpaintus.controllers.command.EditGroupCommand;
import cpaintus.controllers.command.EditZCommand;
import cpaintus.controllers.command.EraseShapeCommand;
import cpaintus.controllers.command.Invoker;
import cpaintus.controllers.popup.AddTextController;
import cpaintus.models.BoundingBox;
import cpaintus.models.DrawSettings;
import cpaintus.models.LineWidth;
import cpaintus.models.Point;
import cpaintus.models.composite.ShapesGroup;
import cpaintus.models.observable.IObserver;
import cpaintus.models.observable.ObservableList;
import cpaintus.models.shapes.Line;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.Shape2D;
import cpaintus.models.shapes.ShapeDimension;
import cpaintus.models.shapes.ShapeType;
import cpaintus.models.shapes.ShapesDictionnary;
import cpaintus.models.shapes.Text;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

public class LeftPaneController implements IObserver {

	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private DrawSettings drawSettings;
	private ShapesDictionnary shapesDict;
	private Shape shapeToEdit;
	private BoundingBox boundingBox;
	private Preferences prefs;
	private SelectShapesSingleton selectShapesSingleton;
	private ChangeListener<Integer> editZListener;
	private Invoker invoker;
	private ChangeListener<TreeItem<Shape>> selectShapeListener;
	private ListChangeListener<Command> addedActionListener;
	private ChangeListener<Command> selectCommandListener;
	private boolean isGrouping;
	private Command commandToUndoUntil;
	private boolean isUpdatingAttributes;

	@FXML
	private ComboBox<ShapeType> shapeType;
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
	private Button undoUntilBtn;
	@FXML
	private Button unselectBtn;
	@FXML
	private Button deleteBtn;
	@FXML
	private Button topBtn;
	@FXML
	private Button rightBtn;
	@FXML
	private Button buttomBtn;
	@FXML
	private Button leftBtn;
	@FXML
	private TreeView<Shape> tree;
	@FXML
	private ListView<Command> commandes;
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
		invoker = Invoker.getInstance();
		invoker.register(this);
		shapesDict = ShapesDictionnary.getInstance();
		shapesDict.register(this);
		drawSettings = DrawSettings.getInstance();
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
				if (newItem == null) {
					return;
				}

				handleSelectShape(newItem.getValue());

			}
		};

		addedActionListener = new ListChangeListener<Command>() {
			@Override
			public void onChanged(Change<? extends Command> c) {
				while (c.next()) {
					updateListCommand();
				}
			}
		};

		selectCommandListener = new ChangeListener<Command>() {
			@Override
			public void changed(ObservableValue<? extends Command> observable, Command oldValue, Command newValue) {
				System.out.println("Selectionnée :" + newValue);
				commandToUndoUntil = newValue;
			}
		};

		isGrouping = false;

	}

	@FXML
	private void initialize() {
		// Shape settings
		shapeType.getItems().setAll(ShapeType.values());
		shapeType.getItems().remove(ShapeType.PICTURE);
		shapeType.getItems().remove(ShapeType.GROUP);
		shapeType.setValue(ShapeType.valueOf(prefs.get("shape", "LINE")));

		// Line width setting
		lineWidth.getItems().setAll(LineWidth.getInstance().getStrings());
		lineWidth.setValue(prefs.get("linewidth", LineWidth.getInstance().getDefaultString()));

		// Color settings
		fillColor.setDisable(shapeType.getValue() == ShapeType.LINE);
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
		deleteBtn.managedProperty().bind(deleteBtn.visibleProperty());
		topBtn.managedProperty().bind(topBtn.visibleProperty());
		rightBtn.managedProperty().bind(rightBtn.visibleProperty());
		buttomBtn.managedProperty().bind(buttomBtn.visibleProperty());
		leftBtn.managedProperty().bind(leftBtn.visibleProperty());
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
		Invoker.getInstance().getCommands().addListener(addedActionListener);
		commandes.getSelectionModel().selectedItemProperty().addListener(selectCommandListener);

		// Set CellFactory ListView
		commandes.setCellFactory(new Callback<ListView<Command>, ListCell<Command>>() {
			@Override
			public ListCell<Command> call(ListView<Command> lv) {
				return new CommandListCell();
			}
		});
	}

	@FXML
	private void handleChangeShape() {
		drawSettings.setShape(shapeType.getValue());
		fillColor.setDisable(shapeType.getValue() == ShapeType.LINE);
		if (shapeType.getValue() == ShapeType.TEXT) {
			handleTextAddClick();
		}
		prefs.put("shape", shapeType.getValue().name());
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
	private void handleSelectClick() {
		isGrouping = true;
		selectShapesSingleton.notifyAllObservers();
	}

	@FXML
	private void handleEraseAllClick() {
		SnapshotSingleton.getInstance().eraseAll();
	}

	private void handleSelectShape(Shape newShape) {
		isUpdatingAttributes = true;

		boundingBox.setVisible(newShape != null);
		if (newShape == null) {
			attributes.setVisible(false);
			return;
		}
		attributes.setVisible(true);
		shapeToEdit = newShape;

		// Always shown attributes
		attributesLabel.setText(newShape.getShapeId() + " Attributes:");
		attributesLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
		editX.setText(String.valueOf((int) Math.round(newShape.getUpLeftCorner().getX())));
		editY.setText(String.valueOf((int) Math.round(newShape.getUpLeftCorner().getY())));

		// Attributes shown on specific ShapeTypes
		boolean isGroup = newShape.getShapeType() == ShapeType.GROUP;
		boolean showFillColor = !isGroup && newShape.getShapeDimension() != ShapeDimension.SHAPE1D;
		boolean showText = !isGroup && newShape.getShapeType() == ShapeType.TEXT;

		// Set the attributes visibility
		unselectBtn.setVisible(isGroup);
		deleteBtn.setVisible(!isGroup);
		topBtn.setVisible(isGroup);
		rightBtn.setVisible(isGroup);
		buttomBtn.setVisible(isGroup);
		leftBtn.setVisible(isGroup);
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
			SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
					shapesDict.getFullShapesList().size(), shapeToEdit.getZ(), 1);
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

		isUpdatingAttributes = false;
		updateBoundingBox(newShape);
	}
	
	@FXML
	private void handleUnSelectClick() {
		selectShapesSingleton.setSelectedShape(shapeToEdit);
		selectShapesSingleton.notifyUngroupObservers();
		attributes.setVisible(false);
	}

	@FXML
	private void handleEditLineWidth() {
		if (isUpdatingAttributes)
			return;
		// Extract the integer in the string
		String widthStr = editLineWidth.getValue().replaceAll("[^0-9]", "");
		int newWidth = Integer.parseInt(widthStr);
		EditCommand editCommand = new EditCommand();
		Shape oldShape = shapeToEdit.makeCopy();
		editCommand.setOldShape(oldShape);
		shapeToEdit.setLineWidth(newWidth);
		editCommand.setShapeToEdit(shapeToEdit);
		invoker.execute(editCommand);

	}

	@FXML
	private void handleEditFillColor() {
		if (isUpdatingAttributes || shapeToEdit.getShapeDimension() != ShapeDimension.SHAPE2D)
			return;
		String color = String.format("#%02X%02X%02X", (int) (editFillColor.getValue().getRed() * 255),
				(int) (editFillColor.getValue().getGreen() * 255), (int) (editFillColor.getValue().getBlue() * 255));
		EditCommand editCommand = new EditCommand();
		Shape oldShape = shapeToEdit.makeCopy();
		editCommand.setOldShape(oldShape);
		((Shape2D) shapeToEdit).setFillColor(color);
		editCommand.setShapeToEdit(shapeToEdit);
		invoker.execute(editCommand);

	}

	@FXML
	private void handleEditStrokeColor() {
		if (isUpdatingAttributes)
			return;
		String color = String.format("#%02X%02X%02X", (int) (editStrokeColor.getValue().getRed() * 255),
				(int) (editStrokeColor.getValue().getGreen() * 255),
				(int) (editStrokeColor.getValue().getBlue() * 255));
		EditCommand editCommand = new EditCommand();
		Shape oldShape = shapeToEdit.makeCopy();
		editCommand.setOldShape(oldShape);
		shapeToEdit.setStrokeColor(color);
		editCommand.setShapeToEdit(shapeToEdit);
		invoker.execute(editCommand);

	}

	@FXML
	private void handleEditText() {
		if (isUpdatingAttributes)
			return;
		if (editText.getText() == null || editText.getText() == "") {
			editText.setText(((Text) shapeToEdit).getText());
			return;
		}
		String editedText = editText.getText();
		EditCommand editCommand = new EditCommand();
		Shape oldShape = shapeToEdit.makeCopy();
		editCommand.setOldShape(oldShape);
		((Text) shapeToEdit).setText(editedText);
		editCommand.setShapeToEdit(shapeToEdit);
		invoker.execute(editCommand);

	}

	@FXML
	private void handleEditX() {
		if (isUpdatingAttributes)
			return;
		if (editX.getText() == null || editX.getText().trim().isEmpty()) {
			editX.setText(String.valueOf((int) Math.round(shapeToEdit.getUpLeftCorner().getX())));
			return;
		}
		int newX = Integer.parseInt(editX.getText());
		EditCommand editCommand = new EditCommand();
		Shape oldShape = shapeToEdit.makeCopy();
		if (shapeToEdit.getShapeType() != ShapeType.GROUP) {
			editCommand.setOldShape(oldShape);
			shapeToEdit.setUpLeftCornerX(newX);
			editCommand.setShapeToEdit(shapeToEdit);
			invoker.execute(editCommand);
		} else {
			EditGroupCommand editGroupCommand = new EditGroupCommand();
			editGroupCommand.setCommandID("Edit Group :" + shapeToEdit.getShapeId());
			editGroupCommand.setOldShape(((ShapesGroup) oldShape).getShapes());
			((ShapesGroup) shapeToEdit).setX(newX);
			editGroupCommand.setShapeToEdit(((ShapesGroup) shapeToEdit).getShapes());
			invoker.execute(editGroupCommand);
			updateBoundingBox(shapeToEdit);
		}
	}

	@FXML
	private void handleEditY() {
		if (isUpdatingAttributes)
			return;
		if (editY.getText() == null || editY.getText().trim().isEmpty()) {
			editY.setText(String.valueOf((int) Math.round(shapeToEdit.getUpLeftCorner().getY())));
			return;
		}
		int newY = Integer.parseInt(editY.getText());
		EditCommand editCommand = new EditCommand();
		Shape oldShape = shapeToEdit.makeCopy();
		if (shapeToEdit.getShapeType() != ShapeType.GROUP) {
			editCommand.setOldShape(oldShape);
			shapeToEdit.setUpLeftCornerY(newY);
			editCommand.setShapeToEdit(shapeToEdit);
			invoker.execute(editCommand);
		} else {
			EditGroupCommand editGroupCommand = new EditGroupCommand();
			editGroupCommand.setCommandID("Edit Group :" + shapeToEdit.getShapeId());
			editGroupCommand.setOldShape(((ShapesGroup) oldShape).getShapes());
			((ShapesGroup) shapeToEdit).setY(newY);
			editGroupCommand.setShapeToEdit(((ShapesGroup) shapeToEdit).getShapes());
			invoker.execute(editGroupCommand);
			updateBoundingBox(shapeToEdit);

		}
	}

	private void handleEditZ(int newZ) {
		if (isUpdatingAttributes)
			return;
		if (shapeToEdit.getZ() == newZ)
			return;
		EditZCommand editZCommand = new EditZCommand();
		shapeToEdit.setZ(newZ);
		editZCommand.setNewZ(newZ);
		editZCommand.setShape(shapeToEdit);
		invoker.execute(editZCommand);
		updateList();
		selectItem(shapeToEdit);
	}

	@FXML
	private void handleEditWidth() {
		if (isUpdatingAttributes)
			return;
		if (editWidth.getText() == null || editWidth.getText().trim().isEmpty()) {
			editWidth.setText(String.valueOf((int) Math.round(shapeToEdit.getWidth())));
			return;
		}
		int newWidth = Integer.parseInt(editWidth.getText());
		EditCommand editCommand = new EditCommand();
		Shape oldShape = shapeToEdit.makeCopy();
		editCommand.setOldShape(oldShape);
		shapeToEdit.setWidth(newWidth);
		editCommand.setShapeToEdit(shapeToEdit);
		invoker.execute(editCommand);

	}

	@FXML
	private void handleEditHeight() {
		if (isUpdatingAttributes)
			return;
		if (editHeight.getText() == null || editHeight.getText().trim().isEmpty()) {
			editHeight.setText(String.valueOf((int) Math.round(shapeToEdit.getHeight())));
			return;
		}
		int newHeight = Integer.parseInt(editHeight.getText());
		EditCommand editCommand = new EditCommand();
		Shape oldShape = shapeToEdit.makeCopy();
		editCommand.setOldShape(oldShape);
		shapeToEdit.setHeight(newHeight);
		editCommand.setShapeToEdit(shapeToEdit);
		invoker.execute(editCommand);

	}

	@FXML
	private void handleRotate() {
		if (isUpdatingAttributes)
			return;
		if (rotate.getText() == null || rotate.getText().trim().isEmpty()) {
			rotate.setText(String.valueOf((int) Math.round(shapeToEdit.getRotation())));
			return;
		}
		int newRotation = Integer.parseInt(rotate.getText());
		EditCommand editCommand = new EditCommand();
		Shape oldShape = shapeToEdit.makeCopy();
		editCommand.setOldShape(oldShape);
		shapeToEdit.setRotation(newRotation);
		editCommand.setShapeToEdit(shapeToEdit);
		invoker.execute(editCommand);
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
			break;
		case SHAPE_REMOVED:
			updateList();
			break;
		case UNSELECT_SHAPE:
			selectLastItem(false);
			break;
		case CHANGEDCOMMAND:
			selectUndo();
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
		InvokerUpdateSingleton.getInstance().setTree(tree);
	}

	private void buildTree(TreeItem<Shape> root, List<Shape> shapes) {
		List<Shape> shallowCopy = shapes.subList(0, shapes.size());
		Collections.sort(shallowCopy, new Comparator<>() {
			@Override
			public int compare(Shape s1, Shape s2) {
				return s2.getZ() - s1.getZ();
			}
		});

		List<TreeItem<Shape>> children = root.getChildren();
		for (Shape shape : shapes) {
			TreeItem<Shape> item = new TreeItem<>(shape);
			if (shape.getShapeType() == ShapeType.GROUP)
				buildTree(item, ((ShapesGroup) shape).getShapes());
			children.add(item);
		}

	}

	private void selectLastItem(boolean shouldSelect) {
		if (!tree.getRoot().getChildren().isEmpty() && shouldSelect) {
			if (isGrouping) {
				ShapesGroup shapeToSelect = selectShapesSingleton.getLastCreatedGroup();
				selectItem(shapeToSelect);
			} else {
				tree.getSelectionModel().select(tree.getRoot().getChildren().get(0));
			}
		} else {
			tree.getSelectionModel().select(null);
		}
	}

	private void selectItem(Shape shapeToSelect) {
		TreeItem<Shape> itemToSelect = tree.getRoot().getChildren().stream()
				.filter(item -> shapeToSelect == item.getValue()).findAny().orElse(null);
		if (itemToSelect != null)
			tree.getSelectionModel().select(itemToSelect);
	}

	@FXML
	private void handleDeleteClick() {
		EraseShapeCommand eraseShapeCommand = new EraseShapeCommand();
		eraseShapeCommand.setShapeToDelete(tree.getSelectionModel().getSelectedItem().getValue());
		invoker.execute(eraseShapeCommand);
	}

	@FXML
	private void handleUndoUntilClick() {
		if (commandToUndoUntil != null) {
			int indexToUndo = invoker.getCommands().indexOf(commandToUndoUntil);
			if (indexToUndo < invoker.getIndex()) {
				while (indexToUndo < invoker.getIndex()) {
					invoker.undo();
				}
			} else if (indexToUndo > invoker.getIndex()) {
				while (indexToUndo > invoker.getIndex()) {
					invoker.redo();
				}
			}
		}
		commandes.getSelectionModel().select(commandToUndoUntil);
	}

	private void updateListCommand() {
		commandes.setItems(invoker.getCommands());
	}

	private void selectUndo() {
		commandes.getSelectionModel().select(invoker.getIndex());
	}

	private class CommandListCell extends ListCell<Command> {
		@Override
		protected void updateItem(Command command, boolean empty) {
			super.updateItem(command, empty);
			setText(null);
			if (!empty && command != null) {
				final String text = command.getCommandID();
				setText(text);
			}
		}
	}

	private void updateBoundingBox(Shape shape) {
		boundingBox.setOrigin(shape.getX(), shape.getY());
		boundingBox.setRotation(shape.getRotation());
		if (shape.getShapeType() == ShapeType.LINE) {
			boundingBox.updateBoundingBox(new Point(((Line) shape).getX2(), ((Line) shape).getY2()));
		} else {
			boundingBox.updateBoundingBox(new Point(shape.getX() + shape.getWidth(), shape.getY() + shape.getHeight()));
		}
	}
	
	private void shapeAlignment(Direction direction, double newVal) {
		if (isUpdatingAttributes)
			return;
		for (Shape shape : ((ShapesGroup) shapeToEdit).getShapes()) {
			EditCommand editCommand = new EditCommand();
			Shape oldShape = shape.makeCopy();
			editCommand.setOldShape(oldShape);
			switch (direction) {
			case TOP:
				shape.setY(newVal);
				break;
			case BOTTOM:
				shape.setY(newVal - shape.getHeight());
				break;
			case RIGHT:
				shape.setX(newVal - shape.getWidth());
				break;
			case LEFT:
				shape.setX(newVal);
				break;
			default:
				break;
			}
			
			editCommand.setShapeToEdit(shape);
			invoker.execute(editCommand);
		}
	}

	@FXML
	private void handleTopClick() {
		double newY = boundingBox.getUpLeftCorner().getY();
		shapeAlignment(Direction.TOP, newY);

	}

	@FXML
	private void handleRightClick() {
		double newX = boundingBox.getUpLeftCorner().getX() + boundingBox.getWidth();
		shapeAlignment(Direction.RIGHT, newX);
	}

	@FXML
	private void handleButtomClick() {
		double newY = boundingBox.getUpLeftCorner().getY() + boundingBox.getHeight();
		shapeAlignment(Direction.BOTTOM, newY);
	}

	@FXML
	private void handleLeftClick() {
		double newX = boundingBox.getUpLeftCorner().getX();
		shapeAlignment(Direction.LEFT, newX);
	}

}
