package cpaintus.controllers.drawers;

import cpaintus.models.shapes.Shape;
import javafx.scene.canvas.GraphicsContext;

public interface IDrawerStrategy {

	public void draw(GraphicsContext gc, Shape shape);
}
