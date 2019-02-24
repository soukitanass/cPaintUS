package cpaintus.controllers.command;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import cpaintus.controllers.SnapshotSingleton;
import cpaintus.controllers.drawers.DrawerStrategyContext;
import cpaintus.models.composite.ShapesGroup;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapeType;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;

public class EditGroupCommand extends Command {

	private EditCommand command;
	private Shape shapeToEdit;
	private Shape oldShape;
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private SnapshotSingleton snapshotSingleton;
	private Canvas activeCanvas;
	private DrawerStrategyContext drawerStrategyContext;
	private AnchorPane pane;

	public EditCommand getCommand() {
		return command;
	}

	public void setCommand(EditCommand command) {
		this.command = command;
	}

	public Shape getShapeToEdit() {
		return shapeToEdit;
	}

	public void setShapeToEdit(Shape shapeToEdit) {
		this.shapeToEdit = shapeToEdit;
		setCommandID("EditGroup " + ((ShapesGroup)shapeToEdit).getShapes().toString());
	}

	public Shape getOldShape() {
		return oldShape;
	}

	public void setOldShape(Shape oldShape) {
		this.oldShape = oldShape;
	}

	public EditGroupCommand() {
		command = new EditCommand();
		drawerStrategyContext = DrawerStrategyContext.getInstance();
		snapshotSingleton = SnapshotSingleton.getInstance();
		pane = snapshotSingleton.getSnapshotPane();
	}

	private void execute(Shape shapesGroup) {
		List<Shape> shapes = ((ShapesGroup)shapesGroup).getShapes();
		for (Shape shape : shapes) {
			if (shape.getShapeType() == ShapeType.GROUP) {
				execute(shape);
				return;
			}
			int hash = shape.getCanvasHash();
			activeCanvas = (Canvas) pane.getChildren().stream().filter(child -> hash == child.hashCode()).findAny()
					.orElse(null);
			if (activeCanvas == null) {
				LOGGER.log(Level.INFO, "No shape to edit. Aborting edit because canvas is null.");
				return;
			}
			drawerStrategyContext.draw(shape, activeCanvas);
		}
	}

	@Override
	public void execute() {
		execute(shapeToEdit);
	}

	@Override
	public void undo() {
		List<Shape> oldShapes = ((ShapesGroup)oldShape).getShapes();
		for (Shape shape : oldShapes) {
			int hash = shape.getCanvasHash();
			activeCanvas = (Canvas) pane.getChildren().stream().filter(child -> hash == child.hashCode()).findAny()
					.orElse(null);
			if (activeCanvas == null) {
				LOGGER.log(Level.INFO, "No shape to edit. Aborting edit because canvas is null.");
				return;
			}
			drawerStrategyContext.draw(shape, activeCanvas);
		}
	}

}
