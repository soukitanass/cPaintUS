package cPaintUS.controllers;

import java.util.ArrayList;
import java.util.List;

import cPaintUS.models.BoundingBox;
import cPaintUS.models.Pointer;
import cPaintUS.models.observable.IObserver;
import cPaintUS.models.observable.ObservableList;
import cPaintUS.models.shapes.Ellipse;
import cPaintUS.models.shapes.Rectangle;
import cPaintUS.models.shapes.Shape;
import cPaintUS.models.shapes.ShapeFactory;
import cPaintUS.models.shapes.ShapeType;
import cPaintUS.models.shapes.ShapesDict;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

	private Canvas activeCanvas;

	private Pointer pointer;
	private BoundingBox boundingBox;
	private ShapeFactory shapeFactory;
	private ShapesDict shapesDict;

	private ShapeType shape = ShapeType.Line;
	private Color fillColor = Color.BLACK;
	private Color strokeColor = Color.BLACK;
	private int lineWidth = 1;

	private boolean hasBeenDragged;

	private EventHandler<MouseEvent> mousePressedEventHandler;

	private EventHandler<MouseEvent> mouseReleasedEventHandler;

	public CenterPaneController() {
		pointer = Pointer.getInstance();
		boundingBox = BoundingBox.getInstance();
		shapeFactory = ShapeFactory.getInstance();
		shapesDict = ShapesDict.getInstance();
		shapesDict.register(this);

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
				draw();

				if (boundingBox.getWidth() + boundingBox.getHeight() == 0 && pane.getChildren().size() > 2) {
					pane.getChildren().remove(pane.getChildren().size() - 2);
				} else {
					createShape();
				}

				System.out.println("Mouse released : " + pane.getChildren().size() + " canevas");
			}
		};
	}

	public void setShape(ShapeType shape) {
		this.shape = shape;
	}

	public void setFillColor(Color color) {
		this.fillColor = color;
	}

	public Canvas getBaseCanvas() {
		return baseCanvas;
	}

	public void setBaseCanvas(Canvas baseCanvas) {
		this.baseCanvas = baseCanvas;
	}

	public AnchorPane getPane() {
		return pane;
	}

	public void setPane(AnchorPane pane) {
		this.pane = pane;
	}

	public Canvas getActiveCanvas() {
		return activeCanvas;
	}

	public void setActiveCanvas(Canvas activeCanvas) {
		this.activeCanvas = activeCanvas;
	}

	public void setStrokeColor(Color color) {
		this.strokeColor = color;
	}

	public void setLineWidth(int width) {
		this.lineWidth = width;
	}

	@FXML
	public void initialize() {
		// configuration of the mouse events
		baseCanvas.addEventFilter(MouseEvent.MOUSE_PRESSED, mousePressedEventHandler);
		baseCanvas.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseReleasedEventHandler);
		boundingBoxCanvas.setMouseTransparent(true);
	}

	@FXML
	public void eraseAll() {
		eraseCanvas();

		shapesDict.clearShapes();
	}

	private void eraseCanvas() {
		List<Node> canvasList = pane.getChildren();
		List<Node> canvasToRemove = new ArrayList<Node>();

		for (int i = 1; i < canvasList.size() - 1; i++) {
			canvasToRemove.add(canvasList.get(i));
		}

		// Erase bounding box
		GraphicsContext bdgc = boundingBoxCanvas.getGraphicsContext2D();
		bdgc.clearRect(0, 0, boundingBoxCanvas.getWidth(), boundingBoxCanvas.getHeight());

		for (Node canvas : canvasToRemove) {
			canvasList.remove(canvas);
		}
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
		draw();
	}

	private void initializeNewCanvas() {
		Canvas newCanvas = new Canvas();
		newCanvas.setHeight(1000.0);
		newCanvas.setWidth(1000.0);
		newCanvas.setMouseTransparent(true);
		pane.getChildren().add(pane.getChildren().size() - 1, newCanvas);
	}

	private void drawSettings(GraphicsContext gc) {
		gc.setFill(fillColor);
		gc.setStroke(strokeColor);
		gc.setLineWidth(lineWidth);
	}

	private void drawShape() {
		if (true) { // TODO has a shape selected
			GraphicsContext gc;
			activeCanvas = ((Canvas) pane.getChildren().get(pane.getChildren().size() - 2));
			gc = activeCanvas.getGraphicsContext2D();
			gc.clearRect(0, 0, activeCanvas.getWidth(), activeCanvas.getHeight());

			drawSettings(gc);
			// getCurrentShape (Rectangle, circle...) TODO once the toolbar is done
			switch (this.shape) {
			case Rectangle:
				gc.fillRect(boundingBox.getUpLeftCorner().getX(), boundingBox.getUpLeftCorner().getY(),
						boundingBox.getWidth(), boundingBox.getHeight());
				gc.strokeRect(boundingBox.getUpLeftCorner().getX(), boundingBox.getUpLeftCorner().getY(),
						boundingBox.getWidth(), boundingBox.getHeight());
				break;
			case Ellipse:
				gc.fillOval(boundingBox.getUpLeftCorner().getX(), boundingBox.getUpLeftCorner().getY(),
						boundingBox.getWidth(), boundingBox.getHeight());
				gc.strokeOval(boundingBox.getUpLeftCorner().getX(), boundingBox.getUpLeftCorner().getY(),
						boundingBox.getWidth(), boundingBox.getHeight());
				break;
			case Line:
				gc.strokeLine(boundingBox.getOrigin().getX(), boundingBox.getOrigin().getY(),
						boundingBox.getOppositeCorner().getX(), boundingBox.getOppositeCorner().getY());
				break;
			default:
				break;
			}
		}
	}

	private void drawBoundingBox() {
		GraphicsContext gc = boundingBoxCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, boundingBoxCanvas.getWidth(), boundingBoxCanvas.getHeight());

		if (boundingBox.isVisible()) {
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(3);
			gc.strokeRect(boundingBox.getUpLeftCorner().getX(), boundingBox.getUpLeftCorner().getY(),
					boundingBox.getWidth(), boundingBox.getHeight());
			gc.setStroke(Color.WHITE);
			gc.setLineWidth(2);
			gc.setLineDashes(5);
			gc.strokeRect(boundingBox.getUpLeftCorner().getX(), boundingBox.getUpLeftCorner().getY(),
					boundingBox.getWidth(), boundingBox.getHeight());
		}
	}

	private void draw() {
		drawShape();
		drawBoundingBox();
	}

	private void createShape() {
		Shape newShape;
		if(shape == ShapeType.Line) {
			newShape = shapeFactory.getShape(shape, activeCanvas.hashCode(), boundingBox.getOrigin().getX(),
					boundingBox.getOrigin().getY(), boundingBox.getOppositeCorner().getX(), boundingBox.getOppositeCorner().getY(), lineWidth,
					String.format("#%02X%02X%02X", ((int) strokeColor.getRed()) * 255, ((int) strokeColor.getGreen()) * 255,
							((int) strokeColor.getBlue()) * 255),
					String.format("#%02X%02X%02X", ((int) fillColor.getRed()) * 255, ((int) fillColor.getGreen()) * 255,
							((int) fillColor.getBlue()) * 255));
		}else {
			newShape = shapeFactory.getShape(shape, activeCanvas.hashCode(), boundingBox.getUpLeftCorner().getX(),
					boundingBox.getUpLeftCorner().getY(), boundingBox.getWidth(), boundingBox.getHeight(), lineWidth,
					String.format("#%02X%02X%02X", ((int) strokeColor.getRed()) * 255, ((int) strokeColor.getGreen()) * 255,
							((int) strokeColor.getBlue()) * 255),
					String.format("#%02X%02X%02X", ((int) fillColor.getRed()) * 255, ((int) fillColor.getGreen()) * 255,
							((int) fillColor.getBlue()) * 255));
		}
		
		shapesDict.addShape(newShape);
		System.out.println(newShape.getShapeId());
	}

	public void refresh() {
		for (Shape shape : shapesDict.getShapesList()) {
			initializeNewCanvas();
			drawShape(shape);
		}
	}

	private void drawShape(Shape shape) {
		GraphicsContext gc;
		activeCanvas = ((Canvas) pane.getChildren().get(pane.getChildren().size() - 2));
		gc = activeCanvas.getGraphicsContext2D();

		gc.setStroke(Color.web(shape.getStrokeColor()));
		gc.setLineWidth(shape.getLineWidth());
		switch (shape.getShapeType()) {
		case Rectangle:
			gc.setFill(Color.web(((Rectangle) shape).getFillColor()));
			gc.fillRect(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
			gc.strokeRect(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
			break;
		case Ellipse:
			gc.setFill(Color.web(((Ellipse) shape).getFillColor()));
			gc.fillOval(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
			gc.strokeOval(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
			break;
		case Line:
			gc.strokeLine(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
			break;
		default:
			break;
		}
	}

	@Override
	public void update(ObservableList obs) {
		eraseCanvas();
		refresh();
		
	}
}
