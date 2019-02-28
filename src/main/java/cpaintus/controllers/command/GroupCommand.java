package cpaintus.controllers.command;

import cpaintus.controllers.SelectShapesSingleton;
import cpaintus.models.BoundingBox;
import cpaintus.models.composite.ShapesGroup;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapesDictionnary;

public class GroupCommand extends Command {

	private ShapesGroup shapesGroup;
	private ShapesDictionnary shapesDict;
	private BoundingBox boundingBox;
	private SelectShapesSingleton selectShapesSingleton;
	private boolean firstime = false;

	public GroupCommand() {
		setCommandID("Group n°" + new String(shapesGroup.getShapeId()) );
		shapesDict = ShapesDictionnary.getInstance();
		boundingBox = BoundingBox.getInstance();
		selectShapesSingleton = SelectShapesSingleton.getInstance();
	}

	public void setFirst(boolean first) {
		this.firstime = first;
	}

	@Override
	public void execute() {
		if (firstime) {
			shapesGroup = new ShapesGroup();

			for (Shape shape : shapesDict.getShapesList()) {
				if (shape.getUpLeftCorner().getX() >= boundingBox.getUpLeftCorner().getX()
						&& shape.getUpLeftCorner().getY() >= boundingBox.getUpLeftCorner().getY()
						&& shape.getUpLeftCorner().getX() + shape.getWidth() <= boundingBox.getUpLeftCorner().getX()
								+ boundingBox.getWidth()
						&& shape.getUpLeftCorner().getY() + shape.getHeight() <= boundingBox.getUpLeftCorner().getY()
								+ boundingBox.getHeight()) {

					shapesGroup.add(shape);
					shapesDict.removeShape(shape, false);
				}
			}

			if (!shapesGroup.getShapes().isEmpty()) {
				selectShapesSingleton.setLastCreatedGroup(shapesGroup);
				shapesDict.addShape(shapesGroup);
			}

		} else {
			shapesDict.addShape(shapesGroup);
		}
	}

	@Override
	public void undo() {
		shapesDict.removeShape(shapesGroup);
		boundingBox.setVisible(false);
		firstime = false;
	}

}
