package cpaintus.controllers.command;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import cpaintus.controllers.SnapshotSingleton;
import cpaintus.controllers.drawers.DrawerStrategyContext;
import cpaintus.models.BoundingBox;
import cpaintus.models.Point;
import cpaintus.models.composite.ShapesGroup;
import cpaintus.models.shapes.Line;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapeType;
import cpaintus.models.shapes.ShapesDictionnary;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;

public class EditGroupCommand extends Command {

	private EditCommand command; 
	private Shape shapeToEdit; 
	private Shape oldShape;
	
	public EditCommand getCommand() {
		return command;
	}

	public void setCommand(EditCommand command) {
		this.command = command;
	}
	
	public void setShapeToEdit(Shape shapeToEdit) {
		this.shapeToEdit = shapeToEdit;
	}

	public void setOldShape(Shape oldShape) {
		this.oldShape = oldShape;
	}
	
	public EditGroupCommand () {
		command = new EditCommand();
	}
	
	@Override
	public void execute() {
		int iterations = ((ShapesGroup) shapeToEdit).getShapes().size();
		for (int i = 0; i < iterations; i++) {
			command.setShapeToEdit(((ShapesGroup) shapeToEdit).getShapes().get(i));
			command.execute();
		}	
	}

	@Override
	public void undo() {
		int iterations = ((ShapesGroup) oldShape).getShapes().size();
		for (int i = 0; i < iterations; i++) {
			command.setShapeToEdit(((ShapesGroup) oldShape).getShapes().get(i));
			command.execute();
		}	
	}


}
