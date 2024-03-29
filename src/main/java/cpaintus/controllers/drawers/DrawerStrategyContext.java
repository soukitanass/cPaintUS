package cpaintus.controllers.drawers;

import cpaintus.models.observable.IObserver;
import cpaintus.models.observable.Observable;
import cpaintus.models.observable.ObservableList;
import cpaintus.models.shapes.Shape;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.StrokeLineCap;

public class DrawerStrategyContext extends Observable<IObserver> {

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
		activeCanvas.setLayoutX(shape.getUpLeftCorner().getX());
		activeCanvas.setLayoutY(shape.getUpLeftCorner().getY());
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
		activeCanvas.setScaleX(shape.isFlippedHorizontally() ? -1 : 1);
		activeCanvas.setScaleY(shape.isFlippedVertically() ? -1 : 1);
		notifyAllObservers();
	}

	@Override
	public void notifyAllObservers() {
		for (IObserver obs : getObserverList()) {
			obs.update(ObservableList.DRAW);
		}
	}
}
