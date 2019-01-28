package cPaintUS.controllers.drawers;

import cPaintUS.models.shapes.Rectangle;
import cPaintUS.models.shapes.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectangleDrawerStrategy implements IDrawerStrategy {

	@Override
	public void draw(GraphicsContext gc, Shape shape) {
		gc.setStroke(Color.web(shape.getStrokeColor()));
		gc.setLineWidth(shape.getLineWidth());
		gc.setFill(Color.web(((Rectangle) shape).getFillColor()));
		gc.fillRect(shape.getLineWidth()/2, shape.getLineWidth()/2, shape.getWidth()-shape.getLineWidth()/2, shape.getHeight()-shape.getLineWidth()/2);
		gc.strokeRect(shape.getLineWidth()/2, shape.getLineWidth()/2, shape.getWidth()-shape.getLineWidth()/2, shape.getHeight()-shape.getLineWidth()/2);
	}

}
