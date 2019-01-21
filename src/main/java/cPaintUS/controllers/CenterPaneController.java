package cPaintUS.controllers;

import java.util.ArrayList;
import java.util.List;

import cPaintUS.models.BoundingBox;
import cPaintUS.models.Pointer;
import cPaintUS.models.shapes.ShapeType;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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

	private Pointer pointer;
	private BoundingBox boundingBox;
	
	private boolean hasBeenDragged;
	
	private EventHandler<MouseEvent> mousePressedEventHandler;

	private EventHandler<MouseEvent> mouseReleasedEventHandler;

	public CenterPaneController() {
		pointer = Pointer.getInstance();
		boundingBox = BoundingBox.getInstance();
		hasBeenDragged = false;
		
		mousePressedEventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				System.out.println("Mouse pressed on " + e.getX() + ";" + e.getY());
				boundingBox.setOrigin(e.getX(), e.getY());
				boundingBox.setVisible(true);
			}
		};
		mouseReleasedEventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if(hasBeenDragged == true) {
					hasBeenDragged = false;
				}
				else {
					boundingBox.setVisible(false);
				}
				boundingBox.updateBoundingBox(pointer.getCursorPoint());
				System.out.println("Mouse released on " + e.getX() + ";" + e.getY());
				draw();
			}
		};
	}

	@FXML
	public void initialize() {
 		//configuration of the mouse events
		baseCanvas.addEventFilter(MouseEvent.MOUSE_PRESSED, mousePressedEventHandler);
		baseCanvas.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseReleasedEventHandler);
		boundingBoxCanvas.setMouseTransparent(true);
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
	
	private void drawSettings(GraphicsContext gc) {
		gc.setFill(Color.GREEN);
		gc.setStroke(Color.BLUE);
		gc.setLineWidth(5);
	}
	
	private void drawShape() {		
		if (true && !hasBeenDragged) { // TODO has a shape selected
			Canvas newCanvas = new Canvas();
			newCanvas.setHeight(1000.0);
			newCanvas.setWidth(1000.0);
			newCanvas.setMouseTransparent(true);
			
			pane.getChildren().add(pane.getChildren().size() - 1, newCanvas);
			
			System.out.println("New canvas created: " + pane.getChildren().size());
			
			GraphicsContext gc = newCanvas.getGraphicsContext2D();
			drawSettings(gc);
			// getCurrentShape (Rectangle, circle...) TODO once the toolbar is done
			switch (ShapeType.Rectangle) {
				case Rectangle: 
					gc.fillRect(boundingBox.getOrigin().getX(), boundingBox.getOrigin().getY(), boundingBox.getWidth(), boundingBox.getHeight());
					break;
				case Circle:
					break;
				case Ellipse:
					break;
				case Line: 
					break;
				default: break;
			}
		}
	}

	private void drawBoundingBox() {
		GraphicsContext gc = boundingBoxCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, boundingBoxCanvas.getWidth(), boundingBoxCanvas.getHeight());
		
		if(boundingBox.isVisible()) {
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(3);
			gc.strokeRect(boundingBox.getOrigin().getX(), boundingBox.getOrigin().getY(), boundingBox.getWidth(), boundingBox.getHeight());
			gc.setStroke(Color.WHITE);
			gc.setLineWidth(2);
			gc.setLineDashes(5);
			gc.strokeRect(boundingBox.getOrigin().getX(), boundingBox.getOrigin().getY(), boundingBox.getWidth(), boundingBox.getHeight());			
		}
	}
	
	private void draw() {
		System.out.println("test draw" + boundingBox.getOrigin().getX() + ";" + boundingBox.getOrigin().getY() + ";" + boundingBox.getWidth() + ";" + boundingBox.getHeight());

		drawShape();
		drawBoundingBox();
		/*
		gc.strokeLine(40, 10, 10, 40);
		gc.fillOval(10, 60, 30, 30);
		gc.strokeOval(60, 60, 30, 30);
		gc.fillRoundRect(110, 60, 30, 30, 10, 10);
		gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
		gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
		gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
		gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
		gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
		gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
		gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
		gc.fillPolygon(new double[] { 10, 40, 10, 40 }, new double[] { 210, 210, 240, 240 }, 4);
		gc.strokePolygon(new double[] { 60, 90, 60, 90 }, new double[] { 210, 210, 240, 240 }, 4);
		gc.strokePolyline(new double[] { 110, 140, 110, 140 }, new double[] { 210, 210, 240, 240 }, 4);
		 */
	}

	public Canvas getCanvas() {
		return this.baseCanvas;
	}

}
