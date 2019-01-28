package cPaintUS.controllers;

import java.util.ArrayList;
import java.util.List;

import cPaintUS.controllers.drawers.DrawerStrategyContext;
import cPaintUS.models.BoundingBox;
import cPaintUS.models.Pointer;
import cPaintUS.models.DrawSettings;
import cPaintUS.models.observable.IObserver;
import cPaintUS.models.observable.ObservableList;
import cPaintUS.models.shapes.Shape;
import cPaintUS.models.shapes.ShapeFactory;
import cPaintUS.models.shapes.ShapeType;
import cPaintUS.models.shapes.ShapesDict;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class CenterPaneController implements IObserver {

	@FXML
	private Canvas baseCanvas;

	@FXML
	private Canvas boundingBoxCanvas;

	@FXML
	private AnchorPane pane;
	
	@FXML
	private ScrollPane scrollPane;
	
	private Canvas activeCanvas;

	private Pointer pointer;
	private BoundingBox boundingBox;
	private DrawSettings drawSettings;
	private ShapeFactory shapeFactory;
	private ShapesDict shapesDict;
	private DrawerStrategyContext drawerStrategyContext;

	private boolean hasBeenDragged;

	private EventHandler<MouseEvent> mousePressedEventHandler;

	private EventHandler<MouseEvent> mouseReleasedEventHandler;

	public CenterPaneController() {
		drawerStrategyContext = DrawerStrategyContext.getInstance();
		pointer = Pointer.getInstance();
		boundingBox = BoundingBox.getInstance();
		boundingBox.register(this);
		drawSettings = DrawSettings.getInstance();
		shapeFactory = ShapeFactory.getInstance();
		shapesDict = ShapesDict.getInstance();
		shapesDict.register(this);
		SnapshotSingleton.getInstance().register(this);

		hasBeenDragged = false;

		mousePressedEventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				System.out.println("Mouse pressed : " + pane.getChildren().size() + " canevas");
				boundingBox.setOrigin(e.getX(), e.getY());
				boundingBox.setVisible(true);
				initializeNewCanvas();
			}
		};
		mouseReleasedEventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (hasBeenDragged == true) {
					hasBeenDragged = false;
				} else {
					boundingBox.setVisible(false);
				}
				boundingBox.updateBoundingBox(pointer.getCursorPoint());
				draw(true);
				System.out.println("Mouse released : " + pane.getChildren().size() + " canevas");
			}
		};
	}

	@FXML
	public void initialize() {
		scrollPane.widthProperty().addListener((obs, oldVal, newVal) -> {
			scrollPaneWidthHandler(newVal.doubleValue());
		});

		scrollPane.heightProperty().addListener((obs, oldVal, newVal) -> {
			scrollPaneHeightHandler(newVal.doubleValue());
		});

		baseCanvas.setBlendMode(BlendMode.SRC_OVER);
		boundingBoxCanvas.setBlendMode(BlendMode.SRC_OVER);
		// configuration of the mouse events
		baseCanvas.addEventFilter(MouseEvent.MOUSE_PRESSED, mousePressedEventHandler);
		baseCanvas.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseReleasedEventHandler);
		boundingBoxCanvas.setMouseTransparent(true);
		SnapshotSingleton.getInstance().setSnapshotPane(pane);
	}

	@Override
	public void update(ObservableList obs) {
		switch (obs) {
		case SHAPES_UPDATED:
			eraseCanvas();
			refresh();
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
		default:
			break;
		}
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
		draw(false);
	}

	private void scrollPaneWidthHandler(double width) {
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		baseCanvas.setWidth(width);
		boundingBoxCanvas.setWidth(width);
		if(pane.getWidth() > width) {
			scrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		}
		drawBoundingBox();
	}
	
	private void scrollPaneHeightHandler(double height) {
		scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		baseCanvas.setHeight(height);
		boundingBoxCanvas.setHeight(height);
		if(pane.getHeight() > height) {
			scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		}
		drawBoundingBox();
	}

	private void eraseCanvas() {
		List<Node> canvasList = pane.getChildren();
		List<Node> canvasToRemove = new ArrayList<Node>();

		for (int i = 1; i < canvasList.size() - 1; i++) {
			canvasToRemove.add(canvasList.get(i));
		}

		for (Node canvas : canvasToRemove) {
			canvasList.remove(canvas);
		}
		
		boundingBox.setVisible(false);
	}

	private void initializeNewCanvas() {
		Canvas newCanvas = new Canvas();
		newCanvas.setMouseTransparent(true);
		newCanvas.setBlendMode(BlendMode.SRC_OVER);
		pane.getChildren().add(pane.getChildren().size() - 1, newCanvas);
	}

	private void draw(boolean persistent) {
		activeCanvas = (Canvas) pane.getChildren().get(pane.getChildren().size() - 2);
		Shape shape = createShape(persistent);
		if(shape != null) {
			drawerStrategyContext.draw(shape, activeCanvas);
		}
		drawBoundingBox();
		scrollPaneWidthHandler(scrollPane.getWidth());
		scrollPaneHeightHandler(scrollPane.getHeight());
	}
	
	private void drawBoundingBox() {
		GraphicsContext gc = boundingBoxCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, boundingBoxCanvas.getWidth(), boundingBoxCanvas.getHeight());

		if (boundingBox.isVisible()) {
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(3);
			gc.strokeRect(boundingBox.getUpLeftCorner().getX() - (1 + drawSettings.getLineWidth() / 2), boundingBox.getUpLeftCorner().getY() - (1 + drawSettings.getLineWidth() / 2),
					boundingBox.getWidth() + 2 + drawSettings.getLineWidth(), boundingBox.getHeight() + 2 + drawSettings.getLineWidth());
			gc.setStroke(Color.WHITE);
			gc.setLineWidth(2);
			gc.setLineDashes(5);
			gc.strokeRect(boundingBox.getUpLeftCorner().getX() - (1 + drawSettings.getLineWidth() / 2), boundingBox.getUpLeftCorner().getY() - (1 + drawSettings.getLineWidth() / 2),
					boundingBox.getWidth() + 2 + drawSettings.getLineWidth(), boundingBox.getHeight() + 2 + drawSettings.getLineWidth());
		}
	}

	private Shape createShape(boolean persistent) {
		if (!hasBeenDragged && boundingBox.getWidth() + boundingBox.getHeight() == 0 && pane.getChildren().size() > 2) {
			pane.getChildren().remove(pane.getChildren().size() - 2);
			return null;
		} 
		
		Shape newShape;
		ShapeType shapeType = drawSettings.getShape();
		int lineWidth = drawSettings.getLineWidth();
		Color fillColor = drawSettings.getFillColor();
		Color strokeColor = drawSettings.getStrokeColor();

		String sfillColor = String.format("#%02X%02X%02X", (int) (fillColor.getRed() * 255),
				(int) (fillColor.getGreen() * 255), (int) (fillColor.getBlue() * 255));
		String sstrokeColor = String.format("#%02X%02X%02X", (int) (strokeColor.getRed() * 255),
				(int) (strokeColor.getGreen() * 255), (int) (strokeColor.getBlue() * 255));
		
		if (shapeType == ShapeType.Line) {
			newShape = shapeFactory.getShape(shapeType, persistent, activeCanvas.hashCode(), boundingBox.getOrigin().getX(),
					boundingBox.getOrigin().getY(), boundingBox.getOppositeCorner().getX(),
					boundingBox.getOppositeCorner().getY(), lineWidth, sstrokeColor, sfillColor);
		} else {
			newShape = shapeFactory.getShape(shapeType, persistent, activeCanvas.hashCode(), boundingBox.getUpLeftCorner().getX(),
					boundingBox.getUpLeftCorner().getY(), boundingBox.getWidth(), boundingBox.getHeight(), lineWidth,
					sstrokeColor, sfillColor);
		}
		
		if(newShape != null && persistent) {
			shapesDict.addShape(newShape);
			System.out.println(newShape.getShapeId());
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
		drawerStrategyContext.draw(SnapshotSingleton.getInstance().getPicture(), (Canvas) pane.getChildren().get(pane.getChildren().size() - 2));
	}
	
}
