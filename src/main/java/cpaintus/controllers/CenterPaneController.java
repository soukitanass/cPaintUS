package cpaintus.controllers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import cpaintus.controllers.command.DrawCommand;
import cpaintus.controllers.command.EraseAllCommand;
import cpaintus.controllers.command.GroupCommand;
import cpaintus.controllers.command.Invoker;
import cpaintus.controllers.command.UngroupCommand;
import cpaintus.controllers.drawers.DrawerStrategyContext;
import cpaintus.models.BoundingBox;
import cpaintus.models.DrawSettings;
import cpaintus.models.Point;
import cpaintus.models.Pointer;
import cpaintus.models.ZoomSingleton;
import cpaintus.models.composite.ShapesGroup;
import cpaintus.models.observable.IObserver;
import cpaintus.models.observable.ObservableList;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapeFactory;
import cpaintus.models.shapes.ShapeType;
import cpaintus.models.shapes.ShapesDictionnary;
import cpaintus.models.shapes.Size;
import cpaintus.models.shapes.Stroke;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class CenterPaneController implements IObserver {

	@FXML
	private Canvas baseCanvas;

	@FXML
	private Canvas boundingBoxCanvas;

	@FXML
	private Canvas snapshotCanvas;

	@FXML
	private Canvas hideCanvas;
	
	@FXML
	private AnchorPane pane;

	@FXML
	private AnchorPane visualPane;

	@FXML
	private ScrollPane scrollPane;

	private Pointer pointer;
	private BoundingBox boundingBox;
	private DrawSettings drawSettings;
	private ShapesDictionnary shapesDict;
	private DrawerStrategyContext drawerStrategyContext;
	private SelectShapesSingleton selectShapesSingleton;
	private ZoomSingleton zoomSingleton;
	private Invoker invoker;

	private boolean hasBeenDragged;
	private boolean selectShapes;

	private EventHandler<MouseEvent> mousePressedEventHandler;

	private EventHandler<MouseEvent> mouseReleasedEventHandler;

	public CenterPaneController() {
		drawerStrategyContext = DrawerStrategyContext.getInstance();
		drawerStrategyContext.register(this);
		shapesDict = ShapesDictionnary.getInstance();
		shapesDict.register(this);
		pointer = Pointer.getInstance();
		boundingBox = BoundingBox.getInstance();
		boundingBox.register(this);
		drawSettings = DrawSettings.getInstance();
		shapesDict = ShapesDictionnary.getInstance();
		shapesDict.register(this);
		SnapshotSingleton.getInstance().register(this);
		selectShapesSingleton = SelectShapesSingleton.getInstance();
		selectShapesSingleton.register(this);
		zoomSingleton = ZoomSingleton.getInstance();
		zoomSingleton.register(this);
		invoker = Invoker.getInstance();

		hasBeenDragged = false;
		selectShapes = false;

		mousePressedEventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				boundingBox.setFollowGrid(true);
				boundingBox.setOrigin(e.getX() / zoomSingleton.getZoomRatio(), e.getY() / zoomSingleton.getZoomRatio());
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
					removeLastAddedCanvas();
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

		scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);

		pane.setStyle("-fx-background-color: white");
		scrollPane.setStyle("-fx-background: #FFFFFF");
		// configuration of the mouse events
		snapshotCanvas.addEventFilter(MouseEvent.MOUSE_PRESSED, mousePressedEventHandler);
		snapshotCanvas.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseReleasedEventHandler);

		boundingBoxCanvas.setMouseTransparent(true);
		SnapshotSingleton.getInstance().setSnapshotPane(pane);
		drawGrid();
		scrollPane.widthProperty().addListener((obs, oldVal, newVal) -> {
			drawSnapshot();
		});
		scrollPane.heightProperty().addListener((obs, oldVal, newVal) -> {
			drawSnapshot();
		});
		snapshotCanvas.getGraphicsContext2D().scale(zoomSingleton.getDynamicZoom(), zoomSingleton.getDynamicZoom());
		drawSnapshot();
	}

	private void drawHidePane() {
		GraphicsContext gc = hideCanvas.getGraphicsContext2D();
		gc.setFill(Color.LIGHTGRAY);
		gc.fillRect(0, 0, hideCanvas.getWidth(), hideCanvas.getHeight());
	}

	@Override
	public void update(ObservableList obs) {
		switch (obs) {
		case SHAPES_LOADED:
			eraseCanvas();
			refresh();
			break;
		case LOAD_IMAGE:
			loadImage();
			drawSnapshot();
			break;
		case MENU_ERASE:
			eraseAll();
			break;
		case BOUNDING_BOX:
			drawBoundingBox();
			break;
		case GRID:
			drawGrid();
			break;
		case GROUP_SHAPES:
			selectShapes = true;
			break;
		case UNGROUP_SHAPES:
			if (selectShapesSingleton.getSelectedShape().getShapeType() == ShapeType.GROUP)
				unselectShapes((ShapesGroup) selectShapesSingleton.getSelectedShape());
			break;
		case ZOOM:
			snapshotCanvas.getGraphicsContext2D().scale(zoomSingleton.getDynamicZoom(), zoomSingleton.getDynamicZoom());
			drawSnapshot();
			drawBoundingBox();
			break;
		case DRAW:
			drawSnapshot();
			break;
		case SHAPE_REMOVED:
			drawSnapshot();
			break;
		case Z_EDIT:
			drawSnapshot();
			break;
		default:
			break;
		}
	}

	private void unselectShapes(ShapesGroup group) {
		selectShapes = false;
		UngroupCommand ungroupCommand = new UngroupCommand();
		ungroupCommand.setShapesGroup(group);
		invoker.execute(ungroupCommand);
	}

	@FXML
	public void eraseAll() {
		EraseAllCommand eraseAllCommand = new EraseAllCommand();
		eraseAllCommand.setPane(pane);
		invoker.execute(eraseAllCommand);
		drawSnapshot();
	}

	@FXML
	private void onMouseMoved(MouseEvent event) {
		pointer.setCursorPoint(event.getX() / zoomSingleton.getZoomRatio(), event.getY() / zoomSingleton.getZoomRatio());
	}

	@FXML
	private void onMouseDragged(MouseEvent event) {
		hasBeenDragged = true;
		pointer.setCursorPoint(event.getX() / zoomSingleton.getZoomRatio(),
				event.getY() / zoomSingleton.getZoomRatio());
		boundingBox.updateBoundingBox(pointer.getCursorPoint());
		if (!selectShapes) {
			draw(false);
		} else {
			drawBoundingBox();
		}
	}

	private void scrollPaneWidthHandler(double width) {
		double tempWidth = getMinimumBaseCanvasSize().getWidth();
		if (tempWidth > width)
			width = tempWidth;

		baseCanvas.setWidth(width);
		drawGrid();
	}

	private void scrollPaneHeightHandler(double height) {
		double tempHeight = getMinimumBaseCanvasSize().getHeight();
		if (tempHeight > height)
			height = tempHeight;

		baseCanvas.setHeight(height);
		drawGrid();
	}

	private Size getMinimumBaseCanvasSize() {
		double width = 0;
		double height = 0;

		List<Shape> shapes = shapesDict.getFullShapesList();
		double tempHeight;
		double tempWidth;
		for (Shape currentShape : shapes) {
			tempHeight = currentShape.getUpLeftCorner().getY() + currentShape.getHeight();
			if (tempHeight > height) {
				height = tempHeight;
			}
			tempWidth = currentShape.getUpLeftCorner().getX() + currentShape.getWidth();
			if (tempWidth > width) {
				width = tempWidth;
			}
		}

		return new Size(width, height);
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
		baseCanvas.setWidth(scrollPane.getWidth());
		baseCanvas.setHeight(scrollPane.getHeight());
		boundingBox.setVisible(false);
	}

	private void initializeNewCanvas() {
		Canvas newCanvas = new Canvas();
		newCanvas.setMouseTransparent(true);
		newCanvas.setBlendMode(BlendMode.SRC_OVER);
		pane.getChildren().add(pane.getChildren().size() - 1, newCanvas);
	}

	private void updateBaseCanvasAndGrid(Shape shape) {
		double newWidth = shape.getUpLeftCorner().getX() + shape.getWidth();
		double newHeight = shape.getUpLeftCorner().getY() + shape.getHeight();
		if (baseCanvas.getWidth() < newWidth || baseCanvas.getHeight() < newHeight) {
			if (baseCanvas.getWidth() < newWidth)
				baseCanvas.setWidth(newWidth);
			if (baseCanvas.getHeight() < newHeight)
				baseCanvas.setHeight(newHeight);
			drawGrid();
		}
	}

	public void draw(boolean persistent) {
		if (persistent) {
			applyDraw();
		} else {
			tempDraw();
			drawBoundingBox();
		}
	}

	private void applyDraw() {
		Canvas activeCanvas = (Canvas) pane.getChildren().get(pane.getChildren().size() - 2);
		Shape shape = createShape(true, activeCanvas.hashCode());
		if (shape != null) {
			updateBaseCanvasAndGrid(shape);
			pane.getChildren().remove(pane.getChildren().size() - 2);
			DrawCommand drawCommand = new DrawCommand(pane, shape);
			invoker.execute(drawCommand);
			drawerStrategyContext.draw(shape, activeCanvas);
		}
	}

	private void tempDraw() {
		Canvas activeCanvas = (Canvas) pane.getChildren().get(pane.getChildren().size() - 2);
		Shape shape = createShape(false, activeCanvas.hashCode());
		if (shape != null) {
			drawerStrategyContext.draw(shape, activeCanvas);
		}
	}
	
	public void drawSnapshot() {
		snapshotCanvas.setWidth(0);
		snapshotCanvas.setHeight(0);
		hideCanvas.setWidth(0);
		hideCanvas.setHeight(0);

		BufferedImage image = SwingFXUtils.fromFXImage(pane.snapshot(new SnapshotParameters(), null), null);

		hideCanvas.setWidth(pane.getWidth());
		hideCanvas.setHeight(pane.getHeight());
		drawHidePane();

		snapshotCanvas.setWidth(image.getWidth() * this.zoomSingleton.getZoomRatio());
		snapshotCanvas.setHeight(image.getHeight() * this.zoomSingleton.getZoomRatio());

		GraphicsContext gc = snapshotCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, snapshotCanvas.getWidth(), snapshotCanvas.getHeight());
		gc.drawImage(SwingFXUtils.toFXImage(image, null), 0, 0);
	}

	/*
	 * The bounding box position and size are the shape sizes, therefore it must be
	 * drawn around that.
	 */
	private void drawBoundingBox() {
		double width = boundingBox.getWidth() * zoomSingleton.getZoomRatio();
		double height = boundingBox.getHeight() * zoomSingleton.getZoomRatio();
		boundingBoxCanvas.setLayoutX(boundingBox.getUpLeftCorner().getX() * zoomSingleton.getZoomRatio() - 4);
		boundingBoxCanvas.setLayoutY(boundingBox.getUpLeftCorner().getY() * zoomSingleton.getZoomRatio() - 4);
		boundingBoxCanvas.setWidth(width + 8);
		boundingBoxCanvas.setHeight(height + 8);

		GraphicsContext gc = boundingBoxCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, boundingBoxCanvas.getWidth(), boundingBoxCanvas.getHeight());
		
		if (boundingBox.isVisible()) {
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(3);
			gc.strokeRect(2, 2, width + 4, height + 4);

			// Gray lines making an X in the center
			gc.setLineWidth(1);
			gc.setStroke(Color.GRAY);
			gc.strokeLine(3, 3, width + 5, height + 5);
			gc.strokeLine(width + 5, 3, 3, height + 5);

			gc.setStroke(Color.WHITE);
			gc.setLineWidth(2);
			gc.setLineDashes(5);
			gc.strokeRect(2, 2, width + 4, height + 4);
		}

		boundingBoxCanvas.setRotate(boundingBox.getRotation());
	}

	private Shape createShape(boolean persistent, int canvasHash) {
		if (!hasBeenDragged && boundingBox.getWidth() + boundingBox.getHeight() == 0 && pane.getChildren().size() > 2) {
			removeLastAddedCanvas();
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
			newShape = ShapeFactory.getShape(shapeType, persistent, canvasHash,
					new Point(boundingBox.getOrigin().getX(), boundingBox.getOrigin().getY()),
					new Point(boundingBox.getOppositeCorner().getX(), boundingBox.getOppositeCorner().getY()),
					new Size(boundingBox.getWidth(), boundingBox.getHeight()), 0, new Stroke(lineWidth, sstrokeColor),
					sfillColor, "", text);
		} else if (shapeType == ShapeType.TEXT && boundingBox.getWidth() + boundingBox.getHeight() == 0) {
			newShape = null;
		} else {
			newShape = ShapeFactory.getShape(shapeType, persistent, canvasHash,
					new Point(boundingBox.getUpLeftCorner().getX(), boundingBox.getUpLeftCorner().getY()),
					new Point(boundingBox.getOppositeCorner().getX(), boundingBox.getOppositeCorner().getY()),
					new Size(boundingBox.getWidth(), boundingBox.getHeight()), 0, new Stroke(lineWidth, sstrokeColor),
					sfillColor, "", text);
		}

		return newShape;
	}

	private void removeLastAddedCanvas() {
		pane.getChildren().remove(pane.getChildren().size() - 2);
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
		GroupCommand groupCommand = new GroupCommand();
		groupCommand.setFirst(true);
		invoker.execute(groupCommand);
		selectShapes = false;
		boundingBox.setFollowGrid(false);
	}

	private void drawGrid() {
		GraphicsContext gc = this.baseCanvas.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, this.baseCanvas.getWidth(), this.baseCanvas.getHeight());
		if (!this.boundingBox.getGridMod())
			return;
		gc.setStroke(Color.LIGHTGRAY);
		gc.setLineWidth(1);
		for (int i = 0; i < this.baseCanvas.getWidth(); i = i + (int) this.boundingBox.getGridStep()) {
			gc.strokeLine(i, 0, i, this.baseCanvas.getHeight());
		}
		for (int i = 0; i < this.baseCanvas.getHeight(); i = i + (int) this.boundingBox.getGridStep()) {
			gc.strokeLine(0, i, this.baseCanvas.getWidth(), i);
		}
		drawSnapshot();
	}
}
