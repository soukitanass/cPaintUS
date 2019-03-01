package cpaintus.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cpaintus.models.composite.ShapesGroup;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapeFactory;
import cpaintus.models.shapes.ShapeType;
import cpaintus.models.shapes.Size;
import cpaintus.models.shapes.Stroke;

class ShapesGroupTest {

	ShapesGroup shapesGroup;

	@BeforeEach
	void setUp() throws Exception {
		shapesGroup = new ShapesGroup();
	}

	@Test
	void testAdd() {
		assertTrue(shapesGroup.getShapes().isEmpty());
		shapesGroup.add(ShapeFactory.getShape(ShapeType.RECTANGLE, true, 0, new Point(0, 11), new Point(0, 0),
				new Size(1, 10), 100, new Stroke(1, "#fff"), "#fff", "", "hh"));
		assertEquals(1, shapesGroup.getShapes().size());
		shapesGroup.add(ShapeFactory.getShape(ShapeType.HEART, true, 0, new Point(0, 11), new Point(0, 0),
				new Size(1, 10), 100, new Stroke(1, "#fff"), "#fff", "", "hh"));
		assertEquals(2, shapesGroup.getShapes().size());

	}
	
	@Test
	void testAddGroup() {
		Shape shape = ShapeFactory.getShape(ShapeType.RECTANGLE, true, 0, new Point(0, 11), new Point(0, 0),
				new Size(1, 10), 100, new Stroke(1, "#fff"), "#fff", "", "hh");
		shapesGroup.add(shape);
		
		assertEquals(1, shapesGroup.getShapes().size());
		assertSame(shape, shapesGroup.getShapes().get(0));
		
		ShapesGroup childGroup = new ShapesGroup();
		childGroup.add(shape);
		shapesGroup.add(childGroup);
		
		assertEquals(1, shapesGroup.getShapes().size());
		assertSame(childGroup, shapesGroup.getShapes().get(0));
	}

	@Test
	void testRemove() {
		assertTrue(shapesGroup.getShapes().isEmpty());
		Shape shape = ShapeFactory.getShape(ShapeType.RECTANGLE, true, 0, new Point(0, 11), new Point(0, 0),
				new Size(1, 10), 100, new Stroke(1, "#fff"), "#fff", "", "hh");
		shapesGroup.add(shape);
		assertEquals(1, shapesGroup.getShapes().size());
		shapesGroup.remove(shape);
		assertTrue(shapesGroup.getShapes().isEmpty());
	}
	
	@Test
	void testRemoveGroup() {
		Shape shape = ShapeFactory.getShape(ShapeType.RECTANGLE, true, 0, new Point(0, 11), new Point(0, 0),
				new Size(1, 10), 100, new Stroke(1, "#fff"), "#fff", "", "hh");
		
		ShapesGroup childGroup = new ShapesGroup();
		childGroup.add(shape);
		shapesGroup.remove(childGroup);
		
		assertEquals(1, shapesGroup.getShapes().size());
		assertSame(shape, shapesGroup.getShapes().get(0));
	}

	@Test
	void testClear() {
		assertTrue(shapesGroup.getShapes().isEmpty());
		shapesGroup.add(ShapeFactory.getShape(ShapeType.RECTANGLE, true, 0, new Point(0, 11), new Point(0, 0),
				new Size(1, 10), 100, new Stroke(1, "#fff"), "#fff", "", "hh"));
		assertEquals(1, shapesGroup.getShapes().size());
		shapesGroup.clear();
		assertTrue(shapesGroup.getShapes().isEmpty());
	}

	@Test
	void getWidthOfGroup() {
		addShapes();
		assertEquals(22 + 320 - 6, shapesGroup.getWidth());
	}

	@Test
	void getHeightOfGroup() {
		addShapes();
		assertEquals(13 + 130 - 11, shapesGroup.getHeight());
	}

	@Test
	void changeXOfGroup() {
		addShapes();
		assertEquals(6, shapesGroup.getX());
		double expectedX = 100;
		shapesGroup.setX(expectedX);
		assertEquals(expectedX, shapesGroup.getShapes().get(0).getX());
	}

	@Test
	void changeYOfGroup() {
		addShapes();
		assertEquals(11, shapesGroup.getY());
		double expectedY = 100;
		shapesGroup.setY(expectedY);
		assertEquals(expectedY, shapesGroup.getShapes().get(0).getY());
	}

	@Test
	void getCenterOfGroup() {
		addShapes();
		Point center = shapesGroup.getCenter();
		assertEquals((6 + (22 + 320)) / 2, center.getX());
		assertEquals((11 + (13 + 130)) / 2, center.getY());
	}
	
	@Test
	void setWidth() {
		addShapes();
		shapesGroup.setWidth(100);
		
		for (Shape shape : shapesGroup.getShapes()) {
			assertEquals(100, shape.getWidth());
		}
	}
	
	@Test
	void setHeight() {
		addShapes();
		shapesGroup.setHeight(100);
		
		for (Shape shape : shapesGroup.getShapes()) {
			assertEquals(100, shape.getHeight());
		}
	}
	
	@Test
	void makeCopy() {
		addShapes();
		ShapesGroup copy = shapesGroup.makeCopy();
		
		for (int i = 0 ; i < shapesGroup.getShapes().size() ; i++) {
			assertEquals(shapesGroup.getShapes().get(i).getShapeId(), copy.getShapes().get(i).getShapeId());
		}
	}
	
	@Test
	void flipHorizontally() {
		addShapes();
		shapesGroup.flipHorizontally();
		
		for (Shape shape : shapesGroup.getShapes()) {
			assertTrue(shape.isFlippedHorizontally());
		}
	}
	
	@Test
	void flipVertically() {
		addShapes();
		shapesGroup.flipVertically();
		
		for (Shape shape : shapesGroup.getShapes()) {
			assertTrue(shape.isFlippedVertically());
		}
	}
	
	void addShapes() {
		shapesGroup.add(ShapeFactory.getShape(ShapeType.RECTANGLE, true, 3, new Point(6, 11), new Point(0, 0),
				new Size(200, 10), 0, new Stroke(1, "#ff1ff"), "#fff56", "", "test"));
		shapesGroup.add(ShapeFactory.getShape(ShapeType.ELLIPSE, true, 5, new Point(7, 12), new Point(0, 0),
				new Size(220, 100), 30, new Stroke(2, "#fff98"), "#ff678", "", "test"));
		shapesGroup.add(ShapeFactory.getShape(ShapeType.HEART, true, 3, new Point(22, 13), new Point(0, 0),
				new Size(320, 130), 100, new Stroke(6, "#fff45"), "#f678f", "", "test"));
		assertEquals(3, shapesGroup.getShapes().size());
	}
}
