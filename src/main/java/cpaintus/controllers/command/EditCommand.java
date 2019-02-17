package cpaintus.controllers.command;


import java.util.logging.Level;
import java.util.logging.Logger;

import cpaintus.controllers.SnapshotSingleton;
import cpaintus.controllers.drawers.DrawerStrategyContext;
import cpaintus.models.BoundingBox;
import cpaintus.models.Point;
import cpaintus.models.shapes.Line;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapeType;
import cpaintus.models.shapes.ShapesDictionnary;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;

public class EditCommand extends Command {
	
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private SnapshotSingleton snapshotSingleton;
	private BoundingBox boundingBox;
	private Shape shapeToEdit; 
	private Shape oldShape;
	private Canvas activeCanvas;
	private DrawerStrategyContext drawerStrategyContext;
	private ShapesDictionnary shapesDictionnary;
	private AnchorPane pane; 
	
	public EditCommand () {
		setCommandID("Edit :" + shapeToEdit);
		drawerStrategyContext = DrawerStrategyContext.getInstance();
		shapesDictionnary = ShapesDictionnary.getInstance();
		snapshotSingleton = SnapshotSingleton.getInstance();
		boundingBox = BoundingBox.getInstance();
		pane = snapshotSingleton.getSnapshotPane();
	}
	
	public void setShapeToEdit(Shape shapeToEdit) {
		this.shapeToEdit = shapeToEdit;
	}

	public void setOldShape(Shape oldShape) {
		this.oldShape = oldShape;
	}
	
	public void execute() {
		shapesDictionnary.addShape(shapeToEdit,false);
		int hash = shapeToEdit.getCanvasHash();
		activeCanvas = (Canvas) pane.getChildren().stream().filter(child -> hash == child.hashCode()).findAny().orElse(null);
		if (activeCanvas == null)  {
			LOGGER.log(Level.INFO,"No shape to edit. Aborting edit because canvas is null.");
			return;
		}
		updateBoundingBox(shapeToEdit);
		drawerStrategyContext.draw(shapeToEdit, activeCanvas);
	}

	public void undo() {
		shapesDictionnary.addShape(oldShape,false);
		int hash = oldShape.getCanvasHash();
		activeCanvas = (Canvas) pane.getChildren().stream().filter(child -> hash == child.hashCode()).findAny().orElse(null);
		if (activeCanvas == null)  {
			LOGGER.log(Level.INFO,"No shape to edit. Aborting edit because canvas is null.");
			return;
		}
		updateBoundingBox(oldShape);
		drawerStrategyContext.draw(oldShape, activeCanvas);
	}
	
	private void updateBoundingBox(Shape shape) {
		boundingBox.setOrigin(shape.getX(), shape.getY());
		boundingBox.setRotation(shape.getRotation());
		if (shape.getShapeType() == ShapeType.LINE) {
			boundingBox.updateBoundingBox(new Point(((Line) shape).getX2(), ((Line) shape).getY2()));
		} else {
			boundingBox.updateBoundingBox(new Point(shape.getX() + shape.getWidth(), shape.getY() + shape.getHeight()));
		}
	}

}
