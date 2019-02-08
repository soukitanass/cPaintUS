package cpaintus.models.composite;

import java.util.ArrayList;
import java.util.List;

import cpaintus.models.shapes.Shape;

public class ShapesGroup extends Shape {

	private List<Shape> shapes = new ArrayList<>();

	public void add(Shape s) {
		this.shapes.add(s);
	}

	public void remove(Shape s) {
		shapes.remove(s);
	}

	public void clear() {
		this.shapes.clear();
	}

	public List<Shape> getShapes() {
		return shapes;
	}

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setWidth(double width) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setHeight(double height) {
		// TODO Auto-generated method stub

	}

}
