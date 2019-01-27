package cPaintUS.controllers.drawers;

import cPaintUS.models.shapes.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LineDrawerStrategy implements IDrawerStrategy {

	@Override
	public void draw(GraphicsContext gc, Shape shape) {
		gc.setStroke(Color.web(shape.getStrokeColor()));
		gc.setLineWidth(shape.getLineWidth());
		gc.strokeLine(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
	}

}
