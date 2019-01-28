package cPaintUS.controllers.drawers;

import cPaintUS.models.shapes.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LineDrawerStrategy implements IDrawerStrategy {

	@Override
	public void draw(GraphicsContext gc, Shape shape) {
		double originX = Math.min(shape.getX(),shape.getWidth());
		double originY = Math.min(shape.getY(),shape.getHeight());
		
		gc.setStroke(Color.web(shape.getStrokeColor()));
		gc.setLineWidth(shape.getLineWidth());
		gc.strokeLine(shape.getX() - originX, shape.getY() - originY, shape.getWidth() - originX, shape.getHeight() - originY);
	}

}
