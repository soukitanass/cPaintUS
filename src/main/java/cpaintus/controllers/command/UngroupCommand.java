package cpaintus.controllers.command;

import java.util.ArrayList;
import java.util.List;

import cpaintus.models.BoundingBox;
import cpaintus.models.composite.ShapesGroup;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapesDictionnary;

public class UngroupCommand extends Command {

	private ShapesGroup shapesGroup;
	private List<ShapesGroup> parents;
	private ShapesDictionnary shapesDict;
	private BoundingBox boundingBox;
	
	public void setShapesGroup(ShapesGroup shapesGroup) {
		this.shapesGroup = shapesGroup;
	}

	
	public UngroupCommand() {
		setCommandID("Ungroup");
		shapesDict = ShapesDictionnary.getInstance();
		boundingBox = BoundingBox.getInstance();
	}
	
	@Override
	public void execute() {
		parents = new ArrayList<ShapesGroup>();
		shapesDict.findParents(shapesGroup, shapesDict.getShapesList(), parents);
		shapesDict.removeShape(shapesGroup);	
		boundingBox.setVisible(false);
	}

	@Override
	public void undo() {
		for (Shape shape : shapesGroup.getShapes()) {
			shapesDict.removeShape(shape, false);
		}
		
		if (this.parents.size() != 0) {
			for (ShapesGroup parent : this.parents) {
				parent.add(shapesGroup);
				shapesDict.addShape(parent); // To trigger event in left pane
			}
		} else {
			shapesDict.addShape(shapesGroup);
		}
	}

}
