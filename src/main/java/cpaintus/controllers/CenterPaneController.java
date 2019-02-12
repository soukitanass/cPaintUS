package cpaintus.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import cpaintus.controllers.drawers.DrawerStrategyContext;
import cpaintus.models.BoundingBox;
import cpaintus.models.DrawSettings;
import cpaintus.models.Point;
import cpaintus.models.Pointer;
import cpaintus.models.composite.ShapesGroup;
import cpaintus.models.observable.IObserver;
import cpaintus.models.observable.ObservableList;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapeEditor;
import cpaintus.models.shapes.ShapeFactory;
import cpaintus.models.shapes.ShapeType;
import cpaintus.models.shapes.ShapesDictionnary;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class CenterPaneController implements IObserver {

	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	@FXML
	private Canvas baseCanvas;

	@FXML
	private Canvas boundingBoxCanvas;

	@FXML
	private AnchorPane pane;

	@FXML
	private ScrollPane scrollPane;

	private Pointer pointer;
	private BoundingBox boundingBox;
	private DrawSettings drawSettings;
	private ShapeFactory shapeFactory;
	private ShapesDictionnary shapesDict;
	private DrawerStrategyContext drawerStrategyContext;
	private ShapeEditor shapeEditor;
	private SelectShapesSingleton selectShapesSingleton;
	private boolean hasBeenDragged;
	private boolean selectShapes;
	private ShapesGroup shapesGroup;

	private EventHandler<MouseEvent> mousePressedEventHandler;

	private EventHandler<MouseEvent> mouseReleasedEventHandler;

	public CenterPaneController() {
		drawerStrategyContext = DrawerStrategyContext.getInstance();
		pointer = Pointer.getInstance();
		boundingBox = BoundingBox.getInstance();
		boundingBox.register(this);
		drawSettings = DrawSettings.getInstance();
		shapeFactory = ShapeFactory.getInstance();
		shapesDict = ShapesDictionnary.getInstance();
		shapesDict.register(this);
		SnapshotSingleton.getInstance().register(this);
		shapeEditor = ShapeEditor.getInstance();
		shapeEditor.register(this);
		selectShapesSingleton = SelectShapesSingleton.getInstance();
		selectShapesSingleton.register(this);

		hasBeenDragged = false;
		selectShapes = false;

		mousePressedEventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				boundingBox.setOrigin(e.getX(), e.getY());
				boundingBox.setVisible(true);
				boundingBox.setRotation(0);
				initializeNewCanvas();
			}
		};
		mouseReleasedEventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (hasBeenDragged) {
					hasBeenDragged = false;
				} else {
					boundingBox.setVisible(false);
					selectShapesSingleton.notifyUnselectShapeObservers();
				}
				boundingBox.updateBoundingBox(pointer.getCursorPoint());
				if (!selectShapes) {
					draw(true);
				} else {
					selectShapes();
					boundingBox.setVisible(true);
				}

			}
		};
	}

	@FXML
	public void initialize() {
		scrollPane.widthProperty().addListener((obs, oldVal, newVal) -> scrollPaneWidthHandler(newVal.doubleValue()));

		scrollPane.heightProperty().addListener((obs, oldVal, newVal) -> scrollPaneHeightHandler(newVal.doubleValue()));

		pane.setStyle("-fx-background-color: white");
		scrollPane.setStyle("-fx-background: #FFFFFF");
		// configuration of the mouse events
		baseCanvas.addEventFilter(MouseEvent.MOUSE_PRESSED, mousePressedEventHandler);
		baseCanvas.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseReleasedEventHandler);
		boundingBoxCanvas.setMouseTransparent(true);
		SnapshotSingleton.getInstance().setSnapshotPane(pane);
	}

	@Override
	public void update(ObservableList obs) {
		switch (obs) {
		case SHAPES_LOADED:
			eraseCanvas();
			refresh();
			break;
		case EDIT_SHAPE:
			editShape(shapeEditor.getShapeToEdit());
			shapeEditor.done();
			break;
		case LOAD_IMAGE:
			loadImage();
			break;
		case MENU_ERASE:
			eraseAll();
			break;
		case BOUNDING_BOX:
			drawBoundingBox();
			break;
		case GROUP_SHAPES:
			selectShapes = true;
			break;
		case UNGROUP_SHAPES:
			unselectShapes();
			break;
		default:
			break;
		}
	}

	private void unselectShapes() {
		selectShapes = false;
		shapesDict.removeShapeByType(ShapeType.GROUP);
		boundingBox.setVisible(false);
		eraseCanvas();
		refresh();
	}

	@FXML
	public void eraseAll() {
		eraseCanvas();
		shapesDict.clearShapes();
	}

	@FXML
	private void onMouseMoved(MouseEvent event) {
		pointer.setCursorPoint(event.getX(), event.getY());
	}

	@FXML
	private void onMouseDragged(MouseEvent event) {
		hasBeenDragged = true;
		pointer.setCursorPoint(event.getX(), event.getY());
		boundingBox.updateBoundingBox(pointer.getCursorPoint());
		if (!selectShapes) {
			draw(false);
		} else {
			drawBoundingBox();
		}

	}

	private void scrollPaneWidthHandler(double width) {
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		baseCanvas.setWidth(width);
		if (pane.getWidth() > width + 10) {
			scrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		}
		drawBoundingBox();
	}

	private void scrollPaneHeightHandler(double height) {
		scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		baseCanvas.setHeight(height);
		if (pane.getHeight() > height + 10) {
			scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		}
		drawBoundingBox();
	}

	private void eraseCanvas() {
		List<Node> canvasList = pane.getChildren();
		List<Node> canvasToRemove = new ArrayList<>();

		for (int i = 1; i < canvasList.size() - 1; i++) {
			canvasToRemove.add(canvasList.get(i));
		}

		for (Node canvas : canvasToRemove) {
			canvasList.remove(canvas);
		}

		boundingBox.setVisible(false);
		scrollPaneWidthHandler(pane.getWidth());
		scrollPaneHeightHandler(pane.getHeight());
	}

	private void initializeNewCanvas() {
		Canvas newCanvas = new Canvas();
		newCanvas.setMouseTransparent(true);
		newCanvas.setBlendMode(BlendMode.SRC_OVER);
		pane.getChildren().add(pane.getChildren().size() - 1, newCanvas);
	}

	private void editShape(Shape shape) {
		Canvas canvas;
		int hash;
		if (shape == null) {
			LOGGER.log(Level.INFO, "No shape to edit. Aborting edit.");
			return;
		}
		if (shape.getShapeType() == ShapeType.GROUP) {
			for (Shape sh : ((ShapesGroup) shape).getShapes()) {
				editShape(sh);
			}
		}
		hash = shape.getCanvasHash();
		canvas = (Canvas) pane.getChildren().stream().filter(child -> hash == child.hashCode()).findAny().orElse(null);
		if (canvas == null) {
			LOGGER.log(Level.INFO, "No shape to edit. Aborting edit.");
			return;
		}

		if (shapeEditor.edittingZ())
			editShapeZ(shape.getZ(), canvas);
		else
			drawerStrategyContext.draw(shape, canvas);
	}

	private void editShapeZ(int z, Node node) {
		List<Node> nodes = pane.getChildren();
		int newZ = z;
		int prevZ = nodes.indexOf(node);

		nodes.remove(prevZ);
		nodes.add(newZ, node);

		// Z index of some shapes have changed! Edit them.
		int start;
		int end;

		if (prevZ < newZ) {
			start = prevZ;
			end = newZ;
		} else {
			start = newZ + 1;
			end = prevZ + 1;
		}

		for (int i = start; i < end; i++) {
			int hash = nodes.get(i).hashCode();
			Shape shape = shapesDict.getShapesList().stream().filter(s -> hash == s.getCanvasHash()).findAny()
					.orElse(null);
			if (shape != null)
				shape.setZ(i);
		}

	}

	public void draw(boolean persistent) {
		Canvas activeCanvas = (Canvas) pane.getChildren().get(pane.getChildren().size() - 2);
		Shape shape = createShape(persistent, activeCanvas.hashCode());
		if (shape != null) {
			drawerStrategyContext.draw(shape, activeCanvas);

		}
		drawBoundingBox();
		scrollPaneWidthHandler(scrollPane.getWidth());
		scrollPaneHeightHandler(scrollPane.getHeight());
	}

	private void drawBoundingBox() {
		boundingBoxCanvas.setLayoutX(boundingBox.getUpLeftCorner().getX() - (1 + drawSettings.getLineWidth() / 2) - 3);
		boundingBoxCanvas.setLayoutY(boundingBox.getUpLeftCorner().getY() - (1 + drawSettings.getLineWidth() / 2) - 3);
		boundingBoxCanvas.setWidth(boundingBox.getWidth() + drawSettings.getLineWidth() + 8);
		boundingBoxCanvas.setHeight(boundingBox.getHeight() + drawSettings.getLineWidth() + 8);
		GraphicsContext gc = boundingBoxCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, boundingBoxCanvas.getWidth(), boundingBoxCanvas.getHeight());
		if (boundingBox.isVisible()) {
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(3);
			gc.strokeRect(2, 2, boundingBox.getWidth() + (1 + drawSettings.getLineWidth() / 2) + 2,
					boundingBox.getHeight() + (1 + drawSettings.getLineWidth() / 2) + 2);
			gc.setLineWidth(1);
			gc.setStroke(Color.GRAY);
			gc.strokeLine(3, 3, boundingBox.getWidth() + 3, boundingBox.getHeight() + 3);
			gc.strokeLine(boundingBox.getWidth() + 3, 3, 3, boundingBox.getHeight() + 3);

			gc.setStroke(Color.WHITE);
			gc.setLineWidth(2);
			gc.setLineDashes(5);
			gc.strokeRect(2, 2, boundingBox.getWidth() + (1 + drawSettings.getLineWidth() / 2) + 2,
					boundingBox.getHeight() + (1 + drawSettings.getLineWidth() / 2) + 2);
		}
		boundingBoxCanvas.setRotate(boundingBox.getRotation());
	}

	private Shape createShape(boolean persistent, int canvasHash) {
		if (!hasBeenDragged && boundingBox.getWidth() + boundingBox.getHeight() == 0 && pane.getChildren().size() > 2) {
			pane.getChildren().remove(pane.getChildren().size() - 2);
			return null;
		}

		Shape newShape;
		ShapeType shapeType = drawSettings.getShape();
		int lineWidth = drawSettings.getLineWidth();
		Color fillColor = drawSettings.getFillColor();
		Color strokeColor = drawSettings.getStrokeColor();
		String text = drawSettings.getText();

		String sfillColor = String.format("#%02X%02X%02X", (int) (fillColor.getRed() * 255),
				(int) (fillColor.getGreen() * 255), (int) (fillColor.getBlue() * 255));
		String sstrokeColor = String.format("#%02X%02X%02X", (int) (strokeColor.getRed() * 255),
				(int) (strokeColor.getGreen() * 255), (int) (strokeColor.getBlue() * 255));

		if (shapeType == ShapeType.LINE) {
			newShape = shapeFactory.getShape(shapeType, persistent, canvasHash, boundingBox.getOrigin().getX(),
					boundingBox.getOrigin().getY(), boundingBox.getOppositeCorner().getX(),
					boundingBox.getOppositeCorner().getY(), boundingBox.getWidth(), boundingBox.getHeight(), 0,
					lineWidth, sstrokeColor, sfillColor, "", text);
		} else if (shapeType == ShapeType.TEXT && boundingBox.getWidth() + boundingBox.getHeight() == 0) {
			newShape = null;
		} else {
			newShape = shapeFactory.getShape(shapeType, persistent, canvasHash, boundingBox.getUpLeftCorner().getX(),
					boundingBox.getUpLeftCorner().getY(), boundingBox.getOppositeCorner().getX(),
					boundingBox.getOppositeCorner().getY(), boundingBox.getWidth(), boundingBox.getHeight(), 0,
					lineWidth, sstrokeColor, sfillColor, "", text);
		}

		if (newShape != null && persistent) {
			shapesDict.addShape(newShape);
		}

		return newShape;
	}

	public void refresh() {
		for (Shape shape : shapesDict.getShapesList()) {
			initializeNewCanvas();
			drawerStrategyContext.draw(shape, (Canvas) pane.getChildren().get(pane.getChildren().size() - 2));
		}
	}

	private void loadImage() {
		initializeNewCanvas();
		Shape picture = SnapshotSingleton.getInstance().getPicture();
		Canvas canvas = (Canvas) pane.getChildren().get(pane.getChildren().size() - 2);
		picture.setCanvasHash(canvas.hashCode());
		drawerStrategyContext.draw(picture, canvas);
	}

	private void selectShapes() {

		shapesGroup = new ShapesGroup();
		shapesGroup.clear();
		shapesGroup.setXGroup(boundingBox.getUpLeftCorner().getX());
		shapesGroup.setYGroup(boundingBox.getUpLeftCorner().getY());
		shapesGroup.setHeightGroup(boundingBox.getHeight());
		shapesGroup.setWidthGroup(boundingBox.getWidth());
		shapesGroup.setCanvasHash(boundingBox.hashCode());
		for (Shape shape : shapesDict.getShapesList()) {
			if (comparePoints(new Point(shape.getX(), shape.getY()), boundingBox.getUpLeftCorner())
					&& shape.getX() + shape.getWidth() <= boundingBox.getUpLeftCorner().getX() + boundingBox.getWidth()
					&& shape.getY() >= boundingBox.getUpLeftCorner().getY()
					&& shape.getX() + shape.getWidth() <= boundingBox.getUpLeftCorner().getX() + boundingBox.getWidth()
					&& shape.getY() + shape.getHeight() <= boundingBox.getUpLeftCorner().getY()
							+ boundingBox.getHeight()) {
				shapesGroup.add(shape);
			}
		}
		if (!shapesGroup.getShapes().isEmpty()) {
			shapesDict.addShape(shapesGroup);
		}
		selectShapes = false;
	}

	private boolean comparePoints(Point a, Point b) {
		return (a.getX() >= b.getX() && a.getY() >= b.getY());

	}
}
