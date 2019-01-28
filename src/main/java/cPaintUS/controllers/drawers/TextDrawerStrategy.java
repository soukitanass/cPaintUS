package cPaintUS.controllers.drawers;

import cPaintUS.models.shapes.Shape;
import cPaintUS.models.shapes.Text;
import javafx.scene.canvas.GraphicsContext;

public class TextDrawerStrategy implements IDrawerStrategy{

	@Override
	public void draw(GraphicsContext gc, Shape shape) {
		gc.strokeText( ((Text)shape).getText(), shape.getX(), shape.getY(), shape.getWidth());
		
	}

}
