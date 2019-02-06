package cpaintusc.controllers.drawers;

import cpaintusc.models.shapes.Line;
import cpaintusc.models.shapes.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LineDrawerStrategy implements IDrawerStrategy {

	@Override
	public void draw(GraphicsContext gc, Shape shape) {
		double originX = Math.min(shape.getX(), ((Line)shape).getX2());
		double originY = Math.min(shape.getY(), ((Line)shape).getY2());
		double lineWidth = shape.getLineWidth()/2;
		double offsetX1 = lineWidth * (shape.getX() < ((Line)shape).getX2() ? 1 : -1),
				offsetY1 = lineWidth * (shape.getY() < ((Line)shape).getY2() ? 1 : -1),
				offsetX2 = lineWidth * (shape.getX() > ((Line)shape).getX2() ? 1 : -1),
				offsetY2 = lineWidth * (shape.getY() > ((Line)shape).getY2() ? 1 : -1);
		
		gc.setStroke(Color.web(shape.getStrokeColor()));
		gc.setLineWidth(shape.getLineWidth());
		gc.strokeLine(
				shape.getX() - originX + offsetX1,
				shape.getY() - originY + offsetY1,
				((Line)shape).getX2() - originX + offsetX2,
				((Line)shape).getY2() - originY + offsetY2);
	}

}
