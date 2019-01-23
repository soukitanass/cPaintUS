package cPaintUS.controllers;

import java.util.ArrayList;
import java.util.List;

import cPaintUS.models.BoundingBox;
import cPaintUS.models.Pointer;
import cPaintUS.models.DrawSettings;
import cPaintUS.models.observable.IObserver;
import cPaintUS.models.observable.ObservableList;
import cPaintUS.models.shapes.Ellipse;
import cPaintUS.models.shapes.Heart;
import cPaintUS.models.shapes.Pokeball;
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
import javafx.scene.shape.ArcType;

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
	private DrawSettings drawSettings;
	private ShapeFactory shapeFactory;
	private ShapesDict shapesDict;

	private boolean hasBeenDragged;

	private EventHandler<MouseEvent> mousePressedEventHandler;

	private EventHandler<MouseEvent> mouseReleasedEventHandler;

	public CenterPaneController() {
		pointer = Pointer.getInstance();
		boundingBox = BoundingBox.getInstance();
		drawSettings = DrawSettings.getInstance();
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
		gc.setFill(drawSettings.getFillColor());
		gc.setStroke(drawSettings.getStrokeColor());
		gc.setLineWidth(drawSettings.getLineWidth());
	}

	private void drawPokeball(GraphicsContext gc) {
		double ratioBetweenCircles = 0.25;
		double halfOfRatio = ratioBetweenCircles/2;
		double startingCornerRatio = 0.5 - halfOfRatio;
		double endingCornerRatio = 0.5 + halfOfRatio;
		
		// Colored top of the ball
		gc.fillArc(boundingBox.getUpLeftCorner().getX(),
				boundingBox.getUpLeftCorner().getY(),
				boundingBox.getWidth(),
				boundingBox.getHeight(),
				0, 180, ArcType.ROUND);

		gc.setFill(Color.WHITE);
		// White bottom of the ball
		gc.fillArc(boundingBox.getUpLeftCorner().getX(),
				boundingBox.getUpLeftCorner().getY(),
				boundingBox.getWidth(),
				boundingBox.getHeight(),
				0, -180, ArcType.ROUND);
		// White circle in center
		gc.fillOval(boundingBox.getUpLeftCorner().getX() + boundingBox.getWidth() * startingCornerRatio,
				boundingBox.getUpLeftCorner().getY() + boundingBox.getHeight() * startingCornerRatio,
				boundingBox.getWidth() * ratioBetweenCircles,
				boundingBox.getHeight() * ratioBetweenCircles);
		// Biggest circle
		gc.strokeOval(boundingBox.getUpLeftCorner().getX(), boundingBox.getUpLeftCorner().getY(),
				boundingBox.getWidth(), boundingBox.getHeight());
		// Smallest circle
		gc.strokeOval(boundingBox.getUpLeftCorner().getX() + boundingBox.getWidth() * startingCornerRatio,
				boundingBox.getUpLeftCorner().getY() + boundingBox.getHeight() * startingCornerRatio,
				boundingBox.getWidth() * ratioBetweenCircles,
				boundingBox.getHeight() * ratioBetweenCircles);
		// Two lines on the sides
		gc.strokeLine(boundingBox.getUpLeftCorner().getX(),
				boundingBox.getUpLeftCorner().getY() + boundingBox.getHeight() * 0.5,
				boundingBox.getUpLeftCorner().getX() + boundingBox.getWidth() * startingCornerRatio,
				boundingBox.getUpLeftCorner().getY() + boundingBox.getHeight() * 0.5);
		gc.strokeLine(boundingBox.getUpLeftCorner().getX() + boundingBox.getWidth() * endingCornerRatio,
				boundingBox.getUpLeftCorner().getY() + boundingBox.getHeight() * 0.5,
				boundingBox.getUpLeftCorner().getX() + boundingBox.getWidth(),
				boundingBox.getUpLeftCorner().getY() + boundingBox.getHeight() * 0.5);
	}

	private void drawPokeball(GraphicsContext gc,Shape shape) {
		double ratioBetweenCircles = 0.25;
		double halfOfRatio = ratioBetweenCircles/2;
		double startingCornerRatio = 0.5 - halfOfRatio;
		double endingCornerRatio = 0.5 + halfOfRatio;
		
		// Colored top of the ball
		gc.fillArc(shape.getX(),
				shape.getY(),
				shape.getWidth(),
				shape.getHeight(),
				0, 180, ArcType.ROUND);

		gc.setFill(Color.WHITE);
		// White bottom of the ball
		gc.fillArc(shape.getX(),
				shape.getY(),
				shape.getWidth(),
				shape.getHeight(),
				0, -180, ArcType.ROUND);
		// White circle in center
		gc.fillOval(shape.getX() + shape.getWidth() * startingCornerRatio,
				shape.getY() + shape.getHeight() * startingCornerRatio,
				shape.getWidth() * ratioBetweenCircles,
				shape.getHeight() * ratioBetweenCircles);
		// Biggest circle
		gc.strokeOval(shape.getX(), shape.getY(),
				shape.getWidth(), shape.getHeight());
		// Smallest circle
		gc.strokeOval(shape.getX() + shape.getWidth() * startingCornerRatio,
				shape.getY() + shape.getHeight() * startingCornerRatio,
				shape.getWidth() * ratioBetweenCircles,
				shape.getHeight() * ratioBetweenCircles);
		// Two lines on the sides
		gc.strokeLine(shape.getX(),
				shape.getY() + shape.getHeight() * 0.5,
				shape.getX() + shape.getWidth() * startingCornerRatio,
				shape.getY() + shape.getHeight() * 0.5);
		gc.strokeLine(shape.getX() + shape.getWidth() * endingCornerRatio,
				shape.getY() + shape.getHeight() * 0.5,
				shape.getX() + shape.getWidth(),
				shape.getY() + shape.getHeight() * 0.5);
	}

	private void drawShape() {
		GraphicsContext gc;
		activeCanvas = ((Canvas) pane.getChildren().get(pane.getChildren().size() - 2));
		gc = activeCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, activeCanvas.getWidth(), activeCanvas.getHeight());

		drawSettings(gc);
		
		switch (drawSettings.getShape()) {
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
		case Pokeball:
			drawPokeball(gc);
			break;
		case Heart:
			drawHeart(gc, boundingBox.getUpLeftCorner().getX(), boundingBox.getUpLeftCorner().getY(), boundingBox.getWidth(), boundingBox.getHeight());
			break;
		default:
			break;
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
		ShapeType shapeType = drawSettings.getShape();
		int lineWidth = drawSettings.getLineWidth();
		Color fillColor = drawSettings.getFillColor();
		Color strokeColor = drawSettings.getStrokeColor();

		String sfillColor = String.format("#%02X%02X%02X", (int) (fillColor.getRed() * 255),
				(int) (fillColor.getGreen() * 255), (int) (fillColor.getBlue() * 255));
		String sstrokeColor = String.format("#%02X%02X%02X", (int) (strokeColor.getRed() * 255),
				(int) (strokeColor.getGreen() * 255), (int) (strokeColor.getBlue() * 255));
		if (shapeType == ShapeType.Line) {
			newShape = shapeFactory.getShape(shapeType, activeCanvas.hashCode(), boundingBox.getOrigin().getX(),
					boundingBox.getOrigin().getY(), boundingBox.getOppositeCorner().getX(),
					boundingBox.getOppositeCorner().getY(), lineWidth, sstrokeColor, sfillColor);
		} else {
			newShape = shapeFactory.getShape(shapeType, activeCanvas.hashCode(), boundingBox.getUpLeftCorner().getX(),
					boundingBox.getUpLeftCorner().getY(), boundingBox.getWidth(), boundingBox.getHeight(), lineWidth,
					sstrokeColor, sfillColor);
		}

		if(newShape != null) {
		shapesDict.addShape(newShape);
		System.out.println(newShape.getShapeId());
		}else {
			System.out.println("Create shape : Unknown shape");
		}
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
		case Pokeball:
			gc.setFill(Color.web(((Pokeball) shape).getFillColor()));
			drawPokeball(gc,shape);
			break;
		case Heart:
			gc.setFill(Color.web(((Heart) shape).getFillColor()));
			drawHeart(gc,(Heart)shape);
			break;
		default:
			break;
		}
	}

	@Override
	public void update(ObservableList obs) {
		switch (obs) {
		case SHAPES_LOAD:
			eraseCanvas();
			refresh();
			break;
		default:
			break;
		}
	}
	
	private void calculShape(double x, double y, double w, double h, double[] xAxis, double[] yTop, double[] yBottom, int size) {
		int j = 0;
		for(double i = -w/2 ; i <= 0; i+=0.5 ) {
			yTop[j] = -h * Math.sqrt(1 - ((Math.abs(4*i/w)-1)*(Math.abs(4*i/w)-1))) /4;
			yBottom[j] = h * 3 * Math.sqrt(1- (Math.sqrt(Math.abs(4*i/w)) / Math.sqrt(2)) ) / 4;
			yTop[j] = yTop[j] + y + h/4;
			yBottom[j] = yBottom[j] + y + h/4;
			xAxis[j] = i+w/2 + x;
			if(i != 0) {
				yTop[size-1-j] = yTop[j];
				yBottom[size-1-j] = yBottom[j];
				xAxis[size-1-j] = w-(i+(w/2)) + x;
			}
			j++;
		}
	}
	
	private void drawStrokeHeart(GraphicsContext gc, double x, double y, double w, double h) {
		int size = 2*(int)w+1;
		double[] yTop = new double[size];
		double[] yBottom = new double[size];
		double[] xAxis = new double[size];
		calculShape(x, y, w, h, xAxis, yTop, yBottom, size);
		gc.setStroke(drawSettings.getStrokeColor());
		gc.setLineWidth(drawSettings.getLineWidth());
		gc.strokePolyline(xAxis, yTop, size);
		gc.strokePolyline(xAxis, yBottom, size);
	}
	
	private void drawStrokeHeart(GraphicsContext gc, Heart heart) {
		int size = 2*(int)heart.getWidth()+1;
		double[] yTop = new double[size];
		double[] yBottom = new double[size];
		double[] xAxis = new double[size];
		calculShape(heart.getX(), heart.getY(), heart.getWidth(), heart.getHeight(), xAxis, yTop, yBottom, size);
		gc.setStroke(Color.web(heart.getStrokeColor()));
		gc.setLineWidth(heart.getLineWidth());
		gc.strokePolyline(xAxis, yTop, size);
		gc.strokePolyline(xAxis, yBottom, size);
	}
	
	private void drawHeart(GraphicsContext gc, double x, double y, double w, double h) {
		drawFillHeart(gc, x, y, w, h);
		drawStrokeHeart(gc, x, y, w, h);
	}
	
	private void drawHeart(GraphicsContext gc, Heart heart) {
		drawFillHeart(gc, heart);
		drawStrokeHeart(gc, heart);
	}
	
	private void drawFillHeart(GraphicsContext gc, double x, double y, double w, double h) {

		int size = 2*(int)w+1;
		double[] yTop = new double[size];
		double[] yBottom = new double[size];
		double[] xAxis = new double[size];
		calculShape(x, y, w, h, xAxis, yTop, yBottom, size);
		for(int i = 0 ; i<size; i++) {
			gc.setStroke(drawSettings.getFillColor());
			gc.setLineWidth(1);
			gc.strokeLine(xAxis[i], yTop[i], xAxis[i], yBottom[i]);
		}
	}
	
	private void drawFillHeart(GraphicsContext gc, Heart heart) {

		int size = 2*(int)heart.getWidth()+1;
		double[] yTop = new double[size];
		double[] yBottom = new double[size];
		double[] xAxis = new double[size];
		calculShape(heart.getX(), heart.getY(), heart.getWidth(), heart.getHeight(), xAxis, yTop, yBottom, size);
		for(int i = 0 ; i<size; i++) {
			gc.setStroke(Color.web(heart.getFillColor()));
			gc.setLineWidth(1);
			gc.strokeLine(xAxis[i], yTop[i], xAxis[i], yBottom[i]);
		}
	}
}
