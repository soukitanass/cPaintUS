package cpaintus.controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
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
		leftRuler.getGraphicsContext2D().setFill(Color.PURPLE);
		leftRuler.getGraphicsContext2D().fillRect(0, 0, leftRuler.getWidth(), leftRuler.getHeight());
		;
		topRuler.getGraphicsContext2D().setFill(Color.PURPLE);
		topRuler.getGraphicsContext2D().fillRect(0, 0, topRuler.getWidth(), topRuler.getHeight());
		;
	}
}
