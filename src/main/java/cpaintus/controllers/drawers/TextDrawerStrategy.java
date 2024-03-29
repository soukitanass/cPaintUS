package cpaintus.controllers.drawers;

import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.Text;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TextDrawerStrategy implements IDrawerStrategy{

	@Override
	public void draw(GraphicsContext gc, Shape shape) {
		gc.setFont(Font.font(shape.getHeight()-5));
		gc.setStroke(Color.web(shape.getStrokeColor()));
		gc.setLineWidth(shape.getLineWidth());
		gc.setFill(Color.web(((Text) shape).getFillColor()));
		gc.fillText(((Text)shape).getText(), shape.getLineWidth()/2, shape.getHeight()*3/4, shape.getWidth() - shape.getLineWidth());
		gc.strokeText(((Text)shape).getText(), shape.getLineWidth()/2, shape.getHeight()*3/4, shape.getWidth() - shape.getLineWidth());
		
	}

}
