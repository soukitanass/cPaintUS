package cpaintus.controllers.drawers;

import cpaintus.models.shapes.Pokeball;
import cpaintus.models.shapes.Shape;
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
	
	private void drawPokeball(GraphicsContext gc, Shape shape) {
		double ratioBetweenCircles = 0.25;
		double halfOfRatio = ratioBetweenCircles/2;
		double startingCornerRatio = 0.5 - halfOfRatio;
		double endingCornerRatio = 0.5 + halfOfRatio;
		
		double width = shape.getWidth();
		double height = shape.getHeight();
		
		double lineWidth = shape.getLineWidth();
		
		// Colored top of the ball
		gc.fillArc(lineWidth/2,
				lineWidth/2,
				width - lineWidth,
				height - lineWidth,
				0, 180, ArcType.ROUND);

		gc.setFill(Color.WHITE);
		// White bottom of the ball
		gc.fillArc(lineWidth/2,
				lineWidth/2,
				width - lineWidth,
				height - lineWidth,
				0, -180, ArcType.ROUND);
		// White circle in center
		gc.fillOval(width * startingCornerRatio + lineWidth/2,
				height * startingCornerRatio + lineWidth/2,
				width * ratioBetweenCircles - lineWidth,
				height * ratioBetweenCircles - lineWidth);
		// Biggest circle
		gc.strokeOval(lineWidth/2, 
				lineWidth/2,
				width - lineWidth,
				height - lineWidth);
		// Smallest circle
		gc.strokeOval(width * startingCornerRatio + lineWidth/2,
				height * startingCornerRatio + lineWidth/2,
				width * ratioBetweenCircles - lineWidth,
				height * ratioBetweenCircles - lineWidth);
		// Two lines on the sides
		gc.strokeLine(lineWidth/2,
				height * 0.5,
				width * startingCornerRatio + lineWidth/2,
				height * 0.5);
		gc.strokeLine(width * endingCornerRatio - lineWidth/2,
				height * 0.5,
				width - lineWidth/2,
				height * 0.5);
	}
}
