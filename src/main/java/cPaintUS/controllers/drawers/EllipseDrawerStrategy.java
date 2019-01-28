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
		gc.fillOval(shape.getLineWidth()/2, shape.getLineWidth()/2, shape.getWidth() - shape.getLineWidth()/2, shape.getHeight()- shape.getLineWidth()/2);
		gc.strokeOval(shape.getLineWidth()/2, shape.getLineWidth()/2, shape.getWidth()- shape.getLineWidth()/2, shape.getHeight()- shape.getLineWidth()/2);
	}

}
