package cpaintus.controllers.command;

import cpaintus.controllers.drawers.DrawerStrategyContext;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapesDictionnary;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.AnchorPane;

public class DrawCommand implements ICommand{
	
	private AnchorPane pane;
	private Canvas activeCanvas;
	private Shape shape;
	private DrawerStrategyContext drawerStrategyContext;
	private ShapesDictionnary shapesDict;

	public DrawCommand (AnchorPane pane, Shape shape) {
		drawerStrategyContext = DrawerStrategyContext.getInstance();
		shapesDict = ShapesDictionnary.getInstance();
		this.pane = pane;
		this.shape = shape;
	}

	public void execute () {
		if(this.shape != null) {
			Canvas newCanvas = new Canvas();
			newCanvas.setMouseTransparent(true);
			newCanvas.setBlendMode(BlendMode.SRC_OVER);
			pane.getChildren().add(pane.getChildren().size() - 1, newCanvas);
			activeCanvas = newCanvas;
			shapesDict.addShape(shape);
			drawerStrategyContext.draw(shape, activeCanvas);
			System.out.println(pane.getChildren().size());
			System.out.println(activeCanvas.toString());
		}	
	}
	
	public void undo() {
		System.out.println("Active Canvas Undo : " + activeCanvas.toString());
		pane.getChildren().remove(activeCanvas);
		shapesDict.removeShape(shape);
	}
}
