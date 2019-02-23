package cpaintus.controllers.command;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import cpaintus.controllers.SnapshotSingleton;
import cpaintus.controllers.drawers.DrawerStrategyContext;
import cpaintus.models.BoundingBox;
import cpaintus.models.composite.ShapesGroup;
import cpaintus.models.shapes.Shape;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;

public class EditGroupCommand extends Command {

	private EditCommand command;
	private List<Shape> shapeToEdit;
	private List<Shape> oldShape;
	private ShapesGroup shapesGroup;
	private BoundingBox boundingBox;
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

	public List<Shape> getShapeToEdit() {
		return shapeToEdit;
	}

	public void setShapeToEdit(List<Shape> shapeToEdit) {
		this.shapeToEdit = shapeToEdit;
		setCommandID("EditGroup" + shapeToEdit.toString());
	}

	public List<Shape> getOldShape() {
		return oldShape;
	}

	public void setOldShape(List<Shape> oldShape) {
		this.oldShape = oldShape;
	}

	public void setGroupShape(ShapesGroup shapesGroup) {
		this.shapesGroup = shapesGroup;
	}

	public EditGroupCommand() {
		command = new EditCommand();
		drawerStrategyContext = DrawerStrategyContext.getInstance();
		snapshotSingleton = SnapshotSingleton.getInstance();
		pane = snapshotSingleton.getSnapshotPane();
		boundingBox = BoundingBox.getInstance();
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
			if (shapesGroup.isFlipVertical()) {
				activeCanvas.getTransforms().add(new Rotate(180, boundingBox.getCenter().getX(),
						boundingBox.getCenter().getY(), 0, Rotate.X_AXIS));

			} else if (shapesGroup.isFlipHorizontal()) {
				activeCanvas.getTransforms().add(new Rotate(180, boundingBox.getCenter().getX(),
						boundingBox.getCenter().getY(), 0, Rotate.Y_AXIS));

			} else
				drawerStrategyContext.draw(shapeToEdit.get(i), activeCanvas);
		}
		shapesGroup.setFlipVertical(false);
		shapesGroup.setFlipHorizontal(false);
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
