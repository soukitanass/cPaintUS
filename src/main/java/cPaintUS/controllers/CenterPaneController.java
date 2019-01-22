package cPaintUS.controllers;

import java.util.ArrayList;
import java.util.List;
import cPaintUS.models.BoundingBox;
import cPaintUS.models.Pointer;
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

public class CenterPaneController {

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
				
				if(boundingBox.getWidth() + boundingBox.getHeight() == 0 && pane.getChildren().size() > 2) {
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
		Shape newShape = shapeFactory.getShape(
				shape,
				activeCanvas.hashCode(),
				boundingBox.getUpLeftCorner().getX(),
				boundingBox.getUpLeftCorner().getY(),
				boundingBox.getWidth(),
				boundingBox.getHeight(),
				lineWidth,
				strokeColor,
				fillColor
			);
		shapesDict.addShape(newShape);
		System.out.println(newShape.getShapeId());
	}
}
