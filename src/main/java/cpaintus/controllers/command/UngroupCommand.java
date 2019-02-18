package cpaintus.controllers.command;

import cpaintus.models.BoundingBox;
import cpaintus.models.composite.ShapesGroup;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapesDictionnary;

public class UngroupCommand extends Command {

	private ShapesGroup shapesGroup;
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
		shapesDict.removeShape(shapesGroup);
		boundingBox.setVisible(false);
	}

	@Override
	public void undo() {
		for (Shape shape : shapesGroup.getShapes()) {
			if (!shapesGroup.getShapes().isEmpty()) {
				shapesDict.addShape(shapesGroup);
				shapesDict.removeShape(shape, false);
			}
		}

	}

}
