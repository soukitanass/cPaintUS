package cpaintus.controllers.command;

import java.util.ArrayList;
import java.util.List;
import cpaintus.controllers.SnapshotSingleton;
import cpaintus.controllers.drawers.DrawerStrategyContext;
import cpaintus.models.BoundingBox;
import cpaintus.models.composite.ShapesGroup;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapesDictionnary;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;

public class EraseShapeCommand extends Command {

	private SnapshotSingleton snapshotSingleton;
	private BoundingBox boundingBox;
	private AnchorPane pane; 
	private Canvas activeCanvas;
	private Shape shapeToDelete;
	private List<ShapesGroup> parents;
	private ShapesDictionnary shapesDictionnary;
	private int z;
	
	public EraseShapeCommand() {
		snapshotSingleton = SnapshotSingleton.getInstance();
		boundingBox = BoundingBox.getInstance();
		pane = snapshotSingleton.getSnapshotPane();
		shapesDictionnary = ShapesDictionnary.getInstance();
	}
	@Override
	public void execute() {
		int hash = shapeToDelete.getCanvasHash();
		z = shapeToDelete.getZ();
		activeCanvas = (Canvas) pane.getChildren().stream().filter(child -> hash == child.hashCode()).findAny().orElse(null);
		pane.getChildren().remove(activeCanvas);
		
		parents = new ArrayList<ShapesGroup>();
		shapesDictionnary.findParents(shapeToDelete, shapesDictionnary.getShapesList(), parents);
		
		shapesDictionnary.removeShape(shapeToDelete);
		boundingBox.setVisible(false);
		
		snapshotSingleton.updateShapesZ();
	}

	@Override
	public void undo() {
		if(this.shapeToDelete != null && this.activeCanvas != null) {
			shapesDictionnary.addShape(this.shapeToDelete, this.parents);
			pane.getChildren().add(z, activeCanvas);
			snapshotSingleton.updateShapesZ();
		}
	}
	
	public void setShapeToDelete(Shape shapeToDelete) {
		this.shapeToDelete = shapeToDelete;
		setCommandID("Erase : "+ new String (shapeToDelete.toString()));
	}
	
	

}
