package cpaintus.controllers.command;

import java.util.List;

import cpaintus.controllers.SnapshotSingleton;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapesDictionnary;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;

public class EditZCommand extends Command {
	
	private boolean firstTime = true;
	private int newZ;
	private int oldZ; 
	private Canvas activeCanvas;
	private Shape shapeAttr; 
	private AnchorPane pane;
	private SnapshotSingleton snapshotSingleton;
	
	public int getNewZ() {
		return newZ;
	}

	public int getOldZ() {
		return oldZ;
	}

	public Shape getShapeAttr() {
		return shapeAttr;
	}
	
	public void setShape(Shape shape) {
		this.shapeAttr = shape;
		setCommandID("Edit Z :" + new String(shapeAttr.toString()));
	}
	
	public void setNewZ(int newZ) {
		this.newZ = newZ;
	}

	public void setOldZ(int oldZ) {
		this.oldZ = oldZ;
	}

	public void setPane(AnchorPane pane) {
		this.pane = pane;
	}

	public EditZCommand () {
		snapshotSingleton = SnapshotSingleton.getInstance();
		pane = snapshotSingleton.getSnapshotPane();
	}

	public void execute() {
		if (firstTime) {
			this.setup();
		}
		changeOrder(newZ,oldZ);
	}

	public void undo() {
		firstTime = false;
		changeOrder(oldZ,newZ);
	}
	
	private void changeOrder (int changingZ, int changedZ) {
		List<Node> nodes = pane.getChildren();		
		nodes.remove(changedZ);
		nodes.add(changingZ, activeCanvas);
		
		snapshotSingleton.updateShapesZ();
	}
	
	private void setup () {
		int hash = shapeAttr.getCanvasHash();
		activeCanvas = (Canvas) pane.getChildren().stream().filter(child -> hash == child.hashCode()).findAny().orElse(null);
		this.setOldZ(pane.getChildren().indexOf(activeCanvas));	
	}

}
