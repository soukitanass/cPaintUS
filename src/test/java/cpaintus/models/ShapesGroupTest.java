package cpaintus.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cpaintus.models.composite.ShapesGroup;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.Shape2D;
import cpaintus.models.shapes.ShapeDimension;
import cpaintus.models.shapes.ShapeFactory;
import cpaintus.models.shapes.ShapeType;

class ShapesGroupTest {

	ShapesGroup shapesGroup;
	private ShapeFactory shapeFactory;
	@BeforeEach
	void setUp() throws Exception {
		shapesGroup = new ShapesGroup();
		shapeFactory = ShapeFactory.getInstance();
	}

	@Test
	void testAdd() {
		assertTrue(shapesGroup.getShapes().isEmpty());
		shapesGroup.add(shapeFactory.getShape(ShapeType.RECTANGLE, true, 0, 0, 11, 0, 0, 1, 10, 100, 1, "#fff", "#fff", "", "test"));
		assertEquals(1, shapesGroup.getShapes().size());
		shapesGroup.add(shapeFactory.getShape(ShapeType.HEART, true, 0, 0, 11, 0, 0, 1, 10, 100, 1, "#fff", "#fff", "", "test"));
		assertEquals(2, shapesGroup.getShapes().size());
		
	}
	
	@Test
	void testRemove() {
		assertTrue(shapesGroup.getShapes().isEmpty());
		Shape shape = shapeFactory.getShape(ShapeType.RECTANGLE, true, 0, 0, 11, 0, 0, 1, 10, 100, 1, "#fff", "#fff", "", "test");
		shapesGroup.add(shape);
		assertEquals(1, shapesGroup.getShapes().size());
		shapesGroup.remove(shape);
		assertTrue(shapesGroup.getShapes().isEmpty());
		
	}
	
	@Test
	void testClear() {
		assertTrue(shapesGroup.getShapes().isEmpty());
		shapesGroup.add(shapeFactory.getShape(ShapeType.RECTANGLE, true, 0, 0, 11, 0, 0, 1, 10, 100, 1, "#fff", "#fff", "", "test"));
		assertEquals(1, shapesGroup.getShapes().size());
		shapesGroup.clear();
		assertTrue(shapesGroup.getShapes().isEmpty());
	}
	
	@Test
	void changeWidthOfGroup() {
		addShapes();
		double expectedWidth = 120;
		shapesGroup.setWidth(expectedWidth);
		for(Shape shape: shapesGroup.getShapes()) {
			assertEquals(expectedWidth, shape.getWidth());
		}
	}
	
	@Test
	void changeHeightOfGroup() {
		addShapes();
		double expectedHeight = 300;
		shapesGroup.setHeight(expectedHeight);
		for(Shape shape: shapesGroup.getShapes()) {
			assertEquals(expectedHeight, shape.getHeight());
		}
	}
	
	@Test
	void changeXOfGroup() {
		addShapes();
		shapesGroup.setXGroup(130);
		double expectedX = shapesGroup.getShapes().get(0).getX() - 10;
		shapesGroup.setX(120);
		assertEquals(expectedX, shapesGroup.getShapes().get(0).getX());	
	}
	

	@Test
	void changeYOfGroup() {
		addShapes();
		shapesGroup.setYGroup(140);
		double expectedY = shapesGroup.getShapes().get(0).getY() - 10;
		shapesGroup.setY(130);
		assertEquals(expectedY, shapesGroup.getShapes().get(0).getY());	
	}
	
	@Test
	void changeRotationOfGroup() {
		addShapes();
		double expectedRotation = 180;
		shapesGroup.setRotation(expectedRotation);
		for(Shape shape: shapesGroup.getShapes()) {
			assertEquals(expectedRotation, shape.getRotation());
		}
	}
	
	@Test
	void changeLineWidthOfGroup() {
		addShapes();
		int expectedLineWidth = 3;
		shapesGroup.setLineWidth(expectedLineWidth);
		for(Shape shape: shapesGroup.getShapes()) {
			assertEquals(expectedLineWidth, shape.getLineWidth());
		}
	}
	
	@Test
	void changeStrokeColorOfGroup() {
		addShapes();
		String expectedStrokeColor = "#f0987";
		shapesGroup.setStrokeColor(expectedStrokeColor);
		for(Shape shape: shapesGroup.getShapes()) {
			assertEquals(expectedStrokeColor, shape.getStrokeColor());
		}
	}
	
	@Test
	void changeFillColorOfGroup() {
		addShapes();
		String expectedFillColor = "#f0956";
		shapesGroup.setFillColor(expectedFillColor);
		for(Shape shape: shapesGroup.getShapes()) {
			if(shape.getShapeDimension() == ShapeDimension.SHAPE2D) {
				assertEquals(expectedFillColor, ((Shape2D) shape).getFillColor());
			}
			
		}
	}
	

	void addShapes() {
		shapesGroup.add(shapeFactory.getShape(ShapeType.RECTANGLE, true, 3, 6, 11, 0, 0, 200, 10, 0, 1, "#ff1ff", "#fff56", "", "test"));
		shapesGroup.add(shapeFactory.getShape(ShapeType.ELLIPSE, true, 5, 7, 11, 0, 0, 220, 100, 30, 2, "#fff98", "#ff678", "", "test"));
		shapesGroup.add(shapeFactory.getShape(ShapeType.HEART, true, 3, 22, 11, 0, 0, 320, 130, 100, 6, "#fff45", "#f678f", "", "test"));
		assertEquals(3, shapesGroup.getShapes().size());
	}
}
