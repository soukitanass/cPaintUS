package cPaintUS.controllers.drawers;

import cPaintUS.models.shapes.Pokeball;
import cPaintUS.models.shapes.Shape;
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
		
		// Colored top of the ball
		gc.fillArc(shape.getX(),
				shape.getY(),
				shape.getWidth(),
				shape.getHeight(),
				0, 180, ArcType.ROUND);

		gc.setFill(Color.WHITE);
		// White bottom of the ball
		gc.fillArc(shape.getX(),
				shape.getY(),
				shape.getWidth(),
				shape.getHeight(),
				0, -180, ArcType.ROUND);
		// White circle in center
		gc.fillOval(shape.getX() + shape.getWidth() * startingCornerRatio,
				shape.getY() + shape.getHeight() * startingCornerRatio,
				shape.getWidth() * ratioBetweenCircles,
				shape.getHeight() * ratioBetweenCircles);
		// Biggest circle
		gc.strokeOval(shape.getX(), shape.getY(),
				shape.getWidth(), shape.getHeight());
		// Smallest circle
		gc.strokeOval(shape.getX() + shape.getWidth() * startingCornerRatio,
				shape.getY() + shape.getHeight() * startingCornerRatio,
				shape.getWidth() * ratioBetweenCircles,
				shape.getHeight() * ratioBetweenCircles);
		// Two lines on the sides
		gc.strokeLine(shape.getX(),
				shape.getY() + shape.getHeight() * 0.5,
				shape.getX() + shape.getWidth() * startingCornerRatio,
				shape.getY() + shape.getHeight() * 0.5);
		gc.strokeLine(shape.getX() + shape.getWidth() * endingCornerRatio,
				shape.getY() + shape.getHeight() * 0.5,
				shape.getX() + shape.getWidth(),
				shape.getY() + shape.getHeight() * 0.5);
	}
}
