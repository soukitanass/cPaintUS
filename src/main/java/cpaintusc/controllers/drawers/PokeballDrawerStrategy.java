package cpaintusc.controllers.drawers;

import cpaintusc.models.shapes.Pokeball;
import cpaintusc.models.shapes.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class PokeballDrawerStrategy implements IDrawerStrategy {

	@Override
	public void draw(GraphicsContext gc, Shape shape) {
		gc.setStroke(Color.web(shape.getStrokeColor()));
		gc.setLineWidth(shape.getLineWidth());
		gc.setFill(Color.web(((Pokeball) shape).getFillColor()));
		drawPokeball(gc,shape);
	}
	
	private void drawPokeball(GraphicsContext gc,Shape shape) {
		double ratioBetweenCircles = 0.25;
		double halfOfRatio = ratioBetweenCircles/2;
		double startingCornerRatio = 0.5 - halfOfRatio;
		double endingCornerRatio = 0.5 + halfOfRatio;
		
		double width = shape.getWidth() - (shape.getLineWidth()/2);
		double height = shape.getHeight() - (shape.getLineWidth()/2);
		
		// Colored top of the ball
		gc.fillArc(shape.getLineWidth()/2,
				shape.getLineWidth()/2,
				width,
				height,
				0, 180, ArcType.ROUND);

		gc.setFill(Color.WHITE);
		// White bottom of the ball
		gc.fillArc(shape.getLineWidth()/2,
				shape.getLineWidth()/2,
				width,
				height,
				0, -180, ArcType.ROUND);
		// White circle in center
		gc.fillOval(width * startingCornerRatio,
				height * startingCornerRatio,
				width * ratioBetweenCircles,
				height * ratioBetweenCircles);
		// Biggest circle
		gc.strokeOval(shape.getLineWidth()/2, shape.getLineWidth()/2,
				width, height);
		// Smallest circle
		gc.strokeOval((shape.getLineWidth()/2 + width) * startingCornerRatio,
				(shape.getLineWidth()/2 + height) * startingCornerRatio,
				width * ratioBetweenCircles,
				height * ratioBetweenCircles);
		// Two lines on the sides
		gc.strokeLine(shape.getLineWidth()/2,
				height * 0.5,
				width * startingCornerRatio,
				height * 0.5);
		gc.strokeLine(shape.getWidth() * endingCornerRatio,
				height * 0.5,
				width,
				height * 0.5);
	}
}
