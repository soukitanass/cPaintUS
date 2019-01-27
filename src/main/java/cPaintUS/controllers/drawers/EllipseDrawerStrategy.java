package cPaintUS.controllers.drawers;

import cPaintUS.models.shapes.Ellipse;
import cPaintUS.models.shapes.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class EllipseDrawerStrategy implements IDrawerStrategy {

	@Override
	public void draw(GraphicsContext gc, Shape shape) {
		gc.setStroke(Color.web(shape.getStrokeColor()));
		gc.setLineWidth(shape.getLineWidth());
		gc.setFill(Color.web(((Ellipse) shape).getFillColor()));
		gc.fillOval(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
		gc.strokeOval(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
	}

}
