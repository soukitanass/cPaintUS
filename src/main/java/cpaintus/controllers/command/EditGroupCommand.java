package cpaintus.controllers.command;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
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
	private List<Shape> shapeToEdit;
	private List<Shape> oldShape;
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private SnapshotSingleton snapshotSingleton;
	private Canvas activeCanvas;
	private DrawerStrategyContext drawerStrategyContext;
	private ShapesDictionnary shapesDictionnary;
	private AnchorPane pane;

	public EditCommand getCommand() {
		return command;
	}

	public void setCommand(EditCommand command) {
		this.command = command;
	}

	public List<Shape> getShapeToEdit() {
		return shapeToEdit;
	}

	public void setShapeToEdit(List<Shape> shapeToEdit) {
		this.shapeToEdit = shapeToEdit;
		setCommandID("EditGroup" + new String (shapeToEdit.toString()));
	}

	public List<Shape> getOldShape() {
		return oldShape;
	}

	public void setOldShape(List<Shape> oldShape) {
		this.oldShape = oldShape;
	}

	public EditGroupCommand() {
		command = new EditCommand();
		drawerStrategyContext = DrawerStrategyContext.getInstance();
		shapesDictionnary = ShapesDictionnary.getInstance();
		snapshotSingleton = SnapshotSingleton.getInstance();
		pane = snapshotSingleton.getSnapshotPane();
	}

	@Override
	public void execute() {
		for (int i = 0; i < shapeToEdit.size(); i++) {
			int hash = shapeToEdit.get(i).getCanvasHash();
			activeCanvas = (Canvas) pane.getChildren().stream().filter(child -> hash == child.hashCode()).findAny()
					.orElse(null);
			if (activeCanvas == null) {
				LOGGER.log(Level.INFO, "No shape to edit. Aborting edit because canvas is null.");
				return;
			}
			drawerStrategyContext.draw(shapeToEdit.get(i), activeCanvas);
		}
	}

	@Override
	public void undo() {
		for (int i = 0; i < oldShape.size(); i++) {
			int hash = oldShape.get(i).getCanvasHash();
			activeCanvas = (Canvas) pane.getChildren().stream().filter(child -> hash == child.hashCode()).findAny()
					.orElse(null);
			if (activeCanvas == null) {
				LOGGER.log(Level.INFO, "No shape to edit. Aborting edit because canvas is null.");
				return;
			}
			drawerStrategyContext.draw(oldShape.get(i), activeCanvas);
		}
	}

}
