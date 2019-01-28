package cPaintUS.controllers.drawers;

import cPaintUS.models.shapes.Shape;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class DrawerStrategyContext {
	
	private DrawerStrategyContext() {
	}

	private static class SingletonHelper {
		private static final DrawerStrategyContext INSTANCE = new DrawerStrategyContext();
	}

	public static DrawerStrategyContext getInstance() {
		return SingletonHelper.INSTANCE;
	}

	public void draw(Shape shape, Canvas activeCanvas) {
		IDrawerStrategy drawerStrategy;
		activeCanvas.setLayoutX(shape.getX()-(shape.getLineWidth()/2));
		activeCanvas.setLayoutY(shape.getY()-(shape.getLineWidth()/2));
		activeCanvas.setWidth(shape.getWidth()+shape.getLineWidth());
		activeCanvas.setHeight(shape.getHeight()+shape.getLineWidth());
		GraphicsContext gc = activeCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, activeCanvas.getWidth(), activeCanvas.getHeight());
		switch (shape.getShapeType()) {
		case Rectangle:
			drawerStrategy = new RectangleDrawerStrategy();
			drawerStrategy.draw(gc, shape);
			break;
		case Ellipse:
			drawerStrategy = new EllipseDrawerStrategy();
			drawerStrategy.draw(gc, shape);
			break;
		case Line:
			double originX = Math.min(shape.getX(),shape.getWidth()) - shape.getLineWidth()/2;
			double originY = Math.min(shape.getY(),shape.getHeight()) - shape.getLineWidth()/2;
			double width = Math.abs(shape.getX()-shape.getWidth());
			double height = Math.abs(shape.getY()-shape.getHeight());
			activeCanvas.setLayoutX(originX);
			activeCanvas.setLayoutY(originY);
			activeCanvas.setWidth(width);
			activeCanvas.setHeight(height);
			drawerStrategy = new LineDrawerStrategy();
			drawerStrategy.draw(gc, shape);
			break;
		case Pokeball:
			drawerStrategy = new PokeballDrawerStrategy();
			drawerStrategy.draw(gc, shape);
			break;
		case Heart:
			drawerStrategy = new HeartDrawerStrategy();
			drawerStrategy.draw(gc, shape);
			break;
		case Picture:
			drawerStrategy = new PictureDrawerStrategy();
			drawerStrategy.draw(gc, shape);
			break;
		case Text:
			drawerStrategy = new TextDrawerStrategy();
			drawerStrategy.draw(gc, shape);
			break;
		default:
			break;
		}
		
		activeCanvas.setRotate(shape.getRotation());
	}
}
