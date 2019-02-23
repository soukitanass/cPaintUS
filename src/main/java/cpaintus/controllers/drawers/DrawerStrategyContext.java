package cpaintus.controllers.drawers;

import cpaintus.models.shapes.Shape;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.StrokeLineCap;

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
		activeCanvas.setLayoutX(shape.getX());
		activeCanvas.setLayoutY(shape.getY());
		activeCanvas.setWidth(shape.getWidth());
		activeCanvas.setHeight(shape.getHeight());

		GraphicsContext gc = activeCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, activeCanvas.getWidth(), activeCanvas.getHeight());
		gc.setLineCap(StrokeLineCap.ROUND);

		switch (shape.getShapeType()) {
		case RECTANGLE:
			drawerStrategy = new RectangleDrawerStrategy();
			drawerStrategy.draw(gc, shape);
			break;
		case ELLIPSE:
			drawerStrategy = new EllipseDrawerStrategy();
			drawerStrategy.draw(gc, shape);
			break;
		case LINE:
			activeCanvas.setLayoutX(shape.getUpLeftCorner().getX());
			activeCanvas.setLayoutY(shape.getUpLeftCorner().getY());

			drawerStrategy = new LineDrawerStrategy();
			drawerStrategy.draw(gc, shape);
			break;
		case POKEBALL:
			drawerStrategy = new PokeballDrawerStrategy();
			drawerStrategy.draw(gc, shape);
			break;
		case HEART:
			drawerStrategy = new HeartDrawerStrategy();
			drawerStrategy.draw(gc, shape);
			break;
		case PICTURE:
			drawerStrategy = new PictureDrawerStrategy();
			drawerStrategy.draw(gc, shape);
			break;
		case TEXT:
			drawerStrategy = new TextDrawerStrategy();
			drawerStrategy.draw(gc, shape);
			break;
		default:
			break;
		}

		activeCanvas.setRotate(shape.getRotation());
		if (shape.isFlipHorizontal()) {
			activeCanvas.setScaleX(-activeCanvas.getScaleX());
			shape.setFlipHorizontal(false);
		}
		if (shape.isFlipVertical()) {
			activeCanvas.setScaleY(-activeCanvas.getScaleY());
			shape.setFlipVertical(false);
		}
	}
}
