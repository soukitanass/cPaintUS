package cpaintus.controllers;

import cpaintus.models.BoundingBox;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class CenterContainerController {

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
	private final int bigLine = 16;
	private final int nbDivisions = 4;
	private BoundingBox boundingBox;

	public CenterContainerController() {
		boundingBox = BoundingBox.getInstance();
	}

	@FXML
	public void initialize() {
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
		int w = (int) topRuler.getWidth();
		int h = (int) topRuler.getHeight();
		int bigSteps = boundingBox.getRulerStep();
		int smallSteps = bigSteps / nbDivisions;
		GraphicsContext gc = topRuler.getGraphicsContext2D();
		gc.setStroke(Color.GRAY);
		gc.setLineWidth(1);
		for (int i = 0; i < w; i += smallSteps) {
			if (i != 0) {
				if (i % bigSteps == 0) {
					gc.strokeLine(i, h, i, h - bigLine);
				} else if (i % smallSteps == 0) {
					gc.strokeLine(i, h, i, h - smallLine);
				}
			}
		}
	}
}
