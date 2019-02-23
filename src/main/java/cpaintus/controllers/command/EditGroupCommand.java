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
	private Shape shapeToEdit;
	private Shape oldShape;
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
		boundingBox = BoundingBox.getInstance();
	}

	@Override
	public void execute() {
		List<Shape> shapes = ((ShapesGroup)shapeToEdit).getShapes();
		for (int i = 0; i < shapes.size(); i++) {
			int hash = shapes.get(i).getCanvasHash();
			activeCanvas = (Canvas) pane.getChildren().stream().filter(child -> hash == child.hashCode()).findAny()
					.orElse(null);
			if (activeCanvas == null) {
				LOGGER.log(Level.INFO, "No shape to edit. Aborting edit because canvas is null.");
				return;
			}
			if (shapeToEdit.isFlipVertical()) {
				activeCanvas.getTransforms().add(new Rotate(180, boundingBox.getCenter().getX(),
						boundingBox.getCenter().getY(), 0, Rotate.X_AXIS));

			} else if (shapeToEdit.isFlipHorizontal()) {
				activeCanvas.getTransforms().add(new Rotate(180, boundingBox.getCenter().getX(),
						boundingBox.getCenter().getY(), 0, Rotate.Y_AXIS));

			} else
				drawerStrategyContext.draw(shapes.get(i), activeCanvas);
		}
		shapeToEdit.setFlipVertical(false);
		shapeToEdit.setFlipHorizontal(false);
	}

	@Override
	public void undo() {
		List<Shape> oldShapes = ((ShapesGroup)oldShape).getShapes();
		for (int i = 0; i < oldShapes.size(); i++) {
			int hash = oldShapes.get(i).getCanvasHash();
			activeCanvas = (Canvas) pane.getChildren().stream().filter(child -> hash == child.hashCode()).findAny()
					.orElse(null);
			if (activeCanvas == null) {
				LOGGER.log(Level.INFO, "No shape to edit. Aborting edit because canvas is null.");
				return;
			}
			drawerStrategyContext.draw(oldShapes.get(i), activeCanvas);
		}
	}

}
