package cpaintus.controllers.command;


import cpaintus.controllers.SnapshotSingleton;
import cpaintus.controllers.drawers.DrawerStrategyContext;
import cpaintus.models.BoundingBox;
import cpaintus.models.Point;
import cpaintus.models.shapes.Line;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapeType;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;

public class EditCommand extends Command {

	private String editType = "";
	private SnapshotSingleton snapshotSingleton;
	private BoundingBox boundingBox;
	private Shape shapeToEdit;
	private Shape oldShape;
	private Canvas activeCanvas;
	private DrawerStrategyContext drawerStrategyContext;
	private AnchorPane pane;

	public EditCommand() {
		drawerStrategyContext = DrawerStrategyContext.getInstance();
		snapshotSingleton = SnapshotSingleton.getInstance();
		boundingBox = BoundingBox.getInstance();
		pane = snapshotSingleton.getSnapshotPane();
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public Shape getShapeToEdit() {
		return shapeToEdit;
	}

	public Shape getOldShape() {
		return oldShape;
	}

	public void setShapeToEdit(Shape shapeToEdit) {
		this.shapeToEdit = shapeToEdit;
		setCommandID("Edit "+ editType +" :" + new String(shapeToEdit.toString()));
	}

	public void setOldShape(Shape oldShape) {
		this.oldShape = oldShape;
	}

	public void execute() {
		int hash = shapeToEdit.getCanvasHash();
		activeCanvas = (Canvas) pane.getChildren().stream().filter(child -> hash == child.hashCode()).findAny()
				.orElse(null);
		if (activeCanvas == null) {
			activeCanvas = new Canvas();
			shapeToEdit.setCanvasHash(activeCanvas.hashCode());
			oldShape.setCanvasHash(activeCanvas.hashCode());
		}
		updateBoundingBox(shapeToEdit);
		drawerStrategyContext.draw(shapeToEdit, activeCanvas);
	}

	public void undo() {
		int hash = oldShape.getCanvasHash();
		activeCanvas = (Canvas) pane.getChildren().stream().filter(child -> hash == child.hashCode()).findAny()
				.orElse(null);
		if (activeCanvas == null) {
			activeCanvas = new Canvas();
			oldShape.setCanvasHash(activeCanvas.hashCode());
			shapeToEdit.setCanvasHash(activeCanvas.hashCode());
		}
		updateBoundingBox(oldShape);
		drawerStrategyContext.draw(oldShape, activeCanvas);
	}

	private void updateBoundingBox(Shape shape) {
		boundingBox.setOrigin(shape.getX(), shape.getY());
		if (shape.getShapeType() == ShapeType.LINE) {
			boundingBox.updateBoundingBox(new Point(((Line) shape).getX2(), ((Line) shape).getY2()));
		} else {
			boundingBox.updateBoundingBox(new Point(shape.getX() + shape.getWidth(), shape.getY() + shape.getHeight()));
		}
		boundingBox.setRotation(shape.getRotation());

	}

}
