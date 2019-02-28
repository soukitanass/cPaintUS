package cpaintus.controllers.command;

import java.util.List;

import cpaintus.controllers.SnapshotSingleton;
import cpaintus.controllers.drawers.DrawerStrategyContext;
import cpaintus.models.composite.ShapesGroup;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapeType;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;

public class EditGroupCommand extends Command {
	
	private String editType = "";
	private EditCommand command;
	private Shape shapeToEdit;
	private Shape oldShape;
	private SnapshotSingleton snapshotSingleton;
	private Canvas activeCanvas;
	private DrawerStrategyContext drawerStrategyContext;
	private AnchorPane pane;

	public void setEditType(String editType) {
		this.editType = editType;
	}

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
		setCommandID("EditGroup "+ editType + ((ShapesGroup)shapeToEdit).getShapes().toString());
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
				activeCanvas = new Canvas();
				shapeToEdit.setCanvasHash(activeCanvas.hashCode());
				oldShape.setCanvasHash(activeCanvas.hashCode());
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
				activeCanvas = new Canvas();
				shapeToEdit.setCanvasHash(activeCanvas.hashCode());
				oldShape.setCanvasHash(activeCanvas.hashCode());
			}
			drawerStrategyContext.draw(shape, activeCanvas);
		}
	}

}
