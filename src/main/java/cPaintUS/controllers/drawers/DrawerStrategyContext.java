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
		activeCanvas.setWidth(shape.getX()+ shape.getWidth());
		activeCanvas.setHeight(shape.getY()+ shape.getHeight());
		GraphicsContext gc = activeCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, activeCanvas.getWidth(), activeCanvas.getHeight());
		shape.setCanvasId(activeCanvas.hashCode());
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
		default:
			break;
		}
	}
}
