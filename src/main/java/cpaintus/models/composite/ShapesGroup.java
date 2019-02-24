package cpaintus.models.composite;

import java.util.ArrayList;
import java.util.List;

import cpaintus.models.Point;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapeDimension;
import cpaintus.models.shapes.ShapeType;

public class ShapesGroup extends Shape {

	private List<Shape> shapes = new ArrayList<>();
	private static int groupNb = 0;

	public ShapesGroup() {
		setShapeId("Group " + groupNb);
		setShapeType(ShapeType.GROUP);
		this.shapeDim = ShapeDimension.SHAPE2D;
		groupNb++;
	}

	public void add(Shape shape) {
		if (shape.getShapeType() == ShapeType.GROUP) {
			for (Shape child : ((ShapesGroup) shape).getShapes()) {
				this.shapes.remove(child);
			}
		}

		this.shapes.add(shape);
	}

	public void remove(Shape shape) {
		if (shape.getShapeType() == ShapeType.GROUP) {
			for (Shape child : ((ShapesGroup) shape).getShapes()) {
				this.shapes.add(child);
			}
		}

		shapes.remove(shape);
	}

	public void clear() {
		this.shapes.clear();
	}

	public List<Shape> getShapes() {
		return shapes;
	}

	@Override
	public double getWidth() {
		double minX = Double.MAX_VALUE;
		double maxX = 0;

		for (Shape shape : shapes) {
			minX = Math.min(minX, shape.getUpLeftCorner().getX());
			maxX = Math.max(maxX, shape.getUpLeftCorner().getX() + shape.getWidth());
		}
		return maxX - minX;
	}

	@Override
	public void setWidth(double width) {
		for (Shape shape : shapes) {
			shape.setWidth(width);
		}
	}

	@Override
	public double getHeight() {
		double minY = Double.MAX_VALUE;
		double maxY = 0;

		for (Shape shape : shapes) {
			minY = Math.min(minY, shape.getUpLeftCorner().getY());
			maxY = Math.max(maxY, shape.getUpLeftCorner().getY() + shape.getHeight());
		}
		return maxY - minY;
	}

	@Override
	public void setHeight(double height) {
		for (Shape shape : shapes) {
			shape.setHeight(height);
		}
	}

	@Override
	public double getX() {
		double minX = Double.MAX_VALUE;
		for (Shape shape : shapes) {
			minX = Math.min(minX, shape.getUpLeftCorner().getX());
		}
		return minX;
	}

	@Override
	public void setX(double x) {
		double marge = getUpLeftCorner().getX() - x;
		for (Shape shape : shapes) {
			shape.setUpLeftCornerX(shape.getUpLeftCorner().getX() - marge);
		}
	}

	@Override
	public double getY() {
		double minY = Double.MAX_VALUE;
		for (Shape shape : shapes) {
			minY = Math.min(minY, shape.getUpLeftCorner().getY());
		}
		return minY;
	}
	
	@Override
	public void setY(double y) {
		double marge = getUpLeftCorner().getY() - y;
		for (Shape shape : shapes) {
			shape.setUpLeftCornerY(shape.getUpLeftCorner().getY() - marge);
		}
	}
	
	@Override
	public ShapesGroup makeCopy() {
		ShapesGroup group = new ShapesGroup();
		--groupNb;
		group.setShapeId("Group " + groupNb);
		group.setShapeType(ShapeType.GROUP);
		group.shapeDim = ShapeDimension.SHAPE2D;
		for (Shape shape : shapes) {
			group.shapes.add(shape.makeCopy());
		}
		return group;
	}

	@Override
	public Point getUpLeftCorner() {
		return new Point(getX(), getY());
	}

	@Override
	public void flipHorizontally() {
		Point center = this.getCenter();
		flipHorizontally(center);
	}

	public void flipHorizontally(Point center) {
		double shapeX;
		for (Shape shape : shapes) {
			if (shape.getShapeType() == ShapeType.GROUP) {
				((ShapesGroup)shape).flipHorizontally(center);
			} else {
				shape.flipHorizontally();
				shapeX = shape.getUpLeftCorner().getX();
				shape.setX(2 * center.getX() - shapeX - shape.getWidth());
			}
		}
	}

	@Override
	public void flipVertically() {
		Point center = this.getCenter();
		flipVertically(center);
	}

	public void flipVertically(Point center) {
		double shapeY;
		for (Shape shape : shapes) {
			if (shape.getShapeType() == ShapeType.GROUP) {
				((ShapesGroup)shape).flipVertically(center);
			} else {
				shape.flipVertically();
				shapeY = shape.getUpLeftCorner().getY();
				shape.setY(2 * center.getY() - shapeY - shape.getHeight());
			}
		}
	}

}
