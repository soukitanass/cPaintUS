package cpaintus.controllers.drawers;

import cpaintus.models.FlipTransform;
import cpaintus.models.shapes.Shape;
import javafx.geometry.Point3D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.transform.Rotate;

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
		applyTransforms(shape, activeCanvas);
	}

	private void applyTransforms(Shape shape, Canvas activeCanvas) {
		activeCanvas.getTransforms().clear();
		for (FlipTransform flip : shape.getTransforms()) {
			Point3D axis = null;
			switch (flip.getType()) {
			case VERTICAL:
				axis = Rotate.X_AXIS;
				break;
			case HORIZONTAL:
				axis = Rotate.Y_AXIS;
				break;
			default:
				break;
			}
			if (axis != null) {
				activeCanvas.getTransforms().add(new Rotate(180,
						flip.getPivot().getX(),
						flip.getPivot().getY(),
						0,
						axis));
			}
		}
	}
}
