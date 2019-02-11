package cpaintus.controllers.command;

import cpaintus.controllers.drawers.DrawerStrategyContext;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapesDictionnary;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;

public class EditCommand implements ICommand {
	
	private Shape shape; 
	private Shape oldShape;
	private Canvas activeCanvas;
	private DrawerStrategyContext drawerStrategyContext;
	
	public EditCommand () {
		drawerStrategyContext = DrawerStrategyContext.getInstance();
	}
	
	public void execute() {
		drawerStrategyContext.draw(shape, activeCanvas);
	}

	public void undo() {
		drawerStrategyContext.draw(oldShape, activeCanvas);
	}

}
