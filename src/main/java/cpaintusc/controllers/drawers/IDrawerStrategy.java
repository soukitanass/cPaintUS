package cpaintusc.controllers.drawers;

import cpaintusc.models.shapes.Shape;
import javafx.scene.canvas.GraphicsContext;

public interface IDrawerStrategy {

	public void draw(GraphicsContext gc, Shape shape);
}
