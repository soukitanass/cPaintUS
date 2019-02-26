package cpaintus.controllers;

import cpaintus.models.BoundingBox;
import cpaintus.models.ZoomSingleton;
import cpaintus.models.observable.IObserver;
import cpaintus.models.observable.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class CenterContainerController implements IObserver {

	@FXML
	private Canvas leftRuler;

	@FXML
	private Canvas topRuler;

	@FXML
	private GridPane gridPane;

	@FXML
	private ScrollPane leftScroll;

	@FXML
	private ScrollPane topScroll;

	private final int smallLine = 8;
	private final int middleLine = 12;
	private final int bigLine = 16;
	private final int nbDivisions = 4;
	private final int labelPxTreshold = 10;
	private double zoom;
	private double demiPixel;
	private BoundingBox boundingBox;
	private ZoomSingleton zoomSingleton;

	public CenterContainerController() {
		boundingBox = BoundingBox.getInstance();
		zoomSingleton = ZoomSingleton.getInstance();
		zoomSingleton.register(this);
		zoom = 1;
		demiPixel = 0.5;
	}

	@FXML
	public void initialize() {
		gridPane.setStyle("-fx-focus-color: #C8C7C5;");
		gridPane.widthProperty().addListener((obs, oldValue, newValue) -> {
			topRuler.setWidth(newValue.doubleValue() - 50);
			drawRulers();
		});
		gridPane.heightProperty().addListener((obs, oldValue, newValue) -> {
			leftRuler.setHeight(newValue.doubleValue() - 50);
			drawRulers();
		});
		drawRulers();
	}

	private void drawRulers() {
		zoom = zoomSingleton.getZoomRatio();
		demiPixel = 0.5 * zoom;
		drawTopRuler();
	}

	private void drawTopRuler() {
		int w = (int) topRuler.getWidth();
		int h = (int) topRuler.getHeight();
		double bigSteps = boundingBox.getRulerStep() * zoom;
		double smallSteps = bigSteps / nbDivisions;

		GraphicsContext gc = topRuler.getGraphicsContext2D();
		gc.clearRect(0, 0, w, h);
		gc.setLineWidth(1);

		drawTopLines(gc, w, h, bigSteps, smallSteps);
		drawTopNumbers(gc, w, h, bigSteps, smallSteps);
	}

	private void drawTopLines(GraphicsContext gc, int w, int h, double bigSteps, double smallSteps) {
		int line;
		int currentNumber;
		boolean numberLabelIsTooBig = bigSteps < labelPxTreshold;
		for (double i = 0; i < w; i += smallSteps) {
			if (i != 0) {
				if (i % bigSteps == 0) {
					currentNumber = (int) (i / bigSteps);
					line = shouldDisplayNumber(numberLabelIsTooBig, currentNumber) ? bigLine : middleLine;
					gc.setStroke(Color.GRAY);
					gc.strokeLine(i - demiPixel, h, i - demiPixel, h - line);
				} else if (i % smallSteps == 0) {
					gc.setStroke(Color.DARKGRAY);
					gc.strokeLine(i - demiPixel, h, i - demiPixel, h - smallLine);
				}
			}
		}
	}

	private void drawTopNumbers(GraphicsContext gc, int w, int h, double bigSteps, double smallSteps) {
		boolean numberLabelIsTooBig = bigSteps < labelPxTreshold;
		String currentNumberLabel;
		int currentNumber;
		gc.setStroke(Color.GRAY);
		gc.save();
		gc.rotate(-90);
		for (double i = 0; i < w; i += smallSteps) {
			if (i != 0) {
				if (i % bigSteps == 0) {
					gc.translate(0, bigSteps);
					currentNumber = (int) (i / bigSteps);
					if (shouldDisplayNumber(numberLabelIsTooBig, currentNumber)) {
						currentNumberLabel = String.valueOf(currentNumber);
						gc.fillText(currentNumberLabel, -h + bigLine + 4, 3.5);
					}
				}
			}
		}
		gc.restore();
	}

	private boolean shouldDisplayNumber(boolean tooBig, int currentNumber) {
		return !(tooBig && currentNumber % 2 != 0);
	}

	@Override
	public void update(ObservableList obs) {
		switch (obs) {
		case ZOOM:
			drawRulers();
			break;
		default:
			break;
		}
	}
}
