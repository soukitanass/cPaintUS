package cPaintUS.controllers.drawers;

import cPaintUS.models.shapes.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LineDrawerStrategy implements IDrawerStrategy {

	@Override
	public void draw(GraphicsContext gc, Shape shape) {
		gc.setStroke(Color.web(shape.getStrokeColor()));
		gc.setLineWidth(shape.getLineWidth());
		gc.strokeLine(shape.getLineWidth()/2, shape.getLineWidth()/2, shape.getWidth() - shape.getLineWidth()/2, shape.getHeight() - shape.getLineWidth()/2);
	}

}
