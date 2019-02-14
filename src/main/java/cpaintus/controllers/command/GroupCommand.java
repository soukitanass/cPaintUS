package cpaintus.controllers.command;

import cpaintus.models.BoundingBox;
import cpaintus.models.composite.ShapesGroup;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapesDictionnary;

public class GroupCommand implements ICommand {
	
	private ShapesGroup shapesGroup;
	private ShapesDictionnary shapesDict;
	private BoundingBox boundingBox;
	
	public GroupCommand() {
		shapesDict = ShapesDictionnary.getInstance();
		boundingBox = BoundingBox.getInstance();
	}
	
	@Override
	public void execute() {
		shapesGroup = new ShapesGroup();
		double x = Double.MAX_VALUE;
		double y = Double.MAX_VALUE;
		double x2 = 0;
		double y2 = 0;

		for (Shape shape : shapesDict.getShapesList()) {
			if (shape.getUpLeftCorner().getX() >= boundingBox.getUpLeftCorner().getX()
					&& shape.getUpLeftCorner().getY() >= boundingBox.getUpLeftCorner().getY()
					&& shape.getUpLeftCorner().getX() + shape.getWidth()
					<= boundingBox.getUpLeftCorner().getX() + boundingBox.getWidth()
					&& shape.getUpLeftCorner().getY() + shape.getHeight()
					<= boundingBox.getUpLeftCorner().getY() + boundingBox.getHeight()) {

				shapesGroup.add(shape);
				shapesDict.removeShape(shape, false);
				x = Math.min(x, shape.getUpLeftCorner().getX());
				y = Math.min(y,  shape.getUpLeftCorner().getY());
				x2 = Math.max(x2, shape.getUpLeftCorner().getX() + shape.getWidth());
				y2 = Math.max(y2, shape.getUpLeftCorner().getY() + shape.getHeight());
			}
		}

		shapesGroup.setXGroup(x);
		shapesGroup.setYGroup(y);
		shapesGroup.setWidthGroup(x2 - x);
		shapesGroup.setHeightGroup(y2 - y);

		if (!shapesGroup.getShapes().isEmpty()) {
			shapesDict.addShape(shapesGroup);
		}
	}

	@Override
	public void undo() {
		shapesDict.removeShape(shapesGroup);
		for (Shape shape : shapesGroup.getShapes()) {
			shapesDict.addShape(shape);
		}
		boundingBox.setVisible(false);
	}

}
